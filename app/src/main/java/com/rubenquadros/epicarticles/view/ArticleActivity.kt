package com.rubenquadros.epicarticles.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.ButterKnife
import com.rubenquadros.epicarticles.R
import com.rubenquadros.epicarticles.callbacks.IWebViewCallBack
import com.rubenquadros.epicarticles.custom.webViewClient.CustomWebViewClient
import com.rubenquadros.epicarticles.utils.ApplicationConstants

class ArticleActivity : AppCompatActivity(), IWebViewCallBack {

    @BindView(R.id.webView) lateinit var mWebView: WebView
    @BindView(R.id.myProgressBar) lateinit var progressBar: ProgressBar
    private var mUrl: String = ""
    private var networkStatus = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        ButterKnife.bind(this)
        this.readData()
        this.setupWebView()
    }

    private fun readData() {
        if(intent.getStringExtra(ApplicationConstants.STRING_FOR_URL) != null) {
            mUrl = ApplicationConstants.ARTICLE_URL + intent.getStringExtra(ApplicationConstants.STRING_FOR_URL)
        }
        if(intent.getStringExtra(ApplicationConstants.OFFLINE) != null) {
            networkStatus = intent.getStringExtra(ApplicationConstants.OFFLINE)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        mWebView.webViewClient = CustomWebViewClient(this)
        mWebView.settings.blockNetworkLoads = false
        mWebView.settings.setAppCacheEnabled(true)
        mWebView.settings.setAppCachePath(application.cacheDir.path)
        mWebView.settings.javaScriptEnabled = true
        if(networkStatus == ApplicationConstants.OFFLINE) {
            mWebView.settings.cacheMode = WebSettings.LOAD_CACHE_ONLY
        }else {
            mWebView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        }
        mWebView.loadUrl(mUrl)
    }

    override fun didFinishLoading(finishedLoading: Boolean) {
        if(finishedLoading) {
            progressBar.visibility = View.GONE
        }
    }

    override fun didReceiveError() {
        //onBackPressed()
        mWebView.loadUrl(ApplicationConstants.ERR_URL)
    }
}
