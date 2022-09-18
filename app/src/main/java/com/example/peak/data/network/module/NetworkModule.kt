package com.example.peak.data.network.module

import com.example.peak.data.network.PeakApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author yaya (@yahyalmh)
 * @since 17th September 2022
 */

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun providesRetrofit(httpClient: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .client(httpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providePeaKApi(retrofit: Retrofit): PeakApi = retrofit.create(PeakApi::class.java)


    companion object {
        const val BASE_URL = "http://10.0.2.2:5000"
//        const val BASE_URL = "localhost:5000"
        const val BASE_URL2 = "http://127.0.0.1:5000"
    }
}