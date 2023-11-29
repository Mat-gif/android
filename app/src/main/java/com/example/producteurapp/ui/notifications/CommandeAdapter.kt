package com.example.producteurapp.ui.notifications


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.producteurapp.R
import com.example.producteurapp.model.Commande
import com.example.producteurapp.model.Produit
import com.example.producteurapp.model.response.CommandeReponse
import com.example.producteurapp.model.response.ProduitReponse


class CommandeAdapter(
    var commandes: List<CommandeReponse>,
    var context: Context,
    private val onItemClick: (CommandeReponse) -> Unit // Ajoutez un paramètre de fonction pour l'OnClick
) : RecyclerView.Adapter<CommandeAdapter.CommandeViewHolder>() {

    class CommandeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var commande_client_nom: TextView = itemView.findViewById(R.id.commande_client_nom)
        var commande_date: TextView = itemView.findViewById(R.id.commande_date)
        var commande_statut: TextView = itemView.findViewById(R.id.commande_statut)
        var card: CardView = itemView.findViewById(R.id.commande_card)

    }
    interface OnCommandeClickListener {
        fun onCommandeClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommandeViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.commande_card, parent, false)
        val commandeViewHolder = CommandeViewHolder(view)

        commandeViewHolder.itemView.setOnClickListener {
            val position = commandeViewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClick(commandes[position])
                Log.d("CommandeAdapter", "Item Clicked: ${commandes[position].client_id}")
            }
        }

        return commandeViewHolder
    }

    // Méthode pour remplir les données dans la vue (appelée pour chaque élément de la liste)
    override fun onBindViewHolder(holder: CommandeViewHolder, position: Int) {
        holder.commande_client_nom.text = commandes[position].client_id
        holder.commande_date.text = commandes[position].date
        holder.commande_statut.text = commandes[position].status.toString()
        }

    // Méthode pour obtenir le nombre total d'éléments à afficher dans la liste
    override fun getItemCount(): Int {
        return commandes.size
    }

    fun updateCommandes(newCommandes: List<CommandeReponse>) {
        commandes = newCommandes
        notifyDataSetChanged()
    }




}
