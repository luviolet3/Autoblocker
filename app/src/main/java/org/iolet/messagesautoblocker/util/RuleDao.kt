package org.iolet.messagesautoblocker.util

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RuleDao {
    @Query("SELECT count(id) FROM rules")
    suspend fun size() : Int

    @Query("SELECT * FROM rules")
    fun getAll(): Flow<List<Rule>>

    @Query("SELECT * FROM rules WHERE id = :id")
    suspend operator fun get(id: Int) : Rule

    @Insert
    suspend fun add(vararg rules: Rule)

    @Update
    suspend fun update(rule: Rule)

    @Query("DELETE FROM rules WHERE id = :id")
    suspend fun delete(id: Int)
    @Query("UPDATE rules SET id = id - 1 WHERE id > :id")
    suspend fun updateAfterDelete(id: Int)

    @Query("DELETE FROM rules")
    suspend fun deleteAll()

}