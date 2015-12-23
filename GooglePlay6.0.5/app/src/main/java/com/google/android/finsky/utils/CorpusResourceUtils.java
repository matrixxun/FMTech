package com.google.android.finsky.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.text.TextUtils;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.Toc.CorpusMetadata;
import com.google.android.finsky.protos.Toc.TocResponse;
import java.util.Iterator;
import java.util.List;

public final class CorpusResourceUtils
{
  public static int getColoredBackendDrawable(int paramInt)
  {
    switch (paramInt)
    {
    case 5: 
    case 7: 
    case 8: 
    case 9: 
    case 10: 
    case 11: 
    case 12: 
    default: 
      return -1;
    case 3: 
      if (isEnterpriseTheme()) {
        return 2130837715;
      }
      return 2130837716;
    case 1: 
      return 2130837718;
    case 6: 
      return 2130837742;
    case 4: 
      return 2130837725;
    case 2: 
      return 2130837727;
    }
    return 2130837720;
  }
  
  public static String getCorpusMyCollectionDescription(int paramInt)
  {
    if (paramInt == 0) {
      paramInt = 3;
    }
    DfeToc localDfeToc = FinskyApp.get().mToc;
    if (localDfeToc == null) {}
    Toc.CorpusMetadata localCorpusMetadata;
    do
    {
      Iterator localIterator;
      while (!localIterator.hasNext())
      {
        return null;
        localIterator = localDfeToc.getCorpusList().iterator();
      }
      localCorpusMetadata = (Toc.CorpusMetadata)localIterator.next();
    } while ((!localCorpusMetadata.hasBackend) || (localCorpusMetadata.backend != paramInt) || (TextUtils.isEmpty(localCorpusMetadata.libraryName)));
    return localCorpusMetadata.libraryName;
  }
  
  public static int getCorpusSpinnerDrawable(int paramInt)
  {
    switch (paramInt)
    {
    case 3: 
    case 5: 
    default: 
      return 2130838113;
    case 1: 
      return 2130838114;
    case 6: 
      return 2130838117;
    case 4: 
      return 2130838115;
    }
    return 2130838116;
  }
  
  public static int getDrawerShopDrawable(int paramInt)
  {
    switch (paramInt)
    {
    case 5: 
    case 7: 
    case 8: 
    case 9: 
    case 10: 
    case 11: 
    case 12: 
    default: 
      return -1;
    case 3: 
      return 2130837714;
    case 1: 
      return 2130837717;
    case 6: 
      return 2130837741;
    case 4: 
      return 2130837724;
    case 2: 
      return 2130837726;
    }
    return 2130837719;
  }
  
  public static String getEntertainmentOnboardingString(Context paramContext, DfeToc paramDfeToc)
  {
    List localList = paramDfeToc.getCorpusList();
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    int i1 = 0;
    if (i1 < localList.size())
    {
      Toc.CorpusMetadata localCorpusMetadata = (Toc.CorpusMetadata)localList.get(i1);
      if (localCorpusMetadata.hasBackend) {
        switch (localCorpusMetadata.backend)
        {
        }
      }
      for (;;)
      {
        i1++;
        break;
        m = 1;
        n++;
        continue;
        k = 1;
        n++;
        continue;
        j = 1;
        n++;
        continue;
        i = 1;
        n++;
      }
    }
    Resources localResources = paramContext.getResources();
    if (n < 2) {}
    do
    {
      do
      {
        do
        {
          return null;
          if (n != 2) {
            break;
          }
          if ((m != 0) && (k != 0)) {
            return localResources.getString(2131362427);
          }
          if ((m != 0) && (j != 0)) {
            return localResources.getString(2131362424);
          }
          if ((m != 0) && (i != 0)) {
            return localResources.getString(2131362426);
          }
          if ((k != 0) && (j != 0)) {
            return localResources.getString(2131362431);
          }
          if ((k != 0) && (i != 0)) {
            return localResources.getString(2131362433);
          }
        } while ((j == 0) || (i == 0));
        return localResources.getString(2131362423);
        if (n != 3) {
          break;
        }
        if ((m != 0) && (k != 0) && (j != 0)) {
          return localResources.getString(2131362428);
        }
        if ((m != 0) && (k != 0) && (i != 0)) {
          return localResources.getString(2131362430);
        }
        if ((m != 0) && (j != 0) && (i != 0)) {
          return localResources.getString(2131362425);
        }
      } while ((k == 0) || (j == 0) || (i == 0));
      return localResources.getString(2131362432);
    } while ((m == 0) || (k == 0) || (j == 0) || (i == 0));
    return localResources.getString(2131362429);
  }
  
