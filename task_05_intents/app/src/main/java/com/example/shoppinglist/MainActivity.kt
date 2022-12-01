package com.example.shoppinglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val items = arrayOf(
            "Tennis rocket",
            "Tennis ball",
            "Boxing gloves",
            "Baseball bat",
            "Hammer"
        )
        val itemList = ArrayList<String>(listOf(*items))
        val itemsList = findViewById<ListView>(R.id.items_id)
        val arrayAdapter: ArrayAdapter<*> =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, itemList)
        itemsList.adapter = arrayAdapter
        itemsList.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position) as String
            intent = Intent(this, ItemActivity::class.java)
            intent.putExtra("item_name", selectedItem.toString())
            startActivity(intent)
        }
    }
}