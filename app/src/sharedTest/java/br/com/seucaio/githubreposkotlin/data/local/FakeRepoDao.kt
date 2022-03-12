package br.com.seucaio.githubreposkotlin.data.local

import androidx.paging.PagingSource
import br.com.seucaio.githubreposkotlin.data.datasource.local.RepoDao
import br.com.seucaio.githubreposkotlin.data.remote.FakeRepoPagingSource
import br.com.seucaio.githubreposkotlin.domain.entity.Repo

class FakeRepoDao(
    val repoList: MutableList<Repo> = mutableListOf(), val hasError: Boolean = false,
) : RepoDao {
//     val repoList: MutableList<Repo> = mutableListOf()
    private val pagingSource = FakeRepoPagingSource()

    fun throwError(hasError: Boolean = true) {
        pagingSource.triggerError = hasError
    }

    override suspend fun insertAll(repos: List<Repo>) {
        this.repoList.addAll(repos)
        println("insertPosts")
        updatePagingSource()
    }

    override fun repos(): PagingSource<Int, Repo> {
        println("getPostsPagingSource")
        return pagingSource
    }

    override suspend fun clearRepos() {
        repoList.clear()
        updatePagingSource()
    }


    private fun updatePagingSource() {
        println("updatePagingSource")
        pagingSource.repoList = repoList
    }

}