package com.google.android.finsky.billing;

import android.content.Context;
import android.graphics.Rect;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import com.android.i18n.addressinput.AddressData;
import com.android.i18n.addressinput.AddressData.Builder;
import com.android.i18n.addressinput.AddressField;
import com.android.i18n.addressinput.FormOptions;
import com.android.i18n.addressinput.FormOptions.Builder;
import com.google.android.finsky.api.DfeApiConfig;
import com.google.android.finsky.protos.Address;
import com.google.android.finsky.protos.CarrierBillingInstrumentStatus;
import com.google.android.finsky.protos.ChallengeProto.InputValidationError;
import com.google.android.finsky.protos.Instrument;
import com.google.android.finsky.protos.VendingProtos.PurchaseMetadataResponseProto.Countries.Country;
import com.google.android.finsky.utils.SetupWizardUtils.SetupWizardParams;
import com.google.android.finsky.utils.Sha1Util;
import com.google.android.play.utils.config.GservicesValue;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class BillingUtils
{
  private static final Pattern PATTERN_EM_TAG = Pattern.compile("<em>");
  private static final Pattern PATTERN_END_EM_TAG = Pattern.compile("</em>");
  
  public static AddressData addressDataFromInstrumentAddress(Address paramAddress)
  {
    AddressData.Builder localBuilder = new AddressData.Builder();
    if (paramAddress.postalCountry != null) {
      localBuilder.setCountry(paramAddress.postalCountry);
    }
    if (paramAddress.addressLine1 != null) {
      localBuilder.setAddressLine1(paramAddress.addressLine1);
    }
    if (paramAddress.addressLine2 != null) {
      localBuilder.setAddressLine2(paramAddress.addressLine2);
    }
    if (paramAddress.state != null) {
      localBuilder.setAdminArea(paramAddress.state);
    }
    if (paramAddress.city != null) {
      localBuilder.setLocality(paramAddress.city);
    }
    if (paramAddress.dependentLocality != null)
    {
      String str2 = paramAddress.dependentLocality;
      localBuilder.set(AddressField.DEPENDENT_LOCALITY, str2);
    }
    if (paramAddress.postalCode != null) {
      localBuilder.setPostalCode(paramAddress.postalCode);
    }
    if (paramAddress.sortingCode != null)
    {
      String str1 = paramAddress.sortingCode;
      localBuilder.set(AddressField.SORTING_CODE, str1);
    }
    if (paramAddress.name != null) {
      localBuilder.setRecipient(paramAddress.name);
    }
    if (paramAddress.languageCode != null) {
      localBuilder.mLanguage = paramAddress.languageCode;
    }
    return localBuilder.build();
  }
  
  public static ChallengeProto.InputValidationError createInputValidationError(int paramInt)
  {
    return createInputValidationError(paramInt, null);
  }
  
  public static ChallengeProto.InputValidationError createInputValidationError(int paramInt, String paramString)
  {
    ChallengeProto.InputValidationError localInputValidationError = new ChallengeProto.InputValidationError();
    localInputValidationError.inputField = paramInt;
    localInputValidationError.hasInputField = true;
    if (!TextUtils.isEmpty(paramString))
    {
      localInputValidationError.errorMessage = paramString;
      localInputValidationError.hasErrorMessage = true;
    }
    return localInputValidationError;
  }
  
  public static VendingProtos.PurchaseMetadataResponseProto.Countries.Country findCountry(String paramString, List<VendingProtos.PurchaseMetadataResponseProto.Countries.Country> paramList)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        VendingProtos.PurchaseMetadataResponseProto.Countries.Country localCountry = (VendingProtos.PurchaseMetadataResponseProto.Countries.Country)localIterator.next();
        if (paramString.equals(localCountry.countryCode)) {
          return localCountry;
        }
      }
    }
    if (paramList.size() > 0) {
      return (VendingProtos.PurchaseMetadataResponseProto.Countries.Country)paramList.get(0);
    }
    return null;
  }
  
  public static FormOptions getAddressFormOptions(int paramInt)
  {
    FormOptions.Builder localBuilder = new FormOptions.Builder().hide(AddressField.COUNTRY).hide(AddressField.RECIPIENT).hide(AddressField.ORGANIZATION).hide(AddressField.DEPENDENT_LOCALITY).hide(AddressField.SORTING_CODE);
    if (paramInt == 1)
    {
      localBuilder.hide(AddressField.ADDRESS_LINE_1).hide(AddressField.ADDRESS_LINE_2);
      localBuilder.hide(AddressField.LOCALITY);
      localBuilder.hide(AddressField.ADMIN_AREA).hide(AddressField.STREET_ADDRESS);
    }
    return localBuilder.build();
  }
  
  public static String getDefaultCountry(Context paramContext, String paramString)
  {
    if (TextUtils.isEmpty(null)) {
      paramString = ((TelephonyManager)paramContext.getSystemService("phone")).getSimCountryIso().toUpperCase();
    }
    if (TextUtils.isEmpty(paramString)) {
      paramString = "US";
    }
    return paramString;
  }
  
  public static int getFopVersion(Instrument paramInstrument)
  {
    if ((paramInstrument.instrumentFamily == 2) && (paramInstrument.carrierBillingStatus != null))
    {
      CarrierBillingInstrumentStatus localCarrierBillingInstrumentStatus = paramInstrument.carrierBillingStatus;
      if (localCarrierBillingInstrumentStatus.hasApiVersion) {
        return localCarrierBillingInstrumentStatus.apiVersion;
      }
    }
    return 0;
  }
  
  public static int getInstrumentManagerThemeResourceId()
  {
    return getInstrumentManagerThemeResourceId(null);
  }
  
  public static int getInstrumentManagerThemeResourceId(SetupWizardUtils.SetupWizardParams paramSetupWizardParams)
  {
    if (paramSetupWizardParams == null) {
      return 2131558915;
    }
    if (paramSetupWizardParams.mIsLightTheme) {
      return 2131558631;
    }
    return 2131558630;
  }
  
  public static String getRiskHeader()
  {
    return Sha1Util.secureHash(String.valueOf(DfeApiConfig.androidId.get()).getBytes());
  }
  
  public static <T extends View> T getTopMostView(ViewGroup paramViewGroup, Collection<T> paramCollection)
  {
    Pair localPair = null;
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      View localView = (View)localIterator.next();
      int i = getViewOffsetToChild(paramViewGroup, localView);
      if ((localPair == null) || (i < ((Integer)localPair.first).intValue())) {
        localPair = Pair.create(Integer.valueOf(i), localView);
      }
    }
    if (localPair != null) {
      return (View)localPair.second;
    }
    return null;
  }
  
  public static int getViewOffsetToChild(ViewGroup paramViewGroup, View paramView)
  {
    Rect localRect = new Rect();
    paramViewGroup.offsetDescendantRectToMyCoords(paramView, localRect);
    return localRect.top;
  }
  
  public static Address instrumentAddressFromAddressData(AddressData paramAddressData, int[] paramArrayOfInt)
  {
    Address localAddress = new Address();
    int i = 0;
    if (i < paramArrayOfInt.length)
    {
      switch (paramArrayOfInt[i])
      {
      }
      for (;;)
      {
        i++;
        break;
        if (paramAddressData.mPostalCountry != null)
        {
          localAddress.postalCountry = paramAddressData.mPostalCountry;
          localAddress.hasPostalCountry = true;
          continue;
          if (paramAddressData.mAddressLine1 != null)
          {
            localAddress.addressLine1 = paramAddressData.mAddressLine1;
            localAddress.hasAddressLine1 = true;
            continue;
            if (paramAddressData.mAddressLine2 != null)
            {
              localAddress.addressLine2 = paramAddressData.mAddressLine2;
              localAddress.hasAddressLine2 = true;
              continue;
              if (paramAddressData.mAdministrativeArea != null)
              {
                localAddress.state = paramAddressData.mAdministrativeArea;
                localAddress.hasState = true;
                continue;
                if (paramAddressData.mLocality != null)
                {
                  localAddress.city = paramAddressData.mLocality;
                  localAddress.hasCity = true;
                  continue;
                  if (paramAddressData.mDependentLocality != null)
                  {
                    localAddress.dependentLocality = paramAddressData.mDependentLocality;
                    localAddress.hasDependentLocality = true;
                    continue;
                    if (paramAddressData.mPostalCode != null)
                    {
                      localAddress.postalCode = paramAddressData.mPostalCode;
                      localAddress.hasPostalCode = true;
                      continue;
                      if (paramAddressData.mRecipient != null)
                      {
                        localAddress.name = paramAddressData.mRecipient;
                        localAddress.hasName = true;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    if (paramAddressData.mSortingCode != null)
    {
      localAddress.sortingCode = paramAddressData.mSortingCode;
      localAddress.hasSortingCode = true;
    }
    if (paramAddressData.mLanguageCode != null)
    {
      localAddress.languageCode = paramAddressData.mLanguageCode;
      localAddress.hasLanguageCode = true;
    }
    return localAddress;
  }
  
  public static Spanned parseHtmlAndColorizeEm(String paramString, int paramInt)
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(0xFFFFFF & paramInt);
    String str1 = String.format("#%06X", arrayOfObject);
    String str2 = PATTERN_EM_TAG.matcher(paramString).replaceAll("<b><font color=\"" + str1 + "\">");
    return Html.fromHtml(PATTERN_END_EM_TAG.matcher(str2).replaceAll("</font></b>"));
  }
  
  public static String replaceLocale(String paramString)
  {
    if (paramString.contains("%locale%"))
    {
      Locale localLocale = Locale.getDefault();
      paramString = paramString.replace("%locale%", localLocale.getLanguage() + "_" + localLocale.getCountry().toLowerCase());
    }
    return paramString;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.BillingUtils
 * JD-Core Version:    0.7.0.1
 */