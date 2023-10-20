package com.example.javepuntos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.navigation.fragment.findNavController
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

            findNavController().navigate(R.id.aproductos)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}