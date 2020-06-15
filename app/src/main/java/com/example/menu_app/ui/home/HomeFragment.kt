package com.example.menu_app.ui.home

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
import kotlinx.android.synthetic.main.fragment_home.*

import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_home.view.rv_dashboard



@Suppress("ImplicitThis")
class HomeFragment : Fragment() {
    lateinit var dbHandler: DBHandler

    //Ya funca
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val  root = inflater.inflate(R.layout.fragment_home, container, false)
        dbHandler = DBHandler(root.context)


        root.rv_dashboard.layoutManager = LinearLayoutManager(requireActivity())



        root.fab_dashboard.setOnClickListener {
            val dialog = AlertDialog.Builder(requireActivity())
            dialog.setTitle("Add Movement")
            val view = layoutInflater.inflate(R.layout.dialog_dashboard, null)
            val movementName = view.findViewById<EditText>(R.id.ev_todo)
            val movementdate = view.findViewById<EditText>(R.id.et_username)

            dialog.setView(view)

            dialog.setPositiveButton("Add") { _: DialogInterface, _: Int ->
                if (movementName.text.isNotEmpty() && movementdate.text.isNotEmpty()) {
                    val movement = Movement()
                    movement.name = movementName.text.toString()
                    movement.date = movementdate.text.toString()

                    dbHandler.addMovement(movement)
                    refreshList()
                }
            }
            dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

            }
            dialog.show()
        }
        return root


    }

/////////////////////////////////////////////////////////////////////////////////

    fun updateMovement(movement: Movement) {
        val dialog = AlertDialog.Builder(requireActivity())
        dialog.setTitle("Update Movement")
        val view = layoutInflater.inflate(R.layout.dialog_dashboard, null)
        val MovementName = view.findViewById<EditText>(R.id.ev_todo)
        //val MovementUser = view.findViewById<EditText>(R.id.et_username)
        val Movementdate = view.findViewById<EditText>(R.id.et_username)

        MovementName.setText(movement.name)
        //   MovementUser.setText(movement.username)
        Movementdate.setText(movement.date)

        dialog.setView(view)
        dialog.setPositiveButton("Update") { _: DialogInterface, _: Int ->

            if (MovementName.text.isNotEmpty() && Movementdate.text.isNotEmpty()) {
                movement.name = MovementName.text.toString()
                //   movement.username = MovementUser.text.toString()
                movement.date = Movementdate.text.toString()

                dbHandler.updateMovement(movement)

                refreshList()
            }
        }
        dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

        }
        dialog.show()
    }


    override fun onResume() {
        refreshList()
        super.onResume()
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

            holder.user.text = list[p1].date
            holder.toDoName.text = list[p1].name
            holder.toDoName.text = "Movimiento: " + holder.toDoName.text
            holder.user.text = "Usuario: " + holder.user.text




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
            val toDoName: TextView = v.findViewById(R.id.tv_todo_name)
            val user: TextView = v.findViewById(R.id.tv_user)
            val menu: ImageView = v.findViewById(R.id.iv_menu)
        }
    }
}
/////


