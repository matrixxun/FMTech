package com.google.android.finsky.api;

import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.experiments.FinskyExperiments;

public final class RequestUtils
{
  private static float sLegacyDefaultTimeoutMultiplier = 1.0F;
  
  public static float getLegacyDefaultTimeoutMultiplier()
  {
    return sLegacyDefaultTimeoutMultiplier;
  }
  
  public static void updateLegacyDefaultTimeoutMultiplier()
  {
    FinskyExperiments localFinskyExperiments = FinskyApp.get().getExperiments();
    if (localFinskyExperiments.isEnabled(12604235L)) {
      sLegacyDefaultTimeoutMultiplier = 1.5F;
    }
    while (!localFinskyExperiments.isEnabled(12604236L)) {
      return;
    }
    sLegacyDefaultTimeoutMultiplier = 2.0F;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.RequestUtils
 * JD-Core Version:    0.7.0.1
 */