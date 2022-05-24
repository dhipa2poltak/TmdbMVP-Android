package com.dpfht.tmdbmvp.di

import com.dpfht.tmdbmvp.BuildConfig
import com.dpfht.tmdbmvp.Config
import com.dpfht.tmdbmvp.rest.RestService
import dagger.Module
import dagger.Provides
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

  @Singleton
  @Provides
  fun provideRestService(retrofit: Retrofit): RestService {
    return retrofit.create(RestService::class.java)
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
  fun provideClient(
    certificatePinner: CertificatePinner,
    httpLoggingInterceptor: HttpLoggingInterceptor
  ): OkHttpClient {
    val httpClientBuilder = OkHttpClient()
      .newBuilder()
      .certificatePinner(certificatePinner)

    if (!httpClientBuilder.interceptors().contains(httpLoggingInterceptor) && BuildConfig.DEBUG) {
      httpClientBuilder.addInterceptor(httpLoggingInterceptor)
    }

    httpClientBuilder.addInterceptor(object : Interceptor {
      override fun intercept(chain: Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
          .addQueryParameter("api_key", Config.API_KEY)
          .build()

        val requestBuilder = original.newBuilder()
          .url(url)
          .method(original.method, original.body)

        val request = requestBuilder.build()

        return chain.proceed(request)
      }
    })

    return httpClientBuilder.build()
  }

  @Singleton
  @Provides
  fun provideCertificatePinner(): CertificatePinner {
    return CertificatePinner.Builder()
      .add("api.themoviedb.org", "sha256/oD/WAoRPvbez1Y2dfYfuo4yujAcYHXdv1Ivb2v2MOKk=")
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
}