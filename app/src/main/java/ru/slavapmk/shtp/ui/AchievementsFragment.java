package ru.slavapmk.shtp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import ru.slavapmk.shtp.databinding.FragmentAchievementsBinding;

public class AchievementsFragment extends Fragment {

    private FragmentAchievementsBinding inflate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflate = FragmentAchievementsBinding.inflate(inflater);
        return inflate.getRoot();
    }

    public static AchievementsFragment newInstance() {
        return new AchievementsFragment();
    }
}
