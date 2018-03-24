package io.batteryteam.dropboxofthings

import android.util.Log
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson


object ApiService {

	fun getItems(): List<TerradaItem> {
		val (_, _, result) = "$BASE_URL/item?oem_key=$OEM_KEY".httpGet().responseObject<ApiResponse>()
		Log.d("ApiService", result.toString())
		return result.get().results
	}

	/**
	 * @return item id
	 */
//	fun createItem(item: TerradaItem): String {
//		val (_, _, result) = "$BASE_URL/item?oem_key=$OEM_KEY".httpPost().body(gson.toJson(item)).responseObject<ApiResponse>()
//		Log.d("ApiService", result.toString())
//		return result.get().results[0].id
//	}

	fun storeItem(item: TerradaItem) {
		val (_, _, result) = "$BASE_URL/storing?oem_key=$OEM_KEY".httpPost(listOf(
				"customer_id" to CUSTOMER_ID,
				"common01" to item.name,
				"storing_address" to "Tokyo Storage 1",
				"issuing_address" to HOME_ADDRESS
		)).response()
		Log.d("ApiService", result.toString())
	}

	fun issuingItem(itemId: String) {
		val (_, _, result) = "$BASE_URL/issuing?oem_key=$OEM_KEY".httpPost(listOf(
				"item_id" to itemId
		)).response()
		Log.d("ApiService", result.toString())
	}

	private const val BASE_URL = "https://junction-tokyo.minikura.com/v1/minikura"
	private const val OEM_KEY = "4e6d869e84072317"
	private const val CUSTOMER_ID = "1" // TODO: replace for every demo application
	private const val HOME_ADDRESS = "223-0014 Tsunashima Letdo building 203"
	private val gson = Gson()
}