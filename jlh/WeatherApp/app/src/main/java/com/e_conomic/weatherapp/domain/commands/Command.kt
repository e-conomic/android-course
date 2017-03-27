package com.e_conomic.weatherapp.domain.commands

interface Command<T> {
    fun execute() : T
}