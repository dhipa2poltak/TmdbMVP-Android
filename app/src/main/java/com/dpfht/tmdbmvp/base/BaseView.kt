package com.dpfht.tmdbmvp.base

interface BaseView {

  fun showLoadingDialog()
  fun hideLoadingDialog()
  fun showErrorMessage(message: String)
  fun showCanceledMessage()
}
