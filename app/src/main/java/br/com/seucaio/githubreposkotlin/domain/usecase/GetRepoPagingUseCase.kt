package br.com.seucaio.githubreposkotlin.domain.usecase

import androidx.paging.PagingData
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.domain.repository.GitHubRepository
import br.com.seucaio.githubreposkotlin.domain.repository.RepoRepository
import kotlinx.coroutines.flow.Flow

class GetRepoPagingUseCase(private val repository: RepoRepository) {
    operator fun invoke(): Flow<PagingData<Repo>> {
        return repository.getRepoPaging()
    }
}