package br.com.seucaio.githubreposkotlin.presentation.repo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import br.com.seucaio.githubreposkotlin.databinding.ListItemRepoBinding
import br.com.seucaio.githubreposkotlin.domain.entity.Repo


class RepoListAdapter(
    private val onItemClicked: (Repo) -> Unit
) : ListAdapter<Repo, RepoListItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListItemViewHolder {
        val binding = ListItemRepoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RepoListItemViewHolder(binding).apply {
            itemView.setOnClickListener { onItemClicked(getItem(adapterPosition)) }
        }
    }

    override fun onBindViewHolder(holder: RepoListItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem == newItem
            }
        }
    }

}