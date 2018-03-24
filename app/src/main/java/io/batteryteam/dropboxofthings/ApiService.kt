package io.batteryteam.dropboxofthings

import android.util.Log
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPatch
import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson


object ApiService {

	fun getItems(): List<TerradaItem> {
		val (_, _, result) = "$BASE_URL/item?oem_key=$OEM_KEY".httpGet().responseObject<ApiResponse>()
		Log.d("ApiService", result.toString())
		return result.get().results.filter { it.customerId == CUSTOMER_ID }
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

	fun storeItemAgain(itemId: String) {
		val (_, _, result) = "$BASE_URL/item".httpPatch(listOf(
				"oem_key" to OEM_KEY,
				"item_id" to itemId,
				"storage_status" to "storage"
		)).response()
		Log.d("ApiService", result.toString())
	}

	fun issuingItem(itemId: String) {
		val (_, _, result) = "$BASE_URL/issuing?oem_key=$OEM_KEY".httpPost(listOf(
				"item_id" to itemId
		)).response()
		Log.d("ApiService", result.toString())
	}

	fun deleteItem(itemId: String) {
		val (_, _, result) = "$BASE_URL/item".httpDelete(listOf(
				"oem_key" to OEM_KEY,
				"item_id" to itemId
		)).response()
		Log.d("ApiService", result.toString())
	}

	fun sendItem(itemId: String) {
		val (_, _, result) = "$BASE_URL/item".httpPatch(listOf(
				"oem_key" to OEM_KEY,
				"item_id" to itemId,
				"customer_id" to FRIEND1_ID
		)).response()
		Log.d("ApiService", result.toString())
	}

	private const val BASE_URL = "https://junction-tokyo.minikura.com/v1/minikura"
	private const val OEM_KEY = "4e6d869e84072317"
	private const val CUSTOMER_ID = "1" // TODO: replace for every demo application
	private const val FRIEND1_ID = "2" // TODO: replace for every demo application
	private const val HOME_ADDRESS = "223-0014 Tsunashima Letdo building 203"
	private val gson = Gson()
}