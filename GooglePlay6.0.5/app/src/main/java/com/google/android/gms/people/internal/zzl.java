package com.google.android.gms.people.internal;

import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.internal.zzx;

public final class zzl
{
  public static String zzd(Object... paramVarArgs)
  {
    int i = 0;
    StringBuilder localStringBuilder = new StringBuilder();
    if (paramVarArgs.length % 2 == 0) {}
    for (boolean bool = true;; bool = false)
    {
      zzx.zzab(bool);
      String str = "";
      while (i < paramVarArgs.length)
      {
        localStringBuilder.append(str);
        localStringBuilder.append(paramVarArgs[i]);
        localStringBuilder.append("=");
        localStringBuilder.append(paramVarArgs[(i + 1)]);
        str = ", ";
        i += 2;
      }
    }
    return localStringBuilder.toString();
  }
  
  public static void zzi(String paramString, Object... paramVarArgs)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append("(");
    String str = "";
    int i = 0;
    if (i < 3)
    {
      Object localObject = paramVarArgs[i];
      localStringBuilder.append(str);
      if ((localObject instanceof Bundle)) {
        localStringBuilder.append(zzp.zzS((Bundle)localObject));
      }
      for (;;)
      {
        str = ", ";
        i++;
        break;
        localStringBuilder.append(localObject);
      }
    }
    localStringBuilder.append(")");
    if (Log.isLoggable("PeopleClientCall", 2)) {}
    for (Throwable localThrowable = new Throwable("STACK TRACE (It's not crash!)");; localThrowable = null)
    {
      Log.d("PeopleClientCall", localStringBuilder.toString(), localThrowable);
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.people.internal.zzl
 * JD-Core Version:    0.7.0.1
 */