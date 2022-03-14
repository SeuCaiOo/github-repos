package br.com.seucaio.githubreposkotlin.presentation.compose

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import br.com.seucaio.githubreposkotlin.R
import org.junit.Rule
import org.junit.Test

class ListActivityTest {
    @Rule
    @JvmField
    val composeTestRule = createAndroidComposeRule<ListActivity>()

    @Test
    fun testGreeting() {
        val errorMessage = InstrumentationRegistry.getInstrumentation()
            .targetContext.resources.getString(R.string.default_error_message)


        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }
}