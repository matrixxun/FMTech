package com.google.android.finsky.placesapi;

public final class PlacesService
{
  private final String mApiKey;
  public final String mLanguage;
  public final AdrMicroformatParser mParser;
  
  public PlacesService(String paramString1, String paramString2, AdrMicroformatParser paramAdrMicroformatParser)
  {
    this.mApiKey = paramString1;
    this.mLanguage = paramString2;
    this.mParser = paramAdrMicroformatParser;
  }
  
  public final String buildRequestUrl(StringBuilder paramStringBuilder)
  {
    paramStringBuilder.append("&key=").append(this.mApiKey).append("&sensor=true");
    return "https://maps.googleapis.com".concat(paramStringBuilder.toString());
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.placesapi.PlacesService
 * JD-Core Version:    0.7.0.1
 */