package com.takwolf.android.demo.loopviewpager.helper

import androidx.lifecycle.LifecycleOwner
import com.takwolf.android.demo.loopviewpager.adapter.PhotoPageAdapter
import com.takwolf.android.demo.loopviewpager.data.Photo
import com.takwolf.android.demo.loopviewpager.vm.ListViewModel
import java.util.*
import kotlin.collections.ArrayList

object PhotoViewHelper {
    fun listen(
        owner: LifecycleOwner,
        viewModel: ListViewModel<Photo>,
        adapter: PhotoPageAdapter,
    ) {
        viewModel.entitiesData.observe(owner) { photos ->
            adapter.submitList(ArrayList(photos))
        }
        adapter.onPhotosSwapListener = { oldPosition, newPosition ->
            viewModel.entitiesData.value?.let { photos ->
                Collections.swap(photos, oldPosition, newPosition)
                viewModel.entitiesData.value = photos
            }
        }
        adapter.onPhotoDeleteListener = { position ->
            viewModel.entitiesData.value?.let { photos ->
                photos.removeAt(position)
                viewModel.entitiesData.value = photos
            }
        }
    }
}
