package com.belyaninrom.mmvbexchangerates

import android.app.Application
import com.belyaninrom.core_api.mediator.ProvidersFacade
import com.belyaninrom.core_api.mediator.AppWithFacade

class App: Application(), AppWithFacade {
    companion object {

        private var facadeComponent: FacadeComponent? = null
    }

    override fun getFacade(): ProvidersFacade {
        return facadeComponent ?: FacadeComponent.init(this).also {
            facadeComponent = it
        }
    }

    override fun onCreate() {
        super.onCreate()
        (getFacade() as FacadeComponent).inject(this)
    }
}