package com.android.i18n.addressinput;

import java.util.EnumMap;
import java.util.Map;

public final class LookupKey
{
  private static final AddressField[] HIERARCHY;
  private final String mKeyString;
  final KeyType mKeyType;
  private final String mLanguageCode;
  private final Map<AddressField, String> mNodes;
  private final ScriptType mScriptType;
  
  static
  {
    AddressField[] arrayOfAddressField = new AddressField[4];
    arrayOfAddressField[0] = AddressField.COUNTRY;
    arrayOfAddressField[1] = AddressField.ADMIN_AREA;
    arrayOfAddressField[2] = AddressField.LOCALITY;
    arrayOfAddressField[3] = AddressField.DEPENDENT_LOCALITY;
    HIERARCHY = arrayOfAddressField;
  }
  
  private LookupKey(Builder paramBuilder)
  {
    this.mKeyType = paramBuilder.keyType;
    this.mScriptType = paramBuilder.script;
    this.mNodes = paramBuilder.nodes;
    this.mLanguageCode = paramBuilder.languageCode;
    this.mKeyString = getKeyString();
  }
  
  private String getKeyString()
  {
    StringBuilder localStringBuilder = new StringBuilder(this.mKeyType.name().toLowerCase());
    if (this.mKeyType == KeyType.DATA)
    {
      AddressField[] arrayOfAddressField = HIERARCHY;
      int i = arrayOfAddressField.length;
      int j = 0;
      if (j < i)
      {
        AddressField localAddressField = arrayOfAddressField[j];
        if (this.mNodes.containsKey(localAddressField))
        {
          if ((localAddressField == AddressField.COUNTRY) && (this.mLanguageCode != null)) {
            localStringBuilder.append("/").append((String)this.mNodes.get(localAddressField)).append("--").append(this.mLanguageCode);
          }
          for (;;)
          {
            j++;
            break;
            localStringBuilder.append("/").append((String)this.mNodes.get(localAddressField));
          }
        }
      }
    }
    else if (this.mNodes.containsKey(AddressField.COUNTRY))
    {
      localStringBuilder.append("/").append((String)this.mNodes.get(AddressField.COUNTRY)).append("/").append(this.mScriptType.name().toLowerCase()).append("/_default");
    }
    return localStringBuilder.toString();
  }
  
  static boolean hasValidKeyPrefix(String paramString)
  {
    KeyType[] arrayOfKeyType = KeyType.values();
    int i = arrayOfKeyType.length;
    for (int j = 0; j < i; j++) {
      if (paramString.startsWith(arrayOfKeyType[j].name().toLowerCase())) {
        return true;
      }
    }
    return false;
  }
  
