package br.com.seucaio.githubreposkotlin.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.seucaio.githubreposkotlin.R
import br.com.seucaio.githubreposkotlin.databinding.ActivityMainBinding
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.presentation.repo.RepoListViewModel
import br.com.seucaio.githubreposkotlin.presentation.repo.adapter.list.RepoListAdapter
import br.com.seucaio.githubreposkotlin.presentation.repo.adapter.paging.ReposAdapterPaging
import br.com.seucaio.githubreposkotlin.presentation.repo.adapter.paging.ReposLoadStateAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<RepoListViewModel>()
    private val adapter by lazy { RepoListAdapter(clickRepository()) }
    private val adapterPaging by lazy { ReposAdapterPaging() }

    private fun clickRepository(): (Repo) -> Unit = {
        Snackbar.make(binding.root, "#${it.id}", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    private var searchJob: Job? = null

    private fun search() {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchRepo().collectLatest {
                adapterPaging.submitData(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar()
        setupRecyclerView()

        initAdapterPaging()
        search()
        initSearch()

        binding.retryButton.setOnClickListener { adapterPaging.retry() }

    }


    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.apply {
            title = getString(R.string.repo_search_fragment_label)
            setNavigationOnClickListener { onBackPressed() }
        }
    }

    private fun setupRecyclerView() {
        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            binding.recyclerView.adapter = adapter
            adapter.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
    }


    private fun initAdapterPaging() {
        binding.recyclerView.adapter = adapterPaging.withLoadStateHeaderAndFooter(
            header = ReposLoadStateAdapter { adapterPaging.retry() },
            footer = ReposLoadStateAdapter { adapterPaging.retry() }
        )
        onLoadStateListener()
        adapterPaging.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private fun onLoadStateListener() {
        adapterPaging.run {
            addLoadStateListener { loadState ->
                val itemsIsEmpty = itemCount == 0
                val listIsEmpty = loadState.refresh is LoadState.NotLoading && itemsIsEmpty
                val isLoading = loadState.source.refresh is LoadState.Loading
                val hasError = loadState.source.refresh is LoadState.Error && itemsIsEmpty
                val showList = loadState.source.refresh is LoadState.NotLoading
                        || loadState.source.refresh is LoadState.NotLoading
                with(binding) {
                    emptyList.isVisible = listIsEmpty
                    // Only show the list if refresh succeeds.
                    recyclerView.isVisible = showList && isLoading.not()
                    // Show loading spinner during initial load or refresh.
                    progressBar.isVisible = isLoading
                    // Show the retry state if initial load or refresh fails.
                    retryButton.isVisible = hasError
                }
                // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        this@MainActivity,
                        "\uD83D\uDE28 Wooops ${it.error}",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        }
    }


    private fun initSearch() {
        // Scroll to top when the list is refreshed from network.
        lifecycleScope.launch {
            adapterPaging.loadStateFlow
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect()
        }
    }
}