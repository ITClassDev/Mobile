package ru.slavapmk.shtp.ui;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LongNavigationBar extends BottomNavigationView {

    public LongNavigationBar(@NonNull Context context) {
        super(context);
    }

    public LongNavigationBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LongNavigationBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getMaxItemCount() {
        return 10;
    }
}
