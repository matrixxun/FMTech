package com.google.android.finsky.utils;

import android.content.res.Resources;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.Library;
import com.google.android.finsky.library.LibraryInAppSubscriptionEntry;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.Common.Docid;
import com.google.android.finsky.protos.Common.Offer;
import com.google.android.finsky.protos.DocV2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class DocUtils
{
  private static final Map<String, Integer> PREFIX_TO_BACKEND;
  
  static
  {
    HashMap localHashMap = new HashMap();
    PREFIX_TO_BACKEND = localHashMap;
    localHashMap.put("app", Integer.valueOf(3));
    PREFIX_TO_BACKEND.put("album", Integer.valueOf(2));
    PREFIX_TO_BACKEND.put("artist", Integer.valueOf(2));
    PREFIX_TO_BACKEND.put("book", Integer.valueOf(1));
    PREFIX_TO_BACKEND.put("device", Integer.valueOf(5));
    PREFIX_TO_BACKEND.put("magazine", Integer.valueOf(6));
    PREFIX_TO_BACKEND.put("magazineissue", Integer.valueOf(6));
    PREFIX_TO_BACKEND.put("newsedition", Integer.valueOf(6));
    PREFIX_TO_BACKEND.put("newsissue", Integer.valueOf(6));
    PREFIX_TO_BACKEND.put("movie", Integer.valueOf(4));
    PREFIX_TO_BACKEND.put("song", Integer.valueOf(2));
    PREFIX_TO_BACKEND.put("tvepisode", Integer.valueOf(7));
    PREFIX_TO_BACKEND.put("tvseason", Integer.valueOf(7));
    PREFIX_TO_BACKEND.put("tvshow", Integer.valueOf(7));
  }
  
  public static boolean canRate(Libraries paramLibraries, Document paramDocument)
  {
    boolean bool = true;
    switch (paramDocument.mDocument.docType)
    {
    default: 
      bool = false;
    }
    do
    {
      return bool;
    } while (!paramLibraries.getAppOwners(paramDocument.getAppDetails().packageName).isEmpty());
    return false;
  }
  
  public static Common.Docid createDocid(int paramInt1, int paramInt2, String paramString)
  {
    Common.Docid localDocid = new Common.Docid();
    localDocid.backend = paramInt1;
    localDocid.hasBackend = true;
    localDocid.type = paramInt2;
    localDocid.hasType = true;
    localDocid.backendDocid = paramString;
    localDocid.hasBackendDocid = true;
    return localDocid;
  }
  
  public static int docidToBackend(String paramString)
  {
    int i = paramString.length();
    if (i <= 0) {
      return -1;
    }
    for (int j = 0;; j++)
    {
      if (j >= i) {
        break label76;
      }
      int k = paramString.charAt(j);
      if ((k == 45) || (k == 58))
      {
        String str = paramString.substring(0, j);
        Integer localInteger = (Integer)PREFIX_TO_BACKEND.get(str);
        if (localInteger == null) {
          break;
        }
        return localInteger.intValue();
      }
    }
    label76:
    return 3;
  }
  
  private static String extractPackageNameForInApp(String paramString)
  {
    int i = paramString.indexOf(':');
    int j = paramString.indexOf(':', i + 1);
    if ((i > 0) && (i < j) && (j < paramString.length())) {
      return paramString.substring(i + 1, j);
    }
    return null;
  }
  
  public static String extractSkuForInApp(String paramString)
  {
    int i = paramString.lastIndexOf(':');
    if ((i > 0) && (i < paramString.length())) {
      return paramString.substring(i + 1, paramString.length());
    }
    return null;
  }
  
  public static int getAvailabilityRestrictionResourceId(Document paramDocument)
  {
    int i = paramDocument.getAvailabilityRestriction();
    int j = 2131361888;
    switch (i)
    {
    }
    for (;;)
    {
      FinskyLog.d("Item is not available. Reason: " + i, new Object[0]);
      return j;
      j = 2131361885;
      continue;
      j = 2131361884;
      continue;
      j = 2131361886;
      continue;
      if (paramDocument.mDocument.docType == 1)
      {
        j = 2131361890;
      }
      else
      {
        j = 2131361889;
        continue;
        j = 2131361893;
        continue;
        j = 2131361892;
        continue;
        j = 2131361891;
        continue;
        j = 2131361887;
      }
    }
  }
  
  public static String getFormattedFileSizeShortFromBytes(long paramLong, Resources paramResources)
  {
    if (paramLong < 0L) {
      return "";
    }
    long l1 = paramLong;
    long l2 = 0L;
    for (int i = 0; l1 >= 990L; i++)
    {
      l2 = l1 & 0x3FF;
      l1 >>= 10;
    }
    String str;
    switch (i)
    {
    default: 
      str = "";
      if (l1 >= 10L)
      {
        int j = (int)Math.round(l1 + l2 / 1024.0D);
        return j + " " + str;
      }
      break;
    case 0: 
      if (paramResources == null) {}
      for (str = "B";; str = paramResources.getString(2131361914)) {
        break;
      }
    case 1: 
      if (paramResources == null) {}
      for (str = "KB";; str = paramResources.getString(2131362284)) {
        break;
      }
    case 2: 
      if (paramResources == null) {}
      for (str = "MB";; str = paramResources.getString(2131362320)) {
        break;
      }
    case 3: 
      if (paramResources == null) {}
      for (str = "GB";; str = paramResources.getString(2131362191)) {
        break;
      }
    case 4: 
      if (paramResources == null) {}
      for (str = "TB";; str = paramResources.getString(2131362785)) {
        break;
      }
    case 5: 
      if (paramResources == null) {}
      for (str = "PB";; str = paramResources.getString(2131362537)) {
        break;
      }
    case 6: 
      if (paramResources == null) {}
      for (str = "EB";; str = paramResources.getString(2131362136)) {
        break;
      }
    }
    double d = Math.round(10.0D * (l1 + l2 / 1024.0D)) / 10.0D;
    return d + " " + str;
  }
  
  public static Common.Offer getListingOffer(Document paramDocument, DfeToc paramDfeToc, Library paramLibrary)
  {
    if ((paramDocument.mDocument.docType == 16) || (paramDocument.mDocument.docType == 24))
    {
      List localList = getSubscriptions(paramDocument, paramDfeToc, paramLibrary);
      int i = localList.size();
      if (i > 0)
      {
        Common.Offer[] arrayOfOffer1;
        if (i == 1) {
          arrayOfOffer1 = ((Document)localList.get(0)).mDocument.offer;
        }
        for (;;)
        {
          Common.Offer localOffer2 = getLowestPricedOffer(arrayOfOffer1, false, null);
          if (localOffer2 == null) {
            localOffer2 = getLowestPricedOffer(arrayOfOffer1, true, null);
          }
          if (localOffer2 == null) {
            break;
          }
          return localOffer2;
          int j = 0;
          for (int k = 0; k < i; k++) {
            j += ((Document)localList.get(k)).mDocument.offer.length;
          }
          int m = 0;
          arrayOfOffer1 = new Common.Offer[j];
          for (int n = 0; n < i; n++)
          {
            Common.Offer[] arrayOfOffer2 = ((Document)localList.get(n)).mDocument.offer;
            System.arraycopy(arrayOfOffer2, 0, arrayOfOffer1, m, arrayOfOffer2.length);
            m += arrayOfOffer2.length;
          }
        }
      }
      Document localDocument = getMagazineCurrentIssueDocument(paramDocument);
      if (localDocument != null)
      {
        Common.Offer localOffer1 = getMagazineIssueOffer(localDocument, paramDfeToc, paramLibrary, 1);
        if ((localOffer1 != null) && (localOffer1.hasFormattedAmount)) {
          return localOffer1;
        }
      }
      return null;
    }
    return getLowestPricedOffer(paramDocument.mDocument.offer, true, null);
  }
  
  public static Common.Offer getLowestPricedOffer(Common.Offer[] paramArrayOfOffer, boolean paramBoolean)
  {
    return getLowestPricedOffer(paramArrayOfOffer, true, null);
  }
  
  public static Common.Offer getLowestPricedOffer(Common.Offer[] paramArrayOfOffer, boolean paramBoolean, OfferFilter paramOfferFilter)
  {
    Object localObject = null;
    long l1 = 9223372036854775807L;
    int i = paramArrayOfOffer.length;
    for (int j = 0; j < i; j++)
    {
      Common.Offer localOffer = paramArrayOfOffer[j];
      if (localOffer.hasFormattedAmount)
      {
        int k = localOffer.offerType;
        if (((k == 1) || (k == 7) || (k == 3) || (k == 4)) && ((paramOfferFilter == null) || (paramOfferFilter.matches(k))))
        {
          long l2 = localOffer.micros;
          if (((paramBoolean) || (l2 != 0L)) && (l2 < l1))
          {
            l1 = l2;
            localObject = localOffer;
          }
        }
      }
    }
    return localObject;
  }
  
  public static Document getMagazineCurrentIssueDocument(Document paramDocument)
  {
    if ((paramDocument.mDocument.docType != 16) && (paramDocument.mDocument.docType != 24)) {
      throw new IllegalArgumentException("This method should be called only on magazine docs. Passed type " + paramDocument.mDocument.docType);
    }
    if (paramDocument.getChildCount() == 0) {
      return null;
    }
    return paramDocument.getChildAt(0);
  }
  
  public static Common.Offer getMagazineIssueOffer(Document paramDocument, DfeToc paramDfeToc, Library paramLibrary, int paramInt)
  {
    Common.Offer localOffer;
    if (paramDocument == null)
    {
      localOffer = null;
      return localOffer;
    }
    if ((paramDocument.mDocument.docType != 17) && (paramDocument.mDocument.docType != 25)) {
      return null;
    }
    if (LibraryUtils.isAvailable(paramDocument, paramDfeToc, paramLibrary))
    {
      Common.Offer[] arrayOfOffer = paramDocument.mDocument.offer;
      int i = arrayOfOffer.length;
      for (int j = 0;; j++)
      {
        if (j >= i) {
          break label91;
        }
        localOffer = arrayOfOffer[j];
        if (localOffer.offerType == paramInt) {
          break;
        }
      }
    }
    label91:
    return null;
  }
  
  public static String getMovieDocid(String paramString)
  {
    return "movie-" + paramString;
  }
  
  public static String getMusicSubscriptionDocid(int paramInt, String paramString)
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = Integer.valueOf(2);
    arrayOfObject[1] = Integer.valueOf(paramInt);
    arrayOfObject[2] = paramString;
    return String.format("id-%d-%d-%s", arrayOfObject);
  }
  
  public static int getNumberOfValidOffers(Common.Offer[] paramArrayOfOffer)
  {
    int i = 0;
    int j = paramArrayOfOffer.length;
    for (int k = 0; k < j; k++)
    {
      Common.Offer localOffer = paramArrayOfOffer[k];
      if (localOffer.hasFormattedAmount)
      {
        int m = localOffer.offerType;
        if ((m == 1) || (m == 7) || (m == 3) || (m == 4)) {
          i++;
        }
      }
    }
    return i;
  }
  
  private static float getOfferDiscountRatio(Common.Offer paramOffer)
  {
    if (!paramOffer.hasFullPriceMicros) {}
    long l1;
    long l2;
    do
    {
      return 0.0F;
      l1 = paramOffer.fullPriceMicros;
      l2 = l1 - paramOffer.micros;
    } while ((l1 <= 0L) || (l2 <= 0L));
    return (float)l2 / (float)l1;
  }
  
  public static Common.Offer getOfferWithLargestDiscountIfAny(Document paramDocument, DfeToc paramDfeToc, Library paramLibrary)
  {
    if ((paramDocument.mDocument.docType == 16) || (paramDocument.mDocument.docType == 24))
    {
      List localList = getSubscriptions(paramDocument, paramDfeToc, paramLibrary);
      float f1 = 0.0F;
      Object localObject = null;
      int i = localList.size();
      for (int j = 0; j < i; j++)
      {
        Common.Offer localOffer2 = getOfferWithLargestDiscountIfAny(((Document)localList.get(j)).mDocument.offer);
        if (localOffer2 != null)
        {
          float f2 = getOfferDiscountRatio(localOffer2);
          if (f2 > f1)
          {
            f1 = f2;
            localObject = localOffer2;
          }
        }
      }
      Document localDocument = getMagazineCurrentIssueDocument(paramDocument);
      if (localDocument != null)
      {
        Common.Offer localOffer1 = getMagazineIssueOffer(localDocument, paramDfeToc, paramLibrary, 1);
        if ((localOffer1 != null) && (localOffer1.hasFormattedAmount) && (getOfferDiscountRatio(localOffer1) > f1)) {
          localObject = localOffer1;
        }
      }
      return localObject;
    }
    return getOfferWithLargestDiscountIfAny(paramDocument.mDocument.offer);
  }
  
  private static Common.Offer getOfferWithLargestDiscountIfAny(Common.Offer[] paramArrayOfOffer)
  {
    float f1 = 0.0F;
    Object localObject = null;
    int i = paramArrayOfOffer.length;
    for (int j = 0; j < i; j++)
    {
      Common.Offer localOffer = paramArrayOfOffer[j];
      float f2 = getOfferDiscountRatio(localOffer);
      if (f2 > f1)
      {
        f1 = f2;
        localObject = localOffer;
      }
    }
    return localObject;
  }
  
  public static String getPackageNameForInApp(String paramString)
  {
    if (!paramString.startsWith("inapp:")) {
      return null;
    }
    return extractPackageNameForInApp(paramString);
  }
  
  public static String getPackageNameForSubscription(String paramString)
  {
    if (!paramString.startsWith("subs:")) {
      return null;
    }
    return extractPackageNameForInApp(paramString);
  }
  
  public static List<Document> getSubscriptions(Document paramDocument, DfeToc paramDfeToc, Library paramLibrary)
  {
    if (paramDocument.hasSubscriptions())
    {
      localObject = new ArrayList();
      List localList = paramDocument.getSubscriptionsList();
      int i = localList.size();
      for (int j = 0; j < i; j++)
      {
        Document localDocument = (Document)localList.get(j);
        if ((LibraryUtils.isAvailable(localDocument, paramDfeToc, paramLibrary)) && (localDocument.mDocument.offer.length > 0)) {
          ((List)localObject).add(localDocument);
        }
      }
    }
    Object localObject = Collections.emptyList();
    return localObject;
  }
  
  public static boolean hasAutoRenewingSubscriptions(Libraries paramLibraries, String paramString)
  {
    Iterator localIterator = paramLibraries.getAccountLibraries().iterator();
    while (localIterator.hasNext())
    {
      List localList = ((AccountLibrary)localIterator.next()).getSubscriptionPurchasesForPackage(paramString);
      for (int i = 0; i < localList.size(); i++) {
        if (((LibraryInAppSubscriptionEntry)localList.get(i)).isAutoRenewing) {
          return true;
        }
      }
    }
    return false;
  }
  
  public static boolean hasDiscount(Common.Offer paramOffer)
  {
    return getOfferDiscountRatio(paramOffer) > 0.0F;
  }
  
  public static boolean isInAppDocid(Common.Docid paramDocid)
  {
    return (paramDocid.backend == 3) && ((paramDocid.type == 11) || (paramDocid.type == 15));
  }
  
  public static enum OfferFilter
  {
    static
    {
      HIGH_DEF = new OfferFilter("HIGH_DEF", 2);
      PURCHASE_HIGH_DEF = new OfferFilter("PURCHASE_HIGH_DEF", 3);
      RENTAL_HIGH_DEF = new OfferFilter("RENTAL_HIGH_DEF", 4);
      OfferFilter[] arrayOfOfferFilter = new OfferFilter[5];
      arrayOfOfferFilter[0] = PURCHASE;
      arrayOfOfferFilter[1] = RENTAL;
      arrayOfOfferFilter[2] = HIGH_DEF;
      arrayOfOfferFilter[3] = PURCHASE_HIGH_DEF;
      arrayOfOfferFilter[4] = RENTAL_HIGH_DEF;
      $VALUES = arrayOfOfferFilter;
    }
    
    private OfferFilter() {}
    
    public final boolean matches(int paramInt)
    {
      boolean bool;
      switch (DocUtils.1.$SwitchMap$com$google$android$finsky$utils$DocUtils$OfferFilter[ordinal()])
      {
      default: 
        throw new IllegalArgumentException();
      case 1: 
        if (paramInt != 1)
        {
          bool = false;
          if (paramInt != 7) {
            break;
          }
        }
        else
        {
          bool = true;
        }
        break;
      }
      do
      {
        do
        {
          do
          {
            do
            {
              return bool;
              if (paramInt == 3) {
                break;
              }
              bool = false;
            } while (paramInt != 4);
            return true;
            if (paramInt == 7) {
              break;
            }
            bool = false;
          } while (paramInt != 4);
          return true;
          bool = false;
        } while (paramInt != 7);
        return true;
        bool = false;
      } while (paramInt != 4);
      return true;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.DocUtils
 * JD-Core Version:    0.7.0.1
 */