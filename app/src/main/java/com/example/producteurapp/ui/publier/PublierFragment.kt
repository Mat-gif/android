package com.example.producteurapp.ui.publier

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.producteurapp.viewmodel.AppViewModel
import com.example.producteurapp.R
import com.example.producteurapp.databinding.FragmentPublierBinding
import com.example.producteurapp.model.CategorieProduit
import com.example.producteurapp.model.request.ProduitRequest
import com.example.producteurapp.service.CustomBarService

class PublierFragment : Fragment() {

    private var _binding: FragmentPublierBinding? = null
    private val binding get() = _binding!!
    private lateinit var root : View

    private lateinit var appViewModel: AppViewModel
    lateinit var categorieProduit: CategorieProduit

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Lier le layout XML au fragment
        _binding = FragmentPublierBinding.inflate(inflater, container, false)
        root = binding.root

        // Obtenir l'instance de AppViewModel
        appViewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]

        // Modifier la bar d'action
        CustomBarService(requireActivity(), R.layout.bar_action_publier).init()





        val autoCompleteTextView = root.findViewById<AutoCompleteTextView>(R.id.publier_categorie)

        autoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            when (selectedItem) {
                "AUTRE" -> categorieProduit = CategorieProduit.AUTRES
                "FRUIT" -> categorieProduit = CategorieProduit.FRUIT
                "LEGUME" -> categorieProduit = CategorieProduit.LEGUME
                "MIEL" -> categorieProduit = CategorieProduit.MIEL
                "POISSON" -> categorieProduit = CategorieProduit.POISSON
                "VIANDE" -> categorieProduit = CategorieProduit.VIANDE

            }

            println("Élément sélectionné : $selectedItem")
        }

        root.findViewById<Button>(R.id.bouton_publier).setOnClickListener {



            val produitRequest = ProduitRequest(
                -1, // car pas pris en compte coté serveur
                root.findViewById<EditText>(R.id.publier_nom_produit).text.toString(),
                root.findViewById<EditText>(R.id.publier_prix).text.toString().toDouble(),
                root.findViewById<EditText>(R.id.publier_description).text.toString(),
                root.findViewById<EditText>(R.id.publier_quantite).text.toString().toInt(),
                categorieProduit
            )

            appViewModel.post(produitRequest)

            val navController = findNavController()
            navController.navigate(R.id.navigation_accueil)

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}