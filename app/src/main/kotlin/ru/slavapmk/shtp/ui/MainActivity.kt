package ru.slavapmk.shtp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.slavapmk.shtp.BuildConfig
import ru.slavapmk.shtp.R
import ru.slavapmk.shtp.Values
import ru.slavapmk.shtp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var fmanager: FragmentManager
    }

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO Make light theme
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        fmanager = supportFragmentManager
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        findViewById<BottomNavigationView>(R.id.bottom_panel).setupWithNavController(
            (supportFragmentManager.findFragmentById(
                R.id.fragmentContainer
            ) as NavHostFragment).navController
        )
        window.navigationBarColor = ContextCompat.getColor(this, R.color.panel)

        when (Values.user.userRole) {
            0 -> intArrayOf(R.id.tasks, R.id.admin)

            1 -> intArrayOf(R.id.tasks, R.id.achievements)

            2 -> intArrayOf(R.id.tasks, R.id.achievements)

            else -> intArrayOf()
        }.forEach {
            binding.bottomPanel.menu.findItem(it).isVisible = false
        }

        Values.versionManager.lastVersion
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ version ->
                if (BuildConfig.VERSION_CODE < version.code) {
                    Dialog(
                        resources.getString(R.string.dialog_title_new_version),
                        resources.getString(R.string.dialog_description_new_version, version.name),
                        resources.getString(R.string.dialog_button_new_version)
                    ) {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(version.downloadUrl)))
                    }.show(supportFragmentManager.beginTransaction(), "new_version_dialog")
                }
            }, {})
    }

}