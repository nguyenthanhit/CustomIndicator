package com.example.admin.customview;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.customindicator.IndicatorView;
import com.example.customindicator.PagesLessException;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    IndicatorView indicatorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        indicatorView = (IndicatorView) findViewById(R.id.indicator);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);

        try {
            indicatorView.setViewPager(viewPager);
        } catch (PagesLessException e) {
            e.printStackTrace();
        }
    }
}
