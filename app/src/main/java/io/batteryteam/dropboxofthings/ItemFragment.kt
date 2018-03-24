package io.batteryteam.dropboxofthings

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_item_list.*
import kotlinx.android.synthetic.main.fragment_item_list.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ItemFragment : Fragment() {
	private var listener: OnListFragmentInteractionListener? = null

	private var filterOnCloudOnly: Boolean = false

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?): View? {
		val view = inflater.inflate(R.layout.fragment_item_list, container, false)

		// Set the adapter
		if (view.list is RecyclerView) {
			val recyclerView = view.list
			recyclerView.layoutManager = GridLayoutManager(context, 3)

			recyclerView.adapter = MyItemRecyclerViewAdapter(emptyList(), listener)

			filterOnCloudOnly = arguments?.getBoolean(ARG_FILTER_ON_CLOUD_ONLY) ?: false
		}
		return view
	}

	override fun onStart() {
		super.onStart()

		progressBar.visibility = View.VISIBLE
		doAsync {
			val items = ApiService.getItems()
			uiThread {
				progressBar.visibility = View.GONE
				ItemRepo.updateItems(items)
				Log.d("ItemFragment", "ItemRepo.updateItems ${items.size}")
				val recyclerView = list as RecyclerView
				val adapter = recyclerView.adapter as MyItemRecyclerViewAdapter
				val items = ItemRepo.ITEMS.filter {
					if (filterOnCloudOnly)
						it.storageStatus == "storage" && it.privacyStatus == "private"
					else
						true
				}
				adapter.values = items
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

	companion object {
		const val ARG_FILTER_ON_CLOUD_ONLY = "ARG_FILTER_ON_CLOUD_ONLY"
	}
}
