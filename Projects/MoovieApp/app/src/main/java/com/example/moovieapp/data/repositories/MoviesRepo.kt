package com.example.moovieapp.data.repositories

import android.content.SharedPreferences
import com.example.moovieapp.data.local.daos.MoviesDao
import com.example.moovieapp.data.remote.ApiInterface
import com.example.moovieapp.data.remote.Movie
import com.example.moovieapp.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MoviesRepo @Inject constructor(
    private val sharedPref: SharedPreferences,
    private val apiInterface: ApiInterface,
    private val moviesDao: MoviesDao
) {

    companion object {
        private val MOVIE_EXPIRY_IN_MILLIS = TimeUnit.HOURS.toMillis(1L)
        private const val KEY_LAST_SYNCED = "last_synced"
    }

    fun getTop250Movies(): Flow<State<List<Movie>>> {
        return object : NetworkBoundRepository<List<Movie>, List<Movie>>() {
            override fun fetchFromLocal(): Flow<List<Movie>> = moviesDao.getAllMovies()

            override suspend fun fetchFromRemote(): Response<List<Movie>> = apiInterface.getTop250Movies()

            override fun saveRemoteData(data: List<Movie>) {
                moviesDao.deleteAll()
                moviesDao.addAll(data)
                val editor = sharedPref.edit()
                editor.putLong(KEY_LAST_SYNCED, System.currentTimeMillis())
                editor.apply()
            }

            override fun shouldFetchFromRemote(data: List<Movie>): Boolean {
                val lastSynced = sharedPref.getLong(KEY_LAST_SYNCED, -1)
                return lastSynced == -1L || data.isNullOrEmpty() || isExpired(lastSynced)
            }

        }.asFlow().flowOn(Dispatchers.IO)
    }

    private fun isExpired(lastSynced: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        return (currentTime - lastSynced) >= MOVIE_EXPIRY_IN_MILLIS
    }
}