package br.com.seucaio.githubreposkotlin.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import br.com.seucaio.githubreposkotlin.data.mapper.RepoMapper
import br.com.seucaio.githubreposkotlin.data.model.RepoResponse
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.domain.entity.RepoSearch
import br.com.seucaio.githubreposkotlin.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetRepoStreamUseCase(
    private val repository: GitHubRepository,
    private val mapper: RepoMapper,
) {
    operator fun invoke(): Flow<PagingData<Repo>> {
        return repository.getSearchResultStream().map {
            it.map { repoResponse -> mapper.map(repoResponse) }
        }
    }
}