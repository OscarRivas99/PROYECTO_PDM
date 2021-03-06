package com.guanacos_sv.bill_wallet_app.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.guanacos_sv.bill_wallet_app.Classes.Accounts
import com.guanacos_sv.bill_wallet_app.Classes.Movement
import com.guanacos_sv.bill_wallet_app.Classes.User
import com.guanacos_sv.bill_wallet_app.Classes.Avisos

class DBHandler(val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val createMoneyAppTable = "CREATE TABLE $TABLE_MONEYAPP ( " +
                "$COL_ID integer PRIMARY KEY AUTOINCREMENT," +
                "$COL_CREATED_AT datetime DEFAULT CURRENT_TIMESTAMP," +
                "$COL_USER_ID varchar," +
                "$COL_CATEGORIA varchar," +
                "$COL_MONTO varchar," +
                "$COL_DESCRIPCION varchar," +
                "$COL_DATE varchar, "+
                "$COL_CUENTA_NAME varchar);"

        val createUserMoneyApp = "CREATE TABLE $USER_TABLE ( " +
                "$COL_ID_USER integer PRIMARY KEY AUTOINCREMENT," +
                "$COL_USER_NAME varchar," +
                "$COL_PASS varchar);"

        val createAccountsMoneyApp = "CREATE TABLE $ACCOUNTS_TABLE ( " +
                "$COL_ID_ACCOUNT integer PRIMARY KEY AUTOINCREMENT," +
                "$COL_NOMBRE varchar," +
                "$COL_CUENTAS varchar,"+
                "$COL_SALDO varchar);"


        val createAvisosMoneyApp = "CREATE TABLE $AVISOS_TABLE ( " +
                "$COL_ID_AVISOS integer PRIMARY KEY AUTOINCREMENT," +
                "$COL_RECORDATORIO varchar,"+
                "$COL_FECHA varchar);"


        db.execSQL(createMoneyAppTable)
        db.execSQL(createUserMoneyApp)
        db.execSQL(createAccountsMoneyApp)
        db.execSQL(createAvisosMoneyApp)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun addMovement(movement: Movement): Boolean {
        val db = writableDatabase
        val cv = ContentValues()
      //  cv.put(COL_ID_USER, movement.id_user)
        cv.put(COL_CATEGORIA, movement.categoria)
        cv.put(COL_DATE, movement.date)
        cv.put(COL_MONTO, movement.monto)
        cv.put(COL_DESCRIPCION, movement.descripcion)
        cv.put(COL_CUENTA_NAME,movement.nombre_cuenta)

        val result = db.insert(TABLE_MONEYAPP, null, cv)
        return result != (-1).toLong()
    }
    fun addUser(user: User):Boolean{
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_USER_NAME, user.username)
        cv.put(COL_PASS, user.pass)
        val result =db.insert(USER_TABLE, null, cv)
return result != (-1).toLong()
    }



    //Agregando Cuentas
    fun addAccounts(accounts: Accounts):Boolean{
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_NOMBRE, accounts.nombre)
        cv.put(COL_CUENTAS, accounts.cuenta)
        cv.put(COL_SALDO, accounts.saldo)
        val result =db.insert(ACCOUNTS_TABLE, null, cv)
        return result !=(-1).toLong()
    }


    //Agregando Avisos
    fun addAvisos(avisos: Avisos):Boolean{
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_RECORDATORIO, avisos.recordatorio)
        cv.put(COL_FECHA, avisos.fecha)
        val result =db.insert(AVISOS_TABLE, null, cv)
        return result !=(-1).toLong()
    }





