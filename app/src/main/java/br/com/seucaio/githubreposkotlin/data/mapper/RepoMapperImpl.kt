package br.com.seucaio.githubreposkotlin.data.mapper

import br.com.seucaio.githubreposkotlin.data.model.OwnerResponse
import br.com.seucaio.githubreposkotlin.data.model.RepoResponse
import br.com.seucaio.githubreposkotlin.domain.entity.Owner
import br.com.seucaio.githubreposkotlin.domain.entity.Repo

class RepoMapperImpl : RepoMapper {
    override fun map(source: RepoResponse): Repo {
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