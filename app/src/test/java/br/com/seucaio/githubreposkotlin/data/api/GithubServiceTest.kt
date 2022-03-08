package br.com.seucaio.githubreposkotlin.data.api

import app.cash.turbine.test
import br.com.seucaio.githubreposkotlin.core.utils.createRetrofit
import br.com.seucaio.githubreposkotlin.core.stub.RepoStub
import br.com.seucaio.githubreposkotlin.core.ext.enqueueResponse
import br.com.seucaio.githubreposkotlin.data.datasource.GitHubDataSourceImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime

private const val REPO_SEARCH_SUCCESS_RESPONSE = "repository/repo_search_success_response.json"
private const val PAGE = 1
@ExperimentalTime
@ExperimentalCoroutinesApi
@ExperimentalSerializationApi
class GithubServiceTest {

    private val mockWebServer = MockWebServer()
    private val baseUrl = mockWebServer.url("/").toString()
    private val service = createRetrofit(baseUrl).create(GitHubService::class.java)
    private val dataSource = GitHubDataSourceImpl(service)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getRepositoryListKotlin Should return RepoSearchResponse When service returns with success`() = runBlocking {
        // Given
        val expectedResult = RepoStub.Response.repoSearch
        mockWebServer.enqueueResponse(200, REPO_SEARCH_SUCCESS_RESPONSE)

        // When
        val result = dataSource.getRepositoryListKotlin(PAGE)

        // Then
        result.test {
            assertEquals(expectedResult, expectItem())
            expectComplete()
        }
    }

    @Test
    fun `getRepositoryListKotlin Should return error id When service returns with error`() = runBlocking {
        // Given
        val expectedResult = RepoStub.Response.repoError
        mockWebServer.enqueueResponse(500)

        // When
        val result = dataSource.getRepositoryListKotlin(PAGE)

        // Then
        result.test {
//            assertEquals(expectedResult, expectItem())
            expectError()
        }
    }

}
