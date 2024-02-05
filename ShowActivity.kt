package com.example.azozdb1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class ShowActivity : AppCompatActivity() {
    // تعريف متغيرات لعناصر واجهة المستخدم وبيانات المستخدم
    lateinit var show: TextView // TextView لعرض بيانات المستخدم
    lateinit var user1: String // اسم المستخدم
    lateinit var pass1: String // كلمة المرور

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show) // تحديد تخطيط الواجهة الخاص بالنشاط


        // تهيئة زر الخروج والبحث والتحديث
        val logout = findViewById<Button>(R.id.logout)
        val search = findViewById<Button>(R.id.search)
        val update = findViewById<Button>(R.id.update)

        // تهيئة TextView و EditText لعرض وإدخال بيانات المستخدم
        show = findViewById<TextView>(R.id.show)
        val uid = findViewById<TextView>(R.id.uid)
        val name = findViewById<TextView>(R.id.name)
        val pass = findViewById<TextView>(R.id.pass)
        val username = findViewById<EditText>(R.id.username)

        // استرجاع بيانات المستخدم من Intent
        user1 = intent.getStringExtra("user1").toString()
        pass1 = intent.getStringExtra("pass1").toString()

        // تهيئة عمليات قاعدة البيانات
        val userOperation = UserOperation(this)

        // تسجيل الدخول وعرض معلومات المستخدم إذا نجحت العملية
        var cursor1 = userOperation.login(user1, pass1)
        if (cursor1.moveToFirst()) {
            uid.setText(cursor1.getString(0)).toString()
            name.setText(cursor1.getString(1))
            pass.setText(cursor1.getString(2))
        }

        // قراءة وعرض كل البيانات من قاعدة البيانات
        val data = userOperation.readAllData()
        showAllData(data)

        // البحث عن مستخدم بواسطة اسم المستخدم وعرض معلوماته إذا تم العثور عليه
        search.setOnClickListener {
            val cursor = userOperation.searchUser(username.text.toString().trim())
            if (cursor.moveToFirst()) {
                uid.setText(cursor.getString(0)).toString()
                name.setText(cursor.getString(1))
                pass.setText(cursor.getString(2))
            } else {
                Toast.makeText(this, "لا توجد بيانات", Toast.LENGTH_LONG).show()
            }
        }

        // بدء ال UpdateActivity مع بيانات المستخدم الحالية لتحديث المعلومات
        update.setOnClickListener {
            var i = Intent(this, UpdateActivity::class.java)
            i.putExtra("user1", user1)
            i.putExtra("pass1", pass1)
            startActivity(i)
        }

        // تسجيل الخروج عن طريق بدء LoginActivity
        logout.setOnClickListener {
            var i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }
    }

    // عرض جميع بيانات المستخدمين في TextView
    fun showAllData(data: List<Users>) {
        var s: String = ""
        for (i in 0 until data.size) {
            s += data[i].Id.toString() + "    " + data[i].Username + "    " + data[i].Password + "\n"
        }
        show.text = s
    }
}
