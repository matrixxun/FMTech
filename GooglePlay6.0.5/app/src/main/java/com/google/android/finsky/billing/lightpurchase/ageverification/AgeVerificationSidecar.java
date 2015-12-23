package com.google.android.finsky.billing.lightpurchase.ageverification;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.fragments.SidecarFragment;
import com.google.android.finsky.protos.ChallengeProto.Challenge;
import com.google.android.finsky.protos.ChallengeResponse;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.FinskyLog;

public final class AgeVerificationSidecar
  extends SidecarFragment
  implements Response.ErrorListener, Response.Listener<ChallengeResponse>, OnDataChangedListener
{
  DfeApi mDfeApi;
  DfeDetails mDfeDetails;
  String mErrorHtml;
  private FinskyEventLog mEventLogger;
  ChallengeProto.Challenge mLastChallenge;
  
  private void logBackgroundEvent(int paramInt, String paramString)
  {
    BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(518).setExceptionType(paramString);
    if (paramInt != -1) {
      localBackgroundEventBuilder.setErrorCode(paramInt);
    }
    this.mEventLogger.sendBackgroundEventToSinks(localBackgroundEventBuilder.event);
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    this.mDfeApi = FinskyApp.get().getDfeApi(this.mArguments.getString("authAccount"));
    this.mEventLogger = FinskyApp.get().getEventLogger(this.mDfeApi.getAccount());
    super.onCreate(paramBundle);
  }
  
  public final void onDataChanged()
  {
    Document localDocument = this.mDfeDetails.getDocument();
    if (localDocument == null)
    {
      this.mErrorHtml = getString(2131362282);
      setState(3, 0);
      return;
    }
    if (localDocument.mDocument.mature)
    {
      setState(4, 0);
      return;
    }
    setState(7, 0);
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    FinskyLog.w("Volley error received: %s", new Object[] { paramVolleyError });
    logBackgroundEvent(1, paramVolleyError.getClass().getSimpleName());
    this.mErrorHtml = ErrorStrings.get(FinskyApp.get(), paramVolleyError);
    setState(3, 0);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.ageverification.AgeVerificationSidecar
 * JD-Core Version:    0.7.0.1
 */