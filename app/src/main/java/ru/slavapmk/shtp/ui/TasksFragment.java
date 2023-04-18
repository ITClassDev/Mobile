package ru.slavapmk.shtp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import ru.slavapmk.shtp.databinding.FragmentTasksBinding;

public class TasksFragment extends Fragment {
    private FragmentTasksBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTasksBinding.inflate(inflater);
        return binding.getRoot();
    }

    public static TasksFragment newInstance() {
        return new TasksFragment();
    }
}
