package com.belyaninrom.coreimpl

import com.belyaninrom.core_api.database.DatabaseProvider
import com.belyaninrom.core_api.mediator.AppProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [AppProvider::class],
    modules = [DatabaseModule::class]
)
interface DatabaseComponent : DatabaseProvider