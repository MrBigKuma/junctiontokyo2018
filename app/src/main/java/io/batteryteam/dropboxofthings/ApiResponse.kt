package io.batteryteam.dropboxofthings

import com.google.gson.annotations.SerializedName


class ApiResponse {
	@SerializedName("results")
	var results: List<TerradaItem> = emptyList()
}