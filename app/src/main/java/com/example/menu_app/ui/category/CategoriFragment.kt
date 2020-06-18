package com.example.menu_app.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil


import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.example.menu_app.R
import com.example.menu_app.databinding.FragmentCategoriaBinding


class CategoriFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding = DataBindingUtil.inflate<FragmentCategoriaBinding>(inflater,R.layout.fragment_categoria,container,false)

       val btn_alimento = binding.btnAlimento
       val btn_transporte = binding.btnTransporte
       val btn_hogar = binding.btnHogar
       val btn_automovil = binding.btnAutomovil
       val btn_compras = binding.btnCompras
       val btn_entretenimiento = binding.btnEntretenimiento
       val btn_mascota = binding.btnMascota
       val btn_ropa = binding.btnRopa
       val btn_salud = binding.btnSalud
       val btn_viajes= binding.btnViajes
       val btn_gimnasio = binding.btnGimnacio


        btn_alimento.setOnClickListener {
            findNavController().navigate(CategoriFragmentDirections.actionNavCategoriaToNavMetodos("Alimentos"))

        }
        btn_transporte.setOnClickListener {
            findNavController().navigate(CategoriFragmentDirections.actionNavCategoriaToNavMetodos("Transporte"))
        }
        btn_hogar.setOnClickListener {
            findNavController().navigate(CategoriFragmentDirections.actionNavCategoriaToNavMetodos("Hogar"))
        }
        btn_automovil.setOnClickListener {
            findNavController().navigate(CategoriFragmentDirections.actionNavCategoriaToNavMetodos("Automovil"))
        }
        btn_compras.setOnClickListener {
            findNavController().navigate(CategoriFragmentDirections.actionNavCategoriaToNavMetodos("Compras"))
        }
        btn_entretenimiento.setOnClickListener {
            findNavController().navigate(CategoriFragmentDirections.actionNavCategoriaToNavMetodos("Entretenimiento"))
        }
        btn_mascota.setOnClickListener {
            findNavController().navigate(CategoriFragmentDirections.actionNavCategoriaToNavMetodos("Mascota"))
        }
        btn_ropa.setOnClickListener {
            findNavController().navigate(CategoriFragmentDirections.actionNavCategoriaToNavMetodos("Ropa"))
        }
        btn_salud.setOnClickListener {
            findNavController().navigate(CategoriFragmentDirections.actionNavCategoriaToNavMetodos("Salud"))
        }
        btn_viajes.setOnClickListener {
            findNavController().navigate(CategoriFragmentDirections.actionNavCategoriaToNavMetodos("Viajes"))
        }
        btn_gimnasio.setOnClickListener {
            findNavController().navigate(CategoriFragmentDirections.actionNavCategoriaToNavMetodos("Gimnasio"))
        }






        return binding.root
    }
}

