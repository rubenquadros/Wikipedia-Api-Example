package com.rubenquadros.epicarticles.custom.webViewClient

import android.webkit.WebView
import android.webkit.WebViewClient
import com.rubenquadros.epicarticles.callbacks.IWebViewCallBack

class CustomWebViewClient(private val listener: IWebViewCallBack): WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        view!!.loadUrl(url)
        return true
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        listener.didFinishLoading(true)
        super.onPageFinished(view, url)
    }

    override fun onReceivedError(
        view: WebView?,
        errorCode: Int,
        description: String?,
        failingUrl: String?
    ) {
        listener.didReceiveError()
        super.onReceivedError(view, errorCode, description, failingUrl)
    }
}