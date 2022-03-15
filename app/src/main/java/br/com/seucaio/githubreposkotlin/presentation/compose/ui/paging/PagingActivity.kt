package br.com.seucaio.githubreposkotlin.presentation.compose.ui.paging

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.EmptyContentTestTag
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.MyApp
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.repo.EmptyContent
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.repo.RepoContent
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.repo.ShowProgress
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.viewmodel.ext.android.viewModel

class PagingActivity : ComponentActivity() {

    private val viewModelPaging by viewModel<PagingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                PagingScreenContent(viewModelPaging.getRepoPaging())
            }
        }
    }
}

@Composable
fun PagingScreenContent(repoPaging: Flow<PagingData<Repo>>) {
    val list: LazyPagingItems<Repo> = repoPaging.collectAsLazyPagingItems()


    // Remember our own LazyListState
    val listState = rememberLazyListState()

    // Remember a CoroutineScope to be able to launch
    val coroutineScope = rememberCoroutineScope()
    LazyColumn(state = listState, modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {
        items(list) { repo ->
            if (repo != null) RepoContent(repo = repo)
        }
        list.apply {
            with(loadState) {
                // show empty list
                val isListEmpty = itemCount == 0
                val isLoading = refresh is LoadState.Loading || append is LoadState.Loading
                if (isLoading) {
                    item {
                        Column {
                            if (isListEmpty) {
                                ShowProgress(modifier = Modifier
                                    .align(CenterHorizontally)
                                    .fillMaxWidth())
                            } else {
                                ShowProgress(modifier = Modifier
                                    .align(CenterHorizontally)
                                    .fillMaxWidth())
                            }
                        }

                    }
                } else {
                    item {
                        Column {
                             if (source.refresh is LoadState.Error) {
                                 EmptyContent(modifier = Modifier
                                     .testTag(EmptyContentTestTag)
                                     .align(CenterHorizontally)
                                     .fillMaxHeight()
                                 )
                             }
                        }
                    }
                }

                // Only show the list if refresh succeeds.
//            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

                val errorState = source.append as? LoadState.Error
                    ?: source.prepend as? LoadState.Error
                    ?: append as? LoadState.Error
                    ?: prepend as? LoadState.Error
                errorState?.let {
                    item {
                        Snackbar(modifier = Modifier.padding(8.dp)) {
                            Text(text = "\uD83D\uDE28 Ops ${it.error}")
                        }
                    }
                }
            }

        }
    }
}