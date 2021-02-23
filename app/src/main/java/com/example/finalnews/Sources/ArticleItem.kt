package com.example.finalnews.Sources

import android.os.Parcel
import android.os.Parcelable

data class ArticleItem(val author: String?="",val title: String?="",val description: String?="",val url: String?="",val urlToImage: String?="",var publishedAt: String?="",var source_id: String?="",var source_name: String?="") : Parcelable {

    private constructor(parcel: Parcel) : this(
        author = parcel.readString(),
        title = parcel.readString(),
        description = parcel.readString(),
        url = parcel.readString(),
        urlToImage = parcel.readString(),
        publishedAt = parcel.readString(),
        source_id = parcel.readString(),
        source_name = parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(url)
        parcel.writeString(urlToImage)
        parcel.writeString(publishedAt)
        parcel.writeString(source_id)
        parcel.writeString(source_name)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(obj: Any?): Boolean {
        return if (obj !is ArticleItem) {
            false
        } else this.title == obj.title
    }

    companion object CREATOR : Parcelable.Creator<ArticleItem> {
        override fun createFromParcel(parcel: Parcel): ArticleItem {
            return ArticleItem(parcel)
        }

        override fun newArray(size: Int): Array<ArticleItem?> {
            return arrayOfNulls(size)
        }
    }
}