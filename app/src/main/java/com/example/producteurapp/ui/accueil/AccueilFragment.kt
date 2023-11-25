package com.example.producteurapp.ui.accueil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.producteurapp.AppViewModel
import com.example.producteurapp.R
import com.example.producteurapp.data.Produits
import com.example.producteurapp.databinding.FragmentAccueilBinding
import com.example.producteurapp.localStorage.Storage
import com.example.producteurapp.model.response.ProduitReponse
import com.google.android.material.tabs.TabLayout

class AccueilFragment : Fragment() {


    private var _binding: FragmentAccueilBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter : ProduitAdapter
    private lateinit var appViewModel: AppViewModel

    //    lateinit var accueilViewModel : AccueilViewModel
    private lateinit var store : Storage
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//         accueilViewModel = ViewModelProvider(this).get(AccueilViewModel::class.java)

        appViewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]

        // Modifier la bar d'action
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.bar_action_accueil) // Change this to the specific layout for this fragment


        // liaison du fragment
        _binding = FragmentAccueilBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val productRecyclerView = root.findViewById<RecyclerView>(R.id.reclyclerView_produit)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        productRecyclerView.layoutManager = layoutManager
        adapter = ProduitAdapter(emptyList(),requireContext())

        productRecyclerView.adapter = adapter

        /**
         * Maj de la liste des produits
         */
        appViewModel.produits.observe(viewLifecycleOwner, Observer { produits ->
            adapter.updateProducts(produits) // maj des produits
        })

        /**
         * Gestion des filtres a l'aide des TAB
         */
        root.findViewById<TabLayout>(R.id.tabs_accueil).addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 ->  appViewModel.getProduits() // reinitialiser
                    1 -> appViewModel.updateProduits(appViewModel.produits.value!!.sortedBy { it.prix }) // croissant
                    2 -> appViewModel.updateProduits(appViewModel.produits.value!!.sortedByDescending { it.prix }) // decroissant
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
        appViewModel.profil.observe(viewLifecycleOwner, Observer { profil ->
            root.findViewById<TextView>(R.id.accueil_producteur_prenom).text  = profil.prenom
        })

        return root
    }








    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}