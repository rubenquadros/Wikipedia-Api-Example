package com.rubenquadros.epicarticles.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rubenquadros.epicarticles.factory.ViewModelFactory
import javax.inject.Inject

abstract class BaseActivity: AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.configureDagger()
    }

    private fun configureDagger() = (this.application as BaseApplication).appComponent.inject(this)
}