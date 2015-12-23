package com.google.android.gms.common.audience.dialogs;

import android.content.Intent;

public final class AudienceSelectionIntentBuilder
  implements CircleSelection.UpdateBuilder
{
  private final Intent mIntent;
  
  private AudienceSelectionIntentBuilder(Intent paramIntent)
  {
    this.mIntent = new Intent(paramIntent);
  }
  
  public AudienceSelectionIntentBuilder(String paramString)
  {
    this(new Intent(paramString).setPackage("com.google.android.gms"));
  }
  
  public final Intent build()
  {
    return this.mIntent;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.audience.dialogs.AudienceSelectionIntentBuilder
 * JD-Core Version:    0.7.0.1
 */