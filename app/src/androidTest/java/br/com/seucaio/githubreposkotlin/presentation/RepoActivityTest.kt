package br.com.seucaio.githubreposkotlin.presentation

import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
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

class RepoActivityTest {

    private lateinit var mockModule: Module
    private val mockApi = FakeRepoService()
    private val mockDb = RepoDatabase.getInstance(
        ApplicationProvider.getApplicationContext()
    )

    // Executes each task synchronously using Architecture Components.
//    @get:Rule
//    var instantExecutorRule = InstantTaskExecutorRule()
//    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setup() {
        mockModule = module() {
            factory<GitHubService> { mockApi }
            factory {
                GitHubRemoteMediator(
                    service = get(),
                    database = mockDb,
                    mapper = RepoMapperImpl()
                )
            }
            single<GitHubRepository> {
                GitHubRepositoryImpl(
                    remoteMediator = get(),
                    database = mockDb
                )
            }
        }

        loadKoinModules(mockModule)
    }

    @After
    fun after() {
//        server.shutdown()
        unloadKoinModules(mockModule)
    }

    @Test
    fun shouldDisplayListItem() {
        mockApi.addRepo(RepoStub.Response.repo)
        launchActivity<MainActivity>().apply {

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
        launchActivity<MainActivity>().apply {
            onView(withId(R.id.emptyList))
                .check(matches(isDisplayed()))
            onView(withId(R.id.emptyList))
                .check(matches(withText("Lista vazia")))
        }
    }

    @Test
    fun shouldDisplayButtonRetry() {
        mockApi.failureMsg = "Throw test failure"
        launchActivity<MainActivity>().apply {
            onView(allOf(
                withId(R.id.btnRetryGetList),
                withText("Tentar novamente"),
                isDisplayed())
            )
        }
    }

    /*@Test
    fun shouldDisplayToolbarTitle() {
        launchActivity<MainActivity>().apply {
            val expectedTitle = context.getString(R.string.repo_search_fragment_label)
            moveToState(Lifecycle.State.RESUMED)
            onView(ViewMatchers.withText(expectedTitle))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }
    }*/


    /* @Test
     fun loadsTheDefaultResults() {
         ActivityScenario.launch(MainActivity::class.java)

         onView(withId(R.id.recyclerView)).check { view, noViewFoundException ->
             if (noViewFoundException != null) {
                 throw noViewFoundException
             }

             val recyclerView = view as RecyclerView
             assertEquals(1, recyclerView.adapter?.itemCount)
         }
     }*/

    /*@Test
    // Verify that the default data is swapped out when the user searches for a
    // different subreddit.
    fun loadsTheTestResultsWhenSearchingForSubreddit() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.recyclerView)).check { view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            // Verify that it loads the default data first.
            assertEquals(1, recyclerView.adapter?.itemCount)
        }

        launchActivity<MainActivity>().apply {
            onView(withId(R.id.recyclerView)).check { view, noViewFoundException ->

                if (noViewFoundException != null) throw noViewFoundException

                val recyclerView = view as RecyclerView
                assertEquals(1, recyclerView.adapter?.itemCount)
            }
            checkRecyclerViewItem(R.id.recyclerView, 0, ViewMatchers.withText("square"))
            onView(withId(R.id.recyclerView)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }


//        onView(withId(R.id.recyclerView)).check { view, noViewFoundException ->
//            if (noViewFoundException != null) {
//                throw noViewFoundException
//            }
//
//            val recyclerView = view as RecyclerView
//            assertEquals(2, recyclerView.adapter?.itemCount)
//        }
    }*/

}