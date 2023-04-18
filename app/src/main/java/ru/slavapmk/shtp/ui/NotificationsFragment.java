package ru.slavapmk.shtp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import ru.slavapmk.shtp.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {
    private FragmentNotificationsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater);
        return binding.getRoot();
    }

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }
}
