package com.example.producteurapp.ui.compte

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider
import com.example.producteurapp.viewmodel.AppViewModel
import com.example.producteurapp.R

import com.example.producteurapp.databinding.FragmentCompteBinding
import com.example.producteurapp.service.CustomBarService
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton


class CompteFragment: Fragment() {

    private var _binding: FragmentCompteBinding? = null
    private val binding get() = _binding!!
    private lateinit var appViewModel: AppViewModel
    private lateinit var root : View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Lier le layout XML au fragment
        _binding = FragmentCompteBinding.inflate(inflater, container, false)
        root = binding.root

        // Obtenir l'instance de AppViewModel
        appViewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]

        // Modifier la bar d'action
        CustomBarService(requireActivity(), R.layout.bar_action_compte).init()

        appViewModel.profileViewModel.profil.observe(viewLifecycleOwner, Observer { profil ->
            profil?.let { // si le profil n'est pas nul
                with(root) {
                    findViewById<TextView>(R.id.compte_nom).text = it.nom
                    findViewById<TextView>(R.id.compte_prenom).text = it.prenom
                    findViewById<TextView>(R.id.compte_adresse).text = it.adresse
                    findViewById<TextView>(R.id.compte_description).text = "Aucune description"
                    findViewById<TextView>(R.id.compte_telephone).text = it.telephone
                    findViewById<TextView>(R.id.compte_categorie).text = it.categorie.toString()
                }
            }
        })

        root.findViewById<ExtendedFloatingActionButton>(R.id.compte_editer).setOnClickListener {
            EditerCompteFragment().show(childFragmentManager,"editerCompte")
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}