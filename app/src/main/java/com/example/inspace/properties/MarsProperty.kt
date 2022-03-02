package com.example.inspace.properties

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json


data class MarsProperty(
    val id: String,
    @Json(name = "img_src") val imgSrcUrl: String,
    val price: Int,
    val type: String
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(imgSrcUrl)
        parcel.writeInt(price)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MarsProperty> {
        override fun createFromParcel(parcel: Parcel): MarsProperty {
            return MarsProperty(parcel)
        }

        override fun newArray(size: Int): Array<MarsProperty?> {
            return arrayOfNulls(size)
        }
    }
}