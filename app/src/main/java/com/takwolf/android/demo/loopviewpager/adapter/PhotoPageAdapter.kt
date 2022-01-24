package com.takwolf.android.demo.loopviewpager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.takwolf.android.demo.loopviewpager.R
import com.takwolf.android.demo.loopviewpager.data.Photo
import com.takwolf.android.demo.loopviewpager.databinding.ItemPageBinding
import kotlin.math.abs
import kotlin.random.Random

class PhotoPageAdapter : ListAdapter<Photo, PhotoPageAdapter.ViewHolder>(PhotoDiffItemCallback) {
    var onPhotosSwapListener: ((oldPosition: Int, newPosition: Int) -> Unit)? = null
    var onPhotoDeleteListener: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemPageBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnItem.setOnClickListener {
                (bindingAdapter as PhotoPageAdapter?)?.let { adapter ->
                    adapter.onPhotosSwapListener?.let { listener ->
                        val oldPosition = bindingAdapterPosition
                        val newPosition = abs(Random.nextInt() % adapter.itemCount)
                        listener(oldPosition, newPosition)
                    }
                }
            }
            binding.btnItem.setOnLongClickListener {
                (bindingAdapter as PhotoPageAdapter?)?.let { adapter ->
                    adapter.onPhotoDeleteListener?.let { listener ->
                        val position = bindingAdapterPosition
                        listener(position)
                    }
                }
                true
            }
        }

        fun bind(photo: Photo) {
            binding.imgPhoto.load(photo.url) {
                placeholder(R.color.image_placeholder)
            }
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
