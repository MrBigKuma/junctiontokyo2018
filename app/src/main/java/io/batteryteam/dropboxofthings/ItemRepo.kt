package io.batteryteam.dropboxofthings

object ItemRepo {

	val ITEMS: MutableList<TerradaItem> = mutableListOf()
	val imgResIds = listOf<Int>(
			R.drawable.teddy1,
			R.drawable.teddy2,
			R.drawable.suit1,
			R.drawable.printer_3d1,
			R.drawable.printer_3d2,
			R.drawable.printer_3d3,
			R.drawable.thumnail_air_purifier,
			R.drawable.thumnail_chair,
			R.drawable.thumnail_cooker,
			R.drawable.thumnail_lamp,
			R.drawable.thumnail_nebulizer,
			R.drawable.thumnail_umbrella
	)

	fun updateItems(items: List<TerradaItem>) {
		ItemRepo.ITEMS.clear()
		items.map {
			it.resId = imgResIds[(it.id.substring(5).toInt() % imgResIds.size)]
		}
		ItemRepo.ITEMS.addAll(items)
	}
}
