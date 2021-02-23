package com.example.finalnews.Fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalnews.MainActivity
import com.example.finalnews.R
import com.example.finalnews.Sources.SourcesInfo

class CountriesSlidePageFragment : Fragment() {
    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClickCountries(item: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener =
            context as OnItemClickListener
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_countries_slide_page, container, false) as ViewGroup
        val mRecyclerView: RecyclerView = rootView.findViewById(R.id.recyclerView_countries)
        val numberOfColumns = requireContext().resources.getString(R.string.no_of_cols).toInt()
        mRecyclerView.layoutManager = GridLayoutManager(context, numberOfColumns)
        mRecyclerView.setHasFixedSize(true)
        val mAdapter = CountriesAdapter()
        mRecyclerView.adapter = mAdapter
        return rootView
    }

     inner class CountriesAdapter() : RecyclerView.Adapter<CountriesAdapter.CountriesAdapterViewHolder>() {
         private var mSourcesInfo: SourcesInfo? = null

         init {
             mSourcesInfo = MainActivity.getSourceInfo()
         }

         override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): CountriesAdapterViewHolder {
             val context = parent.context
             val layoutIdForItem: Int = R.layout.countries_item
             val inflater = LayoutInflater.from(context)
             val view = inflater.inflate(layoutIdForItem, parent, false)
             return CountriesAdapterViewHolder(view)
         }

         override fun onBindViewHolder(holder: CountriesAdapterViewHolder,position: Int) {
             holder.mCountryImageView.setImageResource(mThumbIds[position])
             holder.mCountryTextView.text = mCountries[position]
             holder.bind(mCountries[position], listener)
             if (mSourcesInfo?.mCountriesSelected?.contains(mCountries[position])!!) {
                 holder.mCountryConstraintLayout.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
             } else {
                 holder.mCountryConstraintLayout.setBackgroundColor(Color.TRANSPARENT)
             }
         }

         override fun getItemCount(): Int {
             return mThumbIds.size
         }

          inner class CountriesAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
              val mCountryConstraintLayout: ConstraintLayout = itemView.findViewById(R.id.constraint_layout_countries_item)
              val mCountryImageView: ImageView = itemView.findViewById(R.id.countries_icon)
              val mCountryTextView: TextView = itemView.findViewById(R.id.countries_desc)

              fun bind(mCountry: String,clickListener: OnItemClickListener) {
                  itemView.setOnClickListener {
                      clickListener.onItemClickCountries(mCountry)
                      mSourcesInfo = MainActivity.getSourceInfo() //The SourcesInfo would have changed now from MainActivity
                      notifyDataSetChanged()
                  }
              }
         }
     }

    private val mThumbIds = arrayOf<Int>(
        R.drawable.argentina,
        R.drawable.australia,
        R.drawable.austria,
        R.drawable.belgium,
        R.drawable.brazil,
        R.drawable.bulgaria,
        R.drawable.canada,
        R.drawable.china,
        R.drawable.colombia,
        R.drawable.cuba,
        R.drawable.czech_republic,
        R.drawable.egypt,
        R.drawable.france,
        R.drawable.germany,
        R.drawable.greece,
        R.drawable.hong_kong,
        R.drawable.hungary,
        R.drawable.india,
        R.drawable.indonesia,
        R.drawable.ireland,
        R.drawable.israel,
        R.drawable.italy,
        R.drawable.japan,
        R.drawable.latvia,
        R.drawable.lithuania,
        R.drawable.malaysia,
        R.drawable.mexico,
        R.drawable.morocco,
        R.drawable.netherlands,
        R.drawable.new_zealand,
        R.drawable.nigeria,
        R.drawable.norway,
        R.drawable.philippines,
        R.drawable.poland,
        R.drawable.portugal,
        R.drawable.romania,
        R.drawable.russia,
        R.drawable.saudi_arabia,
        R.drawable.serbia,
        R.drawable.singapore,
        R.drawable.slovakia,
        R.drawable.slovenia,
        R.drawable.southafrica,
        R.drawable.southkorea,
        R.drawable.sweden,
        R.drawable.switzerland,
        R.drawable.taiwan,
        R.drawable.thailand,
        R.drawable.turkey,
        R.drawable.ukraine,
        R.drawable.united_arab_emirates,
        R.drawable.united_kingdom,
        R.drawable.united_states,
        R.drawable.venezuela
    )

    private val mCountries = arrayOf(
        "Argentina",
        "Australia",
        "Austria",
        "Belgium",
        "Brazil",
        "Bulgaria",
        "Canada",
        "China",
        "Colombia",
        "Cuba",
        "Czech Rep.",
        "Egypt",
        "France",
        "Germany",
        "Greece",
        "Hong Kong",
        "Hungary",
        "India",
        "Indonesia",
        "Ireland",
        "Israel",
        "Italy",
        "Japan",
        "Latvia",
        "Lithuania",
        "Malaysia",
        "Mexico",
        "Morocco",
        "Netherlands",
        "New Zealand",
        "Nigeria",
        "Norway",
        "Philippines",
        "Poland",
        "Portugal",
        "Romania",
        "Russia",
        "Saudi Arabia",
        "Serbia",
        "Singapore",
        "Slovakia",
        "Slovenia",
        "South Africa",
        "South Korea",
        "Sweden",
        "Switzerland",
        "Taiwan",
        "Thailand",
        "Turkey",
        "Ukraine",
        "U.A.E",
        "U.K",
        "U.S.A",
        "Venezuela"
    )
}