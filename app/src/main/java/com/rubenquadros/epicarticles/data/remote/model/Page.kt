package com.rubenquadros.epicarticles.data.remote.model

import com.google.gson.annotations.SerializedName

class Page {

    @SerializedName("index")
    var index: Long? = null
    @SerializedName("ns")
    var ns: Long? = null
    @SerializedName("pageid")
    var pageid: Long? = null
    @SerializedName("terms")
    var terms: Terms? = null
    @SerializedName("thumbnail")
    var thumbnail: Thumbnail? = null
    @SerializedName("title")
    var title: String? = null

}
