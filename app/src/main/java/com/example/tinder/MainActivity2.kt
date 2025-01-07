package com.example.tinder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class MainActivity2 : AppCompatActivity() {

    //private lateinit var tvRedirectSignUp: TextView
    lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    lateinit var btnLogin: Button

    // Создание объекта FirebaseAuth
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // View Binding
        //tvRedirectSignUp = findViewById(R.id.tvRedirectSignUp)
        btnLogin = findViewById(R.id.button)
        etEmail = findViewById(R.id.editTextTextEmailAddress2)
        etPass = findViewById(R.id.editTextTextPassword2)

        // initialising Firebase auth object
        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            login()
        }


    }

    private fun login() {
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()

        if (email.isBlank() || pass.isBlank()) {
            Toast.makeText(this, "Поля не заполнены!", Toast.LENGTH_SHORT).show()
            return
        }
        // // вызов авторизации с Помощью Электронной Почты И Пароля(email, pass)
        // функция, использующая объект аутентификации Firebase
        // При успешном ответе Отобразится всплывающее окно
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Успешно вошел в Систему", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity4::class.java)
                startActivity(intent)

            } else
                Toast.makeText(this, "Не удалось выполнить вход в систему", Toast.LENGTH_SHORT).show()
        }
    }

}