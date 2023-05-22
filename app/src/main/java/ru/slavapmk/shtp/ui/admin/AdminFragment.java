package ru.slavapmk.shtp.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import ru.slavapmk.shtp.R;
import ru.slavapmk.shtp.databinding.FragmentAdminBinding;

public class AdminFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentAdminBinding binding = FragmentAdminBinding.inflate(inflater);

        binding.adminPanelButtonGroups.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_admin_to_admin_group));
        binding.adminPanelButtonUsers.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_admin_to_admin_users));
//        binding.adminPanelButtonAchievements.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_admin_to_admin_achievements));
//        binding.adminPanelButtonTasks.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_admin_to_admin_tasks));
        binding.adminPanelButtonNotifications.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_admin_to_admin_notifications));
//        binding.adminPanelButtonSystem.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_admin_to_admin_system));

        return binding.getRoot();
    }
}