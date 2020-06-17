package com.example.menu_app.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        button2.setOnClickListener {
            val intent = Intent(this, registro::class.java)
            startActivity(intent)
        }

    }

}
