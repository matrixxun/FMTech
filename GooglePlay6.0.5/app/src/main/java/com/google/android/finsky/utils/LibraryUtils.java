package com.google.android.finsky.utils;

import android.accounts.Account;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.config.G;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.Library;
import com.google.android.finsky.library.LibraryEntry;
import com.google.android.finsky.protos.Annotations;
import com.google.android.finsky.protos.Common.Docid;
import com.google.android.finsky.protos.Common.Offer;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.FilterRules.Availability;
import com.google.android.finsky.protos.VideoAnnotations;
import com.google.android.play.utils.config.GservicesValue;
import java.util.List;

public final class LibraryUtils
{
  private static LibraryEntry sLibraryEntryForOwnership;
  private static LibraryEntry sMusicAllAccessLibraryEntry;
  
  public static Common.Docid getBundlePreorderForMovie(Document paramDocument, Library paramLibrary)
  {
    if ((paramDocument.mDocument.backendId == 4) && (!paramDocument.isVideoBundle()))
    {
      Annotations localAnnotations = paramDocument.mDocument.annotations;
      Common.Docid[] arrayOfDocid;
      int i;
      if ((localAnnotations != null) && (localAnnotations.videoAnnotations != null))
      {
        arrayOfDocid = localAnnotations.videoAnnotations.bundleDocid;
        i = arrayOfDocid.length;
      }
      for (int j = 0;; j++)
      {
        if (j >= i) {
          break label138;
        }
        Common.Docid localDocid = arrayOfDocid[j];
        int k = getOwnedPurchaseOfferType(localDocid, paramLibrary);
        if (k != -1)
        {
          String str = AccountLibrary.getLibraryIdFromBackend(localDocid.backend);
          LibraryEntry localLibraryEntry = paramLibrary.get(LibraryEntry.fromDocId(LibraryEntry.UNKNOWN_ACCOUNT, str, localDocid, k));
          if ((localLibraryEntry != null) && (localLibraryEntry.mPreordered))
          {
            return localDocid;
            arrayOfDocid = Common.Docid.emptyArray();
            break;
          }
        }
      }
    }
    label138:
    return null;
  }
  
  public static Account getFirstOwner(Document paramDocument, Libraries paramLibraries)
  {
    List localList = paramLibraries.getAccountLibraries();
    int i = localList.size();
    for (int j = 0; j < i; j++)
    {
      AccountLibrary localAccountLibrary = (AccountLibrary)localList.get(j);
      if (isOwned(paramDocument, localAccountLibrary)) {
        return localAccountLibrary.mAccount;
      }
    }
    return null;
  }
  
