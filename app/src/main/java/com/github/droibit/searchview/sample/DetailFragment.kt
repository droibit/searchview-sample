package com.github.droibit.searchview.sample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_detail.text

private const val ARG_TEXT = "ARG_TEXT"

class DetailFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View = inflater.inflate(R.layout.fragment_detail, container, false)

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)

    text.text = requireNotNull(arguments).getString(ARG_TEXT)
  }

  companion object {

    fun newInstance(text: String) = DetailFragment().apply {
      arguments = Bundle(1).apply {
        putString(ARG_TEXT, text)
      }
    }
  }
}