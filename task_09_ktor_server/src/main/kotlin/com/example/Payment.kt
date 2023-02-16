package com.example

data class Payment(
    val id : Int,
    val customerName: String,
    val accountNumber: Long,
    val amount: String,
    val expiry: String,
    val cvv: String
)
