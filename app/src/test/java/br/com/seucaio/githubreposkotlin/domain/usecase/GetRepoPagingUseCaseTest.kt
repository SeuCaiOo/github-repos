package br.com.seucaio.githubreposkotlin.domain.usecase

import androidx.paging.PagingData
import app.cash.turbine.test
import br.com.seucaio.githubreposkotlin.core.stub.RepoStub
import br.com.seucaio.githubreposkotlin.domain.repository.RepoRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsInstanceOf
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime

@ExperimentalTime
class GetRepoPagingUseCaseTest {
    private val repository: RepoRepository = mock()
    private val useCase = GetRepoPagingUseCase(repository = repository)

    @Test
    fun `invoke Should return PagingData Repo When is success`() = runBlocking {
        // Given
        val expectedResult = PagingData.from(data = listOf(RepoStub.Model.repo))
        whenever(repository.getRepoPaging()).doReturn(flow { emit(expectedResult) })

        // When
        val result = useCase.invoke()

        // Then
        result.test {
            assertEquals(expectedResult, expectItem())
            expectComplete()
        }
    }

    @Test
    fun `invoke Should return Error When with error`() = runBlocking {
        // Given
        val expectedError = Throwable()
        whenever(repository.getRepoPaging()).doReturn(flow { throw expectedError })

        // When
        val result = useCase.invoke()

        // Then
        result.test {
            val error = expectError()
            assertThat(error, IsInstanceOf(Throwable::class.java))
            assertEquals(expectedError, error)
        }
    }
}