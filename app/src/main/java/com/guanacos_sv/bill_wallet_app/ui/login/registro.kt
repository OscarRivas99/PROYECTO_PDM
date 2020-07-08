package com.guanacos_sv.bill_wallet_app.ui.login

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.EditText
import android.widget.Toast
import com.guanacos_sv.bill_wallet_app.Classes.User
import com.guanacos_sv.bill_wallet_app.MainActivity
import com.guanacos_sv.bill_wallet_app.R
import com.guanacos_sv.bill_wallet_app.database.DBHandler
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
