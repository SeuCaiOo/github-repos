package br.com.seucaio.githubreposkotlin.data.local

import br.com.seucaio.githubreposkotlin.data.datasource.local.RemoteKeys
import br.com.seucaio.githubreposkotlin.data.datasource.local.RemoteKeysDao

class FakeRemoteKeysDao(val keys: MutableList<RemoteKeys?> = mutableListOf()) : RemoteKeysDao {
    override suspend fun insertAll(remoteKeys: List<RemoteKeys?>) {
        this.keys.addAll(remoteKeys)
        println("insertAll")
    }

    override suspend fun remoteKeysRepoId(repoId: Int): RemoteKeys? {
        println("remoteKeysRepoId")
        return keys.first { it?.repoId == repoId }
    }

    override suspend fun clearRemoteKeys() {
        keys.clear()
    }


}