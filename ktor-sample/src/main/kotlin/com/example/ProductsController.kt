package com.example

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class ProductsController {

    fun create(product: Product) {
        transaction {
            Products.insert {
                it[title] = product.title
                it[description] = product.description
                it[price] = product.price
            }
        }
    }

    fun getAll(): List<Product> {
        return transaction {
            Products.selectAll().map {
                Product(
                    it[Products.id],
                    it[Products.title],
                    it[Products.description],
                    it[Products.price]
                )
            }
        }
    }

    fun getById(id: Int): Product? {
        return transaction {
            Products.select { Products.id eq id }.map {
                Product(
                    it[Products.id],
                    it[Products.title],
                    it[Products.description],
                    it[Products.price]
                )
            }.firstOrNull()
        }
    }
}