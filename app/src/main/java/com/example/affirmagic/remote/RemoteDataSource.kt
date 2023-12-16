package com.example.affirmagic.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
): BaseDataSource() {

        suspend fun getAffirmations(date: String) = getResult { apiService.getAffirmations(date,2) }

}