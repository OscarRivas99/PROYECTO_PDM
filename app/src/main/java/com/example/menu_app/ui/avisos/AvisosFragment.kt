package com.example.menu_app.ui.avisos

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.menu_app.R
import com.example.menu_app.databinding.FragmentAvisosBinding
import kotlinx.android.synthetic.main.fragment_avisos.*
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

    class AvisosFragments : Fragment(), AdapterView.OnItemSelectedListener {
    var categorias = arrayOf("Factura", "Prestamos", "Deposito", "Banco", "Estudio", "Tarjeta credito", "Tarjeta de cretido", "Alquiler de casa", "Alquiler de apartamento", "Abono a cuenta" )
    var spinner: Spinner? = null
    var textView_msg: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentAvisosBinding>(inflater, R.layout.fragment_avisos, container, false)
        val add = binding.btnAdd
        val aviso = binding.txtRecordatorio
        val calendario = binding.txt2
        val category = binding.msg

        calendario.setOnClickListener {

            ShowDatePicker()
            show_categori()

            add.setOnClickListener {

                //Toast.makeText(context, "Agregado aviso: ${aviso.text.toString()}", Toast.LENGTH_SHORT).show()
                //Toast.makeText(context, "categoria: ${this.msg.text.toString()}", Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "Agregar aviso:    " + aviso.text.toString() + "\nCategoria:  "+  category.text.toString()  ,  Toast.LENGTH_SHORT).show()
            }
        }




        return binding.root
    }


    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        textView_msg!!.text = " "+categorias[position]
    }
    private fun ShowDatePicker(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(requireActivity(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            // Display Selected date in textbox
            txt_2.setText("Fecha:  " + dayOfMonth + " / " + (monthOfYear+1) + " / " + year)

        }, year, month, day)

        dpd.show()
    }
    private fun show_categori(){
        textView_msg = this.msg
        spinner = this.spinner_sample
        spinner!!.setOnItemSelectedListener(this)
        // Create an ArrayAdapter using a simple spinner layout and languages array

        val aa = ArrayAdapter<String>(requireActivity(),android.R.layout.simple_spinner_item, categorias)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner!!.setAdapter(aa)

    }
}
