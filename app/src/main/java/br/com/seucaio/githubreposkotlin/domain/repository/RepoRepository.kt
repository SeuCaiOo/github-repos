package br.com.seucaio.githubreposkotlin.domain.repository

import androidx.paging.PagingData
import br.com.seucaio.githubreposkotlin.data.model.RepoResponse
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.domain.entity.RepoSearch
import kotlinx.coroutines.flow.Flow

interface RepoRepository {
    fun getRepoList(): Flow<List<Repo>>
    fun getRepoPaging(): Flow<PagingData<Repo>>
}