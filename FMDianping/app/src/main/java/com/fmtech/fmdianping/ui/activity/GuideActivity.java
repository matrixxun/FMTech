package com.fmtech.fmdianping.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.fmtech.fmdianping.R;
import com.fmtech.fmdianping.utils.Constants;
import com.fmtech.fmdianping.utils.FMSharedPres;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/11/8 14:51
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/11/8 14:51  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class GuideActivity extends AppCompatActivity {

    @ViewInject(R.id.btn_enter_now)
    private Button mEnterNow;

    @ViewInject(R.id.vp_guide)
    private ViewPager mGuideViewPager;

    private List<View> mPagers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        x.view().inject(this);

        initViewPager();
    }

    private void initViewPager(){
        mPagers = new ArrayList<View>();
        ImageView guide0 = new ImageView(this);
//        guide0.setImageResource(R.mipmap.guide_01);
        guide0.setBackgroundResource(R.mipmap.guide_01);
        ImageView guide1 = new ImageView(this);
        guide1.setImageResource(R.mipmap.guide_02);
        ImageView guide2 = new ImageView(this);
        guide2.setImageResource(R.mipmap.guide_03);
        guide2.setScaleType(ImageView.ScaleType.FIT_XY);
        mPagers.add(guide0);
        mPagers.add(guide1);
        mPagers.add(guide2);

        mGuideViewPager.setAdapter(new GuidePagerAdapter());
        mGuideViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    mEnterNow.setVisibility(View.VISIBLE);
                } else {
                    mEnterNow.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Event(R.id.btn_enter_now)
    private void enterNow(View view){
        toMainPage();
    }

    private void toMainPage(){
        Intent intent = new Intent();
        intent.setClass(GuideActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        FMSharedPres.shareInstance().putBoolean(Constants.IS_FIRST_LOGIN, false).commit();
    }

    class GuidePagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mPagers.get(position));
            return mPagers.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View)object);
        }
    }
}
