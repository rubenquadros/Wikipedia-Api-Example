package com.rubenquadros.epicarticles.data.dataCallBack

import com.rubenquadros.epicarticles.data.local.entity.WikiEntity

interface IDBCallBack {

    fun onQueryExecuted(articlesData: List<WikiEntity>)
}