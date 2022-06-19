package com.example.inspace.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.inspace.databinding.GridViewItemBinding
import com.example.inspace.properties.MarsProperty

class MarsPhotosGridAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<MarsProperty, MarsPhotosGridAdapter.MarsPropertyViewHolder>(DiffCallback) {
    class MarsPropertyViewHolder(private var binding: GridViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(marsProperty: MarsProperty) {
            binding.property = marsProperty
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MarsProperty>() {
        override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsPhotosGridAdapter.MarsPropertyViewHolder {
        return MarsPropertyViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MarsPhotosGridAdapter.MarsPropertyViewHolder, position: Int) {
        val marsProperty = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(marsProperty)
        }
        holder.bind(marsProperty)
    }

    class OnClickListener(val clickListener: (marsProperty: MarsProperty) -> Unit) {
        fun onClick(marsProperty: MarsProperty) = clickListener(marsProperty)
    }

}