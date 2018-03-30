package com.github.droibit.searchview.sample

import android.os.IBinder
import android.support.v7.widget.SearchView
import android.support.v7.widget.SearchView.OnQueryTextListener
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.github.droibit.searchview.sample.R.drawable

class SearchViewController(
  private val searchView: SearchView,
  private val overlayView: View,
  private val searchClickListener: () -> Unit,
  private val closeListener: () -> Unit,
  private val submitListener: (String) -> Unit
) {

  private val searchEditText: EditText =
    searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)

  val windowToken: IBinder get() = searchEditText.windowToken

  val isIconified get() = searchView.isIconified

  init {
    searchView.apply {
      findViewById<ImageView>(android.support.v7.appcompat.R.id.search_close_btn)
          .setImageResource(drawable.ic_close)

      setOnSearchClickListener {
        searchClickListener.invoke()
      }

      setOnCloseListener {
        Log.d(TAG, "#onClose()")
        searchView.setQuery("", false)
        closeListener.invoke()
        false
      }

      setOnQueryTextListener(object : OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
          Log.d(TAG, "#onQueryTextSubmit(query=$query)")
          submitListener.invoke(query)
          searchEditText.clearFocus()
          return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
          Log.d(TAG, "#onQueryTextChange(newText=$newText)")
          return true
        }
      })
    }

    searchEditText.setOnFocusChangeListener { _, hasFocus ->
      Log.d(TAG, "#searchEditText#setOnFocusChangeListener(hasFocus=$hasFocus)")
      overlayView.visibility = if (hasFocus) View.VISIBLE else View.GONE
    }

    overlayView.setOnClickListener { close() }
  }

  fun close(): Boolean {
    Log.d(TAG, "#closeSearchView()")
    return if (searchView.isIconified) false else {
      searchView.setQuery("", false)
      searchView.isIconified = true
      true
    }
  }

  companion object {

    private val TAG = SearchViewController::class.java.simpleName
  }
}