package com.jherkenhoff.qalculate.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_functions")
data class CustomFunctionData(
    @PrimaryKey
    val name: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "arguments")
    val arguments: String,
    @ColumnInfo(name = "expression")
    val expression: String
)
