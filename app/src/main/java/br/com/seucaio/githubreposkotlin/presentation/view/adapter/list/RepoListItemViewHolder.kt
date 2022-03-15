package br.com.seucaio.githubreposkotlin.presentation.view.adapter.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.seucaio.githubreposkotlin.R
import br.com.seucaio.githubreposkotlin.databinding.ListItemRepoBinding
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class RepoListItemViewHolder(
    private val binding: ListItemRepoBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(repo: Repo) {
        with(binding) {
            progressBar.visibility = View.VISIBLE
            Picasso.get()
                .load(repo.owner?.avatarUrl)
                .error(R.drawable.ic_image)
                .into(ivOwnerAvatar, object : Callback {
                    override fun onSuccess() {
                        progressBar.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        progressBar.visibility = View.GONE
                    }
                })

            tvName.text = repo.name
            tvFullName.text = repo.fullName
            tvOwnerName.text = repo.owner?.login
            tvStars.text = repo.stargazersCount.toString()
            tvForks.text = repo.forksCount.toString()
        }
    }


    companion object {
        fun create(parent: ViewGroup): RepoListItemViewHolder {
            val binding = ListItemRepoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return RepoListItemViewHolder(binding)
        }
    }
}