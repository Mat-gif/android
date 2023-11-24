package com.example.producteurapp.ui.connexion

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.producteurapp.AppActivity
import com.example.producteurapp.R
import com.example.producteurapp.databinding.FragmentConnexionBinding
import com.example.producteurapp.localStorage.Storage
import com.example.producteurapp.network.ConnexionViewModel
import com.example.producteurapp.request.AuthenticationRequest


class ConnexionFragment : Fragment() {
    private var _binding: FragmentConnexionBinding? = null
    private lateinit var connexionVM: ConnexionViewModel
    private val binding get() = _binding!!
//    private lateinit var http : Http
//    private lateinit var response : HttpResponse
    private lateinit var store : Storage
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        store = Storage(requireContext())
        val token : String = store.getToken()

        if (token != "")  {

            startActivity(Intent(requireActivity(), AppActivity::class.java))
            requireActivity().finish()
        }


        _binding = FragmentConnexionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        root.findViewById<Button>(R.id.bouton_pas_de_compte).setOnClickListener {
            Navigation.findNavController(root)
                .navigate(R.id.action_connexionFragment_to_enregistrementFragment)
        }
        root.findViewById<Button>(R.id.bouton_connexion).setOnClickListener {



            connexionVM = ViewModelProvider(this).get(ConnexionViewModel::class.java)

            // Example of using the connexionToApi function
            val authRequest = AuthenticationRequest(
                root.findViewById<EditText>(R.id.connexion_email).text.toString(),
                 root.findViewById<EditText>(R.id.connexion_password).text.toString()
            )
            connexionVM.connexionToApi(authRequest)

            // Observe changes in status LiveData if needed
            connexionVM.status.observe(viewLifecycleOwner) { status ->

                if (status == "200"){
                    startActivity(Intent(requireActivity(), AppActivity::class.java))
                    requireActivity().finish()
                }

            }

        }




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}