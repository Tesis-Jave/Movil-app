package com.example.javepuntos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.javepuntos.databinding.ActivityMainScreenBinding

class MainScreen : Fragment() {

    private var _binding: ActivityMainScreenBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater:LayoutInflater, container: ViewGroup?,
        savedInstanceState:Bundle?
    ) :View? {

        _binding = ActivityMainScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonProductos.setOnClickListener {
        // se lanza el fragmento
        //  findNavController().navigate(R.id.aproductos)
            // Se lanza actividad nueva para los productos
            val intent = Intent(requireContext(), DepartamentosMain::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}