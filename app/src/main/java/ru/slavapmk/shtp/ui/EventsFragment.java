package ru.slavapmk.shtp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import ru.slavapmk.shtp.databinding.FragmentEventsBinding;

public class EventsFragment extends Fragment {
    private FragmentEventsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEventsBinding.inflate(inflater);
        return binding.getRoot();
    }

    public static EventsFragment newInstance() {
        return new EventsFragment();
    }
}
