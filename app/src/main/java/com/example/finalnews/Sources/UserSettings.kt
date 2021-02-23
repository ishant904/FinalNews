package com.example.finalnews.Sources

import android.os.Parcel
import android.os.Parcelable
import com.example.finalnews.R

data class UserSettings(var fontSize:String? = "Medium", var theme:String? = "Dark", var topHeadLinesCountry:String? = ""):Parcelable{
    private constructor(parcel: Parcel) : this(
        fontSize = parcel.readString(),
        theme = parcel.readString(),
        topHeadLinesCountry = parcel.readString()
    )
    override fun describeContents(): Int {
        return 0
    }
    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeString(fontSize)
        parcel.writeString(theme)
        parcel.writeString(topHeadLinesCountry)
    }
    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<UserSettings> {
            override fun createFromParcel(parcel: Parcel) = UserSettings(parcel)
            override fun newArray(size: Int) = arrayOfNulls<UserSettings>(size)
        }
    }
}