  public static String getItemThumbnailContentDescription(Document paramDocument, Resources paramResources)
  {
    String str = paramDocument.mDocument.title;
    if (TextUtils.isEmpty(str)) {
      return null;
    }
    switch (paramDocument.mDocument.docType)
    {
    default: 
      return null;
    case 1: 
      return paramResources.getString(2131362015, new Object[] { str });
    case 8: 
      return paramResources.getString(2131362019, new Object[] { str });
    case 5: 
      return paramResources.getString(2131362017, new Object[] { str });
    case 44: 
      return paramResources.getString(2131362018, new Object[] { str });
    case 2: 
    case 4: 
      return paramResources.getString(2131362014, new Object[] { str });
    case 3: 
      return paramResources.getString(2131362016, new Object[] { str });
    case 16: 
    case 17: 
    case 24: 
    case 25: 
      return paramResources.getString(2131362021, new Object[] { str });
    case 6: 
      return paramResources.getString(2131362020, new Object[] { str });
    }
    return paramResources.getString(2131362022, new Object[] { str });
  }
  
  public static int getPrimaryColor(Context paramContext, int paramInt)
  {
    return paramContext.getResources().getColor(getPrimaryColorResId(paramInt));
  }
  
  public static int getPrimaryColorResId(int paramInt)
  {
    switch (paramInt)
    {
    case 5: 
    default: 
      if (!isEnterpriseTheme()) {
        break;
      }
    case 3: 
      do
      {
        return 2131689590;
      } while (isEnterpriseTheme());
      return 2131689595;
    case 1: 
      return 2131689602;
    case 6: 
      return 2131689654;
    case 4: 
      return 2131689639;
    case 2: 
      return 2131689649;
    }
    return 2131689644;
  }
  
  public static String getRateString(Resources paramResources, int paramInt)
  {
    switch (paramInt)
    {
    default: 
      throw new IllegalArgumentException("Unsupported doc type (" + paramInt + ")");
    case 1: 
      return paramResources.getString(2131362622);
    case 5: 
      return paramResources.getString(2131362623);
    case 44: 
      return paramResources.getString(2131362624);
    case 2: 
      return paramResources.getString(2131362626);
    case 4: 
      return paramResources.getString(2131362627);
    case 16: 
    case 17: 
    case 24: 
    case 25: 
      return paramResources.getString(2131362628);
    case 6: 
      return paramResources.getString(2131362625);
    case 18: 
      return paramResources.getString(2131362631);
    case 19: 
      return paramResources.getString(2131362630);
    }
    return paramResources.getString(2131362629);
  }
  
  public static int getRegularDetailsIconHeight(Context paramContext, int paramInt)
  {
    Resources localResources = paramContext.getResources();
    switch (paramInt)
    {
    default: 
      throw new IllegalArgumentException("Unsupported document type (" + paramInt + ")");
    case 2: 
    case 4: 
    case 5: 
    case 6: 
    case 16: 
    case 17: 
    case 24: 
    case 25: 
      return localResources.getDimensionPixelSize(2131492968);
    case 44: 
      return localResources.getDimensionPixelSize(2131492969);
    case 1: 
      if (FinskyApp.get().getExperiments().isEnabled(12602392L)) {
        return localResources.getDimensionPixelSize(2131492897);
      }
      return localResources.getDimensionPixelSize(2131492896);
    case 8: 
    case 30: 
      return localResources.getDimensionPixelSize(2131492936);
    }
    return localResources.getDimensionPixelSize(2131492969) / 2;
  }
  
  public static int getRegularDetailsIconWidth(Context paramContext, int paramInt)
  {
    Resources localResources = paramContext.getResources();
    switch (paramInt)
    {
    default: 
      throw new IllegalArgumentException("Unsupported document type (" + paramInt + ")");
    case 1: 
      if (FinskyApp.get().getExperiments().isEnabled(12602392L)) {
        return localResources.getDimensionPixelSize(2131492897);
      }
      return localResources.getDimensionPixelSize(2131492896);
    case 8: 
    case 30: 
      return localResources.getDimensionPixelSize(2131492936);
    case 2: 
    case 4: 
    case 24: 
    case 25: 
      return localResources.getDimensionPixelSize(2131492968);
    }
    return localResources.getDimensionPixelSize(2131492969);
  }
  
