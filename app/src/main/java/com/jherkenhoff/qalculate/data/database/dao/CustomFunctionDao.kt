package com.jherkenhoff.qalculate.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jherkenhoff.qalculate.data.database.model.CustomFunctionData
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomFunctionDao {

    @Query("SELECT * FROM custom_functions")
    fun getAll(): Flow<List<CustomFunctionData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CustomFunctionData)

    @Delete
    suspend fun delete(item: CustomFunctionData)

    @Query("DELETE FROM custom_functions WHERE name = :name")
    suspend fun deleteByName(name: String)
}
