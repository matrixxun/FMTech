package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;

public final class zzbz
{
  public static Status zzhO(int paramInt)
  {
    String str;
    switch (paramInt)
    {
    default: 
      str = CommonStatusCodes.getStatusCodeString(paramInt);
    }
    for (;;)
    {
      return new Status(paramInt, str);
      str = "TARGET_NODE_NOT_CONNECTED";
      continue;
      str = "DUPLICATE_LISTENER";
      continue;
      str = "UNKNOWN_LISTENER";
      continue;
      str = "DATA_ITEM_TOO_LARGE";
      continue;
      str = "INVALID_TARGET_NODE";
      continue;
      str = "ASSET_UNAVAILABLE";
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzbz
 * JD-Core Version:    0.7.0.1
 */