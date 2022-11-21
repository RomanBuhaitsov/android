package com.example.mycalculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.Math.log
import java.lang.Math.pow
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.InvalidPropertiesFormatException
import kotlin.math.log
import kotlin.math.pow

@Suppress("IMPLICIT_CAST_TO_ANY")
class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var isInverted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        display = findViewById(R.id.output)

        val numbers = arrayOf(
            R.id.zero,
            R.id.one,
            R.id.zero,
            R.id.two,
            R.id.three,
            R.id.four,
            R.id.five,
            R.id.six,
            R.id.seven,
            R.id.eight,
            R.id.nine
        )
        val operations = arrayOf(
            R.id.plus,
            R.id.minus,
            R.id.multiply,
            R.id.divide,
            R.id.plus,
            R.id.logarithm,
            R.id.power,
            R.id.percent
        )

        findViewById<Button>(R.id.clear).setOnClickListener(this::clear)
        findViewById<Button>(R.id.equals).setOnClickListener(this::count)
        findViewById<Button>(R.id.inverse).setOnClickListener(this::inverse)

        for (numberButtonId in numbers) {
            findViewById<Button>(numberButtonId).setOnClickListener(this::writeNumber)
        }
        for (operation in operations) {
            findViewById<Button>(operation).setOnClickListener(this::writeOperator)
        }
    }

    fun count(view: View) {
        val flagInverted = isInverted
        if (flagInverted) {
            inverse(view)
        }
        val input: String = display.text.toString()
        val nums = arrayOf(input.split(Regex("[^0-9.]")).filter { it.isNotBlank() })
        val regex = "[^0-9.]".toRegex()
        var operator: String = regex.find(input)?.value ?: "null"
        if(flagInverted && operator=="null")
            operator="inv"

        if (nums.isEmpty() || operator == "null" )
            return

        var numberResult:Double = if (nums[0].size==2 && operator != "%")
            binaryOperation(operator, nums[0][0].toDouble(), nums[0][1].toDouble())
        else if (nums[0].size==1 ){
            if (operator == "%")
                (nums[0][0].toDouble() / 100)
            else
                nums[0][0].toDouble()
        }
        else
            return

        if(flagInverted)
            numberResult*=-1

        try{
            var out = numberResult.toBigDecimal().setScale(20).toString()
            if (out.contains('.'))
                while (out.last()=='0'||out.last()=='.')
                    out=out.dropLast(1)
            display.text=cleanConversion(out)
        } catch (e:java.lang.Exception){
            display.text =""
        }
    }

    private fun binaryOperation(
        operator: String,
        number1: Double,
        number2: Double
    ): Double {
        return when (operator) {
            "+" -> number1 + number2
            "-" -> number1 - number2
            "×" -> number1 * number2
            "÷" -> number1 / number2
            "log" -> log(number2, number1)
            "^" -> number1.pow(number2.toInt())
            else -> {
                throw InvalidPropertiesFormatException("Wrong operator")
            }
        }
    }

    fun clear(view: View) {
        display.text = ""
    }

    private fun writeNumber(view: View) {
        val cannotBeLastChar = ")%"
        if (view is Button) {
            if (display.text.isNotEmpty() && cannotBeLastChar.contains(display.text.last()))
                return
            if(display.text.toString()=="0")
                display.text=view.text
            else
                display.append(view.text)
            println("writeNum")
        }
    }

    private fun inverse(view: View) {
        if(display.text.isEmpty())
            return

        if (isInverted) {
            isInverted = false
            display.text = display.text.subSequence(2, display.text.length - 1)
        } else {
            display.text = getString(R.string.inverted_value, display.text)
            isInverted = true
        }
    }

    fun writeOperator(view: View) {
        if (view is Button && display.text.isNotEmpty() && !isInverted) {
            val nums = arrayOf(display.text.split(Regex("[^0-9.]")).filter { it.isNotBlank() })
            if (nums[0].isNotEmpty()) {
                display.text = cleanConversion(nums[0][0])
                display.append(view.text)
                if (nums[0].size > 1)
                    display.append(cleanConversion(nums[0][1]))
            }
        }
    }

    private fun cleanConversion(number: String): String {
        return number.replace("[", "").replace("]", "").replace(",", "")
    }
}


