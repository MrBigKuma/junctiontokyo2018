package io.batteryteam.dropboxofthings

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_item_detail.*

class ItemDetailActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_item_detail)
		setSupportActionBar(toolbar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		supportActionBar?.title = "Teddy bear"

		fab.setOnClickListener { view ->
			AlertDialog.Builder(this)
					.setTitle("Retrieve item")
					.setMessage("Do you want to send the item back home from warehouse?")
					.setNeutralButton("Cancel", null)
					.setPositiveButton("retrieve", { _, _ ->
						Toast.makeText(this, "Request sent", Toast.LENGTH_SHORT).show()
						startActivity(Intent(this, MainActivity::class.java))
					}).show()
		}
	}
}
