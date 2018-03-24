package io.batteryteam.dropboxofthings

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.widget.EditText
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AddItemToStoreActivity : AppCompatActivity(), ItemFragment.OnListFragmentInteractionListener {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_add_item_to_store)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

		val itemFragment = ItemFragment()
		val bundle = Bundle()
		bundle.putBoolean(ItemFragment.ARG_FILTER_ON_CLOUD_ONLY, true)
		itemFragment.arguments = bundle
		supportFragmentManager.beginTransaction()
				.add(R.id.fragmentSelectItemTo, itemFragment)
				.commit()
	}

	override fun onListFragmentInteraction(item: TerradaItem) {
		val editText = EditText(this)
		editText.inputType = InputType.TYPE_CLASS_NUMBER.or(InputType.TYPE_NUMBER_FLAG_DECIMAL)
		AlertDialog.Builder(this)
				.setTitle("Set ETH price for item")
				.setView(editText)
				.setNeutralButton("Cancel", null)
				.setPositiveButton("Put on Store", { _, _ ->
					doAsync {
						val priceEth = editText.text.toString()
						ApiService.publicItem(item.id, priceEth)
						ApiService.sellOnBlockChain(item.id, priceEth)
						uiThread {
							it.startActivity(Intent(it, StoreActivity::class.java))
						}
					}
				}).show()
	}
}
