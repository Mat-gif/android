package com.example.producteurapp.ui.compte

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider
import com.example.producteurapp.R

import com.example.producteurapp.databinding.FragmentCompteBinding
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton


class CompteFragment: Fragment() {

    private var _binding: FragmentCompteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val compteViewModel =
            ViewModelProvider(this).get(CompteViewModel::class.java)
        // Change ActionBar when this fragment is visible
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar

        // Set custom ActionBar layout for this fragment
        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.bar_action_compte) // Change this to the specific layout for this fragment
        _binding = FragmentCompteBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textCompte
//        compteViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        root.findViewById<ExtendedFloatingActionButton>(R.id.compte_editer).setOnClickListener { EditerCompteFragment().show(childFragmentManager,"editerCompte") }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}