  /* Error */
  private static LibraryEntry getLibraryEntryForOffer(Common.Docid paramDocid, Library paramLibrary, int paramInt)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: aload_0
    //   4: getfield 50	com/google/android/finsky/protos/Common$Docid:backend	I
    //   7: invokestatic 56	com/google/android/finsky/library/AccountLibrary:getLibraryIdFromBackend	(I)Ljava/lang/String;
    //   10: astore 4
    //   12: getstatic 109	com/google/android/finsky/utils/LibraryUtils:sLibraryEntryForOwnership	Lcom/google/android/finsky/library/LibraryEntry;
    //   15: ifnonnull +33 -> 48
    //   18: getstatic 62	com/google/android/finsky/library/LibraryEntry:UNKNOWN_ACCOUNT	Ljava/lang/String;
    //   21: aload 4
    //   23: aload_0
    //   24: iload_2
    //   25: invokestatic 66	com/google/android/finsky/library/LibraryEntry:fromDocId	(Ljava/lang/String;Ljava/lang/String;Lcom/google/android/finsky/protos/Common$Docid;I)Lcom/google/android/finsky/library/LibraryEntry;
    //   28: putstatic 109	com/google/android/finsky/utils/LibraryUtils:sLibraryEntryForOwnership	Lcom/google/android/finsky/library/LibraryEntry;
    //   31: aload_1
    //   32: getstatic 109	com/google/android/finsky/utils/LibraryUtils:sLibraryEntryForOwnership	Lcom/google/android/finsky/library/LibraryEntry;
    //   35: invokeinterface 72 2 0
    //   40: astore 6
    //   42: ldc 2
    //   44: monitorexit
    //   45: aload 6
    //   47: areturn
    //   48: getstatic 109	com/google/android/finsky/utils/LibraryUtils:sLibraryEntryForOwnership	Lcom/google/android/finsky/library/LibraryEntry;
    //   51: astore 5
    //   53: aload 5
    //   55: aload 4
    //   57: putfield 112	com/google/android/finsky/library/LibraryEntry:mLibraryId	Ljava/lang/String;
    //   60: aload 5
    //   62: aload_0
    //   63: getfield 50	com/google/android/finsky/protos/Common$Docid:backend	I
    //   66: putfield 115	com/google/android/finsky/library/LibraryEntry:mBackendId	I
    //   69: aload 5
    //   71: aload_0
    //   72: getfield 118	com/google/android/finsky/protos/Common$Docid:backendDocid	Ljava/lang/String;
    //   75: putfield 121	com/google/android/finsky/library/LibraryEntry:mDocId	Ljava/lang/String;
    //   78: aload 5
    //   80: aload_0
    //   81: getfield 124	com/google/android/finsky/protos/Common$Docid:type	I
    //   84: putfield 127	com/google/android/finsky/library/LibraryEntry:mDocType	I
    //   87: aload 5
    //   89: iload_2
    //   90: putfield 130	com/google/android/finsky/library/LibraryEntry:mOfferType	I
    //   93: goto -62 -> 31
    //   96: astore_3
    //   97: ldc 2
    //   99: monitorexit
    //   100: aload_3
    //   101: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	102	0	paramDocid	Common.Docid
    //   0	102	1	paramLibrary	Library
    //   0	102	2	paramInt	int
    //   96	5	3	localObject	Object
    //   10	46	4	str	String
    //   51	37	5	localLibraryEntry1	LibraryEntry
    //   40	6	6	localLibraryEntry2	LibraryEntry
    // Exception table:
    //   from	to	target	type
    //   3	31	96	finally
    //   31	42	96	finally
    //   48	93	96	finally
  }
  
  public static LibraryEntry getMusicAllAccessLibraryEntry()
  {
    if (sMusicAllAccessLibraryEntry == null) {
      sMusicAllAccessLibraryEntry = new LibraryEntry(LibraryEntry.UNKNOWN_ACCOUNT, AccountLibrary.getLibraryIdFromBackend(2), 2, (String)G.musicAllAccessSubscriptionBackendDocid.get(), 15, 1);
    }
    return sMusicAllAccessLibraryEntry;
  }
  
  public static int getOwnedPurchaseOfferType(Document paramDocument, Library paramLibrary)
  {
    return getOwnedPurchaseOfferType(paramDocument.getFullDocid(), paramLibrary);
  }
  
  private static int getOwnedPurchaseOfferType(Common.Docid paramDocid, Library paramLibrary)
  {
    if (isOfferOwned(paramDocid, paramLibrary, 1)) {
      return 1;
    }
    if (isOfferOwned(paramDocid, paramLibrary, 7)) {
      return 7;
    }
    return -1;
  }
  
  public static Account getOwnerWithCurrentAccount(Document paramDocument, Libraries paramLibraries, Account paramAccount)
  {
    if (isOwned(paramDocument, paramLibraries.getAccountLibrary(paramAccount))) {
      return paramAccount;
    }
    if (paramDocument.mDocument.docType == 1) {
      return getFirstOwner(paramDocument, paramLibraries);
    }
    return null;
  }
  
  public static Account getOwnerWithCurrentAccount(List<Document> paramList, Libraries paramLibraries, Account paramAccount)
  {
    int i = paramList.size();
    for (int j = 0; j < i; j++)
    {
      Account localAccount = getOwnerWithCurrentAccount((Document)paramList.get(j), paramLibraries, paramAccount);
      if (localAccount != null) {
        return localAccount;
      }
    }
    return null;
  }
  
  public static boolean isAvailable(Document paramDocument, DfeToc paramDfeToc, Library paramLibrary)
  {
    boolean bool;
    if (paramDocument.mDocument.backendId != 0) {
      if (paramDfeToc != null)
      {
        if (paramDfeToc.getCorpus(paramDocument.mDocument.backendId) == null)
        {
          Object[] arrayOfObject3 = new Object[1];
          arrayOfObject3[0] = paramDocument.mDocument.docid;
          FinskyLog.d("Corpus for %s is not available.", arrayOfObject3);
          bool = false;
          return bool;
        }
      }
      else if (paramDocument.mDocument.backendId != 3) {
        return false;
      }
    }
    int i = paramDocument.getAvailabilityRestriction();
    if (i == 1)
    {
      bool = true;
      label84:
      if (!bool) {
        if ((paramDocument.mDocument.availability == null) || (!paramDocument.mDocument.availability.availableIfOwned)) {
          break label209;
        }
      }
    }
    label209:
    for (int j = 1;; j = 0)
    {
      if ((j != 0) && (isOwned(paramDocument, paramLibrary)))
      {
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = paramDocument.mDocument.docid;
        arrayOfObject2[1] = Integer.valueOf(i);
        FinskyLog.d("%s available because owned, overriding [restriction=%d].", arrayOfObject2);
        bool = true;
      }
      if (bool) {
        break;
      }
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = paramDocument.mDocument.docid;
      arrayOfObject1[1] = Integer.valueOf(i);
      FinskyLog.d("%s not available [restriction=%d].", arrayOfObject1);
      return bool;
      bool = false;
      break label84;
    }
  }
  
  public static boolean isOfferOwned(Document paramDocument, Library paramLibrary, int paramInt)
  {
    try
    {
      boolean bool = isOfferOwned(paramDocument.getFullDocid(), paramLibrary, paramInt);
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public static boolean isOfferOwned(Common.Docid paramDocid, Library paramLibrary, int paramInt)
  {
    LibraryEntry localLibraryEntry = getLibraryEntryForOffer(paramDocid, paramLibrary, paramInt);
    if (localLibraryEntry == null) {}
    for (;;)
    {
      return false;
      if (System.currentTimeMillis() >= localLibraryEntry.mValidUntilTimestampMs) {}
      for (int i = 1; i == 0; i = 0) {
        return true;
      }
    }
  }
  
  public static boolean isOwned(Document paramDocument, Library paramLibrary)
  {
    return isOwned(paramDocument.getFullDocid(), paramLibrary);
  }
  
  public static boolean isOwned(Common.Docid paramDocid, Library paramLibrary)
  {
    if (isOfferOwned(paramDocid, paramLibrary, 1)) {}
    for (;;)
    {
      return true;
      if ((paramDocid.backend == 4) || (paramDocid.backend == 1)) {}
      for (int i = 1; ((i == 0) || (!isOfferOwned(paramDocid, paramLibrary, 3))) && ((paramDocid.backend != 4) || ((!isOfferOwned(paramDocid, paramLibrary, 7)) && (!isOfferOwned(paramDocid, paramLibrary, 4)))); i = 0) {
        return false;
      }
    }
  }
  
  public static boolean isPreordered(Document paramDocument, Library paramLibrary)
  {
    int i = getOwnedPurchaseOfferType(paramDocument, paramLibrary);
    if (i == -1) {}
    Common.Offer localOffer;
    do
    {
      LibraryEntry localLibraryEntry;
      do
      {
        return false;
        String str = AccountLibrary.getLibraryIdFromBackend(paramDocument.mDocument.backendId);
        localLibraryEntry = paramLibrary.get(LibraryEntry.fromDocument(LibraryEntry.UNKNOWN_ACCOUNT, str, paramDocument, i));
      } while ((localLibraryEntry == null) || (!localLibraryEntry.mPreordered));
      localOffer = paramDocument.getOffer(i);
    } while ((localOffer != null) && (!Document.isPreorderOffer(localOffer)));
    return true;
  }
  
  public static boolean isPreorderedThroughBundle(Document paramDocument, Library paramLibrary)
  {
    return getBundlePreorderForMovie(paramDocument, paramLibrary) != null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.LibraryUtils
 * JD-Core Version:    0.7.0.1
 */