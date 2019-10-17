package com.rubenquadros.epicarticles.callbacks

interface IWebViewCallBack {

    fun didFinishLoading(finishedLoading: Boolean)

    fun didReceiveError()
}