package br.com.seucaio.githubreposkotlin.presentation.repo.adapter.paging

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.presentation.repo.adapter.list.RepoListItemViewHolder

class ReposAdapterPaging : PagingDataAdapter<Repo, RepoListItemViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListItemViewHolder {
        return RepoListItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RepoListItemViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            holder.bind(repoItem)
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem.fullName == newItem.fullName

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem == newItem
        }
    }
}