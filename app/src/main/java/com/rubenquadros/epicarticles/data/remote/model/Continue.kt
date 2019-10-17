package com.rubenquadros.epicarticles.data.remote.model

import com.google.gson.annotations.SerializedName

class Continue {

    @SerializedName("continue")
    var `continue`: String? = null
    @SerializedName("gpsoffset")
    var gpsoffset: Long? = null

}
