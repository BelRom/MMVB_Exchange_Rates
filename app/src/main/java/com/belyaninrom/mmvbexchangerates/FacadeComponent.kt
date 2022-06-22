package com.green.habits

import android.app.Application
import com.belyaninrom.network.NetworkMoex
import com.belyaninrom.core_api.mediator.ProvidersFacade
import com.belyaninrom.mmvbexchangerates.App
import com.belyaninrom.network.NetworkMoexImpl
import com.green.coreapi.mediator.AppProvider
import dagger.Component

@Component(
    dependencies = [AppProvider::class,
        NetworkMoex::class],
)
interface FacadeComponent : ProvidersFacade {

    companion object {

        fun init(application: Application): FacadeComponent =
            DaggerFacadeComponent.builder()
                .appProvider(AppComponent.create(application))
                .networkMoex(NetworkMoexImpl())
//                .databaseProvider(CoreProvidersFactory.createDatabaseBuilder(AppComponent.create(application)))
                .build()
    }

    fun inject(app: App)
}