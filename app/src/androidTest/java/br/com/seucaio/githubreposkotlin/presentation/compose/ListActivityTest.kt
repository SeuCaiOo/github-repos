package br.com.seucaio.githubreposkotlin.presentation.compose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import br.com.seucaio.githubreposkotlin.R
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.theme.GithubreposkotlinTheme
import org.junit.Rule
import org.junit.Test

class ListActivityTest {
    @Rule
    @JvmField
    val composeTestRule = createAndroidComposeRule<ListActivity>()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun testGreeting() {
        composeTestRule.setContent {
            GithubreposkotlinTheme { MyApp { MyScreenContent(uiState = ListUiState()) } }
        }

        val errorMessage = context.resources.getString(R.string.default_error_message)

        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }
}