package com.jherkenhoff.qalculate.ui.functions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jherkenhoff.qalculate.R

@Composable
fun FunctionsScreen(
    viewModel: FunctionsViewModel = viewModel(),
    openDrawer: () -> Unit = {}
) {
    FunctionsScreenContent(
        openDrawer = openDrawer,
        functionList = viewModel.functionList.value,
        onSearchInputUpdate = { viewModel.setSearchString(it) },
        searchString = viewModel.searchString.value,
        onAddFunction = { name, args, expr -> viewModel.addFunction(name, args, expr) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FunctionsScreenContent(
    openDrawer: () -> Unit = {  },
    functionList: List<FunctionDefinition> = emptyList(),
    onSearchInputUpdate: (String) -> Unit = {},
    searchString: String = "",
    onAddFunction: (String, String, String) -> Unit = { _, _, _ -> }
) {
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.functions_title))
                },
                navigationIcon = {
                    IconButton(onClick = openDrawer) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = stringResource(R.string.open_menu_content_description)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {   }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Sort,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.add_function_title))
            }
        },
        modifier = Modifier.imePadding(),
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {

            SearchBar(
                query = searchString,
                placeholder = { Text(stringResource(R.string.functions_search_placeholder)) },
                active = false,
                trailingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                onActiveChange = {  },
                onQueryChange = onSearchInputUpdate,
                onSearch = {},
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
            }

            Spacer(modifier = Modifier.size(8.dp))

            LazyColumn {
                for (func in functionList) {
                    item {
                        ListItem(
                            headlineContent = { Text(func.name) },
                            supportingContent = { Text(func.description) },
                        )
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddFunctionDialog(
            onDismiss = { showAddDialog = false },
            onSave = { name, args, expr ->
                onAddFunction(name, args, expr)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun AddFunctionDialog(
    onDismiss: () -> Unit,
    onSave: (String, String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var args by remember { mutableStateOf("") }
    var expression by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.add_function_title)) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.add_function_name_label)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedTextField(
                    value = args,
                    onValueChange = { args = it },
                    label = { Text(stringResource(R.string.add_function_args_label)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.size(8.dp))
                OutlinedTextField(
                    value = expression,
                    onValueChange = { expression = it },
                    label = { Text(stringResource(R.string.add_function_expression_label)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { if (name.isNotBlank() && expression.isNotBlank()) onSave(name, args, expression) },
                enabled = name.isNotBlank() && expression.isNotBlank()
            ) {
                Text(stringResource(R.string.add_function_save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.add_function_cancel))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    FunctionsScreenContent()
}
