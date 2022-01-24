package com.takwolf.android.demo.loopviewpager.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.MarginPageTransformer
import com.takwolf.android.demo.loopviewpager.adapter.PhotoPageAdapter
import com.takwolf.android.demo.loopviewpager.databinding.ActivityDemoBinding
import com.takwolf.android.demo.loopviewpager.vm.PhotoListViewModel

class DemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photoListViewModel: PhotoListViewModel by viewModels()

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.viewPager.viewPager2.offscreenPageLimit = 1
        val adapter = PhotoPageAdapter()
        photoListViewModel.entitiesData.observe(this) { photos ->
            adapter.submitList(ArrayList(photos))
        }
        binding.viewPager.viewPager2.adapter = adapter
    }
}
