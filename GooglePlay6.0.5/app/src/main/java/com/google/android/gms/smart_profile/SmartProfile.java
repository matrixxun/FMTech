package com.google.android.gms.smart_profile;

import android.content.Intent;

public final class SmartProfile
{
  public static final class IntentBuilder
  {
    public final Intent mIntent = new Intent();
    
    public IntentBuilder()
    {
      this.mIntent.setPackage("com.google.android.gms");
      this.mIntent.setAction("com.google.android.gms.people.smart_profile.ACTION_SHOW_PROFILE");
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.smart_profile.SmartProfile
 * JD-Core Version:    0.7.0.1
 */