package com.dpfht.tmdbmvp.util

import com.dpfht.tmdbmvp.rest.ErrorResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import java.nio.charset.Charset

object ErrorUtil {

  fun parseApiError(response: Response<*>): ErrorResponse {
    response.errorBody()?.source().let {
      val json = it?.readString(Charset.defaultCharset())
      val typeToken = object : TypeToken<ErrorResponse>() {}.type

      return Gson().fromJson(json, typeToken)
    }
  }
}
