package br.com.seucaio.githubreposkotlin.data.repository

import androidx.test.espresso.matcher.ViewMatchers
import app.cash.turbine.test
import br.com.seucaio.githubreposkotlin.core.stub.RepositoryStub
import br.com.seucaio.githubreposkotlin.data.datasource.GitHubDataSource
import br.com.seucaio.githubreposkotlin.data.mapper.RepositoriesMapperImpl
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.IsInstanceOf
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime

@ExperimentalTime
class GitHubRepositoryTest {

    private val dataSource = mock<GitHubDataSource>()
    private val mapper = RepositoriesMapperImpl()

    private val repository = GitHubRepositoryImpl(
        dataSource = dataSource,
        mapper = mapper
    )

    @Test
    fun `getRepositoryListKotlin Should return List Repository When is success`(): Unit = runBlocking {
        // Given
        whenever(dataSource.getRepositoryListKotlin())
            .doReturn(flow { emit(RepositoryStub.Response.repositories) })

        // When
        val result = repository.getRepositoryListKotlin()

        // Then
        result.test {
            assertEquals(RepositoryStub.Model.repositories, expectItem())
            expectComplete()
        }
    }

    @Test
    fun `getRepositoryListKotlin Should return Error When with error`(): Unit = runBlocking {
        // Given
        val expectedError = Throwable()
        whenever(dataSource.getRepositoryListKotlin()).doReturn(flow { throw expectedError })

        // When
        val result = repository.getRepositoryListKotlin()

        // Then
        result.test {
            val error = expectError()
            ViewMatchers.assertThat(error, IsInstanceOf(Throwable::class.java))
            assertEquals(expectedError, error)
        }
    }
}