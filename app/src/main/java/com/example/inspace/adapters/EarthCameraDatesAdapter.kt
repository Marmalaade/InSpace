package com.example.inspace.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.inspace.databinding.DateItemBinding
import com.example.inspace.properties.EarthCameraDateProperty
import com.example.inspace.properties.MarsProperty

class EarthCameraDatesAdapter(private val onClickListener: EarthCameraDatesAdapter.OnClickListener) :
    ListAdapter<EarthCameraDateProperty, EarthCameraDatesAdapter.EarthCameraViewHolder>(DiffCallback) {
    class EarthCameraViewHolder(private var binding: DateItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(earthCameraDateProperty: EarthCameraDateProperty) {
            binding.dates = earthCameraDateProperty
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<EarthCameraDateProperty>() {
        override fun areItemsTheSame(oldItem: EarthCameraDateProperty, newItem: EarthCameraDateProperty): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: EarthCameraDateProperty, newItem: EarthCameraDateProperty): Boolean {
            return oldItem.date == newItem.date
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarthCameraDatesAdapter.EarthCameraViewHolder {
        return EarthCameraViewHolder(DateItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: EarthCameraDatesAdapter.EarthCameraViewHolder, position: Int) {
        val earthCameraDateProperty = getItem(position)
        holder.bind(earthCameraDateProperty)
    }

    class OnClickListener(val clickListener: (earthCameraDateProperty: EarthCameraDateProperty) -> Unit) {
        fun onClick(earthCameraDateProperty: EarthCameraDateProperty) = clickListener(earthCameraDateProperty)
    }


}