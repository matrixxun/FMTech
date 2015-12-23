package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.internal.zzx;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONException;

public final class zzr
{
  private static final Lock zzYr = new ReentrantLock();
  private static zzr zzYs;
  private final Lock zzYt = new ReentrantLock();
  private final SharedPreferences zzYu;
  
  private zzr(Context paramContext)
  {
    this.zzYu = paramContext.getSharedPreferences("com.google.android.gms.signin", 0);
  }
  
  public static zzr zzaf(Context paramContext)
  {
    zzx.zzC(paramContext);
    zzYr.lock();
    try
    {
      if (zzYs == null) {
        zzYs = new zzr(paramContext.getApplicationContext());
      }
      zzr localzzr = zzYs;
      return localzzr;
    }
    finally
    {
      zzYr.unlock();
    }
  }
  
  public GoogleSignInAccount zzbF(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {}
    String str;
    do
    {
      return null;
      str = zzbH("googleSignInAccount" + ":" + paramString);
    } while (str == null);
    try
    {
      GoogleSignInAccount localGoogleSignInAccount = GoogleSignInAccount.zzbq(str);
      return localGoogleSignInAccount;
    }
    catch (JSONException localJSONException) {}
    return null;
  }
  
  public String zzbH(String paramString)
  {
    this.zzYt.lock();
    try
    {
      String str = this.zzYu.getString(paramString, null);
      return str;
    }
    finally
    {
      this.zzYt.unlock();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.auth.api.signin.internal.zzr
 * JD-Core Version:    0.7.0.1
 */