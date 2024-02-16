package com.example.movieproject.di

import android.content.Context
import androidx.room.Room
import com.example.movieproject.api.ApiServices
import com.example.movieproject.db.FavoritesDatabase
import com.example.movieproject.utils.Constants.API_KEY
import com.example.movieproject.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor{
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }


    @Provides
    fun provideOKHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .addHeader("accept", "application/json")
                .header("Authorization", "Bearer $API_KEY")

            val request = requestBuilder.build()
            chain.proceed(request)

        }

        httpClient.addNetworkInterceptor(loggingInterceptor)

        return httpClient.build()
    }


    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit{
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

    }


    @Provides
    fun provideApiService(retrofit : Retrofit) : ApiServices{
        return retrofit.create(ApiServices::class.java)
    }

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,FavoritesDatabase::class.java,"favoritesDb").build()

    @Singleton
    @Provides
    fun provideDao(db : FavoritesDatabase) = db.getFavDao()
}