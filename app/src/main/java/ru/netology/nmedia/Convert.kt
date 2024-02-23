package ru.netology.nmedia

import kotlin.math.floor

fun convertToString(i: Int):String {
    return when (i) {
        in 0..999 -> i.toString()
        in 1000..9999 -> (floor(i / 100.0) / 10.0).toString() + "K"
        in 10000..99999 -> (i/1000).toString() + "К"
        else -> (floor(i / 100000.0) / 10.0).toString() + "М"
    }
}