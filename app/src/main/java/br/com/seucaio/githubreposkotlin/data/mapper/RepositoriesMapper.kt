package br.com.seucaio.githubreposkotlin.data.mapper

import br.com.seucaio.githubreposkotlin.core.mapper.Mapper
import br.com.seucaio.githubreposkotlin.data.model.LicenseResponse
import br.com.seucaio.githubreposkotlin.data.model.OwnerResponse
import br.com.seucaio.githubreposkotlin.data.model.RepositoriesResponse
import br.com.seucaio.githubreposkotlin.data.model.RepositoryResponse
import br.com.seucaio.githubreposkotlin.domain.entity.License
import br.com.seucaio.githubreposkotlin.domain.entity.Owner
import br.com.seucaio.githubreposkotlin.domain.entity.Repository
import br.com.seucaio.githubreposkotlin.domain.entity.Repositories

typealias RepositoriesMapper = Mapper<RepositoriesResponse, Repositories>

class RepositoriesMapperImpl : RepositoriesMapper {
    override fun map(source: RepositoriesResponse): Repositories {
        return Repositories(
            incompleteResults = source.incompleteResults,
            items = source.items?.map { mapperRepo(it) },
            totalCount = source.totalCount,
        )
    }

    private fun mapperRepo(source: RepositoryResponse): Repository {
        return Repository(
            allowForking = source.allowForking,
            archiveUrl = source.archiveUrl,
            archived = source.archived,
            assigneesUrl = source.assigneesUrl,
            blobsUrl = source.blobsUrl,
            branchesUrl = source.branchesUrl,
            cloneUrl = source.cloneUrl,
            collaboratorsUrl = source.collaboratorsUrl,
            commentsUrl = source.commentsUrl,
            commitsUrl = source.commitsUrl,
            compareUrl = source.compareUrl,
            contentsUrl = source.contentsUrl,
            contributorsUrl = source.contributorsUrl,
            createdAt = source.createdAt,
            defaultBranch = source.defaultBranch,
            deploymentsUrl = source.deploymentsUrl,
            description = source.description,
            disabled = source.disabled,
            downloadsUrl = source.downloadsUrl,
            eventsUrl = source.eventsUrl,
            fork = source.fork,
            forks = source.forks,
            forksCount = source.forksCount,
            forksUrl = source.forksUrl,
            fullName = source.fullName,
            gitCommitsUrl = source.gitCommitsUrl,
            gitRefsUrl = source.gitRefsUrl,
            gitTagsUrl = source.gitTagsUrl,
            gitUrl = source.gitUrl,
            hasDownloads = source.hasDownloads,
            hasIssues = source.hasIssues,
            hasPages = source.hasPages,
            hasProjects = source.hasProjects,
            hasWiki = source.hasWiki,
            homepage = source.homepage,
            hooksUrl = source.hooksUrl,
            htmlUrl = source.htmlUrl,
            id = source.id,
            isTemplate = source.isTemplate,
            issueCommentUrl = source.issueCommentUrl,
            issueEventsUrl = source.issueEventsUrl,
            issuesUrl = source.issuesUrl,
            keysUrl = source.keysUrl,
            labelsUrl = source.labelsUrl,
            language = source.language,
            languagesUrl = source.languagesUrl,
            license = source.license?.let { mapperLicense(it) },
            mergesUrl = source.mergesUrl,
            milestonesUrl = source.milestonesUrl,
            mirrorUrl = source.mirrorUrl,
            name = source.name,
            nodeId = source.nodeId,
            notificationsUrl = source.notificationsUrl,
            openIssues = source.openIssues,
            openIssuesCount = source.openIssuesCount,
            owner = source.owner?.let { mapperOwner(it) },
            isPrivate = source.isPrivate,
            pullsUrl = source.pullsUrl,
            pushedAt = source.pushedAt,
            releasesUrl = source.releasesUrl,
            score = source.score,
            size = source.size,
            sshUrl = source.sshUrl,
            stargazersCount = source.stargazersCount,
            stargazersUrl = source.stargazersUrl,
            statusesUrl = source.statusesUrl,
            subscribersUrl = source.subscribersUrl,
            subscriptionUrl = source.subscriptionUrl,
            svnUrl = source.svnUrl,
            tagsUrl = source.tagsUrl,
            teamsUrl = source.teamsUrl,
            topics = source.topics,
            treesUrl = source.treesUrl,
            updatedAt = source.updatedAt,
            url = source.url,
            visibility = source.visibility,
            watchers = source.watchers,
            watchersCount = source.watchersCount,
        )
    }

    private fun mapperLicense(source: LicenseResponse): License {
        return License(
            key = source.key,
            name = source.name,
            nodeId = source.nodeId,
            spdxId = source.spdxId,
            url = source.url
        )
    }

    private fun mapperOwner(source: OwnerResponse): Owner {
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