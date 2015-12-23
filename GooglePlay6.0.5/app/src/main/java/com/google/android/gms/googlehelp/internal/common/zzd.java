package com.google.android.gms.googlehelp.internal.common;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public final class zzd
{
  public static Bitmap zzu(Activity paramActivity)
  {
    FutureTask localFutureTask = new FutureTask(new Callable() {});
    paramActivity.runOnUiThread(localFutureTask);
    try
    {
      Bitmap localBitmap = (Bitmap)localFutureTask.get();
      return localBitmap;
    }
    catch (InterruptedException localInterruptedException)
    {
      Log.w("gH_Utils", "Taking screenshot failed!");
      return null;
    }
    catch (ExecutionException localExecutionException)
    {
      label35:
      break label35;
    }
  }
  
  static Bitmap zzv(Activity paramActivity)
  {
    try
    {
      View localView1 = paramActivity.getWindow().getDecorView().getRootView();
      View localView2 = localView1.findViewById(16908290);
      if (localView2 == null) {
        return null;
      }
      int i = paramActivity.getResources().getIdentifier("status_bar_height", "dimen", "android");
      int j = 0;
      if (i != 0) {
        j = paramActivity.getResources().getDimensionPixelSize(i);
      }
      int k = j + localView2.getTop();
      if (k < localView1.getHeight())
      {
        Bitmap localBitmap1 = Bitmap.createBitmap(localView1.getWidth(), localView1.getHeight(), Bitmap.Config.ARGB_8888);
        localView1.draw(new Canvas(localBitmap1));
        int m = Math.min(localBitmap1.getHeight() - k, localView2.getHeight());
        Bitmap localBitmap2 = Bitmap.createBitmap(localBitmap1, 0, k, localBitmap1.getWidth(), m);
        return localBitmap2;
      }
    }
    catch (Exception localException)
    {
      Log.w("gH_Utils", "Get screenshot failed!", localException);
    }
    return null;
  }
  
  public static String zzw(Activity paramActivity)
  {
    String str = paramActivity.getTitle().toString();
    int i = paramActivity.getResources().getIdentifier("action_bar", "id", paramActivity.getPackageName());
    if (i == 0) {
      return str;
    }
    ViewGroup localViewGroup = (ViewGroup)paramActivity.findViewById(i);
    if (localViewGroup == null) {
      return str;
    }
    for (int j = 0; j < localViewGroup.getChildCount(); j++)
    {
      View localView = localViewGroup.getChildAt(j);
      if ((localView instanceof TextView)) {
        return ((TextView)localView).getText().toString();
      }
    }
    return str;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.googlehelp.internal.common.zzd
 * JD-Core Version:    0.7.0.1
 */