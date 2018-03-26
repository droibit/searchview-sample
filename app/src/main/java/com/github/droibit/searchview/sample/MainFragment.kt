package com.github.droibit.searchview.sample

import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.support.v7.widget.SearchView.OnQueryTextListener
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import kotlinx.android.synthetic.main.fragment_main.list
import kotlinx.android.synthetic.main.fragment_main.toolbar

class MainFragment : Fragment(), OnBackPressListener {

  private lateinit var searchView: SearchView

  private val searchEditText: EditText by lazy {
    searchView.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text)
  }

  private lateinit var listAdapter: TextListAdapter

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View = inflater.inflate(R.layout.fragment_main, container, false)

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)

    list.apply {
      setHasFixedSize(true)
      addItemDecoration(
          DividerItemDecoration(
              requireContext(), (layoutManager as LinearLayoutManager).orientation
          )
      )

      listAdapter = TextListAdapter(
          LayoutInflater.from(requireContext()),
          texts
      ).also {
        adapter = it
      }
    }

    toolbar.inflateMenu(R.menu.main)
    val searchItem = toolbar.menu.findItem(R.id.action_search)
    searchView = searchItem.actionView as SearchView
    searchView.apply {
      searchView.findViewById<ImageView>(android.support.v7.appcompat.R.id.search_close_btn)
          .setImageResource(R.drawable.ic_close)
//      searchEditText.also {
//            it.hint = "Filter"
//            it.setTextColor(Color.BLACK)
//            it.setHintTextColor(Color.parseColor("#88444444"))
//          }

      setOnSearchClickListener {
        Log.d(TAG, "#onSearchClick()")
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back)
//        toolbar.setBackgroundColor(Color.BLUE)
      }
      setOnCloseListener {
        Log.d(TAG, "#onClose()")
        searchView.setQuery("", false)
        listAdapter.updateTexts(texts)
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_menu)
//        toolbar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        false
      }
      setOnQueryTextListener(object : OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
          Log.d(TAG, "#onQueryTextSubmit(query=$query)")
          listAdapter.updateTexts(
              texts.filter { it.contains(query, ignoreCase = true) }
          )
          val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
          imm.hideSoftInputFromWindow(searchEditText.windowToken, 0)
          return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
          Log.d(TAG, "#onQueryTextChange(newText=$newText)")
          return true
        }
      })
    }

    toolbar.setNavigationOnClickListener {
      Log.d(TAG, "SearchView#isIconified=${searchView.isIconified}")
      closeSearchView()
    }
  }

  override fun onBackPressed(): Boolean = closeSearchView()

  private fun closeSearchView(): Boolean {
    Log.d(TAG, "#closeSearchView()")
    return if (searchView.isIconified) false else {
      searchView.setQuery("", false)
      searchView.isIconified = true
      true
    }
  }

  companion object {

    // ref. https://randomwordgenerator.com/
    private val texts = listOf(
        "fail",
        "trustee",
        "innovation",
        "respect",
        "mainstream",
        "glass",
        "vnightmare",
        "press",
        "joint",
        "mature",
        "dome",
        "rain",
        "reservoir",
        "organisation",
        "door",
        "collar",
        "afford",
        "excuse",
        "reliable",
        "frog",
        "marketing",
        "merit",
        "borrow",
        "carriage",
        "improve",
        "computing",
        "vdistortion",
        "vgasp",
        "happen",
        "foundation"
    )

    private val TAG = MainFragment.javaClass.simpleName

    fun newInstance() = MainFragment()
  }
}