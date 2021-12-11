package com.bakigoal.guessit.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    companion object {
        private const val ONE_SECOND = 1000L
        private const val COUNTDOWN_TIME = 90000L
    }

    val gameFinished: LiveData<Boolean>
        get() = _gameFinished
    val word: LiveData<String>
        get() = _word
    val score: LiveData<Int>
        get() = _score
    val time: LiveData<String>
        get() = _time

    private var _gameFinished = MutableLiveData(false)
    private var _word = MutableLiveData("")
    private var _score = MutableLiveData(0)
    private var _time = MutableLiveData(formatTime(COUNTDOWN_TIME))

    private val timer: CountDownTimer
    private lateinit var wordList: MutableList<String>

    init {
        resetList()
        nextWord()
        timer = MyCountDownTimer().start()
        Log.i("GameViewModel", "GameViewModel created")
    }


    inner class MyCountDownTimer : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
        override fun onTick(millisUntilFinished: Long) {
            _time.value = formatTime(millisUntilFinished)
        }

        override fun onFinish() {
            _gameFinished.value = true
        }
    }

    fun formatTime(millis: Long): String = DateUtils.formatElapsedTime(millis / ONE_SECOND)

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
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
        if (wordList.isEmpty()) {
            resetList()
        }
        _word.value = wordList.removeAt(0)
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

    fun onGameFinishComplete() {
        _gameFinished.value = false
    }
}