package br.com.seucaio.githubreposkotlin.data.api

import br.com.seucaio.githubreposkotlin.core.utils.createRetrofit
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import kotlin.time.ExperimentalTime

private const val REPO_SEARCH_SUCCESS_RESPONSE = "repository/repo_search_success_response.json"
@ExperimentalTime
@ExperimentalCoroutinesApi
@ExperimentalSerializationApi
class GithubServiceTest {

    private val mockWebServer = MockWebServer()
    private val baseUrl = mockWebServer.url("/").toString()
    private val service = createRetrofit(baseUrl).create(GitHubService::class.java)
//    private val pagingSource = GitHubPagingSource(service)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    /*@Test
    fun `getRepositoryListKotlin Should return RepoSearchResponse When service returns with success`() = runBlocking {
        // Given
        val expectedResult = RepoStub.Response.repoSearch
        mockWebServer.enqueueResponse(200, REPO_SEARCH_SUCCESS_RESPONSE)

        // When
        val result = pagingSource.load(PagingSource.LoadParams<Int>())

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
        val result = pagingSource.load()

        // Then
        result.test {
//            assertEquals(expectedResult, expectItem())
            expectError()
        }
    }*/

}
