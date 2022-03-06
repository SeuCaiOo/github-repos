package br.com.seucaio.githubreposkotlin.presentation.repository.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.seucaio.githubreposkotlin.R
import br.com.seucaio.githubreposkotlin.databinding.ListItemRepositoryBinding
import br.com.seucaio.githubreposkotlin.domain.entity.Repository
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class RepositoryListItemViewHolder(
    private val binding: ListItemRepositoryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(repository: Repository) {
        with(binding) {
            progressBar.visibility = View.VISIBLE
            Picasso.get()
                .load(repository.owner?.avatarUrl)
                .error(R.drawable.ic_image)
                .into(ivOwnerAvatar, object : Callback {
                    override fun onSuccess() {
                        progressBar.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        progressBar.visibility = View.GONE
                    }
                })

            tvName.text = repository.name
            tvFullName.text = repository.fullName
            tvOwnerName.text = repository.owner?.login
            tvStars.text = repository.stargazersCount.toString()
            tvForks.text = repository.forksCount.toString()
        }
    }
}