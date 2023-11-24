package com.example.producteurapp.ui.connexion

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.producteurapp.AppActivity
import com.example.producteurapp.R
import com.example.producteurapp.databinding.FragmentAccueilBinding
import com.example.producteurapp.databinding.FragmentConnexionBinding
import com.example.producteurapp.http.Http
import com.example.producteurapp.localStorage.Storage
import com.example.producteurapp.request.AuthenticationRequest
import com.example.producteurapp.ui.accueil.AccueilViewModel
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import io.ktor.util.Identity.decode


class ConnexionFragment : Fragment() {
    private var _binding: FragmentConnexionBinding? = null
    private val binding get() = _binding!!
    private lateinit var http : Http
    private lateinit var response : HttpResponse
    private lateinit var store : Storage
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        http= Http(requireContext())
        store= Storage(requireContext())
        val token : String = store.retrieveFromPreferences("token", "")

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


            kotlinx.coroutines.runBlocking {
                response = http.request_post("auth/producteur/authenticate",
                    mapOf(
                        "email" to root.findViewById<EditText>(R.id.connexion_email).text.toString(),
                        "password" to root.findViewById<EditText>(R.id.connexion_password).text.toString()
                    ))
                if (response.status.isSuccess())
                {
                    http.decode(response)["token"]?.let { it1 ->
                        store.saveToPreferences("token",
                            it1
                        )
                    }
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