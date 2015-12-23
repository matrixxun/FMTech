package com.google.android.finsky.billing.switchfamilyinstrument;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.protos.FamilyFopResponse;
import com.google.android.finsky.utils.ErrorStrings;

public final class SwitchFamilyInstrumentSidecar
  extends SidecarFragment
  implements Response.ErrorListener, Response.Listener<FamilyFopResponse>
{
  DfeApi mDfeApi;
  String mErrorMessage;
  
  private void logResponse(int paramInt, Throwable paramThrowable)
  {
    BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(346);
    if (paramInt == 0) {
      localBackgroundEventBuilder.setOperationSuccess(true);
    }
    for (;;)
    {
      FinskyApp.get().getEventLogger(this.mDfeApi.getAccountName()).sendBackgroundEventToSinks(localBackgroundEventBuilder.event);
      return;
      localBackgroundEventBuilder.setOperationSuccess(false).setErrorCode(paramInt).setExceptionType(paramThrowable);
    }
  }
  
  protected static SwitchFamilyInstrumentSidecar newInstance(String paramString)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("authAccount", paramString);
    SwitchFamilyInstrumentSidecar localSwitchFamilyInstrumentSidecar = new SwitchFamilyInstrumentSidecar();
    localSwitchFamilyInstrumentSidecar.setArguments(localBundle);
    return localSwitchFamilyInstrumentSidecar;
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mDfeApi = FinskyApp.get().getDfeApi(this.mArguments.getString("authAccount"));
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    logResponse(1, paramVolleyError);
    this.mErrorMessage = ErrorStrings.get(FinskyApp.get(), paramVolleyError);
    setState(3, 0);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.switchfamilyinstrument.SwitchFamilyInstrumentSidecar
 * JD-Core Version:    0.7.0.1
 */