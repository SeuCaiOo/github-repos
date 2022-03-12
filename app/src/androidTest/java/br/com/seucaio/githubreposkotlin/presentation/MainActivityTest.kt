//package br.com.seucaio.githubreposkotlin.presentation
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.lifecycle.Lifecycle
//import androidx.test.core.app.launchActivity
//import androidx.test.espresso.Espresso
//import androidx.test.espresso.assertion.ViewAssertions
//import androidx.test.espresso.matcher.ViewMatchers
//import androidx.test.espresso.matcher.ViewMatchers.withId
//import androidx.test.platform.app.InstrumentationRegistry
//import br.com.seucaio.githubreposkotlin.R
//import br.com.seucaio.githubreposkotlin.RecyclerViewMatchers.checkRecyclerViewItem
//import br.com.seucaio.githubreposkotlin.core.ext.getResponse
//import br.com.seucaio.githubreposkotlin.core.ext.readResponse
//import br.com.seucaio.githubreposkotlin.core.utils.createRetrofit
//import br.com.seucaio.githubreposkotlin.data.api.GitHubService
//import br.com.seucaio.githubreposkotlin.data.datasource.remote.GitHubPagingSource
//import br.com.seucaio.githubreposkotlin.data.mapper.RepoMapperImpl
//import br.com.seucaio.githubreposkotlin.data.repository.GitHubRepositoryImpl
//import br.com.seucaio.githubreposkotlin.domain.repository.GitHubRepository
//import br.com.seucaio.githubreposkotlin.domain.usecase.GetRepoSearchKotlinUseCase
//import br.com.seucaio.githubreposkotlin.presentation.repo.RepoListViewModel
//import okhttp3.mockwebserver.Dispatcher
//import okhttp3.mockwebserver.MockResponse
//import okhttp3.mockwebserver.MockWebServer
//import okhttp3.mockwebserver.RecordedRequest
//import org.junit.After
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.koin.androidx.viewmodel.dsl.viewModel
//import org.koin.core.context.loadKoinModules
//import org.koin.core.context.unloadKoinModules
//import org.koin.core.module.Module
//import org.koin.dsl.module
//import retrofit2.Retrofit
//
//
//private const val REPO_SEARCH_SUCCESS_RESPONSE = "repository/repo_search_success_response.json"
//
//class MainActivityTest {
//
//    // Executes each task synchronously using Architecture Components.
//    @get:Rule
//    var instantExecutorRule = InstantTaskExecutorRule()
//    private val context = InstrumentationRegistry.getInstrumentation().targetContext
//
//
//    private val server = MockWebServer()
//    private val baseUrl = server.url("/").toString()
////    private val service = createRetrofit(baseUrl).create(GitHubService::class.java)
////    private val remoteDataSource = GitHubDataSourceImpl(service)
////    private lateinit var remoteDataSource: GitHubDataSource
//
//    private lateinit var service: GitHubService
//
//    private lateinit var mockModule: Module
//
//
//
//    @Before
//    fun setup() {
//        service = createRetrofit(baseUrl).create(GitHubService::class.java)
//
////        mockModule = module(override = true) {
////            factory { get<Retrofit>().create(GitHubService::class.java) }
////            factory { GitHubPagingSource(get()) }
////            single<GitHubRepository> {
////                GitHubRepositoryImpl(
////                    pagingSource = get(),
////                    mapper = RepoMapperImpl()
////                )
////            }
////            single { GetRepoSearchKotlinUseCase(get<GitHubRepository>()) }
////            viewModel { RepoListViewModel(get()) }
////        }
////
////        loadKoinModules(mockModule)
//    }
//
//    @After
//    fun after() {
//        server.shutdown()
//        unloadKoinModules(mockModule)
//    }
//
//    @Test
//    fun shouldDisplayToolbarTitle() {
//        launchActivity<MainActivity>().apply {
//            val expectedTitle = context.getString(R.string.repo_search_fragment_label)
//
//            moveToState(Lifecycle.State.RESUMED)
//
//            Espresso.onView(ViewMatchers.withText(expectedTitle))
//                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
//        }
//    }
//
//    @Test
//    fun shouldDisplayListItem() {
//        server.dispatcher = object : Dispatcher() {
//            override fun dispatch(request: RecordedRequest): MockResponse {
//                return when (request.path) {
//                    "search/repositories" -> successResponse
//                    "/search/repositories" -> successResponse
//                    else -> errorResponse
//                }
//            }
//        }
//        server.enqueue(
//            server.getResponse(200, REPO_SEARCH_SUCCESS_RESPONSE)
//        )
//
//        launchActivity<MainActivity>().apply {
//            Espresso.onView(withId(R.id.recyclerView))
//                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
//            checkRecyclerViewItem(R.id.recyclerView, 0, ViewMatchers.withText("square"))
//        }
//
//        server.close()
//    }
//
//
//    companion object {
//
//        private val successResponse by lazy {
//            val body = REPO_SEARCH_SUCCESS_RESPONSE.readResponse()
//            MockResponse()
//                .setResponseCode(200)
//                .setBody(body)
//        }
//
//        private val errorResponse by lazy { MockResponse().setResponseCode(404) }
//    }
//
//}