package com.jherkenhoff.qalculate.ui.functions

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jherkenhoff.libqalculate.Calculator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class FunctionDefinition(
    val name: String,
    val title: String,
    val description: String,
    val category: String
)

@HiltViewModel
class FunctionsViewModel @Inject constructor(
    private val calc: Calculator,
) : ViewModel() {

    private val _searchString = mutableStateOf("")
    val searchString = _searchString

    private val _functionList = mutableStateOf<List<FunctionDefinition>>(emptyList())
    val functionList = _functionList

    init {
        updateFunctionList()
    }

    fun setSearchString(newSearchString: String) {
        _searchString.value = newSearchString
        updateFunctionList()
    }

    fun addFunction(name: String, args: String, expression: String) {
        // Use := syntax to define a function in libqalculate
        // Example: f(x, y) := x^2 + y^2
        val definition = if (args.isBlank()) "$name := $expression" else "$name($args) := $expression"
        calc.calculateAndPrint(definition, 2000)
        updateFunctionList()
    }

    private fun updateFunctionList() {
        val sorted = calc.functions.sortedWith(compareBy { it.name() })

        var filtered = sorted

        if (_searchString.value.isNotEmpty()) {
            filtered = filtered.filter { 
                it.name().lowercase().contains(_searchString.value.lowercase()) ||
                it.description().lowercase().contains(_searchString.value.lowercase())
            }
        }

        _functionList.value = filtered.map { 
            FunctionDefinition(
                it.name(), 
                it.name(), 
                it.description(), 
                "General" // MathFunction doesn't easily expose category in simple bindings usually
            ) 
        }
    }
}
