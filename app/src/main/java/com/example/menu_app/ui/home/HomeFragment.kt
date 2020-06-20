package com.example.menu_app.ui.home

import android.app.DatePickerDialog
import android.app.PendingIntent.getActivity
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.menu_app.Classes.Movement

import com.example.menu_app.R
import com.example.menu_app.database.DBHandler
import com.example.menu_app.databinding.FragmentCategoriaBinding
import com.example.menu_app.databinding.FragmentHomeBinding
import com.example.menu_app.ui.slideshow.MetodosFragmentDirections
import kotlinx.android.synthetic.main.dialog_dashboard.*
import kotlinx.android.synthetic.main.fragment_avisos.*
import kotlinx.android.synthetic.main.fragment_home.*

import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_home.view.rv_dashboard
import kotlinx.android.synthetic.main.fragment_metodos.*

import java.util.*


@Suppress("ImplicitThis")
class HomeFragment : Fragment() {
    lateinit var dbHandler: DBHandler


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





        btn.setOnClickListener {


            val dialog = AlertDialog.Builder(requireActivity())
            dialog.setTitle("Add Movement")
            val view = layoutInflater.inflate(R.layout.dialog_dashboard, null)

            val categoria = view.findViewById<TextView>(R.id.tv_show_categorie)
            val datebutton = view.findViewById<Button>(R.id.show_datepicker_button)
            val btns = view.findViewById<Button>(R.id.send_mount_button)
            val showdate = view.findViewById<TextView>(R.id.fecha)
            val monto = view.findViewById<EditText>(R.id.et_mount)
            val descripcion = view.findViewById<EditText>(R.id.notes)
            val btnCategoria = view.findViewById<Button>(R.id.button_categoria)

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

                btns.setOnClickListener {
                Toast.makeText(context, "Mount: $${monto.text.toString() + " Fecha: ${showdate.text}" + " Description: ${descripcion.text}" + " Categoria: ${categoria.text}"}", Toast.LENGTH_SHORT).show()

            }
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

            dialog.setPositiveButton("Add") { _: DialogInterface, _: Int ->
                if ( monto.text.isNotEmpty()) {
                    val movement = Movement()
                    movement.categoria = categoria.text.toString()
                    movement.date = showdate.text.toString()
                    movement.monto = monto.text.toString()
                    movement.descripcion = descripcion.text.toString()

                    dbHandler.addMovement(movement)
                    refreshList()
                }
            }
            dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

            }
            dialog.show()
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
        val btns = view.findViewById<Button>(R.id.send_mount_button)
        val showdate = view.findViewById<TextView>(R.id.fecha)
        val monto = view.findViewById<EditText>(R.id.et_mount)
        val descripcion = view.findViewById<EditText>(R.id.notes)
        val btnCategoria = view.findViewById<Button>(R.id.button_categoria)

         categoria.setText(movement.categoria)
        showdate.setText(movement.date)
        monto.setText(movement.monto)
        descripcion.setText(movement.descripcion)

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

        btns.setOnClickListener {
            Toast.makeText(context, "Mount: $${monto.text.toString() + "Fecha: ${showdate .text}" + " Description: ${descripcion.text}" + " Categoria: ${categoria.text}"}", Toast.LENGTH_SHORT).show()

        }
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

            if (categoria.text.isNotEmpty() && showdate.text.isNotEmpty()) {
                movement.categoria = categoria.text.toString()
                movement.date = showdate.text.toString()
                movement.monto = monto.text.toString()
                movement.descripcion = descripcion.text.toString()

                dbHandler.updateMovement(movement)

                refreshList()
            }
        }
        dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

        }
        dialog.show()
    }




    private fun refreshList() {
        rv_dashboard.adapter = DashboardAdapter(this, dbHandler.getMovements())
    }



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
            holder.categoria.text = "Categoria: " + holder.categoria.text
            holder.monto.text = "Monto: " + holder.monto.text
            holder.fecha.text = "Fecha: " + holder.fecha.text
            holder.descripcion.text = "Descripcion: " + holder.descripcion.text




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
                            dialog.setTitle("Are you sure")
                            dialog.setMessage("Do you want to delete this movement ?")
                            dialog.setPositiveButton("Continue") { _: DialogInterface, _: Int ->
                                fragment.dbHandler.deleteMovement(list[p1].id)
                                fragment.refreshList()
                            }
                            dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

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
            val categoria: TextView = v.findViewById(R.id.tv_categoria)
            val monto: TextView = v.findViewById(R.id.tv_monto)
            val fecha: TextView = v.findViewById(R.id.tv_fecha)
            val descripcion: TextView = v.findViewById(R.id.tv_descripcion)
            val menu: ImageView = v.findViewById(R.id.iv_menu)
        }
    }
}
/////


