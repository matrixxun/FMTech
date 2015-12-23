package com.google.android.finsky.layout.play;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.play.utils.PlayUtils;

public class PlayRatingBar
  extends ViewGroup
  implements View.OnClickListener, View.OnFocusChangeListener, PlayRatingStar.OnPressStateChangeListener
{
  private int mCurrentRating;
  private int mExtraVerticalPadding;
  private OnRatingChangeListener mListener;
  private PlayRatingStar[] mStars;
  
  public PlayRatingBar(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayRatingBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void resetVisualState()
  {
    int i = 0;
    if (i < this.mStars.length)
    {
      this.mStars[i].setFocused(false);
      PlayRatingStar localPlayRatingStar = this.mStars[i];
      if (i < this.mCurrentRating) {}
      for (boolean bool = true;; bool = false)
      {
        localPlayRatingStar.setFilled(bool);
        i++;
        break;
      }
    }
  }
  
  public final void configure(int paramInt1, int paramInt2, OnRatingChangeListener paramOnRatingChangeListener)
  {
    this.mListener = paramOnRatingChangeListener;
    int i = 0;
    if (i < 5)
    {
      PlayRatingStar localPlayRatingStar = this.mStars[i];
      localPlayRatingStar.mIndex = i;
      localPlayRatingStar.mNormalSvgResourceId = 2131230721;
      localPlayRatingStar.mFilledSvgResourceId = 2131230720;
      localPlayRatingStar.mFocusedSvgResourceId = 2130837606;
      localPlayRatingStar.mNormalColorId = 2131689625;
      localPlayRatingStar.mFilledColorId = CorpusResourceUtils.getPrimaryColorResId(paramInt2);
      localPlayRatingStar.mNormalResourceId = 2130837613;
      localPlayRatingStar.mFocusedResourceId = 2130837614;
      int j;
      switch (paramInt2)
      {
      case 5: 
      default: 
        throw new IllegalArgumentException("Unsupported backend ID (" + paramInt2 + ")");
      case 1: 
        j = 2130837604;
      }
      for (;;)
      {
        localPlayRatingStar.mFilledResourceId = j;
        switch (paramInt2)
        {
        case 5: 
        default: 
          throw new IllegalArgumentException("Unsupported backend ID (" + paramInt2 + ")");
          j = 2130837609;
          continue;
          j = 2130837607;
          continue;
          j = 2130837611;
          continue;
          if (CorpusResourceUtils.isEnterpriseTheme()) {
            j = 2130837601;
          } else {
            j = 2130837600;
          }
          break;
        }
      }
      int k = 2130837605;
      label274:
      localPlayRatingStar.mFilledFocusedResourceId = k;
      localPlayRatingStar.syncState();
      localPlayRatingStar.setOnFocusChangeListener(this);
      localPlayRatingStar.setOnPressStateChangeListener(this);
      localPlayRatingStar.setOnClickListener(this);
      if (i < paramInt1) {}
      for (boolean bool = true;; bool = false)
      {
        localPlayRatingStar.setFilled(bool);
        i++;
        break;
        k = 2130837610;
        break label274;
        k = 2130837608;
        break label274;
        k = 2130837612;
        break label274;
        if (CorpusResourceUtils.isEnterpriseTheme())
        {
          k = 2130837602;
          break label274;
        }
        k = 2130837603;
        break label274;
      }
    }
    this.mCurrentRating = paramInt1;
  }
  
  public int getRating()
  {
    return this.mCurrentRating;
  }
  
  public void onClick(View paramView)
  {
    int i = ((PlayRatingStar)paramView).getIndex();
    int j = 0;
    if (j < this.mStars.length)
    {
      PlayRatingStar localPlayRatingStar = this.mStars[j];
      if (j <= i) {}
      for (boolean bool = true;; bool = false)
      {
        localPlayRatingStar.setFilled(bool);
        j++;
        break;
      }
    }
    this.mCurrentRating = (i + 1);
    this.mListener.onRatingChanged(this, i + 1);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    Resources localResources = getResources();
    this.mStars = new PlayRatingStar[5];
    this.mStars[0] = ((PlayRatingStar)findViewById(2131756004));
    this.mStars[0].setContentDescription(localResources.getString(2131362437));
    this.mStars[1] = ((PlayRatingStar)findViewById(2131756005));
    this.mStars[1].setContentDescription(localResources.getString(2131362806));
    this.mStars[2] = ((PlayRatingStar)findViewById(2131756006));
    this.mStars[2].setContentDescription(localResources.getString(2131362786));
    this.mStars[3] = ((PlayRatingStar)findViewById(2131756007));
    this.mStars[3].setContentDescription(localResources.getString(2131362179));
    this.mStars[4] = ((PlayRatingStar)findViewById(2131756008));
    this.mStars[4].setContentDescription(localResources.getString(2131362160));
  }
  
  public void onFocusChange(View paramView, boolean paramBoolean)
  {
    View localView = getFocusedChild();
    if ((localView == null) || (!localView.isFocused()))
    {
      resetVisualState();
      return;
    }
    int i = ((PlayRatingStar)paramView).getIndex();
    if (!paramBoolean)
    {
      this.mStars[i].setFocused(false);
      this.mStars[i].setFilled(false);
      return;
    }
    int j = 0;
    label60:
    if (j < this.mStars.length) {
      if (j > i) {
        break label110;
      }
    }
    label110:
    for (boolean bool = true;; bool = false)
    {
      this.mStars[j].setFocused(bool);
      this.mStars[j].setFilled(bool);
      j++;
      break label60;
      break;
    }
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (ViewCompat.getLayoutDirection(this) == 0) {}
    for (boolean bool = true;; bool = false)
    {
      int i = this.mStars.length;
      int j = getWidth();
      int k = 0;
      for (int m = 0; m < i; m++)
      {
        PlayRatingStar localPlayRatingStar = this.mStars[m];
        int n = localPlayRatingStar.getMeasuredWidth();
        int i1 = localPlayRatingStar.getMeasuredHeight();
        int i2 = PlayUtils.getViewLeftFromParentStart(j, n, bool, k);
        localPlayRatingStar.layout(i2, 0, i2 + n, i1);
        k += n;
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    this.mStars[0].measure(0, 0);
    int j = this.mStars[0].getMeasuredHeight() + 2 * this.mExtraVerticalPadding;
    int k = View.MeasureSpec.makeMeasureSpec(j, 1073741824);
    int m = i;
    int n = this.mStars.length;
    for (int i1 = 0; i1 < n; i1++)
    {
      int i2 = m / (n - i1);
      this.mStars[i1].measure(View.MeasureSpec.makeMeasureSpec(i2, 1073741824), k);
      m -= i2;
    }
    setMeasuredDimension(i, j);
  }
  
  public final void onPressStateChanged(View paramView, boolean paramBoolean)
  {
    int i = ((PlayRatingStar)paramView).getIndex();
    if (!paramBoolean)
    {
      int k = 0;
      if (k < this.mStars.length)
      {
        PlayRatingStar localPlayRatingStar2 = this.mStars[k];
        if (k < this.mCurrentRating) {}
        for (boolean bool2 = true;; bool2 = false)
        {
          localPlayRatingStar2.setFilled(bool2);
          k++;
          break;
        }
      }
    }
    else
    {
      int j = 0;
      if (j < this.mStars.length)
      {
        PlayRatingStar localPlayRatingStar1 = this.mStars[j];
        if (j <= i) {}
        for (boolean bool1 = true;; bool1 = false)
        {
          localPlayRatingStar1.setFilled(bool1);
          j++;
          break;
        }
      }
    }
  }
  
  public void setRating(int paramInt)
  {
    this.mCurrentRating = paramInt;
    resetVisualState();
  }
  
  public void setVerticalPadding(int paramInt)
  {
    if (paramInt > 0) {}
    for (int i = getResources().getDimensionPixelSize(paramInt);; i = 0)
    {
      this.mExtraVerticalPadding = i;
      requestLayout();
      invalidate();
      return;
    }
  }
  
  public static abstract interface OnRatingChangeListener
  {
    public abstract void onRatingChanged(PlayRatingBar paramPlayRatingBar, int paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayRatingBar
 * JD-Core Version:    0.7.0.1
 */