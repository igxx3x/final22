package com.example.azozdb1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class UpdateActivity : AppCompatActivity() {

    // المتغيرات لتخزين اسم المستخدم وكلمة المرور
    lateinit var user1: String
    lateinit var pass1: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)


        // استرجاع عناصر واجهة المستخدم
        val uid = findViewById<TextView>(R.id.uid)
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val update = findViewById<Button>(R.id.update)
        val delete = findViewById<Button>(R.id.delete)
        val back = findViewById<Button>(R.id.back)


        // استرجاع بيانات المستخدم من النشاط السابق
        user1 = intent.getStringExtra("user1").toString()
        pass1 = intent.getStringExtra("pass1").toString()

        val userOperation = UserOperation(this)



        // استعراض بيانات المستخدم وعرضها في الواجهة
        var cursor1 = userOperation.login(user1, pass1)

        if(cursor1.moveToFirst()){
            uid.text = cursor1.getString(0).toString()
            username.setText(cursor1.getString(1))
            password.setText(cursor1.getString(2))
        }


        // زر تحديث المستخدم
        update.setOnClickListener {
            if(username.text.toString().isEmpty() || password.text.toString().isEmpty()){

                // فحص إذا كانت حقول اسم المستخدم وكلمة المرور غير فارغة
                Toast.makeText(this, "fields cannot be empty", Toast.LENGTH_SHORT).show()
            } else {

                // تحديث مستخدم وتحويل المستخدم إلى شاشة تسجيل الدخول
                val user = UserOperation(this)
                user.updateUser(cursor1.getString(0).toInt(), username.text.toString().trim(), password.text.toString().trim())
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
            }
        }

        // زر حذف المستخدم
        delete.setOnClickListener{
            // استرجاع معرف المستخدم وحذفه ثم توجيه المستخدم إلى شاشة تسجيل الدخول
            val uid1 = Integer.parseInt(uid.text.toString())
            userOperation.deleteUser(uid1)

            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }

        // زر الرجوع إلى شاشة تسجيل الدخول
        back.setOnClickListener{
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }
    }
}
