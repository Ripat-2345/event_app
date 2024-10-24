package com.example.dicodingeventapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dicodingeventapp.data.local.entity.FavoriteEventsEntity

@Dao
interface FavoriteEventsDao {
    @Query("SELECT * FROM favorite_events ORDER BY id DESC")
    fun getFavoriteEvents(): LiveData<List<FavoriteEventsEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteEvents(event: FavoriteEventsEntity)

    @Query("DELETE FROM favorite_events WHERE id = :id")
    suspend fun deleteFavorite(id: Int)
}
