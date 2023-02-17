package com.example.shoppinglist

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmQuery
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

object Cart{
    private val configuration: RealmConfiguration = RealmConfiguration
    .Builder(schema = setOf(Item::class, Category::class))
    .deleteRealmIfMigrationNeeded().build()
    private val realm: Realm = Realm.open(configuration)

    fun writeItem(
        name: String,
        price: String,
    ) {
        val item = Item().apply {
            this.name = name
            this.price = price
        }

        try {
            realm.writeBlocking {
                copyToRealm(item)
            }
        } catch (_: IllegalArgumentException) {
            if (getItemByTitle(name) != null)
                item.number += 1
        }
    }

    private fun getItemByTitle(name: String): Item? {
        return realm.query<Item>("name = $0", name).first().find()
    }

    fun getAllItems(): RealmResults<Item> {
        return realm.query<Item>().find()
    }

    fun getItemsNames(): List<String>{
        return getAllItems().map { item: Item -> item.name }

    }

    fun deleteAll() {
        realm.writeBlocking {
            val query: RealmQuery<Item> = this.query()
            val results: RealmResults<Item> = query.find()
            delete(results)
        }
    }

    fun closeRealm() {
        realm.close()
    }
}