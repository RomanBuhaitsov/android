package com.example

import com.example.DatabaseFactory.dbQuery
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class DAOFacadeImpl : DAOFacade {
    private fun resultRowToProduct(row: ResultRow) = Product(
        id = row[Products.id],
        title = row[Products.title],
        description = row[Products.description],
        price = row[Products.price],
    )

    override suspend fun allProducts(): List<Product> = dbQuery {
        Products.selectAll().map(::resultRowToProduct)
    }

    override suspend fun article(id: Int): Product?= dbQuery {
        Products
            .select { Products.id eq id }
            .map(::resultRowToProduct)
            .singleOrNull()
    }

    override suspend fun addNewProduct(title: String, description: String, price: Int): Product?= dbQuery {
        val insertStatement = Products.insert {
            it[Products.title] = title
            it[Products.description] = description
            it[Products.price] = price
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToProduct)
    }
}

val dao: DAOFacade = DAOFacadeImpl().apply {
    runBlocking {
        if(allProducts().isEmpty()) {
            addNewProduct("Hello", "Sorry for late task submission :(", 0)
        }
    }
}