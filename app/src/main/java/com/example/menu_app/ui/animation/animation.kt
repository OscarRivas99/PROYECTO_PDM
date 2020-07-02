package com.example.menu_app.ui.animation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import com.example.menu_app.R
import com.example.menu_app.database.DBHandler
import com.example.menu_app.ui.login.login2
import com.example.menu_app.ui.login.registro
import kotlinx.android.synthetic.main.activity_animation.*

class animation : AppCompatActivity() {
    lateinit var dbHandler: DBHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        dbHandler=DBHandler(this)
        ic_logo.startAnimation(AnimationUtils.loadAnimation(this,
            R.anim.splash_in
        ))
        Handler().postDelayed({
            ic_logo.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.splash_out
            ))
            Handler().postDelayed({
                ic_logo.visibility = View.GONE
                if (dbHandler.val_user()){
                    startActivity(Intent(this, login2::class.java))
               }else {
                    startActivity(Intent(this, registro::class.java))
                }
            },500)
        },1500)
    }
}
