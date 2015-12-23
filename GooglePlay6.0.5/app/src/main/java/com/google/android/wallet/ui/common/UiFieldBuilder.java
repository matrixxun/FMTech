package com.google.android.wallet.ui.common;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.google.android.wallet.common.util.PaymentUtils;
import com.google.android.wallet.uicomponents.R.layout;
import com.google.android.wallet.uicomponents.R.string;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiField;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiField.DateField;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiField.TextField;

public final class UiFieldBuilder
{
  public Activity mActivity;
  public TooltipUiFieldView.OnTooltipIconClickListener mOnTooltipIconClickListener;
  private final LayoutInflater mThemedInflater;
  private final UiFieldOuterClass.UiField mUiField;
  public int mViewId = -1;
  
  public UiFieldBuilder(UiFieldOuterClass.UiField paramUiField, LayoutInflater paramLayoutInflater)
  {
    this.mUiField = paramUiField;
    this.mThemedInflater = paramLayoutInflater;
  }
  
  public final <T extends View> T build()
  {
    ImageLoader localImageLoader;
    if (this.mActivity == null) {
      localImageLoader = null;
    }
    View localView;
    for (;;)
    {
      if (this.mUiField.textField != null) {
        if (this.mUiField.isDisabled)
        {
          if (TextUtils.isEmpty(this.mUiField.textField.initialValue))
          {
            throw new IllegalArgumentException("Read only text field without an initial value. Name=" + this.mUiField.name);
            localImageLoader = PaymentUtils.getImageLoader(this.mActivity.getApplicationContext());
            continue;
          }
          localView = this.mThemedInflater.inflate(R.layout.view_form_non_editable_text, null);
          ((TextView)localView).setText(this.mUiField.textField.initialValue);
        }
      }
    }
    for (;;)
    {
      if (this.mViewId != -1) {
        localView.setId(this.mViewId);
      }
      if (this.mUiField.tooltip == null) {
        return localView;
      }
      if (this.mOnTooltipIconClickListener != null) {
        break label414;
      }
      throw new IllegalArgumentException("An OnTooltipIconClickListener is required if the UI field has a tooltip.");
      localView = this.mThemedInflater.inflate(R.layout.view_form_edit_text, null);
      WalletUiUtils.applyUiFieldSpecificationToFormEditText(this.mUiField, (FormEditText)localView, this.mActivity);
      continue;
      if (this.mUiField.dateField != null)
      {
        if (this.mUiField.isDisabled) {
          throw new IllegalArgumentException("Date fields don't support disabled look since there is no initial value.");
        }
        if (this.mUiField.dateField.type != 2) {
          throw new IllegalArgumentException("Date fields only support month and year. type=" + this.mUiField.dateField.type);
        }
        if (!TextUtils.isEmpty(this.mUiField.label)) {
          throw new IllegalArgumentException("Date fields do not support labels.");
        }
        localView = this.mThemedInflater.inflate(R.layout.view_exp_edit_text, null);
        this.mUiField.label = this.mThemedInflater.getContext().getString(R.string.wallet_uic_exp_date);
        WalletUiUtils.applyUiFieldSpecificationToFormEditText(this.mUiField, (FormEditText)localView, this.mActivity);
        this.mUiField.label = "";
      }
      else
      {
        if (this.mUiField.selectField == null) {
          break;
        }
        localView = this.mThemedInflater.inflate(R.layout.view_select_field, null);
        ((SelectFieldView)localView).setSelectUiField(this.mUiField, localImageLoader);
      }
    }
    throw new IllegalArgumentException("UiField is not supported: " + this.mUiField);
    label414:
    TooltipUiFieldView localTooltipUiFieldView = new TooltipUiFieldView(this.mThemedInflater.getContext());
    localTooltipUiFieldView.setContent(localView, this.mUiField.tooltip, localImageLoader);
    localTooltipUiFieldView.setOnTooltipIconClickListener(this.mOnTooltipIconClickListener);
    return localTooltipUiFieldView;
    return localView;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.UiFieldBuilder
 * JD-Core Version:    0.7.0.1
 */