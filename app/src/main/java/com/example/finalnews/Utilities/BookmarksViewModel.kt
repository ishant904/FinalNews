package com.example.finalnews.Utilities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalnews.R
import com.example.finalnews.Sources.ArticleItem
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BookmarksViewModel : ViewModel() {
    private val mFirebaseAuth = Firebase.auth
    private val databaseReference = Firebase.database.getReference("users")
    private var bookmarkedArticles:MutableLiveData<ArrayList<ArticleItem>>? = null

    fun getBookmarks():MutableLiveData<ArrayList<ArticleItem>>?{
        if(bookmarkedArticles == null)
            bookmarkedArticles = MutableLiveData()
        mFirebaseAuth.currentUser?.uid?.let { databaseReference.child(it).child("Bookmarks").addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val newBookmarks: ArrayList<ArticleItem> = ArrayList()
                    for (i in snapshot.children) {
                        val articleItem = i.getValue(ArticleItem::class.java)
                        if (articleItem != null) {
                            newBookmarks.add(articleItem)
                        }
                    }
                    bookmarkedArticles!!.value = newBookmarks
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
        return bookmarkedArticles
    }
}