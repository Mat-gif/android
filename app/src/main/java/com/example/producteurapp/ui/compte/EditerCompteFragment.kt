package com.example.producteurapp.ui.compte


import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.producteurapp.R
import com.example.producteurapp.localStorage.Storage
import com.example.producteurapp.model.request.ProducteurRequest
import com.example.producteurapp.model.response.ProducteurResponse


class EditerCompteFragment : DialogFragment() {

    private lateinit var store : Storage
    lateinit var compteViewMode: CompteViewMode

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as a dialog or embedded fragment.
        var root = inflater.inflate(R.layout.fragment_editer_compte, container, false)

        store = Storage(requireContext())

        val producteur : ProducteurResponse = store.getProfil()

        root.findViewById<EditText>(R.id.editer_adresse_producteur).setText(producteur.adresse)
        root.findViewById<EditText>(R.id.editer_description_producteur).setText("Une belle petite description")
        root.findViewById<EditText>(R.id.editer_nom_producteur).setText(producteur.nom)
        root.findViewById<EditText>(R.id.editer_prenom_producteur).setText(producteur.prenom)
        root.findViewById<EditText>(R.id.editer_telephone_producteur).setText(producteur.telephone)
        compteViewMode = ViewModelProvider(this).get(CompteViewMode::class.java)



        root.findViewById<Button>(R.id.boutton_editer_compte_valider).setOnClickListener {
            compteViewMode.modifierProfil(ProducteurRequest(
                root.findViewById<EditText>(R.id.editer_nom_producteur).text.toString(),
                root.findViewById<EditText>(R.id.editer_prenom_producteur).text.toString(),
                root.findViewById<EditText>(R.id.editer_adresse_producteur).text.toString(),
                root.findViewById<EditText>(R.id.editer_telephone_producteur).text.toString()
            ))
//            this.dismiss()

        }
        root.findViewById<Button>(R.id.boutton_editer_compte_annuler).setOnClickListener {

//            this.dismiss()
        }


        return root


    }


}