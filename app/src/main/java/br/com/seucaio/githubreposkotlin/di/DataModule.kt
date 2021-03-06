package br.com.seucaio.githubreposkotlin.di

import br.com.seucaio.githubreposkotlin.data.datasource.local.RepoDao
import br.com.seucaio.githubreposkotlin.data.datasource.local.RepoDatabase
import br.com.seucaio.githubreposkotlin.data.datasource.remote.GitHubDataSource
import br.com.seucaio.githubreposkotlin.data.datasource.remote.GitHubDataSourceImpl
import br.com.seucaio.githubreposkotlin.data.datasource.remote.GitHubPagingSource
import br.com.seucaio.githubreposkotlin.data.datasource.remote.GitHubRemoteMediator
import br.com.seucaio.githubreposkotlin.data.mapper.RepoMapperImpl
import br.com.seucaio.githubreposkotlin.data.repository.GitHubRepositoryImpl
import br.com.seucaio.githubreposkotlin.data.repository.RepoRepositoryImpl
import br.com.seucaio.githubreposkotlin.domain.repository.GitHubRepository
import br.com.seucaio.githubreposkotlin.domain.repository.RepoRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object DataModule {
    fun load() {
        loadKoinModules(module {
            single<RepoDatabase> { RepoDatabase.getInstance(androidContext()) }
            factory<RepoDao> { get<RepoDatabase>().reposDao() }
            factory {
                GitHubRemoteMediator(service = get(), database = get(), mapper = RepoMapperImpl())
            }
            factory<GitHubRepository> {
                GitHubRepositoryImpl(remoteMediator = get(), database = get())
            }
            factory<GitHubDataSource> { GitHubDataSourceImpl(service = get()) }
            factory { GitHubPagingSource(service = get()) }
            factory<RepoRepository> {
                RepoRepositoryImpl(
                    pagingSource = get(), dataSource = get(), mapper = RepoMapperImpl()
                )
            }
        })
    }
}