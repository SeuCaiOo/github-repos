package br.com.seucaio.githubreposkotlin.presentation.view.adapter.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import br.com.seucaio.githubreposkotlin.domain.entity.Repo


class RepoListAdapter(
    private val onItemClicked: (Repo) -> Unit
) : ListAdapter<Repo, RepoListItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListItemViewHolder {
        return RepoListItemViewHolder.create(parent).apply {
            itemView.setOnClickListener { onItemClicked(getItem(bindingAdapterPosition)) }
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