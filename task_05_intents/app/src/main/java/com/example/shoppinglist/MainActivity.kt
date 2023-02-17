package com.example.shoppinglist

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import io.realm.kotlin.query.RealmResults
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var db : Database
    private lateinit var cartDb : Cart

    override fun onDestroy() {
        super.onDestroy()
        db.closeRealm()
        Cart.closeRealm()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!::db.isInitialized)
            db = Database()
//        if (!::cartDb.isInitialized)
//            cartDb = Cart()
        val api = ShopApi()
        api.getDataFromSite()?.let { setUpShop(it) }
        addItemToCart()
        setUpUI()
    }

    private fun setUpUI(){
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
            for ((i, item:Item) in Cart.getAllItems().withIndex()) {
                intent.putExtra("item_name_$i", item.name)
                intent.putExtra("item_number_$i", item.number)
            }
            startActivity(intent)
        }
    }

    private fun setUpShop(jsonObject: JSONObject) {
        db.deleteAll()
        setCategories(jsonObject.getJSONObject("categories"))
        setProducts(jsonObject.getJSONArray("products"))
//        val sportTools: Category = Category("Sport Tools", "Great for health and entertainment")
//        val buildingTools: Category =
//            Category("Building Tools", "Not great for health and entertainment")
//        Database.writeCategory(sportTools)
//        Database.writeCategory(buildingTools)
//        Database.writeItem("Tennis rocket", sportTools, "For hitting tennis ball", "79.99")
//        Database.writeItem("Tennis ball", sportTools, "For being hit by tennis rocket", "4.99")
//        Database.writeItem("Boxing gloves", sportTools, "For hitting people", "69.99")
//        Database.writeItem("Baseball bat", sportTools, "For hitting baseball", "59.99")
//        Database.writeItem("Hammer", buildingTools, "For hitting", "29.99")
    }

    private fun setProducts(products: JSONArray){
        for (i in 0 until products.length()) {
            val item = products.get(i) as JSONObject
            val name = item.get("name") as String
            val description = item.get("description") as String
            val price = item.get("price") as Double
            db.writeItem(name,null,description,price.toBigDecimal().toPlainString())
        }
    }

    private fun setCategories(categories:JSONObject){
        val keys = categories.keys()
        while (keys.hasNext()){
            val key : String = keys.next()
            val category = categories.get(key)
            if (category is JSONObject){
                val name = category.get("name") as String
                val description = category.get("description") as String
                db.writeCategory(name,description)
            }
        }
    }

    private fun addItemToCart(){
        val bundle: Bundle = intent.extras ?: return
        val name = bundle.getString("item_name")
        val price = bundle.getString("item_price")
        if (name != null && price != null)
            Cart.writeItem(name, price)
    }
}


