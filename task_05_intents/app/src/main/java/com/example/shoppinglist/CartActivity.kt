package com.example.shoppinglist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CartActivity: AppCompatActivity()  {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_activity)

        val bundle: Bundle? = intent.extras
//        val name = bundle?.get("item_name")
//
//        Toast.makeText(applicationContext, name.toString() , Toast.LENGTH_LONG).show()
//        val backButton = findViewById<Button>(R.id.backButton)
//        val itemName = findViewById<View>(R.id.itemName)
//        if(itemName is TextView){
//            itemName.text=name.toString().uppercase()
//            println("123$name")
//        }
//        backButton.setOnClickListener() {
//            intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }
    }
}