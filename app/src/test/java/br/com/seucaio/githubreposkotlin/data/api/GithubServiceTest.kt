package br.com.seucaio.githubreposkotlin.data.api

import app.cash.turbine.test
import br.com.seucaio.githubreposkotlin.core.utils.createRetrofit
import br.com.seucaio.githubreposkotlin.core.stub.RepositoryStub
import br.com.seucaio.githubreposkotlin.core.enqueueResponse
import br.com.seucaio.githubreposkotlin.data.datasource.GitHubDataSourceImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime

private const val REPOSITORY_LIST_SUCCESS_RESPONSE = "repository/repository_list_success_response.json"
private const val REPOSITORY_LIST_ERROR_RESPONSE = "repository/repository_list_error_response.json"
private const val REPOSITORY_SUCCESS_RESPONSE = "repository/repository_success_response.json"

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
    fun `getRepositoryListKotlin Should return RepositoriesResponse When service returns with success`() = runBlocking {
        // Given
        val expectedResult = RepositoryStub.Response.repositories
        mockWebServer.enqueueResponse(200, REPOSITORY_LIST_SUCCESS_RESPONSE)

        // When
        val result = dataSource.getRepositoryListKotlin()

        // Then
        result.test {
            assertEquals(expectedResult, expectItem())
            expectComplete()
        }
    }

    @Test
    fun `getRepositoryListKotlin Should return error id When service returns with error`() = runBlocking {
        // Given
        val expectedResult = RepositoryStub.Response.repositoriesError
        mockWebServer.enqueueResponse(500)

        // When
        val result = dataSource.getRepositoryListKotlin()

        // Then
        result.test {
//            assertEquals(expectedResult, expectItem())
            expectError()
        }
    }

}
