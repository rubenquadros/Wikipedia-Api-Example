package com.rubenquadros.epicarticles.di.component

import com.rubenquadros.epicarticles.base.BaseActivity
import com.rubenquadros.epicarticles.di.module.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, DbModule::class,
                        RepositoryModule::class, RxJavaModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(baseActivity: BaseActivity)
}