package com.bakigoal.guessit.util

enum class BuzzType(val pattern: LongArray) {
    CORRECT(BuzzPatterns.CORRECT_BUZZ_PATTERN),
    GAME_OVER(BuzzPatterns.GAME_OVER_BUZZ_PATTERN),
    COUNTDOWN_PANIC(BuzzPatterns.PANIC_BUZZ_PATTERN),
    NO_BUZZ(BuzzPatterns.NO_BUZZ_PATTERN)
}

private class BuzzPatterns {
    companion object{
        val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
        val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
        val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
        val NO_BUZZ_PATTERN = longArrayOf(0)
    }
}