package io.batteryteam.dropboxofthings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_register_item.*
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime

class RegisterItemActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_register_item)
		supportActionBar?.setHomeButtonEnabled(true)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		supportActionBar?.setIcon(R.drawable.ic_arrow_back)

		btnDate.text = LocalDate.now().toString()
		val now = LocalDateTime.now()
		btnDate.setOnClickListener {
			DatePickerDialog.newInstance({ _, y, m, d ->
				btnDate.text = LocalDate.parse("$y-$m-$d").toString()
			}, now.year, now.monthOfYear, now.dayOfMonth)
					.show(fragmentManager, "WTF")

		}
	}

	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.menu_register_item, menu)
		return super.onCreateOptionsMenu(menu)
	}
}
