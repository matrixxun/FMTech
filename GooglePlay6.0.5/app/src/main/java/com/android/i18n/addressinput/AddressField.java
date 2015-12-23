package com.android.i18n.addressinput;

import java.util.HashMap;
import java.util.Map;

public enum AddressField
{
  private static final Map<Character, AddressField> FIELD_MAPPING;
  private final String mAttributeName;
  private final char mField;
  
  static
  {
    ORGANIZATION = new AddressField("ORGANIZATION", 3, 'O', "organization");
    ADDRESS_LINE_1 = new AddressField("ADDRESS_LINE_1", 4, '1', "street1");
    ADDRESS_LINE_2 = new AddressField("ADDRESS_LINE_2", 5, '2', "street2");
    DEPENDENT_LOCALITY = new AddressField("DEPENDENT_LOCALITY", 6, 'D');
    POSTAL_CODE = new AddressField("POSTAL_CODE", 7, 'Z');
    SORTING_CODE = new AddressField("SORTING_CODE", 8, 'X');
    STREET_ADDRESS = new AddressField("STREET_ADDRESS", 9, 'A');
    COUNTRY = new AddressField("COUNTRY", 10, 'R');
    AddressField[] arrayOfAddressField1 = new AddressField[11];
    arrayOfAddressField1[0] = ADMIN_AREA;
    arrayOfAddressField1[1] = LOCALITY;
    arrayOfAddressField1[2] = RECIPIENT;
    arrayOfAddressField1[3] = ORGANIZATION;
    arrayOfAddressField1[4] = ADDRESS_LINE_1;
    arrayOfAddressField1[5] = ADDRESS_LINE_2;
    arrayOfAddressField1[6] = DEPENDENT_LOCALITY;
    arrayOfAddressField1[7] = POSTAL_CODE;
    arrayOfAddressField1[8] = SORTING_CODE;
    arrayOfAddressField1[9] = STREET_ADDRESS;
    arrayOfAddressField1[10] = COUNTRY;
    $VALUES = arrayOfAddressField1;
    FIELD_MAPPING = new HashMap();
    for (AddressField localAddressField : values()) {
      FIELD_MAPPING.put(Character.valueOf(localAddressField.mField), localAddressField);
    }
  }
  
  private AddressField(char paramChar)
  {
    this(paramChar, null);
  }
  
  private AddressField(char paramChar, String paramString)
  {
    this.mField = paramChar;
    this.mAttributeName = paramString;
  }
  
  static AddressField of(char paramChar)
  {
    return (AddressField)FIELD_MAPPING.get(Character.valueOf(paramChar));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.i18n.addressinput.AddressField
 * JD-Core Version:    0.7.0.1
 */