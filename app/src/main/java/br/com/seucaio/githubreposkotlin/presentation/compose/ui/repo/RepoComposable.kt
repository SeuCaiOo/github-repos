package br.com.seucaio.githubreposkotlin.presentation.compose.ui.repo

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import br.com.seucaio.githubreposkotlin.R
import br.com.seucaio.githubreposkotlin.domain.entity.Owner
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.MyApp
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.list.ListUiState
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.list.MyScreenContent
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter

@Composable
fun RepoList(list: List<Repo>, modifier: Modifier = Modifier,
             contentExtra: @Composable () -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(items = list) { repo: Repo -> RepoContent(repo = repo) }
        list.apply { item { contentExtra() } }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun RepoContent(repo: Repo = fakeRepo) {
    Card(
        modifier = Modifier.padding(4.dp),
        elevation = 8.dp,
    ) {
        Surface {
            Row(modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberImagePainter(data = repo.owner?.avatarUrl),
                    contentDescription = "",
                    modifier = Modifier.size(42.dp),
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
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "${repo.stargazersCount}",
                        fontSize = 12.sp,
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_code_fork),
                        contentDescription = "",
                        modifier = Modifier
                            .size(12.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "${repo.forksCount}",
                        fontSize = 12.sp,
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
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
                errorMessage = ""
            ))
    }
}

val fakeRepo = Repo(
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
)