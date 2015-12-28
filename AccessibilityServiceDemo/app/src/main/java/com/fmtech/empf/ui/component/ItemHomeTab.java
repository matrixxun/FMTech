package com.fmtech.empf.ui.component;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fmtech.accessibilityservicedemo.R;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/25 10:54
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/25 10:54  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class ItemHomeTab extends RelativeLayout{

    private TextView mTabTitle;
    private MessageReminder mMessageReminder;

    public ItemHomeTab(Context context) {
        super(context);
    }

    public ItemHomeTab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTabTitle = (TextView)findViewById(R.id.home_tab_title);
        mMessageReminder = (MessageReminder)findViewById(R.id.home_tab_message_reminder);
    }

    public void setTabTitle(String title){
        if(!TextUtils.isEmpty(title)){
            mTabTitle.setText(title);
        }
    }

    public void setTabImage(int imageRes){
        mTabTitle.setCompoundDrawablesWithIntrinsicBounds(0, imageRes, 0, 0);
    }

    public void setMessageCount(int count){
        if(count < 0){
            return;
        }
        if(count == 0){
            mMessageReminder.setVisibility(View.GONE);
        }else{
            mMessageReminder.setMessageCount(count);
        }
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
    }

}
