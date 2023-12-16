package com.example.affirmagic.viewmodel

import androidx.lifecycle.ViewModel
import com.example.affirmagic.repository.AffirmationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AffirmationsViewModel @Inject constructor(private val affirmationsRepository: AffirmationsRepository): ViewModel() {

    fun getAffirmations(date: String) = affirmationsRepository.getAffirmations(date)
}