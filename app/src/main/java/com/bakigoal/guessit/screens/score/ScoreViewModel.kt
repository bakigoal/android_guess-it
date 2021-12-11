package com.bakigoal.guessit.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: Int) : ViewModel() {

    val score: LiveData<Int>
        get() = _score
    val playAgainEvent: LiveData<Boolean>
        get() = _playAgainEvent

    private val _score = MutableLiveData(finalScore)
    private val _playAgainEvent = MutableLiveData(false)

    init {
        Log.i("ScoreViewModel", "ScoreViewModel is created, Score = $finalScore")
    }

    fun playAgain() {
        _playAgainEvent.value = true
    }

    fun onPlayAgainComplete() {
        _playAgainEvent.value = false
    }
}