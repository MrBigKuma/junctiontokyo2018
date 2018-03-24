package io.batteryteam.dropboxofthings

import android.util.Log
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPatch
import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson
import kotlin.math.roundToInt


object ApiService {

	fun getItems(): List<TerradaItem> {
		val (_, _, result) = "$BASE_URL/item".httpGet(listOf(
				"oem_key" to OEM_KEY
		)).responseObject<ApiResponse>()
		Log.d("ApiService", result.toString())
		return result.get().results.filter { it.customerId == CUSTOMER_ID }
	}

	fun getMarketItems(): List<TerradaItem> {
		val (_, _, result) = "$BASE_URL/item".httpGet(listOf(
				"oem_key" to OEM_KEY
		)).responseObject<ApiResponse>()
		Log.d("ApiService", result.toString())
		return result.get().results
				.filter { it.privacyStatus == "public" }
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
		val (_, _, result) = "$BASE_URL/storing".httpPost(listOf(
				"oem_key" to OEM_KEY,
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
		val (_, _, result) = "$BASE_URL/issuing".httpPost(listOf(
				"oem_key" to OEM_KEY,
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

	fun publicItem(itemId: String, priceEth: String) {
		val (_, _, result) = "$BASE_URL/item".httpPatch(listOf(
				"oem_key" to OEM_KEY,
				"item_id" to itemId,
				"privacy_status" to "public",
				"common02" to priceEth
		)).response()
		Log.d("ApiService", result.toString())
	}

	fun buyItem(itemId: String) {
		val (_, _, result) = "$BASE_URL/item".httpPatch(listOf(
				"oem_key" to OEM_KEY,
				"item_id" to itemId,
				"privacy_status" to "private",
				"customer_id" to CUSTOMER_ID
		)).response()
		Log.d("ApiService", result.toString())
	}

	fun buyOnBlockChain(itemId: String) {
		val (_, _, result) = "$BLOCK_CHAIN_URL/buy".httpGet(listOf(
				"user_id" to CUSTOMER_ID,
				"item_id" to itemId
		)).response()
		Log.d("ApiService", result.toString())
	}

	fun sellOnBlockChain(itemId: String, priceEth: String) {
		val (_, _, result) = "$BLOCK_CHAIN_URL/sell".httpGet(listOf(
				"price" to ((priceEth.toDoubleOrNull() ?: 1.0) * 10e9).toInt(),
				"item_id" to itemId
		)).response()
		Log.d("ApiService", result.toString())
	}

	private const val BASE_URL = "https://junction-tokyo.minikura.com/v1/minikura"
	private const val BLOCK_CHAIN_URL = "http://104.199.135.32"
	private const val OEM_KEY = "4e6d869e84072317"
	private const val CUSTOMER_ID = "1" // TODO: replace for every demo application
	private const val FRIEND1_ID = "2" // TODO: replace for every demo application
	private const val HOME_ADDRESS = "223-0014 Tsunashima Letdo building 203"
	private val gson = Gson()
}