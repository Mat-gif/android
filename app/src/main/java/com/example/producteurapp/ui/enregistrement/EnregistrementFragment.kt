package com.example.producteurapp.ui.enregistrement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.producteurapp.R
import com.example.producteurapp.databinding.FragmentConnexionBinding
import com.example.producteurapp.databinding.FragmentEnregistrementBinding

class EnregistrementFragment : Fragment() {
    private var _binding: FragmentEnregistrementBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnregistrementBinding.inflate(inflater, container, false)
        val root: View = binding.root

        root.findViewById<Button>(R.id.bouton_deja_un_compte).setOnClickListener { Navigation.findNavController(root).navigate(
            R.id.action_enregistrementFragment_to_connexionFragment) }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}