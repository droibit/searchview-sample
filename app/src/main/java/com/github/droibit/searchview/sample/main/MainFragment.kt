package com.github.droibit.searchview.sample.main

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.github.droibit.searchview.sample.DetailFragment
import com.github.droibit.searchview.sample.OnBackPressListener
import com.github.droibit.searchview.sample.R
import com.github.droibit.searchview.sample.R.drawable
import com.github.droibit.searchview.sample.R.layout
import com.github.droibit.searchview.sample.R.menu
import com.github.droibit.searchview.sample.SearchViewController
import com.github.droibit.searchview.sample.TextListAdapter
import com.github.droibit.searchview.sample.texts
import kotlinx.android.synthetic.main.fragment_main.contentOverlay
import kotlinx.android.synthetic.main.fragment_main.list
import kotlinx.android.synthetic.main.fragment_main.toolbar

class MainFragment : Fragment(), OnBackPressListener {

  private lateinit var listAdapter: TextListAdapter

  private lateinit var searchViewController: SearchViewController

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View = inflater.inflate(layout.fragment_main, container, false)

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
          requireContext(),
          texts
      ) {
        requireFragmentManager().beginTransaction()
            .add(
                R.id.content,
                DetailFragment.newInstance(it)
            )
            .addToBackStack(null)
            .commit()
      }
          .also {
            adapter = it
          }
    }

    toolbar.inflateMenu(menu.search)
    toolbar.setNavigationOnClickListener {
      if (!searchViewController.isIconified) {
        searchViewController.close()
      }
    }
    val searchItem = toolbar.menu.findItem(R.id.action_search)
    searchViewController = SearchViewController(
        searchView = searchItem.actionView as SearchView,
        overlayView = contentOverlay,
        searchClickListener = {
          toolbar.navigationIcon =
              ContextCompat.getDrawable(
                  requireContext(),
                  drawable.ic_arrow_back
              )
//        toolbar.setBackgroundColor(Color.BLUE)
        },
        closeListener = {
          listAdapter.updateTexts(texts)
          toolbar.navigationIcon = ContextCompat.getDrawable(
              requireContext(),
              drawable.ic_menu
          )
//        toolbar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        },
        submitListener = { query ->
          listAdapter.updateTexts(
              texts.filter { it.contains(query, ignoreCase = true) }
          )
          hideKeyboard()
          view.requestFocus()
        }
    )
  }

  private fun hideKeyboard() {
    val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(searchViewController.windowToken, 0)
  }

  override fun onBackPressed(): Boolean = searchViewController.close()

  companion object {

    fun newInstance() = MainFragment()
  }
}