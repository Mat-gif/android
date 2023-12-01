package com.example.producteurapp.ui.enregistrement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.producteurapp.R
import com.example.producteurapp.databinding.FragmentEnregistrementBinding
import com.example.producteurapp.model.request.RegisterRequest
import com.example.producteurapp.ui.connexion.ConnexionViewModel
import com.google.firebase.messaging.FirebaseMessaging

class EnregistrementFragment : Fragment() {
    private var _binding: FragmentEnregistrementBinding? = null
    private val binding get() = _binding!!
    private lateinit var connexionVM: ConnexionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnregistrementBinding.inflate(inflater, container, false)
        val root: View = binding.root

        root.findViewById<Button>(R.id.bouton_deja_un_compte).setOnClickListener { Navigation.findNavController(root).navigate(
            R.id.action_enregistrementFragment_to_connexionFragment) }




        root.findViewById<Button>(R.id.bouton_enregistrer).setOnClickListener {



                    connexionVM = ViewModelProvider(this).get(ConnexionViewModel::class.java)


                    val registerRequest = RegisterRequest(
                        root.findViewById<EditText>(R.id.enregistrement_email).text.toString(),
                        root.findViewById<EditText>(R.id.enregistrement_password).text.toString(),
                        root.findViewById<EditText>(R.id.enregistrement_nom).text.toString()
                    )


                    connexionVM.enregistrementToApi(registerRequest)

                    connexionVM.status.observe(viewLifecycleOwner) { status ->

                        if (status == "200") {
                            Navigation.findNavController(root).navigate(
                                R.id.action_enregistrementFragment_to_connexionFragment
                            )
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