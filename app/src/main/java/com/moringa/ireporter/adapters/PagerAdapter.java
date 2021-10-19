package com.moringa.ireporter.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.moringa.ireporter.ui.HomeFragment;
import com.moringa.ireporter.ui.InterventionFragment;
import com.moringa.ireporter.ui.RedFlagFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    int tabcount;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();

            case 1:
                return new RedFlagFragment();

            case 2:
                return new InterventionFragment();


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
