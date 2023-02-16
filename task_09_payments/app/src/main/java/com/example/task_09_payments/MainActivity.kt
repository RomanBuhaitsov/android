package com.example.task_09_payments

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.math.BigDecimal
import java.util.Random
import javax.security.auth.callback.Callback
import kotlin.random.Random.Default.nextInt

class MainActivity : AppCompatActivity() {
    private val BASE_URL: String = "http://192.168.52.1:8100/payments" // local ip
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val payButton = findViewById<Button>(R.id.payButton)
        val card = findViewById<EditText>(R.id.card)
        val expiry = findViewById<EditText>(R.id.expiry)
        val cvv = findViewById<EditText>(R.id.cvv)
        val name = findViewById<EditText>(R.id.editTextTextPersonName)
        val amount = findViewById<EditText>(R.id.paymentAmount)
        payButton.setOnClickListener {
            if (card.text.isEmpty() || expiry.text.isEmpty() || cvv.text.isEmpty() || name.text.isEmpty() || amount.text.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    val cardNumber = validateAndReturn(card.text.toString(),
                        "^\\\$?([1-9]{1}[0-9]{0,2}(\\,[0-9]{3})*(\\.[0-9]{0,2})?|[1-9]{1}[0-9]{0,}(\\.[0-9]{0,2})?|0(\\.[0-9]{0,2})?|(\\.[0-9]{1,2})?)\$");
                    val expiryDate = validateAndReturn(expiry.text.toString(),
                        "/\\b(0[1-9]|1[0-2])\\/?([0-9]{4}|[0-9]{2})\\b/;")
                    val cvvNumber = validateAndReturn(cvv.text.toString(), "/^[0-9]{3,4}\$")
                    val nameText = validateAndReturn(name.text.toString(), "/^[a-z ,.'-]+\$/i")
                    val amountNumber = validateAndReturn(amount.text.toString(), "^\\d+\$")
                    val id = nextInt(1, Int.MAX_VALUE);
                    val paymentData = PaymentData(id, nameText, cardNumber, amountNumber)
//                    val paymentData = PaymentData(nameText, BigDecimal("1"), cardNumber, expiryDate, cvvNumber)
                    makePayment(paymentData)
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Incorrect format", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateAndReturn(toString: String, regex: String): String {
//        if(!toString.matches(Regex(regex))) throw IllegalArgumentException()
//        else return toString;
        return toString
    }

    private fun makePayment(paymentData: PaymentData) {
        val url = "$BASE_URL/create"
        val json = Gson().toJson(paymentData)
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = json.toRequestBody(mediaType)

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback, okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Payment failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Payment successful", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

//    fun pay(){
//        val values = mapOf("name" to "John Doe", "occupation" to "gardener")
//
//        val objectMapper = ObjectMapper()
//        val requestBody: String = objectMapper
//            .writeValueAsString(values)
//
//        val client = HttpClient.newBuilder().build();
//        val request = HttpRequest.newBuilder()
//            .uri(URI.create("https://httpbin.org/post"))
//            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
//            .build()
//        val response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        println(response.body())
//    }

    //    data class PaymentData(val id: Int, val userName: String, val paymentAmount: String, val cardNumber: String, val expiryDate: String, val cvv: String)
    data class PaymentData(
        val id: Int,
        val userName: String,
        val cardNumber: String,
        val paymentAmount: String,
    )
}

