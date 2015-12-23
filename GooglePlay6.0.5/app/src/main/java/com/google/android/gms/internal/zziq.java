package com.google.android.gms.internal;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.PopupWindow;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.google.android.gms.ads.internal.client.zzl;
import com.google.android.gms.ads.internal.config.Flags;
import com.google.android.gms.ads.internal.util.client.zza;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.ads.search.SearchAdView;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzx;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzhb
public final class zziq
{
  private static final String zzLb = AdView.class.getName();
  private static final String zzLc = InterstitialAd.class.getName();
  private static final String zzLd = PublisherAdView.class.getName();
  private static final String zzLe = PublisherInterstitialAd.class.getName();
  private static final String zzLf = SearchAdView.class.getName();
  private static final String zzLg = AdLoader.class.getName();
  public static final Handler zzLh = new zzin(Looper.getMainLooper());
  private String zzKB;
  private boolean zzLi = true;
  private boolean zzLj = false;
  private final Object zzqp = new Object();
  
  public static void runOnUiThread(Runnable paramRunnable)
  {
    if (Looper.getMainLooper().getThread() == Thread.currentThread())
    {
      paramRunnable.run();
      return;
    }
    zzLh.post(paramRunnable);
  }
  
  protected static String zzM(Context paramContext)
  {
    return new WebView(paramContext).getSettings().getUserAgentString();
  }
  
  public static AlertDialog.Builder zzN(Context paramContext)
  {
    return new AlertDialog.Builder(paramContext);
  }
  
  public static zzca zzO(Context paramContext)
  {
    return new zzca(paramContext);
  }
  
  public static DisplayMetrics zza(WindowManager paramWindowManager)
  {
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    paramWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
    return localDisplayMetrics;
  }
  
  public static String zza(Context paramContext, zzao paramzzao, String paramString)
  {
    if (paramzzao == null) {
      return paramString;
    }
    try
    {
      Uri localUri = Uri.parse(paramString);
      boolean bool = paramzzao.isGoogleAdUrl(localUri);
      int i = 0;
      String[] arrayOfString;
      int j;
      if (bool)
      {
        arrayOfString = zzao.zzoi;
        j = arrayOfString.length;
      }
      for (int k = 0;; k++)
      {
        i = 0;
        if (k < j)
        {
          String str1 = arrayOfString[k];
          if (localUri.getPath().endsWith(str1)) {
            i = 1;
          }
        }
        else
        {
          if (i != 0) {
            localUri = paramzzao.zzb(localUri, paramContext);
          }
          String str2 = localUri.toString();
          return str2;
        }
      }
      return paramString;
    }
    catch (Exception localException) {}
  }
  
