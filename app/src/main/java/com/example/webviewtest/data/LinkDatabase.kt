package com.example.webviewtest.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Link::class], version = 3)
abstract class LinkDatabase: RoomDatabase() {
  abstract fun linkDao(): LinkDao
}