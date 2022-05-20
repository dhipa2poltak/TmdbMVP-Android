package com.dpfht.tmdbmvp.base

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import javax.inject.Inject

open class BaseFragment: Fragment() {

  @Inject
  lateinit var prgDialog: AlertDialog
}
