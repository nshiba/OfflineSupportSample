package net.nshiba.offlinesupportsample.data.db

import androidx.paging.DataSource
import androidx.room.*
import net.nshiba.offlinesupportsample.data.model.NovelEntity

@Dao
abstract class NovelDao {

    @Query("SELECT * FROM NovelEntity ORDER BY `order`")
    abstract fun selectAllNovels(): DataSource.Factory<Int, NovelEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(novels: List<NovelEntity>)

    @Query("DELETE FROM NovelEntity")
    abstract fun deleteAllNovels()

    @Transaction
    open fun updateNovels(novels: List<NovelEntity>) {
        deleteAllNovels()
        insertAll(novels)
    }
}