package com.example.viewpandrecycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.viewpandrecycler.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vpFragments.adapter = VpAdapter(supportFragmentManager,lifecycle)
        binding.vpFragments.orientation = ViewPager2.ORIENTATION_HORIZONTAL


    }
}