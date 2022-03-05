package br.com.seucaio.githubreposkotlin.data.mapper

import br.com.seucaio.githubreposkotlin.core.mapper.Mapper
import br.com.seucaio.githubreposkotlin.data.model.LicenseResponse
import br.com.seucaio.githubreposkotlin.data.model.RepositoryResponse
import br.com.seucaio.githubreposkotlin.domain.entity.License

typealias LicenseMapper = Mapper<LicenseResponse, License>

class LicenseMapperImpl : LicenseMapper {
    override fun map(source: LicenseResponse): License {
        return License(
            key = source.key,
            name = source.name,
            nodeId = source.nodeId,
            spdxId = source.spdxId,
            url = source.url
        )
    }
}