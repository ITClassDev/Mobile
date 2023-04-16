package ru.slavapmk.shtp.ui;

import static ru.slavapmk.shtp.ui.MainActivity.fmanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import ru.slavapmk.shtp.R;
import ru.slavapmk.shtp.Values;

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
        inflate.<TextView>findViewById(R.id.userNameLastName).setText(getResources().getString(
                R.string.user_name,
                Values.user.getFirstName(),
                Values.user.getLastName()
        ));
        inflate.<TextView>findViewById(R.id.textView6).setText(Integer.toString(Values.user.getRating()));
        inflate.<ImageButton>findViewById(R.id.gh).setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/" + Values.user.getUserGithub()))));
        inflate.<ImageButton>findViewById(R.id.gh2).setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/" + Values.user.getUserTelegram()))));
        inflate.<ImageButton>findViewById(R.id.gh3).setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://stepik.org/users/" + Values.user.getUserStepik()))));
        inflate.<ImageButton>findViewById(R.id.gh4).setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.kaggle.com/" + Values.user.getUserKaggle()))));
        inflate.<ImageButton>findViewById(R.id.gh5).setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Values.user.getUserWebsite()))));
        return inflate;
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

}
