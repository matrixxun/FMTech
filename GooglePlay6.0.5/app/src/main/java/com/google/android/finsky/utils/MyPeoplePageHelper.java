package com.google.android.finsky.utils;

import com.google.android.finsky.api.DfeApi;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class MyPeoplePageHelper
{
  private static long sLastMutationTimeMs;
  private static List<String> sListUrls = new ArrayList();
  
  public static void addPeoplePageListUrls(List<String> paramList)
  {
    sListUrls.addAll(paramList);
  }
  
  public static boolean hasMutationOccurredSince(long paramLong)
  {
    return paramLong < sLastMutationTimeMs;
  }
  
  public static void onMutationOccurred(DfeApi paramDfeApi)
  {
    sLastMutationTimeMs = System.currentTimeMillis();
    Iterator localIterator = sListUrls.iterator();
    while (localIterator.hasNext()) {
      paramDfeApi.invalidateListCache$505cbf4b((String)localIterator.next());
    }
    sListUrls.clear();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.MyPeoplePageHelper
 * JD-Core Version:    0.7.0.1
 */