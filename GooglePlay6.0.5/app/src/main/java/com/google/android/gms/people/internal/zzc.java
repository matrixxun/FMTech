package com.google.android.gms.people.internal;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.people.model.Circle;

public final class zzc
  extends com.google.android.gms.common.data.zzc
  implements Circle
{
  private final Bundle zzarz;
  
  public zzc(DataHolder paramDataHolder, int paramInt, Bundle paramBundle)
  {
    super(paramDataHolder, paramInt);
    this.zzarz = paramBundle;
  }
  
  public final String getCircleId()
  {
    return getString("circle_id");
  }
  
  public final String getCircleName()
  {
    int i = getInteger("type");
    switch (i)
    {
    case 0: 
    default: 
      i = -2;
    }
    if (i != -1)
    {
      Bundle localBundle = this.zzarz.getBundle("localized_group_names");
      if (localBundle != null)
      {
        String str = localBundle.getString(String.valueOf(i));
        if (!TextUtils.isEmpty(str)) {
          return str;
        }
      }
    }
    return getString("name");
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.people.internal.zzc
 * JD-Core Version:    0.7.0.1
 */