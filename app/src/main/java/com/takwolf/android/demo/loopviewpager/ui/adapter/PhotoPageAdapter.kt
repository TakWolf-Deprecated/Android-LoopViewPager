package com.takwolf.android.demo.loopviewpager.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.takwolf.android.demo.loopviewpager.R
import com.takwolf.android.demo.loopviewpager.databinding.ItemPageBinding
import com.takwolf.android.demo.loopviewpager.model.Photo
import java.util.*
import kotlin.math.abs
import kotlin.random.Random

class PhotoPageAdapter(private val layoutInflater: LayoutInflater) : ListAdapter<Photo, PhotoPageAdapter.ViewHolder>(PhotoDiffItemCallback) {
    var onPhotosSwapListener: OnPhotosSwapListener? = null
    var onPhotoDeleteListener: OnPhotoDeleteListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPageBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    class ViewHolder(private val binding: ItemPageBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnItem.setOnClickListener {
                (bindingAdapter as? PhotoPageAdapter)?.let { adapter ->
                    adapter.onPhotosSwapListener?.let { listener ->
                        val oldPosition = bindingAdapterPosition
                        val newPosition = abs(Random.nextInt() % adapter.itemCount)
                        listener.onPhotosSwap(oldPosition, newPosition)
                    }
                }
            }

            binding.btnItem.setOnLongClickListener {
                (bindingAdapter as? PhotoPageAdapter)?.let { adapter ->
                    adapter.onPhotoDeleteListener?.let { listener ->
                        val position = bindingAdapterPosition
                        listener.onPhotoDelete(position)
                    }
                }
                true
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(photo: Photo, position: Int) {
            binding.imgPhoto.load(photo.url) {
                placeholder(R.color.image_placeholder)
                error(R.color.image_placeholder)
            }
            binding.tvInfo.text = "$position - $bindingAdapterPosition - $absoluteAdapterPosition"
        }
    }
}

private object PhotoDiffItemCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }
}

class OnPhotosSwapListener(private val photosData: MutableLiveData<MutableList<Photo>>) {
    fun onPhotosSwap(oldPosition: Int, newPosition: Int) {
        photosData.value?.let { photos ->
            Collections.swap(photos, oldPosition, newPosition)
            photosData.value = photos
        }
    }
}

class OnPhotoDeleteListener(private val photosData: MutableLiveData<MutableList<Photo>>) {
    fun onPhotoDelete(position: Int) {
        photosData.value?.toMutableList()?.let { photos ->
            photos.removeAt(position)
            photosData.value = photos
        }
    }
}
