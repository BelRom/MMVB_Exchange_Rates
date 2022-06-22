package com.belyaninrom.core_api.mediator

import com.belyaninrom.core_api.mediator.ProvidersFacade

interface AppWithFacade {

    fun getFacade(): ProvidersFacade
}