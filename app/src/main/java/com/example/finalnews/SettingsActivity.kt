package com.example.finalnews

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.UserHandle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.preference.PreferenceManager
import com.example.finalnews.Sources.UserSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(),SharedPreferences.OnSharedPreferenceChangeListener{

    private lateinit var pref:SharedPreferences
    private lateinit var settingsDB:DatabaseReference
    private lateinit var mUserSettings: UserSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        pref =PreferenceManager.getDefaultSharedPreferences(this)
        val mFirebaseAuth = Firebase.auth
        settingsDB = mFirebaseAuth.currentUser?.uid?.let {Firebase.database.getReference(getString(R.string.users_label)).child(it).child(getString(R.string.settings_label))}!!
        getUserSettings()
        val themeName = pref.getString(getString(R.string.theme_key),getString(R.string.theme_default))
        if(themeName.equals(getString(R.string.lightLabel))){
            setTheme(R.style.LightTheme)
        } else if (themeName.equals(getString(R.string.darkLabel))) {
            setTheme(R.style.DarkTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(settings_activity_toolbar)
        val actionBar = this.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        pref.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> { finish();return true}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getUserSettings(){
        val fontSize = pref.getString(getString(R.string.pref_fontSize_key),getString(R.string.menu_medium_label))
        val theme = pref.getString(getString(R.string.theme_key),getString(R.string.darkLabel))
        val locale = resources.configuration.locale.country
        val defaultCountry = locale.toLowerCase()
        val topHeadlinesCountry = pref.getString(getString(R.string.widgetKey_top_headlines), defaultCountry)
        mUserSettings = UserSettings(fontSize, theme, topHeadlinesCountry)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if(key.equals(getString(R.string.theme_key))){
            val theme = pref.getString(key,getString(R.string.darkLabel))
            mUserSettings.theme = theme
            recreate()
        }else if(key.equals(getString(R.string.widgetKey_top_headlines))){
            val locale = resources.configuration.locale.country
            val defaultCountry = locale.toLowerCase()
            val topHeadlinesCountry = pref.getString(key, defaultCountry)
            mUserSettings.topHeadLinesCountry = topHeadlinesCountry
        }else if(key.equals(getString(R.string.pref_fontSize_key))){
            val fontSize = pref.getString(key, getString(R.string.menu_medium_label))
            mUserSettings.fontSize = fontSize
        }
        settingsDB.setValue(mUserSettings)
    }

    override fun onDestroy() {
        super.onDestroy()
        pref.unregisterOnSharedPreferenceChangeListener(this)
    }
}
