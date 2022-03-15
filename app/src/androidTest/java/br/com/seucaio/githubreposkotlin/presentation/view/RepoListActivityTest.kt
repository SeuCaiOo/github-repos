package br.com.seucaio.githubreposkotlin.presentation.view

import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import br.com.seucaio.githubreposkotlin.R
import br.com.seucaio.githubreposkotlin.RecyclerViewMatchers.checkRecyclerViewItem
import br.com.seucaio.githubreposkotlin.api.FakeRepoService
import br.com.seucaio.githubreposkotlin.core.stub.RepoStub
import br.com.seucaio.githubreposkotlin.data.api.GitHubService
import br.com.seucaio.githubreposkotlin.data.datasource.local.RepoDatabase
import br.com.seucaio.githubreposkotlin.data.datasource.remote.GitHubRemoteMediator
import br.com.seucaio.githubreposkotlin.data.mapper.RepoMapperImpl
import br.com.seucaio.githubreposkotlin.data.repository.GitHubRepositoryImpl
import br.com.seucaio.githubreposkotlin.domain.repository.GitHubRepository
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

class RepoListActivityTest {

    private lateinit var mockModule: Module
    private val mockApi = FakeRepoService()
    private val mockDb = RepoDatabase.getInstance(ApplicationProvider.getApplicationContext())

    @Before
    fun setup() {
        val mapper = RepoMapperImpl()
        mockModule = module() {
            factory<GitHubService> { mockApi }
            factory {
                GitHubRemoteMediator(service = get(), database = mockDb, mapper = mapper)
            }
            single<GitHubRepository> {
                GitHubRepositoryImpl(remoteMediator = get(), database = mockDb)
            }
        }
        loadKoinModules(mockModule)
    }

    @After
    fun after() {
        unloadKoinModules(mockModule)
    }

    @Test
    fun shouldDisplayListItem() {
        mockApi.addRepo(RepoStub.Response.repo)
        launchActivity<RepoListActivity>().apply {

            moveToState(Lifecycle.State.RESUMED)

            onView(withId(R.id.recyclerView)).check { view, noViewFoundException ->
                if (noViewFoundException != null) throw noViewFoundException
                val recyclerView = view as RecyclerView
                assertEquals(1, recyclerView.adapter?.itemCount)
            }
            checkRecyclerViewItem(R.id.recyclerView, 0, withText("square"))
        }
    }

    @Test
    fun shouldDisplayEmptyList() {
        launchActivity<RepoListActivity>().apply {
            onView(withId(R.id.emptyList))
                .check(matches(isDisplayed()))
            onView(withId(R.id.emptyList))
                .check(matches(withText("Lista vazia")))
        }
    }

    @Test
    fun shouldDisplayButtonRetry() {
        mockApi.failureMsg = "Throw test failure"
        launchActivity<RepoListActivity>().apply {
            onView(allOf(
                withId(R.id.btnRetryGetList),
                withText("Tentar novamente"),
                isDisplayed())
            )
        }
    }
}