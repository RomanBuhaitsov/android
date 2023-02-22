package com.example.shoppinglist

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DatabaseTest {
    private lateinit var database: Database
    private lateinit var realm: Realm

    @Before
    fun setup() {
        val configuration = RealmConfiguration.Builder(schema = setOf(Item::class, Category::class)).deleteRealmIfMigrationNeeded().build()
        realm = Realm.open(configuration)
        database = Database(configuration, realm)
    }

    @After
    fun teardown() {
        realm.close()
    }

    @Test
    suspend fun testWriteItem() {
        val item = Item(name = "test1",
            category = null,
            description = "Test Description",
            price = "$1.99")

        database.writeItem(item.name, item.category, item.description, item.price)
        val result = database.getItemsByTitle(item.name)
        assertTrue(result.isNotEmpty())
        assertEquals(item, result[0])
    }

    @Test
    suspend fun testGetItemsByTitle() {
        val item1 = Item(name = "test",
            category = null,
            description = "test desc 1",
            price = "$9.87")
        val item2 = Item(name = "test2",
            category = null,
            description = "test desc 2",
            price = "$12.34")
        database.writeItem(item1.name, item1.category, item1.description, item1.price)
        database.writeItem(item2.name, item2.category, item2.description, item2.price)

        val result = database.getItemsByTitle(item1.name)
        assertTrue(result.isNotEmpty())
        assertEquals(item1, result[0])
    }

    @Test
    suspend fun testWriteCategory() {
        val category = Category(name = "Test Category", description = "Test Description")
        database.writeCategory(category)
        val result = database.getCategoryByTitle(category.name)
        assertTrue(result.isNotEmpty())
        assertEquals(category, result[0])
    }

    @Test
    suspend fun testGetAllItems() {
        val item1 = Item(name = "test",
            category = null,
            description = "test desc 1",
            price = "$0.99")
        val item2 = Item(name = "test2",
            category = null,
            description = "test desc 2",
            price = "$34.88")
        database.writeItem(item1.name, item1.category, item1.description, item1.price)
        database.writeItem(item2.name, item2.category, item2.description, item2.price)
        val result = database.getAllItems()
        assertTrue(result.isNotEmpty())
        assertEquals(2, result.size)
        assertTrue(result.contains(item1))
        assertTrue(result.contains(item2))
    }
}