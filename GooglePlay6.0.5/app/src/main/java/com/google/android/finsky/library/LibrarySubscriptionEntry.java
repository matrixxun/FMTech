package com.google.android.finsky.library;

public class LibrarySubscriptionEntry
  extends LibraryEntry
{
  public final long initiationTimestampMs;
  public final boolean isAutoRenewing;
  public final long trialUntilTimestampMs;
  
  public LibrarySubscriptionEntry(String paramString1, String paramString2, int paramInt1, String paramString3, int paramInt2, int paramInt3, long paramLong1, Long paramLong, long paramLong2, long paramLong3, boolean paramBoolean)
  {
    super(paramString1, paramString2, paramInt1, paramString3, paramInt2, paramInt3, paramLong1, paramLong.longValue(), false, false, null);
    this.initiationTimestampMs = paramLong2;
    this.trialUntilTimestampMs = paramLong3;
    this.isAutoRenewing = paramBoolean;
  }
  
  public final int getCurrentSubscriptionState()
  {
    long l = System.currentTimeMillis();
    if (!this.isAutoRenewing) {
      return 3;
    }
    if (l < this.trialUntilTimestampMs) {
      return 1;
    }
    if (l < this.mValidUntilTimestampMs) {
      return 0;
    }
    return 2;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.library.LibrarySubscriptionEntry
 * JD-Core Version:    0.7.0.1
 */