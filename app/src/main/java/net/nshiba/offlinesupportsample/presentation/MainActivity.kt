package net.nshiba.offlinesupportsample.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.squareup.moshi.Moshi
import net.nshiba.offlinesupportsample.*
import net.nshiba.offlinesupportsample.data.db.NovelDatabase
import net.nshiba.offlinesupportsample.data.model.NovelEntity
import net.nshiba.offlinesupportsample.data.network.NarouService
import net.nshiba.offlinesupportsample.databinding.ActivityMainBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val viewModelFactory by lazy {
        NovelViewModelFactory(
            novelDatabase,
            narouService
        )
    }

    private val novelViewModel: NovelViewModel by viewModels { viewModelFactory }

    private lateinit var binding: ActivityMainBinding

    private val novelDatabase: NovelDatabase by lazy {
        Room.databaseBuilder(
            baseContext,
            NovelDatabase::class.java,
            "cookpad_store_live.db"
        ).build()
    }

    private val narouService: NarouService by lazy {
        initNarouService()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

        val adapter = NovelListAdapter()
        initLiveData(adapter)
        initNovelList(adapter)
        initButtons()
    }

    private fun initNarouService(): NarouService {
        val okhttp = OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.syosetu.com")
            .client(okhttp)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .build()
        return retrofit.create(NarouService::class.java)
    }

    private fun initLiveData(adapter: NovelListAdapter) {
        novelViewModel.allNovels.observe(this) {
            Timber.d("novels: $it")
            adapter.submitList(it)
        }
    }

    private fun initNovelList(adapter: NovelListAdapter) {
        binding.novelList.layoutManager = LinearLayoutManager(this)
        binding.novelList.adapter = adapter
    }

    private fun initButtons() {
        binding.buttonDelete.setOnClickListener {
            novelViewModel.deleteAll()
        }
        binding.button1.setOnClickListener {
            novelViewModel.insertNovelsOrderHyoka()
        }
        binding.button2.setOnClickListener {
            novelViewModel.insertNovelsOrderNew()
        }
    }

    class NovelViewModelFactory(
        private val database: NovelDatabase,
        private val narouService: NarouService
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass == NovelViewModel::class.java) {
                return NovelViewModel(
                    database,
                    narouService
                ) as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
            }
        }
    }
}
