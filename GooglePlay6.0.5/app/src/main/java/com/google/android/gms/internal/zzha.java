package com.google.android.gms.internal;

import android.content.Context;
import android.net.Uri.Builder;
import android.os.Build.VERSION;
import android.os.Looper;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.config.Flag;
import com.google.android.gms.ads.internal.config.Flags;
import com.google.android.gms.ads.internal.config.zzf;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzp;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedList;

@zzhb
public final class zzha
  implements Thread.UncaughtExceptionHandler
{
  private Context mContext;
  private VersionInfoParcel zzEv;
  private Thread.UncaughtExceptionHandler zzGy = null;
  private Thread.UncaughtExceptionHandler zzGz = null;
  
  public zzha(Context paramContext, VersionInfoParcel paramVersionInfoParcel)
  {
    this.mContext = paramContext;
    this.zzEv = paramVersionInfoParcel;
  }
  
  private static boolean zzas(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    if (paramString.startsWith("com.google.android.gms.ads")) {
      return true;
    }
    if (paramString.startsWith("com.google.ads")) {
      return true;
    }
    try
    {
      boolean bool = Class.forName(paramString).isAnnotationPresent(zzhb.class);
      return bool;
    }
    catch (Exception localException)
    {
      zzb.d("Fail to check class type for class " + paramString, localException);
    }
    return false;
  }
  
  public final void uncaughtException(Thread paramThread, Throwable paramThrowable)
  {
    int i;
    if (paramThrowable != null)
    {
      int j = 0;
      int k = 0;
      for (Throwable localThrowable = paramThrowable; localThrowable != null; localThrowable = localThrowable.getCause()) {
        for (StackTraceElement localStackTraceElement : localThrowable.getStackTrace())
        {
          if (zzas(localStackTraceElement.getClassName())) {
            k = 1;
          }
          if (getClass().getName().equals(localStackTraceElement.getClassName())) {
            j = 1;
          }
        }
      }
      if ((k != 0) && (j == 0))
      {
        i = 1;
        if (i == 0) {
          break label142;
        }
        if (Looper.getMainLooper().getThread() == paramThread) {
          break label136;
        }
        zza(paramThrowable, true);
      }
    }
    label136:
    label142:
    do
    {
      return;
      i = 0;
      break;
      zza(paramThrowable, false);
      if (this.zzGy != null)
      {
        this.zzGy.uncaughtException(paramThread, paramThrowable);
        return;
      }
    } while (this.zzGz == null);
    this.zzGz.uncaughtException(paramThread, paramThrowable);
  }
  
  public final void zza(Throwable paramThrowable, boolean paramBoolean)
  {
    Flag localFlag1 = Flags.zzvP;
    if (!((Boolean)zzp.zzbR().zzd(localFlag1)).booleanValue()) {}
    do
    {
      return;
      Flag localFlag2 = Flags.zzvQ;
      if (!((Boolean)zzp.zzbR().zzd(localFlag2)).booleanValue()) {
        break;
      }
      localObject1 = paramThrowable;
    } while (localObject1 == null);
    Class localClass = paramThrowable.getClass();
    ArrayList localArrayList2 = new ArrayList();
    StringWriter localStringWriter = new StringWriter();
    ((Throwable)localObject1).printStackTrace(new PrintWriter(localStringWriter));
    Uri.Builder localBuilder = new Uri.Builder().scheme("https").path("//pagead2.googlesyndication.com/pagead/gen_204").appendQueryParameter("id", "gmob-apps-report-exception").appendQueryParameter("os", Build.VERSION.RELEASE).appendQueryParameter("api", String.valueOf(Build.VERSION.SDK_INT));
    zzp.zzbI();
    localArrayList2.add(localBuilder.appendQueryParameter("device", zziq.zzhj()).appendQueryParameter("js", this.zzEv.afmaVersion).appendQueryParameter("appid", this.mContext.getApplicationContext().getPackageName()).appendQueryParameter("exceptiontype", localClass.getName()).appendQueryParameter("stacktrace", localStringWriter.toString()).appendQueryParameter("eids", TextUtils.join(",", Flags.zzdu())).appendQueryParameter("trapped", String.valueOf(paramBoolean)).toString());
    zzp.zzbI();
    zziq.zza(this.mContext, this.zzEv.afmaVersion, localArrayList2, zzp.zzbL().zzgX());
    return;
    LinkedList localLinkedList = new LinkedList();
    for (Throwable localThrowable1 = paramThrowable; localThrowable1 != null; localThrowable1 = localThrowable1.getCause()) {
      localLinkedList.push(localThrowable1);
    }
    Object localObject1 = null;
    label290:
    Throwable localThrowable2;
    label467:
    Object localObject2;
    if (!localLinkedList.isEmpty())
    {
      localThrowable2 = (Throwable)localLinkedList.pop();
      StackTraceElement[] arrayOfStackTraceElement = localThrowable2.getStackTrace();
      ArrayList localArrayList1 = new ArrayList();
      localArrayList1.add(new StackTraceElement(localThrowable2.getClass().getName(), "<filtered>", "<filtered>", 1));
      int i = arrayOfStackTraceElement.length;
      int j = 0;
      int k = 0;
      if (j < i)
      {
        StackTraceElement localStackTraceElement = arrayOfStackTraceElement[j];
        if (zzas(localStackTraceElement.getClassName()))
        {
          k = 1;
          localArrayList1.add(localStackTraceElement);
        }
        for (;;)
        {
          j++;
          break;
          String str = localStackTraceElement.getClassName();
          if ((!TextUtils.isEmpty(str)) && ((str.startsWith("android.")) || (str.startsWith("java.")))) {}
          for (int m = 1;; m = 0)
          {
            if (m == 0) {
              break label467;
            }
            localArrayList1.add(localStackTraceElement);
            break;
          }
          localArrayList1.add(new StackTraceElement("<filtered>", "<filtered>", "<filtered>", 1));
        }
      }
      if (k == 0) {
        break label560;
      }
      if (localObject1 != null) {
        break label541;
      }
      localObject2 = new Throwable(localThrowable2.getMessage());
      label517:
      ((Throwable)localObject2).setStackTrace((StackTraceElement[])localArrayList1.toArray(new StackTraceElement[0]));
    }
    for (;;)
    {
      localObject1 = localObject2;
      break label290;
      break;
      label541:
      localObject2 = new Throwable(localThrowable2.getMessage(), (Throwable)localObject1);
      break label517;
      label560:
      localObject2 = localObject1;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzha
 * JD-Core Version:    0.7.0.1
 */