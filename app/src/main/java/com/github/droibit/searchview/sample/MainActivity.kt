package com.github.droibit.searchview.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
          .replace(R.id.content, MainFragment.newInstance())
          .commitNow()
    }
  }

  override fun onBackPressed() {
    Log.d(TAG,"#onBackPressed")
    val listener = supportFragmentManager.findFragmentById(R.id.content) as? OnBackPressListener
    if (listener?.onBackPressed() == false) {
      super.onBackPressed()
    }
  }

  companion object {

    private val TAG = MainActivity::class.java.simpleName
  }
}
