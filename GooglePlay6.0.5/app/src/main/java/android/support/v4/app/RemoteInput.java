package android.support.v4.app;

import android.os.Build.VERSION;
import android.os.Bundle;

public final class RemoteInput
  extends RemoteInputCompatBase.RemoteInput
{
  public static final RemoteInputCompatBase.RemoteInput.Factory FACTORY;
  private static final Impl IMPL;
  private final boolean mAllowFreeFormInput;
  private final CharSequence[] mChoices;
  private final Bundle mExtras;
  private final CharSequence mLabel;
  private final String mResultKey;
  
  static
  {
    if (Build.VERSION.SDK_INT >= 20) {
      IMPL = new ImplApi20();
    }
    for (;;)
    {
      FACTORY = new RemoteInputCompatBase.RemoteInput.Factory() {};
      return;
      if (Build.VERSION.SDK_INT >= 16) {
        IMPL = new ImplJellybean();
      } else {
        IMPL = new ImplBase();
      }
    }
  }
  
  public final boolean getAllowFreeFormInput()
  {
    return this.mAllowFreeFormInput;
  }
  
  public final CharSequence[] getChoices()
  {
    return this.mChoices;
  }
  
  public final Bundle getExtras()
  {
    return this.mExtras;
  }
  
  public final CharSequence getLabel()
  {
    return this.mLabel;
  }
  
  public final String getResultKey()
  {
    return this.mResultKey;
  }
  
  static abstract interface Impl {}
  
  static final class ImplApi20
    implements RemoteInput.Impl
  {}
  
  static final class ImplBase
    implements RemoteInput.Impl
  {}
  
  static final class ImplJellybean
    implements RemoteInput.Impl
  {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.app.RemoteInput
 * JD-Core Version:    0.7.0.1
 */