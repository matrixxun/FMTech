package com.google.android.play.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.google.android.play.R.color;
import com.google.android.play.R.dimen;
import com.google.android.play.R.drawable;
import com.google.android.play.R.string;
import com.google.android.play.utils.Assertions;

public class PageIndicator
  extends LinearLayout
{
  private int mSelectedColorResId = R.color.play_onboard__page_indicator_dot_active;
  private int mSelectedPage = -1;
  private int mUnselectedColorResId = R.color.play_onboard__page_indicator_dot_inactive;
  
  public PageIndicator(Context paramContext)
  {
    this(paramContext, null, 0);
  }
  
  public PageIndicator(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setGravity(16);
  }
  
  public PageIndicator(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setGravity(16);
  }
  
  private void updateContentDescription()
  {
    Resources localResources = getResources();
    int i = R.string.play_content_description_page_indicator;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(1 + getSelectedPage());
    arrayOfObject[1] = Integer.valueOf(getPageCount());
    setContentDescription(localResources.getString(i, arrayOfObject));
  }
  
  protected int getDotHorizontalMargin()
  {
    return getResources().getDimensionPixelSize(R.dimen.play_onboard__page_indicator_dot_diameter) / 2;
  }
  
  protected int getDotVerticalMargin()
  {
    return 0;
  }
  
  public int getPageCount()
  {
    return getChildCount();
  }
  
  public int getSelectedPage()
  {
    return this.mSelectedPage;
  }
  
  protected ImageView setDotState(ImageView paramImageView, boolean paramBoolean1, boolean paramBoolean2, int paramInt)
  {
    GradientDrawable localGradientDrawable = (GradientDrawable)paramImageView.getDrawable();
    if (paramBoolean1) {}
    for (int i = this.mSelectedColorResId;; i = this.mUnselectedColorResId)
    {
      localGradientDrawable.setColor(getResources().getColor(i));
      return paramImageView;
    }
  }
  
  public void setPageCount(int paramInt)
  {
    boolean bool1;
    int i;
    if (paramInt >= 0)
    {
      bool1 = true;
      Assertions.checkArgument(bool1, "numPages must be >=0");
      i = getChildCount();
      if (paramInt >= i) {
        break label40;
      }
      removeViews(paramInt, i - paramInt);
    }
    label40:
    while (paramInt <= i)
    {
      updateContentDescription();
      return;
      bool1 = false;
      break;
    }
    int j = i;
    label48:
    ImageView localImageView;
    if (j < paramInt)
    {
      localImageView = new ImageView(getContext());
      localImageView.setScaleType(ImageView.ScaleType.CENTER);
      localImageView.setImageResource(R.drawable.play_onboard_page_indicator_dot);
      LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -2);
      int k = getDotHorizontalMargin();
      int m = getDotVerticalMargin();
      localLayoutParams.gravity = 16;
      localLayoutParams.setMargins(k, m, k, m);
      addView(localImageView, localLayoutParams);
      if (j != this.mSelectedPage) {
        break label166;
      }
    }
    label166:
    for (boolean bool2 = true;; bool2 = false)
    {
      setDotState(localImageView, bool2, false, j);
      j++;
      break label48;
      break;
    }
  }
  
  public void setSelectedColorResId(int paramInt)
  {
    this.mSelectedColorResId = paramInt;
    invalidate();
  }
  
  public void setSelectedPage(int paramInt)
  {
    if (this.mSelectedPage != paramInt)
    {
      int i = getChildCount();
      int j = 0;
      if (j < i)
      {
        ImageView localImageView = (ImageView)getChildAt(j);
        if (j == paramInt) {}
        for (boolean bool = true;; bool = false)
        {
          setDotState(localImageView, bool, true, j);
          j++;
          break;
        }
      }
      this.mSelectedPage = paramInt;
      updateContentDescription();
    }
  }
  
  public void setUnselectedColorResId(int paramInt)
  {
    this.mUnselectedColorResId = paramInt;
    invalidate();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.widget.PageIndicator
 * JD-Core Version:    0.7.0.1
 */