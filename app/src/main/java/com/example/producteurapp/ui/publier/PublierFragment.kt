package com.example.producteurapp.ui.publier

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.producteurapp.AppActivity
import com.example.producteurapp.AppViewModel
import com.example.producteurapp.R
import com.example.producteurapp.databinding.FragmentPublierBinding
import com.example.producteurapp.model.request.ProduitRequest

class PublierFragment : Fragment() {

    private var _binding: FragmentPublierBinding? = null
//    private lateinit var publierVM: PublierViewModel



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var appViewModel: AppViewModel

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        appViewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]

        // Modifier la bar d'action
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.bar_action_publier)

        _binding = FragmentPublierBinding.inflate(inflater, container, false)
        val root: View = binding.root

        root.findViewById<Button>(R.id.bouton_publier).setOnClickListener {




            val produitRequest = ProduitRequest(
                1,
                root.findViewById<EditText>(R.id.publier_nom_produit).text.toString(),
                root.findViewById<EditText>(R.id.publier_prix).text.toString().toDouble(),
                root.findViewById<EditText>(R.id.publier_description).text.toString(),
                root.findViewById<EditText>(R.id.publier_quantite).text.toString().toInt()
            )

            appViewModel.postProduit(produitRequest)
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