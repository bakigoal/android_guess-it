package com.bakigoal.guessit.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    val gameFinished: LiveData<Boolean>
        get() = _gameFinished
    val word: LiveData<String>
        get() = _word
    val score: LiveData<Int>
        get() = _score

    private var _gameFinished = MutableLiveData(false)
    private var _word = MutableLiveData("")
    private var _score = MutableLiveData(0)
    private lateinit var wordList: MutableList<String>

    init {
        Log.i("GameViewModel", "GameViewModel created")
        resetList()
        nextWord()
    }

    override fun onCleared() {
        Log.i("GameViewModel", "GameViewModel destroyed")
    }

    fun onSkip() {
        _score.value = (_score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (_score.value)?.plus(1)
        nextWord()
    }

    private fun nextWord() {
        if (wordList.isNotEmpty()) {
            _word.value = wordList.removeAt(0)
        } else {
            _gameFinished.value = true
        }
    }

    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }
}