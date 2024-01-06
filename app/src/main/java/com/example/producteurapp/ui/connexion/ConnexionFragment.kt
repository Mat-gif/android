package com.example.producteurapp.ui.connexion

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.producteurapp.AppActivity
import com.example.producteurapp.R
import com.example.producteurapp.databinding.FragmentConnexionBinding
import com.example.producteurapp.localStorage.Storage
import com.example.producteurapp.model.request.AuthenticationRequest
import com.google.firebase.messaging.FirebaseMessaging
import java.io.IOException
import java.util.Locale


class ConnexionFragment : Fragment() {
    private var _binding: FragmentConnexionBinding? = null
    private lateinit var connexionVM: ConnexionViewModel
    private var token : String = ""

    val PERMISSION_REQUEST_CODE = 0

    private val binding get() = _binding!!

    private lateinit var store : Storage
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        store = Storage(requireContext())


        _binding = FragmentConnexionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        root.findViewById<Button>(R.id.bouton_pas_de_compte).setOnClickListener {
            Navigation.findNavController(root)
                .navigate(R.id.action_connexionFragment_to_enregistrementFragment)
        }
        root.findViewById<Button>(R.id.bouton_connexion).setOnClickListener {

            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    token = task.result

                    connexionVM = ViewModelProvider(this).get(ConnexionViewModel::class.java)
                    // Example of using the connexionToApi function
                    val authRequest = AuthenticationRequest(
                        root.findViewById<EditText>(R.id.connexion_email).text.toString(),
                        root.findViewById<EditText>(R.id.connexion_password).text.toString(),
                        token
                    )
                    connexionVM.connexionToApi(authRequest)

                    connexionVM.status.observe(viewLifecycleOwner) { status ->

                        if (status == "200") {
                            startActivity(Intent(requireActivity(), AppActivity::class.java))
                            requireActivity().finish()
                        }
                    }
                }
            }
        }
        displayCountryName(root)

        return root
    }
    private fun displayCountryName(root : View) {
        val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val geocoder = Geocoder(requireContext(), Locale.getDefault())

        val hasFineLocationPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasFineLocationPermission) {
            // Demander la permission d'accès à la localisation si elle n'est pas accordée
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_CODE
            )
            return
        }

        locationManager.allProviders.forEach { provider ->
            val location: Location? = locationManager.getLastKnownLocation(provider)
            location?.let {
                try {
                    val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                    if (addresses != null && addresses.isNotEmpty()) {
                        val countryName = addresses[0].countryName
                        root.findViewById<TextView>(R.id.paysloc).text = countryName
                        return@forEach
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // La permission a été accordée, afficher le pays
                displayCountryName(requireView())
            } else {
                // La permission a été refusée
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}