package com.example.producteurapp.ui.accueil


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.producteurapp.R
import com.example.producteurapp.model.Produit
import com.example.producteurapp.model.response.ProduitReponse


class ProduitAdapter(
    var products: List<ProduitReponse>,
    var context: Context
) : RecyclerView.Adapter<ProduitAdapter.ProtuctViewHolder>() {

    class ProtuctViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var produit_nom: TextView = itemView.findViewById(R.id.produit_nom)
        var produit_prix: TextView = itemView.findViewById(R.id.produit_prix)
        var produit_date: TextView = itemView.findViewById(R.id.produit_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProtuctViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.produit_card, parent, false)
        val viewHolder = ProtuctViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ProtuctViewHolder, position: Int) {
        holder.produit_nom.text = products[position].nom
        holder.produit_prix.text = products[position].prix.toString()
        holder.produit_date.text = products[position].date_publication.toString()
        }

    override fun getItemCount(): Int {
        return products.size
    }

    fun updateProducts(newProducts: List<ProduitReponse>) {
        products = newProducts
        notifyDataSetChanged()
    }


}
