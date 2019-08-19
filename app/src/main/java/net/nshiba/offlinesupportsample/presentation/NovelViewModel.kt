package net.nshiba.offlinesupportsample.presentation

import androidx.lifecycle.ViewModel
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import net.nshiba.offlinesupportsample.data.network.NarouService
import net.nshiba.offlinesupportsample.data.model.NovelEntity
import net.nshiba.offlinesupportsample.data.network.Novel
import net.nshiba.offlinesupportsample.data.db.NovelDao
import net.nshiba.offlinesupportsample.data.db.NovelDatabase
import net.nshiba.offlinesupportsample.utils.ioThread
import timber.log.Timber

class NovelViewModel(
    private val database: NovelDatabase,
    private val narouService: NarouService
) : ViewModel() {

    private val dao: NovelDao by lazy { database.novelDao() }

    val allNovels = dao.selectAllNovels().toLiveData(
        config = Config(
            pageSize = 10,
            prefetchDistance = 10
        ),
        boundaryCallback = object : PagedList.BoundaryCallback<NovelEntity>() {
            override fun onZeroItemsLoaded() {
                super.onZeroItemsLoaded()
                Timber.d("nshiba: onZeroItemsLoaded")
            }
        }
    )

    fun insert(novel: NovelEntity) {
        ioThread {
            dao.insertAll(listOf(novel))
        }
    }

    fun deleteAll() {
        ioThread {
            dao.deleteAllNovels()
        }
    }

    fun insertNovelsOrderHyoka() {
        ioThread {
            narouService.fetchNovelsOrderHyoka().execute().body()
                ?.mapIndexed { index, novel -> novel.toWithOrder(index + 1) }
                .also { dao.updateNovels(it ?: listOf()) }
        }
    }

    fun insertNovelsOrderNew() {
        ioThread {
            narouService.fetchNovelsOrderNew().execute().body()
                ?.mapIndexed { index, novel -> novel.toWithOrder(index + 1) }
                .also { dao.updateNovels(it ?: listOf()) }
        }
    }

    private fun Novel.toWithOrder(order: Int) =
        NovelEntity(
            ncode = ncode ?: "null",
            title = title ?: "null",
            writer = writer ?: "null",
            order = order
        )
}
