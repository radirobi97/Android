package com.example.moovieapp.data.repositories

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import com.example.moovieapp.utils.State
import kotlinx.coroutines.flow.*
import retrofit2.Response

abstract class NetworkBoundRepository<DB, REMOTE> {

    @MainThread
    abstract fun fetchFromLocal(): Flow<DB>

    @MainThread
    abstract suspend fun fetchFromRemote(): Response<REMOTE>

    @WorkerThread
    abstract fun saveRemoteData(data: REMOTE)

    @MainThread
    abstract fun shouldFetchFromRemote(data: DB): Boolean

    fun asFlow() = flow<State<DB>> {

        emit(State.loading())

        val localData = fetchFromLocal().first()

        if(shouldFetchFromRemote(localData)) {
            try {
                val apiResponse = fetchFromRemote()
                val remoteMovies = apiResponse.body()

                if(apiResponse.isSuccessful && remoteMovies != null) {
                    saveRemoteData(remoteMovies)
                    emitLocalDbData()
                } else {
                    emit(State.error(apiResponse.message()))
                }

            } catch(e: Exception) {
                Log.d("DEUG_ADAT", e.message)
                emit(State.error("Network error!"))
                e.printStackTrace()
            }

        } else {
            emitLocalDbData()
        }
    }

    private suspend fun FlowCollector<State<DB>>.emitLocalDbData() {
        emitAll(fetchFromLocal().map { dbData ->
            State.success(dbData)
        })
    }
}