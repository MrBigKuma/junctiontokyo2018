package io.batteryteam.dropboxofthings

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_item_detail.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ItemDetailActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		val itemId = intent.getStringExtra(ITEM_ID)
		val item = ItemRepo.ITEMS.find { it.id == itemId }
				?: run {
					this.finish()
					startActivity(Intent(this, MainActivity::class.java))
					return
				}

		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_item_detail)
		setSupportActionBar(toolbar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		supportActionBar?.title = item.name

		fab.setOnClickListener { view ->
			AlertDialog.Builder(this)
					.setTitle("Retrieve item")
					.setMessage("Do you want to send the item back home from warehouse?")
					.setNeutralButton("Cancel", null)
					.setPositiveButton("retrieve", { _, _ ->
						Log.d("ItemDetailActivity", itemId)
						doAsync {
							ApiService.issuingItem(itemId)
							uiThread {
								Toast.makeText(it, "Request sent", Toast.LENGTH_SHORT).show()
								it.finish()
								startActivity(Intent(it, MainActivity::class.java))
							}
						}
					}).show()
		}
	}

	companion object {
		const val ITEM_ID = "ITEM_ID"
	}
}
