package com.russelarms.offsetanimatorsample;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import com.russelarms.offsetanimator.ViewPagerAnimatorAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.animator_scene)
    AnimatorScene scene;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initViewPager();
        initPagerListeners();
    }

    private void initViewPager() {
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        for (int i = 0; i < 5; i++) {
            pagerAdapter.addFragment(new IntroPageFragment());
        }

        viewPager.setAdapter(pagerAdapter);
    }

    private void initPagerListeners() {
        ViewPagerAnimatorAdapter animatorAdapter = new ViewPagerAnimatorAdapter(scene.getScene());
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                animatorAdapter.onPageScrolled(position, positionOffset);
            }
        });
    }


}
