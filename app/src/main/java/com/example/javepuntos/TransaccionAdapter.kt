package com.example.javepuntos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.javepuntos.model.Transaccion
import java.text.SimpleDateFormat
import java.util.Locale

class TransaccionAdapter(context:Context,private val transacciones: List<Transaccion>):
    ArrayAdapter<Transaccion>(context,0,transacciones) {
    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItem(position: Int): Transaccion? {
        return super.getItem(position)
    }

    override fun getCount(): Int {
        return super.getCount()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.transaccion_item, parent, false)
        }

        val transaccion = transacciones[position]
        val fecha: TextView = view!!.findViewById(R.id.fecha)
        val puntosRedimidos: TextView = view!!.findViewById(R.id.puntosRedimidos)
        val descripcion: TextView = view!!.findViewById(R.id.descripcion)

        // Formatea la fecha en el formato deseado
        val formatoDeseado = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.getDefault())
        val fechaFormateada = formatoDeseado.format(transaccion.fecha)

        fecha.text = fechaFormateada
        puntosRedimidos.text = "${transaccion.puntos_redimidos} puntos"
        descripcion.text = transaccion.descripcion

        return view
    }


}