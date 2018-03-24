package io.batteryteam.dropboxofthings

import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import io.batteryteam.dropboxofthings.ItemFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_item.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * [RecyclerView.Adapter] that can display a [TerradaItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(private val mValues: List<TerradaItem>, private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context)
				.inflate(R.layout.fragment_item, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = mValues.get(position)
		holder.item = item
		holder.contentView.setImageResource(item.resId)
		when (item.storageStatus) {
			"non_storage", "leaving" -> holder.imgStatus.setImageResource(R.drawable.ic_cloud_off)
			"storage" -> holder.imgStatus.setImageResource(R.drawable.ic_cloud_done)
			"temporary_leaving" -> holder.imgStatus.setImageResource(R.drawable.ic_cloud_queue)
		}

		holder.view.setOnClickListener({
			if (null != mListener) {
				// Notify the active callbacks interface (the activity, if the
				// fragment is attached to one) that an item has been selected.
				mListener.onListFragmentInteraction(item)
			}
		})

		holder.view.setOnLongClickListener {
			AlertDialog.Builder(it.context)
					.setTitle("Delete item")
					.setPositiveButton("Delete", { _, _ ->
						doAsync {
							Log.d("ADAPTER", item.id)
							ApiService.deleteItem(item.id)
							val items = ApiService.getItems()
							uiThread {
								ItemRepo.updateItems(items)
								notifyDataSetChanged()
							}
						}
					}).show()
			true
		}
	}

	override fun getItemCount(): Int {
		return mValues.size
	}

	inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
		val contentView: ImageView = view.content as ImageView
		val imgStatus: ImageView = view.imgStatus as ImageView
		var item: TerradaItem? = null
	}
}
