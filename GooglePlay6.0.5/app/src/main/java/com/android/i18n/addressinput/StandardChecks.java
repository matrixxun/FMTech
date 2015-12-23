package com.android.i18n.addressinput;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class StandardChecks
{
  public static final Map<AddressField, List<AddressProblemType>> PROBLEM_MAP;
  
  static
  {
    HashMap localHashMap = new HashMap();
    AddressField localAddressField1 = AddressField.COUNTRY;
    AddressProblemType[] arrayOfAddressProblemType1 = new AddressProblemType[3];
    arrayOfAddressProblemType1[0] = AddressProblemType.USING_UNUSED_FIELD;
    arrayOfAddressProblemType1[1] = AddressProblemType.MISSING_REQUIRED_FIELD;
    arrayOfAddressProblemType1[2] = AddressProblemType.UNKNOWN_VALUE;
    addToMap(localHashMap, localAddressField1, arrayOfAddressProblemType1);
    AddressField localAddressField2 = AddressField.ADMIN_AREA;
    AddressProblemType[] arrayOfAddressProblemType2 = new AddressProblemType[3];
    arrayOfAddressProblemType2[0] = AddressProblemType.USING_UNUSED_FIELD;
    arrayOfAddressProblemType2[1] = AddressProblemType.MISSING_REQUIRED_FIELD;
    arrayOfAddressProblemType2[2] = AddressProblemType.UNKNOWN_VALUE;
    addToMap(localHashMap, localAddressField2, arrayOfAddressProblemType2);
    AddressField localAddressField3 = AddressField.LOCALITY;
    AddressProblemType[] arrayOfAddressProblemType3 = new AddressProblemType[3];
    arrayOfAddressProblemType3[0] = AddressProblemType.USING_UNUSED_FIELD;
    arrayOfAddressProblemType3[1] = AddressProblemType.MISSING_REQUIRED_FIELD;
    arrayOfAddressProblemType3[2] = AddressProblemType.UNKNOWN_VALUE;
    addToMap(localHashMap, localAddressField3, arrayOfAddressProblemType3);
    AddressField localAddressField4 = AddressField.DEPENDENT_LOCALITY;
    AddressProblemType[] arrayOfAddressProblemType4 = new AddressProblemType[3];
    arrayOfAddressProblemType4[0] = AddressProblemType.USING_UNUSED_FIELD;
    arrayOfAddressProblemType4[1] = AddressProblemType.MISSING_REQUIRED_FIELD;
    arrayOfAddressProblemType4[2] = AddressProblemType.UNKNOWN_VALUE;
    addToMap(localHashMap, localAddressField4, arrayOfAddressProblemType4);
    AddressField localAddressField5 = AddressField.POSTAL_CODE;
    AddressProblemType[] arrayOfAddressProblemType5 = new AddressProblemType[4];
    arrayOfAddressProblemType5[0] = AddressProblemType.USING_UNUSED_FIELD;
    arrayOfAddressProblemType5[1] = AddressProblemType.MISSING_REQUIRED_FIELD;
    arrayOfAddressProblemType5[2] = AddressProblemType.UNRECOGNIZED_FORMAT;
    arrayOfAddressProblemType5[3] = AddressProblemType.MISMATCHING_VALUE;
    addToMap(localHashMap, localAddressField5, arrayOfAddressProblemType5);
    AddressField localAddressField6 = AddressField.STREET_ADDRESS;
    AddressProblemType[] arrayOfAddressProblemType6 = new AddressProblemType[2];
    arrayOfAddressProblemType6[0] = AddressProblemType.USING_UNUSED_FIELD;
    arrayOfAddressProblemType6[1] = AddressProblemType.MISSING_REQUIRED_FIELD;
    addToMap(localHashMap, localAddressField6, arrayOfAddressProblemType6);
    AddressField localAddressField7 = AddressField.SORTING_CODE;
    AddressProblemType[] arrayOfAddressProblemType7 = new AddressProblemType[2];
    arrayOfAddressProblemType7[0] = AddressProblemType.USING_UNUSED_FIELD;
    arrayOfAddressProblemType7[1] = AddressProblemType.MISSING_REQUIRED_FIELD;
    addToMap(localHashMap, localAddressField7, arrayOfAddressProblemType7);
    AddressField localAddressField8 = AddressField.ORGANIZATION;
    AddressProblemType[] arrayOfAddressProblemType8 = new AddressProblemType[2];
    arrayOfAddressProblemType8[0] = AddressProblemType.USING_UNUSED_FIELD;
    arrayOfAddressProblemType8[1] = AddressProblemType.MISSING_REQUIRED_FIELD;
    addToMap(localHashMap, localAddressField8, arrayOfAddressProblemType8);
    AddressField localAddressField9 = AddressField.RECIPIENT;
    AddressProblemType[] arrayOfAddressProblemType9 = new AddressProblemType[2];
    arrayOfAddressProblemType9[0] = AddressProblemType.USING_UNUSED_FIELD;
    arrayOfAddressProblemType9[1] = AddressProblemType.MISSING_REQUIRED_FIELD;
    addToMap(localHashMap, localAddressField9, arrayOfAddressProblemType9);
    PROBLEM_MAP = Collections.unmodifiableMap(localHashMap);
  }
  
  private static void addToMap(Map<AddressField, List<AddressProblemType>> paramMap, AddressField paramAddressField, AddressProblemType... paramVarArgs)
  {
    paramMap.put(paramAddressField, Collections.unmodifiableList(Arrays.asList(paramVarArgs)));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.i18n.addressinput.StandardChecks
 * JD-Core Version:    0.7.0.1
 */