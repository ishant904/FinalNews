package com.example.finalnews.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.example.finalnews.NewsArticles
import com.example.finalnews.R

class TabletFragment:Fragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_web, container, false) as ViewGroup
        val bundle = this.arguments
        if(bundle!=null){
            val uriString = bundle.getString(NewsArticles.tabletUriKey).toString()
            val webView:WebView = rootView.findViewById(R.id.webview)
            webView.loadUrl(uriString)
        }
        return rootView
    }
}