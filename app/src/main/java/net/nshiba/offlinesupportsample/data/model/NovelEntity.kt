package net.nshiba.offlinesupportsample.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NovelEntity(
    @PrimaryKey val ncode: String,
    val title: String,
    val writer: String,
    val order: Int
)
