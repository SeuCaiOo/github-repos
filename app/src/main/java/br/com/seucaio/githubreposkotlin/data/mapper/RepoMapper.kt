package br.com.seucaio.githubreposkotlin.data.mapper

import br.com.seucaio.githubreposkotlin.data.model.RepoResponse
import br.com.seucaio.githubreposkotlin.domain.entity.Repo

interface RepoMapper  {
    fun map(source: RepoResponse): Repo
}

