package br.com.seucaio.githubreposkotlin.di

import br.com.seucaio.githubreposkotlin.data.datasource.GitHubDataSource
import br.com.seucaio.githubreposkotlin.data.datasource.GitHubDataSourceImpl
import br.com.seucaio.githubreposkotlin.data.mapper.RepositoriesMapper
import br.com.seucaio.githubreposkotlin.data.mapper.RepositoriesMapperImpl
import br.com.seucaio.githubreposkotlin.data.repository.GitHubRepositoryImpl
import br.com.seucaio.githubreposkotlin.domain.repository.GitHubRepository
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object DataModule {
    fun load() {
        loadKoinModules(module {
            factory<RepositoriesMapper> { RepositoriesMapperImpl() }
            factory<GitHubDataSource> { GitHubDataSourceImpl(service = get()) }
            factory<GitHubRepository> { GitHubRepositoryImpl(dataSource = get(), mapper = get()) }
        })
    }
}