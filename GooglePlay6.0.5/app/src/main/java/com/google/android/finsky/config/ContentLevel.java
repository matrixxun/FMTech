package com.google.android.finsky.config;

import android.accounts.Account;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.finsky.api.AccountHandler;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.utils.config.GservicesValue;

public enum ContentLevel
{
  private static final ContentLevel DEFAULT_VALUE = convertFromLegacyValue(((Integer)G.vendingContentRating.get()).intValue());
  public final int mValue;
  
  static
  {
    HIGH_MATURITY = new ContentLevel("HIGH_MATURITY", 3, 3);
    SHOW_ALL = new ContentLevel("SHOW_ALL", 4, 4);
    ContentLevel[] arrayOfContentLevel = new ContentLevel[5];
    arrayOfContentLevel[0] = EVERYONE;
    arrayOfContentLevel[1] = LOW_MATURITY;
    arrayOfContentLevel[2] = MEDIUM_MATURITY;
    arrayOfContentLevel[3] = HIGH_MATURITY;
    arrayOfContentLevel[4] = SHOW_ALL;
    $VALUES = arrayOfContentLevel;
  }
  
  private ContentLevel(int paramInt)
  {
    this.mValue = paramInt;
  }
  
  public static ContentLevel convertFromLegacyValue(int paramInt)
  {
    ContentLevel localContentLevel;
    if (paramInt == -1)
    {
      localContentLevel = HIGH_MATURITY;
      return localContentLevel;
    }
    ContentLevel[] arrayOfContentLevel = values();
    int i = arrayOfContentLevel.length;
    for (int j = 0;; j++)
    {
      if (j >= i) {
        break label47;
      }
      localContentLevel = arrayOfContentLevel[j];
      if (localContentLevel.mValue == paramInt) {
        break;
      }
    }
    label47:
    return HIGH_MATURITY;
  }
  
  public static ContentLevel importFromSettings(Context paramContext)
  {
    int i = ((Integer)FinskyPreferences.contentFilterLevel.get()).intValue();
    int i3;
    if (i == -1)
    {
      SharedPreferences localSharedPreferences = FinskyPreferences.getPreferencesFile().open();
      int m = HIGH_MATURITY.getDfeValue();
      Account[] arrayOfAccount = AccountHandler.getAccounts(paramContext);
      int n = -1 + arrayOfAccount.length;
      int i1 = m;
      int i2 = 0;
      while (n >= 0)
      {
        String str = arrayOfAccount[n].name;
        int i7 = localSharedPreferences.getInt(Utils.getPreferenceKey(FinskyPreferences.contentFilterLevel.mKey, str), -1);
        if (i7 >= 0)
        {
          i2 = 1;
          i1 = Math.min(i1, i7);
        }
        n--;
      }
      if (i2 == 0) {
        break label180;
      }
      i3 = i1;
      if (i3 != -1)
      {
        FinskyPreferences.contentFilterLevel.put(Integer.valueOf(i3));
        i = i3;
      }
    }
    if (i >= 0)
    {
      ContentLevel[] arrayOfContentLevel1 = values();
      int j = arrayOfContentLevel1.length;
      for (int k = 0;; k++)
      {
        if (k >= j) {
          break label286;
        }
        ContentLevel localContentLevel1 = arrayOfContentLevel1[k];
        if (localContentLevel1.mValue == i)
        {
          return localContentLevel1;
          label180:
          PreferenceFile.SharedPreference localSharedPreference = new PreferenceFile("vending_preferences").value("content_rating", Integer.valueOf(-1));
          if (localSharedPreference.exists())
          {
            int i4 = ((Integer)localSharedPreference.get()).intValue();
            ContentLevel[] arrayOfContentLevel2 = values();
            int i5 = arrayOfContentLevel2.length;
            for (int i6 = 0;; i6++)
            {
              if (i6 >= i5) {
                break label274;
              }
              ContentLevel localContentLevel2 = arrayOfContentLevel2[i6];
              if (localContentLevel2.mValue == i4)
              {
                i3 = localContentLevel2.mValue;
                break;
              }
            }
          }
          label274:
          i3 = -1;
          break;
        }
      }
    }
    label286:
    return DEFAULT_VALUE;
  }
  
  public final int getDfeValue()
  {
    if (this.mValue == SHOW_ALL.mValue) {
      return HIGH_MATURITY.mValue;
    }
    return this.mValue;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.config.ContentLevel
 * JD-Core Version:    0.7.0.1
 */