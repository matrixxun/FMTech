package com.google.android.finsky.activities;

import android.accounts.Account;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.layout.play.GenericUiElementNode;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.wallet.ui.common.AlertDialogBuilderCompat;

public class SimpleAlertDialog
  extends DialogFragment
{
  protected ConfigurableView mConfigurableView = null;
  private PlayStoreUiElementNode mDialogNode;
  private FinskyEventLog mEventLogger;
  private boolean mHasBeenDismissed = false;
  
  private Bundle addViewResults(Bundle paramBundle)
  {
    if (this.mConfigurableView != null)
    {
      Bundle localBundle = this.mConfigurableView.getResult();
      if (localBundle != null)
      {
        if (paramBundle == null) {
          paramBundle = new Bundle();
        }
        paramBundle.putAll(localBundle);
      }
    }
    return paramBundle;
  }
  
  private Listener getListener()
  {
    Fragment localFragment = this.mTarget;
    if ((localFragment instanceof Listener)) {
      return (Listener)localFragment;
    }
    FragmentActivity localFragmentActivity = getActivity();
    if ((localFragmentActivity instanceof Listener)) {
      return (Listener)localFragmentActivity;
    }
    return null;
  }
  
  public final void doNegativeClick()
  {
    dismissInternal(false);
    if (this.mHasBeenDismissed) {
      return;
    }
    this.mHasBeenDismissed = true;
    Bundle localBundle1 = this.mArguments;
    int i = localBundle1.getInt("target_request_code");
    Bundle localBundle2 = addViewResults(localBundle1.getBundle("extra_arguments"));
    int j = localBundle1.getInt("click_event_type_negative", -1);
    if (j != -1) {
      this.mEventLogger.logClickEvent(j, null, this.mDialogNode);
    }
    Listener localListener = getListener();
    if (localListener != null) {
      localListener.onNegativeClick(i, localBundle2);
    }
    onNegativeClick();
  }
  
  public final void doPositiveClick()
  {
    dismissInternal(false);
    if (this.mHasBeenDismissed) {
      return;
    }
    this.mHasBeenDismissed = true;
    Bundle localBundle1 = this.mArguments;
    int i = localBundle1.getInt("target_request_code");
    Bundle localBundle2 = addViewResults(localBundle1.getBundle("extra_arguments"));
    int j = localBundle1.getInt("click_event_type_positive", -1);
    if (j != -1) {
      this.mEventLogger.logClickEvent(j, null, this.mDialogNode);
    }
    Listener localListener = getListener();
    if (localListener != null) {
      localListener.onPositiveClick(i, localBundle2);
    }
    onPositiveClick();
  }
  
  public final ConfigurableView getConfigurableView()
  {
    return this.mConfigurableView;
  }
  
  public void onCancel(DialogInterface paramDialogInterface)
  {
    super.onCancel(paramDialogInterface);
    if (this.mArguments.getBoolean("cancel_does_negative_action", true)) {
      doNegativeClick();
    }
  }
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    Bundle localBundle1 = this.mArguments;
    Account localAccount = (Account)localBundle1.getParcelable("log_account");
    if (localAccount == null) {
      localAccount = FinskyApp.get().getCurrentAccount();
    }
    this.mEventLogger = FinskyApp.get().getEventLogger(localAccount);
    this.mDialogNode = null;
    if (localBundle1.containsKey("impression_type")) {
      this.mDialogNode = new GenericUiElementNode(localBundle1.getInt("impression_type"), localBundle1.getByteArray("impression_cookie"), null, null);
    }
    if ((paramBundle == null) && (this.mDialogNode != null)) {
      this.mEventLogger.logPathImpression(0L, this.mDialogNode);
    }
    AlertDialogBuilderCompat localAlertDialogBuilderCompat = new AlertDialogBuilderCompat(getActivity());
    if (localBundle1.containsKey("title_id")) {
      localAlertDialogBuilderCompat.setTitle(localBundle1.getInt("title_id"));
    }
    if (localBundle1.containsKey("title")) {
      localAlertDialogBuilderCompat.setTitle(localBundle1.getString("title"));
    }
    boolean bool;
    if (localBundle1.containsKey("message_id"))
    {
      localAlertDialogBuilderCompat.setMessage(localBundle1.getInt("message_id"));
      if (localBundle1.containsKey("positive_id")) {
        localAlertDialogBuilderCompat.setPositiveButton(localBundle1.getInt("positive_id"), new DialogInterface.OnClickListener()
        {
          public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            SimpleAlertDialog.this.doPositiveClick();
          }
        });
      }
      if (localBundle1.containsKey("negative_id")) {
        localAlertDialogBuilderCompat.setNegativeButton(localBundle1.getInt("negative_id"), new DialogInterface.OnClickListener()
        {
          public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            SimpleAlertDialog.this.doNegativeClick();
          }
        });
      }
      if (localBundle1.containsKey("force_inverse_background"))
      {
        bool = localBundle1.getBoolean("force_inverse_background");
        if (localAlertDialogBuilderCompat.mPlatformBuilder == null) {
          break label465;
        }
        localAlertDialogBuilderCompat.mPlatformBuilder.setInverseBackgroundForced(bool);
      }
    }
    for (;;)
    {
      if (localBundle1.containsKey("layoutId"))
      {
        int i = localBundle1.getInt("layoutId");
        View localView = LayoutInflater.from(getActivity()).inflate(i, null);
        localAlertDialogBuilderCompat.setView(localView);
        if ((localView instanceof ConfigurableView))
        {
          this.mConfigurableView = ((ConfigurableView)localView);
          if (localBundle1.containsKey("config_arguments"))
          {
            Bundle localBundle2 = localBundle1.getBundle("config_arguments");
            this.mConfigurableView.configureView(localBundle2);
          }
        }
      }
      final Dialog localDialog = localAlertDialogBuilderCompat.create();
      if (!localBundle1.containsKey("layoutId")) {
        localDialog.setOnShowListener(new DialogInterface.OnShowListener()
        {
          public final void onShow(DialogInterface paramAnonymousDialogInterface)
          {
            ((TextView)localDialog.findViewById(16908299)).setMovementMethod(LinkMovementMethod.getInstance());
          }
        });
      }
      if (localBundle1.containsKey("cancel_on_touch_outside")) {
        localDialog.setCanceledOnTouchOutside(localBundle1.getBoolean("cancel_on_touch_outside"));
      }
      return localDialog;
      if (localBundle1.containsKey("message"))
      {
        localAlertDialogBuilderCompat.setMessage(localBundle1.getString("message"));
        break;
      }
      if (!localBundle1.containsKey("messageHtml")) {
        break;
      }
      localAlertDialogBuilderCompat.setMessage(Html.fromHtml(localBundle1.getString("messageHtml")));
      break;
      label465:
      localAlertDialogBuilderCompat.mSupportBuilder.P.mForceInverseBackground = bool;
    }
  }
  
  protected void onNegativeClick() {}
  
  public void onPositiveClick() {}
  
  public static class Builder<T extends Builder>
  {
    private Bundle mArguments = new Bundle();
    private Fragment mTarget = null;
    
    public SimpleAlertDialog build()
    {
      SimpleAlertDialog localSimpleAlertDialog = new SimpleAlertDialog();
      configureDialog(localSimpleAlertDialog);
      return localSimpleAlertDialog;
    }
    
    public final void configureDialog(SimpleAlertDialog paramSimpleAlertDialog)
    {
      paramSimpleAlertDialog.setArguments(this.mArguments);
      if (this.mTarget != null) {
        paramSimpleAlertDialog.setTargetFragment(this.mTarget, 0);
      }
    }
    
    public final T setCallback(Fragment paramFragment, int paramInt, Bundle paramBundle)
    {
      this.mTarget = paramFragment;
      if ((paramBundle != null) || (paramInt != 0))
      {
        this.mArguments.putBundle("extra_arguments", paramBundle);
        this.mArguments.putInt("target_request_code", paramInt);
      }
      return this;
    }
    
    public final T setCancelDoesNegativeAction$5e554932()
    {
      this.mArguments.putBoolean("cancel_does_negative_action", false);
      return this;
    }
    
    public final T setCanceledOnTouchOutside(boolean paramBoolean)
    {
      this.mArguments.putBoolean("cancel_on_touch_outside", paramBoolean);
      return this;
    }
    
    public final T setEventLog(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3, Account paramAccount)
    {
      if (paramAccount == null) {
        paramAccount = FinskyApp.get().getCurrentAccount();
      }
      this.mArguments.putParcelable("log_account", paramAccount);
      this.mArguments.putInt("impression_type", paramInt1);
      this.mArguments.putByteArray("impression_cookie", paramArrayOfByte);
      this.mArguments.putInt("click_event_type_positive", paramInt2);
      this.mArguments.putInt("click_event_type_negative", paramInt3);
      return this;
    }
    
    public final T setLayoutId(int paramInt)
    {
      this.mArguments.putInt("layoutId", paramInt);
      return this;
    }
    
    public final T setMessage(String paramString)
    {
      this.mArguments.putString("message", paramString);
      return this;
    }
    
    public final T setMessageHtml(String paramString)
    {
      this.mArguments.putString("messageHtml", paramString);
      return this;
    }
    
    public final T setMessageId(int paramInt)
    {
      this.mArguments.putInt("message_id", paramInt);
      return this;
    }
    
    public final T setNegativeId(int paramInt)
    {
      this.mArguments.putInt("negative_id", paramInt);
      return this;
    }
    
    public final T setPositiveId(int paramInt)
    {
      this.mArguments.putInt("positive_id", paramInt);
      return this;
    }
    
    public final T setTitle(String paramString)
    {
      this.mArguments.putString("title", paramString);
      return this;
    }
    
    public final T setTitleId(int paramInt)
    {
      this.mArguments.putInt("title_id", paramInt);
      return this;
    }
    
    public final T setViewConfiguration(Bundle paramBundle)
    {
      this.mArguments.putBundle("config_arguments", paramBundle);
      return this;
    }
  }
  
  public static abstract interface ConfigurableView
  {
    public abstract void configureView(Bundle paramBundle);
    
    public abstract Bundle getResult();
  }
  
  public static abstract interface Listener
  {
    public abstract void onNegativeClick(int paramInt, Bundle paramBundle);
    
    public abstract void onPositiveClick(int paramInt, Bundle paramBundle);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.SimpleAlertDialog
 * JD-Core Version:    0.7.0.1
 */