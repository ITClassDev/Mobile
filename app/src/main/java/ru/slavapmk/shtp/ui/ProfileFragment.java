package ru.slavapmk.shtp.ui;

import static ru.slavapmk.shtp.Values.ENDPOINT_URL;

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

import ru.slavapmk.shtp.R;
import ru.slavapmk.shtp.Values;
import ru.slavapmk.shtp.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentProfileBinding binding = FragmentProfileBinding.inflate(inflater);

        binding.imageButton11.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_profile_to_settings));
        binding.userNameLastName.setText(getResources().getString(R.string.user_name, Values.user.getFirstName(), Values.user.getLastName()));
        binding.textView6.setText(String.format(Locale.getDefault(), "%d", Values.user.getRating()));

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
        binding.userPersonalWebsite.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                !Values.user.getUserWebsite().startsWith("http://") && !Values.user.getUserWebsite().startsWith("https://") ?
                        "http://" + Values.user.getUserWebsite() : Values.user.getUserWebsite()
        ))));

        RequestManager with = Glide.with(this);
        if (Values.user.getUserAvatarPath().endsWith(".gif"))
            with
                    .asGif()
                    .circleCrop()
                    .load(ENDPOINT_URL + "/storage/avatars/" + Values.user.getUserAvatarPath())
                    .into(binding.imageView2);
        else
            with
                    .asBitmap()
                    .circleCrop()
                    .load(ENDPOINT_URL + "/storage/avatars/" + Values.user.getUserAvatarPath())
                    .into(binding.imageView2);

        binding.aboutText.setText(Values.user.getUserAboutText());

        if (Values.user.getTechStack() != null)
            for (String s : Values.user.getTechStack().split(",")) {
                Chip chip = new Chip(getContext());
                chip.setText(s);
                binding.techStackChips.addView(chip);
            }
        if (Values.user.getTechStack() == null || Values.user.getTechStack().split(",").length == 0)
            binding.techStack.setVisibility(View.GONE);

        return binding.getRoot();
    }
}
