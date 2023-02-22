package com.example.plugins

import com.example.Product
import com.example.dao
import com.google.gson.Gson
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.util.*
import io.netty.handler.codec.http.HttpResponse
import java.net.URL

fun Application.configureRouting() {
    val gson = Gson()
    routing {
        get {
            val products: List<Product>  = dao.allProducts()
            val jsonTutsList: String = gson.toJson(products)
            call.respond(jsonTutsList)
        }

        get("/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val product : Product? = id.let { it1 -> dao.article(it1) }
            val jsonTutsList: String = gson.toJson(product)
            call.respond(jsonTutsList)
        }

        post() {
            val response = call.receiveParameters().toString()
            val product: Product = gson.fromJson(response, Product::class.java)
            println(product.description)

            val prod = dao.addNewProduct(
                product.title,
                product.description,
                product.price,
            )
            call.respondRedirect("/${prod?.id}")
        }
    }
}
