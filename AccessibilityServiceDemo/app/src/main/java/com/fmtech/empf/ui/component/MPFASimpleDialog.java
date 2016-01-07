package com.fmtech.empf.ui.component;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fmtech.accessibilityservicedemo.R;

/**
 * ==================================================================
 * Copyright (C) 2016 FMTech All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @email chiangchuna@gmail.com
 * @create_date 2016/1/7 11:05
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 2016/1/7 11:05  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */

public class MPFASimpleDialog extends Dialog{

    private TextView mTitle;
    private TextView mMessage;
    private Button mButton;

    public MPFASimpleDialog(Context context) {
        super(context);
    }

    public MPFASimpleDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected MPFASimpleDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public MPFASimpleDialog(Context context, String title, String message, String btnText, android.view.View.OnClickListener onClickListener){
        super(context, R.style.MPFADialog);
        View dialogContentView = View.inflate(context, R.layout.dialog_mpfa_simple, null);
        mTitle = (TextView)dialogContentView.findViewById(R.id.tv_dialog_mpfa_title);
        mMessage = (TextView)dialogContentView.findViewById(R.id.tv_dialog_mpfa_msg);
        mButton = (Button)dialogContentView.findViewById(R.id.btn_dialog_mpfa_simple);

        if(null != title){
            mTitle.setText(title);
        }

        if(null != message){
            mMessage.setText(message);
        }

        if(null != btnText){
            mButton.setText(btnText);
        }

        if(null != onClickListener){
            mButton.setOnClickListener(onClickListener);
        }

        setContentView(dialogContentView);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }
}
