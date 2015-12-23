package com.google.android.finsky.setup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.protos.Restore.BackupDocumentInfo;
import com.google.android.finsky.protos.Restore.GetBackupDocumentChoicesResponse;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.volley.DisplayMessageError;

public final class RestoreAppsSidecar
  extends SidecarFragment
{
  Restore.BackupDocumentInfo[] mBackupDocumentInfos;
  private DfeApi mDfeApi;
  
  public static RestoreAppsSidecar newInstance(String paramString)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("authAccount", paramString);
    RestoreAppsSidecar localRestoreAppsSidecar = new RestoreAppsSidecar();
    localRestoreAppsSidecar.setArguments(localBundle);
    return localRestoreAppsSidecar;
  }
  
  public final void fetchBackupDocs(long paramLong)
  {
    if (this.mState == 4)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(this.mState);
      FinskyLog.wtf("Making another load request while in loading state: %d", arrayOfObject);
      return;
    }
    setState(4, 0);
    this.mDfeApi.getBackupDocumentChoices(paramLong, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        boolean bool = paramAnonymousVolleyError instanceof DisplayMessageError;
        String str = null;
        if (bool) {
          str = ((DisplayMessageError)paramAnonymousVolleyError).mDisplayErrorHtml;
        }
        FinskyLog.e("Unable to fetch backup apps: %s (%s)", new Object[] { paramAnonymousVolleyError, str });
        RestoreAppsSidecar.access$002(RestoreAppsSidecar.this, null);
        RestoreAppsSidecar.access$200$5855e9dc(RestoreAppsSidecar.this);
      }
    });
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    String str = this.mArguments.getString("authAccount");
    this.mDfeApi = FinskyApp.get().getDfeApi(str);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.setup.RestoreAppsSidecar
 * JD-Core Version:    0.7.0.1
 */