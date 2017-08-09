package com.example.admin.customview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Admin on 8/5/2017.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                return new OneFragment();

            case 1:
                return new TwoFragment();
            case 2:
                return new ThirdFragment();

        }
        return new OneFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
