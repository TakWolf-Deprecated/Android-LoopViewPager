package com.takwolf.android.demo.loopviewpager.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewpager2.widget.ViewPager2
import com.takwolf.android.demo.loopviewpager.model.Photo

class MainViewModel : ViewModel() {
    val photosData = MutableLiveData(Photo.getList())
    val orientationData = MutableLiveData(ViewPager2.ORIENTATION_HORIZONTAL)
}
