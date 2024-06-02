package com.c241ps093.ezfarm.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c241ps093.ezfarm.ui.detail.DetailViewModel
import com.c241ps093.ezfarm.ui.home.HomeViewModel
import com.c241ps093.ezfarm.viewmodel.Injection
import com.c241ps093.ezfarm.viewmodel.SplashViewModel

class ViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(SplashViewModel::class.java)){
            return SplashViewModel(
                ezFarmRepository = Injection.injectRepository(context = mApplication.applicationContext)
            ) as T
        } else if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(
                ezFarmRepository = Injection.injectRepository(context = mApplication.applicationContext)
            ) as T
        } else if(modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(
                ezFarmRepository = Injection.injectRepository(context = mApplication.applicationContext)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}