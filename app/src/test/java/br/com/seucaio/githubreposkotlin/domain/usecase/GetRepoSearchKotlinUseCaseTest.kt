package br.com.seucaio.githubreposkotlin.domain.usecase

import androidx.test.espresso.matcher.ViewMatchers
import app.cash.turbine.test
import br.com.seucaio.githubreposkotlin.core.stub.RepoStub
import br.com.seucaio.githubreposkotlin.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.flow
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
class GetRepoSearchKotlinUseCaseTest {
    private val repository: GitHubRepository = mock()
    private val useCase = GetRepoSearchKotlinUseCase(repository = repository)

    @Test
    fun `invoke Should return List Repo When is success`() = runBlocking {
        // Given
        whenever(repository.getRepositoryListKotlin(PAGE_ONE))
            .doReturn(flow { emit(RepoStub.Model.repoSearch) })

        // When
        val result = useCase.invoke(PAGE_ONE)

        // Then
        result.test {
            assertEquals(RepoStub.Model.repoSearch, expectItem())
            expectComplete()
        }
    }

    @Test
    fun `invoke Should return Error When with error`() = runBlocking {
        // Given
        val expectedError = Throwable()
        whenever(repository.getRepositoryListKotlin(PAGE_ONE)).doReturn(flow { throw expectedError })

        // When
        val result = useCase.invoke(PAGE_ONE)

        // Then
        result.test {
            val error = expectError()
            ViewMatchers.assertThat(error, IsInstanceOf(Throwable::class.java))
            assertEquals(expectedError, error)
        }
    }

}