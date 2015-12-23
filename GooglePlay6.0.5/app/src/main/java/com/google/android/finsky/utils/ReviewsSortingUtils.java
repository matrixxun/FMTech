package com.google.android.finsky.utils;

import android.content.Context;
import com.google.android.finsky.api.model.DfeReviews;

public final class ReviewsSortingUtils
{
  private static SortingInfo[] sSortingInfoArray;
  
  static
  {
    SortingInfo[] arrayOfSortingInfo = new SortingInfo[3];
    arrayOfSortingInfo[0] = new SortingInfo(4, 2131362706);
    arrayOfSortingInfo[1] = new SortingInfo(0, 2131362705);
    arrayOfSortingInfo[2] = new SortingInfo(1, 2131362707);
    sSortingInfoArray = arrayOfSortingInfo;
  }
  
  public static int convertDataSortTypeToDisplayIndex(DfeReviews paramDfeReviews)
  {
    int i = paramDfeReviews.mSortType;
    for (int j = 0; j < sSortingInfoArray.length; j++) {
      if (i == sSortingInfoArray[j].mProtocolSortType) {
        return j;
      }
    }
    return -1;
  }
  
  public static int convertDisplayIndexToDataSortType(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= sSortingInfoArray.length)) {
      return -1;
    }
    return sSortingInfoArray[paramInt].mProtocolSortType;
  }
  
  public static CharSequence[] getAllDisplayStrings(Context paramContext)
  {
    CharSequence[] arrayOfCharSequence = new CharSequence[sSortingInfoArray.length];
    for (int i = 0; i < sSortingInfoArray.length; i++) {
      arrayOfCharSequence[i] = paramContext.getString(sSortingInfoArray[i].mDisplayStringId);
    }
    return arrayOfCharSequence;
  }
  
  public static String getDisplayString(Context paramContext, int paramInt)
  {
    for (SortingInfo localSortingInfo : sSortingInfoArray) {
      if (paramInt == localSortingInfo.mProtocolSortType) {
        return paramContext.getString(localSortingInfo.mDisplayStringId);
      }
    }
    return null;
  }
  
  private static final class SortingInfo
  {
    public final int mDisplayStringId;
    public final int mProtocolSortType;
    
    public SortingInfo(int paramInt1, int paramInt2)
    {
      this.mProtocolSortType = paramInt1;
      this.mDisplayStringId = paramInt2;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.ReviewsSortingUtils
 * JD-Core Version:    0.7.0.1
 */