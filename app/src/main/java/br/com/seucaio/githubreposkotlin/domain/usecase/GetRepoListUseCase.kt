package br.com.seucaio.githubreposkotlin.domain.usecase

import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.domain.repository.GitHubRepository
import br.com.seucaio.githubreposkotlin.domain.repository.RepoRepository
import kotlinx.coroutines.flow.Flow

class GetRepoListUseCase(private val repository: RepoRepository) {
    operator fun invoke(): Flow<List<Repo>> {
        return repository.getRepoList()
    }
}