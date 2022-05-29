package com.dpfht.tmdbmvp.data.api

import com.dpfht.tmdbmvp.util.ErrorUtil
import io.reactivex.observers.DisposableObserver
import retrofit2.Response
import java.io.IOException

abstract class CallbackWrapper<T: Response<R>, R>: DisposableObserver<T>() {

  override fun onNext(t: T) {
    if (t.isSuccessful) {
      val responseBody = t.body()
      if (responseBody != null) {
        onSuccessCall(responseBody)
      } else {
        onErrorCall("null response body")
      }
    } else {
      val errorResponse = ErrorUtil.parseApiError(t)
      onErrorCall(errorResponse.statusMessage ?: "")
    }
  }

  override fun onError(e: Throwable) {
    onErrorCall(if (e is IOException) "error in connection" else "error in conversion")
  }

  override fun onComplete() {}

  protected abstract fun onSuccessCall(response: R)

  protected abstract fun onErrorCall(str: String)

  protected abstract fun onCancelCall()
}
