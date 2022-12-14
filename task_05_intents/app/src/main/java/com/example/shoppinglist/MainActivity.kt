package com.example.shoppinglist

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.query.RealmResults

class MainActivity : AppCompatActivity() {

    override fun onDestroy() {
        super.onDestroy()
        Database.closeRealm()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpShop()

        val items: RealmResults<Item> = Database.getAllItems()
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
        }
    }

    private fun setUpShop() {
        Database.deleteAll()
        val sportTools: Category = Category("Sport Tools", "Great for health and entertainment")
        val buildingTools: Category =
            Category("Building Tools", "Not great for health and entertainment")
        Database.writeCategory(sportTools)
        Database.writeCategory(buildingTools)
        Database.writeItem("Tennis rocket", sportTools, "For hitting tennis ball", "79.99")
        Database.writeItem("Tennis ball", sportTools, "For being hit by tennis rocket", "4.99")
        Database.writeItem("Boxing gloves", sportTools, "For hitting people", "69.99")
        Database.writeItem("Baseball bat", sportTools, "For hitting baseball", "59.99")
        Database.writeItem("Hammer", buildingTools, "For hitting", "29.99")
    }
}


