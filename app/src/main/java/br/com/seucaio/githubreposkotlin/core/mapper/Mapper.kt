package br.com.seucaio.githubreposkotlin.core.mapper

interface Mapper<S, T> {
    fun map(source: S): T
}
