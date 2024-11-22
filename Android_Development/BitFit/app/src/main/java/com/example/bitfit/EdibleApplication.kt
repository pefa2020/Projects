package com.example.bitfit

import android.app.Application

class EdibleApplication : Application() {
    val db by lazy { AppDatabase.getInstance(this) }
}