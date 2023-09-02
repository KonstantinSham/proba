package com.example.recycler.photolist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.recycler.databinding.PhotoItemBinding
import com.example.recycler.models.Photo

class PhotoListAdapter(
    private val onClick: (Photo) -> Unit
) : ListAdapter<Photo, PhotoViewHolder>(DiffUtilCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            PhotoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            rover.text = "Rover: ${item?.rover?.name.toString()}"
            camera.text = "Camera: ${item?.camera?.name.toString()}"
            sol.text = "Sol: ${item?.sol.toString()}"
            date.text = "Date: ${item?.earth_date.toString()}"
            item?.let {
                val url = it.img_src.removePrefix("http://mars.jpl.nasa.gov")
                Glide
                    .with(imageView.context)
                    .load("https://mars.nasa.gov$url")
                    .into(imageView)
            }
        }
        holder.binding.root.setOnClickListener {
            onClick(item)
        }
    }
}

class DiffUtilCallBack : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean = oldItem == newItem
}