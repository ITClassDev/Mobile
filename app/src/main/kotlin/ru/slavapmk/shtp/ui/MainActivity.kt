package ru.slavapmk.shtp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.slavapmk.shtp.R
import ru.slavapmk.shtp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var fmanager: FragmentManager
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fmanager = supportFragmentManager
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        findViewById<BottomNavigationView>(R.id.bottom_panel).setupWithNavController(
            (supportFragmentManager.findFragmentById(
                R.id.fragmentContainer
            ) as NavHostFragment).navController
        )
    }

}