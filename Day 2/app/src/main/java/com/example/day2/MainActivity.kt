package com.example.day2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextAge: EditText
    private lateinit var buttonCheck: Button
    private lateinit var textViewResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextName = findViewById(R.id.editTextName)
        editTextAge = findViewById(R.id.editTextAge)
        buttonCheck = findViewById(R.id.buttonCheck)
        textViewResult = findViewById(R.id.textViewResult)


        buttonCheck.setOnClickListener {
            checkAgeAndDisplayInfo()
        }
    }

    private fun checkAgeAndDisplayInfo() {
        val name = editTextName.text.toString().trim()
        val ageString = editTextAge.text.toString().trim()


        if (name.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập họ và tên", Toast.LENGTH_SHORT).show()
            textViewResult.text = ""
            return
        }

        if (ageString.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tuổi", Toast.LENGTH_SHORT).show()
            textViewResult.text = ""
            return
        }

        val age = ageString.toIntOrNull()

        if (age == null || age < 0) {
            Toast.makeText(this, "Tuổi không hợp lệ", Toast.LENGTH_SHORT).show()
            textViewResult.text = ""
            return
        }

        val category = when {
            age < 2 -> "Em bé"
            age < 6 -> "Trẻ em"
            age <= 65 -> "Người lớn"
            else -> "Người già"
        }

        val resultText = "Xin chào ${name}!\nTuổi của bạn là: ${age}\nBạn là: ${category}"
        textViewResult.text = resultText
    }
}
