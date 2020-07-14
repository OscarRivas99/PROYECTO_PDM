package com.guanacos_sv.bill_wallet_app.ui.home

import android.app.DatePickerDialog
import android.content.DialogInterface

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guanacos_sv.bill_wallet_app.Classes.Movement
import com.guanacos_sv.bill_wallet_app.R
import com.guanacos_sv.bill_wallet_app.database.DBHandler
import com.guanacos_sv.bill_wallet_app.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


@Suppress("ImplicitThis")
class HomeFragment : Fragment() {
    var spinner: Spinner? = null
    lateinit var dbHandler: DBHandler
    var banderaListener = false
    lateinit var nombre_cuenta: String
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )
        dbHandler = DBHandler(requireActivity())

        val rv_dashboard = binding.rvDashboard
        val btn = binding.fabDashboard
        rv_dashboard.layoutManager = LinearLayoutManager(requireActivity())


        if (dbHandler.GetAllCount().isEmpty()){
            findNavController().navigate(R.id.action_nav_home_to_nav_cuentas2)
        }else {

            btn.setOnClickListener {

                val dialog = AlertDialog.Builder(requireActivity())
                dialog.setTitle("Añadir Movimiento")
                val view = layoutInflater.inflate(R.layout.dialog_dashboard, null)

                val categoria = view.findViewById<TextView>(R.id.tv_show_categorie)
                val datebutton = view.findViewById<Button>(R.id.show_datepicker_button)
                val showdate = view.findViewById<TextView>(R.id.fecha)
                val monto = view.findViewById<EditText>(R.id.et_mount)
                val descripcion = view.findViewById<EditText>(R.id.notes)
                val btnCategoria = view.findViewById<Button>(R.id.button_categoria)
                ////////////////////////////////////////////////////////////////////////
                val cuenta = view.findViewById<Spinner>(R.id.spn_count)

                dialog.setView(view)
                // spineer inicio

                val cuentas1 = dbHandler.GetAllCount()
                val cuentas2 = cuentas1.toTypedArray()
                val arrayAdapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, cuentas2)
                cuenta.adapter = arrayAdapter
                ////////////////////////////////////////////////////////////////////////
                cuenta.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        Toast.makeText(context!!, "onNothingSelected", Toast.LENGTH_SHORT).show()
                    }


                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        if (!banderaListener) {  //Se agrega esta bandera ya que el listener puede ser invocado cuando se crea el fragmento
                            banderaListener = true

                        }
                        // A partir de acá, podemos asumir que la llamada es genuina y generada por el usuario

                        nombre_cuenta = cuenta.getItemAtPosition(position).toString()

                    }
                }


                datebutton.setOnClickListener {

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
                            showdate.setText("Date:  " + dayOfMonth + " / " + (month + 1) + " / " + year)


                        },
                        year,
                        month,
                        day
                    )
                    datepicker.show()
                }



                //show_count()

                btnCategoria.setOnClickListener {
                    val builder = AlertDialog.Builder(requireActivity())
                    builder.setTitle("   Escoge una categoria")

                    val categories = arrayOf(
                        "Comida",
                        "Transporte",
                        "Entretenimiento",
                        "Salud",
                        "Mascota",
                        "Hogar",
                        "Otro"
                    )
                    builder.setItems(categories) { dialog, which ->
                        when (which) {
                            0 -> {
                                categoria.text = categories.get(0)
                            }
                            1 -> {
                                categoria.text = categories.get(1)
                            }
                            2 -> {
                                categoria.text = categories.get(2)
                            }
                            3 -> {
                                categoria.text = categories.get(3)
                            }
                            4 -> {
                                categoria.text = categories.get(4)
                            }
                            5 -> {
                                categoria.text = categories.get(5)
                            }
                            6 -> {
                                categoria.text = categories.get(6)
                            }
                            7 -> {
                                categoria.text = categories.get(7)

                            }
                        }
                    }


                    val dialog = builder.create()
                    dialog.show()
                }

                dialog.setPositiveButton("Add") { _: DialogInterface, _: Int ->
                    if (monto.text.isNotEmpty() && categoria.text.isNotEmpty()) {
                        val movement = Movement()
                        movement.categoria = categoria.text.toString()
                        movement.date = showdate.text.toString()
                        movement.monto = monto.text.toString()
                        movement.descripcion = descripcion.text.toString()
                        movement.nombre_cuenta = nombre_cuenta
                        dbHandler.addMovement(movement)

                        //ingresos
                        //egresos
                        val tipo_movimiento1 = view.findViewById<Switch>(R.id.tipo_movimiento)
                        val switchState: Boolean = tipo_movimiento1.isChecked()
                        if (switchState) {
                            if(dbHandler.GetSaldo(movement.nombre_cuenta).toInt() < movement.monto.toInt()) {
                                Toast.makeText(
                                    context!!,
                                    "Ha alcanzado su limite de saldo disponible",
                                    Toast.LENGTH_SHORT
                                ).show()

                                dbHandler.egreso(movement.monto, movement.nombre_cuenta)
                                Toast.makeText(
                                    context!!,
                                    "Estado de Cuenta: " + nombre_cuenta + "  Saldo Actual: $ " +  dbHandler.GetSaldo(nombre_cuenta).toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            }else {
                                dbHandler.egreso(movement.monto, movement.nombre_cuenta)
                                Toast.makeText(
                                    context!!,
                                    "Estado de Cuenta: " + nombre_cuenta + "  Saldo Actual: $ " +  dbHandler.GetSaldo(nombre_cuenta).toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                        } else {
                            dbHandler.ingreso(movement.monto, movement.nombre_cuenta)
                            Toast.makeText(
                                context!!,
                                "Estado de Cuenta: " + nombre_cuenta + "  Saldo Actual: $ " +  dbHandler.GetSaldo(nombre_cuenta).toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        //termina ingrso
                        refreshList()


                    }else{
                        Toast.makeText(
                            context!!,
                            "Complete todo los campos o agregue una categoria" ,
                            Toast.LENGTH_LONG).show()
                    }


                }
                dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

                }
                dialog.show()
            }
        }
        return binding.root

    }



/////////////////////////////////////////////////////////////////////////////////



    override fun onResume() {
        refreshList()
        super.onResume()
    }











    fun updateMovement(movement: Movement) {
        val dialog = AlertDialog.Builder(requireActivity())
        dialog.setTitle("Update Movement")
        val view = layoutInflater.inflate(R.layout.dialog_dashboard, null)

        val categoria = view.findViewById<TextView>(R.id.tv_show_categorie)
        val datebutton = view.findViewById<Button>(R.id.show_datepicker_button)

        val showdate = view.findViewById<TextView>(R.id.fecha)
        val monto = view.findViewById<EditText>(R.id.et_mount)
        val descripcion = view.findViewById<EditText>(R.id.notes)
        val btnCategoria = view.findViewById<Button>(R.id.button_categoria)

        categoria.setText(movement.categoria)
        showdate.setText(movement.date)
        monto.setText(movement.monto)
        descripcion.setText(movement.descripcion)
        val cuenta = view.findViewById<Spinner>(R.id.spn_count)

        val cuentas1 = dbHandler.GetAllCount()
        val cuentas2 = cuentas1.toTypedArray()
        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, cuentas2)
        cuenta.adapter = arrayAdapter
        ////////////////////////////////////////////////////////////////////////
        cuenta.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(context!!, "onNothingSelected", Toast.LENGTH_SHORT).show()
            }


            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (!banderaListener) {  //Se agrega esta bandera ya que el listener puede ser invocado cuando se crea el fragmento
                    banderaListener = true

                }
                // A partir de acá, podemos asumir que la llamada es genuina y generada por el usuario

                nombre_cuenta = cuenta.getItemAtPosition(position).toString()

            }
        }
        dialog.setView(view)

        datebutton.setOnClickListener {

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
                    showdate.setText("Date:  " + dayOfMonth + " / " + (month + 1) + " / " + year)


                },
                year,
                month,
                day
            )
            datepicker.show()
        }



        //spiner fin

        btnCategoria.setOnClickListener {
            val builder = AlertDialog.Builder(requireActivity())
            builder.setTitle("   Escoge una categoria")


            val categories = arrayOf("Comida", "Transporte", "Entretenimiento", "Salud", "Mascota", "Hogar", "Otro")
            builder.setItems(categories) { dialog, which ->
                when (which) {
                    0 -> { categoria.text = categories.get(0)
                    }
                    1 -> {categoria.text = categories.get(1)
                    }
                    2 -> { categoria.text = categories.get(2)
                    }
                    3 -> { categoria.text = categories.get(3)
                    }
                    4 -> { categoria.text = categories.get(4)
                    }
                    5 -> { categoria.text = categories.get(5)
                    }
                    6 -> { categoria.text = categories.get(6)
                    }
                    7 -> { categoria.text = categories.get(7)

                    }
                }
            }


            val dialog = builder.create()
            dialog.show()
        }


        dialog.setPositiveButton("Update") { _: DialogInterface, _: Int ->

            if (categoria.text.isNotEmpty() && showdate.text.isNotEmpty() && monto.text.isNotEmpty()) {
                movement.categoria = categoria.text.toString()
                movement.date = showdate.text.toString()
                movement.monto = monto.text.toString()
                movement.descripcion = descripcion.text.toString()
                movement.nombre_cuenta = nombre_cuenta

                dbHandler.updateMovement(movement)
                val tipo_movimiento1 = view.findViewById<Switch>(R.id.tipo_movimiento)
                val switchState: Boolean = tipo_movimiento1.isChecked()
                if (switchState) {
                    if(dbHandler.GetSaldo(movement.nombre_cuenta).toInt() < movement.monto.toInt()) {
                        Toast.makeText(
                            context!!,
                            "Ha alcanzado su limite de saldo disponible",
                            Toast.LENGTH_SHORT
                        ).show()

                        dbHandler.egreso(movement.monto, movement.nombre_cuenta)
                        Toast.makeText(
                            context!!,
                            "Estado de Cuenta: " + nombre_cuenta + "  Saldo Actual: $ " +  dbHandler.GetSaldo(nombre_cuenta).toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }else {
                        dbHandler.egreso(movement.monto, movement.nombre_cuenta)
                        Toast.makeText(
                            context!!,
                            "Estado de Cuenta: " + nombre_cuenta + "  Saldo Actual: $ " +  dbHandler.GetSaldo(nombre_cuenta).toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } else {
                    dbHandler.ingreso(movement.monto, movement.nombre_cuenta)
                    Toast.makeText(
                        context!!,
                        "Estado de Cuenta: " + nombre_cuenta + "  Saldo Actual: $ " +  dbHandler.GetSaldo(nombre_cuenta).toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
                //termina ingrso
                refreshList()


            }else{
                Toast.makeText(
                    context!!,
                    "Complete todo los campos o agregue una categoria" ,
                    Toast.LENGTH_LONG).show()
            }


        }
        dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

        }
        dialog.show()
    }

    private fun show_count() {


        /* var cuentas = dbHandler.GetAllCount()
         val spinner = requireActivity().spinner_accounts
         val cuentas2 = cuentas.toTypedArray()
         val arrayAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item, cuentas2)
         spinner.adapter = arrayAdapter
         spinner.onItemSelectedListener = object :    AdapterView.OnItemSelectedListener {
             override fun onNothingSelected(parent: AdapterView<*>?) {
                 TODO("Not yet implemented")

             }

             override fun onItemSelected(
                 parent: AdapterView<*>,
                 view: View,
                 position: Int,
                 id: Long
             ) {}
         }*/
    }


    private fun refreshList() {
        rv_dashboard.adapter = DashboardAdapter(this, dbHandler.getMovements())
    }



    //desde aqui
    class DashboardAdapter(val fragment: HomeFragment, val list: MutableList<Movement>) :
        RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(fragment!!.context).inflate(
                    R.layout.rv_child_dashboard,
                    p0,
                    false
                )
            )

        }

        override fun getItemCount(): Int {
            return list.size
        }


        override fun onBindViewHolder(holder: ViewHolder, p1: Int) {

            holder.categoria .text = list[p1].categoria
            holder.monto.text = list[p1].monto
            holder.fecha.text = list[p1].date
            holder.descripcion.text = list[p1].descripcion
            holder.nombre_cuenta.text = list[p1].nombre_cuenta

            holder.monto.text = "Monto (en $): " + holder.monto.text
            holder.fecha.text = holder.fecha.text
            holder.descripcion.text = "Descripcion: " + holder.descripcion.text
            holder.nombre_cuenta.text = "Cuenta: " + holder.nombre_cuenta.text


            when(holder.categoria.text.toString()){
                "Comida" -> holder.imagen.setImageResource(R.drawable.burger)
                "Transporte"-> holder.imagen.setImageResource(R.drawable.cars)
                "Entretenimiento" -> holder.imagen.setImageResource(R.drawable.cinema)
                "Salud" -> holder.imagen.setImageResource(R.drawable.help)
                "Mascota" -> holder.imagen.setImageResource(R.drawable.dog)
                "Hogar" -> holder.imagen.setImageResource(R.drawable.cabin)
                "Otro" -> holder.imagen.setImageResource(R.drawable.gym)


                else -> {

                }
            }
            holder.categoria.text = "Categoria: " + holder.categoria.text



            holder.menu.setOnClickListener {
                val popup = PopupMenu(fragment!!.context, holder.menu)
                popup.inflate(R.menu.dashboard_child)
                popup.setOnMenuItemClickListener {

                    when (it.itemId) {
                        R.id.menu_edit -> {
                            fragment.updateMovement(list[p1])
                        }


                        R.id.menu_delete -> {
                            val dialog = AlertDialog.Builder(fragment.requireContext())
                            dialog.setTitle("Esta seguro?")
                            dialog.setMessage("Desea eliminar este movimiento ?")
                            dialog.setPositiveButton("Continuar") { _: DialogInterface, _: Int ->
                                fragment.dbHandler.deleteMovement(list[p1].id)
                                fragment.refreshList()
                            }
                            dialog.setNegativeButton("Cancelar") { _: DialogInterface, _: Int ->

                            }
                            dialog.show()
                        }

                    }

                    true
                }
                popup.show()
            }
        }


        class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val imagen : ImageView = v.findViewById(R.id.img_categoria)
            val categoria: TextView = v.findViewById(R.id.tv_categoria)
            val monto: TextView = v.findViewById(R.id.tv_monto)
            val fecha: TextView = v.findViewById(R.id.tv_fecha)
            val descripcion: TextView = v.findViewById(R.id.tv_descripcion)
            val menu: ImageView = v.findViewById(R.id.iv_menu)
            val nombre_cuenta: TextView = v.findViewById(R.id.tv_nombre_cuenta)
        }
        private fun show_count(){
        }


    }


}