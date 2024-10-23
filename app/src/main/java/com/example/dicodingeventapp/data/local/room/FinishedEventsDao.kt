package com.example.dicodingeventapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dicodingeventapp.data.local.entity.FinishedEventsEntity

@Dao
interface FinishedEventsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEvents(event: List<FinishedEventsEntity>)

    @Update
    suspend fun update(event: FinishedEventsEntity)

    @Query("DELETE FROM finished_events WHERE favorite = 0")
    suspend fun deleteAll()

    @Query("SELECT * FROM finished_events ORDER BY id DESC")
    fun getFinishedEvents(): LiveData<List<FinishedEventsEntity>>

    @Query("SELECT EXISTS(SELECT * FROM finished_events WHERE id = :id AND favorite = 1)")
    suspend fun isFavorite(id: Int): Boolean
}