package com.example.viewpandrecycler

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.viewpandrecycler.adapter.Hits
import com.example.viewpandrecycler.databinding.ItemVpBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

class ImagesVpAdapter(private val list: List<Hits>) :
    RecyclerView.Adapter<ImagesVpAdapter.ImagesVpViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesVpViewHolder {
        val binding = ItemVpBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImagesVpViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImagesVpViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class ImagesVpViewHolder(private val binding: ItemVpBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Hits) {
            var bitmap: Bitmap? = null
            binding.txtLikes.text = item.likes.toString()
            val url = URL(item.userImageURL).openConnection() as HttpURLConnection
            CoroutineScope(Dispatchers.IO).launch {
                bitmap = BitmapFactory.decodeStream(url.inputStream)
                CoroutineScope(Dispatchers.Main).launch{
                    binding.img.setImageBitmap(bitmap)
                }
            }
        }
    }
}