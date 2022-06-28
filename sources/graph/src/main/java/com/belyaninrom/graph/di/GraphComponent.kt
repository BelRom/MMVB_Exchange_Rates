package com.belyaninrom.graph.di

import com.belyaninrom.core_api.mediator.ProvidersFacade
import com.belyaninrom.graph.GraphFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [ProvidersFacade::class]
)
interface GraphComponent {

    companion object {

        fun create(providersFacade: ProvidersFacade): GraphComponent {
            return DaggerGraphComponent.builder()
                .providersFacade(providersFacade)
                .build()
        }
    }

    fun inject(homeFragment: GraphFragment)
}