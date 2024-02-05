package com.example.azozdb1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class CreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        // الحصول على مراجع لعناصر واجهة المستخدم
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)

        // الحصول على مراجع لأزرار التحكم
        val create= findViewById<Button>(R.id.create)
        val cancel = findViewById<Button>(R.id.cancel)


        // إنشاء مستخدم جديد عند النقر على زر "Create"
        create.setOnClickListener {
            // التحقق من أن حقول اسم المستخدم وكلمة المرور ليستا فارغة
            if(username.text.toString().isEmpty() || password.text.toString().isEmpty()){
                // عرض رسالة تنبيه إذا كان أحد الحقول فارغًا
                Toast.makeText(this, "الحقول لا يمكن أن تكون فارغة", Toast.LENGTH_SHORT).show()
            }else{
                // إنشاء كائن لعمليات قاعدة البيانات
                val user = UserOperation(this)

                // محاولة إنشاء مستخدم جديد في قاعدة البيانات
                var t = user.createUser(username.text.toString().trim(), password.text.toString().trim())

                if(t) {
                    // إذا نجحت عملية الإنشاء، قم بالانتقال إلى شاشة تسجيل الدخول
                    var i = Intent(this, LoginActivity::class.java)
                    startActivity(i)
                }else{
                    // إذا فشلت عملية الإنشاء، قم بعرض رسالة خطأ
                    Toast.makeText(this, "فشل الإدخال!!", Toast.LENGTH_LONG).show()
                }
            }
        }

        // عند النقر على زر "Cancel"
        cancel.setOnClickListener{
            // الرجوع إلى شاشة تسجيل الدخول دون حفظ أي تغييرات
            var i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }
    }
}
