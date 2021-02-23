package com.example.finalnews.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.finalnews.Adapters.NewsArticlesAdapter
import com.example.finalnews.NewsArticles
import com.example.finalnews.R
import com.example.finalnews.Sources.ArticleItem
import com.example.finalnews.Utilities.BookmarksViewModel
import com.squareup.picasso.Picasso

class NewsSourceFragment : Fragment() {

    private lateinit var mArrayListOfJSONResults : String
    private lateinit var listener: FragmentListener
    private lateinit var picasso: Picasso
    private lateinit var bookmarksViewModel:BookmarksViewModel

    interface FragmentListener {
        fun onFragmentClick(TAG: String, articleItem: ArticleItem)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        picasso = Picasso.get()
        bookmarksViewModel = ViewModelProvider(this).get(BookmarksViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as FragmentListener
    }


    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_news_articles_page, container, false) as ViewGroup
        val mySwipeRefreshLayout: SwipeRefreshLayout = rootView.findViewById(R.id.swiperefresh)
        mySwipeRefreshLayout.setOnRefreshListener {listener.onFragmentClick(NewsArticles.clickedSwipeToRefreshTAG, ArticleItem("","","","","","","",""))}
        val mRecyclerView : RecyclerView = rootView.findViewById(R.id.news_articles_fragment_recycler_view)
        mRecyclerView.layoutManager = GridLayoutManager(context, 1)
        mRecyclerView.setHasFixedSize(true)
        val bundle = this.arguments
        if(bundle!=null){
            if (bundle.containsKey(NewsArticles.publishersJSONStringKey)) {
                mArrayListOfJSONResults = bundle.getString(NewsArticles.publishersJSONStringKey).toString()
            } else if (bundle.containsKey(NewsArticles.countriesAndCategoriesJSONStringKey)) {
                mArrayListOfJSONResults = bundle.getString(NewsArticles.countriesAndCategoriesJSONStringKey).toString()
            }
        }
        val mArticlesAdapter = NewsArticlesAdapter(object:NewsArticlesAdapter.OnArticleClickListener{
            override fun onArticleItemClick(TAG: String, articleItem: ArticleItem) {
                listener.onFragmentClick(TAG, articleItem)
            }
        }, mArrayListOfJSONResults, requireContext(),picasso,bookmarksViewModel)
        mRecyclerView.adapter = mArticlesAdapter
        return rootView
    }
}