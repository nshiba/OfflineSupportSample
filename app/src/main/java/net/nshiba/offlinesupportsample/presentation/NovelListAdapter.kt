package net.nshiba.offlinesupportsample.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import net.nshiba.offlinesupportsample.data.model.NovelEntity
import net.nshiba.offlinesupportsample.databinding.ItemNovelBinding

class NovelListAdapter
    : PagedListAdapter<NovelEntity, NovelListAdapter.NovelViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NovelViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NovelViewHolder(ItemNovelBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: NovelViewHolder, position: Int) {
        val novel = getItem(position)
        holder.bind(novel)
    }

    class NovelViewHolder(
        private val binding: ItemNovelBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(novel: NovelEntity?) {
            binding.novel = novel
            binding.executePendingBindings()
        }
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NovelEntity>() {

            override fun areItemsTheSame(oldItem: NovelEntity, newItem: NovelEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: NovelEntity, newItem: NovelEntity): Boolean {
                return oldItem == newItem
            }

        }
    }
}