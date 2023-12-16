package com.example.affirmagic.repository

import com.example.affirmagic.data.AffirmationsModel
import com.example.affirmagic.db.dao.AffirmationsDao
import com.example.affirmagic.db.entity.AffirmationsEntity
import com.example.affirmagic.remote.RemoteDataSource
import com.example.affirmagic.utils.performGetOperation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AffirmationsRepository @Inject constructor(
    private val affirmationsDao: AffirmationsDao,
    private val remoteDataSource: RemoteDataSource
){

    fun getAffirmations(date: String) = performGetOperation(
        databaseQuery = { affirmationsDao.getAffirmations(date) },
        networkCall = { remoteDataSource.getAffirmations(date) },
        saveCallResult = { insertAffirmations(date,it) }
    )

    private suspend fun insertAffirmations(date: String, list : List<AffirmationsModel>){
        list.forEach {
            affirmationsDao.insertAffirmations(AffirmationsEntity(it.text, it.author, it.uniqueId, it.type,
                it.dzType, it.language, it.bgImageUrl,
                it.theme, it.themeTitle, it.articleUrl, it.dzImageUrl,
                it.primaryCTAText, it.sharePrefix, date))
        }

    }

}