package net.nshiba.offlinesupportsample.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import net.nshiba.offlinesupportsample.data.model.NovelEntity

@Database(entities = [NovelEntity::class], version = 1)
abstract class NovelDatabase : RoomDatabase() {

    abstract fun novelDao(): NovelDao
}