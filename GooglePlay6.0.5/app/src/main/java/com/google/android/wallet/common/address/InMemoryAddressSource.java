package com.google.android.wallet.common.address;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.support.v4.util.SimpleArrayMap;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.wallet.common.util.ArrayUtils;
import com.google.android.wallet.common.util.ProtoUtils;
import com.google.location.country.Postaladdress.PostalAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public abstract class InMemoryAddressSource
  implements AddressSource
{
  ArrayList<Postaladdress.PostalAddress> mAddresses;
  private final Context mContext;
  private final SimpleArrayMap<String, ArrayList<Postaladdress.PostalAddress>> mMergedAddressesByFields;
  private final String mName;
  
  protected InMemoryAddressSource(String paramString, Context paramContext)
  {
    this.mName = paramString;
    this.mContext = paramContext;
    this.mMergedAddressesByFields = new SimpleArrayMap();
  }
  
  private ArrayList<Postaladdress.PostalAddress> getMergedAddressesByRemainingFields(char[] paramArrayOfChar)
  {
    String str;
    if (paramArrayOfChar != null) {
      str = new String(paramArrayOfChar);
    }
    synchronized (this.mMergedAddressesByFields)
    {
      ArrayList localArrayList;
      for (;;)
      {
        localArrayList = (ArrayList)this.mMergedAddressesByFields.get(str);
        if (localArrayList != null) {
          break label128;
        }
        localArrayList = AddressUtils.mergeAddresses(this.mAddresses, paramArrayOfChar);
        int i = 0;
        int j = localArrayList.size();
        while (i < j)
        {
          Postaladdress.PostalAddress localPostalAddress = (Postaladdress.PostalAddress)localArrayList.get(i);
          if (localPostalAddress.addressLine.length == 1) {
            localPostalAddress.addressLine = ((String[])ArrayUtils.appendToArray(localPostalAddress.addressLine, ""));
          }
          i++;
        }
        str = "*";
      }
      this.mMergedAddressesByFields.put(str, localArrayList);
      label128:
      return localArrayList;
    }
  }
  
  /* Error */
  private void initializeIfNecessary()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 45	com/google/android/wallet/common/address/InMemoryAddressSource:mAddresses	Ljava/util/ArrayList;
    //   6: astore_2
    //   7: aload_2
    //   8: ifnonnull +28 -> 36
    //   11: aload_0
    //   12: invokevirtual 86	com/google/android/wallet/common/address/InMemoryAddressSource:getAddresses	()Ljava/util/List;
    //   15: astore 6
    //   17: aload 6
    //   19: astore 5
    //   21: aload 5
    //   23: ifnull +34 -> 57
    //   26: aload_0
    //   27: aload 5
    //   29: aconst_null
    //   30: invokestatic 51	com/google/android/wallet/common/address/AddressUtils:mergeAddresses	(Ljava/util/Collection;[C)Ljava/util/ArrayList;
    //   33: putfield 45	com/google/android/wallet/common/address/InMemoryAddressSource:mAddresses	Ljava/util/ArrayList;
    //   36: aload_0
    //   37: monitorexit
    //   38: return
    //   39: astore_3
    //   40: aload_0
    //   41: getfield 23	com/google/android/wallet/common/address/InMemoryAddressSource:mName	Ljava/lang/String;
    //   44: ldc 88
    //   46: aload_3
    //   47: invokestatic 94	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   50: pop
    //   51: aconst_null
    //   52: astore 5
    //   54: goto -33 -> 21
    //   57: aload_0
    //   58: new 43	java/util/ArrayList
    //   61: dup
    //   62: invokespecial 95	java/util/ArrayList:<init>	()V
    //   65: putfield 45	com/google/android/wallet/common/address/InMemoryAddressSource:mAddresses	Ljava/util/ArrayList;
    //   68: goto -32 -> 36
    //   71: astore_1
    //   72: aload_0
    //   73: monitorexit
    //   74: aload_1
    //   75: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	76	0	this	InMemoryAddressSource
    //   71	4	1	localObject	Object
    //   6	2	2	localArrayList	ArrayList
    //   39	8	3	localThrowable	Throwable
    //   19	34	5	localList1	List
    //   15	3	6	localList2	List
    // Exception table:
    //   from	to	target	type
    //   11	17	39	java/lang/Throwable
    //   2	7	71	finally
    //   11	17	71	finally
    //   26	36	71	finally
    //   40	51	71	finally
    //   57	68	71	finally
  }
  
  public final Postaladdress.PostalAddress getAddress(String paramString1, String paramString2)
  {
    throw new UnsupportedOperationException(this.mName + " does not use reference identifiers");
  }
  
  protected abstract List<Postaladdress.PostalAddress> getAddresses()
    throws Throwable;
  
  public final List<AddressSourceResult> getAddresses(CharSequence paramCharSequence, char paramChar, char[] paramArrayOfChar, int paramInt, String paramString)
  {
    if ((paramCharSequence == null) || (paramCharSequence.length() < getThresholdForField$132f9b())) {
      return Collections.emptyList();
    }
    Locale localLocale = this.mContext.getResources().getConfiguration().locale;
    if ((Build.VERSION.SDK_INT >= 17) && (TextUtils.getLayoutDirectionFromLocale(localLocale) == 1)) {}
    for (int i = 1; i != 0; i = 0) {
      return Collections.emptyList();
    }
    initializeIfNecessary();
    if (this.mAddresses.isEmpty()) {
      return Collections.emptyList();
    }
    ArrayList localArrayList1 = new ArrayList();
    String str1 = this.mName;
    String str2 = RegionCode.toCountryCode(paramInt);
    ArrayList localArrayList2 = getMergedAddressesByRemainingFields(paramArrayOfChar);
    TreeMap localTreeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
    int j;
    int k;
    label149:
    Postaladdress.PostalAddress localPostalAddress;
    String str3;
    int i1;
    label205:
    SpannableString localSpannableString1;
    int i2;
    int i3;
    if (paramChar == 'N')
    {
      j = 1;
      k = 0;
      int m = localArrayList2.size();
      if (k >= m) {
        break label530;
      }
      localPostalAddress = (Postaladdress.PostalAddress)localArrayList2.get(k);
      if (localPostalAddress != null)
      {
        str3 = AddressFormatter.formatAddressValue(localPostalAddress, paramChar);
        int n = SourceUtils.startsWithOrContainsWordPrefixIndex(str3, paramCharSequence);
        if (n != -1)
        {
          if (!TextUtils.isEmpty(paramCharSequence)) {
            break label317;
          }
          i1 = 0;
          localSpannableString1 = SourceUtils.createSpannableForMatchedSubstrings(Collections.singletonList(Pair.create(Integer.valueOf(n), Integer.valueOf(i1))), str3);
          i2 = 0;
          if (paramInt == 0) {
            break label367;
          }
          if (TextUtils.isEmpty(localPostalAddress.countryNameCode)) {
            break label328;
          }
          i3 = RegionCode.safeToRegionCode(localPostalAddress.countryNameCode);
          label257:
          if ((i3 == 0) || (i3 == 858)) {
            break label336;
          }
          i2 = 0;
          if (i3 == paramInt) {
            break label367;
          }
          if ((j != 0) && (!localTreeMap.containsKey(str3))) {
            localTreeMap.put(str3, localSpannableString1);
          }
        }
      }
    }
    for (;;)
    {
      k++;
      break label149;
      j = 0;
      break;
      label317:
      i1 = paramCharSequence.length();
      break label205;
      label328:
      i3 = 858;
      break label257;
      label336:
      i2 = 0;
      if (paramInt != 858)
      {
        localPostalAddress = (Postaladdress.PostalAddress)ProtoUtils.copyFrom(localPostalAddress);
        localPostalAddress.countryNameCode = str2;
        i2 = 1;
      }
      label367:
      if (TextUtils.isEmpty(paramString)) {
        break label439;
      }
      if (TextUtils.isEmpty(localPostalAddress.languageCode)) {
        break label427;
      }
      if (AddressUtils.isSameLanguage(localPostalAddress.languageCode, paramString)) {
        break label439;
      }
      if ((j != 0) && (!localTreeMap.containsKey(str3))) {
        localTreeMap.put(str3, localSpannableString1);
      }
    }
    label427:
    if (i2 != 0) {}
    for (;;)
    {
      localPostalAddress.languageCode = paramString;
      label439:
      localTreeMap.put(str3, null);
      String str4 = AddressFormatter.formatAddress(localPostalAddress, AddressSourceResult.NEW_LINE_REPLACEMENT_SEPARATOR, paramArrayOfChar, AddressSourceResult.EXCLUDED_ADDRESS_FIELDS);
      SpannableString localSpannableString2 = SourceUtils.createSpannableForMatchedSubstrings(Collections.singletonList(Pair.create(Integer.valueOf(SourceUtils.startsWithOrContainsWordPrefixIndex(str4, paramCharSequence)), Integer.valueOf(i1))), str4);
      AddressSourceResult localAddressSourceResult2 = new AddressSourceResult(str3, localPostalAddress, localSpannableString2, str1);
      localArrayList1.add(localAddressSourceResult2);
      break;
      localPostalAddress = (Postaladdress.PostalAddress)ProtoUtils.copyFrom(localPostalAddress);
    }
    label530:
    Iterator localIterator = localTreeMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      if (localEntry.getValue() != null)
      {
        AddressSourceResult localAddressSourceResult1 = new AddressSourceResult((String)localEntry.getKey(), (CharSequence)localEntry.getValue(), str1, null);
        localArrayList1.add(localAddressSourceResult1);
      }
    }
    Collections.sort(localArrayList1, AddressSourceResult.DEFAULT_COMPARATOR);
    return localArrayList1;
  }
  
  public final String getName()
  {
    return this.mName;
  }
  
  protected abstract int getThresholdForField$132f9b();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.address.InMemoryAddressSource
 * JD-Core Version:    0.7.0.1
 */