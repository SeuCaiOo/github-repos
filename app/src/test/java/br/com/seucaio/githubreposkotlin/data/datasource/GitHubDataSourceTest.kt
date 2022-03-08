package br.com.seucaio.githubreposkotlin.data.datasource

import androidx.test.espresso.matcher.ViewMatchers
import app.cash.turbine.test
import br.com.seucaio.githubreposkotlin.core.stub.RepoStub
import br.com.seucaio.githubreposkotlin.data.api.GitHubService
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.IsInstanceOf
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime

private const val PAGE_ONE = 1

@ExperimentalTime
class GitHubDataSourceTest {

    private val service = mock<GitHubService>()
    private val dataSource = GitHubDataSourceImpl(service)

    @Test
    fun `getRepositoriesSearchKotlin Should call datasource and return RepoSearchResponse`() =
        runBlocking {
            // Given
            val repositoriesResponse = RepoStub.Response.repoSearch
            whenever(service.getRepositoriesSearchKotlin()).doReturn(repositoriesResponse)

            // When
            val result = dataSource.getRepositoryListKotlin(PAGE_ONE)

            // Then
            result.test {
                assertEquals(repositoriesResponse, expectItem())
                expectComplete()
            }
        }

    @Test
    fun `getRepositoriesSearchKotlin Should call datasource and throw error`() = runBlocking {
        // Given
        val expectedError = Throwable()
        whenever(service.getRepositoriesSearchKotlin()).thenAnswer { throw expectedError }

        // When
        val result = dataSource.getRepositoryListKotlin(PAGE_ONE)

        // Then
        result.test {
            val error = expectError()
            ViewMatchers.assertThat(error, IsInstanceOf(Throwable::class.java))
            assertEquals(expectedError, error)
        }
    }
}