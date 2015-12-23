package com.google.android.finsky.library;

import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.protos.Common.Docid;
import com.google.android.finsky.protos.DocV2;

public class LibraryEntry
{
  public static final String UNKNOWN_ACCOUNT = null;
  public final String mAccountName;
  public int mBackendId;
  public String mDocId;
  public int mDocType;
  final long mDocumentHash;
  public String mLibraryId;
  public int mOfferType;
  public final boolean mPreordered;
  public final boolean mSharedByMe;
  public final String mSharerPersonDocid;
  public final long mValidUntilTimestampMs;
  
  public LibraryEntry(String paramString1, String paramString2, int paramInt1, String paramString3, int paramInt2, int paramInt3)
  {
    this(paramString1, paramString2, paramInt1, paramString3, paramInt2, paramInt3, -9223372036854775808L, 9223372036854775807L, false, false, null);
  }
  
  public LibraryEntry(String paramString1, String paramString2, int paramInt1, String paramString3, int paramInt2, int paramInt3, long paramLong1, long paramLong2, boolean paramBoolean1, boolean paramBoolean2, String paramString4)
  {
    if (paramString3 == null) {
      throw new NullPointerException();
    }
    this.mAccountName = paramString1;
    this.mLibraryId = paramString2;
    this.mBackendId = paramInt1;
    this.mDocId = paramString3;
    this.mDocType = paramInt2;
    this.mOfferType = paramInt3;
    this.mDocumentHash = paramLong1;
    this.mValidUntilTimestampMs = paramLong2;
    this.mPreordered = paramBoolean1;
    this.mSharedByMe = paramBoolean2;
    this.mSharerPersonDocid = paramString4;
  }
  
  public static LibraryEntry fromDocId(String paramString1, String paramString2, Common.Docid paramDocid, int paramInt)
  {
    return new LibraryEntry(paramString1, paramString2, paramDocid.backend, paramDocid.backendDocid, paramDocid.type, paramInt);
  }
  
  public static LibraryEntry fromDocument(String paramString1, String paramString2, Document paramDocument, int paramInt)
  {
    return new LibraryEntry(paramString1, paramString2, paramDocument.mDocument.backendId, paramDocument.mDocument.backendDocid, paramDocument.mDocument.docType, paramInt);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    LibraryEntry localLibraryEntry;
    do
    {
      return true;
      if (!(paramObject instanceof LibraryEntry)) {
        return false;
      }
      localLibraryEntry = (LibraryEntry)paramObject;
      if (this.mBackendId != localLibraryEntry.mBackendId) {
        return false;
      }
      if (this.mDocType != localLibraryEntry.mDocType) {
        return false;
      }
      if (this.mOfferType != localLibraryEntry.mOfferType) {
        return false;
      }
      if ((this.mAccountName != UNKNOWN_ACCOUNT) && (localLibraryEntry.mAccountName != UNKNOWN_ACCOUNT) && (!this.mAccountName.equals(localLibraryEntry.mAccountName))) {
        return false;
      }
      if (!this.mDocId.equals(localLibraryEntry.mDocId)) {
        return false;
      }
    } while (this.mLibraryId.equals(localLibraryEntry.mLibraryId));
    return false;
  }
  
  public int hashCode()
  {
    if (this.mLibraryId != null) {}
    for (int i = this.mLibraryId.hashCode();; i = 0)
    {
      int j = 31 * (i + 0);
      String str = this.mDocId;
      int k = 0;
      if (str != null) {
        k = this.mDocId.hashCode();
      }
      return 31 * (31 * (j + k) + this.mDocType) + this.mOfferType;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.library.LibraryEntry
 * JD-Core Version:    0.7.0.1
 */