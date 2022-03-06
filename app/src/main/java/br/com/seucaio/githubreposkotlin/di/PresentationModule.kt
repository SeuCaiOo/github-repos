package br.com.seucaio.githubreposkotlin.di

import br.com.seucaio.githubreposkotlin.presentation.repository.RepositoryListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object PresentationModule {
    fun load() {
        loadKoinModules(module {
            viewModel { RepositoryListViewModel(useCase = get()) }
        })
    }
}