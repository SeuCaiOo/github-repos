package br.com.seucaio.githubreposkotlin.domain.usecase

import androidx.test.espresso.matcher.ViewMatchers
import app.cash.turbine.test
import br.com.seucaio.githubreposkotlin.core.stub.RepositoryStub
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

@ExperimentalTime
class GetRepositoryListKotlinUseCaseTest {
    private val repository: GitHubRepository = mock()
    private val useCase = GetRepositoryListKotlinUseCase(repository = repository)

    @Test
    fun `invoke Should return List Repository When is success`() = runBlocking {
        // Given
        whenever(repository.getRepositoryListKotlin()).doReturn(flow { emit(RepositoryStub.Model.repositories) })

        // When
        val result = useCase.invoke()

        // Then
        result.test {
            assertEquals(RepositoryStub.Model.repositories, expectItem())
            expectComplete()
        }
    }

    @Test
    fun `invoke Should return Error When with error`() = runBlocking {
        // Given
        val expectedError = Throwable()
        whenever(repository.getRepositoryListKotlin()).doReturn(flow { throw expectedError })

        // When
        val result = useCase.invoke()

        // Then
        result.test {
            val error = expectError()
            ViewMatchers.assertThat(error, IsInstanceOf(Throwable::class.java))
            assertEquals(expectedError, error)
        }
    }

}