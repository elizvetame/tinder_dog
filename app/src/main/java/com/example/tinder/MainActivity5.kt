package com.example.tinder

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tinder.ui.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class MainActivity5 : AppCompatActivity() {

    lateinit var etName: EditText
    lateinit var etNum: EditText
    lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main5)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button_vhod = findViewById<Button>(R.id.button5)
        button_vhod.setOnClickListener {
            val intent = Intent(this, MainActivity4::class.java)
            startActivity(intent)
        }


        etName = findViewById(R.id.editTextText1)
        etNum = findViewById(R.id.editTextPhone)


        val button_izm = findViewById<Button>(R.id.button6)
        button_izm.setOnClickListener {
            signUser()
        }


    }


    private fun signUser() {



        val user = Firebase.auth.currentUser

        val name = etName.text.toString()
        val number = etNum.text.toString()


        // check pass
        if (name.isBlank() || number.isBlank()) {
            Toast.makeText(this, "Поля не заполнены!", Toast.LENGTH_SHORT).show()
            return
        }



        user?.let {
            // Name, email address, and profile photo Url
            val uid = it.uid


            val fs = Firebase.firestore
            fs.collection("users")
                .document(uid).set(
                    User(
                        "1",
                        name,
                        number
                    )
                )

            Log.d(TAG, "Данные изменены")
            val intent = Intent(this, MainActivity4::class.java)
            startActivity(intent)

        }

    }
}