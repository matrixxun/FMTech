package com.google.android.play.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import com.google.android.libraries.bind.bidi.BidiAwareViewPager;

public class UserAwareViewPager
  extends BidiAwareViewPager
{
  protected boolean mFirstLayout = true;
  private boolean mIsSettingCurrentItem;
  
  public UserAwareViewPager(Context paramContext)
  {
    super(paramContext);
  }
  
  public UserAwareViewPager(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.mFirstLayout = true;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    this.mFirstLayout = false;
  }
  
  public void setAdapter(PagerAdapter paramPagerAdapter)
  {
    this.mFirstLayout = true;
    super.setAdapter(paramPagerAdapter);
  }
  
  public void setCurrentItem(int paramInt)
  {
    this.mIsSettingCurrentItem = true;
    super.setCurrentItem(paramInt);
    this.mIsSettingCurrentItem = false;
  }
  
  public final void setCurrentItem(int paramInt, boolean paramBoolean)
  {
    this.mIsSettingCurrentItem = true;
    super.setCurrentItem(paramInt, paramBoolean);
    this.mIsSettingCurrentItem = false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.widget.UserAwareViewPager
 * JD-Core Version:    0.7.0.1
 */