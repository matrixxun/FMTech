package com.google.android.finsky.billing.lightpurchase;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public abstract class AppCompatDelegateActivity
  extends FragmentActivity
{
  private AppCompatDelegate mDelegate;
  
  private AppCompatDelegate getDelegate()
  {
    if (shouldDisableAppCompatDelegation()) {
      return null;
    }
    if (this.mDelegate == null) {
      this.mDelegate = AppCompatDelegate.create(this, null);
    }
    return this.mDelegate;
  }
  
  public void addContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    AppCompatDelegate localAppCompatDelegate = getDelegate();
    if (localAppCompatDelegate != null)
    {
      localAppCompatDelegate.addContentView(paramView, paramLayoutParams);
      return;
    }
    super.addContentView(paramView, paramLayoutParams);
  }
  
  public MenuInflater getMenuInflater()
  {
    AppCompatDelegate localAppCompatDelegate = getDelegate();
    if (localAppCompatDelegate != null) {
      return localAppCompatDelegate.getMenuInflater();
    }
    return super.getMenuInflater();
  }
  
  public void invalidateOptionsMenu()
  {
    AppCompatDelegate localAppCompatDelegate = getDelegate();
    if (localAppCompatDelegate != null)
    {
      localAppCompatDelegate.invalidateOptionsMenu();
      return;
    }
    super.invalidateOptionsMenu();
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    AppCompatDelegate localAppCompatDelegate = getDelegate();
    if (localAppCompatDelegate != null) {
      localAppCompatDelegate.onConfigurationChanged(paramConfiguration);
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    AppCompatDelegate localAppCompatDelegate = getDelegate();
    if (localAppCompatDelegate != null)
    {
      localAppCompatDelegate.installViewFactory();
      localAppCompatDelegate.onCreate$79e5e33f();
    }
    super.onCreate(paramBundle);
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    AppCompatDelegate localAppCompatDelegate = getDelegate();
    if (localAppCompatDelegate != null) {
      localAppCompatDelegate.onDestroy();
    }
  }
  
  protected void onPostCreate(Bundle paramBundle)
  {
    super.onPostCreate(paramBundle);
    AppCompatDelegate localAppCompatDelegate = getDelegate();
    if (localAppCompatDelegate != null) {
      localAppCompatDelegate.onPostCreate$79e5e33f();
    }
  }
  
  protected void onPostResume()
  {
    super.onPostResume();
    AppCompatDelegate localAppCompatDelegate = getDelegate();
    if (localAppCompatDelegate != null) {
      localAppCompatDelegate.onPostResume();
    }
  }
  
  public void onStop()
  {
    super.onStop();
    AppCompatDelegate localAppCompatDelegate = getDelegate();
    if (localAppCompatDelegate != null) {
      localAppCompatDelegate.onStop();
    }
  }
  
  protected void onTitleChanged(CharSequence paramCharSequence, int paramInt)
  {
    super.onTitleChanged(paramCharSequence, paramInt);
    AppCompatDelegate localAppCompatDelegate = getDelegate();
    if (localAppCompatDelegate != null) {
      localAppCompatDelegate.setTitle(paramCharSequence);
    }
  }
  
  public void setContentView(int paramInt)
  {
    AppCompatDelegate localAppCompatDelegate = getDelegate();
    if (localAppCompatDelegate != null)
    {
      localAppCompatDelegate.setContentView(paramInt);
      return;
    }
    super.setContentView(paramInt);
  }
  
  public void setContentView(View paramView)
  {
    AppCompatDelegate localAppCompatDelegate = getDelegate();
    if (localAppCompatDelegate != null)
    {
      localAppCompatDelegate.setContentView(paramView);
      return;
    }
    super.setContentView(paramView);
  }
  
  public void setContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams)
  {
    AppCompatDelegate localAppCompatDelegate = getDelegate();
    if (localAppCompatDelegate != null)
    {
      localAppCompatDelegate.setContentView(paramView, paramLayoutParams);
      return;
    }
    super.setContentView(paramView, paramLayoutParams);
  }
  
  public boolean shouldDisableAppCompatDelegation()
  {
    return false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.AppCompatDelegateActivity
 * JD-Core Version:    0.7.0.1
 */