  private JSONArray zza(Collection<?> paramCollection)
    throws JSONException
  {
    JSONArray localJSONArray = new JSONArray();
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext()) {
      zza(localJSONArray, localIterator.next());
    }
    return localJSONArray;
  }
  
  public static void zza(Activity paramActivity, ViewTreeObserver.OnGlobalLayoutListener paramOnGlobalLayoutListener)
  {
    Window localWindow = paramActivity.getWindow();
    if ((localWindow != null) && (localWindow.getDecorView() != null) && (localWindow.getDecorView().getViewTreeObserver() != null)) {
      localWindow.getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(paramOnGlobalLayoutListener);
    }
  }
  
  public static void zza(Activity paramActivity, ViewTreeObserver.OnScrollChangedListener paramOnScrollChangedListener)
  {
    Window localWindow = paramActivity.getWindow();
    if ((localWindow != null) && (localWindow.getDecorView() != null) && (localWindow.getDecorView().getViewTreeObserver() != null)) {
      localWindow.getDecorView().getViewTreeObserver().addOnScrollChangedListener(paramOnScrollChangedListener);
    }
  }
  
  private static void zza(Context paramContext, String paramString, List<String> paramList)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext()) {
      new zzix(paramContext, paramString, (String)localIterator.next()).zzhf();
    }
  }
  
  public static void zza(Context paramContext, String paramString1, List<String> paramList, String paramString2)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext()) {
      new zzix(paramContext, paramString1, (String)localIterator.next(), paramString2).zzhf();
    }
  }
  
  private void zza(JSONArray paramJSONArray, Object paramObject)
    throws JSONException
  {
    if ((paramObject instanceof Bundle))
    {
      paramJSONArray.put(zzg((Bundle)paramObject));
      return;
    }
    if ((paramObject instanceof Map))
    {
      paramJSONArray.put(zzy((Map)paramObject));
      return;
    }
    if ((paramObject instanceof Collection))
    {
      paramJSONArray.put(zza((Collection)paramObject));
      return;
    }
    if ((paramObject instanceof Object[]))
    {
      Object[] arrayOfObject = (Object[])paramObject;
      JSONArray localJSONArray = new JSONArray();
      int i = arrayOfObject.length;
      for (int j = 0; j < i; j++) {
        zza(localJSONArray, arrayOfObject[j]);
      }
      paramJSONArray.put(localJSONArray);
      return;
    }
    paramJSONArray.put(paramObject);
  }
  
  private void zza(JSONObject paramJSONObject, String paramString, Object paramObject)
    throws JSONException
  {
    if ((paramObject instanceof Bundle))
    {
      paramJSONObject.put(paramString, zzg((Bundle)paramObject));
      return;
    }
    if ((paramObject instanceof Map))
    {
      paramJSONObject.put(paramString, zzy((Map)paramObject));
      return;
    }
    if ((paramObject instanceof Collection))
    {
      if (paramString != null) {}
      for (;;)
      {
        paramJSONObject.put(paramString, zza((Collection)paramObject));
        return;
        paramString = "null";
      }
    }
    if ((paramObject instanceof Object[]))
    {
      paramJSONObject.put(paramString, zza(Arrays.asList((Object[])paramObject)));
      return;
    }
    paramJSONObject.put(paramString, paramObject);
  }
  
  public static boolean zza(PackageManager paramPackageManager, String paramString1, String paramString2)
  {
    return paramPackageManager.checkPermission(paramString2, paramString1) == 0;
  }
  
  public static void zza$2d8d796a$11657ff2(HttpURLConnection paramHttpURLConnection, String paramString)
  {
    paramHttpURLConnection.setConnectTimeout(60000);
    paramHttpURLConnection.setInstanceFollowRedirects(true);
    paramHttpURLConnection.setReadTimeout(60000);
    paramHttpURLConnection.setRequestProperty("User-Agent", paramString);
    paramHttpURLConnection.setUseCaches(false);
  }
  
  public static PopupWindow zza$490f73c3(View paramView, int paramInt1, int paramInt2)
  {
    return new PopupWindow(paramView, paramInt1, paramInt2, false);
  }
  
  public static void zza$6b82a487(Context paramContext, String paramString1, String paramString2, Bundle paramBundle)
  {
    Context localContext = paramContext.getApplicationContext();
    if (localContext == null) {
      localContext = paramContext;
    }
    paramBundle.putString("os", Build.VERSION.RELEASE);
    paramBundle.putString("api", String.valueOf(Build.VERSION.SDK_INT));
    zzp.zzbI();
    paramBundle.putString("device", zzhj());
    paramBundle.putString("appid", localContext.getPackageName());
    paramBundle.putString("eids", TextUtils.join(",", Flags.zzdu()));
    if (paramString1 != null) {
      paramBundle.putString("js", paramString1);
    }
    Uri.Builder localBuilder;
    for (;;)
    {
      localBuilder = new Uri.Builder().scheme("https").path("//pagead2.googlesyndication.com/pagead/gen_204").appendQueryParameter("id", paramString2);
      Iterator localIterator = paramBundle.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        localBuilder.appendQueryParameter(str2, paramBundle.getString(str2));
      }
      paramBundle.putString("gmscore_version", Integer.toString(GooglePlayServicesUtil.getApkVersion(paramContext)));
    }
    zzp.zzbI();
    String str1 = localBuilder.toString();
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(str1);
    zza(paramContext, paramString1, localArrayList);
  }
  
  public static int zzaA(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      return i;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      zzb.w("Could not parse value:" + localNumberFormatException);
    }
    return 0;
  }
  
  public static boolean zzaB(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    return paramString.matches("([^\\s]+(\\.(?i)(jpg|png|gif|bmp|webp))$)");
  }
  
  public static void zzb(Activity paramActivity, ViewTreeObserver.OnScrollChangedListener paramOnScrollChangedListener)
  {
    Window localWindow = paramActivity.getWindow();
    if ((localWindow != null) && (localWindow.getDecorView() != null) && (localWindow.getDecorView().getViewTreeObserver() != null)) {
      localWindow.getDecorView().getViewTreeObserver().removeOnScrollChangedListener(paramOnScrollChangedListener);
    }
  }
  
  public static void zzb(Context paramContext, Intent paramIntent)
  {
    try
    {
      paramContext.startActivity(paramIntent);
      return;
    }
    catch (Throwable localThrowable)
    {
      paramIntent.addFlags(268435456);
      paramContext.startActivity(paramIntent);
    }
  }
  
  public static Bitmap zze(Context paramContext, String paramString)
  {
    zzx.zzcy("getBackgroundImage must not be called on the main UI thread.");
    try
    {
      FileInputStream localFileInputStream = paramContext.openFileInput(paramString);
      Bitmap localBitmap = BitmapFactory.decodeStream(localFileInputStream);
      localFileInputStream.close();
      return localBitmap;
    }
    catch (Exception localException)
    {
      zzb.e("Fail to get background image");
    }
    return null;
  }
  
  public static Map<String, String> zze(Uri paramUri)
  {
    if (paramUri == null) {
      return null;
    }
    HashMap localHashMap = new HashMap();
    Iterator localIterator = zzp.zzbK().zzf(paramUri).iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localHashMap.put(str, paramUri.getQueryParameter(str));
    }
    return localHashMap;
  }
  
  private JSONObject zzg(Bundle paramBundle)
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    Iterator localIterator = paramBundle.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      zza(localJSONObject, str, paramBundle.get(str));
    }
    return localJSONObject;
  }
  
  public static int[] zzg(Activity paramActivity)
  {
    Window localWindow = paramActivity.getWindow();
    if (localWindow != null)
    {
      View localView = localWindow.findViewById(16908290);
      if (localView != null)
      {
        int[] arrayOfInt = new int[2];
        arrayOfInt[0] = localView.getWidth();
        arrayOfInt[1] = localView.getHeight();
        return arrayOfInt;
      }
    }
    return zzhk();
  }
  
  private static String zzhh()
  {
    StringBuffer localStringBuffer = new StringBuffer(256);
    localStringBuffer.append("Mozilla/5.0 (Linux; U; Android");
    if (Build.VERSION.RELEASE != null) {
      localStringBuffer.append(" ").append(Build.VERSION.RELEASE);
    }
    localStringBuffer.append("; ").append(Locale.getDefault());
    if (Build.DEVICE != null)
    {
      localStringBuffer.append("; ").append(Build.DEVICE);
      if (Build.DISPLAY != null) {
        localStringBuffer.append(" Build/").append(Build.DISPLAY);
      }
    }
    localStringBuffer.append(") AppleWebKit/533 Version/4.0 Safari/533");
    return localStringBuffer.toString();
  }
  
  public static String zzhi()
  {
    UUID localUUID = UUID.randomUUID();
    byte[] arrayOfByte1 = BigInteger.valueOf(localUUID.getLeastSignificantBits()).toByteArray();
    byte[] arrayOfByte2 = BigInteger.valueOf(localUUID.getMostSignificantBits()).toByteArray();
    Object localObject = new BigInteger(1, arrayOfByte1).toString();
    int i = 0;
    while (i < 2) {
      try
      {
        MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
        localMessageDigest.update(arrayOfByte1);
        localMessageDigest.update(arrayOfByte2);
        byte[] arrayOfByte3 = new byte[8];
        System.arraycopy(localMessageDigest.digest(), 0, arrayOfByte3, 0, 8);
        String str = new BigInteger(1, arrayOfByte3).toString();
        localObject = str;
        label106:
        i++;
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
      {
        break label106;
      }
    }
    return localObject;
  }
  
  public static String zzhj()
  {
    String str1 = Build.MANUFACTURER;
    String str2 = Build.MODEL;
    if (str2.startsWith(str1)) {
      return str2;
    }
    return str1 + " " + str2;
  }
  
  private static int[] zzhk()
  {
    return new int[] { 0, 0 };
  }
  
  public static Bitmap zzl(View paramView)
  {
    paramView.setDrawingCacheEnabled(true);
    Bitmap localBitmap = Bitmap.createBitmap(paramView.getDrawingCache());
    paramView.setDrawingCacheEnabled(false);
    return localBitmap;
  }
  
  public final void zza(Context paramContext, String paramString, boolean paramBoolean, HttpURLConnection paramHttpURLConnection)
  {
    paramHttpURLConnection.setConnectTimeout(60000);
    paramHttpURLConnection.setInstanceFollowRedirects(paramBoolean);
    paramHttpURLConnection.setReadTimeout(60000);
    paramHttpURLConnection.setRequestProperty("User-Agent", zzd(paramContext, paramString));
    paramHttpURLConnection.setUseCaches(false);
  }
  
  public final String zzd(final Context paramContext, String paramString)
  {
    synchronized (this.zzqp)
    {
      if (this.zzKB != null)
      {
        String str3 = this.zzKB;
        return str3;
      }
    }
    try
    {
      this.zzKB = zzp.zzbK().getDefaultUserAgent(paramContext);
      label36:
      if (TextUtils.isEmpty(this.zzKB))
      {
        zzl.zzcX();
        if (!zza.zzhz())
        {
          this.zzKB = null;
          zzLh.post(new Runnable()
          {
            public final void run()
            {
              synchronized (zziq.zza(zziq.this))
              {
                zziq.zza(zziq.this, zziq.zzM(paramContext));
                zziq.zza(zziq.this).notifyAll();
                return;
              }
            }
          });
          for (;;)
          {
            String str2 = this.zzKB;
            if (str2 != null) {
              break;
            }
            try
            {
              this.zzqp.wait();
            }
            catch (InterruptedException localInterruptedException)
            {
              this.zzKB = zzhh();
              zzb.w("Interrupted, use default user agent: " + this.zzKB);
            }
          }
          localObject2 = finally;
          throw localObject2;
        }
      }
      try
      {
        this.zzKB = zzM(paramContext);
        this.zzKB = (this.zzKB + " (Mobile; " + paramString + ")");
        String str1 = this.zzKB;
        return str1;
      }
      catch (Exception localException2)
      {
        for (;;)
        {
          this.zzKB = zzhh();
        }
      }
    }
    catch (Exception localException1)
    {
      break label36;
    }
  }
  
  public final int[] zzh(Activity paramActivity)
  {
    int[] arrayOfInt1 = zzg(paramActivity);
    int[] arrayOfInt2 = new int[2];
    zzl.zzcX();
    arrayOfInt2[0] = zza.zzc(paramActivity, arrayOfInt1[0]);
    zzl.zzcX();
    arrayOfInt2[1] = zza.zzc(paramActivity, arrayOfInt1[1]);
    return arrayOfInt2;
  }
  
  public final int[] zzj(Activity paramActivity)
  {
    Window localWindow = paramActivity.getWindow();
    int[] arrayOfInt1;
    if (localWindow != null)
    {
      View localView = localWindow.findViewById(16908290);
      if (localView != null)
      {
        arrayOfInt1 = new int[2];
        arrayOfInt1[0] = localView.getTop();
        arrayOfInt1[1] = localView.getBottom();
      }
    }
    for (;;)
    {
      int[] arrayOfInt2 = new int[2];
      zzl.zzcX();
      arrayOfInt2[0] = zza.zzc(paramActivity, arrayOfInt1[0]);
      zzl.zzcX();
      arrayOfInt2[1] = zza.zzc(paramActivity, arrayOfInt1[1]);
      return arrayOfInt2;
      arrayOfInt1 = zzhk();
    }
  }
  
  public final JSONObject zzy(Map<String, ?> paramMap)
    throws JSONException
  {
    try
    {
      JSONObject localJSONObject = new JSONObject();
      Iterator localIterator = paramMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        zza(localJSONObject, str, paramMap.get(str));
      }
      return localJSONObject;
    }
    catch (ClassCastException localClassCastException)
    {
      throw new JSONException("Could not convert map to JSON: " + localClassCastException.getMessage());
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zziq
 * JD-Core Version:    0.7.0.1
 */