package com.example.producteurapp.ui.notifications


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.producteurapp.R
import com.example.producteurapp.model.StatutCommande
import com.example.producteurapp.model.StatutProduitQuantite
import com.example.producteurapp.model.CommandeDTO
import com.example.producteurapp.model.response.ProduitQuantiteResponse
import com.example.producteurapp.model.response.ProduitReponse
import com.example.producteurapp.viewmodel.AppViewModel


class CommandeListeProduitAdapter(
    var commande: CommandeDTO,
    var  produits : List<ProduitReponse>,
    var context: Context,
    private val onItemClick: (ProduitQuantiteResponse) -> Unit // Ajoutez un paramètre de fonction pour l'OnClick
) : RecyclerView.Adapter<CommandeListeProduitAdapter.CommandeListeProduitViewHolder>() {

    class CommandeListeProduitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var produitQuantites_produit_nom: TextView = itemView.findViewById(R.id.produit_commande_produit_nom)
        var produitQuantites_produit_quantite: TextView = itemView.findViewById(R.id.produit_commande_quantite)
        var produitQuantites_produit_statut: TextView = itemView.findViewById(R.id.produit_commande_statut)
        var refuser : ImageView = itemView.findViewById(R.id.produit_commande_refuser)
        var valider : ImageView = itemView.findViewById(R.id.produit_commande_valider)
    }
    interface OnCommandeClickListener {
        fun onCommandeClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommandeListeProduitViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.produit_commande_card, parent, false)
        val commandeViewHolder = CommandeListeProduitViewHolder(view)

        commandeViewHolder.refuser.setOnClickListener {
            if(commande.status == StatutCommande.EN_ATTENTE_DE_VALIDATION) {

                val position = commandeViewHolder.adapterPosition
                commande.produits!![position].statusProduitQuantite = StatutProduitQuantite.ACCEPT
                it.visibility = View.INVISIBLE
                commandeViewHolder.valider.visibility = View.VISIBLE
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(commande.produits!![position])
                    Log.d(
                        "CommandeListeProduitAdapter",
                        "Item Statut Clicked: ${commande.produits!![position].statusProduitQuantite}"
                    )

                }
            }

        }
        commandeViewHolder.valider.setOnClickListener {
            if(commande.status == StatutCommande.EN_ATTENTE_DE_VALIDATION) {
                val position = commandeViewHolder.adapterPosition
                commande.produits!![position].statusProduitQuantite = StatutProduitQuantite.REFUSE
                it.visibility = View.INVISIBLE
                commandeViewHolder.refuser.visibility = View.VISIBLE
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(commande.produits!![position])
                    Log.d(
                        "CommandeListeProduitAdapter",
                        "Item Statut Clicked: ${commande.produits!![position].statusProduitQuantite}"
                    )
                }
            }
        }



        return commandeViewHolder
    }

    // Méthode pour remplir les données dans la vue (appelée pour chaque élément de la liste)
    override fun onBindViewHolder(holder: CommandeListeProduitViewHolder, position: Int) {




//        val p : ProduitReponse = produits.first { it.id == commande.produits!![position].id }



        if(commande.produits!![position].statusProduitQuantite == StatutProduitQuantite.ACCEPT)
        {
            holder.valider.visibility = View.VISIBLE
            holder.refuser.visibility = View.INVISIBLE
        }else{
            holder.valider.visibility = View.INVISIBLE
            holder.refuser.visibility = View.VISIBLE
        }
        holder.produitQuantites_produit_nom.text =  "Id : "+  commande.produits!![position].id.toString()


        holder.produitQuantites_produit_quantite.text = commande.produits!![position].quantite.toString()
        holder.produitQuantites_produit_statut.text = commande.produits!![position].statusProduitQuantite.toString()
        }



    // Méthode pour obtenir le nombre total d'éléments à afficher dans la liste
    override fun getItemCount(): Int {
        return commande.produits!!.size
    }

    fun updateProduitsCommande(newCommande: CommandeDTO, newProduits : List<ProduitReponse>) {
        commande = newCommande
        produits = newProduits
        notifyDataSetChanged()
    }




}
