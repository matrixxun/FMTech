package com.google.android.finsky.billing;

import android.text.TextUtils;
import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.billing.lightpurchase.PurchaseParams;
import com.google.android.finsky.protos.Common.Offer;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.PlayAccountProto.BrokerRequiredDocuments;
import com.google.android.finsky.protos.PlayAccountProto.CachedAcceptedLegalDocuments;
import com.google.android.finsky.protos.PlayAccountProto.CachedPaymentsLegalDocument;
import com.google.android.finsky.protos.PlayAccountProto.CachedPlayAccountInstrument;
import com.google.android.finsky.protos.PlayAccountProto.PlayAccountGlobalPurchaseCache;
import com.google.android.finsky.protos.Purchase.ClientCart;
import com.google.android.finsky.protos.PurchaseDetails;
import com.google.android.finsky.utils.ArrayUtils.ArrayAsList;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public final class InstantPurchaseUtils
{
  public static PlayAccountProto.BrokerRequiredDocuments getBrokerRequiredDocuments(PurchaseDetails paramPurchaseDetails, PlayAccountProto.PlayAccountGlobalPurchaseCache paramPlayAccountGlobalPurchaseCache)
  {
    long l = paramPurchaseDetails.legalDocumentBrokerId;
    for (PlayAccountProto.BrokerRequiredDocuments localBrokerRequiredDocuments : paramPlayAccountGlobalPurchaseCache.brokerRequiredDocuments) {
      if (localBrokerRequiredDocuments.brokerGaiaId == l) {
        return localBrokerRequiredDocuments;
      }
    }
    return null;
  }
  
  public static Document getDocument(PurchaseParams paramPurchaseParams, Cache paramCache)
  {
    if (paramPurchaseParams.document != null) {
      return paramPurchaseParams.document;
    }
    Cache.Entry localEntry = paramCache.get("InstantPurchaseDocument-" + paramPurchaseParams.docidStr);
    if ((localEntry == null) || (localEntry.isExpired())) {
      return null;
    }
    try
    {
      byte[] arrayOfByte = localEntry.data;
      Document localDocument = new Document((DocV2)MessageNano.mergeFrom$1ec43da(new DocV2(), arrayOfByte, arrayOfByte.length));
      return localDocument;
    }
    catch (InvalidProtocolBufferNanoException localInvalidProtocolBufferNanoException)
    {
      throw new RuntimeException(localInvalidProtocolBufferNanoException);
    }
  }
  
  public static PlayAccountProto.CachedPaymentsLegalDocument[] getPaymentsLegalDocumentsToAccept(PlayAccountProto.CachedAcceptedLegalDocuments[] paramArrayOfCachedAcceptedLegalDocuments, PlayAccountProto.BrokerRequiredDocuments paramBrokerRequiredDocuments)
  {
    if ((paramBrokerRequiredDocuments == null) || (paramBrokerRequiredDocuments.legalDocument == null) || (paramBrokerRequiredDocuments.legalDocument.length == 0)) {
      return PlayAccountProto.CachedPaymentsLegalDocument.emptyArray();
    }
    ArrayList localArrayList1 = new ArrayList();
    int i2;
    PlayAccountProto.CachedPaymentsLegalDocument[] arrayOfCachedPaymentsLegalDocument2;
    Object localObject;
    if (paramArrayOfCachedAcceptedLegalDocuments != null)
    {
      int i1 = paramArrayOfCachedAcceptedLegalDocuments.length;
      i2 = 0;
      if (i2 < i1)
      {
        PlayAccountProto.CachedAcceptedLegalDocuments localCachedAcceptedLegalDocuments = paramArrayOfCachedAcceptedLegalDocuments[i2];
        if (localCachedAcceptedLegalDocuments.brokerGaiaId != paramBrokerRequiredDocuments.brokerGaiaId) {
          break label211;
        }
        arrayOfCachedPaymentsLegalDocument2 = localCachedAcceptedLegalDocuments.paymentsLegalDocument;
        if ((arrayOfCachedPaymentsLegalDocument2 != null) && (arrayOfCachedPaymentsLegalDocument2.length != 0)) {
          break label197;
        }
        localObject = Collections.emptyList();
        label91:
        localArrayList1.addAll((Collection)localObject);
      }
    }
    ArrayList localArrayList2 = new ArrayList();
    PlayAccountProto.CachedPaymentsLegalDocument[] arrayOfCachedPaymentsLegalDocument1 = paramBrokerRequiredDocuments.legalDocument;
    int i = arrayOfCachedPaymentsLegalDocument1.length;
    int j = 0;
    label120:
    if (j < i)
    {
      PlayAccountProto.CachedPaymentsLegalDocument localCachedPaymentsLegalDocument = arrayOfCachedPaymentsLegalDocument1[j];
      for (int k = 0;; k++)
      {
        int m = localArrayList1.size();
        int n = 0;
        if (k < m)
        {
          if (localCachedPaymentsLegalDocument.externalLegalDocumentId.equals(((PlayAccountProto.CachedPaymentsLegalDocument)localArrayList1.get(k)).externalLegalDocumentId)) {
            n = 1;
          }
        }
        else
        {
          if (n == 0) {
            localArrayList2.add(localCachedPaymentsLegalDocument);
          }
          j++;
          break label120;
          localObject = new ArrayUtils.ArrayAsList(arrayOfCachedPaymentsLegalDocument2);
          break label91;
          i2++;
          break;
        }
      }
    }
    label197:
    label211:
    return (PlayAccountProto.CachedPaymentsLegalDocument[])localArrayList2.toArray(new PlayAccountProto.CachedPaymentsLegalDocument[localArrayList2.size()]);
  }
  
  public static boolean hasCurrencyCodeMismatch(PlayAccountProto.CachedPlayAccountInstrument paramCachedPlayAccountInstrument, Common.Offer paramOffer)
  {
    String[] arrayOfString = paramCachedPlayAccountInstrument.purchaseCurrencyCode;
    String str = paramOffer.currencyCode;
    int i = arrayOfString.length;
    int j = 0;
    if (j < i) {
      if (!str.equals(arrayOfString[j])) {}
    }
    for (int k = 1;; k = 0)
    {
      if (k != 0) {
        break label57;
      }
      return true;
      j++;
      break;
    }
    label57:
    return false;
  }
  
  public static final class InstantPurchaseParams
  {
    public Purchase.ClientCart mClientCart;
    public final ArrayList<Integer> mClientDisabledReasons = new ArrayList();
    public boolean mInstrumentSuppressesAuth;
    public PlayAccountProto.CachedPaymentsLegalDocument[] mLegalDocuments;
    public byte[] mServerLogsCookie;
    
    public final void addDisabledReason(int paramInt)
    {
      this.mClientDisabledReasons.add(Integer.valueOf(paramInt));
    }
    
    public final String getClientDisabledReasonsString()
    {
      return TextUtils.join(",", this.mClientDisabledReasons);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.InstantPurchaseUtils
 * JD-Core Version:    0.7.0.1
 */