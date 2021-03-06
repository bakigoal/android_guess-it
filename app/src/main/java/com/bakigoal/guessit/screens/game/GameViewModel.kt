package com.bakigoal.guessit.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bakigoal.guessit.util.BuzzType

class GameViewModel : ViewModel() {

    companion object {
        private const val DONE = 0L
        private const val ONE_SECOND = 1000L
        private const val COUNTDOWN_TIME = 30000L
        private const val COUNTDOWN_PANIC_SECONDS = 3
    }

    val gameFinishedEvent: LiveData<Boolean>
        get() = _gameFinishedEvent
    val word: LiveData<String>
        get() = _word
    val score: LiveData<Int>
        get() = _score
    val currentTimeString: LiveData<String>
        get() = Transformations.map(_currentTime) { time -> DateUtils.formatElapsedTime(time / ONE_SECOND) }
    val buzzTypeEvent: LiveData<BuzzType>
        get() = _buzzTypeEvent

    private var _gameFinishedEvent = MutableLiveData(false)
    private var _word = MutableLiveData("")
    private var _score = MutableLiveData(0)
    private var _currentTime = MutableLiveData(COUNTDOWN_TIME)
    private var _buzzTypeEvent = MutableLiveData(BuzzType.NO_BUZZ)

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
            _currentTime.value = millisUntilFinished
            if (millisUntilFinished / ONE_SECOND <= COUNTDOWN_PANIC_SECONDS) {
                _buzzTypeEvent.value = BuzzType.COUNTDOWN_PANIC
            }
        }

        override fun onFinish() {
            _currentTime.value = DONE
            _buzzTypeEvent.value = BuzzType.GAME_OVER
            _gameFinishedEvent.value = true
        }
    }

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
        _buzzTypeEvent.value = BuzzType.CORRECT
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
        _gameFinishedEvent.value = false
    }

    fun onBuzzComplete() {
        _buzzTypeEvent.value = BuzzType.NO_BUZZ
    }
}