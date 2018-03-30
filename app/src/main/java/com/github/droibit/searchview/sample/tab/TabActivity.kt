package com.github.droibit.searchview.sample.tab

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.droibit.searchview.sample.OnBackPressListener
import com.github.droibit.searchview.sample.R
import com.github.droibit.searchview.sample.R.id

class TabActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_tab)

    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
          .replace(R.id.content, TabFragment.newInstance())
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

    private val TAG = TabActivity::class.java.simpleName
  }
}
