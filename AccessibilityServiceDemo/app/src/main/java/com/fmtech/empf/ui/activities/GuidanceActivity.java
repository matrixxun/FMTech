package com.fmtech.empf.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fmtech.empf.MainActivity;
import com.fmtech.empf.ui.component.MPFADialog;
import com.fmtech.accessibilityservicedemo.R;
import com.fmtech.empf.ui.component.MPFASimpleDialog;
import com.fmtech.empf.ui.view.NavigationDot;

import java.util.ArrayList;
import java.util.List;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/17 17:26
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/17 17:26  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class GuidanceActivity extends BaseActivity{
    private MPFASimpleDialog mDialog;
    private ViewPager mViewPager;
    private NavigationDot mNavigationDot;

    private List<View> mPagers = new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance);
        initViews();
    }

    private void initViews(){
        mViewPager = (ViewPager)findViewById(R.id.vp_guidance);
        mNavigationDot = (NavigationDot)findViewById(R.id.navigationdot_guidance);
        int[] guidanceDrawables = new int[4];
        guidanceDrawables[0] = R.drawable.new_comer_guide_fg_1;
        guidanceDrawables[1] = R.drawable.new_comer_guide_fg_2;
        guidanceDrawables[2] = R.drawable.new_comer_guide_fg_3;
        guidanceDrawables[3] = R.drawable.new_comer_guide_fg_4;

        for(int i=0; i< 4; i++){
            ImageView pager = new ImageView(this);
            pager.setBackgroundResource(guidanceDrawables[i]);
            mPagers.add(pager);
        }

        mViewPager.setAdapter(new GuidancePagerAdapter());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mNavigationDot.moveToPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void onSkip(View view){
        mDialog = new MPFASimpleDialog(this, "Important Message", "To protect your interest, please never disclose your password to anyone.", "OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                Intent intent = new Intent(GuidanceActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != mDialog){
            if(mDialog.isShowing()){
                mDialog.dismiss();
            }
            mDialog = null;
        }
    }


    class GuidancePagerAdapter extends PagerAdapter{


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mPagers.get(position));
            return mPagers.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public int getCount() {
            return mPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
