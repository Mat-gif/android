package com.example.producteurapp.ui.accueil

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.producteurapp.AppViewModel
import com.example.producteurapp.R
import com.example.producteurapp.localStorage.Storage
import com.example.producteurapp.model.CategorieProducteur
import com.example.producteurapp.model.CategorieProduit
import com.example.producteurapp.model.request.ProducteurRequest
import com.example.producteurapp.model.request.ProduitRequest
import com.example.producteurapp.model.response.ProduitReponse

class EditerProduitFragment : DialogFragment() {

    private lateinit var store : Storage
    private lateinit var appViewModel: AppViewModel
    lateinit var categorie: CategorieProduit
    lateinit var produit : ProduitReponse

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as a dialog or embedded fragment.
        var root = inflater.inflate(R.layout.fragment_editer_produit, container, false)

        appViewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]


        appViewModel.produit.observe(viewLifecycleOwner, Observer { produit ->
            root.findViewById<EditText>(R.id.editer_nom_produit).setText(produit.nom)
            root.findViewById<EditText>(R.id.editer_prix_produit).setText(produit.prix.toString())
            root.findViewById<EditText>(R.id.editer_description_produit).setText(produit.description)
            root.findViewById<EditText>(R.id.editer_quantite_produit).setText(produit.quantite.toString())

        })


        val autoCompleteTextView = root.findViewById<AutoCompleteTextView>(R.id.editer_categorie_produit)

        autoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            when (selectedItem) {
                "AUTRE" -> categorie = CategorieProduit.AUTRES
                "FRUIT" -> categorie = CategorieProduit.FRUIT
                "LEGUME" -> categorie = CategorieProduit.LEGUME
                "MIEL" -> categorie = CategorieProduit.MIEL
                "POISSON" -> categorie = CategorieProduit.POISSON
                "VIANDE" -> categorie = CategorieProduit.VIANDE

            }

            println("Élément sélectionné : $selectedItem")
        }


        appViewModel.produit.observe(viewLifecycleOwner, Observer { p ->
          produit = p
        })



        root.findViewById<Button>(R.id.boutton_editer_produit_valider).setOnClickListener {
            appViewModel.putProduit(
                ProduitRequest(
                    produit.id!!,
                root.findViewById<EditText>(R.id.editer_nom_produit).text.toString(),
                root.findViewById<EditText>(R.id.editer_prix_produit).text.toString().toDouble(),
                root.findViewById<EditText>(R.id.editer_description_produit).text.toString(),
                root.findViewById<EditText>(R.id.editer_quantite_produit).text.toString().toInt(),
                    categorie
            )
            )
            this.dismiss()
        }
        root.findViewById<Button>(R.id.boutton_editer_produit_annuler).setOnClickListener {
            this.dismiss()
        }


        return root


    }


}