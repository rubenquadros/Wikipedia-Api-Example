package com.rubenquadros.epicarticles.data.remote.model

import com.google.gson.annotations.SerializedName

class Query {

    @SerializedName("pages")
    var pages: List<Page>? = null

}
