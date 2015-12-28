package com.fmtech.empf.ui.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fmtech.accessibilityservicedemo.R;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/25 11:52
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/25 11:52  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class MessageReminder extends LinearLayout{

    private TextView mMessageCount;
    private TextView mMessagePlus;

    public MessageReminder(Context context) {
        super(context);
    }

    public MessageReminder(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMessageCount = (TextView)findViewById(R.id.home_tab_message_count);
        mMessagePlus = (TextView)findViewById(R.id.home_tab_message_plus);
    }

    public void setMessageCount(int count){
        if(count <0){
            return;
        }
        if(count >=99){
            mMessagePlus.setVisibility(View.VISIBLE);
            mMessageCount.setVisibility(View.VISIBLE);
            mMessageCount.setText("" + count);
        }else if(count >0 && count <99){
            mMessagePlus.setVisibility(View.GONE);
            mMessageCount.setVisibility(View.VISIBLE);
            mMessageCount.setText(""+count);
        }else if(count == 0){
            mMessageCount.setVisibility(View.GONE);
            mMessagePlus.setVisibility(View.GONE);
        }

    }
}
