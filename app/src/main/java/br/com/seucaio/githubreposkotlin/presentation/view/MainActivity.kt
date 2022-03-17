package br.com.seucaio.githubreposkotlin.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.seucaio.githubreposkotlin.R
import br.com.seucaio.githubreposkotlin.databinding.ActivityMainBinding
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.list.ListActivity
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.paging.PagingActivity

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar()
        setupListeners()
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.apply {
            title = getString(R.string.repo_list_label)
            setNavigationOnClickListener { onBackPressed() }
        }
    }

    private fun setupListeners() {
        binding.btnViewPaging.setOnClickListener {
            navigationTo(Intent(this, RepoListActivity::class.java))
        }
        binding.btnComposeList.setOnClickListener {
            navigationTo(Intent(this, ListActivity::class.java))
        }
        binding.btnComposePaging.setOnClickListener {
            navigationTo(Intent(this, PagingActivity::class.java))
        }
    }

    private fun navigationTo(intent: Intent) = startActivity(intent)
}