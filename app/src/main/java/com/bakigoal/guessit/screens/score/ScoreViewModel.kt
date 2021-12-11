package com.bakigoal.guessit.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: Int) : ViewModel() {

    val score: LiveData<Int>
        get() = _score

    private val _score = MutableLiveData(finalScore)

    init {
        Log.i("ScoreViewModel", "ScoreViewModel is created, Score = $finalScore")
    }
}