package com.google.android.wallet.ui.common;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;

public class BaseWalletUiComponentDialogFragment
  extends DialogFragment
{
  public static Bundle createArgs(int paramInt)
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("themeResourceId", paramInt);
    return localBundle;
  }
  
  public final ContextThemeWrapper getThemedContext()
  {
    int i = this.mArguments.getInt("themeResourceId");
    if (i <= 0) {
      throw new IllegalArgumentException("Invalid theme resource id: " + i);
    }
    return new ContextThemeWrapper(getActivity(), i);
  }
  
  public final LayoutInflater getThemedLayoutInflater()
  {
    return getActivity().getLayoutInflater().cloneInContext(getThemedContext());
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.BaseWalletUiComponentDialogFragment
 * JD-Core Version:    0.7.0.1
 */