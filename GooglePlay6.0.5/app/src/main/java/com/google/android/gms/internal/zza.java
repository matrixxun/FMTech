package com.google.android.gms.internal;

import android.content.Intent;

public final class zza
  extends zzr
{
  private Intent zza;
  
  public zza() {}
  
  public zza(zzi paramzzi)
  {
    super(paramzzi);
  }
  
  public final String getMessage()
  {
    if (this.zza != null) {
      return "User needs to (re)enter credentials.";
    }
    return super.getMessage();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zza
 * JD-Core Version:    0.7.0.1
 */