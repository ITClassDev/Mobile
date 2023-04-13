package ru.slavapmk.shtp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private HashMap<String, ImageButton> panelButtons = new HashMap<>();
    private HashMap<String, Fragment> fragmentClassesList = new HashMap<>();
    private HashMap<String, Integer> fragmentLayoutsList = new HashMap<>();
    private Fragment currentFragment = null;

    public static FragmentManager fmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fmanager = getSupportFragmentManager();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout bottomPanel = findViewById(R.id.panel);
        for (int i = 0; i < bottomPanel.getChildCount(); i++) {
            ImageButton button = (ImageButton) bottomPanel.getChildAt(i);
            panelButtons.put(getResources().getResourceEntryName(button.getId()), button);
        }
        bottomPanel.getChildAt(0).setSelected(true);
        fragmentClassesList.put("profile", ProfileFragment.newInstance("", ""));
        fragmentClassesList.put("achievements", AchievementsFragment.newInstance("", ""));
        fragmentClassesList.put("tasks", TasksFragment.newInstance("", ""));
        fragmentClassesList.put("events", EventsFragment.newInstance("", ""));
        fragmentClassesList.put("notifications", NotificationsFragment.newInstance("", ""));
        fragmentLayoutsList.put("profile", R.layout.fragment_profile);
        fragmentLayoutsList.put("achievements", R.layout.fragment_achievements);
        fragmentLayoutsList.put("tasks", R.layout.fragment_tasks);
        fragmentLayoutsList.put("events", R.layout.fragment_events);
        fragmentLayoutsList.put("notifications", R.layout.fragment_notifications);
        openFragment("profile");
    }

    public void onPanelClick(View view) {
        ImageButton button = (ImageButton) view;
        for (ImageButton imageButton : panelButtons.values()) {
            imageButton.setSelected(false);
        }
        button.setSelected(true);
        String resourceName = getResourceEntryName(view);
        String fragmentName = resourceName.replaceFirst("open_", "").replace("_button", "");
        openFragment(fragmentName);
    }

    private void openFragment(String fragmentName) {
        Fragment fragment = fragmentClassesList.get(fragmentName);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true);
        if (currentFragment != null) {
            fragmentTransaction.remove(currentFragment);
        }
        if (fragment != null) {
            fragmentTransaction
                    .add(R.id.fragmentContainer, fragment)
                    .addToBackStack("backstack")
                    .commit();
        }
        currentFragment = fragment;
    }

    private String getResourceEntryName(View view) {
        return getResources().getResourceEntryName(view.getId());
    }
}
