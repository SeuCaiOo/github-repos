package br.com.seucaio.githubreposkotlin.di

import br.com.seucaio.githubreposkotlin.presentation.compose.ui.list.ListViewModel
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.paging.PagingViewModel
import br.com.seucaio.githubreposkotlin.presentation.view.RepoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object PresentationModule {
    fun load() {
        loadKoinModules(module {
            viewModel { RepoListViewModel(useCasePaging = get()) }
            viewModel { ListViewModel(useCase = get()) }
            viewModel { PagingViewModel(useCase = get()) }
        })
    }
}