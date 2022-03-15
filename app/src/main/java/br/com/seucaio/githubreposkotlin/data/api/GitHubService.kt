package br.com.seucaio.githubreposkotlin.data.api

import br.com.seucaio.githubreposkotlin.data.model.RepoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {

    @GET("search/repositories?q=language:kotlin&sort=stars")
    suspend fun searchRepos(
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): RepoSearchResponse

    @GET("search/repositories?q=language:kotlin&sort=stars")
    suspend fun getRepositoriesSearchKotlin(
        @Query("page")  page : Int = 1
    ) : RepoSearchResponse


    companion object {
        const val GITHUB_STARTING_PAGE_INDEX = 1
        const val BASE_URL = "https://api.github.com/"
    }

}
