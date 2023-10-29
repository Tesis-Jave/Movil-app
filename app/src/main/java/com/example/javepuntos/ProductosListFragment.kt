import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.javepuntos.R
import com.example.javepuntos.model.Producto
import org.json.JSONArray
import org.json.JSONException

class ProductosListFragment : Fragment() {
    private val productList = ArrayList<Producto>()
    private lateinit var listView: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_productos_list2, container, false)

        listView = view.findViewById(R.id.productListView)

        // Llama a la función para cargar los datos de LoopBack
        loadProductData()

        return view
    }

    private fun loadProductData() {
        val url = "http://192.168.1.40:3000/preciosventas"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONArray> { response ->
                for (i in 0 until response.length()) {
                    try {
                        val product = response.getJSONObject(i)
                        val dto = product.getString("dto")
                        val idArticulo = product.getInt("id_articulo")
                        val idPreciosVenta = product.getInt("id_preciosventa")
                        val idTarifa = product.getInt("id_tarifa")
                        val precioBruto = product.getDouble("preciobruto")
                        val precioNeto = product.getDouble("precioneto")
                        productList.add(Producto(dto, idArticulo, idPreciosVenta, idTarifa, precioBruto, precioNeto))
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                // Crear un adaptador para el ListView
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, productList)

                // Asignar el adaptador al ListView
                listView.adapter = adapter
            },
            Response.ErrorListener { error ->
                Toast.makeText(requireContext(), "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        // Agregar la solicitud a la cola de solicitudes
        Volley.newRequestQueue(requireContext()).add(jsonArrayRequest)
    }
}