package io.batteryteam.dropboxofthings

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_store.*

class StoreActivity : AppCompatActivity(), StoreItemFragment.OnListFragmentInteractionListener {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_store)

		fab.setOnClickListener { _ ->
			startActivity(Intent(this, AddItemToStoreActivity::class.java))
		}
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
	}

	override fun onListFragmentInteraction(item: TerradaItem) {
		// Do nothing
	}
}
