package com.example.menu_app.ui.login

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast

import com.example.menu_app.MainActivity
import com.example.menu_app.R
import com.example.menu_app.database.DBHandler
import kotlinx.android.synthetic.main.activity_login2.*
import kotlinx.android.synthetic.main.activity_registro.*

class login2 : AppCompatActivity() {
    lateinit var dbHandler: DBHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)
        dbHandler=DBHandler(this)

        btn1.setOnClickListener {
            var bundle = intent.extras
            val user3 = bundle?.getString("user");
            val pass3 = bundle?.getString("pass")
            if (dbHandler.getUser(usuario.text.toString().trim { it <= ' ' }, pass.text.toString().trim { it<= ' ' })){
                val intent = Intent(this, MainActivity::class.java)

                startActivity(intent)






                } else {

                    Toast.makeText(this, "Usuario o contraseña incorrecta  ", Toast.LENGTH_SHORT)
                        .show()


                }


            }

    }


    override  fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            dialog.setTitle("Are you sure")
            dialog.setMessage("Do you want to close this App ?")
            dialog.setPositiveButton("Continue") { _: DialogInterface, _: Int ->

                intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_HOME)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

                finish()

                //   android.os.Process.killProcess(android.os.Process.myPid())

            }
            dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

            }
            dialog.show()

        }
        return false
    }



}
