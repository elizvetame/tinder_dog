package com.example.tinder

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // запись данных
        /*
        val fs = Firebase.firestore
       fs.collection( "animal")
           .document().set(Animals(
               type = "Кошка",
               breed = "Мейн-кун",
               age = "3 года",
               gender = "Женский",
               vaccinations = "Да",
               imageUrl = "https://lapkins.ru/upload/iblock/19b/19b9f805d035b84a331d0f22953557ed.jpg",
               generalInf = "Спокойная и умная кошка",
               contacts = "+7 (987) 654-32-10"

           ))
*/

        val button_vhod = findViewById<Button>(R.id.button3)
        button_vhod.setOnClickListener{
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        val button_avtor = findViewById<Button>(R.id.button2)
        button_avtor.setOnClickListener{
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }
    }
}