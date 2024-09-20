package com.example.prac06composecalculator

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class DataViewModel:ViewModel(){
    var firstNumber by  mutableStateOf("")
    var secondNumber by mutableStateOf("")
    var result by mutableStateOf("")
    var showResult by mutableStateOf(false) // Control the result visibility

    fun add(){
        val f1 = firstNumber.toDoubleOrNull()
        val f2= secondNumber.toDoubleOrNull()
        if(f1 != null && f2 !=null){
            result = (f1+f2).toString()
            showResult = true // Show result after calculation
        }
    }

    fun subtract() {
        val f1 = firstNumber.toDoubleOrNull()
        val f2 = secondNumber.toDoubleOrNull()
        if (f1 != null && f2 != null) {
            result = (f1 - f2).toString()
            showResult = true // Show result after calculation
        }
    }

    fun multiply() {
        val f1 = firstNumber.toDoubleOrNull()
        val f2 = secondNumber.toDoubleOrNull()
        if (f1 != null && f2 != null) {
            result = (f1 * f2).toString()
            showResult = true // Show result after calculation
        }
    }

    fun divide() {
        val f1 = firstNumber.toDoubleOrNull()
        val f2 = secondNumber.toDoubleOrNull()
        if (f1 != null && f2 != null) {
            if (f2 != 0.0) {
                result = (f1 / f2).toString()
            } else {
                result = "cannot divide by zero"
            }
            showResult = true // Show result after calculation
        }
    }


} // END CLASS