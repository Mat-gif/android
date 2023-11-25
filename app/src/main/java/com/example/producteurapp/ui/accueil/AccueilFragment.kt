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
    lateinit var accueilViewModel : AccueilViewModel
    private lateinit var store : Storage
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         accueilViewModel = ViewModelProvider(this).get(AccueilViewModel::class.java)

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

        accueilViewModel.produitLiveData.observe(viewLifecycleOwner, Observer { produits ->
            // Mettre à jour la liste des produits dans l'adapter
            adapter.updateProducts(produits)
        })
//
//
        root.findViewById<TabLayout>(R.id.tabs_accueil).addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

                // Action lorsqu'un onglet est sélectionné
                when (tab?.position) {
                    0 -> {
//                        Toast.makeText(requireContext(),"0",Toast.LENGTH_SHORT).show()
                        accueilViewModel.getProduits()
                    }
                    1 -> {
//                        Toast.makeText(requireContext(),"1",Toast.LENGTH_SHORT).show()
                        var produits :List<ProduitReponse>  = accueilViewModel.produitLiveData.value ?:return

                        accueilViewModel.updateProduitsList(produits.sortedBy { it.prix })
                    }
                    2 -> {
//                        Toast.makeText(requireContext(),"2",Toast.LENGTH_SHORT).show()
                        var produits :List<ProduitReponse>  = accueilViewModel.produitLiveData.value ?:return

                        accueilViewModel.updateProduitsList(produits.sortedByDescending { it.prix })
                    }
                    // Ajoutez plus de cas pour d'autres onglets si nécessaire
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Action lorsqu'un onglet est désélectionné
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Action lorsqu'un onglet est à nouveau sélectionné
            }

        })

        store = Storage(requireContext())
        root.findViewById<TextView>(R.id.accueil_producteur_prenom).text  = store.getProfil().prenom


        return root
    }








    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}