package com.example.mycalculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.isDigitsOnly
import androidx.core.view.children
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private val operations = Operation.values().toList()
    private var operation: Operation? = null
    private var operand: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.resultTextView)

        findViewById<Button>(R.id.sumButton).setOnClickListener { sum() }
    }

    @SuppressLint("SetTextI18n")
    fun numberClick(v: View?) {
        if(v is Button) {
            val text = v.text.toString()
            if(resultTextView.text.toString() == "0")
                resultTextView.text = ""
            if(text == "." && resultTextView.text.contains(text))
                return
            val result = resultTextView.text.toString()
            resultTextView.text = result + text
        }
    }

    fun operationClick(v: View?) {
        if(v is Button) {
            if(operation != null) {
                sum()
                return
            }
            val text = v.text.toString()
            operation = operations.firstOrNull { it.symbol == text }
            operand = resultTextView.text.toString().toDouble()
            resultTextView.text = ""
            checkForSingleOperand()
        }
    }
    fun clear(v: View?) {
        resultTextView.text = ""
        operation = null
    }

    private fun checkForSingleOperand() {
        val result = when(operation) {
            Operation.PERCENT -> operand / 100
            Operation.SQUARE -> sqrt(operand)
            Operation.PLUS_MINUS -> operand * -1
            else -> return
        }
        operation = null
        resultTextView.text = result.toString()
    }
    private fun sum() {
        if(operation == null || resultTextView.text.isBlank())
            return
        val secondOperand = resultTextView.text.toString().toDouble()
        val result = when(operation) {
            Operation.PLUS -> operand + secondOperand
            Operation.MINUS -> operand - secondOperand
            Operation.DIVIDE -> operand / secondOperand
            Operation.MULTIPLY -> operand * secondOperand
            else -> return
        }
        operation = null
        resultTextView.text = result.toString()
    }
}