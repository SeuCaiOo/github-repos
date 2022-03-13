package br.com.seucaio.githubreposkotlin.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "repo_owner")
@Serializable
data class Owner(
    @PrimaryKey val id: Int? = null,
    @ColumnInfo(name = "login") val login: String? = null,
    @ColumnInfo(name = "avatar_url")
    @SerialName("avatar_url") val avatarUrl: String? = null,
)