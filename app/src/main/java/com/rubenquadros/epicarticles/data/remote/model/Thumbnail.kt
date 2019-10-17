package com.rubenquadros.epicarticles.data.remote.model

import com.google.gson.annotations.SerializedName

class Thumbnail {

    @SerializedName("height")
    var height: Long? = null
    @SerializedName("source")
    var source: String? = null
    @SerializedName("width")
    var width: Long? = null

}
