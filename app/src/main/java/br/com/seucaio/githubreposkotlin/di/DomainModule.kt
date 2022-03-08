package br.com.seucaio.githubreposkotlin.di

import br.com.seucaio.githubreposkotlin.data.mapper.RepoMapperImpl
import br.com.seucaio.githubreposkotlin.domain.usecase.GetRepoSearchKotlinUseCase
import br.com.seucaio.githubreposkotlin.domain.usecase.GetRepoStreamUseCase
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object DomainModule {
    fun load() {
        loadKoinModules(module {
            factory { GetRepoSearchKotlinUseCase(repository = get()) }
            factory {
                GetRepoStreamUseCase(
                    repository = get(), mapper = RepoMapperImpl(),
                )
            }
        })
    }
}