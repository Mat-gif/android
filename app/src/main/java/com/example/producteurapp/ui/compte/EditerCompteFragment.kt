package com.example.producteurapp.ui.compte



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.producteurapp.viewmodel.AppViewModel
import com.example.producteurapp.R
import com.example.producteurapp.localStorage.Storage
import com.example.producteurapp.model.CategorieProducteur
import com.example.producteurapp.model.request.ProducteurRequest


class EditerCompteFragment : DialogFragment() {

    private lateinit var store : Storage
    private lateinit var appViewModel: AppViewModel
    lateinit var categorieProducteur: CategorieProducteur

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as a dialog or embedded fragment.
        var root = inflater.inflate(R.layout.fragment_editer_compte, container, false)

        appViewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]

        appViewModel.profileViewModel.profil.observe(viewLifecycleOwner, Observer { producteur ->
            root.findViewById<EditText>(R.id.editer_adresse_producteur).setText(producteur.adresse)
            root.findViewById<EditText>(R.id.editer_description_producteur).setText("Une belle petite description")
            root.findViewById<EditText>(R.id.editer_nom_producteur).setText(producteur.nom)
            root.findViewById<EditText>(R.id.editer_prenom_producteur).setText(producteur.prenom)
            root.findViewById<EditText>(R.id.editer_telephone_producteur).setText(producteur.telephone)


        })

        val autoCompleteTextView = root.findViewById<AutoCompleteTextView>(R.id.editer_categorie_producteur)

        autoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            when (selectedItem) {
                "APICULTEUR" -> categorieProducteur = CategorieProducteur.APICULTEUR
                "AGRICULTEUR" -> categorieProducteur = CategorieProducteur.AGRICULTEUR
                "PECHEUR" -> categorieProducteur = CategorieProducteur.PECHEUR

            }

            println("Élément sélectionné : $selectedItem")
        }


        root.findViewById<Button>(R.id.boutton_editer_compte_valider).setOnClickListener {
            appViewModel.put(ProducteurRequest(
                root.findViewById<EditText>(R.id.editer_nom_producteur).text.toString(),
                root.findViewById<EditText>(R.id.editer_prenom_producteur).text.toString(),
                root.findViewById<EditText>(R.id.editer_adresse_producteur).text.toString(),
                root.findViewById<EditText>(R.id.editer_telephone_producteur).text.toString(),
                categorieProducteur
            ))
            this.dismiss()
        }
        root.findViewById<Button>(R.id.boutton_editer_compte_annuler).setOnClickListener {
            this.dismiss()
        }


        return root


    }


}