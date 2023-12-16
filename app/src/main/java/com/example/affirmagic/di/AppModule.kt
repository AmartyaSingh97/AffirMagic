package com.example.affirmagic.di

import android.content.Context
import com.example.affirmagic.db.database.Database
import com.example.affirmagic.remote.ApiService
import com.example.affirmagic.remote.RemoteDataSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

   @Singleton
   @Provides
   fun provideRetrofit(): Retrofit = Retrofit.Builder()
       .baseUrl("https://m67m0xe4oj.execute-api.us-east-1.amazonaws.com/prod/")
       .addConverterFactory(GsonConverterFactory.create())
       .build()

   @Provides
   fun provideGson(): Gson = GsonBuilder().create()


   @Provides
   fun provideAffirmationsApi(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

   @Singleton
   @Provides
   fun provideRemoteDataSource(apiService: ApiService) = RemoteDataSource(apiService)

   @Singleton
   @Provides
   fun provideDatabase(@ApplicationContext appContext: Context) = Database.getDatabase(appContext)

   @Singleton
   @Provides
   fun provideAffirmationsDao(db: Database) = db.AffirmationsDao()

   @Singleton
   @Provides
   fun provideRepository(remoteDataSource: RemoteDataSource,
                         localDataSource: com.example.affirmagic.db.dao.AffirmationsDao
   ) =
       com.example.affirmagic.repository.AffirmationsRepository(localDataSource, remoteDataSource)

}