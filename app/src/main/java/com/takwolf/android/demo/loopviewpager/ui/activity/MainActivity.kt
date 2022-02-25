package com.takwolf.android.demo.loopviewpager.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.takwolf.android.demo.loopviewpager.R
import com.takwolf.android.demo.loopviewpager.databinding.ActivityMainBinding
import com.takwolf.android.demo.loopviewpager.ui.adapter.PhotoPageAdapter
import com.takwolf.android.demo.loopviewpager.vm.MainViewModel

class MainActivity : AppCompatActivity() {
    private val viewMode: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        val adapter = PhotoPageAdapter(layoutInflater)
        binding.viewPager.adapter = adapter

        binding.rgOrientation.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_horizontal -> viewMode.orientationData.value = ViewPager2.ORIENTATION_HORIZONTAL
                R.id.rb_vertical -> viewMode.orientationData.value = ViewPager2.ORIENTATION_VERTICAL
            }
        }

        viewMode.photosData.observe(this) { photos ->
            adapter.submitList(photos?.toList())
        }

        viewMode.orientationData.observe(this) {
            it?.let { orientation ->
                binding.viewPager.orientation = orientation
            }
        }

        setContentView(binding.root)
    }
}
