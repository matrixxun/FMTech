package com.google.android.finsky.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.protos.AcceptTosResponse;
import com.google.android.finsky.protos.Toc.TocResponse;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.GetTocHelper;
import com.google.android.finsky.utils.GetTocHelper.Listener;

public class TosAckedReceiver
  extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if ((paramIntent == null) || (paramIntent.getExtras() == null))
    {
      FinskyLog.w("Invalid Broadcast: requires extras.", new Object[0]);
      return;
    }
    Bundle localBundle = paramIntent.getExtras();
    final String str = localBundle.getString("TosAckedReceiver.account");
    final boolean bool = localBundle.getBoolean("TosAckedReceiver.optIn");
    if (str == null)
    {
      FinskyLog.w("Invalid Broadcast: no account.", new Object[0]);
      return;
    }
    DfeApi localDfeApi = FinskyApp.get().getDfeApi(str);
    if (localDfeApi == null)
    {
      FinskyLog.e("Could not get DFE API, returning.", new Object[0]);
      return;
    }
    GetTocHelper.getToc(localDfeApi, false, new GetTocHelper.Listener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        FinskyLog.e("Error fetching TOC: %s", new Object[] { paramAnonymousVolleyError });
      }
      
      public final void onResponse(Toc.TocResponse paramAnonymousTocResponse)
      {
        DfeToc localDfeToc = new DfeToc(paramAnonymousTocResponse);
        FinskyApp.get().setToc(localDfeToc);
        TosAckedReceiver.access$000(TosAckedReceiver.this, str, bool, localDfeToc);
      }
    });
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.receivers.TosAckedReceiver
 * JD-Core Version:    0.7.0.1
 */