package com.example.shoppinglist

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmQuery
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Item : RealmObject {
    @PrimaryKey
    var name: String = ""
    var category = ""
    var description: String = ""
    var price: Double = 0.0
}

class Category : RealmObject {
    var name: String = ""
    var description = ""
}

object Database {
    private val configuration: RealmConfiguration =
        RealmConfiguration.create(schema = setOf(Item::class))
    val realm = Realm.open(configuration)

    fun writeItem(
        name: String,
        categoryName: String,
        description: String = "lorem ipsum",
        price: Double,
    ) {
        val item = Item().apply {
            this.name = name
            this.description = description
            this.price = price
            this.category = categoryName
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

    fun getAllItems(): RealmResults<Item> {
        return realm.query<Item>().find()
    }

    fun getAllCategories(): RealmResults<Category> {
        return realm.query<Category>().find()
    }

    suspend fun deleteAllItems() {
        realm.write {
            val query: RealmQuery<Item> = this.query<Item>()
            val results: RealmResults<Item> = query.find()
            delete(results)
        }
    }
}