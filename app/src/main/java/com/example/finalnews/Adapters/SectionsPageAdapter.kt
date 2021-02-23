package com.example.finalnews.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class SectionsPageAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()

    fun addFragment(fragment: Fragment,title:String){
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mFragmentTitleList[position]
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    //This getItemPosition() method to return POSITION NONE is necessary to update the fragments to new content
    //When notifyDataSetChanged() is called the FragmentStatePagerAdapter will call getItemPosition().

    //This getItemPosition() method to return POSITION NONE is necessary to update the fragments to new content
    //When notifyDataSetChanged() is called the FragmentStatePagerAdapter will call getItemPosition().
    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }
}