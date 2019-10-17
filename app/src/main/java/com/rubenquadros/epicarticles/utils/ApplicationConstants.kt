package com.rubenquadros.epicarticles.utils

class ApplicationConstants {
    companion object {

        const val BASE_URL = "https://en.wikipedia.org/"
        const val TIMEOUT_REQUEST: Long = 30
        const val SUBSCRIBER_ON = "SubscribeOn"
        const val OBSERVER_ON = "ObserveOn"
        const val RANDOM_ARTICLES = "Random articles"
        const val SEARCH_ARTICLE = "Search article"
        const val RANDOM_ARTICLES_ERR = "Random articles fetching error"
        const val SEARCH_ARTICLE_ERR = "Search article error"
        const val ERR_IGNORE = "Cannot invoke setValue on a background thread"
        const val STRING_FOR_URL = "String for URL"
        const val OFFLINE = "Offline"
        const val ONLINE = "Online"
        const val ARTICLE_URL = "https://en.wikipedia.org/wiki/"
        const val ERR_URL = "file:///android_asset/error.html"
        val ARTICLE_LIST = arrayListOf("India", "Football", "Block Chain", "Gandhi", "ISS", "Global Warming",
                                        "Cryonics", "Dance", "Bitcoin", "Startup", "Android")
    }
}
