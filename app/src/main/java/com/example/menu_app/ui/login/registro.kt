package com.example.menu_app.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.menu_app.Classes.User
import com.example.menu_app.MainActivity
import com.example.menu_app.R
import com.example.menu_app.database.DBHandler
import kotlinx.android.synthetic.main.activity_login2.*
import kotlinx.android.synthetic.main.activity_registro.*

class registro : AppCompatActivity() {

    val b:Bundle = Bundle()
    lateinit var dbHandler: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        dbHandler = DBHandler(this)
        button_3.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            val user_add = findViewById<EditText>(R.id.txt_usuario_add)
            val pass_add = findViewById<EditText>(R.id.txt_pass_add)
            if (user_add.text.isNotEmpty() && pass_add.text.isNotEmpty()) {
                val user = User()
                user.username = user_add.text.toString()
                user.pass = pass_add.text.toString()

                dbHandler.addUser(user)
                startActivity(intent)

            } else {
                Toast.makeText(this, "Complete los campos  ", Toast.LENGTH_SHORT).show()


            }
        }

    }
}
