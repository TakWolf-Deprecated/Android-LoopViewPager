package com.takwolf.android.demo.loopviewpager.vm

import com.takwolf.android.demo.loopviewpager.data.Photo

class PhotoListViewModel : ListViewModel<Photo>() {
    init {
        entitiesData.value = Photo.getList(10)
    }
}
