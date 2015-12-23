package com.google.android.wallet.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseWalletUiComponentFragment
  extends Fragment
{
  public int mThemeResourceId;
  public ContextThemeWrapper mThemedContext;
  public LayoutInflater mThemedInflater;
  
  public static Bundle createArgs(int paramInt)
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("themeResourceId", paramInt);
    return localBundle;
  }
  
  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mThemeResourceId = this.mArguments.getInt("themeResourceId");
    if (this.mThemeResourceId <= 0) {
      throw new IllegalArgumentException("Invalid theme resource id: " + this.mThemeResourceId);
    }
    this.mThemedContext = new ContextThemeWrapper(getActivity(), this.mThemeResourceId);
    if ((paramBundle != null) && (paramBundle.containsKey("expandableSavedInstance"))) {
      paramBundle.getParcelable("expandableSavedInstance");
    }
  }
  
  public abstract View onCreateThemedView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle);
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mThemedInflater = paramLayoutInflater.cloneInContext(this.mThemedContext);
    return onCreateThemedView(this.mThemedInflater, paramViewGroup, paramBundle);
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
  }
  
  public final void startActivityForResultFromTopLevelFragment(Intent paramIntent, int paramInt)
  {
    for (Object localObject = this; ((Fragment)localObject).mParentFragment != null; localObject = ((Fragment)localObject).mParentFragment) {}
    ((Fragment)localObject).startActivityForResult(paramIntent, paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.BaseWalletUiComponentFragment
 * JD-Core Version:    0.7.0.1
 */