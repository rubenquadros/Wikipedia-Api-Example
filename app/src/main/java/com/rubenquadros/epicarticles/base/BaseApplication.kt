package com.rubenquadros.epicarticles.base

import android.app.Application
import com.rubenquadros.epicarticles.di.component.AppComponent
import com.rubenquadros.epicarticles.di.component.DaggerAppComponent
import com.rubenquadros.epicarticles.di.module.ApiModule
import com.rubenquadros.epicarticles.di.module.DbModule
import com.rubenquadros.epicarticles.di.module.RepositoryModule
import com.rubenquadros.epicarticles.di.module.RxJavaModule
import com.rubenquadros.epicarticles.utils.ApplicationConstants

open class BaseApplication: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        this.appComponent = initDagger()
    }

    protected open fun initDagger(): AppComponent =
        DaggerAppComponent.builder()
            .apiModule(ApiModule(ApplicationConstants.BASE_URL, this))
            .dbModule(DbModule(this))
            .repositoryModule(RepositoryModule())
            .rxJavaModule(RxJavaModule())
            .build()
}