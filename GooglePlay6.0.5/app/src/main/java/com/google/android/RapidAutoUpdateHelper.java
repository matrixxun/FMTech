package com.google.android;

import android.text.TextUtils;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.config.G;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.TosUtil;
import com.google.android.play.utils.config.GservicesValue;
import java.util.ArrayList;
import java.util.List;

public final class RapidAutoUpdateHelper
{
  private static ArrayList<AutoUpdateData> sAutoUpdateDataList;
  
  public static void extractRapidAutoUpdateApplications(List<Document> paramList1, AppStates paramAppStates, List<Document> paramList2, List<Document> paramList3)
  {
    ArrayList localArrayList = getAutoUpdateDataList();
    int i = TosUtil.getMaxAcceptedTosVersion();
    int j = -1 + paramList1.size();
    if (j >= 0)
    {
      String str = ((Document)paramList1.get(j)).getAppDetails().packageName;
      int k = 0;
      int m = localArrayList.size();
      if (k < m)
      {
        AutoUpdateData localAutoUpdateData = (AutoUpdateData)localArrayList.get(k);
        int n;
        if ((localAutoUpdateData.packageName.equals(str)) && ((0x4 & localAutoUpdateData.flags) != 0))
        {
          n = paramAppStates.mPackageManager.getVersionCode(str);
          if (n >= 0) {
            break label134;
          }
          FinskyLog.w("Server thinks we have an asset that we don't have : %s", new Object[] { str });
        }
        label134:
        while (("com.android.chrome".equals(localAutoUpdateData.packageName)) && (i < ((Integer)G.rapidAutoUpdateTosMinVersion.get()).intValue()))
        {
          k++;
          break;
        }
        if (localAutoUpdateData.mask > 0) {
          n %= localAutoUpdateData.mask;
        }
        if (n < localAutoUpdateData.minVersionCode)
        {
          if ((0x1 & localAutoUpdateData.flags) == 0) {
            break label223;
          }
          paramList3.add(paramList1.remove(j));
        }
      }
      for (;;)
      {
        j--;
        break;
        label223:
        paramList2.add(paramList1.remove(j));
      }
    }
  }
  
  public static AutoUpdateData getAutoUpdateData(String paramString)
  {
    AutoUpdateData localAutoUpdateData;
    if (TextUtils.isEmpty(paramString))
    {
      localAutoUpdateData = null;
      return localAutoUpdateData;
    }
    ArrayList localArrayList = getAutoUpdateDataList();
    int i = 0;
    int j = localArrayList.size();
    for (;;)
    {
      if (i >= j) {
        break label57;
      }
      localAutoUpdateData = (AutoUpdateData)localArrayList.get(i);
      if (localAutoUpdateData.packageName.equals(paramString)) {
        break;
      }
      i++;
    }
    label57:
    return null;
  }
  
  private static ArrayList<AutoUpdateData> getAutoUpdateDataList()
  {
    if (sAutoUpdateDataList != null) {
      return sAutoUpdateDataList;
    }
    String str = (String)G.rapidAutoUpdateListing.get();
    sAutoUpdateDataList = new ArrayList();
    String[] arrayOfString1 = str.split(";");
    int i = 0;
    int j = arrayOfString1.length;
    if (i < j)
    {
      String[] arrayOfString2 = arrayOfString1[i].split(",");
      if (arrayOfString2.length != 4)
      {
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = Integer.valueOf(arrayOfString2.length);
        arrayOfObject2[1] = arrayOfString1[i];
        FinskyLog.w("Invalid number of items for the Rapid Auto Update data (Expecting 4, got %d). Data: %s", arrayOfObject2);
      }
      for (;;)
      {
        i++;
        break;
        AutoUpdateData localAutoUpdateData = new AutoUpdateData((byte)0);
        try
        {
          localAutoUpdateData.packageName = arrayOfString2[0];
          localAutoUpdateData.minVersionCode = Integer.parseInt(arrayOfString2[1]);
          localAutoUpdateData.mask = Integer.parseInt(arrayOfString2[2]);
          localAutoUpdateData.flags = Integer.parseInt(arrayOfString2[3]);
          sAutoUpdateDataList.add(localAutoUpdateData);
        }
        catch (NumberFormatException localNumberFormatException)
        {
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = arrayOfString1[i];
          FinskyLog.w("Invalid format for the Rapid Auto Update data: %s", arrayOfObject1);
        }
      }
    }
    return sAutoUpdateDataList;
  }
  
  public static boolean isParticipating(String paramString)
  {
    return getAutoUpdateData(paramString) != null;
  }
  
  private static final class AutoUpdateData
  {
    public int flags;
    int mask;
    int minVersionCode;
    String packageName;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.RapidAutoUpdateHelper
 * JD-Core Version:    0.7.0.1
 */