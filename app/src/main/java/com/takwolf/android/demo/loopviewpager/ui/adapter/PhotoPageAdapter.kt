package com.takwolf.android.demo.loopviewpager.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.takwolf.android.demo.loopviewpager.R
import com.takwolf.android.demo.loopviewpager.databinding.ItemPageBinding
import com.takwolf.android.demo.loopviewpager.model.Photo

class PhotoPageAdapter(private val layoutInflater: LayoutInflater) : ListAdapter<Photo, PhotoPageAdapter.ViewHolder>(PhotoDiffItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPageBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemPageBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(photo: Photo) {
            binding.imgPhoto.load(photo.url) {
                placeholder(R.color.image_placeholder)
                error(R.color.image_placeholder)
            }
            binding.tvInfo.text = "$bindingAdapterPosition - $absoluteAdapterPosition"
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
