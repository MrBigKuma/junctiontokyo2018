package io.batteryteam.dropboxofthings

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
		ItemFragment.OnListFragmentInteractionListener {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		setSupportActionBar(toolbar)

		fab.setOnClickListener { _ ->
			startActivity(Intent(this, RegisterItemActivity::class.java))
		}

		val toggle = ActionBarDrawerToggle(
				this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
		drawer_layout.addDrawerListener(toggle)
		toggle.syncState()

		nav_view.setNavigationItemSelectedListener(this)
	}

	override fun onBackPressed() {
		if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
			drawer_layout.closeDrawer(GravityCompat.START)
		} else {
			super.onBackPressed()
		}
	}

	override fun onNavigationItemSelected(item: MenuItem): Boolean {
		// Handle navigation view item clicks here.
		when (item.itemId) {
			R.id.nav_data_usage -> startActivity(Intent(this, DataUsageActivity::class.java))

			R.id.nav_store -> startActivity(Intent(this, StoreActivity::class.java))

			R.id.nav_market -> startActivity(Intent(this, MarketActivity::class.java))
		}

		drawer_layout.closeDrawer(GravityCompat.START)
		return true
	}

	override fun onListFragmentInteraction(item: TerradaItem) {
		val intent = Intent(this, ItemDetailActivity::class.java)
		intent.putExtra(ItemDetailActivity.ITEM_ID, item.id)
		startActivity(intent)
	}
}
