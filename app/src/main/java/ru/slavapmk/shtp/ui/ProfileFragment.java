package ru.slavapmk.shtp.ui;

import static android.content.Context.MODE_PRIVATE;
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
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.material.chip.Chip;

import java.util.Locale;

import ru.slavapmk.shtp.FullScreenImageActivity;
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

        if (Values.user.getUserGithub() == null || Values.user.getUserGithub().equals(""))
            binding.userPersonalGithub.setVisibility(View.GONE);
        if (Values.user.getUserTelegram() == null || Values.user.getUserTelegram().equals(""))
            binding.userPersonalTelegram.setVisibility(View.GONE);
        if (Values.user.getUserStepik() == null || Values.user.getUserStepik().equals(""))
            binding.userPersonalStepik.setVisibility(View.GONE);
        if (Values.user.getUserKaggle() == null || Values.user.getUserKaggle().equals(""))
            binding.userPersonalKaggle.setVisibility(View.GONE);
        if (Values.user.getUserWebsite() == null || Values.user.getUserWebsite().equals(""))
            binding.userPersonalWebsite.setVisibility(View.GONE);

        binding.userPersonalGithub.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/" + Values.user.getUserGithub()))));
        binding.userPersonalTelegram.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/" + Values.user.getUserTelegram()))));
        binding.userPersonalStepik.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://stepik.org/users/" + Values.user.getUserStepik()))));
        binding.userPersonalKaggle.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.kaggle.com/" + Values.user.getUserKaggle()))));
        binding.userPersonalWebsite.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(!Values.user.getUserWebsite().startsWith("http://") && !Values.user.getUserWebsite().startsWith("https://") ? "http://" + Values.user.getUserWebsite() : Values.user.getUserWebsite()))));

        RequestManager with = Glide.with(this);
        String avatarWebPath = ENDPOINT_URL + "storage/avatars/" + Values.user.getUserAvatarPath() + "?nocache=" + Values.INSTANCE.getLastAvatarUpdate();
        boolean avatarIsGif = Values.user.getUserAvatarPath().endsWith(".gif");
        if (avatarIsGif)
            with.asGif().circleCrop().load(avatarWebPath).into(binding.avatar);
        else
            with.asBitmap().circleCrop().load(avatarWebPath).into(binding.avatar);

        if ("".equals(Values.user.getUserAboutText())) binding.aboutText.setVisibility(View.GONE);
        else binding.aboutText.setText(Values.user.getUserAboutText());

        if (Values.user.getTechStack() == null || Values.user.getTechStack().equals(""))
            binding.techStack.setVisibility(View.GONE);
        else
            for (String s : Values.user.getTechStack().split(",")) {
                Chip chip = new Chip(getContext());
                chip.setText(s);
                binding.techStack.addView(chip);
            }

        binding.buttonLogout.setOnClickListener(view -> {
            requireActivity().getSharedPreferences(Values.APP_ID, MODE_PRIVATE).edit().remove(Values.AUTH_KEY_ID).apply();
            Intent myIntent = new Intent(requireContext(), LoginActivity.class);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(myIntent);
        });


        //Statistic
        binding.userStatisticScore.setText(String.format(Locale.getDefault(), "%d", Values.user.getRating()));
        for (int i = 0; i < Values.leaderboard.size(); i++) {
            LeaderBoardItem leaderBoardItem = Values.leaderboard.get(i);
            if (leaderBoardItem.getId() == Values.user.getId()) {
                binding.userStatisticPosition.setText(String.format(Locale.getDefault(), "%d", i + 1));
                break;
            }
        }

        binding.avatar.setOnClickListener(view -> {
            Intent intent = new Intent(requireActivity(), FullScreenImageActivity.class);
            intent.putExtra("uri", avatarWebPath);
            intent.putExtra("as_gif", avatarIsGif);
            requireActivity().startActivity(intent);
        });

        return binding.getRoot();
    }
}
