package com.google.android.wallet.common.address;

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import com.google.location.country.Postaladdress.PostalAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class AddressFormatter
{
  private static final Pattern FORMATTING_CHARS_PATTERN = Pattern.compile("^[\\-,\\s]+|[\\-,\\s]+$");
  
  public static String formatAddress(Postaladdress.PostalAddress paramPostalAddress, String paramString, char[] paramArrayOfChar1, char[] paramArrayOfChar2)
  {
    if (paramString == null) {
      paramString = "\n";
    }
    if ((paramArrayOfChar1 == null) && (paramArrayOfChar2 != null) && (paramArrayOfChar2.length > 0)) {
      paramArrayOfChar1 = AddressField.values();
    }
    int i = RegionCode.safeToRegionCode(paramPostalAddress.countryNameCode);
    if (i == 0) {
      i = 858;
    }
    String str1 = paramPostalAddress.languageCode;
    boolean bool = AddressUtils.shouldUseLatinScript(i, str1);
    String str2;
    StringBuilder localStringBuilder1;
    switch (i)
    {
    default: 
      str2 = "%N%n%O%n%A%n%C";
      localStringBuilder1 = new StringBuilder(4 + str2.length());
      if ((!bool) && ((AddressUtils.isSameLanguage("ja", str1)) || (AddressUtils.isSameLanguage("ko", str1)) || (AddressUtils.isSameLanguage("zh", str1))))
      {
        localStringBuilder1.append("%R%n");
        localStringBuilder1.append(str2);
      }
      break;
    }
    String str3;
    StringBuilder localStringBuilder2;
    StringBuilder localStringBuilder3;
    StringBuilder localStringBuilder4;
    int j;
    SparseBooleanArray localSparseBooleanArray;
    for (;;)
    {
      str3 = localStringBuilder1.toString();
      localStringBuilder2 = new StringBuilder();
      localStringBuilder3 = new StringBuilder();
      localStringBuilder4 = new StringBuilder();
      j = paramString.length();
      if (paramArrayOfChar1 == null) {
        break label2431;
      }
      localSparseBooleanArray = new SparseBooleanArray(1 + AddressField.count());
      int k = paramArrayOfChar1.length;
      for (int m = 0; m < k; m++)
      {
        char c3 = paramArrayOfChar1[m];
        if (AddressField.exists(c3)) {
          localSparseBooleanArray.put(c3, true);
        }
      }
      if (bool)
      {
        str2 = "%N%n%O%n%A%n%D%n%C%n%S, %Z";
        break;
      }
      str2 = "%Z%n%S%C%D%n%A%n%O%n%N";
      break;
      if (bool)
      {
        str2 = "%N%n%O%n%A%n%D%n%C%n%S%n%Z";
        break;
      }
      str2 = "%S %C%D%n%A%n%O%n%N%n%Z";
      break;
      if (bool)
      {
        str2 = "%N%n%O%n%A%n%C, %S %Z";
        break;
      }
      str2 = "%Z%n%S%C%n%A%n%O%n%N";
      break;
      if (bool)
      {
        str2 = "%N%n%O%n%A";
        break;
      }
      str2 = "%A%n%O%n%N";
      break;
      if (bool)
      {
        str2 = "%N%n%O%n%A%n%C, %S%n%Z";
        break;
      }
      str2 = "〒%Z%n%S%C%n%A%n%O%n%N";
      break;
      if (bool)
      {
        str2 = "%N%n%O%n%A%n%C%n%S";
        break;
      }
      str2 = "%S%n%C%n%A%n%O%n%N";
      break;
      if (bool)
      {
        str2 = "%N%n%O%n%A%n%D, %C%n%S %Z";
        break;
      }
      str2 = "%N%n%O%n%A%n%D %C%n%S %Z";
      break;
      str2 = "%N%n%O%n%A%n%Z %C%n%S";
      break;
      str2 = "%N%n%O%n%A%n%C - %Z";
      break;
      str2 = "%O%n%N%n%A%n%D%n%C-%S%n%Z";
      break;
      str2 = "%N%n%O%n%A%n%C %Z, %S";
      break;
      str2 = "%N%n%O%n%A%n%C%n%S%n%Z";
      break;
      str2 = "%N%n%O%n%A%n%S%n%C";
      break;
      str2 = "%N%n%O%n%A%n%S %Z";
      break;
      str2 = "%N%n%O%n%A%n%C %X%n%S";
      break;
      str2 = "%N%n%O%n%A%n%X%n%C%nGUERNSEY%n%Z";
      break;
      str2 = "%Z%n%S%n%C%n%A%n%O%n%N";
      break;
      str2 = "%N%n%O%n%A%n%C PR %Z";
      break;
      str2 = "%N%n%O%n%A%n%Z%n%C, %S";
      break;
      str2 = "%O%n%N%n%A%nSE-%Z %C";
      break;
      str2 = "%N%n%O%n%A%nSINGAPORE %Z";
      break;
      str2 = "%N%n%O%n%A%n%X%n%C%nJERSEY%n%Z";
      break;
      str2 = "%O%n%N%n%A%nFL-%Z %C";
      break;
      str2 = "%N%n%O%n%A%n%Z %C%n%D%n%S";
      break;
      str2 = "%N%n%O%n%A%n%X%n%C%n%S";
      break;
      str2 = "%N%n%O%n%A%n%D%n%Z %C, %S";
      break;
      str2 = "%N%n%O%n%A%nAZ %Z %C";
      break;
      str2 = "%N%n%O%n%A%nHT%Z %C %X";
      break;
      str2 = "%N%n%O%n%C%n%A%n%Z";
      break;
      str2 = "%N%n%O%n%A%n%C%n%S";
      break;
      str2 = "%N%n%O%n%A%n%Z %C %S";
      break;
      str2 = "%N%n%O%n%A%n%C, %Z";
      break;
      str2 = "%N%n%O%n%A%n%D, %C%n%Z %S";
      break;
      str2 = "%N%n%O%n%A%n%D%n%Z %C%n%S";
      break;
      str2 = "%N%n%O%n%A%n%C%n%Z";
      break;
      str2 = "%N%n%O%n%A%n%C%n%S %Z";
      break;
      str2 = "%N%n%O%n%A%n%C, %S %Z";
      break;
      str2 = "%N%n%O%n%A%n%C%n%S %X";
      break;
      str2 = "%N%n%O%n%A%n%Z- %C";
      break;
      str2 = "%N%n%O%n%A%n%C, %S";
      break;
      str2 = "%N%n%O%n%A%nMD-%Z %C";
      break;
      str2 = "%N%n%O%n%A%n%C-%Z";
      break;
      str2 = "%N%n%O%n%A%n%Z %C %X";
      break;
      str2 = "%O%n%N%n%A%nAX-%Z %C%nÅLAND";
      break;
      str2 = "%N%n%O%n%Z %A %C";
      break;
      str2 = "%N%n%O%n%A%n%Z %C/%S";
      break;
      str2 = "%N%n%O%n%A%n%Z%n%C%n%S";
      break;
      str2 = "%O%n%N%n%A%nCH-%Z %C";
      break;
      str2 = "%N%n%O%n%A%n%C, %S%n%Z";
      break;
      str2 = "%O%n%N%n%A%nL-%Z %C";
      break;
      str2 = "%N%n%O%n%X %A %C %X";
      break;
      str2 = "%N%n%O%n%A%n%D%n%C %Z";
      break;
      str2 = "%O%n%N%n%A%n%Z %C %X";
      break;
      str2 = "%N%n%O%n%A%n%X%n%C%n%Z";
      break;
      str2 = "%N%n%O%n%A%n%C %S %Z";
      break;
      str2 = "%O%n%N%n%A%n%Z %C";
      break;
      str2 = "%N%n%O%n%A%nSI- %Z %C";
      break;
      str2 = "%Z %C %X%n%A%n%O%n%N";
      break;
      str2 = "%S%n%Z %C %X%n%A%n%O%n%N";
      break;
      str2 = "%N%n%O%n%A%nMC-%Z %C %X";
      break;
      str2 = "%N%n%O%n%A%n%C %X";
      break;
      str2 = "%N%n%O%n%A%nFO%Z %C";
      break;
      str2 = "%N%n%O%n%A%n%C %Z";
      break;
      str2 = "%N%n%O%n%A%n%C %Z%n%S";
      break;
      str2 = "%N%n%O%n%A%n%Z%n%C";
      break;
      str2 = "%N%n%O%n%A%nHR-%Z %C";
      break;
      str2 = "%O%n%N%n%A%nLT-%Z %C";
      break;
      str2 = "%O%n%N%n%A%n%C, %S%n%Z";
      break;
      str2 = "%O%n%N%n%A%nFI-%Z %C";
      break;
      str2 = "%N%n%O%n%A%n%D%n%C%n%Z";
      break;
      str2 = "%N%n%O%n%A%nGIBRALTAR%n%Z";
      break;
      str2 = "%N%n%O%n%A%n%S %C-%X%n%Z";
      break;
      str2 = "%O%n%N%n%A%n%C %S %Z";
      break;
      str2 = "%N%n%O%n%A%n%Z %C";
      break;
      str2 = "%N%n%O%n%A%n%S";
      break;
      str2 = "%O%n%N%n%S%n%C, %D%n%A%n%Z";
      break;
      str2 = "%N%n%O%n%A%n%C %Z %S";
      break;
      str2 = "%N%n%O%n%A%n%Z-%C%n%S";
      break;
      str2 = "%N%n%O%n%A%n%C, %S, %Z";
      break;
      localStringBuilder1.append(str2);
      localStringBuilder1.append("%n%R");
    }
    if (paramArrayOfChar2 != null)
    {
      int i18 = paramArrayOfChar2.length;
      for (int i19 = 0; i19 < i18; i19++)
      {
        char c2 = paramArrayOfChar2[i19];
        if (AddressField.exists(c2)) {
          localSparseBooleanArray.put(c2, false);
        }
      }
      label2431:
      localSparseBooleanArray = null;
    }
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    char[] arrayOfChar = str3.replaceAll("%A", "%1%n%2%n%3").toCharArray();
    int i4 = arrayOfChar.length;
    int i5 = 0;
    int i6 = 0;
    int i7 = 0;
    char c1;
    int i16;
    int i15;
    if (i5 < i4)
    {
      c1 = arrayOfChar[i5];
      if (i7 != 0) {
        if ('n' == c1) {
          if (localStringBuilder3.length() > 0)
          {
            localStringBuilder3.append(localStringBuilder4);
            localStringBuilder2.append(FORMATTING_CHARS_PATTERN.matcher(localStringBuilder3).replaceAll(""));
            i16 = 1;
            localStringBuilder3.setLength(0);
            i15 = i2;
          }
        }
      }
    }
    for (;;)
    {
      label2551:
      if ((i16 != 0) && (j > 0))
      {
        localStringBuilder2.append(paramString);
        i6 = 1;
      }
      int i12 = n;
      int i17 = i15;
      int i8 = i1;
      int i9 = i6;
      int i10 = 0;
      int i11 = i17;
      label2594:
      localStringBuilder4.setLength(0);
      n = i12;
      i2 = i11;
      i7 = 0;
      int i13 = i10;
      i6 = i9;
      i1 = i8;
      i3 = i13;
      for (;;)
      {
        i5++;
        break;
        if ((localStringBuilder4.length() <= 0) || (i3 != 0)) {
          break label3104;
        }
        String str6 = FORMATTING_CHARS_PATTERN.matcher(localStringBuilder4).replaceAll("");
        if (str6.length() <= 0) {
          break label3104;
        }
        localStringBuilder2.append(str6);
        i16 = 1;
        i15 = 1;
        break label2551;
        if (AddressField.exists(c1))
        {
          if ((localSparseBooleanArray == null) || (localSparseBooleanArray.get(c1)))
          {
            String str5 = formatAddressValue(paramPostalAddress, c1, paramString);
            if (str5 != null) {
              str5 = str5.trim();
            }
            if (TextUtils.isEmpty(str5))
            {
              i8 = i1;
              i9 = i6;
              i10 = 1;
              i11 = i2;
              i12 = n;
              break label2594;
            }
            localStringBuilder3.append(localStringBuilder4.toString());
            localStringBuilder3.append(str5);
            if ('N' == c1)
            {
              int i14 = i3;
              i8 = i1;
              i9 = i6;
              i10 = i14;
              i11 = i2;
              i12 = 1;
              break label2594;
            }
            i9 = i6;
            i10 = i3;
            i8 = 1;
            i11 = i2;
            i12 = n;
            break label2594;
          }
          i8 = i1;
          i9 = i6;
          i10 = 1;
          i11 = i2;
          i12 = n;
          break label2594;
        }
        Log.w("AddressFormatter", "Could not format AddressField." + c1);
        i8 = i1;
        i9 = i6;
        i10 = 1;
        i11 = i2;
        i12 = n;
        break label2594;
        if (c1 == '%') {
          i7 = 1;
        } else {
          localStringBuilder4.append(c1);
        }
      }
      if ((localStringBuilder3.length() > 0) || (i3 == 0))
      {
        if ((localStringBuilder3.length() == 0) && (localStringBuilder4.length() > 0)) {
          i2 = 1;
        }
        localStringBuilder3.append(localStringBuilder4);
        localStringBuilder2.append(FORMATTING_CHARS_PATTERN.matcher(localStringBuilder3).replaceAll(""));
      }
      for (;;)
      {
        if (((localSparseBooleanArray == null) || (localSparseBooleanArray.get(82))) && ((localStringBuilder2.length() == 0) || ((n != 0) && (i1 == 0) && (i2 == 0))))
        {
          String str4 = formatAddressValue(paramPostalAddress, 'R', paramString);
          if (!TextUtils.isEmpty(str4))
          {
            if ((localStringBuilder2.length() > 0) && (j > 0)) {
              localStringBuilder2.append(paramString);
            }
            localStringBuilder2.append(str4);
          }
        }
        return localStringBuilder2.toString();
        if (i6 != 0) {
          localStringBuilder2.delete(localStringBuilder2.length() - j, localStringBuilder2.length());
        }
      }
      label3104:
      i15 = i2;
      i16 = 0;
    }
  }
  
  public static String formatAddressValue(Postaladdress.PostalAddress paramPostalAddress, char paramChar)
  {
    return formatAddressValue(paramPostalAddress, paramChar, null);
  }
  
  private static String formatAddressValue(Postaladdress.PostalAddress paramPostalAddress, char paramChar, String paramString)
  {
    if (paramPostalAddress == null) {}
    int i;
    do
    {
      do
      {
        do
        {
          do
          {
            do
            {
              do
              {
                do
                {
                  do
                  {
                    do
                    {
                      do
                      {
                        do
                        {
                          do
                          {
                            do
                            {
                              do
                              {
                                do
                                {
                                  do
                                  {
                                    do
                                    {
                                      return null;
                                      if (paramString == null) {
                                        paramString = "\n";
                                      }
                                      switch (paramChar)
                                      {
                                      default: 
                                        return null;
                                      }
                                    } while (paramPostalAddress.addressLine.length <= 0);
                                    return paramPostalAddress.addressLine[0];
                                  } while (TextUtils.isEmpty(paramPostalAddress.administrativeAreaName));
                                  return paramPostalAddress.administrativeAreaName;
                                } while (TextUtils.isEmpty(paramPostalAddress.localityName));
                                return paramPostalAddress.localityName;
                              } while (TextUtils.isEmpty(paramPostalAddress.recipientName));
                              return paramPostalAddress.recipientName;
                            } while (TextUtils.isEmpty(paramPostalAddress.firmName));
                            return paramPostalAddress.firmName;
                          } while (paramPostalAddress.addressLine.length < 2);
                          return paramPostalAddress.addressLine[1];
                        } while (paramPostalAddress.addressLine.length < 3);
                        return paramPostalAddress.addressLine[2];
                      } while (TextUtils.isEmpty(paramPostalAddress.dependentLocalityName));
                      return paramPostalAddress.dependentLocalityName;
                    } while (TextUtils.isEmpty(paramPostalAddress.postalCodeNumber));
                    return paramPostalAddress.postalCodeNumber;
                  } while (TextUtils.isEmpty(paramPostalAddress.sortingCode));
                  return paramPostalAddress.sortingCode;
                } while (paramPostalAddress.addressLine.length <= 0);
                return TextUtils.join(paramString, paramPostalAddress.addressLine);
              } while (TextUtils.isEmpty(paramPostalAddress.subAdministrativeAreaName));
              return paramPostalAddress.subAdministrativeAreaName;
            } while (TextUtils.isEmpty(paramPostalAddress.subPremiseName));
            return paramPostalAddress.subPremiseName;
          } while (TextUtils.isEmpty(paramPostalAddress.premiseName));
          return paramPostalAddress.premiseName;
        } while (TextUtils.isEmpty(paramPostalAddress.thoroughfareName));
        return paramPostalAddress.thoroughfareName;
      } while (TextUtils.isEmpty(paramPostalAddress.thoroughfareNumber));
      return paramPostalAddress.thoroughfareNumber;
      if (!TextUtils.isEmpty(paramPostalAddress.countryName)) {
        return paramPostalAddress.countryName;
      }
      i = RegionCode.safeToRegionCode(paramPostalAddress.countryNameCode);
    } while ((i == 0) || (858 == i));
    return AddressUtils.getDisplayCountryForDefaultLocale(i);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.address.AddressFormatter
 * JD-Core Version:    0.7.0.1
 */