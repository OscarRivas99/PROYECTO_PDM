package com.example.menu_app.database

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.menu_app.Classes.Movement
import com.example.menu_app.Classes.User


class DBHandler(val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val createMoneyAppTable = "CREATE TABLE $TABLE_MONEYAPP ( " +
                "$COL_ID integer PRIMARY KEY AUTOINCREMENT," +
                "$COL_CREATED_AT datetime DEFAULT CURRENT_TIMESTAMP," +
                //"$COL_USER_NAME varchar," +
                //"$COL_PASS varchar"+
                "$COL_DATE varchar," +
                "$COL_NAME varchar);"

        val createUserMoneyApp = "CREATE TABLE $USER_TABLE ( " +
                "$COL_ID_USER integer PRIMARY KEY AUTOINCREMENT," +
                "$COL_USER_NAME varchar," +
                "$COL_PASS varchar);"+

        db.execSQL(createMoneyAppTable)
        db.execSQL(createUserMoneyApp)


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun addMovement(movement: Movement): Boolean {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_NAME, movement.name)
        // cv.put(COL_USER_NAME, movement.username)
        cv.put(COL_DATE, movement.date)
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
        cv.put(COL_NAME, movement.name)
        //  cv.put(COL_USER_NAME, movement.username)
        cv.put(COL_DATE, movement.date)
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
                movement.name = queryResult.getString(queryResult.getColumnIndex(COL_NAME))
                //   movement.username = queryResult.getString(queryResult.getColumnIndex(COL_USER_NAME))
                movement.date = queryResult.getString(queryResult.getColumnIndex(COL_DATE))

                result.add(movement)

            } while (queryResult.moveToNext())
        }
        queryResult.close()
        return result
    }


}