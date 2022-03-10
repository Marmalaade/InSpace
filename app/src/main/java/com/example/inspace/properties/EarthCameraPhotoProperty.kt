package com.example.inspace.properties

import android.os.Parcel
import android.os.Parcelable

data class EarthCameraPhotoProperty(
    val caption: String,
    val date: String,
    val identifier: String,
    val image: String

) : Parcelable {
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