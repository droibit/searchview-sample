package com.github.droibit.searchview.sample

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.droibit.searchview.sample.TextListAdapter.ViewHolder

typealias OnItemClickListener = (String) -> Unit

// ref. https://randomwordgenerator.com/
val texts = listOf(
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

class TextListAdapter(
  context: Context,
  texts: List<String>,
  private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ViewHolder>() {

  private val inflater = LayoutInflater.from(context)

  private val texts: MutableList<String> = ArrayList(texts)

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ViewHolder {
    val itemView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
    return ViewHolder(itemView).apply {
      itemView.setOnClickListener { itemClickListener.invoke(texts[adapterPosition]) }
    }
  }

  override fun getItemCount() = texts.size

  override fun onBindViewHolder(
    holder: ViewHolder,
    position: Int
  ) {
    holder.text.text = texts[position]
  }

  fun updateTexts(texts: List<String>) {
    this.texts.clear()
    this.texts.addAll(texts)
    this.notifyDataSetChanged()
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val text: TextView = itemView.findViewById(android.R.id.text1)
  }
}