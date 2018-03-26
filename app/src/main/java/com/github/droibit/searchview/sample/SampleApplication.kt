package com.github.droibit.searchview.sample

import android.app.Application
import android.support.v7.app.AppCompatDelegate

class SampleApplication : Application() {

  override fun onCreate() {
    super.onCreate()
  }

  companion object {
    init {
      AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
  }
}