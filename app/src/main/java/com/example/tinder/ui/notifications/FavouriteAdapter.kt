package com.example.tinder.ui.notifications


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tinder.databinding.ItemUserLayoutBinding
import com.example.tinder.favorite
import java.net.URL
import java.util.concurrent.Executors


class FavouriteAdapter (val context : Context, var list : ArrayList<favorite>): RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>(){
    inner class FavouriteViewHolder(val binding : ItemUserLayoutBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        return FavouriteViewHolder(ItemUserLayoutBinding.inflate(
            LayoutInflater.from(context),
            parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val executor = Executors.newSingleThreadExecutor()

        // Once the executor parses the URL
        // and receives the image, handler will load it
        // in the ImageView
        val handler = Handler(Looper.getMainLooper())

        // Initializing the image
        var image: Bitmap? = null

        // Only for Background process (can take time depending on the Internet speed)
        executor.execute {

            // Image URL
            val imageURL = list[position].imageUrl

            // Tries to get the image and post it in the ImageView
            // with the help of Handler
            try {
                val `in` = URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)

                // Only for making changes in UI
                handler.post {
                    holder.binding.userImage.setImageBitmap(image)
                }
            }
            // If the URL doesnot point to
            // image or any other kind of failure
            catch (e: Exception) {
                e.printStackTrace()
            }


        }
    }

}