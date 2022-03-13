package br.com.seucaio.githubreposkotlin.domain.usecase

import androidx.paging.PagingData
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow

class GetRepoSearchKotlinUseCase(private val repository: GitHubRepository) {
    operator fun invoke(): Flow<PagingData<Repo>> {
        return repository.getRepositoryListKotlin()
    }
}