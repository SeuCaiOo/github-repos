package br.com.seucaio.githubreposkotlin.domain.usecase

import br.com.seucaio.githubreposkotlin.data.model.RepositoriesResponse
import br.com.seucaio.githubreposkotlin.domain.entity.Repos
import br.com.seucaio.githubreposkotlin.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow

class GetReposKotlinUseCase (private val repository: GitHubRepository) {
     operator fun invoke(): Flow<Repos> {
         return repository.getRepositoriesSearchKotlin()
     }
}