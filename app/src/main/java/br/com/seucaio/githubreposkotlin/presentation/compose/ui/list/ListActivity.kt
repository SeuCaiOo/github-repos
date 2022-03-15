package br.com.seucaio.githubreposkotlin.presentation.compose.ui.list

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.seucaio.githubreposkotlin.R
import br.com.seucaio.githubreposkotlin.domain.entity.Owner
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.EmptyContentTestTag
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.MyApp
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.ProgressIndicatorTestTag
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.RepoListTestTag
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.repo.*
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.theme.GithubreposkotlinTheme
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListActivity : ComponentActivity() {
    private val viewModel by viewModel<ListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getTempRepoList()

        setContent {
            MyApp { MyScreenContent(uiState = viewModel.uiState) }
        }
    }
}


@Composable
fun MyScreenContent(uiState: ListUiState) {
    with(uiState) {
        Column() {
            if (isLoading) {
                ShowProgress(modifier = Modifier
                    .testTag(ProgressIndicatorTestTag)
                    .align(CenterHorizontally)
                    .fillMaxWidth())
            } else {
                if (repoItems.isNullOrEmpty().not()) {
                    RepoList(
                        list = repoItems,
                        modifier = Modifier
                            .testTag(RepoListTestTag)
                            .weight(1f),
                        contentExtra = {})
                } else {
                    EmptyContent(modifier = Modifier
                        .testTag(EmptyContentTestTag)
                        .align(CenterHorizontally)
                        .fillMaxHeight()
                    )
                }
            }
        }
    }
}


@Composable
fun NameList(names: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(items = names) { name: String ->
            Greeting(name = name)
            Divider()
        }
    }
}

@Composable
fun Counter(count: Int, updateCount: ((Int) -> Unit)) {
    Button(onClick = { updateCount(count + 1) }) {
        Text(text = "Voce clicou $count vezes")
    }
}

@Composable
fun Greeting(name: String) {
    var isSelected by remember { mutableStateOf(false) }
    val targetColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colors.primary else Color.Transparent,
        animationSpec = tween(durationMillis = 1000)
    )
    Surface(color = targetColor) {
        Text(
            text = "Hello $name!",
            modifier = Modifier
                .clickable { isSelected = !isSelected }
                .padding(16.dp)
        )
    }
}


@Preview(name = "Light Mode", showBackground = true)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun DefaultPreview() {
    MyApp {
        MyScreenContent(
            uiState = ListUiState(
//                errorMessage = "",
//                isLoading = true
                repoItems = List<Repo>(10) { fakeRepo }
            )
        )

//        RepoContent()
//        MyScreenContent(getViewModel())
    }
}
