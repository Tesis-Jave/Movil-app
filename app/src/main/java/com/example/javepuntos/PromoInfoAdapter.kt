package com.example.javepuntos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.javepuntos.model.Articulos

class PromoInfoAdapter(private val context: Context, private val promociones: List<Articulos>) :
    RecyclerView.Adapter<PromoInfoAdapter.PromoViewHolder>() {

    class PromoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val descripcionTextView: TextView = itemView.findViewById(R.id.descripcionTextView)
        // Puedes añadir más vistas si es necesario para mostrar más información de las promociones
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromoViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_promo_info, parent, false)
        return PromoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PromoViewHolder, position: Int) {
        val promo = promociones[position]

        // Configurar los datos de la promoción en los elementos de la vista
        holder.descripcionTextView.text = promo.descripcion
        // Puedes configurar más vistas aquí si es necesario
    }

    override fun getItemCount(): Int {
        return promociones.size
    }
}
