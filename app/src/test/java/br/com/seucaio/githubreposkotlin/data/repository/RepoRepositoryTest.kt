package br.com.seucaio.githubreposkotlin.data.repository

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import app.cash.turbine.test
import br.com.seucaio.githubreposkotlin.core.stub.RepoStub
import br.com.seucaio.githubreposkotlin.data.datasource.remote.GitHubDataSource
import br.com.seucaio.githubreposkotlin.data.datasource.remote.GitHubPagingSource
import br.com.seucaio.githubreposkotlin.data.mapper.RepoMapperImpl
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.IsInstanceOf
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.time.ExperimentalTime

@ExperimentalTime
class RepoRepositoryTest {

    private val dataSource = mock<GitHubDataSource>()
    private val pagingSource = mock<GitHubPagingSource>()
    private val mapper = RepoMapperImpl()

    private val repository = RepoRepositoryImpl(
        dataSource = dataSource,
        pagingSource = pagingSource,
        mapper = mapper
    )

    @Test
    fun `getRepoList Should return List Repo When is success`(): Unit = runBlocking {
        // Given
        val resultExpected = RepoStub.Response.repoSearch
        whenever(dataSource.getRepositoryListKotlin()).doReturn(flow { emit(resultExpected) })

        // When
        val result: Flow<List<Repo>> = repository.getRepoList()

        // Then
        result.test {
            val items = resultExpected.items?.map { mapper.map(it) } ?: listOf()
            assertEquals(items, expectItem())
            expectComplete()
        }
    }

    @Test
    fun `getRepoList Should return Error When with error`(): Unit = runBlocking {
        // Given
        val expectedError = Throwable()
        whenever(dataSource.getRepositoryListKotlin()).doReturn(flow { throw expectedError })

        // When
        val result = repository.getRepoList()

        // Then
        result.test {
            val error = expectError()
            assertThat(error, IsInstanceOf(Throwable::class.java))
            assertEquals(expectedError, error)
        }
    }
}