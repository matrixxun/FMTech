package com.google.android.finsky.billing.promptforfop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.fragments.LoggingFragment;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.play.layout.PlayActionButton;

public final class PromptForFopMessageFragment
  extends LoggingFragment
  implements View.OnClickListener
{
  public static PromptForFopMessageFragment newInstance(String paramString1, String paramString2, int paramInt)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("authAccount", paramString1);
    localBundle.putString("PromptForFopMessageFragment.message", paramString2);
    localBundle.putInt("PromptForFopMessageFragment.playlog_ui_element_type", paramInt);
    PromptForFopMessageFragment localPromptForFopMessageFragment = new PromptForFopMessageFragment();
    localPromptForFopMessageFragment.setArguments(localBundle);
    return localPromptForFopMessageFragment;
  }
  
  protected final int getPlayStoreUiElementType()
  {
    return this.mArguments.getInt("PromptForFopMessageFragment.playlog_ui_element_type");
  }
  
  public final void onClick(View paramView)
  {
    if ((getActivity() instanceof Listener))
    {
      logClickEvent(1006);
      ((Listener)getActivity()).onContinueClicked();
      return;
    }
    FinskyLog.wtf("Parent activity expected to implement Listener interface.", new Object[0]);
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    ViewGroup localViewGroup = (ViewGroup)paramLayoutInflater.inflate(2130969027, paramViewGroup, false);
    ((TextView)localViewGroup.findViewById(2131755233)).setText(this.mArguments.getString("PromptForFopMessageFragment.message"));
    ((PlayActionButton)localViewGroup.findViewById(2131755302)).configure(3, 2131362062, this);
    return localViewGroup;
  }
  
  public static abstract interface Listener
  {
    public abstract void onContinueClicked();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.promptforfop.PromptForFopMessageFragment
 * JD-Core Version:    0.7.0.1
 */