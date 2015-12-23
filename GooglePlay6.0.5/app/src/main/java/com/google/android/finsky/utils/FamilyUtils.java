package com.google.android.finsky.utils;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.config.G;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.protos.DocDetails.DocumentDetails;
import com.google.android.finsky.protos.DocDetails.PersonDetails;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Family;
import com.google.android.finsky.protos.FamilyInfo;
import com.google.android.finsky.protos.FamilyMember;
import com.google.android.finsky.protos.UserSettings;
import com.google.android.finsky.protos.UserSettingsConsistencyTokens.ConsistencyTokenInfo;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.play.utils.config.GservicesValue;

public final class FamilyUtils
{
  public static FamilyMember findSelfInFamily(Family paramFamily)
  {
    if ((paramFamily != null) && (paramFamily.member != null)) {
      for (FamilyMember localFamilyMember : paramFamily.member) {
        if ((localFamilyMember.personDocument != null) && (localFamilyMember.personDocument.details != null) && (localFamilyMember.personDocument.details.personDetails != null) && (localFamilyMember.personDocument.details.personDetails.personIsRequester)) {
          return localFamilyMember;
        }
      }
    }
    return null;
  }
  
  public static FamilyInfo getCachedFamilyInfo(String paramString)
  {
    UserSettings localUserSettings = UserSettingsCache.getCachedUserSettings(paramString);
    if (localUserSettings != null) {
      return localUserSettings.familyInfo;
    }
    return null;
  }
  
  public static void saveConsistencyToken(String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString1)) {
      throw new IllegalArgumentException("accountName should never be null");
    }
    if (TextUtils.isEmpty(paramString2)) {
      return;
    }
    UserSettingsConsistencyTokens.ConsistencyTokenInfo localConsistencyTokenInfo = new UserSettingsConsistencyTokens.ConsistencyTokenInfo();
    localConsistencyTokenInfo.requestHeader = "X-DFE-Family-Consistency-Token";
    localConsistencyTokenInfo.hasRequestHeader = true;
    localConsistencyTokenInfo.consistencyToken = paramString2;
    localConsistencyTokenInfo.hasConsistencyToken = true;
    UserSettingsCache.updateConsistencyToken(paramString1, localConsistencyTokenInfo);
  }
  
  public static boolean shouldShowFamilyCard(Context paramContext, String paramString)
  {
    if (!FinskyApp.get().getExperiments(paramString).isEnabled(12603428L))
    {
      FinskyLog.d("Family experiment not enabled; cannot show family card.", new Object[0]);
      return false;
    }
    GoogleApiAvailability.getInstance();
    if (GoogleApiAvailability.getApkVersion(paramContext) <= ((Integer)G.minGmsCoreVersionForFamily.get()).intValue())
    {
      FinskyLog.e("GmsCore too old; cannot show family card.", new Object[0]);
      return false;
    }
    FamilyInfo localFamilyInfo = getCachedFamilyInfo(paramString);
    if (localFamilyInfo == null)
    {
      FinskyLog.d("Family card: user not in family or user setting not available", new Object[0]);
      return false;
    }
    FamilyMember localFamilyMember = findSelfInFamily(localFamilyInfo.family);
    int i;
    if ((FinskyApp.get().getExperiments(paramString).isEnabled(12603772L)) && (localFamilyInfo.familyMembershipStatus == 3))
    {
      i = 1;
      if ((localFamilyMember == null) || (localFamilyInfo.familyMembershipStatus == 4)) {
        break label151;
      }
    }
    label151:
    for (int j = 1;; j = 0)
    {
      if ((i == 0) && (j == 0)) {
        break label157;
      }
      return true;
      i = 0;
      break;
    }
    label157:
    return false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.FamilyUtils
 * JD-Core Version:    0.7.0.1
 */