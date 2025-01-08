package com.example.tinder

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tinder.databinding.ItemFavoriteLayoutBinding
import java.net.URL
import java.util.concurrent.Executors

class FavouriteAdapter(val context: Context, var list: ArrayList<favorite>) :
    RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>() {

    inner class FavouriteViewHolder(val binding: ItemFavoriteLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        return FavouriteViewHolder(
            ItemFavoriteLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.binding.textView17.text = list[position].breed + ", " + list[position].age
        holder.binding.textView13.text = list[position].gender
        holder.binding.textView12.text =
            "Есть прививки: \n" + list[position].vaccinations
        holder.binding.textView14.text = list[position].generalInf

        holder.binding.textView15.text =
            "Контакты: " + list[position].contacts

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        var image: Bitmap? = null

        executor.execute {
            val imageURL = list[position].imageUrl
            try {
                val inStream = URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(inStream)
                handler.post {
                    holder.binding.userImage1.setImageBitmap(image)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
