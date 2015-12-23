package com.google.android.finsky.placesapi;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.finsky.protos.Address;
import java.util.LinkedList;
import org.json.JSONException;
import org.json.JSONObject;

public final class PlaceDetailResponse
{
  public Address mAddress;
  
  private PlaceDetailResponse(Address paramAddress)
  {
    this.mAddress = paramAddress;
  }
  
  public static PlaceDetailResponse parseFromJson(JSONObject paramJSONObject, AdrMicroformatParser paramAdrMicroformatParser)
    throws JSONException
  {
    String str1 = paramJSONObject.getString("adr_address");
    Address localAddress;
    try
    {
      localAddress = new Address();
      LinkedList localLinkedList = AdrMicroformatParser.split(str1);
      AdrMicroformatParser.PartType localPartType = AdrMicroformatParser.PartType.UNKNOWN;
      while (AdrMicroformatParser.getFirstAndRemove(localLinkedList, localPartType) != null) {}
      String str2 = AdrMicroformatParser.getFirstAndRemove(localLinkedList, AdrMicroformatParser.PartType.ADR_STREET_ADDRESS);
      if (!TextUtils.isEmpty(str2))
      {
        localAddress.addressLine1 = str2;
        localAddress.hasAddressLine1 = true;
      }
      String str3 = AdrMicroformatParser.getFirstAndRemove(localLinkedList, AdrMicroformatParser.PartType.ADR_LOCALITY);
      if (!TextUtils.isEmpty(str3))
      {
        localAddress.city = str3;
        localAddress.hasCity = true;
      }
      String str4 = AdrMicroformatParser.getFirstAndRemove(localLinkedList, AdrMicroformatParser.PartType.ADR_POSTAL_CODE);
      if (!TextUtils.isEmpty(str4))
      {
        localAddress.postalCode = str4;
        localAddress.hasPostalCode = true;
      }
      String str5 = AdrMicroformatParser.getFirstAndRemove(localLinkedList, AdrMicroformatParser.PartType.ADR_REGION);
      if (!TextUtils.isEmpty(str5))
      {
        localAddress.state = str5;
        localAddress.hasState = true;
      }
      while ((!localLinkedList.isEmpty()) && (((AdrMicroformatParser.AnnotatedPart)localLinkedList.getLast()).type == AdrMicroformatParser.PartType.SEPARATOR)) {
        localLinkedList.removeLast();
      }
      JSONException localJSONException;
      str6 = TextUtils.join("", localLinkedList).replaceAll("\\n", paramAdrMicroformatParser.mContext.getString(2131362944));
    }
    catch (AdrMicroformatParserException localAdrMicroformatParserException)
    {
      localJSONException = new JSONException(localAdrMicroformatParserException.getMessage());
      localJSONException.initCause(localAdrMicroformatParserException);
      throw localJSONException;
    }
    String str6;
    if (!TextUtils.isEmpty(str6))
    {
      localAddress.addressLine2 = str6;
      localAddress.hasAddressLine2 = true;
    }
    return new PlaceDetailResponse(localAddress);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.placesapi.PlaceDetailResponse
 * JD-Core Version:    0.7.0.1
 */