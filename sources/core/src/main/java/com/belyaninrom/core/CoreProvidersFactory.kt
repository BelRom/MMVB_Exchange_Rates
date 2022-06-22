package com.belyaninrom.core

import com.belyaninrom.core_api.database.DatabaseProvider
import com.belyaninrom.core_api.mediator.AppProvider
import com.belyaninrom.coreimpl.DaggerDatabaseComponent

object CoreProvidersFactory {

    fun createDatabaseBuilder(appProvider: AppProvider): DatabaseProvider {
        return DaggerDatabaseComponent.builder().appProvider(appProvider).build()
    }
}