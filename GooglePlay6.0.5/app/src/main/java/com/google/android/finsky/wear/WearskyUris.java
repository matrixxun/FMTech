package com.google.android.finsky.wear;

import android.net.Uri;
import java.util.List;

public final class WearskyUris
{
  public static final Uri URI_DATA_INSTALL_WEARABLE_ALL = Uri.parse("wear:/install_wearable");
  public static final Uri URI_DATA_REQUEST_STATUS_ALL = Uri.parse("wear:/request_status");
  public static final Uri URI_DATA_WEARABLE_INFO_ALL;
  public static final Uri URI_DEVICE_CONFIGURATION_ALL = Uri.parse("wear:/device_configuration");
  
  static
  {
    URI_DATA_WEARABLE_INFO_ALL = Uri.parse("wear:/wearable_info");
  }
  
  public static String getPackageNameFromUri(Uri paramUri)
  {
    List localList = paramUri.getPathSegments();
    return (String)localList.get(-1 + localList.size());
  }
  
  public static String getnodeId(Uri paramUri)
  {
    return paramUri.getHost();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.wear.WearskyUris
 * JD-Core Version:    0.7.0.1
 */