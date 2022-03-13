package br.com.seucaio.githubreposkotlin.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.seucaio.githubreposkotlin.domain.entity.Repo

@Database(
    entities = [Repo::class, RemoteKeys::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class RepoDatabase : RoomDatabase() {

    abstract fun reposDao(): RepoDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {

        @Volatile
        private var INSTANCE: RepoDatabase? = null

        fun getInstance(context: Context): RepoDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                RepoDatabase::class.java, "github.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}