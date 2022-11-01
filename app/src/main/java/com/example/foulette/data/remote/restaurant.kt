package com.example.foulette.data.remote

data class restaurant(
    val html_attributions: List<Any>,
    val results: List<Result>,
    val status: String
)