package com.guanacos_sv.bill_wallet_app.ui.accounts

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guanacos_sv.bill_wallet_app.Classes.Accounts
import com.guanacos_sv.bill_wallet_app.R
import com.guanacos_sv.bill_wallet_app.database.DBHandler
import com.guanacos_sv.bill_wallet_app.databinding.FragmentAccountsBinding

import kotlinx.android.synthetic.main.fragment_accounts.*


/**
 * A simple [Fragment] subclass.
 */
class AccountsFragment : Fragment() {

    lateinit var dbHandler: DBHandler
    var banderaListener = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentAccountsBinding>(
            inflater, R.layout.fragment_accounts, container,
            false
        )
        Toast.makeText(context, "Cree una cuenta en el boton +" , Toast.LENGTH_LONG).show()
        dbHandler = DBHandler(requireActivity())

        val rv_dashboard_cuenta = binding.rvDashboardCuenta
        val btn = binding.btnAgregar

        rv_dashboard_cuenta.layoutManager = LinearLayoutManager(requireActivity())

        btn.setOnClickListener {

            val dialog = AlertDialog.Builder(requireActivity())
            dialog.setTitle("Añadir nueva cuenta")
            val view = layoutInflater.inflate(R.layout.dialog_dashboard_accounts, null)

            val nombre = view.findViewById<EditText>(R.id.editText_nombre)
            val cuenta = view.findViewById<Spinner>(R.id.spinner_accounts)
            val saldo = view.findViewById<EditText>(R.id.editText_saldo)
            lateinit var valor_cuenta: String

            dialog.setView(view)

            // Spinner
// Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter.createFromResource(
                context!!,
                R.array.cuentas,
                android.R.layout.simple_spinner_item
            )
                .also { adapter ->
                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    // Apply the adapter to the spinner
                    cuenta.adapter = adapter


                }


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

                    valor_cuenta = cuenta.getItemAtPosition(position).toString()

                }
            }

            dialog.setPositiveButton("Add") { _: DialogInterface, _: Int ->
                if (saldo.text.isNotEmpty()) {

                    val accounts = Accounts()
                    accounts.nombre = nombre.text.toString()
                    accounts.cuenta = valor_cuenta
                    accounts.saldo = saldo.text.toString()

                    if(accounts.saldo.toDouble() < 0 ){
                        Toast.makeText(context, "No se permiten saldos negativos" , Toast.LENGTH_LONG).show()
                    }else {

                        dbHandler.addAccounts(accounts)
                        refreshList()
                    }
                }
            }
            dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

            }
            dialog.show()
        }
        return binding.root
    }

    //Hasta aqui


////////////////////////////////////////////////////////////////////////////////

    override fun onResume() {
        refreshList()
        super.onResume()
    }


    private fun refreshList() {
        rv_dashboard_cuenta.adapter = DashboardAdapter2(this, dbHandler.getAccounts())
    }


    class DashboardAdapter2(val fragment: AccountsFragment, val list: MutableList<Accounts>) :
        RecyclerView.Adapter<DashboardAdapter2.ViewHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(fragment!!.context).inflate(
                    R.layout.accounts_dashboard,
                    p0,
                    false
                )
            )

        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, p1: Int) {

            holder.nombre.text = list[p1].nombre
            holder.cuenta.text = list[p1].cuenta
            holder.saldo.text = list[p1].saldo
            holder.nombre.text = "Nombre: " + holder.nombre.text

            holder.saldo.text = "Saldo actual (en $) : " + holder.saldo.text


            when(holder.cuenta.text.toString()){
                "Efectivo" -> holder.img.setImageResource(R.drawable.moneyflat)
                "Débito"-> holder.img.setImageResource(R.drawable.shopper)
                "Crédito"-> holder.img.setImageResource(R.drawable.shopper)

                else -> {

                }
            }
            holder.cuenta.text = "Cuenta: " + holder.cuenta.text


            holder.eliminar.setOnClickListener {
                val dialog = AlertDialog.Builder(fragment.requireContext())
                dialog.setTitle("Aviso")
                dialog.setMessage("¿Desea eliminar esta cuenta?")
                dialog.setPositiveButton("Continue") { _: DialogInterface, _: Int ->
                    fragment.dbHandler.deleteAccounts(list[p1].id)
                    fragment.refreshList()
                }
                dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

                }
                dialog.show()
            }
        }
            class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
                val img : ImageView = v.findViewById(R.id.img_cuenta)
                val nombre: TextView = v.findViewById(R.id.tv_nombre)
                val cuenta: TextView = v.findViewById(R.id.tv_cuenta)
                val saldo: TextView = v.findViewById(R.id.tv_saldo)
                val eliminar: ImageView = v.findViewById(R.id.iv_eliminar)


            }


    }
}
    /**
    class SpinnerFragment : Fragment(), AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            // Another interface callback
        }
    }
**/

