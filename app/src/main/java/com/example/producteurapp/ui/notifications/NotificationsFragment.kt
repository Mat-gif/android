package com.example.producteurapp.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.producteurapp.AppViewModel
import com.example.producteurapp.R
import com.example.producteurapp.data.Commandes
import com.example.producteurapp.databinding.FragmentNotificationsBinding
import com.example.producteurapp.model.Commande
import com.example.producteurapp.model.StatutCommande
import com.example.producteurapp.model.response.CommandeReponse
import com.example.producteurapp.model.response.ProduitReponse
import com.example.producteurapp.ui.compte.EditerCompteFragment
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.tabs.TabLayout

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter : CommandeAdapter
    private lateinit var appViewModel: AppViewModel
    private lateinit var produits : List<ProduitReponse>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        appViewModel = ViewModelProvider(requireActivity())[AppViewModel::class.java]
        // Modifier la bar d'action
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.bar_action_notifications) // Change this to the specific layout for this fragment

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val commandeRecyclerView = root.findViewById<RecyclerView>(R.id.reclyclerView_commande)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        commandeRecyclerView.layoutManager = layoutManager
        adapter = CommandeAdapter(emptyList(),requireContext()){ commande ->

            appViewModel.updateCommande(commande)
            CommandeListeProduitFragment().show(childFragmentManager,"commandeListeProduits")


        }

        commandeRecyclerView.adapter = adapter
        /**
         * Maj de la liste des produits
         */
        appViewModel.produits.observe(viewLifecycleOwner, Observer { p ->
            produits = p
        })


        /**
         * Maj de la liste des commandes
         */
        appViewModel.commandes.observe(viewLifecycleOwner, Observer { c ->
            adapter.updateCommandes(c)
        })




        /**
         * Gestion des filtres a l'aide des TAB
         */
        root.findViewById<TabLayout>(R.id.tabs_notifications).addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 ->  {

                    }
                    1 -> {
                        adapter.updateCommandes(appViewModel.commandes.value!!.filter { it.status == StatutCommande.EN_ATTENTE_DE_VALIDATION })
                    }
                    2 -> {
                        adapter.updateCommandes(appViewModel.commandes.value!!.filter { it.status == StatutCommande.VALIDE })
                    }
                    3 -> {
                        adapter.updateCommandes(appViewModel.commandes.value!!.filter { it.status == StatutCommande.REFUS })
                    }

                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // TODO : Action lorsqu'un onglet est désélectionné
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
                // TODO : Action lorsqu'un onglet est à nouveau sélectionné
            }
        })


//        root.findViewById<ExtendedFloatingActionButton>(R.id.commande_liste_produits).setOnClickListener {
//            CommandeListeProduitFragment().show(childFragmentManager,"commandeListeProduits")
//        }





        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}