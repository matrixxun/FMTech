package com.google.android.gms.googlehelp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import java.util.List;

public final class GoogleHelpLauncher
{
  public final Activity mActivity;
  public final GoogleApiClient mApiClient;
  
  public GoogleHelpLauncher(Activity paramActivity)
  {
    this(paramActivity, new GoogleApiClient.Builder(paramActivity).addApi(zzc.API).build());
  }
  
  private GoogleHelpLauncher(Activity paramActivity, GoogleApiClient paramGoogleApiClient)
  {
    this.mActivity = paramActivity;
    this.mApiClient = paramGoogleApiClient;
  }
  
  public final void handlePlayServicesUnavailable(int paramInt, Intent paramIntent)
  {
    GoogleHelp localGoogleHelp = (GoogleHelp)paramIntent.getParcelableExtra("EXTRA_GOOGLE_HELP");
    Intent localIntent = new Intent("android.intent.action.VIEW").setData(localGoogleHelp.zzbaO);
    if (paramInt != 7)
    {
      if (this.mActivity.getPackageManager().queryIntentActivities(localIntent, 0).size() > 0) {}
      for (int i = 1; i != 0; i = 0)
      {
        this.mActivity.startActivity(localIntent);
        return;
      }
    }
    GooglePlayServicesUtil.showErrorDialogFragment$70a48c07(paramInt, this.mActivity);
  }
  
  public static abstract interface OnToggleFailedListener
  {
    public abstract void onToggleFailed();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.googlehelp.GoogleHelpLauncher
 * JD-Core Version:    0.7.0.1
 */