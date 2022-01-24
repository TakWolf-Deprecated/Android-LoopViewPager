package com.takwolf.android.demo.loopviewpager.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.takwolf.android.demo.loopviewpager.adapter.PhotoPageAdapter
import com.takwolf.android.demo.loopviewpager.databinding.ActivityDemoBinding
import com.takwolf.android.demo.loopviewpager.helper.PhotoViewHelper
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

        val adapter = PhotoPageAdapter()
        PhotoViewHelper.listen(this, photoListViewModel, adapter)
        binding.viewPager.viewPager.adapter = adapter
    }
}
