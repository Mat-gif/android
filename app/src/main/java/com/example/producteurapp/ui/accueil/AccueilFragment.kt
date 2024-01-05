package com.example.producteurapp.ui.accueil

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.producteurapp.viewmodel.AppViewModel
import com.example.producteurapp.R
import com.example.producteurapp.databinding.FragmentAccueilBinding
import com.example.producteurapp.model.GetRequest
import com.example.producteurapp.service.CustomBarService
import com.google.android.material.tabs.TabLayout
import java.util.Locale

class AccueilFragment : Fragment() {

    private var _binding: FragmentAccueilBinding? = null
    private val binding get() = _binding!!
    private lateinit var root : View

    lateinit var adapter : ProduitAdapter

    private lateinit var appViewModel: AppViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Lier le layout XML au fragment
        _binding =  FragmentAccueilBinding.inflate(inflater, container, false)
        root = binding.root

        // Obtenir l'instance de AppViewModel
        appViewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]

        // Modifier la bar d'action
        CustomBarService(requireActivity(), R.layout.bar_action_accueil).init()



        val productRecyclerView = root.findViewById<RecyclerView>(R.id.reclyclerView_produit)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        productRecyclerView.layoutManager = layoutManager
        adapter = ProduitAdapter(emptyList(),requireContext()){ prod ->
            appViewModel.productsViewModel.updateProduit(prod)
            EditerProduitFragment().show(childFragmentManager,"EditerProduitFragment")

        }
        productRecyclerView.adapter = adapter


        /**
         * Maj de la liste des produits
         */
        appViewModel.productsViewModel.produits.observe(viewLifecycleOwner, Observer { produits ->

            adapter.updateProducts(produits.filter { it.isDelete !== true }) // maj des produits
            println(produits)
        })



        /**
         * Gestion des filtres a l'aide des TAB
         */
        root.findViewById<TabLayout>(R.id.tabs_accueil).addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 ->  appViewModel.get(listOf(GetRequest.PRODUCTS)) // reinitialiser
                    1 -> appViewModel.productsViewModel.updateProduits(appViewModel.productsViewModel.produits.value!!.sortedBy { it.prix }) // croissant
                    2 -> appViewModel.productsViewModel.updateProduits(appViewModel.productsViewModel.produits.value!!.sortedByDescending { it.prix }) // decroissant
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // TODO : Action lorsqu'un onglet est désélectionné
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
                // TODO : Action lorsqu'un onglet est à nouveau sélectionné
            }
        })

        /***
         * Maj du Prenom de l'utilisateur sur la page d'accueil
         */
        appViewModel.profileViewModel.profil.observe(viewLifecycleOwner, Observer { profil ->
            root.findViewById<TextView>(R.id.accueil_producteur_prenom).text  = profil.prenom
        })

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

