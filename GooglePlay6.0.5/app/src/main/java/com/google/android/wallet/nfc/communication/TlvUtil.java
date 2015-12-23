package com.google.android.wallet.nfc.communication;

import android.text.TextUtils;
import android.util.SparseIntArray;
import com.google.android.wallet.common.util.DataTypeConversionUtil;
import com.google.android.wallet.nfc.CreditCardNfcResult;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TlvUtil
{
  public static final byte[] APPLICATION_FILE_LOCATOR_TAG;
  public static final byte[] APPLICATION_IDENTIFIER_TAG;
  public static final byte[] APPLICATION_INTERCHANGE_PROFILE_TAG;
  public static final byte[] APPLICATION_LABEL_TAG;
  public static final byte[] CARDHOLDER_NAME_EXTENDED_TAG;
  public static final byte[] CARDHOLDER_NAME_TAG;
  public static final byte[] DF_NAME_TAG;
  public static final byte[] EXP_DATE_TAG;
  public static final byte[] FCI_PROPRIETARY_TEMPLATE_TAG;
  public static final byte[] FCI_TEMPLATE_TAG;
  public static final byte[] GPO_RESPONSE_CONSTRUCTED_TAG;
  public static final byte[] GPO_RESPONSE_PRIMITIVE_TAG;
  private static final Pattern NAME_SPLIT_PATTERN = Pattern.compile("/");
  public static final byte[] PAN_TAG;
  public static final byte[] PDOL_TAG;
  public static final byte[] READ_RECORD_RESPONSE_TAG;
  public static final byte[] SFI_TAG;
  private static final SparseIntArray TAG_ENCODING_MAP;
  public static final byte[] TRACK_1_DATA_TAG;
  private static final Pattern TRACK_1_PATTERN = Pattern.compile("^(?:[A-Z])(\\d{1,19})\\^([^\\^]{2,26})\\^(\\d{4}|\\^)(?:[0-9]{3}|\\^)(?:\\S*)$");
  public static final byte[] TRACK_2_DATA_TAG;
  public static final byte[] TRACK_2_EQV_DATA_TAG;
  private static final Pattern TRACK_2_EQV_PATTERN;
  private static final Pattern TRACK_2_PATTERN = Pattern.compile("^(\\d{1,19})D(\\d{4}|=)(?:[0-9]{3}|=)(?:\\S*)F$");
  
  static
  {
    TRACK_2_EQV_PATTERN = Pattern.compile("^(\\d{1,19})D(\\d{4}|=)(?:[0-9]{3}|=)(?:\\S*)(?:[F]?)$");
    FCI_TEMPLATE_TAG = new byte[] { 111 };
    DF_NAME_TAG = new byte[] { -124 };
    FCI_PROPRIETARY_TEMPLATE_TAG = new byte[] { -91 };
    APPLICATION_LABEL_TAG = new byte[] { 80 };
    APPLICATION_IDENTIFIER_TAG = new byte[] { 79 };
    PDOL_TAG = new byte[] { -97, 56 };
    SFI_TAG = new byte[] { -120 };
    APPLICATION_INTERCHANGE_PROFILE_TAG = new byte[] { -126 };
    APPLICATION_FILE_LOCATOR_TAG = new byte[] { -108 };
    GPO_RESPONSE_CONSTRUCTED_TAG = new byte[] { 119 };
    GPO_RESPONSE_PRIMITIVE_TAG = new byte[] { -128 };
    READ_RECORD_RESPONSE_TAG = new byte[] { 112 };
    TRACK_1_DATA_TAG = new byte[] { 86 };
    TRACK_2_EQV_DATA_TAG = new byte[] { 87 };
    TRACK_2_DATA_TAG = new byte[] { -97, 107 };
    PAN_TAG = new byte[] { 90 };
    CARDHOLDER_NAME_TAG = new byte[] { 95, 32 };
    EXP_DATE_TAG = new byte[] { 95, 36 };
    CARDHOLDER_NAME_EXTENDED_TAG = new byte[] { -97, 11 };
    SparseIntArray localSparseIntArray = new SparseIntArray(19);
    TAG_ENCODING_MAP = localSparseIntArray;
    localSparseIntArray.append(tagToInt(FCI_TEMPLATE_TAG), 1);
    TAG_ENCODING_MAP.append(tagToInt(DF_NAME_TAG), 1);
    TAG_ENCODING_MAP.append(tagToInt(FCI_PROPRIETARY_TEMPLATE_TAG), 1);
    TAG_ENCODING_MAP.append(tagToInt(APPLICATION_LABEL_TAG), 2);
    TAG_ENCODING_MAP.append(tagToInt(APPLICATION_IDENTIFIER_TAG), 1);
    TAG_ENCODING_MAP.append(tagToInt(PDOL_TAG), 1);
    TAG_ENCODING_MAP.append(tagToInt(SFI_TAG), 1);
    TAG_ENCODING_MAP.append(tagToInt(APPLICATION_INTERCHANGE_PROFILE_TAG), 1);
    TAG_ENCODING_MAP.append(tagToInt(APPLICATION_FILE_LOCATOR_TAG), 1);
    TAG_ENCODING_MAP.append(tagToInt(GPO_RESPONSE_CONSTRUCTED_TAG), 1);
    TAG_ENCODING_MAP.append(tagToInt(GPO_RESPONSE_PRIMITIVE_TAG), 1);
    TAG_ENCODING_MAP.append(tagToInt(READ_RECORD_RESPONSE_TAG), 1);
    TAG_ENCODING_MAP.append(tagToInt(TRACK_1_DATA_TAG), 2);
    TAG_ENCODING_MAP.append(tagToInt(TRACK_2_EQV_DATA_TAG), 1);
    TAG_ENCODING_MAP.append(tagToInt(TRACK_2_DATA_TAG), 1);
    TAG_ENCODING_MAP.append(tagToInt(PAN_TAG), 3);
    TAG_ENCODING_MAP.append(tagToInt(CARDHOLDER_NAME_TAG), 2);
    TAG_ENCODING_MAP.append(tagToInt(EXP_DATE_TAG), 3);
    TAG_ENCODING_MAP.append(tagToInt(CARDHOLDER_NAME_EXTENDED_TAG), 2);
  }
  
  public static byte[] buildPdolData(TlvDatum paramTlvDatum)
  {
    byte[] arrayOfByte;
    if (paramTlvDatum == null) {
      arrayOfByte = new byte[0];
    }
    for (;;)
    {
      return arrayOfByte;
      int i = 0;
      List localList = TlvParser.parseDol(paramTlvDatum.value, 0);
      int j = 0;
      int k = localList.size();
      while (j < k)
      {
        i += DataTypeConversionUtil.unsignedByteToInt(((TlObject)localList.get(j)).length);
        j++;
      }
      arrayOfByte = new byte[i + 2];
      arrayOfByte[0] = -125;
      arrayOfByte[1] = ((byte)i);
      for (int m = 0; m < i; m++) {
        arrayOfByte[(m + 2)] = 0;
      }
    }
  }
  
  public static TlvDatum findNestedTlv(TlvDatum paramTlvDatum, byte[] paramArrayOfByte)
  {
    if (Arrays.equals(paramTlvDatum.tag, paramArrayOfByte)) {
      return paramTlvDatum;
    }
    int i = 0;
    int j = paramTlvDatum.subTlvData.size();
    while (i < j)
    {
      TlvDatum localTlvDatum = findNestedTlv((TlvDatum)paramTlvDatum.subTlvData.get(i), paramArrayOfByte);
      if (localTlvDatum != null) {
        return localTlvDatum;
      }
      i++;
    }
    return null;
  }
  
  public static byte[] getAflValue(ResponseApdu paramResponseApdu)
  {
    TlvDatum localTlvDatum1 = TlvParser.parseTlv(paramResponseApdu);
    if (localTlvDatum1 == null) {
      return new byte[0];
    }
    if (Arrays.equals(localTlvDatum1.tag, GPO_RESPONSE_CONSTRUCTED_TAG))
    {
      TlvDatum localTlvDatum2 = findNestedTlv(localTlvDatum1, APPLICATION_FILE_LOCATOR_TAG);
      if (localTlvDatum2 == null) {
        return new byte[0];
      }
      return localTlvDatum2.value;
    }
    if (Arrays.equals(localTlvDatum1.tag, GPO_RESPONSE_PRIMITIVE_TAG))
    {
      if (localTlvDatum1.value.length < 2) {
        return new byte[0];
      }
      return Arrays.copyOfRange(localTlvDatum1.value, 2, localTlvDatum1.value.length);
    }
    return new byte[0];
  }
  
  private static String getNameFromTlvString(String paramString)
  {
    String str = NAME_SPLIT_PATTERN.matcher(paramString).replaceAll("");
    if (TextUtils.getTrimmedLength(str) == 0) {
      str = "";
    }
    return str;
  }
  
  public static String getValue(TlvDatum paramTlvDatum)
  {
    if (paramTlvDatum == null) {
      return null;
    }
    switch (TAG_ENCODING_MAP.get(tagToInt(paramTlvDatum.tag), 1))
    {
    default: 
      return DataTypeConversionUtil.byteArrayToHexString(paramTlvDatum.value);
    }
    return DataTypeConversionUtil.byteArrayToAscii(paramTlvDatum.value);
  }
  
  public static CreditCardNfcResult parseCreditCardInfoFromTlv(TlvDatum paramTlvDatum)
  {
    if (!Arrays.equals(paramTlvDatum.tag, READ_RECORD_RESPONSE_TAG)) {
      return null;
    }
    String str1 = "";
    String str2 = "";
    TlvDatum localTlvDatum1 = findNestedTlv(paramTlvDatum, PAN_TAG);
    if (localTlvDatum1 != null) {
      str1 = getValue(localTlvDatum1);
    }
    TlvDatum localTlvDatum2 = findNestedTlv(paramTlvDatum, CARDHOLDER_NAME_TAG);
    if (localTlvDatum2 != null) {
      str2 = getNameFromTlvString(getValue(localTlvDatum2));
    }
    TlvDatum localTlvDatum3 = findNestedTlv(paramTlvDatum, CARDHOLDER_NAME_EXTENDED_TAG);
    if (localTlvDatum3 != null) {
      str2 = getNameFromTlvString(getValue(localTlvDatum3));
    }
    TlvDatum localTlvDatum4 = findNestedTlv(paramTlvDatum, EXP_DATE_TAG);
    int i = 0;
    int j = 0;
    if (localTlvDatum4 != null)
    {
      String str6 = getValue(localTlvDatum4);
      i = Integer.parseInt(str6.substring(2, 4));
      j = Integer.parseInt(str6.substring(0, 2));
    }
    TlvDatum localTlvDatum5 = findNestedTlv(paramTlvDatum, TRACK_1_DATA_TAG);
    if (localTlvDatum5 != null)
    {
      Matcher localMatcher3 = TRACK_1_PATTERN.matcher(getValue(localTlvDatum5));
      if (localMatcher3.find())
      {
        str1 = localMatcher3.group(1);
        str2 = getNameFromTlvString(localMatcher3.group(2));
        String str5 = localMatcher3.group(3);
        i = Integer.parseInt(str5.substring(2, 4));
        j = Integer.parseInt(str5.substring(0, 2));
      }
    }
    TlvDatum localTlvDatum6 = findNestedTlv(paramTlvDatum, TRACK_2_DATA_TAG);
    if (localTlvDatum6 != null)
    {
      Matcher localMatcher2 = TRACK_2_PATTERN.matcher(getValue(localTlvDatum6));
      if (localMatcher2.find())
      {
        str1 = localMatcher2.group(1);
        String str4 = localMatcher2.group(2);
        i = Integer.parseInt(str4.substring(2, 4));
        j = Integer.parseInt(str4.substring(0, 2));
      }
    }
    TlvDatum localTlvDatum7 = findNestedTlv(paramTlvDatum, TRACK_2_EQV_DATA_TAG);
    if (localTlvDatum7 != null)
    {
      Matcher localMatcher1 = TRACK_2_EQV_PATTERN.matcher(getValue(localTlvDatum7));
      if (localMatcher1.find())
      {
        str1 = localMatcher1.group(1);
        String str3 = localMatcher1.group(2);
        i = Integer.parseInt(str3.substring(2, 4));
        j = Integer.parseInt(str3.substring(0, 2));
      }
    }
    if ((str2 != null) || (str1 != null) || ((i > 0) && (j > 0))) {
      return new CreditCardNfcResult(str1, i, j, str2);
    }
    return null;
  }
  
  private static int tagToInt(byte[] paramArrayOfByte)
  {
    int i = 0;
    int j = 0;
    int k = paramArrayOfByte.length;
    while (j < k)
    {
      i = i << 8 | paramArrayOfByte[j];
      j++;
    }
    return i | paramArrayOfByte.length << 16;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.nfc.communication.TlvUtil
 * JD-Core Version:    0.7.0.1
 */