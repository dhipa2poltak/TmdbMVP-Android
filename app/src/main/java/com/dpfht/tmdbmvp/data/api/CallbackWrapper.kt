package com.dpfht.tmdbmvp.data.api

import com.dpfht.tmdbmvp.util.ErrorUtil
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException
import java.io.IOException

abstract class CallbackWrapper<T>: DisposableObserver<T>() {

  override fun onNext(responseBody: T) {
    if (responseBody != null) {
      onSuccessCall(responseBody)
    } else {
      onErrorCall("null response body")
    }
  }

  override fun onError(e: Throwable) {
    when (e) {
      is HttpException -> {
        val response = e.response()
        val errorResponse = response?.let { ErrorUtil.parseApiError(it) }
        onErrorCall(errorResponse?.statusMessage ?: "")
      }
      is IOException -> {
        onErrorCall("error in connection")
      } else -> {
        onErrorCall("error in conversion")
      }
    }
  }

  override fun onComplete() {}

  protected abstract fun onSuccessCall(responseBody: T)

  protected abstract fun onErrorCall(message: String)

  protected abstract fun onCancelCall()
}
