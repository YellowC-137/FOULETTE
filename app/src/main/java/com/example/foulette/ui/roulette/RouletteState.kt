package com.example.foulette.ui.roulette

sealed class RouletteState {

    data class Success(
        val mode: MODE
    ) : RouletteState()

    data class Failure(
        val mode: MODE
    ) : RouletteState()

    object Modify : RouletteState()

    object Reading : RouletteState()

    object Review : RouletteState()
}

enum class MODE {
    CLOSE,PLAYING,
}