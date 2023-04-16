package ru.slavapmk.shtp.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.slavapmk.shtp.R
import ru.slavapmk.shtp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val panelButtons = HashMap<String, ImageButton>()
    private val fragmentClassesList = HashMap<String, Fragment>()
    private val fragmentLayoutsList = HashMap<String, Int>()
    private var currentFragment: Fragment? = null

    companion object {
        lateinit var fmanager: FragmentManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fmanager = supportFragmentManager
        setContentView(R.layout.activity_main)
        val bottomPanel = findViewById<LinearLayout>(R.id.panel)
        for (i in 0 until bottomPanel.childCount) {
            val button = bottomPanel.getChildAt(i) as ImageButton
            panelButtons[resources.getResourceEntryName(button.id)] = button
        }
        bottomPanel.getChildAt(0).isSelected = true
        fragmentClassesList["profile"] = ProfileFragment.newInstance()
        fragmentClassesList["achievements"] = AchievementsFragment.newInstance("", "")
        fragmentClassesList["tasks"] = TasksFragment.newInstance("", "")
        fragmentClassesList["events"] = EventsFragment.newInstance("", "")
        fragmentClassesList["notifications"] = NotificationsFragment.newInstance("", "")
        fragmentLayoutsList["profile"] = R.layout.fragment_profile
        fragmentLayoutsList["achievements"] = R.layout.fragment_achievements
        fragmentLayoutsList["tasks"] = R.layout.fragment_tasks
        fragmentLayoutsList["events"] = R.layout.fragment_events
        fragmentLayoutsList["notifications"] = R.layout.fragment_notifications
        openFragment("profile")
    }

    fun onPanelClick(view: View) {
        val button = view as ImageButton
        for (imageButton in panelButtons.values) {
            imageButton.isSelected = false
        }
        button.isSelected = true
        val resourceName = getResourceEntryName(view)
        val fragmentName = resourceName.replaceFirst("open_".toRegex(), "").replace("_button", "")
        openFragment(fragmentName)
    }

    private fun openFragment(fragmentName: String) {
        val fragment = fragmentClassesList[fragmentName]
        val fragmentTransaction =
            supportFragmentManager.beginTransaction().setReorderingAllowed(true)
        if (currentFragment != null) {
            fragmentTransaction.remove(currentFragment!!)
        }
        if (fragment != null) {
            fragmentTransaction.add(R.id.fragmentContainer, fragment).addToBackStack("backstack")
                .commit()
        }
        currentFragment = fragment
    }

    private fun getResourceEntryName(view: View): String {
        return resources.getResourceEntryName(view.id)
    }
}