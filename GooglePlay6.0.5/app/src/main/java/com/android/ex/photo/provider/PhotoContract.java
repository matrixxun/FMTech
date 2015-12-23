package com.android.ex.photo.provider;

public final class PhotoContract
{
  public static abstract interface PhotoQuery
  {
    public static final String[] OPTIONAL_COLUMNS = { "loadingIndicator" };
    public static final String[] PROJECTION = { "uri", "_display_name", "contentUri", "thumbnailUri", "contentType" };
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.ex.photo.provider.PhotoContract
 * JD-Core Version:    0.7.0.1
 */