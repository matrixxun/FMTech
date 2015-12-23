package com.android.i18n.addressinput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public final class FormatInterpreter
{
  private final String mDefaultFormat;
  private final FormOptions mFormOptions;
  
  FormatInterpreter(FormOptions paramFormOptions)
  {
    Util.checkNotNull(RegionDataConstants.getCountryFormatMap(), "null country name map not allowed");
    Util.checkNotNull(paramFormOptions);
    this.mFormOptions = paramFormOptions;
    this.mDefaultFormat = getJsonValue("ZZ", AddressDataKey.FMT);
    Util.checkNotNull(this.mDefaultFormat, "null default format not allowed");
  }
  
  private String getJsonValue(String paramString, AddressDataKey paramAddressDataKey)
  {
    Util.checkNotNull(paramString);
    String str1 = (String)RegionDataConstants.getCountryFormatMap().get(paramString);
    if (str1 == null) {
      return this.mDefaultFormat;
    }
    try
    {
      JSONObject localJSONObject = new JSONObject(new JSONTokener(str1));
      if (!localJSONObject.has(paramAddressDataKey.name().toLowerCase())) {
        return this.mDefaultFormat;
      }
      String str2 = localJSONObject.getString(paramAddressDataKey.name().toLowerCase());
      return str2;
    }
    catch (JSONException localJSONException)
    {
      throw new RuntimeException("Invalid json for region code " + paramString + ": " + str1);
    }
  }
  
  private void overrideFieldOrder(String paramString, List<AddressField> paramList)
  {
    if (this.mFormOptions.getCustomFieldOrder(paramString) == null) {}
    for (;;)
    {
      return;
      final HashMap localHashMap = new HashMap();
      int i = 0;
      AddressField[] arrayOfAddressField = this.mFormOptions.getCustomFieldOrder(paramString);
      int j = arrayOfAddressField.length;
      for (int k = 0; k < j; k++)
      {
        localHashMap.put(arrayOfAddressField[k], Integer.valueOf(i));
        i++;
      }
      ArrayList localArrayList1 = new ArrayList();
      ArrayList localArrayList2 = new ArrayList();
      int m = 0;
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        AddressField localAddressField = (AddressField)localIterator.next();
        if (localHashMap.containsKey(localAddressField))
        {
          localArrayList1.add(localAddressField);
          localArrayList2.add(Integer.valueOf(m));
        }
        m++;
      }
      Collections.sort(localArrayList1, new Comparator() {});
      for (int n = 0; n < localArrayList1.size(); n++) {
        paramList.set(((Integer)localArrayList2.get(n)).intValue(), localArrayList1.get(n));
      }
    }
  }
  
  static String removeAllRedundantSpaces(String paramString)
  {
    return paramString.trim().replaceAll(" +", " ");
  }
  
  public final List<AddressField> getAddressFieldOrder(LookupKey.ScriptType paramScriptType, String paramString)
  {
    Util.checkNotNull(paramScriptType);
    Util.checkNotNull(paramString);
    ArrayList localArrayList1 = new ArrayList();
    Iterator localIterator1 = getFormatSubStrings(paramScriptType, paramString).iterator();
    while (localIterator1.hasNext())
    {
      String str = (String)localIterator1.next();
      if ((str.matches("%.")) && (!str.equals("%n"))) {
        localArrayList1.add(AddressField.of(str.charAt(1)));
      }
    }
    overrideFieldOrder(paramString, localArrayList1);
    ArrayList localArrayList2 = new ArrayList();
    Iterator localIterator2 = localArrayList1.iterator();
    while (localIterator2.hasNext())
    {
      AddressField localAddressField = (AddressField)localIterator2.next();
      if (localAddressField == AddressField.STREET_ADDRESS)
      {
        localArrayList2.add(AddressField.ADDRESS_LINE_1);
        localArrayList2.add(AddressField.ADDRESS_LINE_2);
      }
      else
      {
        localArrayList2.add(localAddressField);
      }
    }
    return localArrayList2;
  }
  
  final List<String> getFormatSubStrings(LookupKey.ScriptType paramScriptType, String paramString)
  {
    String str;
    ArrayList localArrayList;
    int i;
    int k;
    label42:
    char c;
    if (paramScriptType == LookupKey.ScriptType.LOCAL)
    {
      str = getJsonValue(paramString, AddressDataKey.FMT);
      localArrayList = new ArrayList();
      i = 0;
      char[] arrayOfChar = str.toCharArray();
      int j = arrayOfChar.length;
      k = 0;
      if (k >= j) {
        break label211;
      }
      c = arrayOfChar[k];
      if (i == 0) {
        break label182;
      }
      i = 0;
      if (!"%n".equals("%" + c)) {
        break label117;
      }
      localArrayList.add("%n");
    }
    for (;;)
    {
      k++;
      break label42;
      str = getJsonValue(paramString, AddressDataKey.LFMT);
      break;
      label117:
      Util.checkNotNull(AddressField.of(c), "Unrecognized character '" + c + "' in format pattern: " + str);
      localArrayList.add("%" + c);
      i = 0;
      continue;
      label182:
      if (c == '%') {
        i = 1;
      } else {
        localArrayList.add(String.valueOf(c));
      }
    }
    label211:
    return localArrayList;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.i18n.addressinput.FormatInterpreter
 * JD-Core Version:    0.7.0.1
 */