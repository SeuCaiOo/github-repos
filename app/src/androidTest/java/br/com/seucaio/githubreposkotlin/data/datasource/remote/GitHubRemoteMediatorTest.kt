package br.com.seucaio.githubreposkotlin.data.datasource.remote

import androidx.paging.*
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.seucaio.githubreposkotlin.core.stub.RepoStub
import br.com.seucaio.githubreposkotlin.data.datasource.local.RepoDatabase
import br.com.seucaio.githubreposkotlin.data.mapper.RepoMapperImpl
import br.com.seucaio.githubreposkotlin.api.FakeRepoService
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalPagingApi
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class GitHubRemoteMediatorTest {

    private val mockApi = FakeRepoService()
    private val mockDb = RepoDatabase.getInstance(
        ApplicationProvider.getApplicationContext()
    )
    private val remoteMediator by lazy {
        GitHubRemoteMediator(database = mockDb, service = mockApi, mapper = RepoMapperImpl())
    }
    private val pagingState by lazy {
        PagingState<Int, Repo>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(10),
            leadingPlaceholderCount = 10
        )
    }

    @After
    fun tearDown() {
        mockDb.clearAllTables()
        // Clear out failure message to default to the successful response.
        mockApi.failureMsg = null
        // Clear out repos after each test run.
        mockApi.clearRepos()
    }

    /*@Test
    fun appendLoadReturnsSuccessResultWhenMoreDataIsPresent() = runBlocking {
        // Add mock results for the API to return.
//        mockRepos.forEach { repo -> mockApi.addRepo(repo) }
        mockApi.addRepo(RepoStub.Response.repo)

        val repos = listOf(RepoStub.Model.repo) ?: emptyList()

        val page = PagingSource.LoadResult.Page<Int, Repo>(
            data = repos,
            prevKey = -1,
            nextKey = 2
        )

        val remoteMediator = GitHubRemoteMediator(
            database = mockDb,
            service = mockApi,
            mapper = RepoMapperImpl()
        )
        val pagingState = PagingState<Int, Repo>(
            pages = listOf<PagingSource.LoadResult.Page<Int, Repo>>(page),
            anchorPosition = null,
            config = PagingConfig(10),
            leadingPlaceholderCount = 10
        )
        val result = remoteMediator.load(LoadType.APPEND, pagingState)



        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }*/


    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runBlocking {
        // Add mock results for the API to return.
        // Given
        mockApi.addRepo(RepoStub.Response.repo)

        // When
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        // Then
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun refreshLoadSuccessAndEndOfPaginationWhenNoMoreData() = runBlocking {
        // To test endOfPaginationReached, don't set up the mockApi to return repo
        // data here.

        // When
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        // Then
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() = runBlocking {
        // Set up failure message to throw exception from the mock API.
        // Given
        mockApi.failureMsg = "Throw test failure"

        // When
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        // Then
        assertTrue(result is RemoteMediator.MediatorResult.Error)
    }
}