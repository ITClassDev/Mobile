package ru.slavapmk.shtp.ui;

import static ru.slavapmk.shtp.ui.MainActivity.fmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import ru.slavapmk.shtp.R;

public class ProfileFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_profile, container, false);
        inflate.findViewById(R.id.imageButton11).setOnClickListener(view ->
                fmanager
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .remove(ProfileFragment.this)
                        .add(R.id.fragmentContainer, SettingsFragment.newInstance())
                        .addToBackStack("backstack")
                        .commit()
        );
        return inflate;
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }
}
