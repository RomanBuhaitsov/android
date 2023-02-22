package models

import com.google.gson.annotations.JsonAdapter
import org.jetbrains.exposed.sql.*
import com.rnett.exposedgson.*
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID

object Products : Table() {
    val id = integer("id").autoIncrement().uniqueIndex() // Column<Int>
    val title = varchar("title", 50) // Column<String>
    val price = varchar("price", 30)
    val description = varchar("description", 200)

    override val primaryKey = PrimaryKey(id, name = "PK_Products_ID")
}

@JsonAdapter(ExposedTypeAdapter::class)
class ProductsData(id: EntityID<Int>) : IntEntity(id) {
    @ExposedGSON.JsonName("title")
    var title by Products.title
    @ExposedGSON.JsonName("title")
    var price by Products.price
    @ExposedGSON.JsonName("title")
    var description by Products.description

}

//data class ProductsData(val id: Int, val title: String)
class Product(i: Int, s: String, any: Any, s1: String) {

}
