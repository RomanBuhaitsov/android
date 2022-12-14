package com.example.shoppinglist

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmQuery
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.jetbrains.annotations.PropertyKey
import java.math.BigDecimal

class Item : RealmObject {
    @PrimaryKey
    var name: String = ""
    var category: Category? = null
    var description: String = ""
    var price: String = ""

    constructor(
        name: String,
        category: Category?,
        description: String,
        price: String,
    ) : this() {
        this.name = name
        this.category = category
        this.description = description
        this.price = price
    }

    constructor()

    override fun toString(): String {
        return name;
    }
}

class Category() : RealmObject {
    var name: String = ""
    var description: String = ""
    constructor(name: String, description: String) : this() {
        this.name=name
        this.description=description
    }
}

object Database {
    private val configuration = RealmConfiguration
        .Builder(schema = setOf(Item::class, Category::class))
        .deleteRealmIfMigrationNeeded().build()
    private val realm = Realm.open(configuration)

    fun writeItem(
        name: String,
        category: Category,
        description: String = "lorem ipsum",
        price: String,
    ) {
        val item = Item().apply {
            this.name = name
            this.description = description
            this.category = category
            this.price = price
            println("PRICE "+ price)
        }

        realm.writeBlocking {
            copyToRealm(item)
        }
    }

    fun getItemsByTitle(name: String): List<Item> {
        return realm.query<Item>("name = $0", name).find()
    }

    fun getCategoryByTitle(name: String): List<Category> {
        return realm.query<Category>("name = $0", name).find()
    }

    fun writeCategory(
        name: String,
        description: String = "lorem ipsum",
    ) {
        val item = Category().apply {
            this.name = name
            this.description = description
        }

        realm.writeBlocking {
            copyToRealm(item)
        }
    }

    fun writeCategory(category: Category){
        realm.writeBlocking { copyToRealm(category) }
    }

    fun getAllItems(): RealmResults<Item> {
        return realm.query<Item>().find()
    }

    fun getAllCategories(): RealmResults<Category> {
        return realm.query<Category>().find()
    }

    fun deleteAll() {
        realm.writeBlocking {
            val query: RealmQuery<Item> = this.query<Item>()
            val results: RealmResults<Item> = query.find()
            delete(results)
        }
        realm.writeBlocking {
            val query: RealmQuery<Category> = this.query<Category>()
            val results: RealmResults<Category> = query.find()
            delete(results)
        }
    }

    fun closeRealm() {
        realm.close()
    }
}