package io.batteryteam.dropboxofthings

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_register_item.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime

class RegisterItemActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_register_item)
		supportActionBar?.setHomeButtonEnabled(true)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
		supportActionBar?.title = "Register item"

		btnDate.text = LocalDate.now().toString()
		val now = LocalDateTime.now()
		btnDate.setOnClickListener {
			DatePickerDialog.newInstance({ _, y, m, d ->
				btnDate.text = LocalDate.parse("$y-$m-$d").toString()
			}, now.year, now.monthOfYear, now.dayOfMonth)
					.show(fragmentManager, "WTF")

		}
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			R.id.action_register -> {
				val progressDialog = ProgressDialog.show(this, "Making storing request", "In progress...")
				doAsync {
					val itemId = ApiService.storeItem(TerradaItem(
							name = editTextName.text.toString()
					))

					uiThread {
						progressDialog.dismiss()
						Toast.makeText(it, "Made Storing request", Toast.LENGTH_LONG).show()
						startActivity(Intent(it, MainActivity::class.java))
					}
				}
			}
		}
		return super.onOptionsItemSelected(item)
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.menu_register_item, menu)
		return super.onCreateOptionsMenu(menu)
	}
}
