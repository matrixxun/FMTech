package com.google.android.finsky.layout;

import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.android.i18n.addressinput.AddressField;
import com.android.i18n.addressinput.AddressProblems;
import com.android.i18n.addressinput.AddressUIComponent;
import com.android.i18n.addressinput.AddressUIComponent.UIComponent;
import com.android.i18n.addressinput.AddressWidget;
import com.android.i18n.addressinput.FormOptions;
import com.android.i18n.addressinput.FormOptions.Builder;
import com.android.i18n.addressinput.FormatInterpreter;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.billing.AddressMetadataCacheManager;
import com.google.android.finsky.billing.BillingUtils;
import com.google.android.finsky.config.G;
import com.google.android.finsky.placesapi.AdrMicroformatParser;
import com.google.android.finsky.placesapi.PlaceAutocompletePrediction;
import com.google.android.finsky.placesapi.PlaceAutocompleteRequest;
import com.google.android.finsky.placesapi.PlaceAutocompleteResponse;
import com.google.android.finsky.placesapi.PlaceDetailRequest;
import com.google.android.finsky.placesapi.PlaceDetailResponse;
import com.google.android.finsky.placesapi.PlacesService;
import com.google.android.finsky.placesapi.WhitelistedCountriesFlagParser;
import com.google.android.finsky.protos.Address;
import com.google.android.finsky.protos.BillingAddressSpec;
import com.google.android.finsky.protos.ChallengeProto.InputValidationError;
import com.google.android.finsky.protos.VendingProtos.PurchaseMetadataResponseProto.Countries.Country;
import com.google.android.finsky.utils.CachedLocationAccess;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.utils.config.GservicesValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BillingAddress
  extends LinearLayout
  implements OnHeightOffsetChangedListener
{
  private static String KEY_ADDRESS_SPEC = "address_spec";
  private static String KEY_SELECTED_COUNTRY = "selected_country";
  private AddressFieldsLayout mAddressPlaceholder;
  private BillingAddressSpec mAddressSpec;
  private AddressWidget mAddressWidget;
  private List<VendingProtos.PurchaseMetadataResponseProto.Countries.Country> mCountries;
  private BillingCountryChangeListener mCountryChangeListener;
  private Spinner mCountrySpinner;
  private boolean mCountrySpinnerSelectionSet = false;
  private EditText mEmailAddress;
  private EditText mFirstName;
  private EditText mLastName;
  private EditText mNameEntry;
  private OnHeightOffsetChangedListener mParentListener;
  private EditText mPhoneNumber;
  private VendingProtos.PurchaseMetadataResponseProto.Countries.Country mSelectedCountry;
  private AddressSuggestionProviderImpl mSuggestionProvider;
  private WhitelistedCountriesFlagParser mWhitelistedCountries;
  
  public BillingAddress(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    ((LayoutInflater)paramContext.getSystemService("layout_inflater")).inflate(2130968625, this, true);
  }
  
  public final void clearErrorMessage()
  {
    this.mNameEntry.setError(null);
    this.mFirstName.setError(null);
    this.mLastName.setError(null);
    this.mPhoneNumber.setError(null);
    this.mEmailAddress.setError(null);
    AddressWidget localAddressWidget = this.mAddressWidget;
    Iterator localIterator = localAddressWidget.mFormatInterpreter.getAddressFieldOrder(localAddressWidget.mScript, localAddressWidget.mCurrentRegion).iterator();
    while (localIterator.hasNext())
    {
      AddressField localAddressField = (AddressField)localIterator.next();
      AddressUIComponent localAddressUIComponent = (AddressUIComponent)localAddressWidget.mInputWidgets.get(localAddressField);
      if ((localAddressUIComponent != null) && (localAddressUIComponent.mUiType == AddressUIComponent.UIComponent.EDIT))
      {
        EditText localEditText = (EditText)localAddressUIComponent.mView;
        if (localEditText != null) {
          localEditText.setError(null);
        }
      }
    }
  }
  
  public final TextView displayError(ChallengeProto.InputValidationError paramInputValidationError)
  {
    Context localContext = getContext();
    String str = paramInputValidationError.errorMessage;
    Object localObject = null;
    AddressField localAddressField = null;
    switch (paramInputValidationError.inputField)
    {
    case 14: 
    default: 
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(paramInputValidationError.inputField);
      arrayOfObject[1] = paramInputValidationError.errorMessage;
      FinskyLog.d("InputValidationError that can't be displayed: type=%d, message=%s", arrayOfObject);
    }
    for (;;)
    {
      if (localAddressField != null)
      {
        if (this.mAddressWidget.getViewForField(localAddressField) == null) {
          break;
        }
        this.mAddressWidget.displayErrorMessageForInvalidEntryIn(localAddressField);
      }
      return localObject;
      localObject = this.mNameEntry;
      UiUtils.setErrorOnTextView(this.mNameEntry, localContext.getString(2131362359), str);
      localAddressField = null;
      continue;
      localObject = this.mFirstName;
      UiUtils.setErrorOnTextView(this.mFirstName, localContext.getString(2131362159), str);
      localAddressField = null;
      continue;
      localObject = this.mLastName;
      UiUtils.setErrorOnTextView(this.mLastName, localContext.getString(2131362287), str);
      localAddressField = null;
      continue;
      localObject = this.mPhoneNumber;
      UiUtils.setErrorOnTextView(this.mPhoneNumber, localContext.getString(2131362538), str);
      localAddressField = null;
      continue;
      localObject = this.mEmailAddress;
      UiUtils.setErrorOnTextView(this.mEmailAddress, localContext.getString(2131362107), str);
      localAddressField = null;
      continue;
      localAddressField = AddressField.ADMIN_AREA;
      localObject = null;
      continue;
      localAddressField = AddressField.LOCALITY;
      localObject = null;
      continue;
      FinskyLog.d("Input error ADDR_WHOLE_ADDRESS. Displaying at ADDRESS_LINE_1.", new Object[0]);
      localAddressField = AddressField.ADDRESS_LINE_1;
      localObject = null;
      continue;
      localAddressField = AddressField.ADDRESS_LINE_2;
      localObject = null;
      continue;
      localAddressField = AddressField.DEPENDENT_LOCALITY;
      localObject = null;
      continue;
      localAddressField = AddressField.POSTAL_CODE;
      localObject = null;
      continue;
      localAddressField = AddressField.COUNTRY;
      localObject = null;
    }
    EditText localEditText = this.mNameEntry;
    UiUtils.setErrorOnTextView(this.mNameEntry, localContext.getString(2131362359), str);
    return localEditText;
  }
  
  public Address getAddress()
  {
    Address localAddress = BillingUtils.instrumentAddressFromAddressData(this.mAddressWidget.getAddressData(), this.mAddressSpec.requiredField);
    if (this.mAddressSpec.billingAddressType != 1) {}
    for (boolean bool = true;; bool = false)
    {
      localAddress.deprecatedIsReduced = bool;
      localAddress.hasDeprecatedIsReduced = true;
      if (this.mPhoneNumber.getVisibility() == 0)
      {
        localAddress.phoneNumber = this.mPhoneNumber.getText().toString();
        localAddress.hasPhoneNumber = true;
      }
      if (this.mNameEntry.getVisibility() == 0)
      {
        localAddress.name = this.mNameEntry.getText().toString();
        localAddress.hasName = true;
      }
      if (this.mFirstName.getVisibility() == 0)
      {
        localAddress.firstName = this.mFirstName.getText().toString();
        localAddress.hasFirstName = true;
      }
      if (this.mLastName.getVisibility() == 0)
      {
        localAddress.lastName = this.mLastName.getText().toString();
        localAddress.hasLastName = true;
      }
      if (this.mEmailAddress.getVisibility() == 0)
      {
        localAddress.email = this.mEmailAddress.getText().toString();
        localAddress.hasEmail = true;
      }
      return localAddress;
    }
  }
  
  public List<ChallengeProto.InputValidationError> getAddressValidationErrors()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.mAddressWidget.getAddressProblems().mProblems.entrySet().iterator();
    if (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      int i;
      switch (2.$SwitchMap$com$android$i18n$addressinput$AddressField[((AddressField)localEntry.getKey()).ordinal()])
      {
      default: 
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = localEntry.getKey();
        FinskyLog.w("No equivalent for address widget field: %s", arrayOfObject);
        i = 13;
      }
      for (;;)
      {
        localArrayList.add(BillingUtils.createInputValidationError(i));
        break;
        i = 8;
        continue;
        i = 7;
        continue;
        i = 5;
        continue;
        i = 6;
        continue;
        i = 11;
        continue;
        i = 9;
        continue;
        i = 10;
      }
    }
    if ((this.mNameEntry.getVisibility() == 0) && (Utils.isEmptyOrSpaces(this.mNameEntry.getText()))) {
      localArrayList.add(BillingUtils.createInputValidationError(4, getContext().getString(2131362272)));
    }
    if ((this.mFirstName.getVisibility() == 0) && (Utils.isEmptyOrSpaces(this.mFirstName.getText()))) {
      localArrayList.add(BillingUtils.createInputValidationError(15, getContext().getString(2131362272)));
    }
    if ((this.mLastName.getVisibility() == 0) && (Utils.isEmptyOrSpaces(this.mLastName.getText()))) {
      localArrayList.add(BillingUtils.createInputValidationError(16, getContext().getString(2131362272)));
    }
    if ((this.mPhoneNumber.getVisibility() == 0) && (Utils.isEmptyOrSpaces(this.mPhoneNumber.getText()))) {
      localArrayList.add(BillingUtils.createInputValidationError(12, getContext().getString(2131362274)));
    }
    if (this.mEmailAddress.getVisibility() == 0)
    {
      Editable localEditable = this.mEmailAddress.getText();
      if (!Patterns.EMAIL_ADDRESS.matcher(localEditable).matches()) {
        localArrayList.add(BillingUtils.createInputValidationError(17, getContext().getString(2131362267)));
      }
    }
    return localArrayList;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mNameEntry = ((EditText)findViewById(2131755221));
    this.mFirstName = ((EditText)findViewById(2131755264));
    this.mLastName = ((EditText)findViewById(2131755265));
    this.mEmailAddress = ((EditText)findViewById(2131755268));
    this.mCountrySpinner = ((Spinner)findViewById(2131755266));
    this.mPhoneNumber = ((EditText)findViewById(2131755224));
    this.mAddressPlaceholder = ((AddressFieldsLayout)findViewById(2131755267));
    PlacesService localPlacesService = new PlacesService((String)G.placesApiKey.get(), Locale.getDefault().getLanguage(), new AdrMicroformatParser(getContext()));
    RequestQueue localRequestQueue = FinskyApp.get().mRequestQueue;
    new CachedLocationAccess();
    this.mSuggestionProvider = new AddressSuggestionProviderImpl(localPlacesService, localRequestQueue, CachedLocationAccess.getCachedLocation(getContext()));
    this.mWhitelistedCountries = new WhitelistedCountriesFlagParser(getContext());
  }
  
  public final void onHeightOffsetChanged(float paramFloat)
  {
    if (Build.VERSION.SDK_INT >= 11)
    {
      this.mPhoneNumber.setTranslationY(paramFloat);
      this.mEmailAddress.setTranslationY(paramFloat);
    }
    if (this.mParentListener != null) {
      this.mParentListener.onHeightOffsetChanged(paramFloat);
    }
  }
  
  public final void restoreInstanceState(Bundle paramBundle)
  {
    BillingAddressSpec localBillingAddressSpec = (BillingAddressSpec)ParcelableProto.getProtoFromBundle(paramBundle, KEY_ADDRESS_SPEC);
    if (localBillingAddressSpec != null)
    {
      this.mAddressSpec = localBillingAddressSpec;
      this.mSelectedCountry = ((VendingProtos.PurchaseMetadataResponseProto.Countries.Country)ParcelableProto.getProtoFromBundle(paramBundle, KEY_SELECTED_COUNTRY));
      setAddressSpec(this.mSelectedCountry, this.mAddressSpec, null);
      this.mAddressWidget.restoreInstanceState(paramBundle);
    }
  }
  
  public final void saveInstanceState(Bundle paramBundle)
  {
    paramBundle.putParcelable(KEY_ADDRESS_SPEC, ParcelableProto.forProto(this.mAddressSpec));
    paramBundle.putParcelable(KEY_SELECTED_COUNTRY, ParcelableProto.forProto(this.mSelectedCountry));
    if (this.mAddressWidget != null) {
      this.mAddressWidget.saveInstanceState(paramBundle);
    }
  }
  
  public final void setAddressSpec(VendingProtos.PurchaseMetadataResponseProto.Countries.Country paramCountry, BillingAddressSpec paramBillingAddressSpec, Address paramAddress)
  {
    if (!this.mCountrySpinnerSelectionSet)
    {
      int i6 = -1;
      int i7 = 0;
      Iterator localIterator = this.mCountries.iterator();
      while (localIterator.hasNext())
      {
        VendingProtos.PurchaseMetadataResponseProto.Countries.Country localCountry = (VendingProtos.PurchaseMetadataResponseProto.Countries.Country)localIterator.next();
        if (paramCountry.countryCode.equals(localCountry.countryCode)) {
          i6 = i7;
        }
        i7++;
      }
      if (i6 >= 0)
      {
        this.mCountrySpinner.setSelection(i6);
        this.mCountrySpinnerSelectionSet = true;
      }
      this.mCountrySpinner.setVisibility(0);
    }
    this.mSelectedCountry = paramCountry;
    this.mAddressSpec = paramBillingAddressSpec;
    int[] arrayOfInt2;
    FormOptions.Builder localBuilder;
    int j;
    label225:
    AddressField localAddressField;
    int i2;
    label299:
    int i4;
    if (paramBillingAddressSpec.requiredField.length == 0)
    {
      if (paramBillingAddressSpec.billingAddressType == 1)
      {
        arrayOfInt2 = new int[] { 4, 10, 9, 5, 6, 8, 7, 12 };
        paramBillingAddressSpec.requiredField = arrayOfInt2;
      }
    }
    else
    {
      int[] arrayOfInt1 = paramBillingAddressSpec.requiredField;
      localBuilder = new FormOptions.Builder();
      localBuilder.hide(AddressField.COUNTRY).hide(AddressField.RECIPIENT).hide(AddressField.ORGANIZATION);
      AddressField[] arrayOfAddressField = AddressField.values();
      int i = arrayOfAddressField.length;
      j = 0;
      if (j >= i) {
        break label467;
      }
      localAddressField = arrayOfAddressField[j];
      switch (2.$SwitchMap$com$android$i18n$addressinput$AddressField[localAddressField.ordinal()])
      {
      default: 
        i2 = -1;
        if (i2 != -1)
        {
          i4 = 0;
          label308:
          if (i4 < arrayOfInt1.length) {
            if (arrayOfInt1[i4] != i2) {
              break;
            }
          }
        }
        break;
      }
    }
    for (int i3 = 0;; i3 = 1)
    {
      if (i3 != 0) {
        localBuilder.hide(localAddressField);
      }
      j++;
      break label225;
      boolean bool = ((Boolean)G.reducedBillingAddressRequiresPhonenumber.get()).booleanValue();
      if (bool) {}
      for (int i5 = 4;; i5 = 3)
      {
        arrayOfInt2 = new int[i5];
        arrayOfInt2[0] = 4;
        arrayOfInt2[1] = 10;
        arrayOfInt2[2] = 9;
        if (!bool) {
          break;
        }
        arrayOfInt2[3] = 12;
        break;
      }
      i2 = 8;
      break label299;
      i2 = 7;
      break label299;
      i2 = 5;
      break label299;
      i2 = 6;
      break label299;
      i2 = 11;
      break label299;
      i2 = 9;
      break label299;
      i2 = 10;
      break label299;
      i4++;
      break label308;
      label467:
      FormOptions localFormOptions = localBuilder.build();
      int k = 1;
      int m = 1;
      int n = 0;
      if (n < paramBillingAddressSpec.requiredField.length)
      {
        switch (paramBillingAddressSpec.requiredField[n])
        {
        }
        for (;;)
        {
          n++;
          break;
          this.mEmailAddress.setVisibility(0);
          continue;
          this.mNameEntry.setVisibility(8);
          this.mFirstName.setVisibility(0);
          continue;
          this.mNameEntry.setVisibility(8);
          this.mLastName.setVisibility(0);
          continue;
          k = 0;
          continue;
          m = 0;
        }
      }
      if (k != 0) {
        this.mPhoneNumber.setVisibility(8);
      }
      if (m != 0) {
        this.mCountrySpinner.setVisibility(8);
      }
      if (this.mAddressWidget == null)
      {
        this.mAddressWidget = new AddressWidget(getContext(), this.mAddressPlaceholder, localFormOptions, new AddressMetadataCacheManager(FinskyApp.get().mCache), this.mSelectedCountry.countryCode);
        this.mAddressWidget.renderForm();
      }
      if (paramAddress != null)
      {
        if (!TextUtils.isEmpty(paramAddress.name)) {
          this.mNameEntry.setText(paramAddress.name);
        }
        if (!TextUtils.isEmpty(paramAddress.firstName)) {
          this.mFirstName.setText(paramAddress.firstName);
        }
        if (!TextUtils.isEmpty(paramAddress.lastName)) {
          this.mLastName.setText(paramAddress.lastName);
        }
        if (!TextUtils.isEmpty(paramAddress.email)) {
          this.mEmailAddress.setText(paramAddress.email);
        }
        if (!TextUtils.isEmpty(paramAddress.phoneNumber)) {
          this.mPhoneNumber.setText(paramAddress.phoneNumber);
        }
        this.mAddressWidget.renderFormWithSavedAddress(BillingUtils.addressDataFromInstrumentAddress(paramAddress));
      }
      this.mAddressWidget.mFormOptions = localFormOptions;
      AddressWidget localAddressWidget = this.mAddressWidget;
      String str1 = this.mSelectedCountry.countryCode;
      if (!localAddressWidget.mCurrentRegion.equalsIgnoreCase(str1))
      {
        localAddressWidget.mSavedAddress = null;
        localAddressWidget.mCurrentRegion = str1;
        localAddressWidget.mFormController.mCurrentCountry = localAddressWidget.mCurrentRegion;
        localAddressWidget.renderForm();
      }
      this.mAddressPlaceholder.setOnHeightOffsetChangedListener(this);
      WhitelistedCountriesFlagParser localWhitelistedCountriesFlagParser = this.mWhitelistedCountries;
      String str2 = this.mSelectedCountry.countryCode;
      if ((localWhitelistedCountriesFlagParser.mEnabledCountries != null) && (localWhitelistedCountriesFlagParser.mEnabledCountries.contains(str2))) {}
      for (int i1 = 1; i1 != 0; i1 = 0)
      {
        this.mSuggestionProvider.mCountry = this.mSelectedCountry.countryCode;
        this.mAddressWidget.setSuggestionProvider(this.mSuggestionProvider);
        return;
      }
      this.mAddressWidget.setSuggestionProvider(null);
      return;
    }
  }
  
  public void setBillingCountries(List<VendingProtos.PurchaseMetadataResponseProto.Countries.Country> paramList)
  {
    this.mCountries = paramList;
    this.mCountrySpinner = ((Spinner)findViewById(2131755266));
    this.mCountrySpinner.setPrompt(getResources().getText(2131362719));
    this.mCountrySpinner.setOnItemSelectedListener(null);
    ArrayAdapter localArrayAdapter = new ArrayAdapter(getContext(), 17367048);
    localArrayAdapter.setDropDownViewResource(17367049);
    Iterator localIterator = this.mCountries.iterator();
    while (localIterator.hasNext()) {
      localArrayAdapter.add(new CountrySpinnerItem((VendingProtos.PurchaseMetadataResponseProto.Countries.Country)localIterator.next()));
    }
    this.mCountrySpinner.setAdapter(localArrayAdapter);
    this.mCountrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
    {
      public final void onItemSelected(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        VendingProtos.PurchaseMetadataResponseProto.Countries.Country localCountry = (VendingProtos.PurchaseMetadataResponseProto.Countries.Country)BillingAddress.this.mCountries.get(paramAnonymousInt);
        if ((BillingAddress.this.mSelectedCountry != null) && (BillingAddress.this.mSelectedCountry.countryCode.equals(localCountry.countryCode))) {}
        while (BillingAddress.this.mCountryChangeListener == null) {
          return;
        }
        BillingAddress.this.mCountryChangeListener.onBillingCountryChanged(localCountry);
      }
      
      public final void onNothingSelected(AdapterView<?> paramAnonymousAdapterView)
      {
        onItemSelected(paramAnonymousAdapterView, null, 0, 0L);
      }
    });
  }
  
  public void setBillingCountryChangeListener(BillingCountryChangeListener paramBillingCountryChangeListener)
  {
    this.mCountryChangeListener = paramBillingCountryChangeListener;
  }
  
  public void setDefaultName(String paramString)
  {
    if (this.mNameEntry.getText().length() == 0) {
      this.mNameEntry.setText(paramString);
    }
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    this.mNameEntry.setEnabled(paramBoolean);
    this.mFirstName.setEnabled(paramBoolean);
    this.mLastName.setEnabled(paramBoolean);
    this.mEmailAddress.setEnabled(paramBoolean);
    this.mCountrySpinner.setEnabled(paramBoolean);
    Iterator localIterator = this.mAddressWidget.mInputWidgets.values().iterator();
    while (localIterator.hasNext())
    {
      AddressUIComponent localAddressUIComponent = (AddressUIComponent)localIterator.next();
      if (localAddressUIComponent.mView != null) {
        localAddressUIComponent.mView.setEnabled(paramBoolean);
      }
    }
    this.mPhoneNumber.setEnabled(paramBoolean);
  }
  
  public void setInitializationStateListener(InitializationStateListener paramInitializationStateListener) {}
  
  public void setNameInputHint(int paramInt)
  {
    this.mNameEntry.setHint(paramInt);
  }
  
  public void setOnHeightOffsetChangedListener(OnHeightOffsetChangedListener paramOnHeightOffsetChangedListener)
  {
    this.mParentListener = paramOnHeightOffsetChangedListener;
  }
  
  public void setPhoneNumber(String paramString)
  {
    if ((!TextUtils.isEmpty(paramString)) && (TextUtils.isEmpty(this.mPhoneNumber.getText()))) {
      this.mPhoneNumber.setText(PhoneNumberUtils.formatNumber(paramString));
    }
  }
  
  private final class AddressSuggestionProviderImpl
    implements Response.ErrorListener, Response.Listener<PlaceDetailResponse>, AddressSuggestionProvider
  {
    String mCountry;
    private Location mLocation;
    private PlacesService mPlacesService;
    private RequestQueue mRequestQueue;
    
    AddressSuggestionProviderImpl(PlacesService paramPlacesService, RequestQueue paramRequestQueue, Location paramLocation)
    {
      this.mPlacesService = paramPlacesService;
      this.mRequestQueue = paramRequestQueue;
      this.mLocation = paramLocation;
    }
    
    public final List<PlaceAutocompletePrediction> getSuggestions(CharSequence paramCharSequence)
    {
      if ((paramCharSequence != null) && (this.mCountry != null))
      {
        RequestFuture localRequestFuture = RequestFuture.newFuture();
        PlacesService localPlacesService = this.mPlacesService;
        String str1 = paramCharSequence.toString();
        Location localLocation = this.mLocation;
        String str2 = this.mCountry;
        StringBuilder localStringBuilder = new StringBuilder("/maps/api/place/autocomplete/json?input=");
        localStringBuilder.append(Utils.urlEncode(str1.trim()));
        localStringBuilder.append("&language=").append(localPlacesService.mLanguage);
        localStringBuilder.append("&types=address");
        localStringBuilder.append("&components=country:").append(str2);
        if (localLocation != null)
        {
          localStringBuilder.append("&location=").append(localLocation.getLatitude()).append(',').append(localLocation.getLongitude());
          localStringBuilder.append("&radius=").append(G.placesApiBiasRadiusMeters.get());
        }
        PlaceAutocompleteRequest localPlaceAutocompleteRequest = new PlaceAutocompleteRequest(localPlacesService.buildRequestUrl(localStringBuilder), localRequestFuture, localRequestFuture);
        this.mRequestQueue.add(localPlaceAutocompleteRequest);
        try
        {
          List localList = ((PlaceAutocompleteResponse)localRequestFuture.get()).mPredictions;
          return localList;
        }
        catch (ExecutionException localExecutionException)
        {
          return null;
        }
        catch (InterruptedException localInterruptedException)
        {
          return null;
        }
      }
      return null;
    }
    
    public final void onErrorResponse(VolleyError paramVolleyError)
    {
      BillingAddress.this.mAddressWidget.mRootView.hideUpperRightProgressBar();
    }
    
    public final void onSuggestionAccepted(PlaceAutocompletePrediction paramPlaceAutocompletePrediction)
    {
      RequestQueue localRequestQueue = this.mRequestQueue;
      PlacesService localPlacesService = this.mPlacesService;
      String str = paramPlaceAutocompletePrediction.mReference;
      localRequestQueue.add(new PlaceDetailRequest(localPlacesService.buildRequestUrl(new StringBuilder("/maps/api/place/details/json?reference=").append(str).append("&language=").append(localPlacesService.mLanguage)), localPlacesService.mParser, this, this));
      BillingAddress.this.mAddressWidget.mRootView.showUpperRightProgressBar();
    }
  }
  
  public static abstract interface BillingCountryChangeListener
  {
    public abstract void onBillingCountryChanged(VendingProtos.PurchaseMetadataResponseProto.Countries.Country paramCountry);
  }
  
  public static final class CountrySpinnerItem
  {
    final VendingProtos.PurchaseMetadataResponseProto.Countries.Country mCountry;
    
    public CountrySpinnerItem(VendingProtos.PurchaseMetadataResponseProto.Countries.Country paramCountry)
    {
      this.mCountry = paramCountry;
    }
    
    public final String toString()
    {
      return this.mCountry.countryName;
    }
  }
  
  public static abstract interface InitializationStateListener {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.BillingAddress
 * JD-Core Version:    0.7.0.1
 */