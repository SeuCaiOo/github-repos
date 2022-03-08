package br.com.seucaio.githubreposkotlin.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.collect
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
        setupRecyclerView()


        initAdapterPaging()
        search()
        initSearch()

        binding.retryButton.setOnClickListener { adapterPaging.retry() }

        setupToolbar()
//        viewModel.getRepositories()
        onStateChange()
    }


    private fun initAdapterPaging() {
        binding.recyclerView.adapter = adapterPaging.withLoadStateHeaderAndFooter(
            header = ReposLoadStateAdapter { adapterPaging.retry() },
            footer = ReposLoadStateAdapter { adapterPaging.retry() }
        )
        adapterPaging.addLoadStateListener { loadState ->
            // show empty list
            val isListEmpty = loadState.refresh is LoadState.NotLoading && adapterPaging.itemCount == 0
            showEmptyList(isListEmpty)

            // Only show the list if refresh succeeds.
            binding.recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading

            // todo mostrar loading corretamente e remover dataSource
            binding.progressBar.isVisible = true
            // Show the retry state if initial load or refresh fails.
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    this,
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        adapterPaging.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
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


    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.emptyList.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        } else {
            binding.emptyList.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        }
    }


    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.apply {
            title = getString(R.string.repo_search_fragment_label)
            setNavigationOnClickListener { onBackPressed() }
            inflateMenu(R.menu.menu_main)
            setupOptionsMenuItem(menu)
        }

    }

    private fun setupOptionsMenuItem(menu: Menu) {
        val optionsMenuItem = menu.findItem(R.id.action_settings)
        optionsMenuItem?.let {
            it.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            it.setOnMenuItemClickListener {
                Snackbar.make(
                    binding.root, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
                true
            }
        }
    }


    private fun setupRecyclerView() {
        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            binding.recyclerView.adapter = adapter
            adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
    }

    private fun onStateChange() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    showRepos(uiState.repos)
                    handleLoading(uiState.isLoading)
                    if (uiState.hasError) showError()
                }
            }
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.recyclerView.isGone = isLoading
    }

    private fun showRepos(repos: List<Repo>?) {
        repos?.let { adapter.submitList(it) }
    }

    private fun showError() {
        val message = getString(R.string.error)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}