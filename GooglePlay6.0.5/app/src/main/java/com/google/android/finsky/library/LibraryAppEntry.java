package com.google.android.finsky.library;

public final class LibraryAppEntry
  extends LibraryEntry
{
  public static final String[] ANY_CERTIFICATE_HASHES = null;
  public final String[] certificateHashes;
  public final boolean isOwnedViaLicense;
  public final long refundPostDeliveryWindowMs;
  public final long refundPreDeliveryEndtimeMs;
  
  public LibraryAppEntry(String paramString1, String paramString2, int paramInt, long paramLong1, String[] paramArrayOfString, long paramLong2, long paramLong3, boolean paramBoolean1, boolean paramBoolean2, String paramString3)
  {
    super(paramString1, AccountLibrary.LIBRARY_ID_APPS, 3, paramString2, 1, paramInt, paramLong1, 9223372036854775807L, false, paramBoolean2, paramString3);
    this.certificateHashes = paramArrayOfString;
    this.refundPreDeliveryEndtimeMs = paramLong2;
    this.refundPostDeliveryWindowMs = paramLong3;
    this.isOwnedViaLicense = paramBoolean1;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (!super.equals(paramObject)) {}
    LibraryAppEntry localLibraryAppEntry;
    do
    {
      return false;
      if (!(paramObject instanceof LibraryAppEntry)) {
        return true;
      }
      localLibraryAppEntry = (LibraryAppEntry)paramObject;
    } while (this.certificateHashes.length != localLibraryAppEntry.certificateHashes.length);
    int i = this.certificateHashes.length;
    int j = 0;
    label46:
    if (j < i) {
      for (int k = 0;; k++)
      {
        int m = 0;
        if (k < i)
        {
          if (this.certificateHashes[j].equals(localLibraryAppEntry.certificateHashes[k])) {
            m = 1;
          }
        }
        else
        {
          if (m == 0) {
            break;
          }
          j++;
          break label46;
        }
      }
    }
    return true;
  }
  
  public final boolean hasMatchingCertificateHash(String[] paramArrayOfString)
  {
    if (paramArrayOfString == ANY_CERTIFICATE_HASHES) {
      return true;
    }
    int i = paramArrayOfString.length;
    label64:
    for (int j = 0;; j++)
    {
      if (j >= i) {
        break label70;
      }
      String str = paramArrayOfString[j];
      String[] arrayOfString = this.certificateHashes;
      int k = arrayOfString.length;
      for (int m = 0;; m++)
      {
        if (m >= k) {
          break label64;
        }
        if (str.equals(arrayOfString[m])) {
          break;
        }
      }
    }
    label70:
    return false;
  }
  
  public final String toString()
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.mDocId;
    return String.format("{package=%s}", arrayOfObject);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.library.LibraryAppEntry
 * JD-Core Version:    0.7.0.1
 */