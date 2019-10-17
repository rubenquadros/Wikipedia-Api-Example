package com.rubenquadros.epicarticles.data.remote.model

import com.google.gson.annotations.SerializedName

class ArticlesResponse {

    @SerializedName("batchcomplete")
    var batchcomplete: Boolean? = null
    @SerializedName("continue")
    var `continue`: Continue? = null
    @SerializedName("query")
    var query: Query? = null

}
