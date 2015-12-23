package com.google.android.wallet.ui.address;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.google.android.wallet.ui.common.WalletUiUtils;
import com.google.android.wallet.uicomponents.R.id;
import java.util.ArrayList;

public class DynamicAddressFieldsLayout
  extends RelativeLayout
{
  private SparseBooleanArray mAlwaysHiddenFields;
  RelativeLayout mFieldContainer;
  OnHeightOffsetChangedListener mOnHeightOffsetChangedListener;
  View mProgressBar;
  
  public DynamicAddressFieldsLayout(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DynamicAddressFieldsLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public DynamicAddressFieldsLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  protected final void addViews(ArrayList<View> paramArrayList)
  {
    this.mFieldContainer.removeViews(1, -1 + this.mFieldContainer.getChildCount());
    int i = paramArrayList.size();
    this.mAlwaysHiddenFields = new SparseBooleanArray(i);
    int j = 0;
    if (j < i)
    {
      View localView = (View)paramArrayList.get(j);
      RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-1, -2);
      if (j > 0) {
        localLayoutParams.addRule(WalletUiUtils.sanitizeRelativeLayoutVerb(3), ((View)paramArrayList.get(j - 1)).getId());
      }
      SparseBooleanArray localSparseBooleanArray = this.mAlwaysHiddenFields;
      int k = localView.getId();
      if (localView.getVisibility() != 0) {}
      for (boolean bool = true;; bool = false)
      {
        localSparseBooleanArray.put(k, bool);
        this.mFieldContainer.addView(localView, localLayoutParams);
        j++;
        break;
      }
    }
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mProgressBar = ((ViewStub)findViewById(R.id.progress_bar)).inflate();
    this.mFieldContainer = ((RelativeLayout)findViewById(R.id.dynamic_address_fields_container));
  }
  
  public void replaceView(View paramView1, View paramView2)
  {
    int i = this.mFieldContainer.indexOfChild(paramView1);
    if (i < 0) {
      throw new IllegalArgumentException("oldView: " + paramView1 + " is not present in the fields container");
    }
    if (this.mFieldContainer.indexOfChild(paramView2) >= 0) {
      throw new IllegalArgumentException("newView: " + paramView2 + " is already present in the fields container");
    }
    if (i > 1)
    {
      RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-1, -2);
      localLayoutParams.addRule(WalletUiUtils.sanitizeRelativeLayoutVerb(3), this.mFieldContainer.getChildAt(i - 1).getId());
      paramView2.setLayoutParams(localLayoutParams);
      if (i < -1 + this.mFieldContainer.getChildCount()) {
        ((RelativeLayout.LayoutParams)this.mFieldContainer.getChildAt(i + 1).getLayoutParams()).addRule(WalletUiUtils.sanitizeRelativeLayoutVerb(3), paramView2.getId());
      }
    }
    this.mFieldContainer.removeViewAt(i);
    this.mFieldContainer.addView(paramView2, i);
  }
  
  protected void setAddressFieldsVisibility(int paramInt)
  {
    int i = 1;
    int j = this.mFieldContainer.getChildCount();
    while (i < j)
    {
      View localView = this.mFieldContainer.getChildAt(i);
      if (!this.mAlwaysHiddenFields.get(localView.getId())) {
        localView.setVisibility(paramInt);
      }
      i++;
    }
  }
  
  public void setFields(ArrayList<View> paramArrayList)
  {
    addViews(paramArrayList);
  }
  
  public void setOnHeightOffsetChangedListener(OnHeightOffsetChangedListener paramOnHeightOffsetChangedListener)
  {
    this.mOnHeightOffsetChangedListener = paramOnHeightOffsetChangedListener;
  }
  
  public void switchToShowingFields()
  {
    this.mProgressBar.setVisibility(8);
    setAddressFieldsVisibility(0);
  }
  
  public void switchToShowingProgressBar()
  {
    this.mProgressBar.setVisibility(0);
    setAddressFieldsVisibility(4);
  }
  
  public static abstract interface OnHeightOffsetChangedListener
  {
    public abstract void onHeightOffsetChanged(float paramFloat);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.address.DynamicAddressFieldsLayout
 * JD-Core Version:    0.7.0.1
 */