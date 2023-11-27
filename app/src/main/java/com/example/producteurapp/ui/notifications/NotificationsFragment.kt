package com.example.producteurapp.ui.notifications

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.producteurapp.R
import com.example.producteurapp.data.Commandes
import com.example.producteurapp.databinding.FragmentNotificationsBinding
import com.example.producteurapp.ui.accueil.ProduitAdapter
import com.google.android.material.tabs.TabLayout

class NotificationsFragment : Fragment() {
    private var commandes : Commandes = Commandes()
    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter : CommandeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        // Modifier la bar d'action
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar?.setCustomView(R.layout.bar_action_notifications) // Change this to the specific layout for this fragment

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val commandeRecyclerView = root.findViewById<RecyclerView>(R.id.reclyclerView_commande)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        commandeRecyclerView.layoutManager = layoutManager
        adapter = CommandeAdapter(emptyList(),requireContext())

        commandeRecyclerView.adapter = adapter

        notificationsViewModel.commandeLiveData.observe(viewLifecycleOwner, Observer { commandes ->
            // Mettre à jour la liste des produits dans l'adapter
            adapter.updateCommandes(commandes)
        })

        notificationsViewModel.updateCommandesList(commandes.list())


        /**
         * Gestion des filtres a l'aide des TAB
         */
        root.findViewById<TabLayout>(R.id.tabs_notifications).addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

                when (tab?.position) {
                    0 ->  notificationsViewModel.updateCommandesList(commandes.list())
                    1 -> notificationsViewModel.updateCommandesList(commandes.list().filter { it.status == Statut.EN_ATTENTE })
                    2 -> notificationsViewModel.updateCommandesList(commandes.list().filter { it.status == Statut.VALIDEE })
                    3 -> notificationsViewModel.updateCommandesList(commandes.list().filter { it.status == Statut.REFUSEE })

                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // TODO : Action lorsqu'un onglet est désélectionné
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
                // TODO : Action lorsqu'un onglet est à nouveau sélectionné
            }
        })




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}