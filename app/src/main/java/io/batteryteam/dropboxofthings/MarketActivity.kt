package io.batteryteam.dropboxofthings

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MarketActivity : AppCompatActivity(), MarketItemFragment.OnListFragmentInteractionListener {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_market_activity)
	}

	override fun onListFragmentInteraction(item: TerradaItem) {
		AlertDialog.Builder(this)
				.setTitle("Buy Item")
				.setMessage("Do you want to buy ${item.name} for ${item.priceEth} ETH")
				.setNeutralButton("Cancel", null)
				.setPositiveButton("Buy", { _, _ ->
					doAsync {
						ApiService.buyItem(item.id)
						ApiService.buyOnBlockChain(item.id)
						uiThread {
							val fragment = supportFragmentManager.findFragmentById(R.id.fragmentMarket) as MarketItemFragment
							fragment.reloadItems()
						}
					}
				}).show()

	}
}
