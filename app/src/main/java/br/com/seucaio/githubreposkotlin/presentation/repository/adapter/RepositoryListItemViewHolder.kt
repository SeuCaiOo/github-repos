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
                .error(R.drawable.ic_error_24)
                .into(ivAvatar, object : Callback {
                    override fun onSuccess() {
                        progressBar.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        progressBar.visibility = View.GONE
                    }
                })
            val icPrivate = if (repository.isPrivate) R.drawable.ic_lock_24 else R.drawable.ic_public_24
            ivPrivate.setImageResource(icPrivate)
            tvName.text = repository.name
            tvFullName.text = repository.fullName
            tvDescription.text = repository.description
        }
    }
}