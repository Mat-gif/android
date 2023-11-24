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



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}