package br.com.seucaio.githubreposkotlin.di

import br.com.seucaio.githubreposkotlin.data.datasource.GitHubDataSource
import br.com.seucaio.githubreposkotlin.data.datasource.GitHubDataSourceImpl
import br.com.seucaio.githubreposkotlin.data.mapper.RepoSearchMapper
import br.com.seucaio.githubreposkotlin.data.mapper.RepoSearchMapperImpl
import br.com.seucaio.githubreposkotlin.data.repository.GitHubRepositoryImpl
import br.com.seucaio.githubreposkotlin.domain.repository.GitHubRepository
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object DataModule {
    fun load() {
        loadKoinModules(module {
            factory<RepoSearchMapper> { RepoSearchMapperImpl() }
            factory<GitHubDataSource> { GitHubDataSourceImpl(service = get()) }
            factory<GitHubRepository> { GitHubRepositoryImpl(dataSource = get(), mapper = get()) }
        })
    }
}