package com.example.shoppinglist

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext

const val CHANNEL_ID = "CHANNEL_ID"

class MainActivity() : AppCompatActivity(), CoroutineScope {
    private lateinit var db: Database
    private lateinit var cartDb : Cart
    private lateinit var cartController : CartController
    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        db.closeRealm()
        cartDb.closeRealm()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()
        if (!::db.isInitialized) {
            db = Database()
        }
        if (!::cartDb.isInitialized) {
            cartDb = Cart(this)
        }
        if(!::cartController.isInitialized) {
            cartController = CartController(cartDb, this)
        }



        launch {
            val bundle: Bundle? = intent.extras
            val operation = bundle?.getString("operation")
            if (operation!=null){
                addItemToCart()
                return@launch
            }
            val result = generalSetup()

            onResult(result)
        }

    }

    private fun onResult(result: String) {
        println(result)
    }

    private suspend fun generalSetup(): String {
        try {
            val api = ShopApi()
            api.getDataFromSite()?.let { setUpShop(it) }
            addItemToCart()
            setUpUI()
        } catch (e: java.lang.Exception) {
            return "fail";
        }
        return "success"
    }

    private fun setUpUI() {
        val items: RealmResults<Item> = db.getAllItems()
        val arrayAdapter: ArrayAdapter<*> =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        val itemsList = findViewById<ListView>(R.id.items_id)
        itemsList.adapter = arrayAdapter
        itemsList.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position) as Item
            intent = Intent(this, ItemActivity::class.java)
            intent.putExtra("item_name", selectedItem.name)
            intent.putExtra("item_description", selectedItem.description)
            intent.putExtra("item_price", selectedItem.price)
            startActivity(intent)
        }
        val cartButton = findViewById<Button>(R.id.cartButton)
        cartButton.setOnClickListener {
            intent = Intent(this, CartActivity::class.java)
            for ((i, item: Item) in cartDb.getAllItems().withIndex()) {
                intent.putExtra("item_name_$i", item.name)
                intent.putExtra("item_number_$i", item.number)
            }
            startActivity(intent)
        }
    }

    private suspend fun setUpShop(jsonObject: JSONObject) {
        db.deleteAll()
        setCategories(jsonObject.getJSONObject("categories"))
        setProducts(jsonObject.getJSONArray("products"))
    }

    private suspend fun setProducts(products: JSONArray) {
        for (i in 0 until products.length()) {
            val item = products.get(i) as JSONObject
            val name = item.get("name") as String
            val description = item.get("description") as String
            val price = item.get("price") as Double
            db.writeItem(name, null, description, price.toBigDecimal().toPlainString())
        }
    }

    private suspend fun setCategories(categories: JSONObject) {
        val keys = categories.keys()
        while (keys.hasNext()) {
            val key: String = keys.next()
            val category = categories.get(key)
            if (category is JSONObject) {
                val name = category.get("name") as String
                val description = category.get("description") as String
                db.writeCategory(name, description)
            }
        }
    }

    private suspend fun addItemToCart() {
        val bundle: Bundle = intent.extras ?: return
        val name = bundle.getString("item_name")
        val price = bundle.getString("item_price")
        if (name != null && price != null)
            cartController.writeItem(name, price)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel1"
            val descriptionText = "description1"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}




