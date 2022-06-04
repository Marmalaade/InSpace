package com.example.inspace.properties

import android.os.Parcel
import android.os.Parcelable

data class EarthCameraDateProperty(
    val date: String
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EarthCameraDateProperty> {
        override fun createFromParcel(parcel: Parcel): EarthCameraDateProperty {
            return EarthCameraDateProperty(parcel)
        }

        override fun newArray(size: Int): Array<EarthCameraDateProperty?> {
            return arrayOfNulls(size)
        }
    }
}