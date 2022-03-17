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
class RepoDaoTest  {

    private lateinit var dao: RepoDao
    private lateinit var database: RepoDatabase

    @Before
    fun createDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RepoDatabase::class.java
        ).build()
        dao = database.reposDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun repos() = runBlocking {
        // Given
        val list = listOf(RepoStub.Model.repo)
        dao.insertAll(list)

        // When
        val result = dao.repos().getData()

        // Then
        assertTrue(list.isNotEmpty())
        assertEquals(list, result)
    }

    @Test
    fun testPagingDb() = runBlocking {
        // Given
        val list = listOf(RepoStub.Model.repo)

        // When
        dao.insertAll(list)
        // Then
        val tData = PagingSourceUtils(list)
        assertEquals(list, tData.getData())
    }
}