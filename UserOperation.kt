package com.example.azozdb1

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper



// كلاس لإدارة عمليات قاعدة البيانات
class UserOperation(context: Context) : SQLiteOpenHelper(context, "myDb", null, 1) {


    // ثوابت لتحديد هيكل الجدول
    companion object {
        const val TABLE_NAME = "Users"
        const val ID_COL = "Id"
        const val USER_COL = "Username"
        const val PASS_COL = "Password"
    }

    // يُستدعى عند إنشاء قاعدة البيانات
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE $TABLE_NAME (" +
                "$ID_COL INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$USER_COL TEXT," +
                "$PASS_COL TEXT)"
        db?.execSQL(query)
    }

    // يُستدعى عند ترقية إصدار قاعدة البيانات
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val drop_table = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(drop_table)
        onCreate(db)
    }

    // إضافة مستخدم جديد
    fun createUser(username: String, password: String): Boolean {
        var db = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(USER_COL, username)
        contentValues.put(PASS_COL, password)
        val success = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return (Integer.parseInt("$success") != -1)
    }

    // تسجيل الدخول
    fun login(username: String, password: String): Cursor {
        val db = this.readableDatabase

        val cols = arrayOf(ID_COL, USER_COL, PASS_COL)
        val selections = "$USER_COL=? AND $PASS_COL=?"
        val args = arrayOf(username, password)

        val cursor = db.query(TABLE_NAME, cols, selections, args, null, null, null)
        return  cursor
    }

    // البحث عن مستخدم باسم المستخدم
    fun searchUser(username: String): Cursor {
        val db = this.readableDatabase

        val cols = arrayOf(ID_COL, USER_COL, PASS_COL)
        val selections = "$USER_COL=?"
        val args = arrayOf(username)

        val cursor = db.query(TABLE_NAME, cols, selections, args, null, null, null)
        return cursor
    }

    // تحديث معلومات مستخدم
    fun updateUser(id: Int, username: String?, password: String?): Int {
        var db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(USER_COL, username)
        contentValues.put(PASS_COL, password)
        return db.update(TABLE_NAME, contentValues, "$ID_COL = $id", null)
    }

    // حذف مستخدم
    fun deleteUser(id: Int){
        var db = this.writableDatabase
        db.delete(TABLE_NAME, "$ID_COL = $id", null);
    }

    // قراءة كل البيانات وتحويلها إلى قائمة من كائنات Users
    @SuppressLint("Range")
    fun readAllData(): List<Users> {
        val list = ArrayList<Users>()
        val db = this.writableDatabase
        val query = "Select * from $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        if(cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val user = Users()
                    user.Id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID_COL)))
                    user.Username = cursor.getString(cursor.getColumnIndex(USER_COL))
                    user.Password = cursor.getString(cursor.getColumnIndex(PASS_COL))
                    list.add(user)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return list
    }
}

// كلاس يُستخدم لتمثيل بيانات المستخدم
class Users{
    var Id: Int  = 0
    var Username: String = ""
    var Password: String = ""
}
