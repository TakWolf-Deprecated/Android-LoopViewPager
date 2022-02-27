package com.takwolf.android.demo.loopviewpager.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.takwolf.android.demo.loopviewpager.R
import com.takwolf.android.demo.loopviewpager.databinding.ActivityMainBinding
import com.takwolf.android.demo.loopviewpager.model.Photo
import com.takwolf.android.demo.loopviewpager.ui.adapter.OnPhotoDeleteListener
import com.takwolf.android.demo.loopviewpager.ui.adapter.OnPhotosSwapListener
import com.takwolf.android.demo.loopviewpager.ui.adapter.PhotoPageAdapter
import com.takwolf.android.demo.loopviewpager.vm.MainViewModel
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {
    private val viewMode: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        binding.refreshLayout.setOnRefreshListener {
            viewMode.photosData.value = Photo.getList()
            binding.refreshLayout.isRefreshing = false
        }

        val adapter = PhotoPageAdapter(layoutInflater).apply {
            onPhotosSwapListener = OnPhotosSwapListener(viewMode.photosData)
            onPhotoDeleteListener = OnPhotoDeleteListener(viewMode.photosData)
        }
        binding.viewPager.adapter = adapter

        lifecycleScope.launchWhenResumed {
            while (true) {
                delay(5000)
                if (viewMode.isAutoData.value == true) {
                    binding.viewPager.setNextItem()
                }
            }
        }

        binding.rgOrientation.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_horizontal -> viewMode.orientationData.value = ViewPager2.ORIENTATION_HORIZONTAL
                R.id.rb_vertical -> viewMode.orientationData.value = ViewPager2.ORIENTATION_VERTICAL
            }
        }

        binding.switchLooping.setOnCheckedChangeListener { _, isChecked ->
            viewMode.isLoopingData.value = isChecked
        }

        binding.switchAuto.setOnCheckedChangeListener { _, isChecked ->
            viewMode.isAutoData.value = isChecked
        }

        binding.btnPrev.setOnClickListener {
            binding.viewPager.setPrevItem()
        }

        binding.btnNext.setOnClickListener {
            binding.viewPager.setNextItem()
        }

        viewMode.photosData.observe(this) { photos ->
            adapter.submitList(photos?.toList())
        }

        viewMode.orientationData.observe(this) {
            it?.let { orientation ->
                when (orientation) {
                    ViewPager2.ORIENTATION_HORIZONTAL -> binding.rgOrientation.check(R.id.rb_horizontal)
                    ViewPager2.ORIENTATION_VERTICAL -> binding.rgOrientation.check(R.id.rb_vertical)
                }
                binding.viewPager.orientation = orientation
            }
        }

        viewMode.isLoopingData.observe(this) {
            it?.let { isLopping ->
                binding.switchLooping.isChecked = isLopping
                binding.viewPager.isLopping = isLopping
            }
        }

        viewMode.isAutoData.observe(this) {
            it?.let { isAuto ->
                binding.switchAuto.isChecked = isAuto
            }
        }

        setContentView(binding.root)
    }
}
