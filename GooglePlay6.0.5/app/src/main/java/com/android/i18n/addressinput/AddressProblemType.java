package com.android.i18n.addressinput;

public enum AddressProblemType
{
  static
  {
    MISSING_REQUIRED_FIELD = new AddressProblemType("MISSING_REQUIRED_FIELD", 1);
    UNKNOWN_VALUE = new AddressProblemType("UNKNOWN_VALUE", 2);
    UNRECOGNIZED_FORMAT = new AddressProblemType("UNRECOGNIZED_FORMAT", 3);
    MISMATCHING_VALUE = new AddressProblemType("MISMATCHING_VALUE", 4);
    AddressProblemType[] arrayOfAddressProblemType = new AddressProblemType[5];
    arrayOfAddressProblemType[0] = USING_UNUSED_FIELD;
    arrayOfAddressProblemType[1] = MISSING_REQUIRED_FIELD;
    arrayOfAddressProblemType[2] = UNKNOWN_VALUE;
    arrayOfAddressProblemType[3] = UNRECOGNIZED_FORMAT;
    arrayOfAddressProblemType[4] = MISMATCHING_VALUE;
    $VALUES = arrayOfAddressProblemType;
  }
  
  private AddressProblemType() {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.i18n.addressinput.AddressProblemType
 * JD-Core Version:    0.7.0.1
 */