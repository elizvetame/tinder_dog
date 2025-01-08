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
import com.example.tinder.ui.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class MainActivity3 : AppCompatActivity() {

    lateinit var etEmail: EditText
    lateinit var etName: EditText
    private lateinit var etNumber: EditText
    private lateinit var etPass: EditText
    private lateinit var btnSignUp: Button
    lateinit var btnReg: TextView


    // создать объект аутентификации Firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // View Bindings
        etEmail = findViewById(R.id.editTextTextEmailAddress2)
        etPass = findViewById(R.id.editTextTextPassword2)
        btnSignUp = findViewById(R.id.button4)
        etName = findViewById(R.id.editTextText)
        etNumber = findViewById(R.id.editTextPhone)
        //tvRedirectLogin = findViewById(R.id.tvRedirectLogin)

        btnReg = findViewById(R.id.textView20)

        btnReg.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        // Initialising auth object
        auth = Firebase.auth

        btnSignUp.setOnClickListener {
            signUpUser()
        }

    }


    private fun signUpUser() {
        val email = etEmail.text.toString()
        val pass = etPass.text.toString()
        val name = etName.text.toString()
        val number = etNumber.text.toString()
        // check pass
        if (email.isBlank() || pass.isBlank() || name.isBlank() || number.isBlank()) {
            Toast.makeText(this, "Поля не заполнены!", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "Регистрация успешна!", Toast.LENGTH_SHORT).show()
                val user = Firebase.auth.currentUser
                user?.let {
                    // Name, email address, and profile photo Url
                    val uid = it.uid

                    val fs = Firebase.firestore
                    fs.collection("users")
                        .document(uid).set(
                            User(
                                email,
                                name,
                                number
                            )
                        )

                }
                finish()
            } else {
                Toast.makeText(this, "Зарегистрироваться не удалось!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}