package br.com.seucaio.githubreposkotlin.presentation.compose.ui.list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import br.com.seucaio.githubreposkotlin.R
import br.com.seucaio.githubreposkotlin.core.stub.RepoStub
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.EmptyContentTestTag
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.MyApp
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.ProgressIndicatorTestTag
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.RepoListTestTag
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.list.*
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.theme.GithubreposkotlinTheme
import org.junit.Rule
import org.junit.Test

class ListActivityTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ListActivity>()

    private val activity by lazy { composeTestRule.activity }
    private val fakeUiState by lazy { ListUiState() }

    @Test
    fun showEmptyContent() {
        composeTestRule.setContent {
            GithubreposkotlinTheme {
                MyApp { MyScreenContent(uiState = fakeUiState) }
            }
        }

        composeTestRule.onNodeWithTag(EmptyContentTestTag).assertExists()
        composeTestRule.onNodeWithTag(ProgressIndicatorTestTag).assertDoesNotExist()
        composeTestRule.onNodeWithTag(RepoListTestTag).assertDoesNotExist()

        val errorMessage = activity.getString(R.string.msg_error_list_repositories)
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun showLoading() {
        composeTestRule.setContent {
            GithubreposkotlinTheme {
                MyApp {
                    MyScreenContent(uiState = fakeUiState.copy(isLoading = true))
                }
            }
        }

        composeTestRule.onNodeWithTag(ProgressIndicatorTestTag).assertExists()
        composeTestRule.onNodeWithTag(EmptyContentTestTag).assertDoesNotExist()
        composeTestRule.onNodeWithTag(RepoListTestTag).assertDoesNotExist()
    }

    @Test
    fun showRepoList() {
        composeTestRule.setContent {
            GithubreposkotlinTheme {
                MyApp {
                    MyScreenContent(
                        uiState = fakeUiState.copy(
                            repoItems = List(4) { RepoStub.Model.repo }
                        )
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag(RepoListTestTag).assertExists()
        composeTestRule.onNodeWithTag(EmptyContentTestTag).assertDoesNotExist()
        composeTestRule.onNodeWithTag(ProgressIndicatorTestTag).assertDoesNotExist()
    }
}