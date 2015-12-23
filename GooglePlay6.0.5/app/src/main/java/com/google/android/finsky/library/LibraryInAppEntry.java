package com.google.android.finsky.library;

public final class LibraryInAppEntry
  extends LibraryEntry
{
  public final String signature;
  public final String signedPurchaseData;
  
  public LibraryInAppEntry(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, String paramString5, long paramLong)
  {
    super(paramString1, paramString2, 3, paramString3, 11, paramInt, paramLong, 9223372036854775807L, false, false, null);
    this.signedPurchaseData = paramString4;
    this.signature = paramString5;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.library.LibraryInAppEntry
 * JD-Core Version:    0.7.0.1
 */