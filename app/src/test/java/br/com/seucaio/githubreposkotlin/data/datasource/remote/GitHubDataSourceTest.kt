package br.com.seucaio.githubreposkotlin.data.datasource.remote

import app.cash.turbine.test
import br.com.seucaio.githubreposkotlin.core.stub.RepoStub
import br.com.seucaio.githubreposkotlin.data.api.GitHubService
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime

@ExperimentalTime
class GitHubDataSourceTest {

    private val service = mock<GitHubService>()
    private val dataSource = GitHubDataSourceImpl(service = service)

    @Test
    fun `getRepoList Should return List Repo When is success`(): Unit = runBlocking {
        // Given
        val resultExpected = RepoStub.Response.repoSearch
        whenever(service.getRepositoriesSearchKotlin(1)).doReturn(resultExpected)

        // When
        val result = dataSource.getRepositoryListKotlin()

        // Then
        result.test {
            assertEquals(resultExpected, expectItem())
            expectComplete()
        }
    }
}