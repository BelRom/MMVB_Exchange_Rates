package com.belyaninrom.network.di

import com.belyaninrom.network.MoexRestService
import com.belyaninrom.network.call_adapter_factory.NetworkResultCallAdapterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://iss.moex.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun gsonBuilder(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

     @Provides
     @Singleton
     fun interceptor(): HttpLoggingInterceptor {
         val logging = HttpLoggingInterceptor()
         logging.setLevel(HttpLoggingInterceptor.Level.BODY)
         return logging
     }

     @Provides
     @Singleton
     fun okHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
         val client = OkHttpClient.Builder()
             .addInterceptor(interceptor)
             .build()
         return client
     }

    @Provides
    @Singleton
    fun provideMoexRestService(retrofit :Retrofit): MoexRestService =
        retrofit.create(MoexRestService::class.java)
}