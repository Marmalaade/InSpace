package com.example.inspace.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.example.inspace.R
import com.example.inspace.network.ApiStatus
import com.example.inspace.properties.EarthCameraDateProperty
import com.example.inspace.properties.EarthCameraPhotoProperty
import com.example.inspace.properties.MarsProperty

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<MarsProperty>?) {
    val adapter = recyclerView.adapter as MarsPhotosGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("listDates")
fun bindRecyclerViewDates(recyclerView: RecyclerView, data: List<EarthCameraDateProperty>?) {
    val adapter = recyclerView.adapter as EarthCameraDatesAdapter
    adapter.submitList(data)
}

@BindingAdapter("listPhotos")
fun bindRecyclerViewPhotos(recyclerView: RecyclerView, data: List<EarthCameraPhotoProperty>?) {
    val adapter = recyclerView.adapter as EarthCameraPhotosAdapter
    adapter.submitList(data)
}

@SuppressLint("CheckResult")
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions().apply {
                    imgView.layoutParams?.apply {
                        if (width == ViewGroup.LayoutParams.WRAP_CONTENT && height == ViewGroup.LayoutParams.WRAP_CONTENT)
                            override(SIZE_ORIGINAL, SIZE_ORIGINAL)
                    }
                    placeholder(R.drawable.loading_animation)
                    error(R.drawable.ic_broken_image)
                }

            )
            .into(imgView)

    }
}

@BindingAdapter("marsApiStatus")
fun bindStatus(statusImageView: ImageView, status: ApiStatus?) {
    Log.e("status", "$status")
    when (status) {
        ApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        ApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}