package com.example.shoppinglist

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.internal.interop.RealmCoreDuplicatePrimaryKeyValueException
import io.realm.kotlin.query.RealmQuery
import io.realm.kotlin.query.RealmResults

class Cart(activity: MainActivity) {
    private val configuration: RealmConfiguration = RealmConfiguration
        .Builder(schema = setOf(Item::class, Category::class))
        .deleteRealmIfMigrationNeeded().build()
    private val realm: Realm = Realm.open(configuration)


    suspend fun writeItem(
        name: String,
        price: String,
    ): Boolean {
        val item = Item().apply {
            this.name = name
            this.price = price
        }

        try {
            realm.write {
                copyToRealm(item)
            }
        } catch (e: java.lang.Exception) {
            if (e is RealmCoreDuplicatePrimaryKeyValueException ||
                e is java.lang.IllegalArgumentException
            ) {
                realm.write {
                    getItemByTitle(name)!!.number += 1
                }

            } else {
                e.printStackTrace()
                return false
            }
        }
        return true

    }

    private fun getItemByTitle(name: String): Item? {
        return realm.query<Item>("name = $0", name).first().find()
    }

    fun getAllItems(): RealmResults<Item> {
        return realm.query<Item>().find()
    }

    fun getItemsNames(): List<String> {
        return getAllItems().map { item: Item -> item.name }

    }

    suspend fun deleteAll() {
        realm.write {
            val query: RealmQuery<Item> = this.query()
            val results: RealmResults<Item> = query.find()
            delete(results)
        }
    }

    fun closeRealm() {
        realm.close()
    }
}




