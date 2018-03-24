package io.batteryteam.dropboxofthings

import com.google.gson.annotations.SerializedName

class TerradaItem(
		@SerializedName("item_id")
		var id: String = "",

		@SerializedName("customer_id")
		var customerId: String = "",

		@SerializedName("storage_status") // 'non_storage', 'storage', 'temporary_leaving', 'leaving'
		var storageStatus: String = "",

		@SerializedName("privacy_status") // public, private
		var privacyStatus: String = "",

		@SerializedName("storing_address")
		var storingAddress: String = "",

		@SerializedName("issuing_address")
		var issuingAddress: String = "",

		@SerializedName("common01")
		var name: String = "",

		@SerializedName("common02")
		var priceEth: String = "",

		var resId: Int = 0
)