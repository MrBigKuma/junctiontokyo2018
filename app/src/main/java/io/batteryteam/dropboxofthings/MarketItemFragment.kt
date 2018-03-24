package io.batteryteam.dropboxofthings

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_item_list.*
import kotlinx.android.synthetic.main.fragment_item_list.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MarketItemFragment : Fragment() {
	private var listener: OnListFragmentInteractionListener? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?): View? {
		val view = inflater.inflate(R.layout.fragment_market_item_list, container, false)

		// Set the adapter
		if (view.list is RecyclerView) {
			val recyclerView = view.list
			recyclerView.layoutManager = GridLayoutManager(context, 4)
			recyclerView.adapter = MarketItemRecyclerViewAdapter(emptyList(), listener)
		}
		return view
	}

	override fun onStart() {
		super.onStart()

		reloadItems()
	}

	fun reloadItems() {
		progressBar.visibility = View.VISIBLE
		doAsync {
			progressBar.visibility = View.GONE
			val marketItems = ApiService.getMarketItems()
			marketItems.forEach {
				it.resId = ItemRepo.imgResIds[(it.id.substring(5).toInt() % ItemRepo.imgResIds.size)]
			}
			uiThread {
				val v = view?.list as RecyclerView
				val adapter = v.adapter as MarketItemRecyclerViewAdapter
				adapter.values = marketItems
				adapter.notifyDataSetChanged()
			}
		}
	}

	override fun onAttach(context: Context?) {
		super.onAttach(context)
		if (context is OnListFragmentInteractionListener) {
			listener = context
		} else {
			throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
		}
	}

	override fun onDetach() {
		super.onDetach()
		listener = null
	}

	interface OnListFragmentInteractionListener {
		fun onListFragmentInteraction(item: TerradaItem)
	}
}
