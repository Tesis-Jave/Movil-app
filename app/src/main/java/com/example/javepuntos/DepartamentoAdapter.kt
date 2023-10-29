import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.javepuntos.model.Departamento
import com.example.javepuntos.R

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

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val departamento = getItem(position)
        val view = LayoutInflater.from(context).inflate(R.layout.departamento_item, parent, false)
        println(departamento)
        // Configurar la vista con datos del departamento
        val departamentoImageView: ImageView = view.findViewById(R.id.departamentoImageView)
        val departamentoNombreTextView: TextView = view.findViewById(R.id.departamentoNombreTextView)
        // Cargar la imagen desde la URL utilizando Glide o Picasso

        Handler(Looper.getMainLooper()).post {
            Glide.with(context)
                .load(departamento.url)
                .apply(RequestOptions()
                    .placeholder(R.drawable.imagen_dummie)
                    .error(R.drawable.error))
                .into(departamentoImageView)
        }


        departamentoNombreTextView.text = departamento.descripcion
        println(departamentoNombreTextView.text)
        view.setOnClickListener {
            // Manejar el clic en el departamento aquí
            // Por ejemplo, abrir un nuevo fragmento con los productos del departamento
        }

        return view
    }
}
