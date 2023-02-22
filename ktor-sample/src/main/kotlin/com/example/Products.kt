package com.example

import org.jetbrains.exposed.sql.*

data class Product(val id: Int, val title: String, val description: String, val price: Int)
object Products : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 128)
    val description = varchar("description", 256)
    val price = integer("price")

    override val primaryKey = PrimaryKey(id)
}

