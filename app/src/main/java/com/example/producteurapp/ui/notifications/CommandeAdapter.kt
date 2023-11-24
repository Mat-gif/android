package com.example.producteurapp.ui.notifications


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.producteurapp.R
import com.example.producteurapp.model.Commande



class CommandeAdapter(
    var commandes: List<Commande>,
    var context: Context
) : RecyclerView.Adapter<CommandeAdapter.CommandeViewHolder>() {

    class CommandeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var commande_client_nom: TextView = itemView.findViewById(R.id.commande_client_nom)
        var commande_date: TextView = itemView.findViewById(R.id.commande_date)
        var commande_statut: TextView = itemView.findViewById(R.id.commande_statut)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommandeViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.commande_card, parent, false)
        return CommandeViewHolder(view)
    }

    // Méthode pour remplir les données dans la vue (appelée pour chaque élément de la liste)
    override fun onBindViewHolder(holder: CommandeViewHolder, position: Int) {
        holder.commande_client_nom.text = commandes[position].client
        holder.commande_date.text = commandes[position].date
        holder.commande_statut.text = commandes[position].status.toString()
        }

    // Méthode pour obtenir le nombre total d'éléments à afficher dans la liste
    override fun getItemCount(): Int {
        return commandes.size
    }

    fun updateCommandes(newCommandes: List<Commande>) {
        commandes = newCommandes
        notifyDataSetChanged()
    }




}
