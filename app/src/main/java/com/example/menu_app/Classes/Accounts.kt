package com.example.menu_app.Classes

class Accounts {
    var id: Long = -1
    var nombre = ""
    var cuenta = ""
    var saldo = ""

    override fun toString(): String{
        return nombre
    }
}