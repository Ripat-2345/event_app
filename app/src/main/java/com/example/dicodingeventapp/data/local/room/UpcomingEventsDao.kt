package com.example.dicodingeventapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dicodingeventapp.data.local.entity.UpcomingEventsEntity

@Dao
interface UpcomingEventsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEvents(event: List<UpcomingEventsEntity>)

    @Update
    suspend fun update(event: UpcomingEventsEntity)

    @Query("UPDATE upcoming_events SET favorite = :favoriteState WHERE id = :id")
    suspend fun updateIsFavorite(id: Int, favoriteState: Int)

    @Query("DELETE FROM upcoming_events WHERE favorite = 0")
    suspend fun deleteAll()

    @Query("SELECT * FROM upcoming_events ORDER BY id DESC")
    fun getUpcomingEvents(): LiveData<List<UpcomingEventsEntity>>

    @Query("SELECT EXISTS(SELECT * FROM upcoming_events WHERE id = :id AND favorite = 1)")
    suspend fun isFavorite(id: Int): Boolean

    @Query("SELECT * FROM upcoming_events WHERE id = :id")
    fun checkIsFavorite(id: Int): LiveData<UpcomingEventsEntity>

    // query untuk menampilkan data favorite dari 2 table
    @Query("SELECT * FROM upcoming_events WHERE favorite = 1 UNION SELECT * FROM finished_events WHERE favorite = 1")
    fun getFavoriteEvents(): LiveData<List<UpcomingEventsEntity>>
}