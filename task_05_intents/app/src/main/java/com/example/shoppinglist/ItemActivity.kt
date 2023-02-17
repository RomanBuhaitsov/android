package com.example.shoppinglist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_activity)

        val bundle: Bundle? = intent.extras
        val name = bundle?.getString("item_name")
        val description = bundle?.getString("item_description")
        val price = bundle?.getString("item_price")
        Toast.makeText(applicationContext, name, Toast.LENGTH_LONG).show()
        Toast.makeText(applicationContext, description, Toast.LENGTH_LONG).show()
        Toast.makeText(applicationContext, price, Toast.LENGTH_LONG).show()
        val backButton = findViewById<Button>(R.id.backButton)
        val addButton = findViewById<Button>(R.id.addToCartButton)
        val itemName = findViewById<View>(R.id.itemName) as TextView
        val itemDescription = findViewById<View>(R.id.itemDescription) as TextView
        val itemPrice = findViewById<View>(R.id.itemPrice) as TextView
        itemName.text = name
        itemPrice.text = price
        itemDescription.text = description
        backButton.setOnClickListener {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        addButton.setOnClickListener{
            intent = Intent(this, MainActivity::class.java)
            intent.putExtra("item_name", name)
            intent.putExtra("item_price", price)
            startActivity(intent)
        }
    }
}