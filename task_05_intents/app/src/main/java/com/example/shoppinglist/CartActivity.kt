package com.example.shoppinglist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import io.realm.kotlin.query.RealmResults
import java.util.ArrayList

class CartActivity: AppCompatActivity()  {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_activity)

        val bundle: Bundle? = intent.extras

        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val itemsNames: Array<String> = bundle?.getStringArrayList("items")?.toTypedArray() ?: arrayOf("")
        val arrayAdapter: ArrayAdapter<String> =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, itemsNames)
        val itemsList = findViewById<ListView>(R.id.cart_items)
        itemsList.adapter = arrayAdapter
    }

}