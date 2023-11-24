package com.example.producteurapp.ui.publier

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.producteurapp.AppActivity
import com.example.producteurapp.R
import com.example.producteurapp.databinding.FragmentPublierBinding
import com.example.producteurapp.http.Http
import com.example.producteurapp.localStorage.Storage
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

class PublierFragment : Fragment() {

    private var _binding: FragmentPublierBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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
        val publierViewModel = ViewModelProvider(this).get(PublierViewModel::class.java)

        // Modifier la bar d'action
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.bar_action_publier)

        _binding = FragmentPublierBinding.inflate(inflater, container, false)
        val root: View = binding.root

        root.findViewById<Button>(R.id.bouton_publier).setOnClickListener {



            kotlinx.coroutines.runBlocking {
                response = http.request_post("auth/producteur/produit",
                    mapOf(
                        "id" to null,
                        "nom" to root.findViewById<EditText>(R.id.publier_nom_produit).text.toString(),
                        "prix" to root.findViewById<EditText>(R.id.publier_prix).text.toString().toDouble(),
                        "description" to root.findViewById<EditText>(R.id.publier_description).text.toString(),
                        "quantite" to root.findViewById<EditText>(R.id.publier_quantite).text.toString().toDouble()
                    ))

                println(http.decode(response))
            }
        }

//        val textView: TextView = binding.textDashboard
//        publierViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}