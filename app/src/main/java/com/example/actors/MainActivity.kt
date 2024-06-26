package com.example.actors

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.actors.ui.main.actorDetails.ActorDetailsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ActorDetailsFragment.newInstance())
                .commitNow()
        }
    }
}