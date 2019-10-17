package com.rubenquadros.epicarticles.data.repository

import androidx.lifecycle.MutableLiveData
import com.rubenquadros.epicarticles.data.dataCallBack.IDBCallBack
import com.rubenquadros.epicarticles.data.local.dao.WikiDAO
import com.rubenquadros.epicarticles.data.local.entity.WikiEntity
import com.rubenquadros.epicarticles.data.remote.api.WikipediaApi
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class WikiRepo(private val wikiDAO: WikiDAO, private val wikiApi: WikipediaApi) {

    private var articlesData: List<WikiEntity> = ArrayList()
    private var articles: MutableLiveData<List<WikiEntity>> = MutableLiveData()

    fun getRandomArticles(topic: String) = this.wikiApi.getArticles(topic)

    fun deleteArticles() {
        doAsync {
            wikiDAO.deleteAll()
        }
    }

    fun insetArticles(articles: ArrayList<WikiEntity>) {
        doAsync {
            wikiDAO.insertAll(articles)
        }
    }

    fun getArticlesFromDB(): MutableLiveData<List<WikiEntity>> {
        setArticlesFromDB(object : IDBCallBack {
            override fun onQueryExecuted(articlesData: List<WikiEntity>) {
                articles.value = articlesData
            }
        })
        return articles
    }

    private fun setArticlesFromDB(dbCallBack: IDBCallBack) {
        doAsync {
            articlesData = wikiDAO.getArticles()
            uiThread { dbCallBack.onQueryExecuted(articlesData) }
        }
    }
}