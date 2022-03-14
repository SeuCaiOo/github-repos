package br.com.seucaio.githubreposkotlin.presentation.compose

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.seucaio.githubreposkotlin.R
import br.com.seucaio.githubreposkotlin.domain.entity.Owner
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.theme.GithubreposkotlinTheme
import br.com.seucaio.githubreposkotlin.presentation.repo.RepoListViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListActivity : ComponentActivity() {
    private val viewModel by viewModel<RepoListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getTempRepoList()

        setContent {
            MyApp {
                MyScreenContent(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    GithubreposkotlinTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}

@Composable
fun MyScreenContent(viewModel: RepoListViewModel) {
    with(viewModel.repoUiState) {
        Column(modifier = Modifier.fillMaxHeight()) {
            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            } else {
                if (repoItems.isNullOrEmpty().not()) {
                    RepoList(list = repoItems, modifier = Modifier.Companion.weight(1f))
                } else {
                    EmptyContent()
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

@Composable
fun RepoList(list: List<Repo>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(items = list) { repo: Repo ->
            RepoContent(repo = repo)
            Divider()
        }
    }
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun RepoContent(
    repo: Repo = Repo(
        id = 5152285,
        name = "okhttp",
        fullName = "square/okhttp",
        description = "Square’s meticulous HTTP client for the JVM, Android, and GraalVM.",
        language = "Kotlin",
        forksCount = 8792,
        stargazersCount = 41700,
        owner = Owner(
            id = 82592,
            login = "square",
            avatarUrl = "https://avatars.githubusercontent.com/u/82592?v=4",
        ),
    ),
) {

    Surface(color = MaterialTheme.colors.background) {
        Row(modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(data = repo.owner?.avatarUrl),
                contentDescription = "",
                modifier = Modifier.size(40.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${repo.name}",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.body2,
                )
                Text(
                    text = "${repo.fullName}",
                    style = MaterialTheme.typography.caption,

                    )
                Text(
                    text = "${repo.owner?.login}",
                    fontSize = 20.sp,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column() {
                Image(
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = "",
                    modifier = Modifier
                        .size(12.dp)
                        .align(CenterHorizontally)
                )
                Text(
                    text = "${repo.stargazersCount}",
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .align(CenterHorizontally)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_code_fork),
                    contentDescription = "",
                    modifier = Modifier
                        .size(12.dp)
                        .align(CenterHorizontally)
                )
                Text(
                    text = "${repo.forksCount}",
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .align(CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun EmptyContent() {
    Column(modifier = Modifier.fillMaxHeight()) {
        Text(
            text = stringResource(id = R.string.default_error_message),
            textAlign = TextAlign.Center,
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
        RepoContent()
//        MyScreenContent(getViewModel())
    }
}
