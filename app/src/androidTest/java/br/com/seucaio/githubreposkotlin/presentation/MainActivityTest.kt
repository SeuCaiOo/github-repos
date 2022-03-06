package br.com.seucaio.githubreposkotlin.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import br.com.seucaio.githubreposkotlin.R
import br.com.seucaio.githubreposkotlin.core.utils.createRetrofit
import br.com.seucaio.githubreposkotlin.data.api.GitHubService
import br.com.seucaio.githubreposkotlin.data.datasource.GitHubDataSourceImpl
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val mockWebServer = MockWebServer()
    private val baseUrl = mockWebServer.url("/").toString()
    private val service = createRetrofit(baseUrl).create(GitHubService::class.java)
    private val dataSource = GitHubDataSourceImpl(service)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun shouldDisplayTitle() {
        launchActivity<MainActivity>().apply {
            val expectedTitle = context.getString(R.string.repository_list_fragment_label)

            moveToState(Lifecycle.State.RESUMED)

            Espresso.onView(ViewMatchers.withText(expectedTitle))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }
    }

}