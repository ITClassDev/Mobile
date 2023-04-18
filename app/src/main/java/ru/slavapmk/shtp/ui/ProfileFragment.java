package ru.slavapmk.shtp.ui;

import static ru.slavapmk.shtp.Values.ENDPOINT_URL;
import static ru.slavapmk.shtp.ui.MainActivity.fmanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import ru.slavapmk.shtp.R;
import ru.slavapmk.shtp.Values;
import ru.slavapmk.shtp.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentProfileBinding binding = FragmentProfileBinding.inflate(inflater);

        binding.imageButton11.setOnClickListener(view -> fmanager.beginTransaction().setReorderingAllowed(true).remove(ProfileFragment.this).add(R.id.fragmentContainer, SettingsFragment.newInstance()).addToBackStack("backstack").commit());
        binding.userNameLastName.setText(getResources().getString(R.string.user_name, Values.user.getFirstName(), Values.user.getLastName()));
        binding.textView6.setText(String.format(Locale.getDefault(), "%d", Values.user.getRating()));

        if (Values.user.getUserGithub() == null) binding.userPersonalGithub.setVisibility(View.GONE);
        if (Values.user.getUserTelegram() == null) binding.userPersonalTelegram.setVisibility(View.GONE);
        if (Values.user.getUserStepik() == null) binding.userPersonalStepik.setVisibility(View.GONE);
        if (Values.user.getUserKaggle() == null) binding.userPersonalKaggle.setVisibility(View.GONE);
        if (Values.user.getUserWebsite() == null) binding.userPersonalWebsite.setVisibility(View.GONE);

        binding.userPersonalGithub.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/" + Values.user.getUserGithub()))));
        binding.userPersonalTelegram.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/" + Values.user.getUserTelegram()))));
        binding.userPersonalStepik.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://stepik.org/users/" + Values.user.getUserStepik()))));
        binding.userPersonalKaggle.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.kaggle.com/" + Values.user.getUserKaggle()))));
        binding.userPersonalWebsite.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                !Values.user.getUserWebsite().startsWith("http://") && !Values.user.getUserWebsite().startsWith("https://") ?
                        "http://" + Values.user.getUserWebsite() : Values.user.getUserWebsite()
        ))));

        Picasso.get().load(ENDPOINT_URL + "/storage/avatars/" + Values.user.getUserAvatarPath()).transform(new CropCircleTransformation()).into(binding.imageView2);

        binding.aboutText.setText(Values.user.getUserAboutText());

        return binding.getRoot();
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }
}
