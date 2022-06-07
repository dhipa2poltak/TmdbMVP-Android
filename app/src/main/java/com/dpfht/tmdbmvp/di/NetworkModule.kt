package com.dpfht.tmdbmvp.di

import com.dpfht.tmdbmvp.BuildConfig
import com.dpfht.tmdbmvp.Config
import com.dpfht.tmdbmvp.data.api.AuthInterceptor
import com.dpfht.tmdbmvp.data.api.RestService
import com.dpfht.tmdbmvp.data.api.UnsafeOkHttpClient
import dagger.Module
import dagger.Provides
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

  @Singleton
  @Provides
  fun provideCertificatePinner(): CertificatePinner {
    return CertificatePinner.Builder()
      .add("api.themoviedb.org", "sha256/oD/WAoRPvbez1Y2dfYfuo4yujAcYHXdv1Ivb2v2MOKk=")
      .add("api.themoviedb.org", "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
      .add("api.themoviedb.org", "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
      .add("api.themoviedb.org", "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
      .build()
  }

  @Singleton
  @Provides
  fun httpLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return httpLoggingInterceptor
  }

  @Provides
  @Singleton
  fun provideClient(certificatePinner: CertificatePinner): OkHttpClient {
    if (BuildConfig.DEBUG) {
      return UnsafeOkHttpClient.getUnsafeOkHttpClient()
    }

    val httpClientBuilder = OkHttpClient()
      .newBuilder()
      .certificatePinner(certificatePinner)

    httpClientBuilder.addInterceptor(AuthInterceptor())

    return httpClientBuilder.build()
  }

  @Singleton
  @Provides
  fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .baseUrl(Config.API_BASE_URL)
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create())
      .client(client)
      .build()
  }

  @Singleton
  @Provides
  fun provideRestService(retrofit: Retrofit): RestService {
    return retrofit.create(RestService::class.java)
  }
}
