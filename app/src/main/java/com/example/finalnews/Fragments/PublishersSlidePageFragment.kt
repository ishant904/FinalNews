package com.example.finalnews.Fragments

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalnews.MainActivity
import com.example.finalnews.R
import com.example.finalnews.Sources.SourcesInfo

class PublishersSlidePageFragment : Fragment() {

    interface OnItemClickListener {
        fun onItemClickPublishers(item: String)
    }

    private lateinit var listener: OnItemClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnItemClickListener
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_publishers_slide_page, container, false) as ViewGroup
        val mRecyclerView: RecyclerView = rootView.findViewById(R.id.recyclerView_publishers)
        //int numberOfColumns = Integer.parseInt(getContext().getResources().getString(R.string.no_of_cols));
        mRecyclerView.layoutManager = GridLayoutManager(context, getNumberofColumns())
        mRecyclerView.setHasFixedSize(true)
        val mAdapter = PublishersAdapter()
        mRecyclerView.adapter = mAdapter
        return rootView
    }

    private fun getNumberofColumns(): Int {
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val widthDivider = 400
        val width = displayMetrics.widthPixels
        val nColumns = width / widthDivider
        return if (nColumns < 2) 2 else nColumns
    }

    inner class PublishersAdapter() : RecyclerView.Adapter<PublishersAdapter.PublishersAdapterViewHolder>() {
        var mSourcesInfo: SourcesInfo? = null
        init {
            mSourcesInfo = MainActivity.getSourceInfo()
        }

        override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): PublishersAdapterViewHolder {
            val context = parent.context
            val inflater = LayoutInflater.from(context)
            val view: View = inflater.inflate(R.layout.publisher_item, parent, false)
            return PublishersAdapterViewHolder(view)
        }

        override fun onBindViewHolder(holder: PublishersAdapterViewHolder,position: Int) {
            holder.mPublisherImageView.setImageResource(mThumbIds[position])
            holder.mPublisherTextView.text = mPublishers[position]
            holder.bind(mPublishers[position], listener, position)
            if (mSourcesInfo?.mPublishersSelected?.contains(mPublishers.get(position))!!) {
                //source has been selected
                holder.mPublisherConstraintLayout.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
            } else {
                holder.mPublisherConstraintLayout.setBackgroundColor(Color.TRANSPARENT)
            }
        }

        override fun getItemCount(): Int {
            return mThumbIds.size
        }

        inner class PublishersAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var mPublisherConstraintLayout: ConstraintLayout = itemView.findViewById(R.id.constraint_layout_publisher_item)
            var mPublisherImageView: ImageView = itemView.findViewById(R.id.publisher_icon)
            var mPublisherTextView: TextView = itemView.findViewById(R.id.publisher_desc)

            fun bind(mPublisher: String, clickListener: OnItemClickListener, position: Int) {
                itemView.setOnClickListener {
                    clickListener.onItemClickPublishers(mPublisher)
                    mSourcesInfo = MainActivity.getSourceInfo()
                    notifyDataSetChanged()
                }
            }
        }
    }
    val mThumbIds = arrayOf<Int>(
        R.drawable.abc_news, R.drawable.abc_news_au,
        R.drawable.aftenposten, R.drawable.aljazeera_english,
        R.drawable.bbc, R.drawable.cbs_news,
        R.drawable.cnn_news, R.drawable.entertainment_weekly,
        R.drawable.espn, R.drawable.financial_post,
        R.drawable.financial_times, R.drawable.fox_news,
        R.drawable.fox_sports, R.drawable.ign,
        R.drawable.independent, R.drawable.lequipe,
        R.drawable.metro, R.drawable.msnbc,
        R.drawable.mtvnews, R.drawable.nat_geo,
        R.drawable.nbc_news, R.drawable.new_scientist,
        R.drawable.new_york_magazine, R.drawable.talk_sport,
        R.drawable.techradar, R.drawable.the_guardian,
        R.drawable.the_nyt, R.drawable.wsj
    )

    val mPublishers = arrayOf(
        "ABC News",
        "ABC News (AU)",
        "Aftenposten",
        "AlJazeera (ENG)",
        "BBC",
        "CBS News",
        "CNN News",
        "Entertainment Weekly",
        "ESPN",
        "Financial Post",
        "Financial Times",
        "Fox News",
        "Fox Sports",
        "IGN",
        "Independent",
        "L'Equipe",
        "Metro",
        "MSNBC",
        "MTV News",
        "Nat. Geo.",
        "NBC News",
        "New Scientist",
        "NY Magazine",
        "Talk Sport",
        "TechRadar",
        "The Guardian",
        "NYT",
        "Wall Street Journal"
    )

}