  public final boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if ((paramObject == null) || (paramObject.getClass() != getClass())) {
      return false;
    }
    return ((LookupKey)paramObject).toString().equals(this.mKeyString);
  }
  
  final LookupKey getKeyForUpperLevelField(AddressField paramAddressField)
  {
    if (this.mKeyType != KeyType.DATA) {
      throw new RuntimeException("Only support getting parent keys for the data key type.");
    }
    Builder localBuilder = new Builder(this);
    int i = 0;
    int j = 0;
    AddressField[] arrayOfAddressField = HIERARCHY;
    int k = arrayOfAddressField.length;
    int m = 0;
    if (m < k)
    {
      localAddressField = arrayOfAddressField[m];
      if ((i != 0) && (localBuilder.nodes.containsKey(localAddressField))) {
        localBuilder.nodes.remove(localAddressField);
      }
      if (localAddressField == paramAddressField) {
        if (localBuilder.nodes.containsKey(localAddressField)) {}
      }
    }
    while (j == 0)
    {
      AddressField localAddressField;
      return null;
      i = 1;
      j = 1;
      m++;
      break;
    }
    localBuilder.languageCode = this.mLanguageCode;
    localBuilder.script = this.mScriptType;
    return localBuilder.build();
  }
  
  public final int hashCode()
  {
    return this.mKeyString.hashCode();
  }
  
  public final String toString()
  {
    return this.mKeyString;
  }
  
  public static final class Builder
  {
    LookupKey.KeyType keyType;
    String languageCode;
    Map<AddressField, String> nodes = new EnumMap(AddressField.class);
    LookupKey.ScriptType script = LookupKey.ScriptType.LOCAL;
    
    public Builder(LookupKey.KeyType paramKeyType)
    {
      this.keyType = paramKeyType;
    }
    
    Builder(LookupKey paramLookupKey)
    {
      this.keyType = paramLookupKey.mKeyType;
      this.script = paramLookupKey.mScriptType;
      this.languageCode = paramLookupKey.mLanguageCode;
      for (AddressField localAddressField : LookupKey.HIERARCHY)
      {
        if (!paramLookupKey.mNodes.containsKey(localAddressField)) {
          break;
        }
        this.nodes.put(localAddressField, paramLookupKey.mNodes.get(localAddressField));
      }
    }
    
    Builder(String paramString)
    {
      String[] arrayOfString1 = paramString.split("/");
      if ((!arrayOfString1[0].equals(LookupKey.KeyType.DATA.name().toLowerCase())) && (!arrayOfString1[0].equals(LookupKey.KeyType.EXAMPLES.name().toLowerCase()))) {
        throw new RuntimeException("Wrong key type: " + arrayOfString1[0]);
      }
      if (arrayOfString1.length > 1 + LookupKey.HIERARCHY.length) {
        throw new RuntimeException("input key '" + paramString + "' deeper than supported hierarchy");
      }
      String str1;
      if (arrayOfString1[0].equals("data"))
      {
        this.keyType = LookupKey.KeyType.DATA;
        if (arrayOfString1.length > 1)
        {
          String str3 = Util.trimToNull(arrayOfString1[1]);
          if (str3.contains("--"))
          {
            String[] arrayOfString2 = str3.split("--");
            if (arrayOfString2.length != 2) {
              throw new RuntimeException("Wrong format: Substring should be country code--language code");
            }
            str3 = arrayOfString2[0];
            this.languageCode = arrayOfString2[1];
          }
          this.nodes.put(LookupKey.HIERARCHY[0], str3);
        }
        if (arrayOfString1.length > 2) {
          for (int i = 2; i < arrayOfString1.length; i++)
          {
            String str2 = Util.trimToNull(arrayOfString1[i]);
            if (str2 == null) {
              break;
            }
            this.nodes.put(LookupKey.HIERARCHY[(i - 1)], str2);
          }
        }
      }
      else if (arrayOfString1[0].equals("examples"))
      {
        this.keyType = LookupKey.KeyType.EXAMPLES;
        if (arrayOfString1.length > 1) {
          this.nodes.put(AddressField.COUNTRY, arrayOfString1[1]);
        }
        if (arrayOfString1.length > 2)
        {
          str1 = arrayOfString1[2];
          if (!str1.equals("local")) {
            break label379;
          }
        }
      }
      for (this.script = LookupKey.ScriptType.LOCAL;; this.script = LookupKey.ScriptType.LATIN)
      {
        if ((arrayOfString1.length > 3) && (!arrayOfString1[3].equals("_default"))) {
          this.languageCode = arrayOfString1[3];
        }
        return;
        label379:
        if (!str1.equals("latin")) {
          break;
        }
      }
      throw new RuntimeException("Script type has to be either latin or local.");
    }
    
    public final LookupKey build()
    {
      return new LookupKey(this, (byte)0);
    }
    
    public final Builder setAddressData(AddressData paramAddressData)
    {
      this.languageCode = paramAddressData.mLanguageCode;
      if ((this.languageCode != null) && (Util.isExplicitLatinScript(this.languageCode))) {
        this.script = LookupKey.ScriptType.LATIN;
      }
      if (paramAddressData.mPostalCountry == null) {}
      do
      {
        do
        {
          do
          {
            return this;
            this.nodes.put(AddressField.COUNTRY, paramAddressData.mPostalCountry);
          } while (paramAddressData.mAdministrativeArea == null);
          this.nodes.put(AddressField.ADMIN_AREA, paramAddressData.mAdministrativeArea);
        } while (paramAddressData.mLocality == null);
        this.nodes.put(AddressField.LOCALITY, paramAddressData.mLocality);
      } while (paramAddressData.mDependentLocality == null);
      this.nodes.put(AddressField.DEPENDENT_LOCALITY, paramAddressData.mDependentLocality);
      return this;
    }
  }
  
  public static enum KeyType
  {
    static
    {
      KeyType[] arrayOfKeyType = new KeyType[2];
      arrayOfKeyType[0] = DATA;
      arrayOfKeyType[1] = EXAMPLES;
      $VALUES = arrayOfKeyType;
    }
    
    private KeyType() {}
  }
  
  static enum ScriptType
  {
    static
    {
      ScriptType[] arrayOfScriptType = new ScriptType[2];
      arrayOfScriptType[0] = LATIN;
      arrayOfScriptType[1] = LOCAL;
      $VALUES = arrayOfScriptType;
    }
    
    private ScriptType() {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.i18n.addressinput.LookupKey
 * JD-Core Version:    0.7.0.1
 */