package br.com.seucaio.githubreposkotlin.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.seucaio.githubreposkotlin.domain.entity.Repo

class FakeRepoPagingSource() : PagingSource<Int, Repo>() {
    var triggerError = false
    var posts: List<Repo> = emptyList()
        set(value) {
            println("set")
            field = value
            invalidate()
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        println("load")
        if (triggerError) {
            return LoadResult.Error(Exception("A test error triggered"))
        }
        println("not error")

        return LoadResult.Page(
            data = posts,
            prevKey = null,
            nextKey = null
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        println("refresh")

        return state.anchorPosition ?: 1
    }
}