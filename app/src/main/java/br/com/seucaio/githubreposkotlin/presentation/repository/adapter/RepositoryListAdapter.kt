package br.com.seucaio.githubreposkotlin.presentation.repository.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import br.com.seucaio.githubreposkotlin.databinding.ListItemRepositoryBinding
import br.com.seucaio.githubreposkotlin.domain.entity.Repository


class RepositoryListAdapter(
    private val onItemClicked: (Repository) -> Unit
) : ListAdapter<Repository, RepositoryListItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryListItemViewHolder {
        val binding = ListItemRepositoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RepositoryListItemViewHolder(binding).apply {
            itemView.setOnClickListener { onItemClicked(getItem(adapterPosition)) }
        }
    }

    override fun onBindViewHolder(holder: RepositoryListItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                return oldItem == newItem
            }
        }
    }

}