  public static int getRewardDrawable(int paramInt)
  {
    switch (paramInt)
    {
    case 5: 
    default: 
      if (!isEnterpriseTheme()) {
        break;
      }
    case 3: 
      do
      {
        return 2130837873;
      } while (isEnterpriseTheme());
      return 2130837872;
    case 1: 
      return 2130837874;
    case 6: 
      return 2130837877;
    case 4: 
      return 2130837875;
    case 2: 
      return 2130837876;
    }
    return 2130837878;
  }
  
  public static int getSecondaryColorResId(int paramInt)
  {
    switch (paramInt)
    {
    case 5: 
    case 7: 
    case 8: 
    case 9: 
    case 10: 
    case 11: 
    case 12: 
    default: 
      if (!isEnterpriseTheme()) {
        break;
      }
    case 3: 
      do
      {
        return 2131689593;
      } while (isEnterpriseTheme());
      return 2131689598;
    case 1: 
      return 2131689605;
    case 6: 
      return 2131689657;
    case 4: 
      return 2131689642;
    case 2: 
      return 2131689652;
    case 0: 
    case 13: 
      return 2131689647;
    }
    return 2131689647;
  }
  
  public static ColorStateList getSecondaryTextColor(Context paramContext, int paramInt)
  {
    int i = 2131689772;
    switch (paramInt)
    {
    case 5: 
    default: 
      if (!isEnterpriseTheme()) {
        break;
      }
    }
    for (;;)
    {
      return paramContext.getResources().getColorStateList(i);
      if (!isEnterpriseTheme())
      {
        for (;;)
        {
          i = 2131689775;
        }
        i = 2131689778;
        continue;
        i = 2131689793;
        continue;
        i = 2131689784;
        continue;
        i = 2131689790;
        continue;
        i = 2131689787;
      }
    }
  }
  
  public static int getStatusBarColor(Context paramContext, int paramInt)
  {
    int i = 2131689724;
    switch (paramInt)
    {
    case 5: 
    default: 
      if (!isEnterpriseTheme()) {
        break;
      }
    }
    for (;;)
    {
      return paramContext.getResources().getColor(i);
      if (!isEnterpriseTheme())
      {
        for (;;)
        {
          i = 2131689723;
        }
        i = 2131689725;
        continue;
        i = 2131689729;
        continue;
        i = 2131689726;
        continue;
        i = 2131689728;
        continue;
        i = 2131689727;
      }
    }
  }
  
  public static int getTitleContentDescriptionResourceId(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return -1;
    case 1: 
      return 2131361970;
    case 8: 
      return 2131361982;
    case 5: 
      return 2131361972;
    case 44: 
      return 2131361971;
    case 2: 
      return 2131361993;
    case 4: 
      return 2131361995;
    case 3: 
      return 2131361994;
    case 16: 
    case 17: 
    case 24: 
    case 25: 
      return 2131362002;
    case 6: 
      return 2131361992;
    case 18: 
      return 2131362034;
    case 19: 
      return 2131362033;
    case 20: 
      return 2131362032;
    }
    return 2131362000;
  }
  
  public static int getWarningDrawable(int paramInt)
  {
    switch (paramInt)
    {
    case 5: 
    default: 
      throw new IllegalArgumentException("Unsupported backend ID (" + paramInt + ")");
    case 1: 
      return 2130837896;
    case 2: 
      return 2130837898;
    case 4: 
      return 2130837897;
    case 6: 
      return 2130837899;
    }
    if (isEnterpriseTheme()) {
      return 2130837895;
    }
    return 2130837894;
  }
  
  public static int getWishlistOnDrawable(int paramInt)
  {
    switch (paramInt)
    {
    case 5: 
    default: 
      throw new IllegalArgumentException("Unsupported backend ID (" + paramInt + ")");
    case 1: 
      return 2130837816;
    case 2: 
      return 2130837820;
    case 4: 
      return 2130837818;
    case 6: 
      return 2130837822;
    }
    if (isEnterpriseTheme()) {
      return 2130837814;
    }
    return 2130837813;
  }
  
  public static boolean isEnterpriseTheme()
  {
    DfeToc localDfeToc = FinskyApp.get().mToc;
    return (localDfeToc != null) && (localDfeToc.mToc.themeId == 1);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.CorpusResourceUtils
 * JD-Core Version:    0.7.0.1
 */