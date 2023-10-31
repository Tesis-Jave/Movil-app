package com.example.javepuntos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.javepuntos.model.Articulos

class ArticulosAdapter(context: Context, private val articulos: List<Articulos>) :
    ArrayAdapter<Articulos>(context, 0, articulos) {
    override fun getItemId(position: Int): Long {
        // Devuelve el ID del artículo en la posición dada
        return articulos[position].idArticulo.toLong()
    }

    override fun getItem(position: Int): Articulos {
        // Devuelve el artículo en la posición dada
        return articulos[position]
    }

    override fun getCount(): Int {
        // Devuelve la cantidad total de artículos en la lista
        return articulos.size
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.articulo_item, parent, false)
        }

        val articulo = articulos[position]

        // Configurar los elementos de la vista (item_articulo.xml)
        val nombreTextView: TextView = view!!.findViewById(R.id.nombreArticuloTextView)
        val precioTextView: TextView = view.findViewById(R.id.precioArticuloTextView)

        // Configurar los valores del artículo en los TextViews
        nombreTextView.text = articulo.descripcion // Asegúrate de tener el atributo correcto para el nombre del artículo
        precioTextView.text = "$${articulo.precio}" // Asegúrate de tener el atributo correcto para el precio del artículo

        return view
    }
}
