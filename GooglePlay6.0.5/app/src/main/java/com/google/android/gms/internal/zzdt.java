package com.google.android.gms.internal;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.overlay.AdLauncherIntentInfoParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zze;
import com.google.android.gms.ads.internal.zzp;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@zzhb
public final class zzdt
  implements zzdm
{
  private final zze zzzB;
  private final zzfn zzzC;
  private final zzdo zzzE;
  
  public zzdt(zzdo paramzzdo, zze paramzze, zzfn paramzzfn)
  {
    this.zzzE = paramzzdo;
    this.zzzB = paramzze;
    this.zzzC = paramzzfn;
  }
  
  private static boolean zzc(Map<String, String> paramMap)
  {
    return "1".equals(paramMap.get("custom_close"));
  }
  
  private static int zzd(Map<String, String> paramMap)
  {
    String str = (String)paramMap.get("o");
    if (str != null)
    {
      if ("p".equalsIgnoreCase(str)) {
        return zzp.zzbK().zzhm();
      }
      if ("l".equalsIgnoreCase(str)) {
        return zzp.zzbK().zzhl();
      }
      if ("c".equalsIgnoreCase(str)) {
        return zzp.zzbK().zzhn();
      }
    }
    return -1;
  }
  
  private void zzo(boolean paramBoolean)
  {
    if (this.zzzC != null) {
      this.zzzC.zzp(paramBoolean);
    }
  }
  
  public final void zza(zzjo paramzzjo, Map<String, String> paramMap)
  {
    String str1 = (String)paramMap.get("a");
    if (str1 == null) {
      zzb.w("Action missing from an open GMSG.");
    }
    zzjp localzzjp;
    String str5;
    do
    {
      return;
      if ((this.zzzB != null) && (!this.zzzB.zzbs()))
      {
        paramMap.get("u");
        zzb.d("Action was blocked because no click was detected.");
        return;
      }
      localzzjp = paramzzjo.zzhK();
      if ("expand".equalsIgnoreCase(str1))
      {
        if (paramzzjo.zzhO())
        {
          zzb.w("Cannot expand WebView that is already expanded.");
          return;
        }
        zzo(false);
        localzzjp.zza(zzc(paramMap), zzd(paramMap));
        return;
      }
      if ("webapp".equalsIgnoreCase(str1))
      {
        String str6 = (String)paramMap.get("u");
        zzo(false);
        if (str6 != null)
        {
          localzzjp.zza(zzc(paramMap), zzd(paramMap), str6);
          return;
        }
        localzzjp.zza(zzc(paramMap), zzd(paramMap), (String)paramMap.get("html"), (String)paramMap.get("baseurl"));
        return;
      }
      if (!"in_app_purchase".equalsIgnoreCase(str1)) {
        break;
      }
      paramMap.get("product_id");
      str5 = (String)paramMap.get("report_urls");
    } while (this.zzzE == null);
    if ((str5 != null) && (!str5.isEmpty()))
    {
      new ArrayList(Arrays.asList(str5.split(" ")));
      return;
    }
    new ArrayList();
    return;
    if (("app".equalsIgnoreCase(str1)) && ("true".equalsIgnoreCase((String)paramMap.get("play_store"))))
    {
      String str4 = (String)paramMap.get("u");
      if (TextUtils.isEmpty(str4))
      {
        zzb.w("Destination url cannot be empty.");
        return;
      }
      new zza(paramzzjo, str4).zzhf();
      return;
    }
    if (("app".equalsIgnoreCase(str1)) && ("true".equalsIgnoreCase((String)paramMap.get("system_browser"))))
    {
      Context localContext = paramzzjo.getContext();
      if (TextUtils.isEmpty((String)paramMap.get("u")))
      {
        zzb.w("Destination url cannot be empty.");
        return;
      }
      Intent localIntent = new zzb().zzb(localContext, paramMap);
      try
      {
        zzp.zzbI();
        zziq.zzb(localContext, localIntent);
        return;
      }
      catch (ActivityNotFoundException localActivityNotFoundException)
      {
        zzb.w(localActivityNotFoundException.getMessage());
        return;
      }
    }
    zzo(true);
    String str2 = (String)paramMap.get("u");
    if (!TextUtils.isEmpty(str2)) {
      zzp.zzbI();
    }
    for (String str3 = zziq.zza(paramzzjo.getContext(), paramzzjo.zzhM(), str2);; str3 = str2)
    {
      localzzjp.zza(new AdLauncherIntentInfoParcel((String)paramMap.get("i"), str3, (String)paramMap.get("m"), (String)paramMap.get("p"), (String)paramMap.get("c"), (String)paramMap.get("f"), (String)paramMap.get("e")));
      return;
    }
  }
  
  public static final class zza
    extends zzim
  {
    private final String zzE;
    private final zzjo zzpX;
    private final String zzzF = "play.google.com";
    private final String zzzG = "market";
    private final int zzzH = 10;
    
    public zza(zzjo paramzzjo, String paramString)
    {
      this.zzpX = paramzzjo;
      this.zzE = paramString;
    }
    
    public final void zzbB()
    {
      int i = 0;
      Object localObject1 = this.zzE;
      int j;
      if (i < 10) {
        j = i + 1;
      }
      for (;;)
      {
        Object localObject2;
        try
        {
          localURL = new URL((String)localObject1);
          boolean bool1 = "play.google.com".equalsIgnoreCase(localURL.getHost());
          if (!bool1) {
            continue;
          }
          localObject2 = localObject1;
        }
        catch (IndexOutOfBoundsException localIndexOutOfBoundsException1)
        {
          URL localURL;
          Uri localUri;
          Intent localIntent;
          HttpURLConnection localHttpURLConnection;
          localObject5 = localIndexOutOfBoundsException1;
          localObject2 = localObject1;
          zzb.w("Error while parsing ping URL: " + localObject2, (Throwable)localObject5);
          continue;
        }
        catch (IOException localIOException1)
        {
          localObject4 = localIOException1;
          localObject2 = localObject1;
          zzb.w("Error while pinging URL: " + localObject2, (Throwable)localObject4);
          continue;
        }
        catch (RuntimeException localRuntimeException1)
        {
          localObject3 = localRuntimeException1;
          localObject2 = localObject1;
          zzb.w("Error while pinging URL: " + localObject2, (Throwable)localObject3);
          continue;
          String str = "";
          continue;
        }
        localUri = Uri.parse(localObject2);
        localIntent = new Intent("android.intent.action.VIEW");
        localIntent.addFlags(268435456);
        localIntent.setData(localUri);
        zzp.zzbI();
        zziq.zzb(this.zzpX.getContext(), localIntent);
        return;
        if ("market".equalsIgnoreCase(localURL.getProtocol()))
        {
          localObject2 = localObject1;
        }
        else
        {
          localHttpURLConnection = (HttpURLConnection)localURL.openConnection();
          try
          {
            zzp.zzbI().zza(this.zzpX.getContext(), this.zzpX.zzhN().afmaVersion, false, localHttpURLConnection);
            int k = localHttpURLConnection.getResponseCode();
            Map localMap = localHttpURLConnection.getHeaderFields();
            if ((k >= 300) && (k <= 399))
            {
              List localList;
              if (localMap.containsKey("Location"))
              {
                localList = (List)localMap.get("Location");
                if ((localList == null) || (localList.size() <= 0)) {
                  continue;
                }
                str = (String)localList.get(0);
                if (TextUtils.isEmpty(str))
                {
                  zzb.w("Arrived at landing page, this ideally should not happen. Will open it in browser.");
                  localHttpURLConnection.disconnect();
                  localObject2 = localObject1;
                }
              }
              else
              {
                boolean bool2 = localMap.containsKey("location");
                localList = null;
                if (!bool2) {
                  continue;
                }
                localList = (List)localMap.get("location");
                continue;
              }
            }
          }
          finally
          {
            try
            {
              localHttpURLConnection.disconnect();
              i = j;
              localObject1 = str;
            }
            catch (RuntimeException localRuntimeException2)
            {
              localObject2 = str;
              Object localObject3 = localRuntimeException2;
              continue;
            }
            catch (IOException localIOException2)
            {
              localObject2 = str;
              Object localObject4 = localIOException2;
              continue;
            }
            catch (IndexOutOfBoundsException localIndexOutOfBoundsException2)
            {
              localObject2 = str;
              Object localObject5 = localIndexOutOfBoundsException2;
              continue;
            }
            localObject6 = finally;
            localHttpURLConnection.disconnect();
          }
          localObject2 = localObject1;
        }
      }
    }
  }
  
  public static final class zzb
  {
    private static Intent zza(Intent paramIntent, ResolveInfo paramResolveInfo)
    {
      Intent localIntent = new Intent(paramIntent);
      localIntent.setClassName(paramResolveInfo.activityInfo.packageName, paramResolveInfo.activityInfo.name);
      return localIntent;
    }
    
    private static ResolveInfo zza(Context paramContext, Intent paramIntent)
    {
      return zza(paramContext, paramIntent, new ArrayList());
    }
    
    private static ResolveInfo zza(Context paramContext, Intent paramIntent, ArrayList<ResolveInfo> paramArrayList)
    {
      PackageManager localPackageManager = paramContext.getPackageManager();
      if (localPackageManager == null) {
        return null;
      }
      List localList = localPackageManager.queryIntentActivities(paramIntent, 65536);
      ResolveInfo localResolveInfo1 = localPackageManager.resolveActivity(paramIntent, 65536);
      int i;
      if ((localList != null) && (localResolveInfo1 != null))
      {
        i = 0;
        if (i < localList.size())
        {
          ResolveInfo localResolveInfo3 = (ResolveInfo)localList.get(i);
          if ((localResolveInfo1 == null) || (!localResolveInfo1.activityInfo.name.equals(localResolveInfo3.activityInfo.name))) {}
        }
      }
      for (ResolveInfo localResolveInfo2 = localResolveInfo1;; localResolveInfo2 = null)
      {
        paramArrayList.addAll(localList);
        return localResolveInfo2;
        i++;
        break;
      }
    }
    
    private static Intent zzd(Uri paramUri)
    {
      if (paramUri == null) {
        return null;
      }
      Intent localIntent = new Intent("android.intent.action.VIEW");
      localIntent.addFlags(268435456);
      localIntent.setData(paramUri);
      localIntent.setAction("android.intent.action.VIEW");
      return localIntent;
    }
    
    public final Intent zzb(Context paramContext, Map<String, String> paramMap)
    {
      ActivityManager localActivityManager = (ActivityManager)paramContext.getSystemService("activity");
      String str = (String)paramMap.get("u");
      boolean bool1 = TextUtils.isEmpty(str);
      Intent localIntent1 = null;
      if (bool1) {
        return localIntent1;
      }
      Uri localUri1 = Uri.parse(str);
      boolean bool2 = Boolean.parseBoolean((String)paramMap.get("use_first_package"));
      boolean bool3 = Boolean.parseBoolean((String)paramMap.get("use_running_process"));
      Uri localUri2;
      if ("http".equalsIgnoreCase(localUri1.getScheme())) {
        localUri2 = localUri1.buildUpon().scheme("https").build();
      }
      for (;;)
      {
        ArrayList localArrayList = new ArrayList();
        Intent localIntent2 = zzd(localUri1);
        Intent localIntent3 = zzd(localUri2);
        ResolveInfo localResolveInfo1 = zza(paramContext, localIntent2, localArrayList);
        if (localResolveInfo1 != null)
        {
          return zza(localIntent2, localResolveInfo1);
          if ("https".equalsIgnoreCase(localUri1.getScheme())) {
            localUri2 = localUri1.buildUpon().scheme("http").build();
          }
        }
        else
        {
          if (localIntent3 != null)
          {
            ResolveInfo localResolveInfo3 = zza(paramContext, localIntent3);
            if (localResolveInfo3 != null)
            {
              localIntent1 = zza(localIntent2, localResolveInfo3);
              if (zza(paramContext, localIntent1) != null) {
                break;
              }
            }
          }
          if (localArrayList.size() == 0) {
            return localIntent2;
          }
          if ((bool3) && (localActivityManager != null))
          {
            List localList = localActivityManager.getRunningAppProcesses();
            if (localList != null)
            {
              Iterator localIterator1 = localArrayList.iterator();
              ResolveInfo localResolveInfo2;
              Iterator localIterator2;
              do
              {
                while (!localIterator2.hasNext())
                {
                  if (!localIterator1.hasNext()) {
                    break;
                  }
                  localResolveInfo2 = (ResolveInfo)localIterator1.next();
                  localIterator2 = localList.iterator();
                }
              } while (!((ActivityManager.RunningAppProcessInfo)localIterator2.next()).processName.equals(localResolveInfo2.activityInfo.packageName));
              return zza(localIntent2, localResolveInfo2);
            }
          }
          if (bool2) {
            return zza(localIntent2, (ResolveInfo)localArrayList.get(0));
          }
          return localIntent2;
        }
        localUri2 = null;
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzdt
 * JD-Core Version:    0.7.0.1
 */