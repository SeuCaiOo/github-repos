package br.com.seucaio.githubreposkotlin.data.datasource.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.seucaio.githubreposkotlin.data.api.GitHubService
import br.com.seucaio.githubreposkotlin.data.model.RepoResponse
import br.com.seucaio.githubreposkotlin.data.repository.GitHubRepositoryImpl.Companion.NETWORK_PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException

private const val GITHUB_STARTING_PAGE_INDEX = 1

class GitHubPagingSource(
    private val service: GitHubService
) : PagingSource<Int, RepoResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepoResponse> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        return try {
            val response = service.searchRepos(position, params.loadSize)
            val repos = response.items ?: emptyList()
            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            val prevKey = if (position == GITHUB_STARTING_PAGE_INDEX) null else position - 1
            LoadResult.Page(
                data = repos,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RepoResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}