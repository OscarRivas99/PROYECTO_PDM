package com.example.menu_app.ui.avisos

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.work.Data
import com.example.menu_app.Classes.Worknoti
import com.example.menu_app.R
import com.example.menu_app.databinding.FragmentAvisosBinding
import kotlinx.android.synthetic.main.fragment_avisos.*
import java.text.SimpleDateFormat
import java.util.*



/**
 * A simple [Fragment] subclass.
 */
class AvisosFragments : Fragment(), AdapterView.OnItemSelectedListener {
    var categorias = arrayOf(
        "Factura",
        "Prestamos",
        "Deposito",
        "Banco",
        "Estudio",
        "Tarjeta credito",
        "Tarjeta de cretido",
        "Alquiler de casa",
        "Alquiler de apartamento",
        "Abono a cuenta"
    )

    var spinner: Spinner? = null
    var textView_msg: TextView? = null
    private var calendar: Calendar = Calendar.getInstance()
    private var actual:  Calendar = Calendar.getInstance()
    private var minutos = 0
    private var hora = 0
    private var milisecond = 0
    private var dia = 0
    private var mes = 0
    private var anio = 0


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentAvisosBinding>(inflater, R.layout.fragment_avisos, container, false)
        val btn_add = binding.btnAdd
        // val recordatorio = binding.txtRecordatorio
        val btn_selectfecha = binding.btnFecha
        val btn_selecthora = binding.btnHora
        val category = binding.msg



        btn_selectfecha.setOnClickListener {
            ShowDatePicker()


        }

        btn_selecthora.setOnClickListener {
            ShowTimePicker()
            show_categori()

        }


        btn_add.setOnClickListener {

            //Toast.makeText(context, "Agregar aviso:    "+ "\nCategoria:  "+  category.text.toString()  ,  Toast.LENGTH_SHORT).show()
            // Toast.makeText(context, "Hora telefono:    "+ System.currentTimeMillis()+ "Hora app: "+ calendar.timeInMillis , Toast.LENGTH_LONG).show()

            val tag = generateKey()
            //Convierte la hora en milisegundos y lo resta con la hora actual del telefono en milisegundos
            val airtime: Long  =  ( (  System.currentTimeMillis() - (calendar.timeInMillis) ))
            // val diff: Long = date1.getTime() - date2.getTime()
            //alertTime.toLong()
            // if( System.currentTimeMillis() == calendar.timeInMillis ) {
            val random = (Math.random() * 50 + 1).toInt()
            val aviso = txt_recordatorio.text.toString()

            Toast.makeText(context, airtime.toString() , Toast.LENGTH_LONG).show()
            val data = enviarData("Notificación Bill Money App", aviso, random)
            Worknoti.Guardarnoti(airtime,data,tag)
            //}
        }

        return binding.root
    }

    private fun generateKey() : String {
        return UUID.randomUUID().toString()
    }

    private fun enviarData(titulo:String, detalle:String, id_noti:Int):Data {
        return Data.Builder()
            .putString("titulo",titulo)
            .putString("detalle",detalle)
            .putInt("id_noti",id_noti).build()
    }


    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        textView_msg!!.text = " "+categorias[position]
    }


    //Fecha, Hora y Categoria
    @RequiresApi(Build.VERSION_CODES.N)
    private fun ShowDatePicker(){

        anio = actual[Calendar.YEAR]
        mes = actual[Calendar.MONTH]
        dia = actual[Calendar.DAY_OF_MONTH]

        val datePickerDialog = DatePickerDialog(requireActivity(), DatePickerDialog.OnDateSetListener { view, y, m, d ->
            calendar[Calendar.DAY_OF_MONTH] = d
            calendar[Calendar.MONTH] = m
            calendar[Calendar.YEAR] = y

            val format = SimpleDateFormat("dd/MM/yyyy")
            val strDate = format.format(calendar.time)
            btn_fecha.setText(strDate)

        },anio,mes,dia)
        datePickerDialog.show()
    }

    private fun ShowTimePicker(){

        hora = actual[Calendar.HOUR_OF_DAY]
        minutos = actual[Calendar.MINUTE]

        var timePicker = TimePickerDialog(requireActivity(), TimePickerDialog.OnTimeSetListener { view, h, m ->

            btn_hora.setText("  Hora: " + String.format("%02d:%02d", h, m))

        },hora,minutos, true)
        timePicker.show()
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
