package br.com.seucaio.githubreposkotlin.domain.usecase

import br.com.seucaio.githubreposkotlin.domain.entity.Repositories
import br.com.seucaio.githubreposkotlin.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow

class GetRepositoryListKotlinUseCase (private val repository: GitHubRepository) {
     operator fun invoke(page: Int): Flow<Repositories> {
         return repository.getRepositoryListKotlin(page)
     }
}