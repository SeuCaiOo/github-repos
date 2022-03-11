package br.com.seucaio.githubreposkotlin.api

import br.com.seucaio.githubreposkotlin.data.api.GitHubService
import br.com.seucaio.githubreposkotlin.data.model.RepoResponse
import br.com.seucaio.githubreposkotlin.data.model.RepoSearchResponse
import java.io.IOException

class FakeRepoService() : GitHubService {
    private val repoList: MutableList<RepoResponse> = mutableListOf()
    var failureMsg: String? = null

    fun addRepo(repo: RepoResponse) {
        repoList.add(repo)
    }

    fun clearRepos() {
        repoList.clear()
    }

    override suspend fun searchRepos(page: Int, itemsPerPage: Int): RepoSearchResponse {
        failureMsg?.let {
            throw IOException(it)
        }
        println("Loaded")
        return RepoSearchResponse(incompleteResults = false, totalCount = 1, items = repoList)
    }

    override suspend fun getRepositoriesSearchKotlin(page: Int): RepoSearchResponse {
        failureMsg?.let {
            throw IOException(it)
        }
        println("Loaded")
        return RepoSearchResponse(incompleteResults = false, totalCount = 1, items = repoList)
    }
}