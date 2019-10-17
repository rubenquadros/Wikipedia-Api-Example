package com.rubenquadros.epicarticles.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rubenquadros.epicarticles.base.BaseViewModel
import com.rubenquadros.epicarticles.data.local.entity.WikiEntity
import com.rubenquadros.epicarticles.data.remote.model.ArticlesResponse
import com.rubenquadros.epicarticles.data.repository.WikiRepo
import com.rubenquadros.epicarticles.utils.ApplicationConstants
import com.rubenquadros.epicarticles.utils.ApplicationUtility
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class HomeViewModel @Inject
constructor(private val wikiRepo: WikiRepo,
            @param:Named(ApplicationConstants.SUBSCRIBER_ON) private val subscriberOn: Scheduler,
            @param:Named(ApplicationConstants.OBSERVER_ON) private val observerOn: Scheduler): BaseViewModel(){

    val isLoading: MutableLiveData<Boolean?> = MutableLiveData()
    private val randomArticlesResponse: MutableLiveData<ArticlesResponse> = MutableLiveData()
    private val randomArticlesError: MutableLiveData<String?> = MutableLiveData()
    private var wikiEntity = ArrayList<WikiEntity>()
    private var dbResponse: MutableLiveData<List<WikiEntity>> = MutableLiveData()

    fun getArticles(topic: String, service: String) {
        this.disposable.addAll(this.wikiRepo.getRandomArticles(topic)
            .subscribeOn(subscriberOn)
            .observeOn(observerOn)
            .doOnSubscribe { isLoading.value = true }
            .doOnError { isLoading.value = false }
            .doOnComplete { isLoading.value = false }
            .subscribe({resources -> getRandomArticlesResponse().postValue(resources)},
                {resources -> if(service == ApplicationConstants.RANDOM_ARTICLES && resources.message != ApplicationConstants.ERR_IGNORE)
                    getRandomArticlesError().postValue(ApplicationConstants.RANDOM_ARTICLES_ERR) else if (service == ApplicationConstants.SEARCH_ARTICLE && resources.message != ApplicationConstants.ERR_IGNORE)
                    getRandomArticlesError().postValue(ApplicationConstants.SEARCH_ARTICLE_ERR)}))
    }

    fun getRandomArticlesResponse() = randomArticlesResponse

    fun getRandomArticlesError() = randomArticlesError

    fun deleteArticles() {
        this.wikiRepo.deleteArticles()
    }

    fun saveArticles(randomArticles: ArticlesResponse) {
        for(i in randomArticles.query!!.pages!!.indices) {
            wikiEntity.add(WikiEntity(i, randomArticles.query!!.pages!![i].title,
                randomArticles.query!!.pages!![i].terms?.description?.get(0),
                randomArticles.query!!.pages!![i].thumbnail?.source,
                ApplicationUtility.getStringForUrl(randomArticles.query!!.pages!![i].title!!)
                )
            )
        }
        this.wikiRepo.insetArticles(wikiEntity)
    }

    fun initLocalData() {
        dbResponse = this.wikiRepo.getArticlesFromDB()
    }

    fun getArticlesFromDB(): LiveData<List<WikiEntity>> {
        return dbResponse
    }
}