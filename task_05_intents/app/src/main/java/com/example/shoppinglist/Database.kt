package com.example.shoppinglist

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmQuery
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
class User: RealmObject {
    @PrimaryKey
    var id : Number = 0
    var name: String = ""
    var email: String = "name@example.com"

    constructor(id: Number, name: String, email: String) : this(){
        this.id = id
        this.name=name
        this.email=name
    }

    constructor()

}

class Item : RealmObject {
    @PrimaryKey
    var name: String = ""
    var category: Category? = null
    var description: String = ""
    var price: String = ""
    var number = 1

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

    operator fun component1(): Any {
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

class Database(
    configuration: RealmConfiguration = RealmConfiguration
        .Builder(schema = setOf(Item::class, Category::class))
        .deleteRealmIfMigrationNeeded().build(),
    private val realm: Realm = Realm.open(configuration)
) {
    suspend fun writeItem(
        name: String,
        category: Category?,
        description: String = "lorem ipsum",
        price: String,
    ) {
        val item = Item().apply {
            this.name = name
            this.description = description
            this.category = category
            this.price = price
        }

        try {
                realm.write {
                println(item.name)
                copyToRealm(item)
            }
        }catch (e: IllegalArgumentException){}
    }

    fun getItemsByTitle(name: String): List<Item> {
        return realm.query<Item>("name = $0", name).find()
    }

    fun getCategoryByTitle(name: String): List<Category> {
        return realm.query<Category>("name = $0", name).find()
    }

    suspend fun writeCategory(
        name: String,
        description: String = "lorem ipsum",
    ) {
        val item = Category().apply {
            this.name = name
            this.description = description
        }

        realm.write {
            copyToRealm(item)
        }
    }

    suspend fun writeCategory(category: Category){
        realm.write { copyToRealm(category) }
    }

    fun getAllItems(): RealmResults<Item> {
        return realm.query<Item>().find()
    }

    fun getAllCategories(): RealmResults<Category> {
        return realm.query<Category>().find()
    }

    suspend fun deleteAll() {
        realm.write {
            val query: RealmQuery<Item> = this.query<Item>()
            val results: RealmResults<Item> = query.find()
            delete(results)
        }
        realm.write {
            val query: RealmQuery<Category> = this.query<Category>()
            val results: RealmResults<Category> = query.find()
            delete(results)
        }
    }

    fun closeRealm() {
        realm.close()
    }
}