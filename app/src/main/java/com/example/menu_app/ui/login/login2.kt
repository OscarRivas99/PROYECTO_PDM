package com.example.menu_app.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.menu_app.DashboardActivity
import com.example.menu_app.MainActivity
import com.example.menu_app.R
import kotlinx.android.synthetic.main.activity_login2.*
import kotlinx.android.synthetic.main.activity_registro.*

class login2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        btn1.setOnClickListener {
            var bundle = intent.extras
            val user3 = bundle?.getString("user");
            val pass3=bundle?.getString("pass")
            val user_name = usuario.text.trim().toString();
            val password = pass.text.trim().toString();
            usuario.setText(user3)
            pass.setText(pass3)
            if ((user_name.equals("root", true) && (password.equals("root", true)))||((user_name.equals(user3,true))&&(password.equals(pass3,true)))) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {

                Toast.makeText(this,"Usuario o contraseña incorrecta  ", Toast.LENGTH_SHORT).show()


            }



        }
        button2.setOnClickListener {
            val intent = Intent(this, registro::class.java)
            startActivity(intent)
        }

    }}
