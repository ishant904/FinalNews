package com.example.finalnews.Adapters

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.text.TextUtils
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalnews.NewsArticles
import com.example.finalnews.R
import com.example.finalnews.Sources.ArticleItem
import com.example.finalnews.Utilities.TranslateSources
import com.squareup.picasso.Picasso

class BookmarkedArticlesAdapter(private val bookmarkedArticles : ArrayList<ArticleItem>,private val listner:NewsArticlesAdapter.OnArticleClickListener,private val context: Context) : RecyclerView.Adapter<BookmarkedArticlesAdapter.ArticleViewHolder>(),SharedPreferences.OnSharedPreferenceChangeListener {

    private val sharedPreferences : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): BookmarkedArticlesAdapter.ArticleViewHolder {
        val inflater =LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.news_article_item,parent,false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookmarkedArticlesAdapter.ArticleViewHolder,position: Int) {
        holder.bind(bookmarkedArticles[position],listner,bookmarkedArticles[position].title!!)
        val articleImage = bookmarkedArticles[position].urlToImage
        val publishedAtDate = bookmarkedArticles[position].publishedAt
        val formattedDate = TranslateSources.formatPublishedDate(publishedAtDate!!)
        val mAuthor = bookmarkedArticles[position].author
        if(mAuthor != null){
            if(!mAuthor.contains("http")){
                holder.mArticleAuthorTextView.text = mAuthor
            } else {
                holder.mArticleAuthorTextView.setPadding(0,0,0,0)
                holder.mArticleAuthorTextView.text = ""
            }
        } else {
            holder.mArticleAuthorTextView.setPadding(0,0,0,0)
            holder.mArticleAuthorTextView.text = ""
        }

        if(TextUtils.isEmpty(articleImage)){
            holder.mArticleImageView.visibility = View.GONE
        } else{
            holder.mArticleImageView.visibility = View.VISIBLE
            Picasso.get().load(articleImage).into(holder.mArticleImageView)
        }
        holder.mArticleTitleTextView.text = bookmarkedArticles[position].title
        holder.mArticlePublishedTextView.text = formattedDate
        holder.mArticleDescTextView.text = bookmarkedArticles[position].description
        holder.itemView.tag = bookmarkedArticles[position]

    }

    override fun getItemCount(): Int {
        return bookmarkedArticles.size
    }

    private fun convertFontSize():Float{

        return when(sharedPreferences.getString(context.resources.getString(R.string.pref_fontSize_key), context.resources.getString(R.string.fontSize_default))) {
            "Small"->
                12f
            "Medium"->
                18f
            "Large"->
                24f
            else->
                18f
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if(key.equals(context.resources.getString(R.string.pref_fontSize_key))){
            convertFontSize()
            notifyDataSetChanged()
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        super.onDetachedFromRecyclerView(recyclerView)
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mArticleTitleTextView: TextView = itemView.findViewById(R.id.article_title)
        val mArticleAuthorTextView: TextView = itemView.findViewById(R.id.article_author)
        val mArticlePublishedTextView: TextView = itemView.findViewById(R.id.article_published_time)
        val mArticleDescTextView: TextView = itemView.findViewById(R.id.article_desc)
        val mArticleImageView: ImageView = itemView.findViewById(R.id.article_image)
        val mBookmarkImageView: ImageView = itemView.findViewById(R.id.bookmark_article)
        val mShareImageView: ImageView = itemView.findViewById(R.id.share_article)

        init {
            mArticleDescTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, convertFontSize())
            mBookmarkImageView.visibility = View.INVISIBLE
            val theme = sharedPreferences.getString(context.resources.getString(R.string.theme_key), context.resources.getString(R.string.theme_default))
            if(theme.equals("Light")){
                mShareImageView.setImageDrawable(context.resources.getDrawable(R.drawable.share_black))
            }else{
                mShareImageView.setImageDrawable(context.resources.getDrawable(R.drawable.share_white))
            }
        }

        fun bind(articleItem:ArticleItem, listener: NewsArticlesAdapter.OnArticleClickListener, title:String){
            itemView.setOnClickListener {
                listener.onArticleItemClick(NewsArticles.clickedArticleURLTAG, articleItem)
                //When user clicks on article, it will open website for article.
            }

            mShareImageView.setOnClickListener {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, articleItem.url)
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, title)
                sendIntent.type = "text/plain"
                context.startActivity(Intent.createChooser(sendIntent,context.resources.getText(R.string.send_to)))
            }
        }
    }
}