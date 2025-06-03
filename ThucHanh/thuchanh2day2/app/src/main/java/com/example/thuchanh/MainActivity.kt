package com.example.thuchanh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.graphics.Color

class MainActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var buttonCheck: Button
    private lateinit var textViewMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        editTextEmail = findViewById(R.id.editTextEmail)
        buttonCheck = findViewById(R.id.buttonCheck)
        textViewMessage = findViewById(R.id.textViewMessage)


        buttonCheck.setOnClickListener {
            validateEmail()
        }
    }


    private fun validateEmail() {
        val email = editTextEmail.text.toString().trim()


        if (email.isEmpty()) {
            textViewMessage.text = "Email không hợp lệ"
            textViewMessage.setTextColor(Color.RED)
            return
        }


        if (!email.contains("@")) {
            textViewMessage.text = "Email không đúng định dạng"
            textViewMessage.setTextColor(Color.RED)
            return
        }


        textViewMessage.text = "Bạn đã nhập email hợp lệ"
        textViewMessage.setTextColor(Color.parseColor("#4CAF50"))
    }
}