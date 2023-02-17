package com.example.shoppinglist


import org.json.JSONObject
import org.json.JSONTokener
import java.lang.Thread.sleep
import java.net.URL
import kotlin.concurrent.thread

class ShopApi {
    private val url: String = "https://romanbuhaitsov.github.io/shop.json"

    fun getDataFromSite(): JSONObject? {
        var jsonObject: JSONObject? = null
        thread(start = true) {
            val apiResponse = URL(url).readText()
            jsonObject= JSONTokener(apiResponse).nextValue() as JSONObject
            sleep(1000)
        }.join()
        return jsonObject
    }
}