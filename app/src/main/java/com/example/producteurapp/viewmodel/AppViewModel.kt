package com.example.producteurapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.producteurapp.localStorage.Storage
import com.example.producteurapp.model.CommandeDTO
import com.example.producteurapp.model.GetRequest
import com.example.producteurapp.model.request.ProducteurRequest
import com.example.producteurapp.model.request.ProduitRequest
import com.example.producteurapp.model.request.Request
import com.example.producteurapp.network.ApiClient
import com.example.producteurapp.viewmodel.vm.OrdersViewModel
import com.example.producteurapp.viewmodel.vm.ProductsViewModel
import com.example.producteurapp.viewmodel.vm.ProfileViewModel


class AppViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var store : Storage

    private val _navigationEvent = MutableLiveData<NavigationEvent>()
    val navigationEvent: LiveData<NavigationEvent> = _navigationEvent

    /**
     * initalisation de http client
     */
    private val apiClient: ApiClient by lazy {ApiClient(getApplication()) }
    private val apiService by lazy { apiClient.apiService }


    // Instanciation des sous-ViewModels
    lateinit var productsViewModel : ProductsViewModel
    lateinit var  profileViewModel : ProfileViewModel
    lateinit var  ordersViewModel : OrdersViewModel


    /**
     * AU CHARGEMENT DE L'ACTIVITE
     */
    init {

        store= Storage(getApplication())
        productsViewModel = ProductsViewModel(application,  store)
        profileViewModel = ProfileViewModel(application, store)
        ordersViewModel = OrdersViewModel(application, store)
        Log.d("INFO-TOKEN",store.decodeToken())
        Log.d("INFO-TOKEN",store.isExpired().toString())
        // j'appelle les methodes
        get(listOf(
            GetRequest.PRODUCTS,
            GetRequest.ORDER,
            GetRequest.PROFIL
        ))
    }


    /**
     *  -- GET --
     */
    fun  get(requests: List<GetRequest>)
    {
        for (request in requests) {
            when (request) {
                GetRequest.PROFIL -> profileViewModel.getProducteur();
                GetRequest.PRODUCTS -> productsViewModel.getProduits()
                GetRequest.ORDER -> ordersViewModel.getCommandes()

                else -> { Log.d("AppViewModel-GET", "the GetRequest is not good") }
            }
        }
    }



    /**
     * -- POST --
     */
    fun  <T : Request>  post(request: T)
    {
        when (request)
        {
            is ProduitRequest -> productsViewModel.postProduit(request);
        }
    }

    /**
     * -- PUT --
     */
    fun  <T : Request>  put(request: T)
    {
        when (request)
        {
            is ProduitRequest -> productsViewModel.putProduit(request);
            is ProducteurRequest -> profileViewModel.putProducteur(request);
            is CommandeDTO -> ordersViewModel.putCommande(request);
        }
    }

    sealed class NavigationEvent {
        object LaunchNewActivity : NavigationEvent()
    }


}