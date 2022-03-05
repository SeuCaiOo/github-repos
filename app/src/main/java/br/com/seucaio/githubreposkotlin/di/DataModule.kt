package br.com.seucaio.githubreposkotlin.di

import br.com.seucaio.githubreposkotlin.data.datasource.GitHubDataSource
import br.com.seucaio.githubreposkotlin.data.datasource.GitHubDataSourceImpl
import br.com.seucaio.githubreposkotlin.data.mapper.ReposMapper
import br.com.seucaio.githubreposkotlin.data.mapper.ReposMapperImpl
import br.com.seucaio.githubreposkotlin.data.repository.GitHubRepositoryImpl
import br.com.seucaio.githubreposkotlin.domain.repository.GitHubRepository
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object DataModule {
    fun load() {
        loadKoinModules(module {
            factory<ReposMapper> { ReposMapperImpl() }
            factory<GitHubDataSource> { GitHubDataSourceImpl(service = get()) }
            factory<GitHubRepository> { GitHubRepositoryImpl(dataSource = get(), mapper = get()) }
        })
    }
}