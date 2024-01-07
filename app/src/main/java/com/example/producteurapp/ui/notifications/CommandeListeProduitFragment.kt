package com.example.producteurapp.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.producteurapp.viewmodel.AppViewModel
import com.example.producteurapp.R
import com.example.producteurapp.localStorage.Storage
import com.example.producteurapp.model.StatutCommande
import com.example.producteurapp.model.CommandeDTO
import com.example.producteurapp.model.response.ProduitReponse


class CommandeListeProduitFragment : DialogFragment() {


    private lateinit var store : Storage
    private lateinit var appViewModel: AppViewModel
    lateinit var adapter : CommandeListeProduitAdapter
    lateinit var produits : List<ProduitReponse> ;
    var commande : CommandeDTO = CommandeDTO(null,null,null,null,null)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as a dialog or embedded fragment.
        var root = inflater.inflate(R.layout.fragment_commande_liste_produit, container, false)
        appViewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]
        appViewModel.productsViewModel.produits.observe(viewLifecycleOwner, Observer { p ->
            produits = p
        })



        val productRecyclerView = root.findViewById<RecyclerView>(R.id.recyclerView_produit_commande)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        productRecyclerView.layoutManager = layoutManager
        adapter = CommandeListeProduitAdapter(commande,
            emptyList(),requireContext()){ produitQuantiteResponse ->
            adapter.updateProduitsCommande(commande, produits)

        }
        productRecyclerView.adapter = adapter


        /**
         * Maj de la liste des produits
         */
        appViewModel.ordersViewModel.commande.observe(viewLifecycleOwner, Observer { c ->
            commande = c!!
            adapter.updateProduitsCommande(commande, produits)

            if( commande.status != StatutCommande.EN_ATTENTE_DE_VALIDATION)
            {
                root.findViewById<Button>(R.id.boutton_valider_commande).visibility = View.INVISIBLE
                root.findViewById<Button>(R.id.boutton_refuser_commande).visibility = View.INVISIBLE
            }
            if( commande.status == StatutCommande.EN_ATTENTE_DE_VALIDATION)
            {
                root.findViewById<Button>(R.id.boutton_valider_commande).visibility = View.VISIBLE
                root.findViewById<Button>(R.id.boutton_refuser_commande).visibility = View.VISIBLE
            }


        })




        root.findViewById<Button>(R.id.boutton_valider_commande).setOnClickListener {listenerAction(StatutCommande.VALIDE)}
        root.findViewById<Button>(R.id.boutton_refuser_commande).setOnClickListener {listenerAction(StatutCommande.REFUS) }




        return root


    }


    private fun listenerAction(status : StatutCommande)
    {
        commande.status = status
        appViewModel.put(commande)
        this.dismiss()
    }



}