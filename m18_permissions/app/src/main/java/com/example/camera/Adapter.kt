package com.example.camera

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.camera.databinding.PhotoItemBinding

class Adapter : RecyclerView.Adapter<PhotoViewHolder>() {
    private var data: List<Photo> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Photo>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(

            PhotoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
            item?.let {
                Glide
                    .with(imageView.context)
                    .load(Uri.parse(it.route))
                    .into(imageView)
            }
        }
    }
}


class PhotoViewHolder(
    val binding: PhotoItemBinding
) : RecyclerView.ViewHolder(binding.root)