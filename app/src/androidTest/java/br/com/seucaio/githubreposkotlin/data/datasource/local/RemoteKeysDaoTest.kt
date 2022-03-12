package br.com.seucaio.githubreposkotlin.data.datasource.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.seucaio.githubreposkotlin.core.PagingSourceUtils
import br.com.seucaio.githubreposkotlin.core.ext.getData
import br.com.seucaio.githubreposkotlin.core.stub.RepoStub
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class RemoteKeysDaoTest  {

    private lateinit var dao: RemoteKeysDao
    private lateinit var database: RepoDatabase

    @Before
    fun createDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RepoDatabase::class.java
        ).build()
        dao = database.remoteKeysDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun remoteKeysRepoId() = runBlocking {
        // Given
        val remoteKeys = RepoStub.Model.remoteKeys
        dao.insertAll(listOf(remoteKeys))

        // When
        val result = dao.remoteKeysRepoId(repoId = remoteKeys.repoId)

        // Then
        assertTrue(result != null)
        assertEquals(remoteKeys, result)
    }
}