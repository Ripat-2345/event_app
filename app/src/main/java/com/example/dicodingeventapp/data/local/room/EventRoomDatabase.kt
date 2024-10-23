package com.example.dicodingeventapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dicodingeventapp.data.local.entity.FinishedEventsEntity
import com.example.dicodingeventapp.data.local.entity.UpcomingEventsEntity

@Database(entities = [UpcomingEventsEntity::class, FinishedEventsEntity::class], version = 1)
abstract class EventRoomDatabase: RoomDatabase() {
    abstract fun upcomingEventsDao(): UpcomingEventsDao
    abstract fun finishedEventsDao(): FinishedEventsDao

    companion object{
        @Volatile
        private var instance: EventRoomDatabase? = null

        fun getInstance(context: Context): EventRoomDatabase =
            instance ?: synchronized(this){
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    EventRoomDatabase::class.java,
                    "Event.db"
                ).build()
            }
    }
}