fun val_user():Boolean{
    var count = 0
    val db = this.readableDatabase

    val cursor = db.rawQuery("SELECT count(*) FROM $USER_TABLE", null)

    try {
        if(cursor != null)
            if(cursor.count > 0){
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
    }finally {
        if (cursor != null && !cursor.isClosed) {
            cursor.close();
        }
    }
    if (count > 0)
        return true

    return false
}
    fun GetAllCount(): MutableList<Accounts> {
        val result: MutableList<Accounts> = ArrayList()
        val db = readableDatabase
        val queryResult = db.rawQuery("SELECT * from $ACCOUNTS_TABLE " , null)
        if (queryResult.moveToFirst()) {
            do {
                val counts = Accounts()
                counts.nombre = queryResult.getString(queryResult.getColumnIndex(COL_NOMBRE))

                result.add(counts)
            } while (queryResult.moveToNext())
        }
        queryResult.close()
        return result
    }



    fun getUser(user: String, pass: String ): Boolean {

        // array of columns
        val columns = arrayOf(COL_ID_USER)

        val db = this.readableDatabase

        // selection criteria
        val selection = "$COL_USER_NAME = ? AND $COL_PASS = ?"

        // selection arguments
        val selectionArgs = arrayOf(user, pass)

        val cursor = db.query(
            USER_TABLE, //Table to query
            columns, //columns to return
            selection, //columns for the WHERE clause
            selectionArgs, //The values for the WHERE clause
            null,  //group the rows
            null, //filter by row groups
            null) //The sort order

        val cursorCount = cursor.count
        cursor.close()
        db.close()

        if (cursorCount > 0)
            return true

        return false
    }

    fun updateMovement(movement: Movement) {
        val db = writableDatabase
        val cv = ContentValues()
       // cv.put(COL_ID_USER, movement.id_user)
        cv.put(COL_CATEGORIA, movement.categoria)
        cv.put(COL_DATE, movement.date)
        cv.put(COL_MONTO, movement.monto)
        cv.put(COL_DESCRIPCION, movement.descripcion)
        cv.put(COL_CUENTA_NAME, movement.nombre_cuenta)
        db.update(TABLE_MONEYAPP, cv, "$COL_ID=?", arrayOf(movement.id.toString()))
    }


    fun deleteMovement(MovementId: Long) {
        val db = writableDatabase
        db.delete(TABLE_MONEYAPP, "$COL_ID=?", arrayOf(MovementId.toString()))
    }

    //Ojo
    fun getMovements(): MutableList<Movement> {
        val result: MutableList<Movement> = ArrayList()
        val db = readableDatabase
        val queryResult = db.rawQuery("SELECT * from $TABLE_MONEYAPP", null)
        if (queryResult.moveToFirst()) {
            do {
                val movement = Movement()
                movement.id = queryResult.getLong(queryResult.getColumnIndex(COL_ID))
              //  movement.id_user = queryResult.getInt(queryResult.getColumnIndex(COL_USER_ID))
                movement.categoria = queryResult.getString(queryResult.getColumnIndex(COL_CATEGORIA))
                movement.date = queryResult.getString(queryResult.getColumnIndex(COL_DATE))
                movement.monto = queryResult.getString(queryResult.getColumnIndex(COL_MONTO))
                movement.descripcion = queryResult.getString(queryResult.getColumnIndex(COL_DESCRIPCION))
                movement.nombre_cuenta = queryResult.getString(queryResult.getColumnIndex(COL_CUENTA_NAME))

                result.add(movement)

            } while (queryResult.moveToNext())
        }
        queryResult.close()
        return result
    }

    fun getCredentials(): MutableList<User> {
        val result: MutableList<User> = ArrayList()
        val db = readableDatabase
        val queryResult = db.rawQuery("SELECT * from $USER_TABLE", null)
        if (queryResult.moveToFirst()) {
            do {
                val user = User()
                user.id = queryResult.getLong(queryResult.getColumnIndex(COL_ID_USER))
                //  movement.id_user = queryResult.getInt(queryResult.getColumnIndex(COL_USER_ID))
                user.username = queryResult.getString(queryResult.getColumnIndex(COL_USER_NAME))
                user.pass = queryResult.getString(queryResult.getColumnIndex(COL_PASS))


                result.add(user)

            } while (queryResult.moveToNext())
        }
        queryResult.close()
        return result
    }

    fun updateCredentials(user: User) {
        val db = writableDatabase
        val cv = ContentValues()
        // cv.put(COL_ID_USER, movement.id_user)
        cv.put(COL_USER_NAME, user.username)
        cv.put(COL_PASS, user.pass)

        db.update(USER_TABLE, cv, "$COL_ID_USER=?", arrayOf(user.id.toString()))
    }


    //Consultas de cuentas

    fun deleteAccounts(AccountId: Long){
        val db = writableDatabase
        db.delete(ACCOUNTS_TABLE, "$COL_ID=?", arrayOf(AccountId.toString()))
    }

    fun deleteAvisos(AvisosId: Long){
        val db = writableDatabase
        db.delete(AVISOS_TABLE, "$COL_ID_AVISOS=?", arrayOf(AvisosId.toString()))
    }


    fun getAccounts(): MutableList<Accounts> {
        val result: MutableList<Accounts> = ArrayList()
        val db = readableDatabase
        val queryResult = db.rawQuery("SELECT * from $ACCOUNTS_TABLE", null)
        if (queryResult.moveToFirst()) {
            do {
                val accounts = Accounts()
                accounts.id = queryResult.getLong(queryResult.getColumnIndex(COL_ID))
                accounts.nombre = queryResult.getString(queryResult.getColumnIndex(COL_NOMBRE))
                accounts.cuenta = queryResult.getString(queryResult.getColumnIndex(COL_CUENTAS))
                accounts.saldo = queryResult.getString(queryResult.getColumnIndex(COL_SALDO))


                result.add(accounts)

            } while (queryResult.moveToNext())
        }
        queryResult.close()
        return result
    }


    //Consultas Avisos

    fun getAvisos(): MutableList<Avisos> {
        val result: MutableList<Avisos> = ArrayList()
        val db = readableDatabase
        val queryResult = db.rawQuery("SELECT * from $AVISOS_TABLE", null)
        if (queryResult.moveToFirst()) {
            do {
                val avisos = Avisos()
                avisos.id = queryResult.getLong(queryResult.getColumnIndex(COL_ID_AVISOS))
                avisos.recordatorio = queryResult.getString(queryResult.getColumnIndex(COL_RECORDATORIO))
                avisos.fecha = queryResult.getString(queryResult.getColumnIndex(COL_FECHA))

                result.add(avisos)

            } while (queryResult.moveToNext())
        }
        queryResult.close()
        return result
    }




      fun ingreso(monto: String, nombreCuenta: String) {
       val db = writableDatabase
       val ubdate_monto = ("UPDATE $ACCOUNTS_TABLE SET  $COL_SALDO = $monto + $COL_SALDO  WHERE $COL_NOMBRE = '$nombreCuenta'")
       db.rawQuery(ubdate_monto,null)
          db.execSQL(ubdate_monto)
   }
    fun egreso(monto: String, nombreCuenta: String) {
        val db = writableDatabase
        val ubdate_monto = ("UPDATE $ACCOUNTS_TABLE SET  $COL_SALDO =  $COL_SALDO - $monto  WHERE $COL_NOMBRE = '$nombreCuenta'")
        db.rawQuery(ubdate_monto,null)
        db.execSQL(ubdate_monto)
    }
// get saldo unico
fun GetSaldo (nombreCuenta: String): String {
    var result: String = ""
    val db = readableDatabase
    val queryResult = db.rawQuery("SELECT $COL_SALDO from $ACCOUNTS_TABLE  WHERE $COL_NOMBRE = '$nombreCuenta'" , null)
    if (queryResult.moveToFirst()) {
        do {
            val counts = Accounts()
            counts.saldo = queryResult.getString(queryResult.getColumnIndex(COL_SALDO))

            result = counts.saldo.toString()
        } while (queryResult.moveToNext())
    }
    queryResult.close()
    return result
}


}