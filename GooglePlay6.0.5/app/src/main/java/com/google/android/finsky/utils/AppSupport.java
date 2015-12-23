package com.google.android.finsky.utils;

import android.support.v4.app.FragmentManager;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.SimpleAlertDialog;
import com.google.android.finsky.activities.SimpleAlertDialog.Builder;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.library.RevokeListenerWrapper;
import com.google.android.finsky.receivers.Installer;

public final class AppSupport
{
  public static void silentRefund(FragmentManager paramFragmentManager, final String paramString1, String paramString2, boolean paramBoolean, final RefundListener paramRefundListener)
  {
    paramRefundListener.onRefundStart();
    DfeApi localDfeApi = FinskyApp.get().getDfeApi(paramString2);
    localDfeApi.revoke$b40b8a6(paramString1, new RevokeListenerWrapper(FinskyApp.get().mLibraryReplicators, localDfeApi.getAccount(), new Runnable()
    {
      public final void run()
      {
        MyAccountHelper.sLastMutationTimeMs = System.currentTimeMillis();
        if (this.val$tryUninstall) {
          FinskyApp.get().mInstaller.uninstallAssetSilently(paramString1, false);
        }
        if (paramRefundListener != null) {
          paramRefundListener.onRefundComplete(true);
        }
      }
    }), new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        if (this.val$fragmentManager != null)
        {
          FragmentManager localFragmentManager = this.val$fragmentManager;
          SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
          localBuilder.setMessageId(2131362813).setPositiveId(2131362418);
          localBuilder.build().show(localFragmentManager, "refund_failure");
        }
        if (paramRefundListener != null) {
          paramRefundListener.onRefundComplete(false);
        }
      }
    });
  }
  
  public static abstract interface RefundListener
  {
    public abstract void onRefundComplete(boolean paramBoolean);
    
    public abstract void onRefundStart();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.AppSupport
 * JD-Core Version:    0.7.0.1
 */