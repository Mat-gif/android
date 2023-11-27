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
import com.example.producteurapp.AppViewModel
import com.example.producteurapp.R

import com.example.producteurapp.databinding.FragmentCompteBinding
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton


class CompteFragment: Fragment() {

    private var _binding: FragmentCompteBinding? = null
    private val binding get() = _binding!!
    private lateinit var appViewModel: AppViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        appViewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]

        // Modifier la bar d'action
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.bar_action_compte) // Change this to the specific layout for this fragment

        _binding = FragmentCompteBinding.inflate(inflater, container, false)
        val root: View = binding.root



        appViewModel.profil.observe(viewLifecycleOwner, Observer { profil ->
            root.findViewById<TextView>(R.id.compte_nom).text = profil.nom
            root.findViewById<TextView>(R.id.compte_prenom).text = profil.prenom
            root.findViewById<TextView>(R.id.compte_adresse).text = profil.adresse
            root.findViewById<TextView>(R.id.compte_description).text = "Une petite description"
            root.findViewById<TextView>(R.id.compte_telephone).text = profil.telephone
            root.findViewById<TextView>(R.id.compte_categorie).text = profil.categorie.toString()
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