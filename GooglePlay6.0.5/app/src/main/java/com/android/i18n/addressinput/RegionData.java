package com.android.i18n.addressinput;

final class RegionData
{
  String mKey;
  String mName;
  
  private RegionData() {}
  
  private RegionData(RegionData paramRegionData)
  {
    Util.checkNotNull(paramRegionData);
    this.mKey = paramRegionData.mKey;
    this.mName = paramRegionData.mName;
  }
  
  public final String getDisplayName()
  {
    if (this.mName != null) {
      return this.mName;
    }
    return this.mKey;
  }
  
  final boolean isValidName(String paramString)
  {
    if (paramString == null) {}
    while ((!paramString.equalsIgnoreCase(this.mKey)) && (!paramString.equalsIgnoreCase(this.mName))) {
      return false;
    }
    return true;
  }
  
  static final class Builder
  {
    RegionData mData = new RegionData((byte)0);
    
    final RegionData build()
    {
      return new RegionData(this.mData, (byte)0);
    }
    
    final Builder setKey(String paramString)
    {
      Util.checkNotNull(paramString, "Key should not be null.");
      this.mData.mKey = paramString;
      return this;
    }
    
    final Builder setName(String paramString)
    {
      this.mData.mName = Util.trimToNull(paramString);
      return this;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.i18n.addressinput.RegionData
 * JD-Core Version:    0.7.0.1
 */