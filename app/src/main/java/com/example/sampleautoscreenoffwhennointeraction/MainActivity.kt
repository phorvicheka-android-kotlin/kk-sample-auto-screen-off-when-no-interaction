package com.example.sampleautoscreenoffwhennointeraction

import android.os.Bundle

class MainActivity : MyBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}