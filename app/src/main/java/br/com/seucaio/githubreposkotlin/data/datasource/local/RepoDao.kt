package br.com.seucaio.githubreposkotlin.data.datasource.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.seucaio.githubreposkotlin.domain.entity.Repo

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<Repo>)

    @Query("SELECT * FROM repos ORDER BY stars DESC")
    fun repos(): PagingSource<Int, Repo>

    @Query("DELETE FROM repos")
    suspend fun clearRepos()
}