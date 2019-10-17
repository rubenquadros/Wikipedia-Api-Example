package com.rubenquadros.epicarticles.data.remote.api

import com.rubenquadros.epicarticles.data.remote.model.ArticlesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WikipediaApi {

    @GET("w/api.php?action=query&format=json&prop=pageimages%7Cpageterms&generator=prefixsearch&redirects=1&formatversion=2&piprop=thumbnail&pithumbsize=300&pilimit=10&wbptterms=description&gpslimit=20")
    fun getArticles(
        @Query("gpssearch") topic: String
    ): Observable<ArticlesResponse>

}