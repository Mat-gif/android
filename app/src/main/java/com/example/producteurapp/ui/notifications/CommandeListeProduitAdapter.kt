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
import com.example.producteurapp.model.response.ProduitQuantiteResponse
import com.example.producteurapp.model.response.ProduitReponse


class CommandeListeProduitAdapter(
    var produitQuantites: List<ProduitQuantiteResponse>,
//    var produits: List<ProduitReponse>,
    var context: Context
) : RecyclerView.Adapter<CommandeListeProduitAdapter.CommandeListeProduitViewHolder>() {

    class CommandeListeProduitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var produitQuantites_produit_nom: TextView = itemView.findViewById(R.id.produit_commande_produit_nom)
        var produitQuantites_produit_quantite: TextView = itemView.findViewById(R.id.produit_commande_quantite)
        var produitQuantites_produit_statut: TextView = itemView.findViewById(R.id.produit_commande_statut)

    }
    interface OnCommandeClickListener {
        fun onCommandeClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommandeListeProduitViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.produit_commande_card, parent, false)
        val commandeViewHolder = CommandeListeProduitViewHolder(view)


        return commandeViewHolder
    }

    // Méthode pour remplir les données dans la vue (appelée pour chaque élément de la liste)
    override fun onBindViewHolder(holder: CommandeListeProduitViewHolder, position: Int) {
        holder.produitQuantites_produit_nom.text = produitQuantites[position].id.toString()
        holder.produitQuantites_produit_quantite.text = produitQuantites[position].quantite.toString()
        holder.produitQuantites_produit_statut.text = produitQuantites[position].statusProduitQuantite.toString()
        }

    // Méthode pour obtenir le nombre total d'éléments à afficher dans la liste
    override fun getItemCount(): Int {
        return produitQuantites.size
    }

    fun updateProduitsCommande(newProduitQuantites: List<ProduitQuantiteResponse>) {
        produitQuantites = newProduitQuantites
        notifyDataSetChanged()
    }




}
