package com.google.android.wallet.common.address;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.wallet.uicomponents.R.id;
import com.google.android.wallet.uicomponents.R.string;
import com.google.location.country.Postaladdress.PostalAddress;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.UnknownFormatConversionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;

public final class AddressUtils
{
  private static final char[] ALL_ADDRESS_FIELDS;
  private static final Pattern ID_SEPARATOR = Pattern.compile("/");
  public static final Pattern LANGUAGE_CODE_SEPARATORS = Pattern.compile("[_-]");
  private static final Pattern LANGUAGE_SEPARATOR = Pattern.compile("--");
  private static final Pattern POSTAL_CODE_NUMERIC_CHARS;
  private static final Pattern POSTAL_CODE_STATIC_VALUE;
  
  static
  {
    ALL_ADDRESS_FIELDS = AddressField.values();
    POSTAL_CODE_NUMERIC_CHARS = Pattern.compile("(\\\\d|\\d|[^\\^\\w])");
    POSTAL_CODE_STATIC_VALUE = Pattern.compile("^[\\w \\-]+$");
  }
  
  public static char[] determineAddressFieldsToDisplay(String paramString)
    throws UnknownFormatConversionException
  {
    if (TextUtils.isEmpty(paramString)) {
      throw new UnknownFormatConversionException("Cannot convert null/empty formats");
    }
    ArrayList localArrayList1 = new ArrayList();
    Iterator localIterator1 = getFormatSubStrings(paramString).iterator();
    while (localIterator1.hasNext())
    {
      String str = (String)localIterator1.next();
      if ((str.matches("%.")) && (!str.equals("%n"))) {
        localArrayList1.add(Character.valueOf(str.charAt(1)));
      }
    }
    ArrayList localArrayList2 = new ArrayList();
    Iterator localIterator2 = localArrayList1.iterator();
    while (localIterator2.hasNext())
    {
      char c = ((Character)localIterator2.next()).charValue();
      if (c == 'A')
      {
        localArrayList2.add(Character.valueOf('1'));
        localArrayList2.add(Character.valueOf('2'));
        localArrayList2.add(Character.valueOf('3'));
      }
      else
      {
        localArrayList2.add(Character.valueOf(c));
      }
    }
    char[] arrayOfChar = new char[localArrayList2.size()];
    for (int i = 0; i < arrayOfChar.length; i++) {
      arrayOfChar[i] = ((Character)localArrayList2.get(i)).charValue();
    }
    return arrayOfChar;
  }
  
