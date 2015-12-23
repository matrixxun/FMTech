package com.google.android.finsky.activities;

import android.view.View;
import com.google.android.finsky.utils.ObjectMap;

public abstract interface ViewPagerTab
{
  public abstract View getView(int paramInt);
  
  @Deprecated
  public abstract void onDestroy();
  
  public abstract ObjectMap onDestroyView();
  
  public abstract void onRestoreInstanceState(ObjectMap paramObjectMap);
  
  public abstract void setTabSelected(boolean paramBoolean);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.ViewPagerTab
 * JD-Core Version:    0.7.0.1
 */