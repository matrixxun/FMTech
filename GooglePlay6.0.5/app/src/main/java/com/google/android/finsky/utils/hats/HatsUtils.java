package com.google.android.finsky.utils.hats;

import android.support.v4.util.LongSparseArray;
import android.text.TextUtils;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.EventProtoCache;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreLogEvent;
import com.google.android.finsky.analytics.PlayStore.PlayStoreSurveyEvent;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.protos.DismissSurveyResponse;
import com.google.android.finsky.protos.Survey;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import java.util.Iterator;
import java.util.List;

public final class HatsUtils
{
  public static void dismissSurvey(long paramLong, int paramInt)
  {
    DfeApi localDfeApi = FinskyApp.get().getDfeApi(null);
    SurveyStore localSurveyStore = FinskyApp.get().getClientMutationCache(FinskyApp.get().getCurrentAccountName()).getSurveyStore();
    localSurveyStore.mDismissedSurveyList.append(paramLong, null);
    localSurveyStore.updateDismissedSurveysPreference();
    if (localDfeApi != null) {
      localDfeApi.dismissSurvey(paramLong, paramInt, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
      {
        public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
        {
          FinskyLog.w("Volley error received: %s", new Object[] { paramAnonymousVolleyError });
        }
      });
    }
  }
  
  public static boolean isSurveyEligible(String paramString)
  {
    PreferenceFile.SharedPreference localSharedPreference = FinskyPreferences.lastSurveyActionMs.get(paramString);
    if (localSharedPreference == null) {}
    while (!localSharedPreference.exists()) {
      return true;
    }
    return false;
  }
  
  public static void logSurveyEvent(int paramInt1, long paramLong, int paramInt2, int paramInt3)
  {
    PlayStore.PlayStoreSurveyEvent localPlayStoreSurveyEvent = new PlayStore.PlayStoreSurveyEvent();
    localPlayStoreSurveyEvent.type = paramInt1;
    localPlayStoreSurveyEvent.hasType = true;
    localPlayStoreSurveyEvent.surveyId = paramLong;
    boolean bool1;
    boolean bool2;
    if (paramLong != -1L)
    {
      bool1 = true;
      localPlayStoreSurveyEvent.hasSurveyId = bool1;
      localPlayStoreSurveyEvent.answerId = paramInt2;
      if (paramInt2 == -1) {
        break label231;
      }
      bool2 = true;
      label59:
      localPlayStoreSurveyEvent.hasAnswerId = bool2;
      localPlayStoreSurveyEvent.surveyContext = paramInt3;
      if (paramInt3 == -1) {
        break label237;
      }
    }
    label231:
    label237:
    for (boolean bool3 = true;; bool3 = false)
    {
      localPlayStoreSurveyEvent.hasSurveyContext = bool3;
      FinskyEventLog localFinskyEventLog = FinskyApp.get().getEventLogger();
      if ((FinskyEventLog.shouldSendEventToLogcat()) && (FinskyEventLog.shouldSendEventToLogcat()))
      {
        StringBuilder localStringBuilder = new StringBuilder("Sending survey event");
        localStringBuilder.append(" type=").append(localPlayStoreSurveyEvent.type);
        localStringBuilder.append(" survey_id=").append(localPlayStoreSurveyEvent.surveyId);
        localStringBuilder.append(" answer_id=").append(localPlayStoreSurveyEvent.answerId);
        localStringBuilder.append(" survey_context=").append(localPlayStoreSurveyEvent.surveyContext);
        FinskyLog.d("%s", new Object[] { localStringBuilder });
      }
      PlayStore.PlayStoreLogEvent localPlayStoreLogEvent = localFinskyEventLog.mProtoCache.obtainPlayStoreLogEvent();
      localPlayStoreLogEvent.survey = localPlayStoreSurveyEvent;
      localFinskyEventLog.serializeAndWrite("7", localPlayStoreLogEvent);
      return;
      bool1 = false;
      break;
      bool2 = false;
      break label59;
    }
  }
  
  public static void showSurveyIfAvailable(int paramInt, PageFragmentHost paramPageFragmentHost)
  {
    FinskyApp localFinskyApp = FinskyApp.get();
    String str = localFinskyApp.getCurrentAccountName();
    if (TextUtils.isEmpty(str)) {
      return;
    }
    SurveyStore localSurveyStore = localFinskyApp.getClientMutationCache(str).getSurveyStore();
    Survey localSurvey2;
    if (localSurveyStore.mSurveyList != null)
    {
      Iterator localIterator = localSurveyStore.mSurveyList.iterator();
      do
      {
        if (!localIterator.hasNext()) {
          break;
        }
        localSurvey2 = (Survey)localIterator.next();
      } while ((localSurvey2.context != paramInt) || (localSurveyStore.mDismissedSurveyList.indexOfKey(localSurvey2.id) >= 0));
    }
    for (Survey localSurvey1 = localSurvey2; localSurvey1 != null; localSurvey1 = null)
    {
      paramPageFragmentHost.maybeShowSatisfactionSurveyV2(localSurvey1);
      return;
    }
    paramPageFragmentHost.hideSatisfactionSurveyV2();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.hats.HatsUtils
 * JD-Core Version:    0.7.0.1
 */