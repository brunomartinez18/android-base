package com.example.marsheroly.presentation

import android.app.Application


class MarsHerolyApplication : Application() {

    companion object {

        fun get(): Application = instance

        private lateinit var instance: Application

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}