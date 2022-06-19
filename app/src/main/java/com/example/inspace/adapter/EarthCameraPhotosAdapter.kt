package com.example.inspace.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.inspace.databinding.PhotoItemBinding
import com.example.inspace.properties.EarthCameraPhotoProperty

class EarthCameraPhotosAdapter(private val onClickListener: EarthCameraPhotosAdapter.OnClickListener) :
    ListAdapter<EarthCameraPhotoProperty, EarthCameraPhotosAdapter.EarthCameraPhotosViewHolder>(DiffCallback) {
    class EarthCameraPhotosViewHolder(private var binding: PhotoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(earthCameraPhotoProperty: EarthCameraPhotoProperty) {
            binding.photos = earthCameraPhotoProperty
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<EarthCameraPhotoProperty>() {
        override fun areItemsTheSame(oldItem: EarthCameraPhotoProperty, newItem: EarthCameraPhotoProperty): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: EarthCameraPhotoProperty, newItem: EarthCameraPhotoProperty): Boolean {
            return oldItem.identifier == newItem.identifier
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarthCameraPhotosAdapter.EarthCameraPhotosViewHolder {
        return EarthCameraPhotosViewHolder(PhotoItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: EarthCameraPhotosAdapter.EarthCameraPhotosViewHolder, position: Int) {
        val earthCameraPhotoProperty = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(earthCameraPhotoProperty)
        }
        holder.bind(earthCameraPhotoProperty)
    }

    class OnClickListener(val clickListener: (earthCameraPhotoProperty: EarthCameraPhotoProperty) -> Unit) {
        fun onClick(earthCameraPhotoProperty: EarthCameraPhotoProperty) = clickListener(earthCameraPhotoProperty)
    }


}