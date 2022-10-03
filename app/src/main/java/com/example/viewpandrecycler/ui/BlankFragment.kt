package com.example.viewpandrecycler.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.viewpandrecycler.ImagesVpAdapter
import com.example.viewpandrecycler.R
import com.example.viewpandrecycler.adapter.Hits
import com.example.viewpandrecycler.adapter.Response
import com.example.viewpandrecycler.databinding.ActivityMainBinding
import com.example.viewpandrecycler.databinding.FragmentBlankBinding
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import org.json.JSONTokener
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class BlankFragment : Fragment(R.layout.fragment_blank) {
    private lateinit var binding: FragmentBlankBinding
    private val list = mutableListOf<Hits>()
    private lateinit var coun : CountDownTimer
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBlankBinding.bind(view)

        setVp()

    }

    private fun setVp() {
        OkHttpClient.Builder().build().newCall(get()).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Snackbar.make(binding.root, "Server Error!", Snackbar.LENGTH_SHORT).show()
                Log.e("onFailure: ", e.message.toString())
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                val json = JSONTokener(response.body!!.string()).nextValue() as JSONObject
                try {
                    val gson = Gson().fromJson(json.toString(), Response::class.java)
                    list.addAll(gson.hits)
                    requireActivity().runOnUiThread {
                        binding.vpImages.adapter = ImagesVpAdapter(gson.hits)
                        binding.vpImages.orientation = ViewPager2.ORIENTATION_VERTICAL
                        changeItem()
                    }
                    setImage()
                } catch (e: Exception) {
                    Log.e("onResponse: ", e.message.toString())
                }
            }
        })
    }

    private fun changeItem() {
         coun = object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                if (binding.vpImages.currentItem == list.size - 1) {
                    binding.vpImages.currentItem = 0
                } else {
                    binding.vpImages.currentItem++
                }
                changeItem()
            }
        }.start()
    }

    private fun setImage() {
        val url = URL(list[0].userImageURL).openConnection() as HttpURLConnection
        val bitmap = BitmapFactory.decodeStream(url.inputStream)
        requireActivity().runOnUiThread {
            binding.imgB.setImageBitmap(bitmap)
        }

    }

    fun get(): Request {
        return Request.Builder().get()
            .url("https://pixabay.com/api/?key=30243195-5ec147e6ba62a277ce17ce78b&image_type=photo")
            .build()
    }

    override fun onDestroy() {
        coun.cancel()
        super.onDestroy()
    }


}