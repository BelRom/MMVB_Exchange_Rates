package com.belyaninrom.network.di

import com.belyaninrom.network.NetworkMoexImpl
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [NetworkModule::class]
)
interface NetworkComponent {
    companion object {
        fun create() = DaggerNetworkComponent.create()
    }

    fun inject(networkMoexImpl: NetworkMoexImpl)
}