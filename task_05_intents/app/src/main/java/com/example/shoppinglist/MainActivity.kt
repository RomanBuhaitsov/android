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
    private val db : Database = Database

    override fun onDestroy() {
        super.onDestroy()
        db.realm.close()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpShop()
        val itemList: RealmResults<Item> = db.getAllItems()
        val itemsList = findViewById<ListView>(R.id.items_id)
        val arrayAdapter: ArrayAdapter<*> =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, itemList)
        itemsList.adapter = arrayAdapter
        itemsList.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position) as String
            intent = Intent(this, ItemActivity::class.java)
            intent.putExtra("item_name", selectedItem)
            startActivity(intent)
        }
        val cartButton = findViewById<Button>(R.id.cartButton)
        cartButton.setOnClickListener{
            intent = Intent(this, CartActivity::class.java)
        }
        println("here123")
    }

    private fun setUpShop(){
        db.writeItem("Tennis rocket","Sport Tools", "For hitting tennis ball",79.99)
        db.writeItem("Tennis ball","Sport Tools","For being hit by tennis rocket",4.99)
        db.writeItem("Boxing gloves","Sport Tools","For hitting people",69.99)
        db.writeItem("Baseball bat","Sport Tools","For hitting baseball",59.99)
        db.writeItem("Hammer","Building Tools","For hitting",29.99)
    }
}


