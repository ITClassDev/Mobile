package ru.slavapmk.shtp.ui;

import static ru.slavapmk.shtp.Values.ENDPOINT_URL;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.material.chip.Chip;

import java.util.Locale;

import ru.slavapmk.shtp.R;
import ru.slavapmk.shtp.Values;
import ru.slavapmk.shtp.databinding.FragmentProfileBinding;
import ru.slavapmk.shtp.io.dto.user.LeaderBoardItem;

public class ProfileFragment extends Fragment {
    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentProfileBinding binding = FragmentProfileBinding.inflate(inflater);

        binding.buttonOpenSettings.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_profile_to_settings));
        binding.profileUserName.setText(getResources().getString(R.string.user_name, Values.user.getFirstName(), Values.user.getLastName()));

        if (Values.user.getGithub() == null || Values.user.getGithub().equals(""))
            binding.userPersonalGithub.setVisibility(View.GONE);
        if (Values.user.getTelegram() == null || Values.user.getTelegram().equals(""))
            binding.userPersonalTelegram.setVisibility(View.GONE);
        if (Values.user.getStepik() == null || Values.user.getStepik().equals(""))
            binding.userPersonalStepik.setVisibility(View.GONE);
        if (Values.user.getKaggle() == null || Values.user.getKaggle().equals(""))
            binding.userPersonalKaggle.setVisibility(View.GONE);
        if (Values.user.getWebsite() == null || Values.user.getWebsite().equals(""))
            binding.userPersonalWebsite.setVisibility(View.GONE);

        binding.userPersonalGithub.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/" + Values.user.getGithub()))));
        binding.userPersonalTelegram.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/" + Values.user.getTelegram()))));
        binding.userPersonalStepik.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://stepik.org/users/" + Values.user.getStepik()))));
        binding.userPersonalKaggle.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.kaggle.com/" + Values.user.getKaggle()))));
        binding.userPersonalWebsite.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(!Values.user.getWebsite().startsWith("http://") && !Values.user.getWebsite().startsWith("https://") ? "http://" + Values.user.getWebsite() : Values.user.getWebsite()))));

        boolean socialBlock = false;
        for (int i = 0; i < binding.userSocialContainer.getChildCount(); i++)
            if (binding.userSocialContainer.getChildAt(i).getVisibility() == View.VISIBLE)
                socialBlock = true;
        if (socialBlock)
            binding.userSocialContainer.setVisibility(View.VISIBLE);
        else
            binding.userSocialContainer.setVisibility(View.GONE);

        RequestManager with = Glide.with(this);
        String avatarWebPath = ENDPOINT_URL + "storage/avatars/" + Values.user.getAvatarPath();
        if (Values.user.getAvatarPath().endsWith(".gif"))
            with.asGif().circleCrop().load(avatarWebPath).into(binding.avatar);
        else
            with.asBitmap().circleCrop().load(avatarWebPath).into(binding.avatar);

        if (Values.user.getAboutText() == null || "".equals(Values.user.getAboutText()))
            binding.aboutText.setVisibility(View.GONE);
        else binding.aboutText.setText(Values.user.getAboutText());

        if (Values.user.getTechStack() == null || Values.user.getTechStack().equals(""))
            binding.techStack.setVisibility(View.GONE);
        else
            for (String s : Values.user.getTechStack().split(",")) {
                Chip chip = new Chip(getContext());
                chip.setText(s);
                binding.techStack.addView(chip);
            }

        binding.accountStatus.setText(getResources().getString(
                switch (Values.user.getRole()) {
                    case "student" -> R.string.user_role_student;
                    case "teacher" -> R.string.user_role_teacher;
                    case "admin" -> R.string.user_role_administrator;
                    default ->
                            throw new IllegalStateException("Unexpected value: " + Values.user.getRole());
                }
        ));

        binding.buttonLogout.setOnClickListener(view -> {
            FragmentManager manager = getChildFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            new Dialog(
                    getResources().getString(R.string.dialog_title_quit),
                    getResources().getString(R.string.dialog_description_quit),
                    getResources().getString(R.string.dialog_button_quit),
                    view1 -> {
                        requireActivity().getSharedPreferences(Values.APP_ID, 0).edit().remove(Values.AUTH_KEY_ID).apply();
                        Intent intent = new Intent(requireContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
            ).show(transaction, "quit_dialog");
        });


        //Statistic
        binding.userStatisticScore.setText(String.format(Locale.getDefault(), "%d", Values.user.getRating()));
        for (int i = 0; i < Values.leaderboard.size(); i++) {
            LeaderBoardItem leaderBoardItem = Values.leaderboard.get(i);
            if (leaderBoardItem.getUuid() == Values.user.getUuid()) {
                binding.userStatisticPosition.setText(String.format(Locale.getDefault(), "%d", i + 1));
                break;
            }
        }

        binding.avatar.setOnClickListener(view -> {
            Intent intent = new Intent(requireActivity(), FullScreenImageActivity.class);
            intent.putExtra("uri", avatarWebPath);
            requireActivity().startActivity(intent);
        });

        return binding.getRoot();
    }
}