  public static boolean doesCountryUseNumericPostalCode(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null) {}
    String str;
    do
    {
      return false;
      str = getAddressData(paramJSONObject, "zip");
    } while ((TextUtils.isEmpty(str)) || (POSTAL_CODE_NUMERIC_CHARS.matcher(str).replaceAll("").length() != 0));
    return true;
  }
  
  public static String getAddressData(JSONObject paramJSONObject, String paramString)
  {
    if ((paramJSONObject == null) || (paramString == null)) {
      return null;
    }
    return paramJSONObject.optString(paramString, null);
  }
  
  public static String[] getAddressDataArray(JSONObject paramJSONObject, String paramString)
  {
    String str = getAddressData(paramJSONObject, paramString);
    if (str == null) {
      return null;
    }
    return str.split("~");
  }
  
  public static int getAddressFieldViewId(char paramChar)
  {
    switch (paramChar)
    {
    default: 
      return 0;
    case '1': 
      return R.id.address_field_address_line_1;
    case '2': 
      return R.id.address_field_address_line_2;
    case '3': 
      return R.id.address_field_address_line_3;
    case 'S': 
      return R.id.address_field_admin_area;
    case 'R': 
      return R.id.address_field_country;
    case 'D': 
      return R.id.address_field_dependent_locality;
    case 'C': 
      return R.id.address_field_locality;
    case 'O': 
      return R.id.address_field_organization;
    case 'Z': 
      return R.id.address_field_postal_code;
    case 'N': 
      return R.id.address_field_recipient;
    case 'X': 
      return R.id.address_field_sorting_code;
    }
    return R.id.address_field_address_line_1;
  }
  
  public static int getAddressFormFieldViewId(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      throw new IllegalArgumentException("Unexpected addressFormField: " + paramInt);
    case 1: 
      return R.id.address_field_country;
    case 2: 
      return R.id.address_field_recipient;
    case 3: 
      return R.id.address_field_address_line_1;
    case 4: 
      return R.id.address_field_address_line_2;
    case 5: 
      return R.id.address_field_locality;
    case 6: 
      return R.id.address_field_admin_area;
    case 7: 
      return R.id.address_field_postal_code;
    case 9: 
      return R.id.address_field_sorting_code;
    case 10: 
      return R.id.address_field_dependent_locality;
    case 11: 
      return R.id.address_field_organization;
    }
    return R.id.address_field_phone_number;
  }
  
  public static String getAdminAreaForPostalCode(JSONObject paramJSONObject, String paramString)
  {
    if ((paramJSONObject == null) || (TextUtils.isEmpty(paramString))) {
      return null;
    }
    String[] arrayOfString1 = { paramString };
    String[] arrayOfString2 = new String[1];
    Pattern localPattern = getPostalCodeRegexpPattern(paramJSONObject);
    String[] arrayOfString3;
    int i;
    label68:
    int j;
    int m;
    if (localPattern != null)
    {
      String[] arrayOfString4 = getAddressDataArray(paramJSONObject, "sub_zips");
      if ((arrayOfString4 == null) || (arrayOfString4.length == 0))
      {
        arrayOfString3 = arrayOfString2;
        return arrayOfString3[0];
      }
      i = 0;
      if (i <= 0)
      {
        String str = arrayOfString1[0];
        if ((!TextUtils.isEmpty(str)) && (localPattern.matcher(str).matches()))
        {
          j = -1;
          int k = 0;
          m = 0;
          int n = arrayOfString4.length;
          label113:
          if (m < n)
          {
            Matcher localMatcher = getPostalCodeRegexpPatternForAdminArea(arrayOfString4[m]).matcher(str);
            if (!localMatcher.matches()) {
              break label231;
            }
            int i2 = localMatcher.group(1).length();
            if ((j != -1) && (i2 <= k)) {
              break label231;
            }
            k = i2;
          }
        }
      }
    }
    label231:
    for (int i1 = m;; i1 = j)
    {
      m++;
      j = i1;
      break label113;
      if (j >= 0)
      {
        String[] arrayOfString5 = getAddressDataArray(paramJSONObject, "sub_keys");
        if ((arrayOfString5 != null) && (j < arrayOfString5.length)) {
          arrayOfString2[0] = arrayOfString5[j];
        }
      }
      i++;
      break label68;
      arrayOfString3 = arrayOfString2;
      break;
    }
  }
  
  public static String getAlternativeLanguageCode(JSONObject paramJSONObject, String paramString)
  {
    String[] arrayOfString = getAddressDataArray(paramJSONObject, "languages");
    String str1;
    if ((arrayOfString == null) || (arrayOfString.length <= 1))
    {
      str1 = null;
      return str1;
    }
    String str2 = getAddressData(paramJSONObject, "lang");
    if (TextUtils.isEmpty(str2)) {
      return null;
    }
    if (isSameLanguage(str2, paramString)) {
      return null;
    }
    int i = arrayOfString.length;
    for (int j = 0;; j++)
    {
      if (j >= i) {
        break label83;
      }
      str1 = arrayOfString[j];
      if (isSameLanguage(str1, paramString)) {
        break;
      }
    }
    label83:
    return null;
  }
  
  public static String getDefaultPostalCodeForCountry(JSONObject paramJSONObject)
  {
    String str;
    if (paramJSONObject == null) {
      str = null;
    }
    for (;;)
    {
      return str;
      str = getAddressData(paramJSONObject, "zip");
      if (TextUtils.isEmpty(str)) {}
      for (boolean bool = false; !bool; bool = POSTAL_CODE_STATIC_VALUE.matcher(str).matches()) {
        return null;
      }
    }
  }
  
  public static String getDisplayCountryForDefaultLocale(int paramInt)
  {
    Locale localLocale = Locale.getDefault();
    return new Locale(localLocale.getLanguage(), RegionCode.toCountryCode(paramInt), localLocale.getVariant()).getDisplayCountry();
  }
  
  private static ArrayList<String> getFormatSubStrings(String paramString)
    throws UnknownFormatConversionException
  {
    ArrayList localArrayList = new ArrayList();
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
        if ("%n".equals("%" + c)) {
          localArrayList.add("%n");
        }
      }
      for (;;)
      {
        k++;
        break;
        if (!AddressField.exists(c)) {
          throw new UnknownFormatConversionException("Cannot determine AddressField for '" + c + "'");
        }
        localArrayList.add("%" + c);
        i = 0;
        continue;
        if (c == '%') {
          i = 1;
        } else {
          localArrayList.add(Character.toString(c));
        }
      }
    }
    return localArrayList;
  }
  
  public static Pattern getPostalCodeRegexpPattern(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null) {}
    String str1;
    String str2;
    do
    {
      do
      {
        return null;
        str1 = getAddressData(paramJSONObject, "zip");
      } while (TextUtils.isEmpty(str1));
      str2 = getAddressData(paramJSONObject, "id");
    } while (TextUtils.isEmpty(str2));
    switch (ID_SEPARATOR.split(str2).length)
    {
    default: 
      return null;
    case 2: 
      return Pattern.compile(str1, 2);
    }
    return getPostalCodeRegexpPatternForAdminArea(str1);
  }
  
  public static Pattern getPostalCodeRegexpPatternForAdminArea(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    return Pattern.compile("(" + paramString + ").*", 2);
  }
  
  public static int getRegionCodeFromAddressData(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null) {}
    String str;
    do
    {
      return 0;
      str = getAddressData(paramJSONObject, "id");
    } while (str == null);
    String[] arrayOfString = ID_SEPARATOR.split(str);
    switch (arrayOfString.length)
    {
    default: 
      throw new IllegalArgumentException("Invalid address data id: " + str);
    }
    return RegionCode.safeToRegionCode(LANGUAGE_SEPARATOR.split(arrayOfString[1])[0]);
  }
  
  public static boolean isAddressFieldRequired(char paramChar, JSONObject paramJSONObject)
  {
    if (paramChar == 'N') {
      return true;
    }
    String str = getAddressData(paramJSONObject, "require");
    if (TextUtils.isEmpty(str)) {
      return false;
    }
    char c = paramChar;
    if (paramChar == '1') {
      c = 'A';
    }
    return str.contains(String.valueOf(c));
  }
  
  public static boolean isSameLanguage(String paramString1, String paramString2)
  {
    if ((TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2))) {
      return false;
    }
    return LANGUAGE_CODE_SEPARATORS.split(paramString1)[0].equalsIgnoreCase(LANGUAGE_CODE_SEPARATORS.split(paramString2)[0]);
  }
  
  private static boolean isSubsetOf(Postaladdress.PostalAddress paramPostalAddress1, Postaladdress.PostalAddress paramPostalAddress2, char[] paramArrayOfChar)
  {
    boolean bool;
    if ((paramPostalAddress1 == null) || (paramPostalAddress2 == null) || (paramArrayOfChar == null)) {
      bool = false;
    }
    for (;;)
    {
      return bool;
      bool = false;
      int i = paramArrayOfChar.length;
      for (int j = 0; j < i; j++)
      {
        char c = paramArrayOfChar[j];
        if (c != 0)
        {
          String str = AddressFormatter.formatAddressValue(paramPostalAddress1, c);
          if (!TextUtils.isEmpty(str))
          {
            if (!str.equals(AddressFormatter.formatAddressValue(paramPostalAddress2, c))) {
              break label81;
            }
            bool = true;
          }
        }
      }
    }
    label81:
    return false;
  }
  
  public static String makeAddressFieldLabel(Context paramContext, char paramChar, JSONObject paramJSONObject)
  {
    switch (paramChar)
    {
    default: 
      return null;
    case '1': 
      return paramContext.getString(R.string.wallet_uic_address_field_address_line_1);
    case '2': 
      return paramContext.getString(R.string.wallet_uic_address_field_address_line_2);
    case '3': 
      return paramContext.getString(R.string.wallet_uic_address_field_address_line_3);
    case 'S': 
      String str2 = getAddressData(paramJSONObject, "state_name_type");
      if ("province".equals(str2)) {
        return paramContext.getString(R.string.wallet_uic_address_field_admin_area_province);
      }
      if ("state".equals(str2)) {
        return paramContext.getString(R.string.wallet_uic_address_field_admin_area_state);
      }
      if ("area".equals(str2)) {
        return paramContext.getString(R.string.wallet_uic_address_field_admin_area_area);
      }
      if ("county".equals(str2)) {
        return paramContext.getString(R.string.wallet_uic_address_field_admin_area_county);
      }
      if ("department".equals(str2)) {
        return paramContext.getString(R.string.wallet_uic_address_field_admin_area_department);
      }
      if ("district".equals(str2)) {
        return paramContext.getString(R.string.wallet_uic_address_field_admin_area_district);
      }
      if ("do_si".equals(str2)) {
        return paramContext.getString(R.string.wallet_uic_address_field_admin_area_do_si);
      }
      if ("emirate".equals(str2)) {
        return paramContext.getString(R.string.wallet_uic_address_field_admin_area_emirate);
      }
      if ("island".equals(str2)) {
        return paramContext.getString(R.string.wallet_uic_address_field_admin_area_island);
      }
      if ("parish".equals(str2)) {
        return paramContext.getString(R.string.wallet_uic_address_field_admin_area_parish);
      }
      if ("prefecture".equals(str2)) {
        return paramContext.getString(R.string.wallet_uic_address_field_admin_area_prefecture);
      }
      if ("region".equals(str2)) {
        return paramContext.getString(R.string.wallet_uic_address_field_admin_area_region);
      }
      return paramContext.getString(R.string.wallet_uic_address_field_admin_area_province);
    case 'R': 
      return paramContext.getString(R.string.wallet_uic_address_field_country);
    case 'D': 
      return paramContext.getString(R.string.wallet_uic_address_field_dependent_locality);
    case 'C': 
      return paramContext.getString(R.string.wallet_uic_address_field_locality);
    case 'O': 
      return paramContext.getString(R.string.wallet_uic_address_field_organization);
    case 'Z': 
      String str1 = getAddressData(paramJSONObject, "zip_name_type");
      if ("postal".equals(str1)) {
        return paramContext.getString(R.string.wallet_uic_address_field_postal_code);
      }
      if ("zip".equals(str1)) {
        return paramContext.getString(R.string.wallet_uic_address_field_zip_code);
      }
      return paramContext.getString(R.string.wallet_uic_address_field_postal_code);
    case 'N': 
      throw new IllegalArgumentException("Recipient labels should be read from an AddressForm proto.");
    case 'X': 
      return paramContext.getString(R.string.wallet_uic_address_field_sorting_code);
    }
    return paramContext.getString(R.string.wallet_uic_address_field_address_line_1);
  }
  
  public static ArrayList<Postaladdress.PostalAddress> mergeAddresses(Collection<Postaladdress.PostalAddress> paramCollection, char[] paramArrayOfChar)
  {
    Object localObject;
    if (paramCollection == null)
    {
      localObject = null;
      return localObject;
    }
    label16:
    int j;
    if (paramArrayOfChar == null)
    {
      paramArrayOfChar = ALL_ADDRESS_FIELDS;
      localObject = new ArrayList(paramCollection);
      j = -1 + ((ArrayList)localObject).size();
      label33:
      if (j < 0) {}
    }
    for (int k = -1 + ((ArrayList)localObject).size();; k--) {
      if (k >= 0)
      {
        if ((j != k) && (isSubsetOf((Postaladdress.PostalAddress)((ArrayList)localObject).get(j), (Postaladdress.PostalAddress)((ArrayList)localObject).get(k), paramArrayOfChar))) {
          ((ArrayList)localObject).remove(j);
        }
      }
      else
      {
        j--;
        break label33;
        break;
        for (int i = 0; i < paramArrayOfChar.length; i++) {
          if (!AddressField.exists(paramArrayOfChar[i])) {
            paramArrayOfChar[i] = '\000';
          }
        }
        break label16;
      }
    }
  }
  
  public static int[] scrubAndSortRegionCodes(int[] paramArrayOfInt)
  {
    int[] arrayOfInt;
    if (paramArrayOfInt == null) {
      arrayOfInt = null;
    }
    for (;;)
    {
      return arrayOfInt;
      final SparseArray localSparseArray = new SparseArray(274);
      localSparseArray.put(858, "");
      ArrayList localArrayList = new ArrayList(paramArrayOfInt.length);
      int i = paramArrayOfInt.length;
      for (int j = 0; j < i; j++)
      {
        int i8 = paramArrayOfInt[j];
        if ((i8 != 0) && (i8 != 858)) {
          localArrayList.add(Integer.valueOf(i8));
        }
      }
      int k = localArrayList.size();
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        int i7 = ((Integer)localIterator.next()).intValue();
        String str = getDisplayCountryForDefaultLocale(i7);
        if (TextUtils.isEmpty(str))
        {
          Log.w("AddressUtils", "Region code '" + i7 + "' without label");
          str = "";
        }
        localSparseArray.put(i7, str);
      }
      Collections.sort(localArrayList, new Comparator() {});
      int m = 0;
      int n = 0;
      for (int i1 = 0; i1 < k; i1++)
      {
        int i6 = ((Integer)localArrayList.get(i1)).intValue();
        if (i6 != n)
        {
          n = i6;
          m++;
        }
      }
      int i2 = 0;
      arrayOfInt = new int[m];
      int i3 = 0;
      int i4 = 0;
      while (i3 < k)
      {
        int i5 = ((Integer)localArrayList.get(i3)).intValue();
        if (i5 != i2)
        {
          arrayOfInt[i4] = i5;
          i2 = i5;
          i4++;
        }
        i3++;
      }
    }
  }
  
  public static boolean shouldUseLatinScript(int paramInt, String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    Object localObject;
    switch (paramInt)
    {
    default: 
      localObject = null;
    }
    while ((!TextUtils.isEmpty((CharSequence)localObject)) && (!isSameLanguage((String)localObject, paramString)))
    {
      return true;
      localObject = "ar";
      continue;
      localObject = "hy";
      continue;
      localObject = "zh";
      continue;
      localObject = "ja";
      continue;
      localObject = "ko";
      continue;
      localObject = "ru";
      continue;
      localObject = "th";
      continue;
      localObject = "uk";
      continue;
      localObject = "vi";
    }
  }
  
  public static boolean shouldUseLatinScript(JSONObject paramJSONObject, String paramString)
  {
    if (paramJSONObject == null) {}
    String str;
    do
    {
      do
      {
        return false;
      } while ((TextUtils.isEmpty(paramString)) || ((!paramJSONObject.has("lname")) && (!paramJSONObject.has("sub_lnames")) && (!paramJSONObject.has("lfmt"))));
      str = getAddressData(paramJSONObject, "lang");
      if (TextUtils.isEmpty(str)) {
        return shouldUseLatinScript(getRegionCodeFromAddressData(paramJSONObject), paramString);
      }
      if (isSameLanguage(str, Locale.ENGLISH.getLanguage())) {
        return true;
      }
    } while (isSameLanguage(str, paramString));
    return true;
  }
  
  public static int[] stringArrayToRegionCodeArray(String[] paramArrayOfString)
  {
    int[] arrayOfInt;
    if (paramArrayOfString == null) {
      arrayOfInt = null;
    }
    for (;;)
    {
      return arrayOfInt;
      int i = paramArrayOfString.length;
      arrayOfInt = new int[i];
      for (int j = 0; j < i; j++) {
        arrayOfInt[j] = RegionCode.safeToRegionCode(paramArrayOfString[j]);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.address.AddressUtils
 * JD-Core Version:    0.7.0.1
 */