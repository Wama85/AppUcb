package com.calyrsoft.ucbp1.features.dollar.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.calyrsoft.ucbp1.features.dollar.data.database.entity.DollarEntity

@Dao
interface IDollarDao {
    @Query("SELECT * FROM dollars")
    suspend fun getList(): List<DollarEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dollar: DollarEntity)

    @Query("DELETE FROM dollars")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDollars(lists: List<DollarEntity>)

    @Query("SELECT * FROM dollars ORDER BY timestamp DESC")
    suspend fun getAllOrderedByDate(): List<DollarEntity>

    @Query("DELETE FROM dollars WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM dollars WHERE timestamp >= :timestampFrom ORDER BY timestamp DESC")
    suspend fun getDollarsFromDate(timestampFrom: Long): List<DollarEntity>

    @Query("SELECT * FROM dollars WHERE source = :source ORDER BY timestamp DESC")
    suspend fun getDollarsBySource(source: String): List<DollarEntity>

    @Query("SELECT * FROM dollars ORDER BY timestamp DESC LIMIT 1")
    suspend fun getMostRecent(): DollarEntity?

    @Query("SELECT * FROM dollars WHERE timestamp BETWEEN :startTime AND :endTime ORDER BY timestamp DESC")
    suspend fun getDollarsBetweenDates(startTime: Long, endTime: Long): List<DollarEntity>
}
