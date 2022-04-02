package com.example.inspace.properties

import android.os.Parcel
import android.os.Parcelable
import com.example.inspace.network.EarthCameraApiService

data class EarthCameraPhotoProperty(
    val caption: String,
    val date: String,
    val identifier: String,
    val image: String

) : Parcelable {

    fun getImageUrl(): String {
        val sb = StringBuilder()
        sb.append("https://api.nasa.gov/EPIC/archive/natural/")
        val dateComponents = date.split(" ".toRegex()).toTypedArray()[0].split("-".toRegex()).toTypedArray()
        sb
            .append(dateComponents[0]).append('/')
            .append(dateComponents[1]).append('/')
            .append(dateComponents[2]).append("/png/")
            .append(image).append(".png?api_key=").append(EarthCameraApiService.ApiKey.KEY)
        return sb.toString()
    }

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(caption)
        parcel.writeString(date)
        parcel.writeString(identifier)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EarthCameraPhotoProperty> {
        override fun createFromParcel(parcel: Parcel): EarthCameraPhotoProperty {
            return EarthCameraPhotoProperty(parcel)
        }

        override fun newArray(size: Int): Array<EarthCameraPhotoProperty?> {
            return arrayOfNulls(size)
        }
    }
}