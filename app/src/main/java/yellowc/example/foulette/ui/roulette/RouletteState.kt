package yellowc.example.foulette.ui.roulette

sealed class RouletteState {

    object closed : RouletteState()

    object playing : RouletteState()

    object finish : RouletteState()
}

enum class MODE {
    CLOSE,PLAYING,FINISH
}