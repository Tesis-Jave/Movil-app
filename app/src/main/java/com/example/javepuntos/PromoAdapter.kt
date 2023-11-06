package com.example.javepuntos

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.javepuntos.model.Promociones

class PromoAdapter(private val context: Context, private val promociones: List<Promociones>, val token: String) : BaseAdapter() {
    override fun getCount(): Int {
        return promociones.size
    }

    override fun getItem(position: Int): Promociones {
        return promociones[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val promo = getItem(position)

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.promo_list_item, null)

        // Obtener referencias a los elementos de la vista
        val descripcionTextView: TextView = view.findViewById(R.id.descripcionTextView)

        // Configurar los datos de la promoción en los elementos de la vista
        descripcionTextView.text = promo.descripcion

        descripcionTextView.setOnClickListener {
            // Crear un Intent para iniciar la nueva actividad (PromoInfoActivity)
            val intent = Intent(context, PromoInfoActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            // Pasar datos adicionales si es necesario
            intent.putExtra("PROMO_ID", promo.idPromocion) // Por ejemplo, pasando el ID de la promoción

            // Iniciar la nueva actividad
            context.startActivity(intent)
        }

        return view
    }

}