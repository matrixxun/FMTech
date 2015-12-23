package com.android.i18n.addressinput;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public final class FieldVerifier
{
  Map<String, String> mCandidateValues;
  private DataSource mDataSource;
  Pattern mFormat;
  private String mId;
  String[] mKeys;
  String[] mLatinNames;
  String[] mLocalNames;
  Pattern mMatch;
  Set<AddressField> mPossibleFields;
  Set<AddressField> mRequired;
  
  public FieldVerifier(DataSource paramDataSource)
  {
    this.mDataSource = paramDataSource;
    this.mId = "data";
    AddressVerificationNodeData localAddressVerificationNodeData1 = this.mDataSource.getDefaultData("data");
    if (localAddressVerificationNodeData1.containsKey(AddressDataKey.COUNTRIES)) {
      this.mKeys = localAddressVerificationNodeData1.get(AddressDataKey.COUNTRIES).split("~");
    }
    this.mCandidateValues = Util.buildNameToKeyMap(this.mKeys, null, null);
    AddressVerificationNodeData localAddressVerificationNodeData2 = this.mDataSource.getDefaultData("data/ZZ");
    this.mPossibleFields = new HashSet();
    if (localAddressVerificationNodeData2.containsKey(AddressDataKey.FMT)) {
      this.mPossibleFields = parseAddressFields(localAddressVerificationNodeData2.get(AddressDataKey.FMT));
    }
    this.mRequired = new HashSet();
    if (localAddressVerificationNodeData2.containsKey(AddressDataKey.REQUIRE)) {
      this.mRequired = parseRequireString(localAddressVerificationNodeData2.get(AddressDataKey.REQUIRE));
    }
  }
  
  private FieldVerifier(FieldVerifier paramFieldVerifier, AddressVerificationNodeData paramAddressVerificationNodeData)
  {
    this.mPossibleFields = paramFieldVerifier.mPossibleFields;
    this.mRequired = paramFieldVerifier.mRequired;
    this.mDataSource = paramFieldVerifier.mDataSource;
    this.mFormat = paramFieldVerifier.mFormat;
    this.mMatch = paramFieldVerifier.mMatch;
    int i;
    if (paramAddressVerificationNodeData != null)
    {
      if (paramAddressVerificationNodeData.containsKey(AddressDataKey.ID)) {
        this.mId = paramAddressVerificationNodeData.get(AddressDataKey.ID);
      }
      if (paramAddressVerificationNodeData.containsKey(AddressDataKey.SUB_KEYS)) {
        this.mKeys = paramAddressVerificationNodeData.get(AddressDataKey.SUB_KEYS).split("~");
      }
      if (paramAddressVerificationNodeData.containsKey(AddressDataKey.SUB_LNAMES)) {
        this.mLatinNames = paramAddressVerificationNodeData.get(AddressDataKey.SUB_LNAMES).split("~");
      }
      if (paramAddressVerificationNodeData.containsKey(AddressDataKey.SUB_NAMES)) {
        this.mLocalNames = paramAddressVerificationNodeData.get(AddressDataKey.SUB_NAMES).split("~");
      }
      if (paramAddressVerificationNodeData.containsKey(AddressDataKey.FMT)) {
        this.mPossibleFields = parseAddressFields(paramAddressVerificationNodeData.get(AddressDataKey.FMT));
      }
      if (paramAddressVerificationNodeData.containsKey(AddressDataKey.REQUIRE)) {
        this.mRequired = parseRequireString(paramAddressVerificationNodeData.get(AddressDataKey.REQUIRE));
      }
      if (paramAddressVerificationNodeData.containsKey(AddressDataKey.XZIP)) {
        this.mFormat = Pattern.compile(paramAddressVerificationNodeData.get(AddressDataKey.XZIP), 2);
      }
      if (paramAddressVerificationNodeData.containsKey(AddressDataKey.ZIP))
      {
        Util.checkNotNull(this.mId, "Cannot use null as key");
        if (this.mId.split("/").length != 2) {
          break label336;
        }
        i = 1;
        if (i == 0) {
          break label341;
        }
        this.mFormat = Pattern.compile(paramAddressVerificationNodeData.get(AddressDataKey.ZIP), 2);
      }
    }
    for (;;)
    {
      if ((this.mKeys != null) && (this.mLocalNames == null) && (this.mLatinNames != null) && (this.mKeys.length == this.mLatinNames.length)) {
        this.mLocalNames = this.mKeys;
      }
      this.mCandidateValues = Util.buildNameToKeyMap(this.mKeys, this.mLocalNames, this.mLatinNames);
      return;
      label336:
      i = 0;
      break;
      label341:
      this.mMatch = Pattern.compile(paramAddressVerificationNodeData.get(AddressDataKey.ZIP), 2);
    }
  }
  
  private static Set<AddressField> parseAddressFields(String paramString)
  {
    EnumSet localEnumSet = EnumSet.of(AddressField.COUNTRY);
    int i = 0;
    char[] arrayOfChar = paramString.toCharArray();
    int j = arrayOfChar.length;
    int k = 0;
    if (k < j)
    {
      char c = arrayOfChar[k];
      if (i != 0)
      {
        i = 0;
        if (c != 'n')
        {
          AddressField localAddressField = AddressField.of(c);
          if (localAddressField == null) {
            throw new RuntimeException("Unrecognized character '" + c + "' in format pattern: " + paramString);
          }
          localEnumSet.add(localAddressField);
        }
      }
      for (;;)
      {
        k++;
        break;
        if (c == '%') {
          i = 1;
        }
      }
    }
    localEnumSet.remove(AddressField.ADDRESS_LINE_1);
    localEnumSet.remove(AddressField.ADDRESS_LINE_2);
    return localEnumSet;
  }
  
  private static Set<AddressField> parseRequireString(String paramString)
  {
    EnumSet localEnumSet = EnumSet.of(AddressField.COUNTRY);
    for (char c : paramString.toCharArray())
    {
      AddressField localAddressField = AddressField.of(c);
      if (localAddressField == null) {
        throw new RuntimeException("Unrecognized character '" + c + "' in require pattern: " + paramString);
      }
      localEnumSet.add(localAddressField);
    }
    localEnumSet.remove(AddressField.ADDRESS_LINE_1);
    localEnumSet.remove(AddressField.ADDRESS_LINE_2);
    return localEnumSet;
  }
  
  final FieldVerifier refineVerifier(String paramString)
  {
    if (Util.trimToNull(paramString) == null) {
      return new FieldVerifier(this, null);
    }
    String str1 = this.mId + "/" + paramString;
    AddressVerificationNodeData localAddressVerificationNodeData1 = this.mDataSource.get(str1);
    if (localAddressVerificationNodeData1 != null) {
      return new FieldVerifier(this, localAddressVerificationNodeData1);
    }
    if (this.mLatinNames == null) {
      return new FieldVerifier(this, null);
    }
    for (int i = 0; i < this.mLatinNames.length; i++) {
      if (this.mLatinNames[i].equalsIgnoreCase(paramString))
      {
        String str2 = this.mId + "/" + this.mLocalNames[i];
        AddressVerificationNodeData localAddressVerificationNodeData2 = this.mDataSource.get(str2);
        if (localAddressVerificationNodeData2 != null) {
          return new FieldVerifier(this, localAddressVerificationNodeData2);
        }
      }
    }
    return new FieldVerifier(this, null);
  }
  
  public final String toString()
  {
    return this.mId;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.i18n.addressinput.FieldVerifier
 * JD-Core Version:    0.7.0.1
 */