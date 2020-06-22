package com.example.foodlist.data.repository

import com.example.foodlist.util.State
import kotlinx.coroutines.flow.*
import retrofit2.Response

abstract class NetworkBoundRepository<RESULT, REQUEST> {

    fun asFlow() = flow<State<RESULT>> {

        emit(State.loading())

        try {
            var temp = State.success(fetchFromLocal().first())
            emit(temp)

            val apiResponse = fetchFromRemote()

            val remotePosts = apiResponse.body()

            if (apiResponse.isSuccessful && remotePosts != null) {
                saveRemoteData(remotePosts)
            } else {
                emit(State.error(apiResponse.message()))
            }
        } catch (e: Exception) {
            emit(State.error("Network error! Can't get latest posts."))
            e.printStackTrace()
        }

        emitAll(fetchFromLocal().map {
            State.success<RESULT>(it)
        })
    }

    protected abstract suspend fun saveRemoteData(response: REQUEST)

    protected abstract fun fetchFromLocal(): Flow<RESULT>

    protected abstract suspend fun fetchFromRemote(): Response<REQUEST>
}