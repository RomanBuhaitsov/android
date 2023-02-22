package com.example

interface DAOFacade {
    suspend fun allProducts(): List<Product>
    suspend fun article(id: Int): Product?
    suspend fun addNewProduct(title: String, description: String, price: Int): Product?
}