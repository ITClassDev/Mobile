package ru.slavapmk.shtp

import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {
    private val panelButtons: java.util.HashMap<String, ImageButton> = java.util.HashMap<String, ImageButton>()
    private val fragmentClassesList: java.util.HashMap<String, Fragment> = java.util.HashMap<String, Fragment>()
    private val fragmentLayoutsList: java.util.HashMap<String, Int> = java.util.HashMap<String, Int>()
    private var currentFragment: Fragment? = null

    companion object {
        lateinit var fmanager: FragmentManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        fmanager = supportFragmentManager
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomPanel: LinearLayout = findViewById(R.id.panel)
        for (i in 0 until bottomPanel.childCount) {
            val button: ImageButton = bottomPanel.getChildAt(i) as ImageButton
            panelButtons[getResourceEntryName(button)] = button
        }
        bottomPanel.getChildAt(0).isSelected = true
        fragmentClassesList["profile"] = ProfileFragment.newInstance("", "")
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

    fun onPanelClick(view: android.view.View) {
        val button: ImageButton = view as ImageButton
        panelButtons.forEach { (_: String?, imageButton: ImageButton) -> imageButton.isSelected = false }
        button.isSelected = true
        val resourceName = getResourceEntryName(view)
        val fragmentName: String = resourceName.replace("open_".toRegex(), "").replace("_button".toRegex(), "")
        openFragment(fragmentName)
    }

    private fun openFragment(fragmentName: String) {
        val fragment: Fragment? = fragmentClassesList[fragmentName]
        val fragmentTransaction: androidx.fragment.app.FragmentTransaction = supportFragmentManager
                .beginTransaction()
                .setReorderingAllowed(true)
        if (currentFragment != null) fragmentTransaction.remove(currentFragment!!)
        if (fragment != null) {
            fragmentTransaction.add(R.id.fragmentContainer, fragment)
                    .addToBackStack("backstack")
                    .commit()
        }
        currentFragment = fragment
    }

    private fun getResourceEntryName(view: android.view.View): String {
        return view.resources.getResourceEntryName(view.id)
    }
}