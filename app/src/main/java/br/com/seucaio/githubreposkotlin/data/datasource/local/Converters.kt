package br.com.seucaio.githubreposkotlin.data.datasource.local

import androidx.room.TypeConverter
import br.com.seucaio.githubreposkotlin.domain.entity.Owner
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {

    @TypeConverter
    fun ownerToString(owner: Owner): String {
        return Json.encodeToString(owner)
    }

    @TypeConverter
    fun stringToOwner(value: String): Owner {
        return Json.decodeFromString(value)
    }
}