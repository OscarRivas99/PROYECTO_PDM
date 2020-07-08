package com.guanacos_sv.bill_wallet_app.ui.perfil

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.guanacos_sv.bill_wallet_app.Classes.User

import com.guanacos_sv.bill_wallet_app.R
import com.guanacos_sv.bill_wallet_app.database.DBHandler
import com.guanacos_sv.bill_wallet_app.databinding.FragmentPerfilBinding

/**
 * A simple [Fragment] subclass.
 */
class perfil : Fragment() {



    lateinit var dbHandler: DBHandler
    lateinit var credenciales : MutableList<User>

    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var binding = DataBindingUtil.inflate<FragmentPerfilBinding>(
            inflater, R.layout.fragment_perfil, container, false
        )
        dbHandler = DBHandler(requireActivity())


        //mostrar credenciales
        val usuario = binding.txtNombreUsuario
        val password = binding.txtContrasenia

        credenciales = dbHandler.getCredentials()

        usuario.text = credenciales[0].username.toString()
        password.text = credenciales[0].pass.toString()

        //actualizar credenciales

        val btn = binding.btnAdd


        btn.setOnClickListener {
            credenciales = dbHandler.getCredentials()
            updateCredentials(credenciales[0],usuario,password)

    }


        return binding.root
        }

    fun updateCredentials(user: User,usu:TextView,passw:TextView){
        val dialog = AlertDialog.Builder(requireActivity())
        dialog.setTitle("                 Actualizar Credenciales")
        val view = layoutInflater.inflate(R.layout.dialog_credenciales, null)
        val nuevoUser = view.findViewById<EditText>(R.id.edtxt_username)
        val nuevoPassw = view.findViewById<EditText>(R.id.edtxt_passw)
        dialog.setView(view)

        nuevoUser.setText(user.username.toString())

        nuevoPassw.setText(user.pass.toString())

        dialog.setPositiveButton("Update") { _: DialogInterface, _: Int ->

            if (nuevoUser.text.isNotEmpty() && nuevoPassw.text.isNotEmpty()) {


                user.username = nuevoUser.text.toString()
                user.pass = nuevoPassw.text.toString()
                dbHandler.updateCredentials(user)
                refreshList()


                usu.setText(credenciales[0].username.toString())
                passw.setText(credenciales[0].pass.toString())
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
         dbHandler.getCredentials()
    }


}





