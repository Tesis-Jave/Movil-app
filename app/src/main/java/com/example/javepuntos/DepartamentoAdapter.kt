
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.javepuntos.ArticulosActivity
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
        val departamentoImageButton: ImageView = view.findViewById(R.id.departamentoImageButton)
        val departamentoNombreTextView: TextView = view.findViewById(R.id.departamentoNombreTextView)

        // Cargar la imagen desde la URL
        Picasso.get()
            .load(departamento.url)
            .placeholder(R.drawable.imagen_dummie)
            .error(R.drawable.error)
            .into(departamentoImageButton)

        departamentoNombreTextView.text = departamento.descripcion
        departamentoImageButton.setOnClickListener {
            println("clic en la imagen")
            val depto_id = departamento.idDpto

            // Crear un Intent para iniciar la ArticulosActivity
            val intent = Intent(context, ArticulosActivity::class.java)

            // Agregar el idDepartamento como extra al Intent
            intent.putExtra("idDepartamento", depto_id)

            // Iniciar la actividad
            context.startActivity(intent)
        }

        view.setOnClickListener {
            println("entra al onclick")
            // Manejar el clic en el departamento aquí

        }

        return view
    }
}
