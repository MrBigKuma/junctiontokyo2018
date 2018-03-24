package io.batteryteam.dropboxofthings

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.batteryteam.dropboxofthings.MarketItemFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_market_item.view.*

class MarketItemRecyclerViewAdapter(var values: List<TerradaItem>, private val listener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<MarketItemRecyclerViewAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context)
				.inflate(R.layout.fragment_market_item, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = values[position]
		holder.item = item
		holder.contentView.setImageResource(item.resId)
		holder.priceView.text = "${item.priceEth} ETH"

		holder.view.setOnClickListener({
			if (null != listener) {
				listener.onListFragmentInteraction(item)
			}
		})
	}

	override fun getItemCount(): Int {
		return values.size
	}

	inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
		val contentView: ImageView = view.content as ImageView
		val priceView: TextView = view.txtPrice as TextView
		var item: TerradaItem? = null
	}
}
