package br.com.seucaio.githubreposkotlin.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repos")
data class Repo(
    @PrimaryKey val id: Int? = null,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "full_name") val fullName: String? = null,
    @ColumnInfo(name = "description") val description: String? = null,
    @ColumnInfo(name = "language") val language: String? = null,
    @ColumnInfo(name = "forks")val forksCount: Int? = null,
    @ColumnInfo(name = "stars") val stargazersCount: Int? = null,
    @ColumnInfo(name = "owner") val owner: Owner? = null
)