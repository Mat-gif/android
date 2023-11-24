package com.example.producteurapp.ui.accueil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class AccueilFragment : Fragment() {

    private  var produits : Produits = Produits()
    private var _binding: FragmentAccueilBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter : ProduitAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val accueilViewModel = ViewModelProvider(this).get(AccueilViewModel::class.java)

        // Modifier la bar d'action
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.bar_action_accueil) // Change this to the specific layout for this fragment

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

        accueilViewModel.updateProduitsList(produits.list())

        return root
    }









    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}