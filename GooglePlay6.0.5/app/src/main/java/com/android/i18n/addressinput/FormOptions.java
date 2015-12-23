package com.android.i18n.addressinput;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public final class FormOptions
{
  private final String mBaseId;
  private final EnumMap<AddressField, String> mCustomLabels = new EnumMap(AddressField.class);
  final EnumSet<AddressField> mHiddenFields;
  private final EnumMap<AddressField, Integer> mMaxLengths = new EnumMap(AddressField.class);
  private final Map<String, AddressField[]> mOverrideFieldOrder = new HashMap();
  final EnumSet<AddressField> mReadonlyFields;
  private final EnumSet<AddressField> mRequiredFields;
  private final String mServerUrl;
  
  private FormOptions(Builder paramBuilder)
  {
    this.mBaseId = paramBuilder.mBaseId;
    this.mHiddenFields = EnumSet.copyOf(paramBuilder.mHiddenFields);
    this.mReadonlyFields = EnumSet.copyOf(paramBuilder.mReadonlyFields);
    this.mRequiredFields = EnumSet.copyOf(paramBuilder.mRequiredFields);
    this.mCustomLabels.putAll(paramBuilder.mCustomLabels);
    this.mOverrideFieldOrder.putAll(paramBuilder.mOverrideFieldOrder);
    this.mMaxLengths.putAll(paramBuilder.mMaxLengths);
    this.mServerUrl = paramBuilder.mServerUrl;
  }
  
  final AddressField[] getCustomFieldOrder(String paramString)
  {
    if (paramString == null) {
      throw new RuntimeException("regionCode cannot be null.");
    }
    return (AddressField[])this.mOverrideFieldOrder.get(paramString);
  }
  
  final boolean isHidden(AddressField paramAddressField)
  {
    return this.mHiddenFields.contains(paramAddressField);
  }
  
  public static final class Builder
  {
    String mBaseId = "addressform";
    final EnumMap<AddressField, String> mCustomLabels = new EnumMap(AddressField.class);
    final EnumSet<AddressField> mHiddenFields = EnumSet.noneOf(AddressField.class);
    final EnumMap<AddressField, Integer> mMaxLengths = new EnumMap(AddressField.class);
    final Map<String, AddressField[]> mOverrideFieldOrder = new HashMap();
    final EnumSet<AddressField> mReadonlyFields = EnumSet.noneOf(AddressField.class);
    final EnumSet<AddressField> mRequiredFields = EnumSet.noneOf(AddressField.class);
    String mServerUrl = new CacheData().mServiceUrl;
    
    public final FormOptions build()
    {
      return new FormOptions(this, (byte)0);
    }
    
    public final Builder hide(AddressField paramAddressField)
    {
      if (paramAddressField == null) {
        throw new RuntimeException("AddressField field cannot be null.");
      }
      this.mHiddenFields.add(paramAddressField);
      this.mRequiredFields.remove(paramAddressField);
      return this;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.i18n.addressinput.FormOptions
 * JD-Core Version:    0.7.0.1
 */