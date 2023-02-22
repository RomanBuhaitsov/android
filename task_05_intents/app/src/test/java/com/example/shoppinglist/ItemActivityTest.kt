package com.example.shoppinglist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class ItemActivityTest {

    @Mock
    private lateinit var mockIntent: Intent

    @Mock
    private lateinit var backButton: Button

    @Mock
    private lateinit var addToCartButton: Button

    @Mock
    private lateinit var itemName: TextView

    @Mock
    private lateinit var itemDescription: TextView

    @Mock
    private lateinit var itemPrice: TextView

    private lateinit var itemActivity: ItemActivity

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(mockIntent.extras).thenReturn(Bundle())
        `when`(backButton.setOnClickListener(any())).thenAnswer {
            val listener = it.getArgument<() -> Unit>(0)
            listener.invoke()
        }
        `when`(addToCartButton.setOnClickListener(any())).thenAnswer {
            val listener = it.getArgument<() -> Unit>(0)
            listener.invoke()
        }
        itemActivity = mock(ItemActivity::class.java)
//        itemActivity = mock(ItemActivity())
        doReturn(mockIntent).`when`(itemActivity).intent
        doReturn(itemName).`when`(itemActivity).findViewById<TextView>(R.id.itemName)
        doReturn(itemDescription).`when`(itemActivity).findViewById<TextView>(R.id.itemDescription)
        doReturn(itemPrice).`when`(itemActivity).findViewById<TextView>(R.id.itemPrice)
        doReturn(backButton).`when`(itemActivity).findViewById<Button>(R.id.backButton)
        doReturn(addToCartButton).`when`(itemActivity).findViewById<Button>(R.id.addToCartButton)
    }

    @Test
    fun `test onCreate() with valid extras`() {
        val name = "test1"
        val description = "This is a test item"
        val price = "$13.33"
        `when`(mockIntent .extras?.getString("item_name")).thenReturn(name)
        `when`(mockIntent.extras?.getString("item_description")).thenReturn(description)
        `when`(mockIntent.extras?.getString("item_price")).thenReturn(price)
        itemActivity.onCreate(Bundle())
        verify(itemName).text = name
        verify(itemDescription).text = description
        verify(itemPrice).text = price
        verify(mockIntent.extras, times(3))?.getString(anyString())
    }

    @Test
    fun `test onCreate() with null extras`() {
        itemActivity.onCreate(Bundle())
        verify(itemName, never()).text = anyString()
        verify(itemDescription, never()).text = anyString()
        verify(itemPrice, never()).text = anyString()
    }

    @Test
    fun `test backButton onClick`() {
        backButton.performClick()
        assert(backButton.isClickable)
        verify(itemActivity).startActivity(any(Intent::class.java))
    }

    @Test
    fun `test addToCartButton onClick`() {
        val name = "test1"
        val price = "$123.45"
        addToCartButton.performClick()
        verify(itemActivity).startActivity(mockIntent)
        verify(mockIntent).putExtra("item_name", name)
        verify(mockIntent).putExtra("item_price", price)
        verify(mockIntent).putExtra("operation", "add_to_cart")
    }
}
