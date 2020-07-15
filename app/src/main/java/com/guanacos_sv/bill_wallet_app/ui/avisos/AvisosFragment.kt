package com.guanacos_sv.bill_wallet_app.ui.avisos

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Data
import com.guanacos_sv.bill_wallet_app.Classes.Avisos
import com.guanacos_sv.bill_wallet_app.Classes.Worknoti
import com.guanacos_sv.bill_wallet_app.R
import com.guanacos_sv.bill_wallet_app.database.DBHandler
import com.guanacos_sv.bill_wallet_app.databinding.FragmentAvisosBinding
import kotlinx.android.synthetic.main.dialog_dashboard_avisos.*
import kotlinx.android.synthetic.main.fragment_avisos.*
import java.text.SimpleDateFormat
import java.util.*



/**
 * A simple [Fragment] subclass.
 */
class AvisosFragments : Fragment(){

    lateinit var dbHandler: DBHandler


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
        Toast.makeText(context, "Cree un aviso en el boton +" , Toast.LENGTH_LONG).show()
        dbHandler = DBHandler(requireActivity())


        val rv_dashboard_avisos = binding.rvDashboardAvisos
        val btn_agregar = binding.btnAgregar

        rv_dashboard_avisos.layoutManager = LinearLayoutManager(requireActivity())

        btn_agregar.setOnClickListener {


            val dialog = AlertDialog.Builder(requireActivity())
            dialog.setTitle("Añadir nuevo aviso")
            val view = layoutInflater.inflate(R.layout.dialog_dashboard_avisos, null)

            val txt_recordatorio = view.findViewById<EditText>(R.id.txt_recordatorio)
            val btn_selectfecha = view.findViewById<TextView>(R.id.btn_fecha)

            dialog.setView(view)

            btn_selectfecha.setOnClickListener {

                anio = actual[Calendar.YEAR]
                mes = actual[Calendar.MONTH]
                dia = actual[Calendar.DAY_OF_MONTH]

                val datePickerDialog = DatePickerDialog(
                    requireActivity(),
                    DatePickerDialog.OnDateSetListener { view, y, m, d ->
                        calendar[Calendar.DAY_OF_MONTH] = d
                        calendar[Calendar.MONTH] = m
                        calendar[Calendar.YEAR] = y

                        val format = SimpleDateFormat("dd/MM/yyyy")
                        val strDate = format.format(calendar.time)
                        btn_selectfecha.setText(strDate)

                    },
                    anio,
                    mes,
                    dia
                )
                datePickerDialog.show()

            }



            //Boton añadir del dialogo
            dialog.setPositiveButton("Add") { _: DialogInterface, _: Int ->
                if (txt_recordatorio.text.isNotEmpty()){

                    val tag = generateKey()
                    //Convierte la hora en milisegundos y lo resta con la hora actual del telefono en milisegundos

                    val airtime: Long = calendar.timeInMillis - System.currentTimeMillis()

                    val random = (Math.random() * 50 + 1).toInt()
                    val aviso = txt_recordatorio.text.toString()


                    val data = enviarData("Notificación Bill Wallet App", aviso, random)
                    Worknoti.Guardarnoti(airtime, data, tag)
                    //}

                    val avisos = Avisos()
                    avisos.recordatorio = txt_recordatorio.text.toString()
                    avisos.fecha = btn_selectfecha.text.toString()


                    dbHandler.addAvisos(avisos)
                    refreshList()
                }
            }

            dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

            }
            dialog.show()


        }

        return binding.root
    }

    override fun onResume() {
        refreshList()
        super.onResume()
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

    private fun refreshList() {
        rv_dashboard_avisos.adapter = DashboardAdapter(this, dbHandler.getAvisos())
    }


    class DashboardAdapter(val fragment: AvisosFragments, val list: MutableList<Avisos>) :
        RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(fragment!!.context).inflate(
                    R.layout.avisos_dashboard,
                    p0,
                    false
                )
            )

        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, p1: Int) {

           holder.recordatorio.text = list[p1].recordatorio
            holder.fecha.text = list[p1].fecha

            holder.recordatorio.text = "Recordatorio: " + holder.recordatorio.text
            holder.fecha.text = holder.fecha.text



            holder.eliminar.setOnClickListener {
                val dialog = AlertDialog.Builder(fragment.requireContext())
                dialog.setTitle("Aviso")
                dialog.setMessage("¿Desea eliminar este recordatorio?")
                dialog.setPositiveButton("Continue") { _: DialogInterface, _: Int ->
                    fragment.dbHandler.deleteAvisos(list[p1].id)
                    fragment.refreshList()
                }
                dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

                }
                dialog.show()
            }
        }
        class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val recordatorio: TextView = v.findViewById(R.id.tv_recordatorio)
            val fecha: TextView = v.findViewById(R.id.tv_fecha)
            val eliminar: ImageView = v.findViewById(R.id.eliminar)


        }


    }



}



