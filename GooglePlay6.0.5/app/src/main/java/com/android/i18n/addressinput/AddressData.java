package com.android.i18n.addressinput;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class AddressData
  implements Serializable
{
  public final String mAddressLine1;
  public final String mAddressLine2;
  public final String mAdministrativeArea;
  public final String mDependentLocality;
  public final String mLanguageCode;
  public final String mLocality;
  final String mOrganization;
  public final String mPostalCode;
  public final String mPostalCountry;
  public final String mRecipient;
  public final String mSortingCode;
  
  private AddressData(Builder paramBuilder)
  {
    this.mPostalCountry = ((String)paramBuilder.mValues.get(AddressField.COUNTRY));
    this.mAdministrativeArea = ((String)paramBuilder.mValues.get(AddressField.ADMIN_AREA));
    this.mLocality = ((String)paramBuilder.mValues.get(AddressField.LOCALITY));
    this.mDependentLocality = ((String)paramBuilder.mValues.get(AddressField.DEPENDENT_LOCALITY));
    this.mPostalCode = ((String)paramBuilder.mValues.get(AddressField.POSTAL_CODE));
    this.mSortingCode = ((String)paramBuilder.mValues.get(AddressField.SORTING_CODE));
    this.mOrganization = ((String)paramBuilder.mValues.get(AddressField.ORGANIZATION));
    this.mRecipient = ((String)paramBuilder.mValues.get(AddressField.RECIPIENT));
    this.mAddressLine1 = ((String)paramBuilder.mValues.get(AddressField.ADDRESS_LINE_1));
    this.mAddressLine2 = ((String)paramBuilder.mValues.get(AddressField.ADDRESS_LINE_2));
    this.mLanguageCode = paramBuilder.mLanguage;
  }
  
  public final String getFieldValue(AddressField paramAddressField)
  {
    switch (1.$SwitchMap$com$android$i18n$addressinput$AddressField[paramAddressField.ordinal()])
    {
    default: 
      throw new IllegalArgumentException("unrecognized key: " + paramAddressField);
    case 1: 
      return this.mPostalCountry;
    case 2: 
      return this.mAdministrativeArea;
    case 3: 
      return this.mLocality;
    case 4: 
      return this.mDependentLocality;
    case 5: 
      return this.mPostalCode;
    case 6: 
      return this.mSortingCode;
    case 7: 
      return this.mAddressLine1;
    case 8: 
      return this.mAddressLine2;
    case 9: 
      return this.mOrganization;
    }
    return this.mRecipient;
  }
  
  public static final class Builder
  {
    public String mLanguage = null;
    final Map<AddressField, String> mValues = new HashMap();
    
    public Builder() {}
    
    public Builder(AddressData paramAddressData)
    {
      set(paramAddressData);
    }
    
    private void normalizeAddresses()
    {
      Object localObject = (String)this.mValues.get(AddressField.ADDRESS_LINE_1);
      String str = (String)this.mValues.get(AddressField.ADDRESS_LINE_2);
      if ((localObject == null) || (((String)localObject).trim().length() == 0))
      {
        localObject = str;
        str = null;
      }
      if (localObject != null)
      {
        String[] arrayOfString = ((String)localObject).split("\n");
        if (arrayOfString.length > 1)
        {
          localObject = arrayOfString[0];
          str = arrayOfString[1];
        }
      }
      this.mValues.put(AddressField.ADDRESS_LINE_1, localObject);
      this.mValues.put(AddressField.ADDRESS_LINE_2, str);
    }
    
    private Builder set(AddressData paramAddressData)
    {
      this.mValues.clear();
      for (AddressField localAddressField : AddressField.values()) {
        if (localAddressField != AddressField.STREET_ADDRESS) {
          set(localAddressField, paramAddressData.getFieldValue(localAddressField));
        }
      }
      normalizeAddresses();
      this.mLanguage = paramAddressData.mLanguageCode;
      return this;
    }
    
    public final AddressData build()
    {
      return new AddressData(this, (byte)0);
    }
    
    public final Builder set(AddressField paramAddressField, String paramString)
    {
      if ((paramString == null) || (paramString.length() == 0)) {
        this.mValues.remove(paramAddressField);
      }
      for (;;)
      {
        normalizeAddresses();
        return this;
        this.mValues.put(paramAddressField, paramString.trim());
      }
    }
    
    public final Builder setAddressLine1(String paramString)
    {
      return set(AddressField.ADDRESS_LINE_1, paramString);
    }
    
    public final Builder setAddressLine2(String paramString)
    {
      return set(AddressField.ADDRESS_LINE_2, paramString);
    }
    
    public final Builder setAdminArea(String paramString)
    {
      return set(AddressField.ADMIN_AREA, paramString);
    }
    
    public final Builder setCountry(String paramString)
    {
      return set(AddressField.COUNTRY, paramString);
    }
    
    public final Builder setLocality(String paramString)
    {
      return set(AddressField.LOCALITY, paramString);
    }
    
    public final Builder setPostalCode(String paramString)
    {
      return set(AddressField.POSTAL_CODE, paramString);
    }
    
    public final Builder setRecipient(String paramString)
    {
      return set(AddressField.RECIPIENT, paramString);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.i18n.addressinput.AddressData
 * JD-Core Version:    0.7.0.1
 */