package br.com.seucaio.githubreposkotlin.data.mapper

import br.com.seucaio.githubreposkotlin.core.mapper.Mapper
import br.com.seucaio.githubreposkotlin.data.model.OwnerResponse
import br.com.seucaio.githubreposkotlin.domain.entity.Owner

typealias OwnerMapper = Mapper<OwnerResponse, Owner>

class OwnerMapperImpl : OwnerMapper {
    override fun map(source: OwnerResponse): Owner {
        return Owner(
            avatarUrl = source.avatarUrl,
            eventsUrl = source.eventsUrl,
            followersUrl = source.followersUrl,
            followingUrl = source.followingUrl,
            gistsUrl = source.gistsUrl,
            gravatarId = source.gravatarId,
            htmlUrl = source.htmlUrl,
            id = source.id,
            login = source.login,
            nodeId = source.nodeId,
            organizationsUrl = source.organizationsUrl,
            receivedEventsUrl = source.receivedEventsUrl,
            reposUrl = source.reposUrl,
            siteAdmin = source.siteAdmin,
            starredUrl = source.starredUrl,
            subscriptionsUrl = source.subscriptionsUrl,
            type = source.type,
            url = source.url
        )
    }
}