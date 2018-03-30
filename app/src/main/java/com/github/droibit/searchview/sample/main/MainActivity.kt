package com.github.droibit.searchview.sample.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.droibit.searchview.sample.OnBackPressListener
import com.github.droibit.searchview.sample.R.id
import com.github.droibit.searchview.sample.R.layout

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)

    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
          .replace(
              id.content,
              MainFragment.newInstance()
          )
          .commitNow()
    }
  }

  override fun onBackPressed() {
    Log.d(TAG,"#onBackPressed")
    val listener = supportFragmentManager.findFragmentById(
        id.content
    ) as? OnBackPressListener
    if (listener?.onBackPressed() != true) {
      super.onBackPressed()
    }
  }

  companion object {

    private val TAG = MainActivity::class.java.simpleName
  }
}
