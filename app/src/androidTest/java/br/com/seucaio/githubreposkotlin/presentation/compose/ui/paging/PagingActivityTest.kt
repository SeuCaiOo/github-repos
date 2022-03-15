package br.com.seucaio.githubreposkotlin.presentation.compose.ui.paging

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Assert.*
import org.junit.Rule

class PagingActivityTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<PagingActivity>()

    private val activity by lazy { composeTestRule.activity }

}