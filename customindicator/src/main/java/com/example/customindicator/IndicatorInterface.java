package com.example.customindicator;

import android.support.v4.view.ViewPager;

/**
 * Created by Admin on 8/4/2017.
 */

public interface IndicatorInterface {
    void setViewPager(ViewPager viewPager) throws PagesLessException;

    void setAnimateDuration(long duration);

    void setRadiusSelected(int radius);

    void setRadiusUnSelected(int radius);

    void setDistanceDot(int distance);
}
