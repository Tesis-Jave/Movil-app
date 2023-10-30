package com.example.javepuntos

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.javepuntos.model.Cafeteria
import com.squareup.picasso.Picasso

class CafeteriasAdapter(private val context: Context, private val cafeterias: List<Cafeteria>): BaseAdapter() {

    override fun getCount(): Int {
        return cafeterias.size
    }

    override fun getItem(position: Int): Any {
        return cafeterias[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val cafeteria = getItem(position) as Cafeteria

        val view = LayoutInflater.from(context).inflate(R.layout.cafeteria_item, parent, false)

        val cafeteriaImageView : ImageView = view.findViewById(R.id.departamentoImageView)
        val cafeteriaNombreTextView: TextView = view.findViewById(R.id.cafeteriaNombreTextView)

        // Cargar la imagen desde la URL
        Picasso.get()
            .load(cafeteria.url)
            .placeholder(R.drawable.imagen_dummie)
            .error(R.drawable.error)
            .into(cafeteriaImageView)

        cafeteriaNombreTextView.text = cafeteria.descripcion
        view.setOnClickListener{
            // accion de cuando se interactue con la imagen de la cafeteia aca
        }

        return view
    }
}
