package com.fmtech.empf.ui.component;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fmtech.accessibilityservicedemo.R;

/**
 * ==================================================================
 * Copyright (C) 2015 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2015/12/17 12:50
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2015/12/17 12:50  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class MPFADialog extends Dialog{
    private TextView mTitle;
    private TextView mMessage;
    private Button mButton1;
    private Button mButton2;

    public MPFADialog(Context context) {
        super(context);
    }

    public MPFADialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected MPFADialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public MPFADialog(Context context, String title, String message, String btnText, android.view.View.OnClickListener onClickListener){
        this(context, title, message, false, btnText, onClickListener, null, null);
    }

    public MPFADialog(Context context, String title, String message, boolean isTwoButton, String btnText1, android.view.View.OnClickListener onClickListener1, String btnText2, android.view.View.OnClickListener onClickListener2){
        super(context, R.style.MPFADialog);
        View dialogContentView = View.inflate(context, R.layout.dialog_mpfa, null);
        mTitle = (TextView)dialogContentView.findViewById(R.id.tv_dialog_mpfa_title);
        mMessage = (TextView)dialogContentView.findViewById(R.id.tv_dialog_mpfa_msg);
        mButton1 = (Button)dialogContentView.findViewById(R.id.btn_dialog_mpfa1);
        mButton2 = (Button)dialogContentView.findViewById(R.id.btn_dialog_mpfa2);
        if(null != title){
            mTitle.setText(title);
        }
        if(null != message){
            mMessage.setText(message);
        }
        if(isTwoButton){
            mButton2.setVisibility(View.VISIBLE);
            if(null != btnText2){
                mButton2.setText(btnText2);
            }
            if(null != onClickListener2){
                mButton2.setOnClickListener(onClickListener2);
            }
        }
        if(null != btnText1){
            mButton1.setText(btnText1);
        }
        if(null != onClickListener1){
            mButton1.setOnClickListener(onClickListener1);
        }

        setContentView(dialogContentView);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }
}
