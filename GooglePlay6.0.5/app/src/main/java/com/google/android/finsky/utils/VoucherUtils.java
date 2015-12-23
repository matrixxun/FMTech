package com.google.android.finsky.utils;

import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Library;
import com.google.android.finsky.library.LibraryEntry;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.VoucherInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public final class VoucherUtils
{
  private static LibraryEntry sLibraryEntryForVoucherOwnership;
  
  public static Collection<String> getVoucherIds(AccountLibrary paramAccountLibrary)
  {
    Object localObject = null;
    Iterator localIterator = paramAccountLibrary.getLibrary(AccountLibrary.LIBRARY_ID_COMMERCE).iterator();
    while (localIterator.hasNext())
    {
      LibraryEntry localLibraryEntry = (LibraryEntry)localIterator.next();
      if (localLibraryEntry.mDocType == 29)
      {
        if (localObject == null) {
          localObject = new ArrayList();
        }
        ((ArrayList)localObject).add(localLibraryEntry.mDocId);
      }
    }
    if (localObject == null) {
      localObject = Collections.emptySet();
    }
    return localObject;
  }
  
  public static boolean hasApplicableVouchers(Document paramDocument, AccountLibrary paramAccountLibrary)
  {
    int k;
    if (paramDocument.hasVouchers())
    {
      VoucherInfo[] arrayOfVoucherInfo = paramDocument.getVouchers();
      int j = arrayOfVoucherInfo.length;
      k = 0;
      if (k < j) {
        if (!isVoucherOwned(arrayOfVoucherInfo[k].doc.backendDocid, paramAccountLibrary)) {}
      }
    }
    for (int i = 1;; i = 0)
    {
      if ((i == 0) || (LibraryUtils.isOwned(paramDocument, paramAccountLibrary))) {
        break label70;
      }
      return true;
      k++;
      break;
    }
    label70:
    return false;
  }
  
  public static boolean hasVouchers(AccountLibrary paramAccountLibrary)
  {
    Iterator localIterator = paramAccountLibrary.getLibrary(AccountLibrary.LIBRARY_ID_COMMERCE).iterator();
    while (localIterator.hasNext()) {
      if (((LibraryEntry)localIterator.next()).mDocType == 29) {
        return true;
      }
    }
    return false;
  }
  
  /* Error */
  public static boolean isVoucherOwned(String paramString, Library paramLibrary)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: getstatic 94	com/google/android/finsky/utils/VoucherUtils:sLibraryEntryForVoucherOwnership	Lcom/google/android/finsky/library/LibraryEntry;
    //   6: ifnonnull +40 -> 46
    //   9: new 36	com/google/android/finsky/library/LibraryEntry
    //   12: dup
    //   13: getstatic 97	com/google/android/finsky/library/LibraryEntry:UNKNOWN_ACCOUNT	Ljava/lang/String;
    //   16: getstatic 14	com/google/android/finsky/library/AccountLibrary:LIBRARY_ID_COMMERCE	Ljava/lang/String;
    //   19: bipush 10
    //   21: aload_0
    //   22: bipush 29
    //   24: iconst_1
    //   25: invokespecial 100	com/google/android/finsky/library/LibraryEntry:<init>	(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;II)V
    //   28: putstatic 94	com/google/android/finsky/utils/VoucherUtils:sLibraryEntryForVoucherOwnership	Lcom/google/android/finsky/library/LibraryEntry;
    //   31: aload_1
    //   32: getstatic 94	com/google/android/finsky/utils/VoucherUtils:sLibraryEntryForVoucherOwnership	Lcom/google/android/finsky/library/LibraryEntry;
    //   35: invokeinterface 104 2 0
    //   40: istore_3
    //   41: ldc 2
    //   43: monitorexit
    //   44: iload_3
    //   45: ireturn
    //   46: getstatic 94	com/google/android/finsky/utils/VoucherUtils:sLibraryEntryForVoucherOwnership	Lcom/google/android/finsky/library/LibraryEntry;
    //   49: aload_0
    //   50: putfield 49	com/google/android/finsky/library/LibraryEntry:mDocId	Ljava/lang/String;
    //   53: goto -22 -> 31
    //   56: astore_2
    //   57: ldc 2
    //   59: monitorexit
    //   60: aload_2
    //   61: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	62	0	paramString	String
    //   0	62	1	paramLibrary	Library
    //   56	5	2	localObject	Object
    //   40	5	3	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   3	31	56	finally
    //   31	41	56	finally
    //   46	53	56	finally
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.VoucherUtils
 * JD-Core Version:    0.7.0.1
 */