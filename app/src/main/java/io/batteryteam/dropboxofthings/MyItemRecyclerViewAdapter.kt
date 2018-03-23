package io.batteryteam.dropboxofthings

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import io.batteryteam.dropboxofthings.ItemFragment.OnListFragmentInteractionListener
import io.batteryteam.dropboxofthings.dummy.DummyContent.DummyItem
import kotlinx.android.synthetic.main.fragment_item.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(private val mValues: List<DummyItem>, private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context)
				.inflate(R.layout.fragment_item, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = mValues.get(position)
		holder.mItem = item
		holder.mContentView.setImageResource(R.drawable.teddy1)

		holder.mView.setOnClickListener({
			if (null != mListener) {
				// Notify the active callbacks interface (the activity, if the
				// fragment is attached to one) that an item has been selected.
				mListener.onListFragmentInteraction(item)
			}
		})
	}

	override fun getItemCount(): Int {
		return mValues.size
	}

	inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
		val mContentView: ImageView = mView.content as ImageView
		var mItem: DummyItem? = null
	}
}
