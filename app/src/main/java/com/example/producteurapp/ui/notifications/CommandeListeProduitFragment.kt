package com.example.producteurapp.ui.notifications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.producteurapp.AppViewModel
import com.example.producteurapp.R
import com.example.producteurapp.localStorage.Storage
import com.example.producteurapp.model.request.ProducteurRequest
import com.example.producteurapp.ui.accueil.ProduitAdapter


class CommandeListeProduitFragment : DialogFragment() {


    private lateinit var store : Storage
    private lateinit var appViewModel: AppViewModel
    lateinit var adapter : CommandeListeProduitAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as a dialog or embedded fragment.
        var root = inflater.inflate(R.layout.fragment_commande_liste_produit, container, false)
        appViewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]



        val productRecyclerView = root.findViewById<RecyclerView>(R.id.recyclerView_produit_commande)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        productRecyclerView.layoutManager = layoutManager
        adapter = CommandeListeProduitAdapter(emptyList(),requireContext())
        productRecyclerView.adapter = adapter


        /**
         * Maj de la liste des produits
         */
        appViewModel.commande.observe(viewLifecycleOwner, Observer { commande ->
            commande.produits?.let { adapter.updateProduitsCommande(it) }
        })


//        appViewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]
//
//        appViewModel.profil.observe(viewLifecycleOwner, Observer { producteur ->
//            root.findViewById<EditText>(R.id.editer_adresse_producteur).setText(producteur.adresse)
//            root.findViewById<EditText>(R.id.editer_description_producteur).setText("Une belle petite description")
//            root.findViewById<EditText>(R.id.editer_nom_producteur).setText(producteur.nom)
//            root.findViewById<EditText>(R.id.editer_prenom_producteur).setText(producteur.prenom)
//            root.findViewById<EditText>(R.id.editer_telephone_producteur).setText(producteur.telephone)
//
//        })
//
//
//        root.findViewById<Button>(R.id.boutton_editer_compte_valider).setOnClickListener {
//            appViewModel.putProducteur(
//                ProducteurRequest(
//                root.findViewById<EditText>(R.id.editer_nom_producteur).text.toString(),
//                root.findViewById<EditText>(R.id.editer_prenom_producteur).text.toString(),
//                root.findViewById<EditText>(R.id.editer_adresse_producteur).text.toString(),
//                root.findViewById<EditText>(R.id.editer_telephone_producteur).text.toString()
//            )
//            )
//            this.dismiss()
//        }
//        root.findViewById<Button>(R.id.boutton_editer_compte_annuler).setOnClickListener {
//            this.dismiss()
//        }


        return root


    }

}