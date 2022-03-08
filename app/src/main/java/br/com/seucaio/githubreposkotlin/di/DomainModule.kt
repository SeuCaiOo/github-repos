package br.com.seucaio.githubreposkotlin.di

import br.com.seucaio.githubreposkotlin.domain.usecase.GetRepoSearchKotlinUseCase
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object DomainModule {
    fun load() {
        loadKoinModules(module {
            factory { GetRepoSearchKotlinUseCase(repository = get()) }
        })
    }
}