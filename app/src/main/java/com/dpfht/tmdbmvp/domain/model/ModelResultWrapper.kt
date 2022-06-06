package com.dpfht.tmdbmvp.domain.model

sealed class ModelResultWrapper<out T> {
  data class Success<out T>(val value: T): ModelResultWrapper<T>()
  data class ErrorResult(val message: String): ModelResultWrapper<Nothing>()
}
