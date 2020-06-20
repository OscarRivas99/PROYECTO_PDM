package com.example.menu_app.ui.slideshow

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.menu_app.R
import com.example.menu_app.databinding.FragmentMetodosBinding
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.app_bar_main.view.*
import kotlinx.android.synthetic.main.fragment_avisos.*
import kotlinx.android.synthetic.main.fragment_metodos.*
import java.nio.file.Paths.get
import java.util.*

class MetodosFragment : Fragment() {
 var bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setup_title()
        val binding = DataBindingUtil.inflate<FragmentMetodosBinding>(
            inflater,
            R.layout.fragment_metodos,
            container,
            false
        )



        val sendMount = binding.sendMountButton
        val et_mount = binding.etMount
        val date_button = binding.showDatepickerButton
        //val tv_date = binding.fecha
        val notes = binding.notes

        val categoria = binding.tvShowCategorie
        val imagen = binding.ivCategorie

        categoria.text = arguments?.getString("categoria")



        when (categoria.text) {
            "Alimentos" ->  imagen.setImageResource(R.drawable.alimento)
            "Transporte" ->  imagen.setImageResource(R.drawable.transporte)
            "Hogar" ->  imagen.setImageResource(R.drawable.hogar)
            "Automovil" ->  imagen.setImageResource(R.drawable.automovil)
            "Compras" ->  imagen.setImageResource(R.drawable.compras)
            "Entretenimiento" ->  imagen.setImageResource(R.drawable.entretenimiento)
            "Mascota" ->  imagen.setImageResource(R.drawable.mascota)
            "Ropa" ->  imagen.setImageResource(R.drawable.ropa)
            "Salud" ->  imagen.setImageResource(R.drawable.salud)
            "Viajes" ->  imagen.setImageResource(R.drawable.viajes)
            "Gimnasio" ->  imagen.setImageResource(R.drawable.gimnacio)

            else -> {
            }
        }

        date_button.setOnClickListener {

            ShowDatePicker()

        }

        sendMount.setOnClickListener {
         //   Toast.makeText(context, "Mount: $${et_mount.text.toString() + " ${tv_date.text}" + " Description: ${notes.text}" + " Categoria: ${categoria.text}"}", Toast.LENGTH_SHORT).show()



          //  findNavController().navigate(MetodosFragmentDirections.actionNavMetodosToNavHome(categoria.text.toString(),et_mount.text.toString(),tv_date.text.toString(),notes.text.toString()))
        }

        return binding.root

    }

    private fun setup_title() {
        (activity as AppCompatActivity)
            .supportActionBar?.title = "Detalles de movimiento"
    }


    private fun ShowDatePicker() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val datepicker = DatePickerDialog(
            view!!.context,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                Toast.makeText(
                    context,
                    "Day:" + dayOfMonth + "\nMonth: " + (month + 1) + "\nYear: " + year,
                    Toast.LENGTH_SHORT
                ).show()
            //    fecha.setText("Date:  " + dayOfMonth + " / " + (month + 1) + " / " + year)

            },
            year,
            month,
            day
        )

        datepicker.show()
    }


}
