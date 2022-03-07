package br.com.seucaio.githubreposkotlin.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.seucaio.githubreposkotlin.R
import br.com.seucaio.githubreposkotlin.databinding.ActivityMainBinding
import br.com.seucaio.githubreposkotlin.domain.entity.Repositories
import br.com.seucaio.githubreposkotlin.domain.entity.Repository
import br.com.seucaio.githubreposkotlin.presentation.repository.RepositoryListViewModel
import br.com.seucaio.githubreposkotlin.presentation.repository.adapter.RepositoryListAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModel<RepositoryListViewModel>()
    private val adapter by lazy { RepositoryListAdapter(clickRepository()) }

    private fun clickRepository(): (Repository) -> Unit = {
        Snackbar.make(binding.root, "#${it.id}", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.recyclerView.adapter = adapter
        setupToolbar()
        viewModel.getRepositories()
        onStateChange()
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
//        setSupportActionBar(toolbar)
        toolbar.apply {
            title = getString(R.string.repository_list_fragment_label)
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

    private fun onStateChange() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    showRepos(uiState.repositories)
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

    private fun showRepos(repositories: List<Repository>?) {
        repositories?.let {
            with(binding) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                adapter.submitList(it)
                recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        handleScrollToLastItem(layoutManager, adapter)
                    }
                })
            }
        }
    }

    private fun handleScrollToLastItem(
        layoutManager: LinearLayoutManager,
        adapter: RepositoryListAdapter,
    ) {
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        if (lastVisibleItemPosition == adapter.itemCount - 1) {
            Snackbar.make(binding.root, "Carregar mais itens", Snackbar.LENGTH_LONG).show()
            viewModel.getNextPage()
        }
    }

    private fun showError() {
        val message = getString(R.string.error)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}