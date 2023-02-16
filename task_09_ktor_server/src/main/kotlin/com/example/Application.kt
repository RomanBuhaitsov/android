package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

data class Response(
    val message: String
)

fun main() {
    val server = embeddedServer(Netty, port = 8100) {
        install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
            }
        }
        routing {
            payment(payments = PaymentRepo())
        }
    }
    server.start(wait = true)

}

fun Route.payment(payments: PaymentRepo) {
    route("/payments"){
        get {
            call.respond(HttpStatusCode.OK, payments.paymentsList)
        }

        get("/{paymentId}"){
            val paymentId = call.parameters["paymentId"]?.toInt()
            val requestPayment = payments.paymentsList.firstOrNull {it.id == paymentId };
            if (requestPayment != null) {
                call.respond(HttpStatusCode.OK, requestPayment)
            } else {
                call.respond(HttpStatusCode.NotFound, Response("Payment with this ID does not exist."))
            }
        }

        post("/create"){
            val payment = call.receive<Payment>()
            payments.paymentsList.add(payment)
            call.respond(HttpStatusCode.OK, Response("New payment added."))
        }
    }
}


fun Application.module() {
    configureRouting()
}
