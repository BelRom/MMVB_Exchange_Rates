package com.belyaninrom.mmvbexchangerates

import android.app.Application
import com.belyaninrom.core.CoreProvidersFactory
import com.belyaninrom.core_api.database.DatabaseProvider
import com.belyaninrom.network.NetworkMoex
import com.belyaninrom.core_api.mediator.ProvidersFacade
import com.belyaninrom.network.NetworkMoexImpl
import com.belyaninrom.core_api.mediator.AppProvider
import dagger.Component

@Component(
    dependencies = [AppProvider::class,
        DatabaseProvider::class,
        NetworkMoex::class],
)
interface FacadeComponent : ProvidersFacade {

    companion object {

        fun init(application: Application): FacadeComponent =
            DaggerFacadeComponent.builder()
                .appProvider(AppComponent.create(application))
                .networkMoex(NetworkMoexImpl())
                .databaseProvider(CoreProvidersFactory.createDatabaseBuilder(AppComponent.create(application)))
                .build()
    }

    fun inject(app: App)
}