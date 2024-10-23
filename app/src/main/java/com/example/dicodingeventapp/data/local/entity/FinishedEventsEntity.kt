package com.example.dicodingeventapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "finished_events")
class FinishedEventsEntity (
    @field:PrimaryKey
    @field:ColumnInfo(name = "id")
    val id: Int,

    @field:ColumnInfo(name = "summary")
    var summary: String? = null,

    @field:ColumnInfo(name = "mediaCover")
    var mediaCover: String? = null,

    @field:ColumnInfo(name = "registrants")
    var registrants: Int? = null,

    @field:ColumnInfo(name = "imageLogo")
    var imageLogo: String? = null,

    @field:ColumnInfo(name = "link")
    var link: String? = null,

    @field:ColumnInfo(name = "description")
    var description: String? = null,

    @field:ColumnInfo(name = "ownerName")
    var ownerName: String? = null,

    @field:ColumnInfo(name = "cityName")
    var cityName: String? = null,

    @field:ColumnInfo(name = "quota")
    var quota: Int? = null,

    @field:ColumnInfo(name = "name")
    var name: String? = null,

    @field:ColumnInfo(name = "beginTime")
    var beginTime: String? = null,

    @field:ColumnInfo(name = "endTime")
    var endTime: String? = null,

    @field:ColumnInfo(name = "category")
    var category: String? = null,

    @field:ColumnInfo(name = "favorite")
    var isFavorite: Boolean? = null
)