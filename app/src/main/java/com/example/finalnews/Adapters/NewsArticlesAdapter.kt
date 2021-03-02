package com.example.finalnews.Adapters

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.finalnews.NewsArticles
import com.example.finalnews.R
import com.example.finalnews.Sources.ArticleItem
import com.example.finalnews.Utilities.BookmarksViewModel
import com.example.finalnews.Utilities.TranslateSources
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_article_item.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class NewsArticlesAdapter(private val listener:OnArticleClickListener, private val mArrayListOfJSONResults:String, private val context: Context, private val picasso: Picasso,private val bookmarksViewModel: BookmarksViewModel) : RecyclerView.Adapter<NewsArticlesAdapter.ArticleViewHolder>(),SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var listOfArticles:ArrayList<ArticleItem>
    private lateinit var sharedPreferences:SharedPreferences
    private var fontSize:Float = 0f
    private var bookmarkedArticles:ArrayList<ArticleItem> = ArrayList()

    init {
    makeListOfArticles()
    setUpSharedPreferences()
    setUpViewModel()
    }

    private fun setUpViewModel() {
        Log.i("viewModel",NewsArticlesAdapter::class.java.simpleName)
        bookmarksViewModel.getBookmarks()?.observe(context as LifecycleOwner,
            Observer<ArrayList<ArticleItem>> {
                bookmarkedArticles.clear()
                for(i in 0 until it.size)
                    bookmarkedArticles.add(it[i])
                notifyDataSetChanged()
            })
    }

    interface OnArticleClickListener {
        fun onArticleItemClick(TAG: String, articleItem: ArticleItem)
    }

    private fun makeListOfArticles(){
        val newsArticlesJSONresponse = JSONObject(mArrayListOfJSONResults)

        val status = newsArticlesJSONresponse.getString(context.resources.getString(R.string.status_key))
        if(status == "ok"){
            val newsArticlesJSONArray = newsArticlesJSONresponse.getJSONArray("articles")
            listOfArticles = ArrayList()
            for(i in 0 until  newsArticlesJSONArray.length()){
                val jsonArticle = newsArticlesJSONArray.get(i) as JSONObject
                val jsonString = jsonArticle.toString()
                val gson = Gson()
                val articleItem:ArticleItem = gson.fromJson(jsonString, ArticleItem::class.java)

                //For the source JSON Object
                val newsArticleSourceJSONObject = jsonArticle.getJSONObject(context.resources.getString(R.string.source_jsonkey))
                val source_id = newsArticleSourceJSONObject.getString(context.resources.getString(R.string.id_jsonkey))
                val source_name = newsArticleSourceJSONObject.getString(context.resources.getString(R.string.name_jsonkey))

                articleItem.source_id = source_id
                articleItem.source_name = source_name
                listOfArticles.add(articleItem)
            }
        }
    }

    private fun setUpSharedPreferences(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        fontSize = convertFontSize()
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
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


    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): NewsArticlesAdapter.ArticleViewHolder {
        val context = parent.context
        val layoutIdForArticleItem = R.layout.news_article_item
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(layoutIdForArticleItem, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsArticlesAdapter.ArticleViewHolder, position: Int) {
        holder.bind(listOfArticles[position], listener, listOfArticles[position].title!!)
        val articleImage = listOfArticles[position].urlToImage
        val publishedAtDate = listOfArticles[position].publishedAt
        val formattedDate= publishedAtDate?.let { TranslateSources.formatPublishedDate(it) }
        val mAuthor = listOfArticles[position].author
        if(mAuthor != null){
            if(!mAuthor.contains("http")) {
                holder.mArticleAuthorTextView.text = mAuthor
            }else {
                holder.mArticleAuthorTextView.setPadding(0,0,0,0)
                holder.mArticleAuthorTextView.text = ""
            }
        } else {
            holder.mArticleAuthorTextView.setPadding(0,0,0,0)
            holder.mArticleAuthorTextView.text = ""
        }
        if(TextUtils.isEmpty(articleImage)){
            holder.mArticleImageView.visibility = View.GONE
        }else {
            holder.mArticleImageView.visibility = View.VISIBLE
            picasso.load(articleImage).into(holder.mArticleImageView)
        }
        holder.mArticleTitleTextView.text = listOfArticles[position].title
        holder.mArticleDescTextView.text = listOfArticles[position].description
        holder.mArticlePublishedTextView.text = formattedDate
        if(bookmarkedArticles.contains(listOfArticles[position])){
            if (holder.theme.equals(context.resources.getString(R.string.darkLabel))){
                holder.mBookmarkImageView.setImageDrawable(context.resources.getDrawable(R.drawable.baseline_bookmark_white_18dp))
            }else{
                holder.mBookmarkImageView.setImageDrawable(context.resources.getDrawable(R.drawable.baseline_bookmark_black_18dp))
            }
        }else{
            if(holder.theme.equals(context.resources.getString(R.string.darkLabel))){
                holder.mBookmarkImageView.setImageDrawable(context.resources.getDrawable(R.drawable.baseline_bookmark_border_white_18dp))
            }else {
                holder.mBookmarkImageView.setImageDrawable(context.resources.getDrawable(R.drawable.baseline_bookmark_border_black_18dp))
            }
        }
    }

    override fun getItemCount(): Int {
        return listOfArticles.size
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val mArticleTitleTextView:TextView = itemView.findViewById(R.id.article_title)
        val mArticleAuthorTextView:TextView = itemView.findViewById(R.id.article_author)
        val mArticlePublishedTextView:TextView = itemView.findViewById(R.id.article_published_time)
        val mArticleDescTextView:TextView = itemView.findViewById(R.id.article_desc)
        val mArticleImageView:ImageView = itemView.findViewById(R.id.article_image)
        val mBookmarkImageView:ImageView = itemView.findViewById(R.id.bookmark_article)
        val mShareImageView:ImageView = itemView.findViewById(R.id.share_article)
        val theme : String?
        init {
            mArticleDescTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
            theme = sharedPreferences.getString(context.resources.getString(R.string.theme_key), context.resources.getString(R.string.theme_default))
            if(theme.equals("Light")){
                mShareImageView.setImageDrawable(context.resources.getDrawable(R.drawable.share_black))
            }else{
                mShareImageView.setImageDrawable(context.resources.getDrawable(R.drawable.share_white))
            }
        }

      fun bind(articleItem:ArticleItem,listener:OnArticleClickListener, title:String){
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

          mBookmarkImageView.setOnClickListener { listener.onArticleItemClick(NewsArticles.clickedArticleBookmarkTAG,articleItem) }
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
}