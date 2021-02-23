package com.example.finalnews.Fragments

import android.os.Bundle
import android.preference.PreferenceFragment
import com.example.finalnews.R

class SettingsFragment : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
    }

}