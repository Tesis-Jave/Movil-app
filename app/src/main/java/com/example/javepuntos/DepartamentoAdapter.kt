
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.javepuntos.R
import com.example.javepuntos.model.Departamento
import com.squareup.picasso.Picasso

class DepartamentoAdapter(private val context: Context, private val departamentos: List<Departamento>) : BaseAdapter() {
    override fun getCount(): Int {
        return departamentos.size
    }

    override fun getItem(position: Int): Departamento {
        return departamentos[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val departamento = getItem(position)

        val view = LayoutInflater.from(context).inflate(R.layout.departamento_item, parent, false)

        // Configurar la vista con datos del departamento
        val departamentoImageView: ImageView = view.findViewById(R.id.departamentoImageView)
        val departamentoNombreTextView: TextView = view.findViewById(R.id.departamentoNombreTextView)

        // Cargar la imagen desde la URL
        Picasso.get()
            .load(departamento.url)
            .placeholder(R.drawable.imagen_dummie)
            .error(R.drawable.error)
            .into(departamentoImageView)

        departamentoNombreTextView.text = departamento.descripcion
        view.setOnClickListener {
            // Manejar el clic en el departamento aquí

        }

        return view
    }
}
