package com.google.android.wallet.instrumentmanager.ui.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gsf.GservicesValue;
import com.google.android.wallet.common.util.PaymentUtils;
import com.google.android.wallet.config.G.images;
import com.google.android.wallet.instrumentmanager.R.attr;
import com.google.android.wallet.instrumentmanager.R.id;
import com.google.android.wallet.ui.common.FifeNetworkImageView;

public class TopBarView
  extends LinearLayout
{
  private boolean mHideTitleIcon;
  FifeNetworkImageView mTitleImageView;
  TextView mTitleTextView;
  
  public TopBarView(Context paramContext)
  {
    super(paramContext);
    readAttributes$643f623b(paramContext);
  }
  
  public TopBarView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    readAttributes$643f623b(paramContext);
  }
  
  @TargetApi(11)
  public TopBarView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    readAttributes$643f623b(paramContext);
  }
  
  @TargetApi(21)
  public TopBarView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    readAttributes$643f623b(paramContext);
  }
  
  private void readAttributes$643f623b(Context paramContext)
  {
    int[] arrayOfInt = new int[1];
    arrayOfInt[0] = R.attr.imHideTitleIcon;
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(arrayOfInt);
    this.mHideTitleIcon = localTypedArray.getBoolean(0, false);
    localTypedArray.recycle();
  }
  
  public CharSequence getTitle()
  {
    return this.mTitleTextView.getText();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mTitleTextView = ((TextView)findViewById(R.id.title));
    this.mTitleImageView = ((FifeNetworkImageView)findViewById(R.id.title_icon));
  }
  
  public final void setTitle(CharSequence paramCharSequence, String paramString1, String paramString2)
  {
    if (!TextUtils.isEmpty(paramCharSequence))
    {
      this.mTitleTextView.setText(paramCharSequence);
      this.mTitleTextView.setVisibility(0);
    }
    while ((!this.mHideTitleIcon) && (!TextUtils.isEmpty(paramString1)))
    {
      this.mTitleImageView.setVisibility(0);
      this.mTitleImageView.setFadeIn(true);
      this.mTitleImageView.setFifeImageUrl(paramString1, PaymentUtils.getImageLoader(getContext().getApplicationContext()), ((Boolean)G.images.useWebPForFife.get()).booleanValue());
      this.mTitleImageView.setContentDescription(paramString2);
      return;
      this.mTitleTextView.setVisibility(8);
    }
    this.mTitleImageView.setVisibility(8);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.common.TopBarView
 * JD-Core Version:    0.7.0.1
 */