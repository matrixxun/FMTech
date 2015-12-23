package com.android.i18n.addressinput;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public final class FormController
{
  private static final AddressField[] ADDRESS_HIERARCHY;
  private static final LookupKey ROOT_KEY;
  public String mCurrentCountry;
  private ClientData mIntegratedData;
  String mLanguageCode;
  
  static
  {
    AddressData localAddressData = new AddressData.Builder().build();
    ROOT_KEY = new LookupKey.Builder(LookupKey.KeyType.DATA).setAddressData(localAddressData).build();
    AddressField[] arrayOfAddressField = new AddressField[4];
    arrayOfAddressField[0] = AddressField.COUNTRY;
    arrayOfAddressField[1] = AddressField.ADMIN_AREA;
    arrayOfAddressField[2] = AddressField.LOCALITY;
    arrayOfAddressField[3] = AddressField.DEPENDENT_LOCALITY;
    ADDRESS_HIERARCHY = arrayOfAddressField;
  }
  
  FormController(ClientData paramClientData, String paramString1, String paramString2)
  {
    Util.checkNotNull(paramClientData, "null data not allowed");
    this.mLanguageCode = paramString1;
    this.mCurrentCountry = paramString2;
    LookupKey localLookupKey = getDataKeyFor(new AddressData.Builder().setCountry("ZZ").build());
    Util.checkNotNull(paramClientData.getDefaultData(localLookupKey.toString()), "require data for default country key: " + localLookupKey);
    this.mIntegratedData = paramClientData;
  }
  
  static LookupKey getDataKeyFor(AddressData paramAddressData)
  {
    return new LookupKey.Builder(LookupKey.KeyType.DATA).setAddressData(paramAddressData).build();
  }
  
  private String getSubKey(LookupKey paramLookupKey, String paramString)
  {
    Iterator localIterator = getRegionData(paramLookupKey).iterator();
    while (localIterator.hasNext())
    {
      RegionData localRegionData = (RegionData)localIterator.next();
      if (localRegionData.isValidName(paramString)) {
        return localRegionData.mKey;
      }
    }
    return null;
  }
  
  private LookupKey normalizeLookupKey(LookupKey paramLookupKey)
  {
    Util.checkNotNull(paramLookupKey);
    if (paramLookupKey.mKeyType != LookupKey.KeyType.DATA) {
      throw new RuntimeException("Only DATA keyType is supported");
    }
    String[] arrayOfString1 = paramLookupKey.toString().split("/");
    if (arrayOfString1.length < 2) {
      return paramLookupKey;
    }
    StringBuilder localStringBuilder = new StringBuilder(arrayOfString1[0]);
    for (int i = 1; i < arrayOfString1.length; i++)
    {
      String str1 = null;
      if (i == 1)
      {
        boolean bool = arrayOfString1[i].contains("--");
        str1 = null;
        if (bool)
        {
          String[] arrayOfString2 = arrayOfString1[i].split("--");
          arrayOfString1[i] = arrayOfString2[0];
          str1 = arrayOfString2[1];
        }
      }
      String str2 = getSubKey(new LookupKey.Builder(localStringBuilder.toString()).build(), arrayOfString1[i]);
      if (str2 == null) {
        while (i < arrayOfString1.length)
        {
          localStringBuilder.append("/").append(arrayOfString1[i]);
          i++;
        }
      }
      localStringBuilder.append("/").append(str2);
      if (str1 != null) {
        localStringBuilder.append("--").append(str1);
      }
    }
    return new LookupKey.Builder(localStringBuilder.toString()).build();
  }
  
  private void requestDataRecursively(final LookupKey paramLookupKey, final Queue<String> paramQueue, final DataLoadListener paramDataLoadListener)
  {
    Util.checkNotNull(paramLookupKey, "Null key not allowed");
    Util.checkNotNull(paramQueue, "Null subkeys not allowed");
    ClientData localClientData = this.mIntegratedData;
    DataLoadListener local1 = new DataLoadListener()
    {
      public final void dataLoadingEnd()
      {
        List localList = FormController.this.getRegionData(paramLookupKey);
        if (localList.isEmpty())
        {
          if (paramDataLoadListener != null) {
            paramDataLoadListener.dataLoadingEnd();
          }
          return;
        }
        if (paramQueue.size() > 0)
        {
          String str2 = (String)paramQueue.remove();
          Iterator localIterator = localList.iterator();
          while (localIterator.hasNext())
          {
            RegionData localRegionData = (RegionData)localIterator.next();
            if (localRegionData.isValidName(str2))
            {
              LookupKey localLookupKey2 = FormController.access$000(FormController.this, paramLookupKey, localRegionData.mKey);
              FormController.this.requestDataRecursively(localLookupKey2, paramQueue, paramDataLoadListener);
              return;
            }
          }
        }
        String str1 = ((RegionData)localList.get(0)).mKey;
        LookupKey localLookupKey1 = FormController.access$000(FormController.this, paramLookupKey, str1);
        LinkedList localLinkedList = new LinkedList();
        FormController.this.requestDataRecursively(localLookupKey1, localLinkedList, paramDataLoadListener);
      }
    };
    Util.checkNotNull(paramLookupKey, "Null lookup key not allowed");
    JsoMap localJsoMap = (JsoMap)localClientData.mBootstrapMap.get(paramLookupKey.toString());
    localClientData.mCacheData.fetchDynamicData(paramLookupKey, localJsoMap, local1);
  }
  
  private static String[] splitData(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return new String[0];
    }
    return paramString.split("~");
  }
  
  final List<RegionData> getRegionData(LookupKey paramLookupKey)
  {
    if (paramLookupKey.mKeyType == LookupKey.KeyType.EXAMPLES) {
      throw new RuntimeException("example key not allowed for getting region data");
    }
    Util.checkNotNull(paramLookupKey, "null regionKey not allowed");
    LookupKey localLookupKey = normalizeLookupKey(paramLookupKey);
    ArrayList localArrayList = new ArrayList();
    if (localLookupKey.equals(ROOT_KEY))
    {
      String[] arrayOfString3 = splitData(this.mIntegratedData.getDefaultData(localLookupKey.toString()).get(AddressDataKey.COUNTRIES));
      for (int j = 0; j < arrayOfString3.length; j++) {
        localArrayList.add(new RegionData.Builder().setKey(arrayOfString3[j]).setName(arrayOfString3[j]).build());
      }
    }
    AddressVerificationNodeData localAddressVerificationNodeData = this.mIntegratedData.get(localLookupKey.toString());
    if (localAddressVerificationNodeData != null)
    {
      String[] arrayOfString1 = splitData(localAddressVerificationNodeData.get(AddressDataKey.SUB_KEYS));
      LookupKey.ScriptType localScriptType;
      String[] arrayOfString2;
      label196:
      int i;
      label199:
      RegionData.Builder localBuilder;
      if ((this.mLanguageCode != null) && (Util.isExplicitLatinScript(this.mLanguageCode)))
      {
        localScriptType = LookupKey.ScriptType.LATIN;
        if (localScriptType != LookupKey.ScriptType.LOCAL) {
          break label270;
        }
        arrayOfString2 = splitData(localAddressVerificationNodeData.get(AddressDataKey.SUB_NAMES));
        i = 0;
        if (i >= arrayOfString1.length) {
          return localArrayList;
        }
        localBuilder = new RegionData.Builder().setKey(arrayOfString1[i]);
        if (i >= arrayOfString2.length) {
          break label286;
        }
      }
      label270:
      label286:
      for (String str = arrayOfString2[i];; str = arrayOfString1[i])
      {
        localArrayList.add(localBuilder.setName(str).build());
        i++;
        break label199;
        localScriptType = LookupKey.ScriptType.LOCAL;
        break;
        arrayOfString2 = splitData(localAddressVerificationNodeData.get(AddressDataKey.SUB_LNAMES));
        break label196;
      }
    }
    return localArrayList;
  }
  
  final boolean isDefaultLanguage(String paramString)
  {
    if (paramString == null) {}
    LookupKey localLookupKey;
    do
    {
      return true;
      localLookupKey = getDataKeyFor(new AddressData.Builder().setCountry(this.mCurrentCountry).build());
    } while ((Util.trimToNull(this.mIntegratedData.getDefaultData(localLookupKey.toString()).get(AddressDataKey.LANG)) == null) || (Util.getLanguageSubtag(paramString).equals(Util.getLanguageSubtag(paramString))));
    return false;
  }
  
  final void requestDataForAddress(AddressData paramAddressData, DataLoadListener paramDataLoadListener)
  {
    Util.checkNotNull(paramAddressData.mPostalCountry, "null country not allowed");
    LinkedList localLinkedList = new LinkedList();
    AddressField[] arrayOfAddressField = ADDRESS_HIERARCHY;
    int i = arrayOfAddressField.length;
    for (int j = 0; j < i; j++)
    {
      String str = paramAddressData.getFieldValue(arrayOfAddressField[j]);
      if (str == null) {
        break;
      }
      localLinkedList.add(str);
    }
    if (localLinkedList.size() == 0) {
      throw new RuntimeException("Need at least country level info");
    }
    requestDataRecursively(ROOT_KEY, localLinkedList, paramDataLoadListener);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.i18n.addressinput.FormController
 * JD-Core Version:    0.7.0.1
 */