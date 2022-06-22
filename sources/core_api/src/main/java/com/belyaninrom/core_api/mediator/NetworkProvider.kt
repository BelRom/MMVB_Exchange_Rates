package com.belyaninrom.core_api.mediator

import com.belyaninrom.network.NetworkMoex

interface NetworkProvider {

    fun provideNetwork(): NetworkMoex
}