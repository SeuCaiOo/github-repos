package br.com.seucaio.githubreposkotlin.presentation.compose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.seucaio.githubreposkotlin.R
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.theme.GithubreposkotlinTheme

const val ProgressIndicatorTestTag = "ProgressIndicatorTestTag"
const val RepoListTestTag = "RepoListTestTag"
const val EmptyContentTestTag = "EmptyContentTestTag"

@Composable
fun MyApp(content: @Composable () -> Unit) {
    GithubreposkotlinTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.repo_list_label))
                    }
                )
            }
        ) {
            Surface {
                Column(modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) { content() }
            }
        }
    }
}