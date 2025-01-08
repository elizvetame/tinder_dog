package com.example.tinder.ui.home

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tinder.MainActivity
import com.example.tinder.MainActivity2
import com.example.tinder.MainActivity5
import com.example.tinder.R
import com.example.tinder.databinding.FragmentHomeBinding
import com.example.tinder.ui.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    val fs = Firebase.firestore

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        val homeViewModel1 =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val button_vhod = binding.button5
        button_vhod.setOnClickListener {
            val context = requireActivity()

            // Создаем интент для запуска новой активности
            val intent = Intent(context, MainActivity::class.java)

            // Запускаем активность
            context.startActivity(intent)
        }

        val button_izm = binding.button6
        button_izm.setOnClickListener {
            val context = requireActivity()

            // Создаем интент для запуска новой активности
            val intent = Intent(context, MainActivity5::class.java)

            // Запускаем активность
            context.startActivity(intent)
        }





        val list = emptyList<User>()


        val user_с = Firebase.auth.currentUser

        user_с?.let {

            val uids = it.uid
            val docRef = fs.collection("users").document(uids)
            docRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    val user = documentSnapshot.toObject(User::class.java)

                    val textView: TextView = binding.textView4
                    homeViewModel.text.observe(viewLifecycleOwner) {
                        if (user != null) {
                            textView.text = user.name
                        }
                    }

                    val textView1: TextView = binding.textView6
                    homeViewModel.text.observe(viewLifecycleOwner) {
                        if (user != null) {
                            textView1.text = user.number
                        }
                    }

                    if (documentSnapshot != null) {
                        Log.d(TAG, "DocumentSnapshot data: ${documentSnapshot.data}")
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }




            val email = it.email

            val textView1: TextView = binding.textView7
            homeViewModel1.text.observe(viewLifecycleOwner) {
                textView1.text = email
            }




        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}