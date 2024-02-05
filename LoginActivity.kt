package com.example.azozdb1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // الحصول على مراجع لعناصر واجهة المستخدم
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)

        // الحصول على مراجع لأزرار التحكم
        val login = findViewById<Button>(R.id.login)
        val clear = findViewById<Button>(R.id.clear)
        val close = findViewById<Button>(R.id.close)
        val create = findViewById<Button>(R.id.create)

        // عند النقر على زر الدخول
        login.setOnClickListener {
            // التحقق من أن حقول اسم المستخدم وكلمة المرور ليستا فارغة
            if (username.text.toString().isEmpty() || password.text.toString().isEmpty()) {
                Toast.makeText(this, "الحقول لا يمكن أن تكون فارغة", Toast.LENGTH_SHORT).show()
            } else {
                val userOperation = UserOperation(this)

                // محاولة تسجيل الدخول باستخدام اسم المستخدم وكلمة المرور
                var cursor1 = userOperation.login(
                    username.text.toString().trim(),
                    password.text.toString().trim()
                )

                if (cursor1.moveToFirst()) {
                    // إذا كانت هناك بيانات صحيحة، قم بالانتقال إلى نشاط ShowActivity
                    var user1 = cursor1.getString(1)
                    var pass1 = cursor1.getString(2)

                    var i = Intent(this, ShowActivity::class.java)
                    i.putExtra("user1", user1)
                    i.putExtra("pass1", pass1)
                    startActivity(i)
                } else {
                    // إذا لم تكن هناك بيانات صحيحة، قم بعرض رسالة خطأ
                    Toast.makeText(this, "اسم المستخدم أو كلمة المرور خاطئة", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // عند النقر على زر إنشاء حساب جديد
        create.setOnClickListener {
            var i = Intent(this, CreateActivity::class.java)
            startActivity(i)
        }

        // عند النقر على زر مسح الحقول
        clear.setOnClickListener {
            username.text.clear()
            password.text.clear()
        }

        // عند النقر على زر إغلاق التطبيق
        close.setOnClickListener {
            System.exit(-1)
        }
    }
}
