package com.google.android.finsky.library;

public final class LibraryInAppSubscriptionEntry
  extends LibrarySubscriptionEntry
{
  public final String signature;
  public final String signedPurchaseData;
  
  public LibraryInAppSubscriptionEntry(String paramString1, String paramString2, int paramInt1, String paramString3, int paramInt2, long paramLong1, long paramLong2, long paramLong3, long paramLong4, boolean paramBoolean, String paramString4, String paramString5)
  {
    super(paramString1, paramString2, paramInt1, paramString3, 15, paramInt2, paramLong1, Long.valueOf(paramLong2), paramLong3, paramLong4, paramBoolean);
    this.signedPurchaseData = paramString4;
    this.signature = paramString5;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.library.LibraryInAppSubscriptionEntry
 * JD-Core Version:    0.7.0.1
 */