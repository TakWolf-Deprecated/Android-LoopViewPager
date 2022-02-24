package com.takwolf.android.demo.loopviewpager.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takwolf.android.demo.loopviewpager.model.Photo

class MainViewModel : ViewModel() {
    val photosData = MutableLiveData(Photo.getList())
}
