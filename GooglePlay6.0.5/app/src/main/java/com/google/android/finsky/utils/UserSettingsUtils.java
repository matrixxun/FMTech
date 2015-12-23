package com.google.android.finsky.utils;

import android.util.SparseArray;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.BackgroundEventBuilder;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.config.G;
import com.google.android.finsky.config.PreferenceFile.PrefixSharedPreference;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.layout.play.PlayCardClusterWithNoticeView.OnNoticeShownListener;
import com.google.android.finsky.protos.PrivacySetting;
import com.google.android.finsky.protos.UpdateUserSettingResponse;
import com.google.android.play.utils.config.GservicesValue;

public final class UserSettingsUtils
{
  private static SparseArray<Long> settingLastSeenNoticeTimes = new SparseArray();
  
  public static PlayCardClusterWithNoticeView.OnNoticeShownListener getNoticeListenerForSetting(int paramInt)
  {
    final long l = ((Long)G.clusterPrivacySettingNoticeWindowTimeMs.get()).longValue();
    final int i = ((Integer)G.maxPrivacySettingNoticeSeenCount.get()).intValue();
    new PlayCardClusterWithNoticeView.OnNoticeShownListener()
    {
      public final void onNoticeShown()
      {
        Long localLong = (Long)UserSettingsUtils.settingLastSeenNoticeTimes.get(this.val$settingType);
        long l = System.currentTimeMillis();
        if ((localLong == null) || (l - localLong.longValue() > l))
        {
          UserSettingsUtils.settingLastSeenNoticeTimes.put(this.val$settingType, Long.valueOf(l));
          int i = 1 + ((Integer)i.get()).intValue();
          i.put(Integer.valueOf(i));
          if (i >= this.val$maxSeenCount) {
            UserSettingsUtils.updateUserSetting$2f7b0d8d(this.val$settingType, 3, null);
          }
        }
      }
    };
  }
  
  public static PreferenceFile.SharedPreference<Integer> getPreferenceForUserSetting(int paramInt, String paramString)
  {
    switch (paramInt)
    {
    default: 
      FinskyLog.wtf("No shared preference for privacy setting " + paramInt, new Object[0]);
      return null;
    }
    return FinskyPreferences.locationSuggestionsEnabled.get(paramString);
  }
  
  public static String getPreferenceKeyForSetting(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return null;
    }
    return "location";
  }
  
  public static void updateUserSetting$2f7b0d8d(final int paramInt1, final int paramInt2, final Response.ErrorListener paramErrorListener)
  {
    final FinskyApp localFinskyApp = FinskyApp.get();
    PreferenceFile.SharedPreference localSharedPreference = getPreferenceForUserSetting(paramInt1, localFinskyApp.getCurrentAccountName());
    Integer localInteger1;
    if (localSharedPreference != null)
    {
      localInteger1 = (Integer)localSharedPreference.get();
      localSharedPreference.put(Integer.valueOf(paramInt2));
    }
    for (;;)
    {
      final Integer localInteger2 = localInteger1;
      DfeApi localDfeApi = localFinskyApp.getDfeApi(null);
      localDfeApi.updateUserPrivacySetting(paramInt1, paramInt2, UserSettingsCache.getConsistencyTokens(localFinskyApp.getCurrentAccountName()), new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
      {
        public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
        {
          if (this.val$finskyPreference != null) {
            this.val$finskyPreference.put(localInteger2);
          }
          Integer localInteger = UserSettingsUtils.access$000(paramInt1);
          if (localInteger != null)
          {
            BackgroundEventBuilder localBackgroundEventBuilder = new BackgroundEventBuilder(localInteger.intValue()).setToSetting(Integer.valueOf(paramInt2)).setFromSetting(localInteger2).setErrorCode(1).setExceptionType(paramAnonymousVolleyError.getClass().getSimpleName());
            localFinskyApp.getEventLogger().sendBackgroundEventToSinks(localBackgroundEventBuilder.event);
          }
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = Integer.valueOf(paramInt1);
          arrayOfObject[1] = paramAnonymousVolleyError;
          FinskyLog.e("Error updating user setting for type %d: %s", arrayOfObject);
          if (paramErrorListener != null) {
            paramErrorListener.onErrorResponse(paramAnonymousVolleyError);
          }
        }
      });
      return;
      DfeToc localDfeToc = localFinskyApp.mToc;
      localInteger1 = null;
      if (localDfeToc != null)
      {
        boolean bool = localFinskyApp.mToc.getUserPrivacySetting(paramInt1).hasCurrentStatus;
        localInteger1 = null;
        if (bool) {
          localInteger1 = Integer.valueOf(localFinskyApp.mToc.getUserPrivacySetting(paramInt1).currentStatus);
        }
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.UserSettingsUtils
 * JD-Core Version:    0.7.0.1
 */