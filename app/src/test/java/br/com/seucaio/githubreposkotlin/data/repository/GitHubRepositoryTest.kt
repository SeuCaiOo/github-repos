package br.com.seucaio.githubreposkotlin.data.repository

import kotlin.time.ExperimentalTime

@ExperimentalTime
class GitHubRepositoryTest {

//    private val pagingSource = mock<GitHubPagingSource>()
//
//    private val repository = GitHubRepositoryImpl(
//        pagingSource = pagingSource,
//        mapper = RepoMapperImpl()
//    )

    /*@Test
    fun `getRepositoryListKotlin Should return RepoSearch When is success`(): Unit = runBlocking {
        // Given
        whenever(pagingSource.load())
            .doReturn(flow { emit(RepoStub.Response.repoSearch) })

        // When
        val result = repository.getRepositoryListKotlin()

        // Then
        result.test {
            assertEquals(RepoStub.Model.repoSearch, expectItem())
            expectComplete()
        }
    }

    @Test
    fun `getRepositoryListKotlin Should return Error When with error`(): Unit = runBlocking {
        // Given
        val expectedError = Throwable()
        whenever(pagingSource.load()).doReturn(flow { throw expectedError })

        // When
        val result = repository.getRepositoryListKotlin()

        // Then
        result.test {
            val error = expectError()
            ViewMatchers.assertThat(error, IsInstanceOf(Throwable::class.java))
            assertEquals(expectedError, error)
        }
    }*/
}