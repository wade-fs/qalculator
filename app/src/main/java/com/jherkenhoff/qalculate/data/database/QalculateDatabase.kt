package com.jherkenhoff.qalculate.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jherkenhoff.qalculate.data.database.dao.CalculationHistoryItemDao
import com.jherkenhoff.qalculate.data.database.dao.CustomFunctionDao
import com.jherkenhoff.qalculate.data.database.model.CalculationHistoryItemData
import com.jherkenhoff.qalculate.data.database.model.CustomFunctionData

@Database(
    entities = [CalculationHistoryItemData::class, CustomFunctionData::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(DateTimeTypeConverters::class)
abstract class QalculateDatabase : RoomDatabase() {
    abstract fun calculationHistoryItemDao(): CalculationHistoryItemDao
    abstract fun customFunctionDao(): CustomFunctionDao
}