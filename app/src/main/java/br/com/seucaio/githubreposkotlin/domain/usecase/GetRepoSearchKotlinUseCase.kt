package br.com.seucaio.githubreposkotlin.domain.usecase

import br.com.seucaio.githubreposkotlin.domain.entity.RepoSearch
import br.com.seucaio.githubreposkotlin.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow

class GetRepoSearchKotlinUseCase (private val repository: GitHubRepository) {
     operator fun invoke(page: Int): Flow<RepoSearch> {
         return repository.getRepositoryListKotlin(page)
     }
}