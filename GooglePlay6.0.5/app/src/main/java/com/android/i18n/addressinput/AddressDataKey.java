package com.android.i18n.addressinput;

import java.util.HashMap;
import java.util.Map;

 enum AddressDataKey
{
  private static final Map<String, AddressDataKey> ADDRESS_KEY_NAME_MAP;
  
  static
  {
    AddressDataKey[] arrayOfAddressDataKey1 = new AddressDataKey[15];
    arrayOfAddressDataKey1[0] = COUNTRIES;
    arrayOfAddressDataKey1[1] = FMT;
    arrayOfAddressDataKey1[2] = ID;
    arrayOfAddressDataKey1[3] = KEY;
    arrayOfAddressDataKey1[4] = LANG;
    arrayOfAddressDataKey1[5] = LFMT;
    arrayOfAddressDataKey1[6] = REQUIRE;
    arrayOfAddressDataKey1[7] = STATE_NAME_TYPE;
    arrayOfAddressDataKey1[8] = SUB_KEYS;
    arrayOfAddressDataKey1[9] = SUB_LNAMES;
    arrayOfAddressDataKey1[10] = SUB_MORES;
    arrayOfAddressDataKey1[11] = SUB_NAMES;
    arrayOfAddressDataKey1[12] = XZIP;
    arrayOfAddressDataKey1[13] = ZIP;
    arrayOfAddressDataKey1[14] = ZIP_NAME_TYPE;
    $VALUES = arrayOfAddressDataKey1;
    ADDRESS_KEY_NAME_MAP = new HashMap();
    for (AddressDataKey localAddressDataKey : values()) {
      ADDRESS_KEY_NAME_MAP.put(localAddressDataKey.toString().toLowerCase(), localAddressDataKey);
    }
  }
  
  private AddressDataKey() {}
  
  static AddressDataKey get(String paramString)
  {
    return (AddressDataKey)ADDRESS_KEY_NAME_MAP.get(paramString.toLowerCase());
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.i18n.addressinput.AddressDataKey
 * JD-Core Version:    0.7.0.1
 */