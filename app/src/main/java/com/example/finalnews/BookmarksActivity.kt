package com.example.finalnews

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalnews.Adapters.BookmarkedArticlesAdapter
import com.example.finalnews.Adapters.NewsArticlesAdapter
import com.example.finalnews.Sources.ArticleItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_bookmarks.*
import java.util.*

class BookmarksActivity : AppCompatActivity() {
    private lateinit var mFirebaseAuth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var bookmarkedArticles: ArrayList<ArticleItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmarks)
        setSupportActionBar(bookmarks_activity_toolbar)
        mFirebaseAuth = Firebase.auth
        databaseRef =Firebase.database.getReference(getString(R.string.users_label))
        bookmarkedArticles = ArrayList()
        getListOfBookmarkedArticles()
        val ab = supportActionBar
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true)
            ab.title = getString(R.string.bookmarked_articles)
        }
        setUpBookmarkedArticles()
    }

    private fun getListOfBookmarkedArticles(){
        mFirebaseAuth.currentUser?.uid?.let { databaseRef.child(it).child(getString(R.string.bookmarks_label)).addValueEventListener(
            object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    bookmarkedArticles.clear()
                    for (i in snapshot.children) {
                        bookmarkedArticles.add(i.getValue(ArticleItem::class.java)!!)
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    private fun setUpBookmarkedArticles(){
        bookmarkedArticles = intent.getParcelableArrayListExtra(NewsArticles.bookmarksKey)!!
        val bookmarksAdapter = BookmarkedArticlesAdapter(bookmarkedArticles, object : NewsArticlesAdapter.OnArticleClickListener{
            override fun onArticleItemClick(TAG: String, articleItem: ArticleItem) {
                if(TAG == NewsArticles.clickedArticleURLTAG){
                    val webpage = Uri.parse(articleItem.url)
                    val intent = Intent(Intent.ACTION_VIEW, webpage)
                    if(intent.resolveActivity(applicationContext.packageManager)!=null){
                        startActivity(intent)
                    }
                }
            }

        },this)
        bookmarks_recyclerView.setHasFixedSize(true)
        bookmarks_recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        bookmarks_recyclerView.adapter =bookmarksAdapter

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{onBackPressed()
            return true}
        }
        return super.onOptionsItemSelected(item)
    }
}
