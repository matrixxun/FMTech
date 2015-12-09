package com.fmtech.fmdianping.main.guide.guidance;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.fmtech.fmdianping.R;
import com.fmtech.fmdianping.app.BaseActivity;
import com.fmtech.fmdianping.app.FMEnvironment;
import com.fmtech.fmdianping.base.widget.Flipper;
import com.fmtech.fmdianping.base.widget.FlipperAdapter;
import com.fmtech.fmdianping.util.Constants;
import com.fmtech.fmdianping.util.FMSharedPres;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/11/12 21:07
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/11/12 21:07  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class GuidanceActivity extends BaseActivity{

    protected int mViewCount;
    int[] mBackgrounds = GuidanceDrawable.backgroundImages;
    protected GuidanceFlipper mFlipper;
    int[] mForgrounds = GuidanceDrawable.foregroundImages;
    protected ImageAdapter mImageAdapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFlipper = new GuidanceFlipper(this);
        mFlipper.setBackgroundResource(R.drawable.guidance_background);
        setupViews();
        if(null != mImageAdapter){
            mFlipper.setAdapter(mImageAdapter);
        }

        setContentView(mFlipper);
        mFlipper.setCurrentItem(Integer.valueOf(0));
        mFlipper.update();
        mFlipper.enableNavigationDotView(mViewCount);
    }

    public void setupViews(){
        int[] backgrounds = new int[4];
        backgrounds[0] = R.drawable.new_comer_guide_bg;
        backgrounds[1] = R.drawable.new_comer_guide_bg;
        backgrounds[2] = R.drawable.new_comer_guide_bg;
        backgrounds[3] = R.drawable.new_comer_guide_bg;
        this.mBackgrounds = backgrounds;
        int[] forgrounds = new int[4];
        forgrounds[0] = R.drawable.new_comer_guide_fg_1;
        forgrounds[1] = R.drawable.new_comer_guide_fg_2;
        forgrounds[2] = R.drawable.new_comer_guide_fg_3;
        forgrounds[3] = R.drawable.new_comer_guide_fg_4;
        this.mForgrounds = forgrounds;
        mViewCount = mBackgrounds.length;
        mImageAdapter = new ImageAdapter(this);
    }

    protected void gotoMainActivity(){
        //save new version code
        FMSharedPres.shareInstance().putInt(Constants.PREF_KEY_GUIDANCE_VERSION_CODE, FMEnvironment.versionCode()).commit();
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(Constants.URI_MAIN_ACTIVITY));
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            gotoMainActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class GuidanceFlipper extends Flipper<Integer> {
        public GuidanceFlipper(Context context) {
            super(context);
        }

    }

    class ImageAdapter implements FlipperAdapter<Integer>{

        public ImageAdapter(Context context){

        }

        @Override
        public Integer getNextItem(Integer index) {
			if(index.intValue() == mViewCount){
				startActivity(Constants.URI_MAIN_ACTIVITY);
				finish();
				return null;
			}
			if(index.intValue() + 1 < mViewCount){
				return Integer.valueOf(1 + index.intValue());
			}
            return null;
        }

        @Override
        public Integer getPreviousItem(Integer index) {
            if(null == index){
                return null;
            }
            if(index.intValue() == 0){
                return null;
            }
            if(index.intValue() -1 >=0){
                return Integer.valueOf(index.intValue() -1);
            }
            return null;
        }

        @Override
        public View getView(Integer index, View view) {
			if(index == null || index.intValue() <0){
				return null;
			}
			View targetView;
			if(null == view){
				targetView = View.inflate(GuidanceActivity.this,R.layout.guidance, null);
				((ImageView)targetView.findViewById(R.id.iv_bg)).setImageResource(mBackgrounds[index.intValue()]);
				((ImageView)targetView.findViewById(R.id.iv_fg)).setImageResource(mForgrounds[index.intValue()]);
				ImageView ivSkip = (ImageView)targetView.findViewById(R.id.iv_skip);
				Button btnSkip = (Button)targetView.findViewById(R.id.guidance_skip);
				//TODO set onclick listener
				if(index.intValue() == mBackgrounds.length -1){
					ivSkip.setVisibility(View.VISIBLE);
					btnSkip.setVisibility(View.GONE);
				}else{
					ivSkip.setVisibility(View.GONE);
					btnSkip.setVisibility(View.VISIBLE);
				}
			}else{
				targetView = view;
			}
            return targetView;
        }

        @Override
        public void onMoved(Integer index, Integer param2) {

        }

        @Override
        public void onMoving(Integer index, Integer param2) {

        }

        @Override
        public void onTap(Integer index) {

        }

        @Override
        public void recycleView(View view) {

        }
    }
}
