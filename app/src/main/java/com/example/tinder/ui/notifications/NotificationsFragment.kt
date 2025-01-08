package com.example.tinder.ui.notifications

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.tinder.Animals
import com.example.tinder.DatingAdapter
import com.example.tinder.FavouriteAdapter
import com.example.tinder.databinding.FragmentNotificationsBinding
import com.example.tinder.favorite
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    private lateinit var manager : CardStackLayoutManager

    val animals = arrayListOf<favorite>()


    val fs = Firebase.firestore


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        getData()


        return root
    }

    private fun init() {
        manager = CardStackLayoutManager(requireContext(), object : CardStackListener {
            override fun onCardDragging(direction: Direction?, ratio: Float) {

            }

            override fun onCardSwiped(direction: Direction?) {

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
        manager.setDirections(Direction.VERTICAL)


    }


    private fun getData() {

        val user_с = Firebase.auth.currentUser

        // Name, email address, and profile photo Url


        user_с?.let {

            val uid = it.uid


            val favoritesCollectionRef = fs.collection("favorite").document(uid).collection("favorites")

            favoritesCollectionRef
                .get()
                .addOnSuccessListener { result ->
                    for (documentSnapshot in result) {
                        animals.add(documentSnapshot.toObject(favorite::class.java))
                        Log.d(TAG, "${documentSnapshot.id} => ${documentSnapshot.data}")

                    }
                    animals.shuffle()


                    init()
                    binding.cardStackView1.layoutManager = manager
                    binding.cardStackView1.itemAnimator = DefaultItemAnimator()
                    binding.cardStackView1.adapter = FavouriteAdapter(requireContext(), animals)
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "Error getting documents: ", exception)
                }




        }


    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}