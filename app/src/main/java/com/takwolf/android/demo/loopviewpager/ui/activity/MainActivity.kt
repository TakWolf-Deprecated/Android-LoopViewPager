package com.takwolf.android.demo.loopviewpager.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.takwolf.android.demo.loopviewpager.databinding.ActivityMainBinding
import com.takwolf.android.demo.loopviewpager.ui.adapter.PhotoPageAdapter
import com.takwolf.android.demo.loopviewpager.vm.MainViewModel

class MainActivity : AppCompatActivity() {
    private val viewMode: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        val adapter = PhotoPageAdapter(layoutInflater)
        binding.viewPager.viewPager2.adapter = adapter

        viewMode.photosData.observe(this) { photos ->
            adapter.submitList(photos?.toList())
        }

        setContentView(binding.root)
    }
}
