package com.example.finalnews.Sources

import android.os.Parcel
import android.os.Parcelable

data class SourcesInfo(var mPublishersSelected: ArrayList<String>?= arrayListOf(), var mCategoriesSelected: ArrayList<String>?= arrayListOf(), var mCountriesSelected: ArrayList<String>?= arrayListOf()):Parcelable {

    private constructor(parcel: Parcel) : this(
        mPublishersSelected = parcel.createStringArrayList(),
        mCategoriesSelected = parcel.createStringArrayList(),
        mCountriesSelected = parcel.createStringArrayList()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeStringList(mPublishersSelected)
        parcel.writeStringList(mCategoriesSelected)
        parcel.writeStringList(mCountriesSelected)
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<SourcesInfo> {
            override fun createFromParcel(parcel: Parcel) = SourcesInfo(parcel)
            override fun newArray(size: Int) = arrayOfNulls<SourcesInfo>(size)
        }
    }
}