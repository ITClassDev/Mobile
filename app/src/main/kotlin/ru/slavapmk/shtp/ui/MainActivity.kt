package ru.slavapmk.shtp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        fmanager = supportFragmentManager
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        findViewById<BottomNavigationView>(R.id.bottom_panel).setupWithNavController(
            (supportFragmentManager.findFragmentById(
                R.id.fragmentContainer
            ) as NavHostFragment).navController
        )
        window.navigationBarColor = ContextCompat.getColor(this, R.color.panel)

        Values.versionManager.lastVersion
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (BuildConfig.VERSION_CODE < it.code) {
                    val dialog = NewVersionDialog()
                    val manager: FragmentManager = supportFragmentManager
                    val transaction = manager.beginTransaction()
                    val args = Bundle()
                    args.putString("version_name", it.name)
                    args.putInt("version_code", it.code)
                    args.putString("version_url", it.downloadUrl)
                    dialog.arguments = args
                    dialog.show(transaction, "new_version_dialog")
                }
            }, {})
    }

}