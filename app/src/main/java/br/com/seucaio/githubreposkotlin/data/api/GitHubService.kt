package br.com.seucaio.githubreposkotlin.data.api

import br.com.seucaio.githubreposkotlin.data.model.RepositoriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {

    @GET("repositories")
    suspend fun getRepositories() : RepositoriesResponse


    @GET("search/repositories")
    suspend fun getRepositoriesSearchKotlin(
        @Query("q")  query: String = "language:kotlin",
        @Query("sort")  sort: String = "stars",
        @Query("page")  page : Int = 1
    ) : RepositoriesResponse


    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

}