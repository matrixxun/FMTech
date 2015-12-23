package com.google.android.finsky.widget.consumption;

import android.util.SparseIntArray;
import com.google.android.finsky.config.PreferenceFile.SharedPreference;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.FinskyPreferences;
import com.google.android.finsky.widget.WidgetUtils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class NowPlayingArranger
{
  private static final int[][][] PERMUTATIONS = { new int[0][], { { 0 } }, { { 0, 1 }, { 1, 0 } }, { { 0, 1, 2 }, { 0, 2, 1 }, { 1, 0, 2 }, { 2, 0, 1 } }, { { 0, 1, 2, 3 }, { 0, 1, 3, 2 }, { 0, 2, 1, 3 }, { 0, 2, 3, 1 }, { 0, 3, 1, 2 }, { 0, 3, 2, 1 }, { 1, 0, 3, 2 }, { 1, 0, 2, 3 }, { 1, 2, 3, 0 }, { 1, 2, 0, 3 }, { 1, 3, 2, 0 }, { 1, 3, 0, 2 }, { 2, 0, 1, 3 }, { 2, 0, 3, 1 }, { 2, 1, 0, 3 }, { 2, 1, 3, 0 }, { 2, 3, 0, 1 }, { 2, 3, 1, 0 }, { 3, 0, 2, 1 }, { 3, 0, 1, 2 }, { 3, 1, 2, 0 }, { 3, 1, 0, 2 }, { 3, 2, 1, 0 }, { 3, 2, 0, 1 } } };
  private static SparseIntArray sBottomAffinity;
  private static Map<String, int[]> sCachedArrangements = new HashMap();
  private static boolean sInitialized = false;
  private static SparseIntArray sLeftAffinity;
  private static SparseIntArray sRightAffinity;
  private static SparseIntArray sTopAffinity;
  
  public static Arrangement arrange$568a224(List<ConsumptionAppDocList> paramList)
  {
    initialize();
    int i = paramList.size();
    int[] arrayOfInt1 = new int[i];
    for (int j = 0; j < i; j++) {
      arrayOfInt1[j] = ((ConsumptionAppDocList)paramList.get(j)).mBackend;
    }
    if (i == 0) {
      return new Arrangement(new ConsumptionAppDocList[0], 0);
    }
    if (i == 1) {
      return new Arrangement((ConsumptionAppDocList[])paramList.toArray(new ConsumptionAppDocList[1]), 0);
    }
    int[] arrayOfInt2 = getCachedCandidate(arrayOfInt1, 0);
    int k;
    if (arrayOfInt2 != null) {
      k = 1;
    }
    int i2;
    int i4;
    int i7;
    while (k == 0)
    {
      if (FinskyLog.DEBUG) {
        FinskyLog.v("Arrangement cache miss, computing from scratch.", new Object[0]);
      }
      i2 = -1;
      int[] arrayOfInt3 = new int[i];
      arrayOfInt2 = new int[i];
      int[][] arrayOfInt = PERMUTATIONS[i];
      int i3 = arrayOfInt.length;
      i4 = 0;
      if (i4 < i3)
      {
        int[] arrayOfInt4 = arrayOfInt[i4];
        int i5 = 0;
        for (;;)
        {
          if (i5 < arrayOfInt4.length)
          {
            arrayOfInt3[i5] = arrayOfInt1[arrayOfInt4[i5]];
            i5++;
            continue;
            k = 0;
            break;
          }
        }
        int i6 = 0;
        if (i == 3) {
          i6 = determineLayoutVariant3(arrayOfInt1, arrayOfInt3);
        }
        i7 = 0;
        int i8 = 0;
        while (i8 < i)
        {
          int i10 = i7 + getScore(arrayOfInt3[i8], Arrangement.getLocation(i, i8, i6));
          i8++;
          i7 = i10;
        }
        if (FinskyLog.DEBUG)
        {
          Object[] arrayOfObject2 = new Object[2];
          arrayOfObject2[0] = Arrays.toString(arrayOfInt3);
          arrayOfObject2[1] = Integer.valueOf(i7);
          FinskyLog.v("Score for candidate %s: %d", arrayOfObject2);
        }
        if (i7 <= i2) {
          break label485;
        }
        System.arraycopy(arrayOfInt3, 0, arrayOfInt2, 0, i);
      }
    }
    label485:
    for (int i9 = i7;; i9 = i2)
    {
      i4++;
      i2 = i9;
      break;
      if (i == 3) {}
      ConsumptionAppDocList[] arrayOfConsumptionAppDocList;
      for (int m = determineLayoutVariant3(arrayOfInt1, arrayOfInt2);; m = 0)
      {
        if (k == 0) {
          putCachedCandidate(arrayOfInt1, 0, arrayOfInt2, m);
        }
        arrayOfConsumptionAppDocList = new ConsumptionAppDocList[i];
        for (int n = 0; n < arrayOfInt2.length; n++)
        {
          int i1 = arrayOfInt2[n];
          Iterator localIterator = paramList.iterator();
          while (localIterator.hasNext())
          {
            ConsumptionAppDocList localConsumptionAppDocList = (ConsumptionAppDocList)localIterator.next();
            if (localConsumptionAppDocList.mBackend == i1) {
              arrayOfConsumptionAppDocList[n] = localConsumptionAppDocList;
            }
          }
        }
      }
      if (FinskyLog.DEBUG)
      {
        Object[] arrayOfObject1 = new Object[2];
        arrayOfObject1[0] = Arrays.toString(arrayOfInt2);
        arrayOfObject1[1] = Integer.valueOf(m);
        FinskyLog.v("Widget arrangement: quadrants=%s, layoutVariant=%d", arrayOfObject1);
      }
      return new Arrangement(arrayOfConsumptionAppDocList, m);
    }
  }
  
  private static int determineLayoutVariant3(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    if (paramArrayOfInt1[0] == paramArrayOfInt2[0]) {
      return 0;
    }
    return 1;
  }
  
  private static String getCacheKey(int[] paramArrayOfInt, int paramInt)
  {
    return Arrays.toString(paramArrayOfInt) + "/" + paramInt;
  }
  
  private static int[] getCachedCandidate(int[] paramArrayOfInt, int paramInt)
  {
    try
    {
      int[] arrayOfInt = (int[])sCachedArrangements.get(getCacheKey(paramArrayOfInt, 0));
      return arrayOfInt;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  private static int getScore(int paramInt1, int paramInt2)
  {
    int i = paramInt2 & 0x4;
    int j = 0;
    if (i != 0) {}
    try
    {
      j = 0 + sTopAffinity.get(paramInt1, 0);
      if ((paramInt2 & 0x8) != 0) {
        j += sBottomAffinity.get(paramInt1, 0);
      }
      if ((paramInt2 & 0x1) != 0) {
        j += sLeftAffinity.get(paramInt1, 0);
      }
      if ((paramInt2 & 0x2) != 0)
      {
        int k = sRightAffinity.get(paramInt1, 0);
        j += k;
      }
      return j;
    }
    finally {}
  }
  
  private static void increaseAndDecrease(SparseIntArray paramSparseIntArray1, SparseIntArray paramSparseIntArray2, int paramInt)
  {
    paramSparseIntArray1.put(paramInt, Math.min(1 + paramSparseIntArray1.get(paramInt, 0), 10));
    paramSparseIntArray2.put(paramInt, Math.max(-1 + paramSparseIntArray2.get(paramInt, 0), 0));
  }
  
  private static void initialize()
  {
    try
    {
      if (!sInitialized)
      {
        sTopAffinity = WidgetUtils.parseSparseIntArray((String)FinskyPreferences.myLibraryWidgetTopAffinity.get());
        sBottomAffinity = WidgetUtils.parseSparseIntArray((String)FinskyPreferences.myLibraryWidgetBottomAffinity.get());
        sLeftAffinity = WidgetUtils.parseSparseIntArray((String)FinskyPreferences.myLibraryWidgetLeftAffinity.get());
        sRightAffinity = WidgetUtils.parseSparseIntArray((String)FinskyPreferences.myLibraryWidgetRightAffinity.get());
        sInitialized = true;
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  private static void putCachedCandidate(int[] paramArrayOfInt1, int paramInt1, int[] paramArrayOfInt2, int paramInt2)
  {
    try
    {
      sCachedArrangements.put(getCacheKey(paramArrayOfInt1, 0), paramArrayOfInt2);
      for (int i = 0; i < paramArrayOfInt2.length; i++) {
        updateAffinity(paramArrayOfInt2[i], Arrangement.getLocation(paramArrayOfInt1.length, i, paramInt2));
      }
      FinskyPreferences.myLibraryWidgetTopAffinity.put(WidgetUtils.serializeSparseIntArray(sTopAffinity));
      FinskyPreferences.myLibraryWidgetBottomAffinity.put(WidgetUtils.serializeSparseIntArray(sBottomAffinity));
      FinskyPreferences.myLibraryWidgetLeftAffinity.put(WidgetUtils.serializeSparseIntArray(sLeftAffinity));
      FinskyPreferences.myLibraryWidgetRightAffinity.put(WidgetUtils.serializeSparseIntArray(sRightAffinity));
      return;
    }
    finally {}
  }
  
  private static void updateAffinity(int paramInt1, int paramInt2)
  {
    if ((paramInt2 & 0x4) != 0) {}
    try
    {
      increaseAndDecrease(sTopAffinity, sBottomAffinity, paramInt1);
      if ((paramInt2 & 0x8) != 0) {
        increaseAndDecrease(sBottomAffinity, sTopAffinity, paramInt1);
      }
      if ((paramInt2 & 0x1) != 0) {
        increaseAndDecrease(sLeftAffinity, sRightAffinity, paramInt1);
      }
      if ((paramInt2 & 0x2) != 0) {
        increaseAndDecrease(sRightAffinity, sLeftAffinity, paramInt1);
      }
      return;
    }
    finally {}
  }
  
  public static final class Arrangement
  {
    private static final byte[] LOCATION_FLAGS_2_HORIZONTAL = { 1, 2 };
    private static final byte[] LOCATION_FLAGS_2_VERTICAL = { 4, 8 };
    private static final byte[] LOCATION_FLAGS_3_STRETCH_FIRST = { 1, 6, 10 };
    private static final byte[] LOCATION_FLAGS_3_STRETCH_SECOND = { 5, 2, 9 };
    private static final byte[] LOCATION_FLAGS_4 = { 5, 6, 9, 10 };
    public int layoutVariant;
    public final ConsumptionAppDocList[] quadrantToData;
    
    public Arrangement(ConsumptionAppDocList[] paramArrayOfConsumptionAppDocList, int paramInt)
    {
      this.quadrantToData = paramArrayOfConsumptionAppDocList;
      this.layoutVariant = paramInt;
    }
    
    public static byte getLocation(int paramInt1, int paramInt2, int paramInt3)
    {
      if (paramInt1 == 1) {}
      do
      {
        return 0;
        if ((paramInt1 == 2) && (paramInt3 == 0)) {
          return LOCATION_FLAGS_2_HORIZONTAL[paramInt2];
        }
        if ((paramInt1 == 2) && (paramInt3 == 1)) {
          return LOCATION_FLAGS_2_VERTICAL[paramInt2];
        }
        if ((paramInt1 == 3) && (paramInt3 == 0)) {
          return LOCATION_FLAGS_3_STRETCH_FIRST[paramInt2];
        }
        if ((paramInt1 == 3) && (paramInt3 == 1)) {
          return LOCATION_FLAGS_3_STRETCH_SECOND[paramInt2];
        }
      } while (paramInt1 != 4);
      return LOCATION_FLAGS_4[paramInt2];
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.consumption.NowPlayingArranger
 * JD-Core Version:    0.7.0.1
 */