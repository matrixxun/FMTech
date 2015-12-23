package com.google.android.finsky.download;

public final class DownloadManagerConstants
{
  public static boolean isStatusCompleted(int paramInt)
  {
    return ((paramInt >= 200) && (paramInt < 300)) || ((paramInt >= 400) && (paramInt < 600));
  }
  
  public static boolean isStatusSuccess(int paramInt)
  {
    return (paramInt >= 200) && (paramInt < 300);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.download.DownloadManagerConstants
 * JD-Core Version:    0.7.0.1
 */