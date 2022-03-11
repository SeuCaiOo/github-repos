package br.com.seucaio.githubreposkotlin.core.stub

import br.com.seucaio.githubreposkotlin.data.datasource.local.RemoteKeys
import br.com.seucaio.githubreposkotlin.data.model.OwnerResponse
import br.com.seucaio.githubreposkotlin.data.model.RepoErrorResponse
import br.com.seucaio.githubreposkotlin.data.model.RepoResponse
import br.com.seucaio.githubreposkotlin.data.model.RepoSearchResponse
import br.com.seucaio.githubreposkotlin.domain.entity.Owner
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.domain.entity.RepoSearch

object RepoStub {
    object Response {
        private val owner = OwnerResponse(
            login = "square",
            id = 82592,
            avatarUrl = "https://avatars.githubusercontent.com/u/82592?v=4",
        )
        val repo = RepoResponse(
            id = 5152285,
            name = "okhttp",
            fullName = "square/okhttp",
            description = "Square’s meticulous HTTP client for the JVM, Android, and GraalVM.",
            forksCount = 8792,
            stargazersCount = 41700,
            language = "Kotlin",
            owner = owner,
        )

        val repoSearch = RepoSearchResponse(
            totalCount = 758480,
            incompleteResults = false,
            items = listOf(repo)
        )

        val repoError = RepoErrorResponse(
            message = "",
            errors = listOf(
                RepoErrorResponse.ErrorResponse(
                    resource = "Search",
                    field = "q",
                    code = "missing"
                )
            ),
            documentationUrl = "https://docs.github.com/v3/search"
        )

    }

    object Model {

        val remoteKeys = RemoteKeys(repoId = 1, 1, 3)

        private val owner = Owner(
            id = 82592,
            login = "square",
            avatarUrl = "https://avatars.githubusercontent.com/u/82592?v=4",
        )

        val repo = Repo(
            id = 5152285,
            name = "okhttp",
            fullName = "square/okhttp",
            description = "Square’s meticulous HTTP client for the JVM, Android, and GraalVM.",
            language = "Kotlin",
            forksCount = 8792,
            stargazersCount = 41700,
            owner = owner,
        )

        val repoSearch = RepoSearch(
            totalCount = 758480,
            incompleteResults = false,
            items = listOf(repo)
        )
    }

}