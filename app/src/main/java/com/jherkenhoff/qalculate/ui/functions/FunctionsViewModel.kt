package com.jherkenhoff.qalculate.ui.functions

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jherkenhoff.libqalculate.Calculator
import com.jherkenhoff.qalculate.data.CalculatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FunctionDefinition(
    val name: String,
    val title: String,
    val description: String,
    val category: String,
    val args: String = "",
    val expression: String = "",
    val isCustom: Boolean = false
)

@HiltViewModel
class FunctionsViewModel @Inject constructor(
    private val calculatorRepository: CalculatorRepository,
) : ViewModel() {

    private val _searchString = mutableStateOf("")
    val searchString = _searchString

    private val _functionList = mutableStateOf<List<FunctionDefinition>>(emptyList())
    val functionList = _functionList

    init {
        viewModelScope.launch {
            combine(
                calculatorRepository.functions,
                calculatorRepository.customFunctions,
                // Accessing _searchString inside a Flow might not be the standard way with Compose State, 
                // but let's stick to update logic for now or convert searchString to Flow.
            ) { functions, customFunctions ->
                updateFunctionList(functions, customFunctions)
            }.collect { list ->
                _functionList.value = list
            }
        }
    }

    fun setSearchString(newSearchString: String) {
        _searchString.value = newSearchString
        // Trigger manual update because searchString isn't a Flow
        viewModelScope.launch {
            _functionList.value = updateFunctionList(calculatorRepository.functions.value, calculatorRepository.customFunctions.value)
        }
    }

    fun addFunction(name: String, title: String, description: String, args: String, expression: String) {
        calculatorRepository.addCustomFunction(name, title, description, args, expression)
    }

    fun deleteFunction(name: String) {
        calculatorRepository.deleteCustomFunction(name)
    }

    private fun updateFunctionList(functions: List<com.jherkenhoff.libqalculate.MathFunction>, customFunctions: List<com.jherkenhoff.qalculate.data.database.model.CustomFunctionData>): List<FunctionDefinition> {
        val customMap = customFunctions.associateBy { it.name }
        
        val sorted = functions.sortedWith(compareBy { it.name() })

        var filtered = sorted

        if (_searchString.value.isNotEmpty()) {
            filtered = filtered.filter { 
                it.name().lowercase().contains(_searchString.value.lowercase()) ||
                it.description().lowercase().contains(_searchString.value.lowercase()) ||
                it.title().lowercase().contains(_searchString.value.lowercase())
            }
        }

        return filtered.map { 
            val customData = customMap[it.name()]
            FunctionDefinition(
                it.name(), 
                it.title(), 
                it.description(), 
                it.category(),
                customData?.arguments ?: "",
                customData?.expression ?: "",
                !it.isBuiltin
            ) 
        }
    }
}
