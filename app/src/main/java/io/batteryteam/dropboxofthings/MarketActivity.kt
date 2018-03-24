package io.batteryteam.dropboxofthings

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MarketActivity : AppCompatActivity(), MarketItemFragment.OnListFragmentInteractionListener {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_market_activity)
	}

	override fun onListFragmentInteraction(item: TerradaItem) {
		// TODO: add block chain calling code here
	}
}
