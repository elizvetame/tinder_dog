package com.example.tinder.ui.dashboard

import android.content.ContentResolver
import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tinder.databinding.FragmentDashboardBinding


import android.util.Log;
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.tinder.Animals
import com.example.tinder.DatingAdapter
import com.example.tinder.R
import com.example.tinder.favorite
import com.example.tinder.ui.User

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import java.net.URL


import java.util.concurrent.Executors


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    private lateinit var manager : CardStackLayoutManager

    val animals = arrayListOf<Animals>()



    // Declaring and initializing the ImageView


    val fs = Firebase.firestore

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root


        getData()




        return root
    }

    private fun init() {
        manager = CardStackLayoutManager(requireContext(), object : CardStackListener {
            override fun onCardDragging(direction: Direction?, ratio: Float) {

            }

            override fun onCardSwiped(direction: Direction?) {
                // Проверяем, что карточка была пролистана вправо
                if (direction == Direction.Right) {
                    val currentPosition = manager.topPosition
                    val fs = Firebase.firestore
                    val user = Firebase.auth.currentUser
                    user?.let {
                        // Name, email address, and profile photo Url
                        val uid = it.uid

                        val favoriteDocument = favorite(
                            type = animals[currentPosition - 1].type,
                            breed = animals[currentPosition - 1].breed,
                            age = animals[currentPosition - 1].age,
                            gender = animals[currentPosition - 1].gender,
                            vaccinations = animals[currentPosition - 1].vaccinations,
                            imageUrl = animals[currentPosition - 1].imageUrl,
                            generalInf = animals[currentPosition - 1].generalInf,
                            contacts = animals[currentPosition - 1].contacts
                        )

                        val favoritesCollectionRef = fs.collection("favorite").document(uid).collection("favorites")

                        favoritesCollectionRef.add(favoriteDocument)
                            .addOnSuccessListener { documentReference ->
                                Log.d(TAG, "Favorite added with ID: ${documentReference.id}")
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding favorite", e)
                            }
                    }

                }

                if (manager.topPosition == animals.size) {
                    Toast.makeText(requireContext(), "this is last card", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCardRewound() {

            }

            override fun onCardCanceled() {

            }

            override fun onCardAppeared(view: View?, position: Int) {

            }

            override fun onCardDisappeared(view: View?, position: Int) {

            }

        })

        manager.setVisibleCount(3)
        manager.setTranslationInterval(0.6f)
        manager.setScaleInterval(0.8f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)


    }


    private fun imageToBase64(uri: Uri, contentResolver: ContentResolver): String {
        val inputStream = contentResolver.openInputStream(uri)
        val bytes = inputStream?.readBytes()
        return bytes?.let {
            android.util.Base64.encodeToString(it, android.util.Base64.DEFAULT)
        } ?: ""

    }

    private fun getData() {

        val user_с = Firebase.auth.currentUser

        user_с?.let {
            fs.collection("animal")
                .get()
                .addOnSuccessListener { result ->
                    for (documentSnapshot in result) {
                        animals.add(documentSnapshot.toObject(Animals::class.java))
                        Log.d(TAG, "${documentSnapshot.id} => ${documentSnapshot.data}")

                    }
                    animals.shuffle()


                    init()
                    binding.cardStackView.layoutManager = manager
                    binding.cardStackView.itemAnimator = DefaultItemAnimator()
                    binding.cardStackView.adapter = DatingAdapter(requireContext(), animals)
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }




        }
        // Проходим по каждому элементу списка и выводим его в лог
        animals.forEach { animal ->
            Log.d(
                "ANIMALS_LOG",
                "Тип: ${animal.type}, Порода: ${animal.breed}, Возраст: ${animal.age}, Пол: ${animal.gender}, Привит: ${animal.vaccinations}, Описание: ${animal.generalInf}, Контакт: ${animal.contacts}"
            )
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
