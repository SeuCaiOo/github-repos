package br.com.seucaio.githubreposkotlin.data.mapper

import br.com.seucaio.githubreposkotlin.core.mapper.Mapper
import br.com.seucaio.githubreposkotlin.data.model.OwnerResponse
import br.com.seucaio.githubreposkotlin.data.model.RepoSearchResponse
import br.com.seucaio.githubreposkotlin.data.model.RepoResponse
import br.com.seucaio.githubreposkotlin.domain.entity.Owner
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.domain.entity.RepoSearch

typealias RepoSearchMapper = Mapper<RepoSearchResponse, RepoSearch>

class RepoSearchMapperImpl : RepoSearchMapper {
    override fun map(source: RepoSearchResponse): RepoSearch {
        return RepoSearch(
            incompleteResults = source.incompleteResults,
            totalCount = source.totalCount,
            items = source.items?.map { mapperRepo(it) },
        )
    }

    private fun mapperRepo(source: RepoResponse): Repo {
        return Repo(
            id = source.id,
            name = source.name,
            fullName = source.fullName,
            description = source.description,
            language = source.language,
            forksCount = source.forksCount,
            stargazersCount = source.stargazersCount,
            owner = source.owner?.let { mapperOwner(it) },
        )
    }



    private fun mapperOwner(source: OwnerResponse): Owner {
        return Owner(
            id = source.id,
            login = source.login,
            avatarUrl = source.avatarUrl,
        )
    }
}