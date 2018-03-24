package io.batteryteam.dropboxofthings

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_item_detail.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ItemDetailActivity : AppCompatActivity() {
	private lateinit var itemId: String

	override fun onCreate(savedInstanceState: Bundle?) {
		itemId = intent.getStringExtra(ITEM_ID)
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

		imgItem.setImageResource(item.resId)

		when (item.storageStatus) {
			"storage" -> {
				fab.setImageResource(R.drawable.ic_cloud_download)
				fab.setOnClickListener { _ ->
					AlertDialog.Builder(this)
							.setTitle("Retrieve item")
							.setMessage("Do you want to send the item back home from storage?")
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
			else -> {
				fab.setImageResource(R.drawable.ic_cloud_upload)
				fab.setOnClickListener { _ ->
					AlertDialog.Builder(this)
							.setTitle("Upload item")
							.setMessage("Do you want to send the item to storage?")
							.setNeutralButton("Cancel", null)
							.setPositiveButton("Send", { _, _ ->
								Log.d("ItemDetailActivity", itemId)
								doAsync {
									ApiService.storeItemAgain(itemId)
									uiThread {
										Toast.makeText(it, "Request sent", Toast.LENGTH_SHORT).show()
										it.finish()
										startActivity(Intent(it, MainActivity::class.java))
									}
								}
							}).show()
				}
			}
		}
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.menu_detail_item, menu)
		return super.onCreateOptionsMenu(menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			R.id.action_send -> {
				AlertDialog.Builder(this)
						.setTitle("Send Item")
						.setView(layoutInflater.inflate(R.layout.dialog_send_to_friend, null))
						.setNeutralButton("Cancel", null)
						.setPositiveButton("Send", { _, _ ->
							doAsync {
								ApiService.sendItem(itemId)
								uiThread {
									it.finish()
									startActivity(Intent(it, MainActivity::class.java))
								}
							}
						}).show()
			}
		}
		return super.onOptionsItemSelected(item)
	}

	companion object {
		const val ITEM_ID = "ITEM_ID"
	}
}
