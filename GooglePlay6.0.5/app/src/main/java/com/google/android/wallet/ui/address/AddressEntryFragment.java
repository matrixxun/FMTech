package com.google.android.wallet.ui.address;

import android.annotation.TargetApi;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RequestQueue.RequestFilter;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.wallet.analytics.UiNode;
import com.google.android.wallet.analytics.WalletUiElement;
import com.google.android.wallet.common.address.AddressFormatter;
import com.google.android.wallet.common.address.AddressSource;
import com.google.android.wallet.common.address.AddressSourceResult;
import com.google.android.wallet.common.address.AddressUtils;
import com.google.android.wallet.common.address.CountryMetadataRetrievalTask;
import com.google.android.wallet.common.address.CountryMetadataRetrievalTask.AdminAreaMetadataRetrievalRequest;
import com.google.android.wallet.common.address.CountryMetadataRetrievalTask.CountryMetadataRetrievalRequest;
import com.google.android.wallet.common.address.DeviceAddressSource;
import com.google.android.wallet.common.address.GooglePlacesAddressSource;
import com.google.android.wallet.common.address.LocalAddressSource;
import com.google.android.wallet.common.address.RegionCode;
import com.google.android.wallet.common.api.WalletRequestQueue;
import com.google.android.wallet.common.util.ArrayUtils;
import com.google.android.wallet.common.util.ErrorUtils;
import com.google.android.wallet.common.util.Objects;
import com.google.android.wallet.common.util.ParcelableProto;
import com.google.android.wallet.common.util.PaymentUtils;
import com.google.android.wallet.ui.common.BaseWalletUiComponentFragment;
import com.google.android.wallet.ui.common.FieldValidatable;
import com.google.android.wallet.ui.common.FormEditText;
import com.google.android.wallet.ui.common.FormFragment;
import com.google.android.wallet.ui.common.FormFragment.FieldData;
import com.google.android.wallet.ui.common.FormSpinner;
import com.google.android.wallet.ui.common.RegionCodeSelectorSpinner.OnRegionCodeSelectedListener;
import com.google.android.wallet.ui.common.RegionCodeView;
import com.google.android.wallet.ui.common.SelectableItem;
import com.google.android.wallet.ui.common.ValidatableComponent;
import com.google.android.wallet.ui.common.WalletUiUtils;
import com.google.android.wallet.ui.common.validator.AbstractValidator;
import com.google.android.wallet.ui.common.validator.AndValidator;
import com.google.android.wallet.ui.common.validator.ComposedValidator;
import com.google.android.wallet.ui.common.validator.PatternValidator;
import com.google.android.wallet.uicomponents.R.attr;
import com.google.android.wallet.uicomponents.R.id;
import com.google.android.wallet.uicomponents.R.layout;
import com.google.android.wallet.uicomponents.R.string;
import com.google.commerce.payments.orchestration.proto.ui.common.FormFieldReferenceOuterClass.FormFieldReference;
import com.google.commerce.payments.orchestration.proto.ui.common.UiErrorOuterClass.FormFieldMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.components.AddressFormOuterClass.AddressForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.AddressFormOuterClass.AddressFormValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.AddressFormOuterClass.DisplayAddress;
import com.google.location.country.Postaladdress.PostalAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

public final class AddressEntryFragment
  extends FormFragment<AddressFormOuterClass.AddressForm>
  implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, DynamicAddressFieldsLayout.OnHeightOffsetChangedListener
{
  private static final SparseBooleanArray ALL_DYNAMIC_ADDRESS_FIELDS;
  private static final Comparator<Object> CASE_INSENSITIVE_COMPARATOR = new Comparator()
  {
    public final int compare(Object paramAnonymousObject1, Object paramAnonymousObject2)
    {
      return String.CASE_INSENSITIVE_ORDER.compare(paramAnonymousObject1.toString(), paramAnonymousObject2.toString());
    }
  };
  private static final SparseIntArray I18N_FIELD_ID_TO_ADDRESS_FORM_FIELD_ID;
  private static final SparseBooleanArray POSTAL_CODE_ONLY_DYNAMIC_ADDRESS_FIELDS;
  ArrayList<View> mAddressFields = new ArrayList();
  private ArrayList<Postaladdress.PostalAddress> mAddressHints;
  ArrayList<AddressSource> mAddressSources;
  private JSONObject mAdminAreaData;
  private CountryMetadataRetrievalTask.AdminAreaMetadataRetrievalRequest mAdminAreaMetadataRetrievalRequest;
  private boolean mCanSwitchToEditMode;
  private LinearLayout mContainer;
  private int mContainerLayoutResource;
  private JSONObject mCountryData;
  DynamicAddressFieldsLayout mDynamicAddressFieldsLayout;
  ImageButton mEditAddressIcon;
  CheckBox mHideAddressCheckbox;
  private AddressFormOuterClass.AddressFormValue mInitialValue;
  private boolean mIsReadOnlyMode;
  String mLanguageCode = Locale.getDefault().toString();
  public OnAddressFieldsShownListener mOnAddressFieldsShownListener;
  public DynamicAddressFieldsLayout.OnHeightOffsetChangedListener mOnHeightOffsetChangedListener;
  public RegionCodeSelectorSpinner.OnRegionCodeSelectedListener mOnRegionCodeSelectedListener;
  private Postaladdress.PostalAddress mPendingAddress;
  private int mPendingRequestCounter;
  TextView mPhoneNumberText;
  private ComposedValidator mPostalCodeValidator;
  View mReadOnlyContainer;
  TextView mReadOnlyText;
  public RecipientNameListener mRecipientNameListener;
  TextView mRecipientNameText;
  private final TextWatcher mRecipientNameTextWatcher = new TextWatcher()
  {
    public final void afterTextChanged(Editable paramAnonymousEditable)
    {
      if (AddressEntryFragment.this.mRecipientNameListener != null)
      {
        AddressEntryFragment.RecipientNameListener localRecipientNameListener = AddressEntryFragment.this.mRecipientNameListener;
        paramAnonymousEditable.toString();
        localRecipientNameListener.onRecipientNameChanged$552c4e01();
      }
    }
    
    public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
    
    public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
  };
  RegionCodeView mRegionCodeView;
  int[] mRegionCodes;
  int mSelectedCountry;
  private final WalletUiElement mUiElement = new WalletUiElement(1667);
  private boolean mValidateNonVisibleFields;
  
  static
  {
    SparseBooleanArray localSparseBooleanArray1 = new SparseBooleanArray(7);
    ALL_DYNAMIC_ADDRESS_FIELDS = localSparseBooleanArray1;
    localSparseBooleanArray1.put(83, true);
    ALL_DYNAMIC_ADDRESS_FIELDS.put(82, true);
    ALL_DYNAMIC_ADDRESS_FIELDS.put(67, true);
    ALL_DYNAMIC_ADDRESS_FIELDS.put(49, true);
    ALL_DYNAMIC_ADDRESS_FIELDS.put(50, true);
    ALL_DYNAMIC_ADDRESS_FIELDS.put(90, true);
    ALL_DYNAMIC_ADDRESS_FIELDS.put(88, true);
    SparseBooleanArray localSparseBooleanArray2 = new SparseBooleanArray(2);
    POSTAL_CODE_ONLY_DYNAMIC_ADDRESS_FIELDS = localSparseBooleanArray2;
    localSparseBooleanArray2.put(82, true);
    POSTAL_CODE_ONLY_DYNAMIC_ADDRESS_FIELDS.put(90, true);
    SparseIntArray localSparseIntArray = new SparseIntArray(10);
    I18N_FIELD_ID_TO_ADDRESS_FORM_FIELD_ID = localSparseIntArray;
    localSparseIntArray.put(82, 1);
    I18N_FIELD_ID_TO_ADDRESS_FORM_FIELD_ID.put(78, 2);
    I18N_FIELD_ID_TO_ADDRESS_FORM_FIELD_ID.put(49, 3);
    I18N_FIELD_ID_TO_ADDRESS_FORM_FIELD_ID.put(50, 4);
    I18N_FIELD_ID_TO_ADDRESS_FORM_FIELD_ID.put(67, 5);
    I18N_FIELD_ID_TO_ADDRESS_FORM_FIELD_ID.put(83, 6);
    I18N_FIELD_ID_TO_ADDRESS_FORM_FIELD_ID.put(90, 7);
    I18N_FIELD_ID_TO_ADDRESS_FORM_FIELD_ID.put(88, 9);
    I18N_FIELD_ID_TO_ADDRESS_FORM_FIELD_ID.put(68, 10);
    I18N_FIELD_ID_TO_ADDRESS_FORM_FIELD_ID.put(79, 11);
  }
  
  private boolean areFormFieldsHidden()
  {
    return (this.mHideAddressCheckbox.getVisibility() == 0) && (this.mHideAddressCheckbox.isChecked());
  }
  
  private void cancelPendingAdminAreaMetadataRetrievalRequest()
  {
    if (this.mAdminAreaMetadataRetrievalRequest != null)
    {
      this.mAdminAreaMetadataRetrievalRequest.cancel();
      this.mAdminAreaMetadataRetrievalRequest = null;
    }
  }
  
  private void configureAddressFieldsFocusOrder()
  {
    Object localObject = null;
    int i = 0;
    int j = this.mAddressFields.size();
    if (i < j)
    {
      View localView = (View)this.mAddressFields.get(i);
      if (localView.isFocusableInTouchMode()) {
        if (localObject != null)
        {
          if (Build.VERSION.SDK_INT < 11) {
            break label74;
          }
          localObject.setNextFocusForwardId(localView.getId());
          localView.setNextFocusForwardId(-1);
        }
      }
      for (;;)
      {
        localObject = localView;
        i++;
        break;
        label74:
        localObject.setNextFocusDownId(localView.getId());
        localView.setNextFocusDownId(-1);
      }
    }
  }
  
  private void configureAddressFormEditTextAutocomplete(char paramChar, char[] paramArrayOfChar, String paramString, final FormEditText paramFormEditText)
  {
    int i;
    if ((this.mAddressSources == null) || (this.mAddressSources.isEmpty())) {
      i = 0;
    }
    for (;;)
    {
      if ((i != 0) && (PaymentUtils.shouldAutoCompleteBeEnabled(getActivity())))
      {
        paramFormEditText.setAdapter(new AddressSourceResultAdapter(this.mThemedContext, R.layout.view_row_address_hint_spinner, this.mSelectedCountry, getAddressLanguageCode(), paramChar, paramArrayOfChar, paramString, this.mAddressSources));
        paramFormEditText.setThreshold(0);
        paramFormEditText.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
          public final void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
          {
            AddressSourceResult localAddressSourceResult = (AddressSourceResult)paramAnonymousAdapterView.getItemAtPosition(paramAnonymousInt);
            Postaladdress.PostalAddress localPostalAddress = localAddressSourceResult.address;
            if (localPostalAddress != null) {
              AddressEntryFragment.access$700(AddressEntryFragment.this, paramFormEditText, localPostalAddress);
            }
            do
            {
              return;
              if (!TextUtils.isEmpty(localAddressSourceResult.reference))
              {
                new AddressEntryFragment.FetchAddressTask(AddressEntryFragment.this, paramFormEditText).execute(new AddressSourceResult[] { localAddressSourceResult });
                return;
              }
            } while (!paramFormEditText.validate());
            AddressEntryFragment.access$800(AddressEntryFragment.this, paramFormEditText);
          }
        });
      }
      return;
      switch (paramChar)
      {
      default: 
        i = 1;
        break;
      case '2': 
      case '3': 
      case 'X': 
        i = 0;
      }
    }
  }
  
  private ArrayList<Postaladdress.PostalAddress> constructAddressesToFetchAdminAreaFor()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(this.mPendingAddress);
    if (this.mAddressHints != null) {
      localArrayList.addAll(this.mAddressHints);
    }
    return localArrayList;
  }
  
  private void createAndConfigureAddressFields()
  {
    String str1 = getActiveCountryDataAddressFields();
    String str2 = AddressUtils.getAddressData(this.mCountryData, "require");
    ArrayList localArrayList = new ArrayList(str1.length());
    this.mAddressFields = new ArrayList(str1.length());
    int i = 0;
    int j = str1.length();
    if (i < j)
    {
      char c = str1.charAt(i);
      char[] arrayOfChar = str1.substring(i).toCharArray();
      if (c == 'N')
      {
        if ((this.mRecipientNameText instanceof FormEditText))
        {
          configureAddressFormEditTextAutocomplete('N', arrayOfChar, str2, (FormEditText)this.mRecipientNameText);
          this.mRecipientNameText.setEnabled(this.mUiEnabled);
        }
        this.mAddressFields.add(this.mRecipientNameText);
      }
      for (;;)
      {
        i++;
        break;
        View localView = newAddressFieldInput(c, arrayOfChar, str2, null);
        localArrayList.add(localView);
        this.mAddressFields.add(localView);
      }
    }
    this.mDynamicAddressFieldsLayout.setFields(localArrayList);
    updatePostalCodeValidation();
    configureAddressFieldsFocusOrder();
  }
  
  private int findInputIndexWithAddressField(char paramChar)
  {
    int i = 0;
    int j = this.mAddressFields.size();
    while (i < j)
    {
      Character localCharacter = (Character)((View)this.mAddressFields.get(i)).getTag();
      if ((localCharacter != null) && (localCharacter.charValue() == paramChar)) {
        return i;
      }
      i++;
    }
    return -1;
  }
  
  private View findInputWithAddressField(char paramChar)
  {
    int i = findInputIndexWithAddressField(paramChar);
    if (i >= 0) {
      return (View)this.mAddressFields.get(i);
    }
    return null;
  }
  
  private void fireCountryDataRequest(int paramInt, String paramString1, String paramString2, final ArrayList<Postaladdress.PostalAddress> paramArrayList)
  {
    this.mAdminAreaData = null;
    cancelPendingAdminAreaMetadataRetrievalRequest();
    CountryMetadataRetrievalTask localCountryMetadataRetrievalTask = new CountryMetadataRetrievalTask(getRequestQueue(), paramInt, paramString1, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
    {
      public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
      {
        AddressEntryFragment.this.indicatePendingRequest(false);
        Bundle localBundle = new Bundle();
        localBundle.putString("FormEventListener.EXTRA_FORM_ID", ((AddressFormOuterClass.AddressForm)AddressEntryFragment.this.mFormProto).id);
        AddressEntryFragment.this.notifyFormEvent(5, ErrorUtils.addErrorDetailsToBundle(localBundle, 1000, AddressEntryFragment.this.getString(R.string.wallet_uic_network_error_title), AddressEntryFragment.this.getString(R.string.wallet_uic_network_error_message), null, AddressEntryFragment.this.getString(R.string.wallet_uic_retry)));
      }
    });
    indicatePendingRequest(true);
    this.mDynamicAddressFieldsLayout.switchToShowingProgressBar();
    localCountryMetadataRetrievalTask.run(paramString2);
  }
  
  private String getActiveCountryDataAddressFields()
  {
    boolean bool = AddressUtils.shouldUseLatinScript(this.mCountryData, this.mLanguageCode);
    String str1 = null;
    if (bool) {
      str1 = AddressUtils.getAddressData(this.mCountryData, "lfmt");
    }
    if (TextUtils.isEmpty(str1)) {
      str1 = AddressUtils.getAddressData(this.mCountryData, "fmt");
    }
    char[] arrayOfChar = AddressUtils.determineAddressFieldsToDisplay(str1);
    String[] arrayOfString = ((AddressFormOuterClass.AddressForm)this.mFormProto).postalCodeOnlyCountryCode;
    String str2 = RegionCode.toCountryCode(this.mSelectedCountry);
    int i;
    int j;
    label86:
    int k;
    if (arrayOfString != null)
    {
      i = arrayOfString.length;
      j = 0;
      if (j >= i) {
        break label210;
      }
      if (!Objects.equals(arrayOfString[j], str2)) {
        break label204;
      }
      k = j;
      label110:
      if (k < 0) {
        break label216;
      }
    }
    StringBuilder localStringBuilder;
    label204:
    label210:
    label216:
    for (int m = 1;; m = 0)
    {
      localStringBuilder = new StringBuilder(arrayOfChar.length);
      localStringBuilder.append('N');
      int n = arrayOfChar.length;
      for (int i1 = 0; i1 < n; i1++)
      {
        char c = arrayOfChar[i1];
        if ((ALL_DYNAMIC_ADDRESS_FIELDS.get(c)) && ((m == 0) || (POSTAL_CODE_ONLY_DYNAMIC_ADDRESS_FIELDS.get(c)))) {
          localStringBuilder.append(c);
        }
      }
      i = 0;
      break;
      j++;
      break label86;
      k = -1;
      break label110;
    }
    return localStringBuilder.toString();
  }
  
  private SparseArray<String> getAddressFieldValues()
  {
    Object localObject;
    if (this.mContainer == null) {
      localObject = null;
    }
    do
    {
      return localObject;
      int i = this.mAddressFields.size();
      localObject = new SparseArray(i);
      for (int j = 0; j < i; j++)
      {
        View localView = (View)this.mAddressFields.get(j);
        Character localCharacter = (Character)localView.getTag();
        String str = getInputValue(localView);
        ((SparseArray)localObject).put(localCharacter.charValue(), str);
      }
    } while (this.mSelectedCountry == 0);
    ((SparseArray)localObject).put(82, RegionCode.toCountryCode(this.mSelectedCountry));
    return localObject;
  }
  
  private SparseArray<String> getAddressFieldValuesForPrefilling()
  {
    SparseArray localSparseArray = getAddressFieldValues();
    for (int i = -1 + localSparseArray.size(); i >= 0; i--)
    {
      char c = (char)localSparseArray.keyAt(i);
      if ((findInputWithAddressField(c) instanceof Spinner)) {
        localSparseArray.remove(c);
      }
    }
    return localSparseArray;
  }
  
  private String getAddressLanguageCode()
  {
    if (AddressUtils.shouldUseLatinScript(this.mCountryData, this.mLanguageCode)) {
      return this.mLanguageCode;
    }
    return AddressUtils.getAddressData(this.mCountryData, "lang");
  }
  
  private ArrayList<Locality> getAdminAreaLocalities()
  {
    boolean bool = AddressUtils.shouldUseLatinScript(this.mCountryData, this.mLanguageCode);
    String[] arrayOfString1 = null;
    if (bool) {
      arrayOfString1 = AddressUtils.getAddressDataArray(this.mAdminAreaData, "sub_lnames");
    }
    if (arrayOfString1 == null) {
      arrayOfString1 = AddressUtils.getAddressDataArray(this.mAdminAreaData, "sub_keys");
    }
    Object localObject;
    if ((arrayOfString1 == null) || (arrayOfString1.length == 0)) {
      localObject = null;
    }
    do
    {
      return localObject;
      if (bool) {}
      for (String[] arrayOfString2 = arrayOfString1;; arrayOfString2 = AddressUtils.getAddressDataArray(this.mAdminAreaData, "sub_names"))
      {
        if ((arrayOfString2 == null) || (arrayOfString2.length != arrayOfString1.length)) {
          arrayOfString2 = arrayOfString1;
        }
        localObject = new ArrayList(arrayOfString1.length);
        for (int i = 0; i < arrayOfString1.length; i++) {
          ((ArrayList)localObject).add(new Locality(arrayOfString1[i], arrayOfString2[i]));
        }
      }
    } while (!bool);
    Collections.sort((List)localObject, CASE_INSENSITIVE_COMPARATOR);
    return localObject;
  }
  
  private ArrayList<AdminArea> getCountryAdminAreas()
  {
    boolean bool1 = AddressUtils.shouldUseLatinScript(this.mCountryData, this.mLanguageCode);
    String[] arrayOfString1 = AddressUtils.getAddressDataArray(this.mCountryData, "sub_keys");
    String[] arrayOfString2 = AddressUtils.getAddressDataArray(this.mCountryData, "sub_mores");
    String[] arrayOfString3 = null;
    if (bool1) {
      arrayOfString3 = AddressUtils.getAddressDataArray(this.mCountryData, "sub_lnames");
    }
    if (arrayOfString3 == null) {
      arrayOfString3 = arrayOfString1;
    }
    Object localObject;
    if ((arrayOfString3 == null) || (arrayOfString3.length == 0)) {
      localObject = null;
    }
    label178:
    label203:
    label213:
    do
    {
      return localObject;
      String[] arrayOfString4;
      String[] arrayOfString5;
      int i;
      boolean bool2;
      String str1;
      String str2;
      String str3;
      if (bool1)
      {
        arrayOfString4 = arrayOfString3;
        if ((arrayOfString4 == null) || (arrayOfString4.length != arrayOfString3.length)) {
          arrayOfString4 = arrayOfString3;
        }
        arrayOfString5 = AddressUtils.getAddressDataArray(this.mCountryData, "sub_zips");
        if ((arrayOfString5 != null) && (arrayOfString5.length != arrayOfString3.length)) {
          arrayOfString5 = null;
        }
        if ((arrayOfString1 != null) && (arrayOfString1.length != arrayOfString3.length))
        {
          arrayOfString1 = null;
          arrayOfString2 = null;
        }
        if ((arrayOfString2 != null) && (arrayOfString2.length != arrayOfString3.length)) {
          arrayOfString2 = null;
        }
        localObject = new ArrayList(arrayOfString3.length);
        i = 0;
        if (i >= arrayOfString3.length) {
          continue;
        }
        if ((arrayOfString2 == null) || (!Boolean.parseBoolean(arrayOfString2[i]))) {
          break label283;
        }
        bool2 = true;
        if (arrayOfString1 == null) {
          break label289;
        }
        str1 = arrayOfString1[i];
        str2 = arrayOfString3[i];
        str3 = arrayOfString4[i];
        if (arrayOfString5 == null) {
          break label295;
        }
      }
      for (String str4 = arrayOfString5[i];; str4 = null)
      {
        ((ArrayList)localObject).add(new AdminArea(str1, bool2, str2, str3, str4));
        i++;
        break label178;
        arrayOfString4 = AddressUtils.getAddressDataArray(this.mCountryData, "sub_names");
        break;
        bool2 = false;
        break label203;
        str1 = null;
        break label213;
      }
    } while (!bool1);
    label283:
    label289:
    label295:
    Collections.sort((List)localObject, CASE_INSENSITIVE_COMPARATOR);
    return localObject;
  }
  
  private String getFieldLabel(char paramChar)
  {
    switch (paramChar)
    {
    default: 
      return AddressUtils.makeAddressFieldLabel(getActivity(), paramChar, this.mCountryData);
    }
    return ((AddressFormOuterClass.AddressForm)this.mFormProto).recipientLabel;
  }
  
  private static String getInputValue(View paramView)
  {
    if (paramView == null) {
      throw new IllegalArgumentException("Input must not be null");
    }
    if ((paramView instanceof TextView)) {
      return ((TextView)paramView).getText().toString();
    }
    if ((paramView instanceof Spinner))
    {
      Object localObject = ((Spinner)paramView).getSelectedItem();
      if ((localObject instanceof SelectableItem)) {
        return ((SelectableItem)localObject).getValue();
      }
      if (localObject != null) {
        return String.valueOf(localObject);
      }
      return null;
    }
    throw new IllegalArgumentException("Unknown input type: " + paramView.getClass());
  }
  
  private static Postaladdress.PostalAddress getPostalAddressFromFieldValues(SparseArray<String> paramSparseArray)
  {
    Postaladdress.PostalAddress localPostalAddress = new Postaladdress.PostalAddress();
    int i = 0;
    int j = paramSparseArray.size();
    if (i < j)
    {
      int k = (char)paramSparseArray.keyAt(i);
      String str = (String)paramSparseArray.valueAt(i);
      if (str != null) {
        switch (k)
        {
        }
      }
      for (;;)
      {
        i++;
        break;
        localPostalAddress.countryNameCode = str;
        continue;
        localPostalAddress.addressLine = ((String[])ArrayUtils.appendToArray(localPostalAddress.addressLine, str));
        continue;
        localPostalAddress.administrativeAreaName = str;
        continue;
        localPostalAddress.localityName = str;
        continue;
        localPostalAddress.postalCodeNumber = str.toUpperCase();
        continue;
        localPostalAddress.sortingCode = str;
        continue;
        localPostalAddress.recipientName = str;
        continue;
        localPostalAddress.firmName = str;
      }
    }
    return localPostalAddress;
  }
  
  private RequestQueue getRequestQueue()
  {
    return WalletRequestQueue.getApiRequestQueue(getActivity().getApplicationContext());
  }
  
  private AdminArea getSelectedAdmin()
  {
    View localView = findInputWithAddressField('S');
    if (localView == null) {}
    while (!(localView instanceof Spinner)) {
      return null;
    }
    return (AdminArea)((Spinner)localView).getSelectedItem();
  }
  
  private boolean hasPendingRequests()
  {
    return this.mPendingRequestCounter > 0;
  }
  
  private boolean hideKeyboard()
  {
    View localView = this.mDynamicAddressFieldsLayout.findFocus();
    if (localView != null)
    {
      WalletUiUtils.hideSoftKeyboard(getActivity(), localView);
      return true;
    }
    return false;
  }
  
  private View newAddressFieldInput(char paramChar, char[] paramArrayOfChar, String paramString, View paramView)
  {
    if ((paramView != null) && (!Character.valueOf(paramChar).equals(paramView.getTag()))) {
      paramView = null;
    }
    if (paramChar == 'N') {
      throw new IllegalArgumentException("Recipient name should not be created dynamically");
    }
    boolean bool1 = AddressUtils.isAddressFieldRequired(paramChar, this.mCountryData);
    String str = getFieldLabel(paramChar);
    LayoutInflater localLayoutInflater = this.mThemedInflater;
    int i = I18N_FIELD_ID_TO_ADDRESS_FORM_FIELD_ID.get(paramChar);
    Object localObject;
    FormEditText localFormEditText;
    label121:
    int k;
    if (ArrayUtils.contains(((AddressFormOuterClass.AddressForm)this.mFormProto).readOnlyField, i))
    {
      localObject = localLayoutInflater.inflate(R.layout.view_form_non_editable_text, null);
      if (localObject == null)
      {
        if (!(paramView instanceof FormEditText)) {
          break label603;
        }
        localFormEditText = (FormEditText)paramView;
        configureAddressFormEditTextAutocomplete(paramChar, paramArrayOfChar, paramString, localFormEditText);
        localFormEditText.setRequired(bool1);
        localFormEditText.setHint(str);
        k = 1;
      }
      switch (paramChar)
      {
      default: 
        label216:
        localFormEditText.setInputType(k);
        localObject = localFormEditText;
        if (ArrayUtils.contains(((AddressFormOuterClass.AddressForm)this.mFormProto).hiddenField, i))
        {
          ((View)localObject).setVisibility(8);
          if (!(localObject instanceof FormEditText)) {
            break label682;
          }
          ((FormEditText)localObject).setShouldValidateWhenNotVisible(false);
        }
        break;
      }
    }
    for (;;)
    {
      int j = AddressUtils.getAddressFieldViewId(paramChar);
      ((View)localObject).setId(j);
      Character localCharacter = Character.valueOf(paramChar);
      ((View)localObject).setTag(localCharacter);
      boolean bool3 = this.mUiEnabled;
      ((View)localObject).setEnabled(bool3);
      return localObject;
      if (paramChar == 'S')
      {
        ArrayList localArrayList2 = getCountryAdminAreas();
        localObject = null;
        if (localArrayList2 == null) {
          break;
        }
        boolean bool4 = localArrayList2.isEmpty();
        localObject = null;
        if (bool4) {
          break;
        }
        if ((paramView instanceof FormSpinner)) {}
        for (FormSpinner localFormSpinner2 = (FormSpinner)paramView;; localFormSpinner2 = (FormSpinner)localLayoutInflater.inflate(R.layout.view_default_spinner, null))
        {
          localFormSpinner2.setRequired(bool1);
          localFormSpinner2.setPrompt(str);
          localFormSpinner2.setLabel(str);
          AdminArea localAdminArea = new AdminArea(null, false, null, str, null);
          PromptArrayAdapter localPromptArrayAdapter = new PromptArrayAdapter(this.mThemedContext, R.layout.view_row_spinner, R.id.description, localArrayList2, localAdminArea);
          localPromptArrayAdapter.setDropDownViewResource(R.layout.view_spinner_dropdown);
          localFormSpinner2.setAdapter(localPromptArrayAdapter);
          AdapterView.OnItemSelectedListener local7 = new AdapterView.OnItemSelectedListener()
          {
            public final void onItemSelected(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
            {
              AddressEntryFragment.access$600(AddressEntryFragment.this);
            }
            
            public final void onNothingSelected(AdapterView<?> paramAnonymousAdapterView) {}
          };
          localFormSpinner2.setOnItemSelectedListener(local7);
          localObject = localFormSpinner2;
          break;
        }
      }
      localObject = null;
      if (paramChar != 'C') {
        break;
      }
      ArrayList localArrayList1 = getAdminAreaLocalities();
      localObject = null;
      if (localArrayList1 == null) {
        break;
      }
      boolean bool2 = localArrayList1.isEmpty();
      localObject = null;
      if (bool2) {
        break;
      }
      ArrayAdapter localArrayAdapter = new ArrayAdapter(this.mThemedContext, R.layout.view_row_spinner, R.id.description, localArrayList1);
      localArrayAdapter.setDropDownViewResource(R.layout.view_spinner_dropdown);
      if ((paramView instanceof FormSpinner)) {}
      for (FormSpinner localFormSpinner1 = (FormSpinner)paramView;; localFormSpinner1 = (FormSpinner)localLayoutInflater.inflate(R.layout.view_default_spinner, null))
      {
        localFormSpinner1.setRequired(bool1);
        localFormSpinner1.setPrompt(str);
        localFormSpinner1.setAdapter(localArrayAdapter);
        localObject = localFormSpinner1;
        break;
      }
      label603:
      localFormEditText = (FormEditText)localLayoutInflater.inflate(R.layout.view_form_edit_text, null);
      break label121;
      if (AddressUtils.doesCountryUseNumericPostalCode(this.mCountryData))
      {
        k = 3;
        if (Build.VERSION.SDK_INT < 17) {
          break label216;
        }
        localFormEditText.setTextDirection(3);
        break label216;
      }
      k = 528385;
      break label216;
      k = 8305;
      break label216;
      k = 8193;
      break label216;
      k = 8193;
      break label216;
      label682:
      if ((localObject instanceof FormSpinner)) {
        ((FormSpinner)localObject).setShouldValidateWhenNotVisible(false);
      }
    }
  }
  
  public static AddressEntryFragment newInstance(AddressFormOuterClass.AddressForm paramAddressForm, int paramInt)
  {
    AddressEntryFragment localAddressEntryFragment = new AddressEntryFragment();
    localAddressEntryFragment.setArguments(createArgsForFormFragment$179723a0(paramInt, paramAddressForm));
    return localAddressEntryFragment;
  }
  
  private void onReceivedCountryData(JSONObject paramJSONObject)
  {
    if (AddressUtils.getRegionCodeFromAddressData(paramJSONObject) != this.mSelectedCountry) {
      return;
    }
    Postaladdress.PostalAddress localPostalAddress = this.mPendingAddress;
    SparseArray localSparseArray = null;
    if (localPostalAddress == null) {
      localSparseArray = getAddressFieldValuesForPrefilling();
    }
    this.mCountryData = paramJSONObject;
    this.mAddressSources = new ArrayList();
    if ((ArrayUtils.contains(((AddressFormOuterClass.AddressForm)this.mFormProto).addressAutocompleteOption, 2)) && (this.mAddressHints != null) && (!this.mAddressHints.isEmpty())) {
      this.mAddressSources.add(new LocalAddressSource(getActivity(), this.mAddressHints));
    }
    if (ArrayUtils.contains(((AddressFormOuterClass.AddressForm)this.mFormProto).addressAutocompleteOption, 1)) {
      this.mAddressSources.add(new DeviceAddressSource(getActivity()));
    }
    if ((ArrayUtils.contains(((AddressFormOuterClass.AddressForm)this.mFormProto).addressAutocompleteOption, 3)) && (GooglePlacesAddressSource.isCountrySupported(this.mSelectedCountry))) {
      this.mAddressSources.add(new GooglePlacesAddressSource(getActivity(), getRequestQueue()));
    }
    createAndConfigureAddressFields();
    setAddressFieldValues(localSparseArray);
    if (this.mPendingAddress != null)
    {
      setPostalAddressValues(this.mPendingAddress);
      this.mPendingAddress = null;
    }
    View localView = findInputWithAddressField('S');
    if ((localView instanceof Spinner))
    {
      Spinner localSpinner = (Spinner)localView;
      if ((localSpinner.getSelectedItemPosition() == 0) && (((AdminArea)localSpinner.getItemAtPosition(1)).mHasLocalities)) {
        localSpinner.setSelection(1);
      }
    }
    this.mDynamicAddressFieldsLayout.switchToShowingFields();
    updatePostalCodeValidation();
  }
  
  private void onSelectedCountryChange()
  {
    if (!Objects.equals(RegionCode.toCountryCode(this.mSelectedCountry), this.mInitialValue.address.countryNameCode))
    {
      Bundle localBundle = new Bundle();
      localBundle.putString("FormEventListener.EXTRA_FORM_ID", ((AddressFormOuterClass.AddressForm)this.mFormProto).id);
      localBundle.putInt("FormEventListener.EXTRA_FIELD_ID", 1);
      notifyFormEvent(3, localBundle);
    }
    this.mRegionCodeView.setSelectedRegionCode$2563266(this.mSelectedCountry);
    if (AddressUtils.getRegionCodeFromAddressData(this.mCountryData) == this.mSelectedCountry)
    {
      onReceivedCountryData(this.mCountryData);
      return;
    }
    fireCountryDataRequest(this.mSelectedCountry, this.mLanguageCode, null, constructAddressesToFetchAdminAreaFor());
  }
  
  private void setAddress(Postaladdress.PostalAddress paramPostalAddress)
  {
    int i = 1;
    if (paramPostalAddress == null) {
      if (this.mContainer == null)
      {
        this.mPendingAddress = null;
        this.mSelectedCountry = 0;
        this.mLanguageCode = Locale.getDefault().toString();
      }
    }
    int j;
    for (;;)
    {
      return;
      paramPostalAddress = new Postaladdress.PostalAddress();
      j = RegionCode.toRegionCode(paramPostalAddress.countryNameCode);
      this.mPendingAddress = paramPostalAddress;
      if (!TextUtils.isEmpty(this.mPendingAddress.languageCode)) {
        this.mLanguageCode = this.mPendingAddress.languageCode;
      }
      if (this.mContainer == null)
      {
        this.mSelectedCountry = j;
        return;
      }
      if (j != this.mSelectedCountry) {
        break;
      }
      if (!hasPendingRequests())
      {
        if (this.mCountryData != null)
        {
          String str1 = AddressUtils.getAlternativeLanguageCode(this.mCountryData, this.mLanguageCode);
          if (!TextUtils.isEmpty(str1)) {
            fireCountryDataRequest(this.mSelectedCountry, this.mLanguageCode, str1, constructAddressesToFetchAdminAreaFor());
          }
        }
        while (i == 0)
        {
          setPostalAddressValues(this.mPendingAddress);
          this.mPendingAddress = null;
          return;
          String str2 = AddressUtils.getAddressData(this.mCountryData, "id");
          if ((!TextUtils.isEmpty(str2)) && (str2.contains("--")) && (!AddressUtils.isSameLanguage(AddressUtils.getAddressData(this.mCountryData, "lang"), this.mLanguageCode))) {
            fireCountryDataRequest(this.mSelectedCountry, this.mLanguageCode, null, constructAddressesToFetchAdminAreaFor());
          } else {
            i = 0;
          }
        }
      }
    }
    if (this.mSelectedCountry == 0)
    {
      this.mSelectedCountry = j;
      return;
    }
    setSelectedCountry(j, false);
  }
  
  private void setAddressFieldValues(SparseArray<String> paramSparseArray)
  {
    if ((this.mContainer == null) || (paramSparseArray == null)) {}
    for (;;)
    {
      return;
      int i = 0;
      int j = this.mAddressFields.size();
      while (i < j)
      {
        View localView = (View)this.mAddressFields.get(i);
        String str = (String)paramSparseArray.get(((Character)localView.getTag()).charValue());
        if (str != null) {
          setInputValue(localView, str, false);
        }
        i++;
      }
    }
  }
  
  private void setAddressFieldVisibilities()
  {
    int i;
    if ((this.mHideAddressCheckbox.getVisibility() == 0) && (this.mHideAddressCheckbox.isChecked()))
    {
      i = 1;
      if ((i == 0) && (!this.mIsReadOnlyMode) && (!ArrayUtils.contains(((AddressFormOuterClass.AddressForm)this.mFormProto).hiddenField, 2))) {
        break label193;
      }
      this.mRecipientNameText.setVisibility(8);
      label59:
      if ((i == 0) && (!this.mIsReadOnlyMode) && (!ArrayUtils.contains(((AddressFormOuterClass.AddressForm)this.mFormProto).hiddenField, 1))) {
        break label204;
      }
      this.mRegionCodeView.setVisibility(8);
      label96:
      if ((i == 0) && (!this.mIsReadOnlyMode)) {
        break label215;
      }
      this.mDynamicAddressFieldsLayout.setVisibility(8);
      label116:
      if (((AddressFormOuterClass.AddressForm)this.mFormProto).phoneNumberForm)
      {
        if ((i == 0) && (!this.mIsReadOnlyMode) && (!ArrayUtils.contains(((AddressFormOuterClass.AddressForm)this.mFormProto).hiddenField, 8))) {
          break label226;
        }
        this.mPhoneNumberText.setVisibility(8);
      }
    }
    for (;;)
    {
      if ((i == 0) && (this.mIsReadOnlyMode)) {
        break label237;
      }
      this.mReadOnlyContainer.setVisibility(8);
      return;
      i = 0;
      break;
      label193:
      this.mRecipientNameText.setVisibility(0);
      break label59;
      label204:
      this.mRegionCodeView.setVisibility(0);
      break label96;
      label215:
      this.mDynamicAddressFieldsLayout.setVisibility(0);
      break label116;
      label226:
      this.mPhoneNumberText.setVisibility(0);
    }
    label237:
    this.mReadOnlyContainer.setVisibility(0);
  }
  
  private static void setInputValue(View paramView, String paramString, boolean paramBoolean)
  {
    if (paramView == null) {
      throw new IllegalArgumentException("Input must not be null");
    }
    if ((paramView instanceof FormEditText)) {
      ((FormEditText)paramView).replaceTextAndPreventFilter(paramString, paramBoolean);
    }
    Spinner localSpinner;
    ArrayAdapter localArrayAdapter;
    do
    {
      do
      {
        return;
        if ((paramView instanceof TextView))
        {
          ((TextView)paramView).setText(paramString);
          return;
        }
        if (!(paramView instanceof Spinner)) {
          break;
        }
        localSpinner = (Spinner)paramView;
        if (paramString == null)
        {
          localSpinner.setSelection(0);
          return;
        }
      } while (!(localSpinner.getAdapter() instanceof ArrayAdapter));
      localArrayAdapter = (ArrayAdapter)localSpinner.getAdapter();
    } while (localArrayAdapter.isEmpty());
    int i = 0;
    int j = 0;
    int k = localArrayAdapter.getCount();
    for (;;)
    {
      Object localObject;
      if (j < k)
      {
        localObject = localArrayAdapter.getItem(j);
        if ((!(localObject instanceof SelectableItem)) || (!paramString.equalsIgnoreCase(((SelectableItem)localObject).getValue()))) {
          break label176;
        }
      }
      for (i = 1;; i = 1) {
        label176:
        do
        {
          if (i == 0) {
            break label199;
          }
          localSpinner.setSelection(j);
          if (i != 0) {
            break;
          }
          localSpinner.setSelection(0);
          return;
        } while ((localObject == null) || (!paramString.equalsIgnoreCase(localObject.toString())));
      }
      label199:
      j++;
    }
    throw new IllegalArgumentException("Unknown input type: " + paramView.getClass());
  }
  
  private void setPhoneNumber(String paramString)
  {
    WalletUiUtils.setPhoneNumber(this.mPhoneNumberText, paramString);
  }
  
  private void setPostalAddressValues(Postaladdress.PostalAddress paramPostalAddress)
  {
    if (this.mContainer == null) {}
    for (;;)
    {
      return;
      int i = 0;
      int j = this.mAddressFields.size();
      while (i < j)
      {
        View localView = (View)this.mAddressFields.get(i);
        setInputValue(localView, AddressFormatter.formatAddressValue(paramPostalAddress, ((Character)localView.getTag()).charValue()), false);
        i++;
      }
    }
  }
  
  private boolean switchToEditModeIfNecessary()
  {
    boolean bool1 = this.mIsReadOnlyMode;
    boolean bool2 = false;
    if (bool1)
    {
      boolean bool3 = this.mCanSwitchToEditMode;
      bool2 = false;
      if (bool3)
      {
        this.mIsReadOnlyMode = false;
        setAddressFieldVisibilities();
        this.mContainer.requestFocus(130);
        bool2 = true;
      }
    }
    return bool2;
  }
  
  private void updateLocalityInput()
  {
    int i = findInputIndexWithAddressField('C');
    View localView1 = (View)this.mAddressFields.get(i);
    String str = getInputValue(localView1);
    View localView2 = newAddressFieldInput('C', getActiveCountryDataAddressFields().substring(i).toCharArray(), AddressUtils.getAddressData(this.mCountryData, "require"), localView1);
    if (localView2 != localView1)
    {
      this.mDynamicAddressFieldsLayout.replaceView(localView1, localView2);
      this.mAddressFields.set(i, localView2);
      configureAddressFieldsFocusOrder();
    }
    setInputValue(localView2, str, false);
  }
  
  private void updatePostalCodeValidation()
  {
    View localView = findInputWithAddressField('Z');
    if (!(localView instanceof FieldValidatable)) {
      this.mPostalCodeValidator = null;
    }
    ValidatableComponent localValidatableComponent;
    TextView localTextView;
    do
    {
      return;
      localValidatableComponent = (ValidatableComponent)localView;
      if (this.mPostalCodeValidator != null)
      {
        localValidatableComponent.removeValidator(this.mPostalCodeValidator);
        this.mPostalCodeValidator = null;
      }
      this.mPostalCodeValidator = new AndValidator(new AbstractValidator[0]);
      int i = R.string.wallet_uic_error_address_field_invalid;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = getFieldLabel('Z');
      String str = getString(i, arrayOfObject);
      Pattern localPattern1 = AddressUtils.getPostalCodeRegexpPattern(this.mCountryData);
      if (localPattern1 != null) {
        this.mPostalCodeValidator.add(new PatternValidator(str, localPattern1));
      }
      AdminArea localAdminArea = getSelectedAdmin();
      if (localAdminArea != null)
      {
        Pattern localPattern2 = AddressUtils.getPostalCodeRegexpPatternForAdminArea(localAdminArea.mZipStartsWithRegEx);
        if (localPattern2 != null) {
          this.mPostalCodeValidator.add(new PatternValidator(str, localPattern2));
        }
      }
      if (this.mPostalCodeValidator.isEmpty())
      {
        this.mPostalCodeValidator = null;
        return;
      }
      localValidatableComponent.addValidator(this.mPostalCodeValidator);
      if (!(localValidatableComponent instanceof TextView)) {
        break;
      }
      localTextView = (TextView)localValidatableComponent;
    } while ((TextUtils.isEmpty(localTextView.getText())) && (TextUtils.isEmpty(localTextView.getError())));
    localValidatableComponent.validate();
    return;
    localValidatableComponent.validate();
  }
  
  public final boolean applyFormFieldMessage(UiErrorOuterClass.FormFieldMessage paramFormFieldMessage)
  {
    if (!paramFormFieldMessage.formFieldReference.formId.equals(((AddressFormOuterClass.AddressForm)this.mFormProto).id)) {
      return false;
    }
    if (((AddressFormOuterClass.AddressForm)this.mFormProto).editability == 2) {
      throw new IllegalArgumentException("FormFieldMessage should not be received for read-only address form");
    }
    if (this.mIsReadOnlyMode)
    {
      this.mIsReadOnlyMode = false;
      setAddressFieldVisibilities();
    }
    View localView = this.mContainer.findViewById(AddressUtils.getAddressFormFieldViewId(paramFormFieldMessage.formFieldReference.fieldId));
    boolean bool;
    if (localView != null)
    {
      if (localView.getVisibility() == 0) {
        break label181;
      }
      if (!(localView instanceof FormEditText)) {
        break label155;
      }
      bool = ((FormEditText)localView).mValidateWhenNotVisible;
    }
    while (!bool)
    {
      throw new IllegalArgumentException("FormFieldMessage received for invalid field: " + paramFormFieldMessage.formFieldReference.fieldId + " view: " + localView);
      label155:
      if ((localView instanceof FormSpinner)) {
        bool = ((FormSpinner)localView).mValidateWhenNotVisible;
      } else {
        bool = this.mValidateNonVisibleFields;
      }
    }
    label181:
    if ((localView instanceof EditText))
    {
      ((EditText)localView).setError(paramFormFieldMessage.message);
      if ((localView == this.mRecipientNameText) && (this.mRecipientNameListener != null)) {
        this.mRecipientNameListener.onRecipientNameErrorOccurred();
      }
      return true;
    }
    throw new IllegalArgumentException("FormFieldMessage received for non-EditText field: " + paramFormFieldMessage.formFieldReference.fieldId);
  }
  
  protected final void doEnableUi()
  {
    if (this.mContainer == null) {}
    boolean bool;
    do
    {
      return;
      if ((this.mUiEnabled) && (!hasPendingRequests())) {}
      for (bool = true;; bool = false)
      {
        this.mHideAddressCheckbox.setEnabled(bool);
        this.mRegionCodeView.setEnabled(bool);
        int i = 0;
        int j = this.mAddressFields.size();
        while (i < j)
        {
          ((View)this.mAddressFields.get(i)).setEnabled(bool);
          i++;
        }
      }
    } while (this.mPhoneNumberText == null);
    this.mPhoneNumberText.setEnabled(bool);
  }
  
  public final AddressFormOuterClass.AddressFormValue getAddressFormValue$64352f99()
  {
    AddressFormOuterClass.AddressFormValue localAddressFormValue = new AddressFormOuterClass.AddressFormValue();
    localAddressFormValue.id = ((AddressFormOuterClass.AddressForm)this.mFormProto).id;
    localAddressFormValue.dataToken = ((AddressFormOuterClass.AddressForm)this.mFormProto).dataToken;
    if (areFormFieldsHidden()) {
      localAddressFormValue.isHidden = true;
    }
    do
    {
      return localAddressFormValue;
      localAddressFormValue.address = getPostalAddressFromFieldValues(getAddressFieldValues());
      String str = getAddressLanguageCode();
      if (!TextUtils.isEmpty(str)) {
        localAddressFormValue.address.languageCode = str;
      }
    } while ((this.mPhoneNumberText == null) || (TextUtils.isEmpty(this.mPhoneNumberText.getText())));
    localAddressFormValue.phoneNumber = this.mPhoneNumberText.getText().toString();
    return localAddressFormValue;
  }
  
  public final List<UiNode> getChildren()
  {
    return null;
  }
  
  public final List<FormFragment.FieldData<View>> getFieldsForValidationInOrder()
  {
    ArrayList localArrayList = new ArrayList(this.mAddressFields.size());
    Postaladdress.PostalAddress localPostalAddress = this.mInitialValue.address;
    int i = 0;
    int j = this.mAddressFields.size();
    while (i < j)
    {
      View localView = (View)this.mAddressFields.get(i);
      localArrayList.add(new FormFragment.FieldData(0, localView, AddressFormatter.formatAddressValue(localPostalAddress, ((Character)localView.getTag()).charValue())));
      i++;
    }
    if (this.mPhoneNumberText != null) {
      localArrayList.add(new FormFragment.FieldData(0, this.mPhoneNumberText, this.mInitialValue.phoneNumber));
    }
    return localArrayList;
  }
  
  public final WalletUiElement getUiElement()
  {
    return this.mUiElement;
  }
  
  public final boolean handleErrorMessageDismissed(String paramString, int paramInt)
  {
    if (!paramString.equals(((AddressFormOuterClass.AddressForm)this.mFormProto).id)) {
      return false;
    }
    if (paramInt == 1000)
    {
      JSONObject localJSONObject = this.mCountryData;
      String str = null;
      if (localJSONObject != null) {
        str = AddressUtils.getAlternativeLanguageCode(this.mCountryData, this.mLanguageCode);
      }
      fireCountryDataRequest(this.mSelectedCountry, this.mLanguageCode, str, constructAddressesToFetchAdminAreaFor());
      return true;
    }
    throw new IllegalArgumentException("Unrecognized errorType: " + paramInt);
  }
  
  final void indicatePendingRequest(boolean paramBoolean)
  {
    int i = this.mPendingRequestCounter;
    if (paramBoolean) {}
    for (int j = 1;; j = -1)
    {
      this.mPendingRequestCounter = Math.max(0, j + i);
      if (((paramBoolean) && (this.mPendingRequestCounter == 1)) || ((!paramBoolean) && (this.mPendingRequestCounter == 0))) {
        doEnableUi();
      }
      return;
    }
  }
  
  public final boolean isReadyToSubmit()
  {
    return true;
  }
  
  /* Error */
  public final void onActivityCreated(Bundle paramBundle)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokespecial 1174	com/google/android/wallet/ui/common/FormFragment:onActivityCreated	(Landroid/os/Bundle;)V
    //   5: aload_1
    //   6: ifnull +179 -> 185
    //   9: aload_1
    //   10: ldc_w 1176
    //   13: invokevirtual 1179	android/os/Bundle:containsKey	(Ljava/lang/String;)Z
    //   16: ifeq +17 -> 33
    //   19: aload_0
    //   20: aload_1
    //   21: ldc_w 1176
    //   24: invokestatic 1185	com/google/android/wallet/common/util/ParcelableProto:getProtoFromBundle	(Landroid/os/Bundle;Ljava/lang/String;)Lcom/google/protobuf/nano/MessageNano;
    //   27: checkcast 652	com/google/location/country/Postaladdress$PostalAddress
    //   30: invokespecial 1187	com/google/android/wallet/ui/address/AddressEntryFragment:setAddress	(Lcom/google/location/country/Postaladdress$PostalAddress;)V
    //   33: aload_0
    //   34: getfield 186	com/google/android/wallet/ui/address/AddressEntryFragment:mSelectedCountry	I
    //   37: ifne +14 -> 51
    //   40: aload_0
    //   41: aload_1
    //   42: ldc_w 1189
    //   45: invokevirtual 1192	android/os/Bundle:getInt	(Ljava/lang/String;)I
    //   48: putfield 186	com/google/android/wallet/ui/address/AddressEntryFragment:mSelectedCountry	I
    //   51: aload_1
    //   52: ldc_w 1194
    //   55: invokevirtual 1179	android/os/Bundle:containsKey	(Ljava/lang/String;)Z
    //   58: ifeq +78 -> 136
    //   61: aload_0
    //   62: new 1196	org/json/JSONObject
    //   65: dup
    //   66: aload_1
    //   67: ldc_w 1194
    //   70: invokevirtual 1199	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   73: invokespecial 1200	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   76: putfield 286	com/google/android/wallet/ui/address/AddressEntryFragment:mCountryData	Lorg/json/JSONObject;
    //   79: aload_0
    //   80: getfield 286	com/google/android/wallet/ui/address/AddressEntryFragment:mCountryData	Lorg/json/JSONObject;
    //   83: invokestatic 853	com/google/android/wallet/common/address/AddressUtils:getRegionCodeFromAddressData	(Lorg/json/JSONObject;)I
    //   86: istore 4
    //   88: iload 4
    //   90: ifeq +46 -> 136
    //   93: iload 4
    //   95: sipush 858
    //   98: if_icmpeq +38 -> 136
    //   101: iload 4
    //   103: aload_0
    //   104: getfield 186	com/google/android/wallet/ui/address/AddressEntryFragment:mSelectedCountry	I
    //   107: if_icmpeq +29 -> 136
    //   110: aload_0
    //   111: getfield 186	com/google/android/wallet/ui/address/AddressEntryFragment:mSelectedCountry	I
    //   114: istore 5
    //   116: aload_0
    //   117: iload 4
    //   119: putfield 186	com/google/android/wallet/ui/address/AddressEntryFragment:mSelectedCountry	I
    //   122: aload_0
    //   123: aload_0
    //   124: getfield 286	com/google/android/wallet/ui/address/AddressEntryFragment:mCountryData	Lorg/json/JSONObject;
    //   127: invokespecial 177	com/google/android/wallet/ui/address/AddressEntryFragment:onReceivedCountryData	(Lorg/json/JSONObject;)V
    //   130: aload_0
    //   131: iload 5
    //   133: putfield 186	com/google/android/wallet/ui/address/AddressEntryFragment:mSelectedCountry	I
    //   136: aload_1
    //   137: ldc_w 1201
    //   140: invokevirtual 1179	android/os/Bundle:containsKey	(Ljava/lang/String;)Z
    //   143: ifeq +14 -> 157
    //   146: aload_0
    //   147: aload_1
    //   148: ldc_w 1201
    //   151: invokevirtual 1199	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   154: putfield 130	com/google/android/wallet/ui/address/AddressEntryFragment:mLanguageCode	Ljava/lang/String;
    //   157: aload_1
    //   158: ldc_w 1203
    //   161: invokevirtual 1179	android/os/Bundle:containsKey	(Ljava/lang/String;)Z
    //   164: ifeq +21 -> 185
    //   167: aload_0
    //   168: new 1196	org/json/JSONObject
    //   171: dup
    //   172: aload_1
    //   173: ldc_w 1203
    //   176: invokevirtual 1199	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   179: invokespecial 1200	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   182: putfield 195	com/google/android/wallet/ui/address/AddressEntryFragment:mAdminAreaData	Lorg/json/JSONObject;
    //   185: aload_0
    //   186: invokespecial 1004	com/google/android/wallet/ui/address/AddressEntryFragment:setAddressFieldVisibilities	()V
    //   189: aload_0
    //   190: invokevirtual 1168	com/google/android/wallet/ui/address/AddressEntryFragment:doEnableUi	()V
    //   193: aload_0
    //   194: getfield 925	com/google/android/wallet/ui/address/AddressEntryFragment:mRegionCodeView	Lcom/google/android/wallet/ui/common/RegionCodeView;
    //   197: aload_0
    //   198: getfield 1205	com/google/android/wallet/ui/address/AddressEntryFragment:mRegionCodes	[I
    //   201: invokevirtual 1209	com/google/android/wallet/ui/common/RegionCodeView:setRegionCodes	([I)V
    //   204: aload_0
    //   205: getfield 925	com/google/android/wallet/ui/address/AddressEntryFragment:mRegionCodeView	Lcom/google/android/wallet/ui/common/RegionCodeView;
    //   208: new 1211	com/google/android/wallet/ui/address/AddressEntryFragment$6
    //   211: dup
    //   212: aload_0
    //   213: invokespecial 1212	com/google/android/wallet/ui/address/AddressEntryFragment$6:<init>	(Lcom/google/android/wallet/ui/address/AddressEntryFragment;)V
    //   216: invokevirtual 1216	com/google/android/wallet/ui/common/RegionCodeView:setRegionCodeSelectedListener	(Lcom/google/android/wallet/ui/common/RegionCodeSelectorSpinner$OnRegionCodeSelectedListener;)V
    //   219: aload_0
    //   220: invokespecial 1218	com/google/android/wallet/ui/address/AddressEntryFragment:onSelectedCountryChange	()V
    //   223: aload_0
    //   224: getfield 352	com/google/android/wallet/ui/address/AddressEntryFragment:mHideAddressCheckbox	Landroid/widget/CheckBox;
    //   227: invokevirtual 357	android/widget/CheckBox:getVisibility	()I
    //   230: ifne +18 -> 248
    //   233: aload_0
    //   234: aload_0
    //   235: getfield 352	com/google/android/wallet/ui/address/AddressEntryFragment:mHideAddressCheckbox	Landroid/widget/CheckBox;
    //   238: aload_0
    //   239: getfield 352	com/google/android/wallet/ui/address/AddressEntryFragment:mHideAddressCheckbox	Landroid/widget/CheckBox;
    //   242: invokevirtual 360	android/widget/CheckBox:isChecked	()Z
    //   245: invokevirtual 1222	com/google/android/wallet/ui/address/AddressEntryFragment:onCheckedChanged	(Landroid/widget/CompoundButton;Z)V
    //   248: aload_0
    //   249: getfield 1224	com/google/android/wallet/ui/address/AddressEntryFragment:mOnRegionCodeSelectedListener	Lcom/google/android/wallet/ui/common/RegionCodeSelectorSpinner$OnRegionCodeSelectedListener;
    //   252: ifnull +28 -> 280
    //   255: aload_0
    //   256: getfield 186	com/google/android/wallet/ui/address/AddressEntryFragment:mSelectedCountry	I
    //   259: ifeq +21 -> 280
    //   262: aload_0
    //   263: getfield 1224	com/google/android/wallet/ui/address/AddressEntryFragment:mOnRegionCodeSelectedListener	Lcom/google/android/wallet/ui/common/RegionCodeSelectorSpinner$OnRegionCodeSelectedListener;
    //   266: aload_0
    //   267: getfield 186	com/google/android/wallet/ui/address/AddressEntryFragment:mSelectedCountry	I
    //   270: aload_0
    //   271: getfield 1229	android/support/v4/app/Fragment:mFragmentId	I
    //   274: iconst_0
    //   275: invokeinterface 1235 4 0
    //   280: return
    //   281: astore_3
    //   282: new 1237	java/lang/RuntimeException
    //   285: dup
    //   286: ldc_w 1239
    //   289: aload_3
    //   290: invokespecial 1242	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   293: athrow
    //   294: astore_2
    //   295: new 1237	java/lang/RuntimeException
    //   298: dup
    //   299: ldc_w 1244
    //   302: aload_2
    //   303: invokespecial 1242	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   306: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	307	0	this	AddressEntryFragment
    //   0	307	1	paramBundle	Bundle
    //   294	9	2	localJSONException1	JSONException
    //   281	9	3	localJSONException2	JSONException
    //   86	32	4	i	int
    //   114	18	5	j	int
    // Exception table:
    //   from	to	target	type
    //   61	79	281	org/json/JSONException
    //   167	185	294	org/json/JSONException
  }
  
  public final void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
  {
    setAddressFieldVisibilities();
    OnAddressFieldsShownListener localOnAddressFieldsShownListener;
    if (this.mOnAddressFieldsShownListener != null)
    {
      localOnAddressFieldsShownListener = this.mOnAddressFieldsShownListener;
      if (paramBoolean) {
        break label91;
      }
    }
    label91:
    for (boolean bool = true;; bool = false)
    {
      localOnAddressFieldsShownListener.onAddressFieldsShown(bool);
      Iterator localIterator = getFieldsForValidationInOrder().iterator();
      while (localIterator.hasNext())
      {
        FormFragment.FieldData localFieldData = (FormFragment.FieldData)localIterator.next();
        if ((localFieldData.field instanceof FieldValidatable)) {
          ((FieldValidatable)localFieldData.field).setError(null);
        }
      }
    }
  }
  
  public final void onClick(View paramView)
  {
    if (paramView == this.mEditAddressIcon)
    {
      if (((AddressFormOuterClass.AddressForm)this.mFormProto).editability != 4) {
        break label94;
      }
      if ((!this.mIsReadOnlyMode) || (this.mCanSwitchToEditMode))
      {
        Postaladdress.PostalAddress localPostalAddress = new Postaladdress.PostalAddress();
        localPostalAddress.countryNameCode = RegionCode.toCountryCode(this.mSelectedCountry);
        localPostalAddress.languageCode = this.mLanguageCode;
        setAddress(localPostalAddress);
        if (((AddressFormOuterClass.AddressForm)this.mFormProto).phoneNumberForm) {
          setPhoneNumber("");
        }
        switchToEditModeIfNecessary();
      }
    }
    return;
    label94:
    switchToEditModeIfNecessary();
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    ContextThemeWrapper localContextThemeWrapper = this.mThemedContext;
    int[] arrayOfInt = new int[2];
    arrayOfInt[0] = R.attr.internalUicAddressRootLayout;
    arrayOfInt[1] = R.attr.internalUicValidateFieldsWhenNotVisible;
    TypedArray localTypedArray = localContextThemeWrapper.obtainStyledAttributes(arrayOfInt);
    this.mContainerLayoutResource = localTypedArray.getResourceId(0, R.layout.fragment_address_entry);
    this.mValidateNonVisibleFields = localTypedArray.getBoolean(1, false);
    localTypedArray.recycle();
    int i = ((AddressFormOuterClass.AddressForm)this.mFormProto).initialAvailableOptionIndex;
    if ((i >= 0) && (i < ((AddressFormOuterClass.AddressForm)this.mFormProto).availableOption.length)) {
      this.mInitialValue = ((AddressFormOuterClass.AddressForm)this.mFormProto).availableOption[i].addressFormValue;
    }
    label244:
    boolean bool2;
    for (;;)
    {
      if (paramBundle == null)
      {
        try
        {
          this.mCountryData = new JSONObject(((AddressFormOuterClass.AddressForm)this.mFormProto).initialCountryI18NDataJson);
          String str = RegionCode.toCountryCode(AddressUtils.getRegionCodeFromAddressData(this.mCountryData));
          if (str.equals(this.mInitialValue.address.countryNameCode)) {
            break label244;
          }
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = str;
          arrayOfObject[1] = this.mInitialValue.address.countryNameCode;
          throw new IllegalArgumentException(String.format("JSON provided for country %s but initial value has country %s", arrayOfObject));
        }
        catch (JSONException localJSONException)
        {
          throw new RuntimeException("Could not construct JSONObject from mFormProto.initialCountryI18NDataJson", localJSONException);
        }
        this.mInitialValue = ((AddressFormOuterClass.AddressForm)this.mFormProto).initialValue;
        continue;
        setAddress(this.mInitialValue.address);
        this.mRegionCodes = AddressUtils.scrubAndSortRegionCodes(AddressUtils.stringArrayToRegionCodeArray(((AddressFormOuterClass.AddressForm)this.mFormProto).allowedCountryCode));
        if (this.mRegionCodes.length <= 0) {
          throw new IllegalArgumentException("Array length of allowedCountryCodes must be > 0");
        }
        if (TextUtils.isEmpty(((AddressFormOuterClass.AddressForm)this.mFormProto).recipientLabel)) {
          throw new IllegalArgumentException("Recipient field hint must be specified!");
        }
        if ((((AddressFormOuterClass.AddressForm)this.mFormProto).editability == 2) || (((AddressFormOuterClass.AddressForm)this.mFormProto).editability == 3) || (((AddressFormOuterClass.AddressForm)this.mFormProto).editability == 4)) {
          bool2 = true;
        }
      }
    }
    for (this.mIsReadOnlyMode = bool2;; this.mIsReadOnlyMode = paramBundle.getBoolean("isReadOnlyMode"))
    {
      this.mAddressHints = new ArrayList(((AddressFormOuterClass.AddressForm)this.mFormProto).availableOption.length);
      for (AddressFormOuterClass.DisplayAddress localDisplayAddress : ((AddressFormOuterClass.AddressForm)this.mFormProto).availableOption) {
        this.mAddressHints.add(localDisplayAddress.addressFormValue.address);
      }
      bool2 = false;
      break;
      this.mRegionCodes = paramBundle.getIntArray("regionCodes");
    }
    if ((((AddressFormOuterClass.AddressForm)this.mFormProto).editability == 3) || (((AddressFormOuterClass.AddressForm)this.mFormProto).editability == 4)) {}
    for (boolean bool1 = true;; bool1 = false)
    {
      this.mCanSwitchToEditMode = bool1;
      return;
    }
  }
  
  @TargetApi(11)
  protected final View onCreateThemedView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(this.mContainerLayoutResource, paramViewGroup, false);
    this.mContainer = ((LinearLayout)localView.findViewById(R.id.container));
    if (!TextUtils.isEmpty(((AddressFormOuterClass.AddressForm)this.mFormProto).title))
    {
      TextView localTextView2 = (TextView)this.mContainer.findViewById(R.id.address_title);
      localTextView2.setText(((AddressFormOuterClass.AddressForm)this.mFormProto).title);
      localTextView2.setVisibility(0);
    }
    this.mHideAddressCheckbox = ((CheckBox)this.mContainer.findViewById(R.id.hide_address_checkbox));
    if (!TextUtils.isEmpty(((AddressFormOuterClass.AddressForm)this.mFormProto).hideFormFieldsToggleLabel))
    {
      this.mHideAddressCheckbox.setText(((AddressFormOuterClass.AddressForm)this.mFormProto).hideFormFieldsToggleLabel);
      this.mHideAddressCheckbox.setVisibility(0);
      this.mHideAddressCheckbox.setOnCheckedChangeListener(this);
    }
    if (ArrayUtils.contains(((AddressFormOuterClass.AddressForm)this.mFormProto).readOnlyField, 2))
    {
      this.mRecipientNameText = ((TextView)paramLayoutInflater.inflate(R.layout.view_form_non_editable_text, this.mContainer, false));
      this.mRecipientNameText.setTag(Character.valueOf('N'));
      this.mRecipientNameText.setId(R.id.address_field_recipient);
      this.mContainer.addView(this.mRecipientNameText, 1 + this.mContainer.indexOfChild(this.mHideAddressCheckbox));
      this.mRegionCodeView = ((RegionCodeView)this.mContainer.findViewById(R.id.region_code_view));
      this.mDynamicAddressFieldsLayout = ((DynamicAddressFieldsLayout)this.mContainer.findViewById(R.id.dynamic_address_fields_layout));
      if (((AddressFormOuterClass.AddressForm)this.mFormProto).phoneNumberForm)
      {
        if (!ArrayUtils.contains(((AddressFormOuterClass.AddressForm)this.mFormProto).readOnlyField, 8)) {
          break label677;
        }
        this.mPhoneNumberText = ((TextView)paramLayoutInflater.inflate(R.layout.view_form_non_editable_text, this.mContainer, false));
        label311:
        this.mPhoneNumberText.setId(R.id.address_field_phone_number);
        if (Build.VERSION.SDK_INT >= 17) {
          this.mPhoneNumberText.setTextDirection(3);
        }
        if (Build.VERSION.SDK_INT >= 11) {
          this.mPhoneNumberText.setLayerType(2, null);
        }
        this.mContainer.addView(this.mPhoneNumberText, 1 + this.mContainer.indexOfChild(this.mDynamicAddressFieldsLayout));
        if ((paramBundle == null) && (TextUtils.isEmpty(this.mPhoneNumberText.getText())))
        {
          if (TextUtils.isEmpty(this.mInitialValue.phoneNumber)) {
            break label746;
          }
          setPhoneNumber(this.mInitialValue.phoneNumber);
        }
      }
    }
    TextView localTextView1;
    AddressFormOuterClass.AddressFormValue localAddressFormValue;
    ArrayList localArrayList;
    for (;;)
    {
      this.mDynamicAddressFieldsLayout.setOnHeightOffsetChangedListener(this);
      this.mReadOnlyContainer = this.mContainer.findViewById(R.id.address_read_only_container);
      this.mReadOnlyText = ((TextView)this.mContainer.findViewById(R.id.address_read_only_text));
      this.mEditAddressIcon = ((ImageButton)this.mContainer.findViewById(R.id.edit_address_icon));
      if (!this.mIsReadOnlyMode) {
        break label994;
      }
      localTextView1 = this.mReadOnlyText;
      localAddressFormValue = this.mInitialValue;
      localArrayList = new ArrayList();
      for (int m : ((AddressFormOuterClass.AddressForm)this.mFormProto).hiddenField)
      {
        int n = I18N_FIELD_ID_TO_ADDRESS_FORM_FIELD_ID.indexOfValue(m);
        if (n >= 0) {
          localArrayList.add(Character.valueOf((char)I18N_FIELD_ID_TO_ADDRESS_FORM_FIELD_ID.keyAt(n)));
        }
      }
      this.mRecipientNameText = ((TextView)paramLayoutInflater.inflate(R.layout.view_form_edit_text, this.mContainer, false));
      this.mRecipientNameText.setHint(getFieldLabel('N'));
      this.mRecipientNameText.setInputType(8289);
      if (ArrayUtils.contains(((AddressFormOuterClass.AddressForm)this.mFormProto).hiddenField, 2)) {
        ((FormEditText)this.mRecipientNameText).setShouldValidateWhenNotVisible(false);
      }
      FormEditText localFormEditText = (FormEditText)this.mRecipientNameText;
      TextWatcher localTextWatcher = this.mRecipientNameTextWatcher;
      localFormEditText.mUserInputOnlyTextWatcherList.add(localTextWatcher);
      break;
      label677:
      this.mPhoneNumberText = ((TextView)paramLayoutInflater.inflate(R.layout.view_form_edit_text, this.mContainer, false));
      this.mPhoneNumberText.setHint(R.string.wallet_uic_phone_number);
      this.mPhoneNumberText.setInputType(3);
      if (!ArrayUtils.contains(((AddressFormOuterClass.AddressForm)this.mFormProto).hiddenField, 8)) {
        break label311;
      }
      ((FormEditText)this.mPhoneNumberText).setShouldValidateWhenNotVisible(false);
      break label311;
      label746:
      if (WalletUiUtils.tryAutoFillPhoneUiField(getActivity(), this.mPhoneNumberText)) {
        if ((this.mPhoneNumberText instanceof FormEditText)) {
          this.mInitialValue.phoneNumber = ((FormEditText)this.mPhoneNumberText).getValue();
        } else {
          this.mInitialValue.phoneNumber = this.mPhoneNumberText.getText().toString();
        }
      }
    }
    String str1 = AddressFormatter.formatAddress(localAddressFormValue.address, "\n", null, ArrayUtils.toCharArray(localArrayList));
    String str3;
    if ((((AddressFormOuterClass.AddressForm)this.mFormProto).phoneNumberForm) && (!ArrayUtils.contains(((AddressFormOuterClass.AddressForm)this.mFormProto).hiddenField, 8))) {
      str3 = str1 + "\n";
    }
    for (String str2 = str3 + localAddressFormValue.phoneNumber;; str2 = str1)
    {
      localTextView1.setText(str2);
      if (this.mCanSwitchToEditMode) {
        if (((AddressFormOuterClass.AddressForm)this.mFormProto).editability != 4) {
          break label997;
        }
      }
      label994:
      label997:
      for (int k = R.attr.uicClearDrawable;; k = R.attr.uicEditDrawable)
      {
        TypedArray localTypedArray = this.mThemedContext.obtainStyledAttributes(new int[] { k });
        this.mEditAddressIcon.setImageDrawable(localTypedArray.getDrawable(0));
        localTypedArray.recycle();
        this.mEditAddressIcon.setVisibility(0);
        this.mEditAddressIcon.setOnClickListener(this);
        return localView;
      }
    }
  }
  
  public final void onDestroyView()
  {
    super.onDestroyView();
    this.mPendingRequestCounter = 0;
    doEnableUi();
  }
  
  public final void onDetach()
  {
    super.onDetach();
    this.mOnRegionCodeSelectedListener = null;
    cancelPendingAdminAreaMetadataRetrievalRequest();
    getRequestQueue().cancelAll(new RequestQueue.RequestFilter()
    {
      public final boolean apply(Request<?> paramAnonymousRequest)
      {
        return paramAnonymousRequest instanceof CountryMetadataRetrievalTask.CountryMetadataRetrievalRequest;
      }
    });
  }
  
  @TargetApi(11)
  public final void onHeightOffsetChanged(float paramFloat)
  {
    if ((this.mPhoneNumberText != null) && (this.mPhoneNumberText.getVisibility() == 0)) {
      this.mPhoneNumberText.setTranslationY(paramFloat);
    }
    if (this.mOnHeightOffsetChangedListener != null) {
      this.mOnHeightOffsetChangedListener.onHeightOffsetChanged(paramFloat);
    }
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("selectedCountry", this.mSelectedCountry);
    paramBundle.putIntArray("regionCodes", this.mRegionCodes);
    if (this.mPendingAddress != null) {
      paramBundle.putParcelable("pendingAddress", ParcelableProto.forProto(this.mPendingAddress));
    }
    if (this.mCountryData != null) {
      paramBundle.putString("countryData", this.mCountryData.toString());
    }
    paramBundle.putString("languageCode", this.mLanguageCode);
    if (this.mAdminAreaData != null) {
      paramBundle.putString("adminAreaData", this.mAdminAreaData.toString());
    }
    paramBundle.putBoolean("isReadOnlyMode", this.mIsReadOnlyMode);
  }
  
  public final void setRecipientName(String paramString)
  {
    if (this.mRecipientNameText != null)
    {
      if ((this.mRecipientNameText instanceof FormEditText)) {
        ((FormEditText)this.mRecipientNameText).setValue(paramString, true);
      }
    }
    else {
      return;
    }
    this.mRecipientNameText.setText(paramString);
  }
  
  public final void setSelectedCountry(int paramInt, boolean paramBoolean)
  {
    if (paramInt != this.mSelectedCountry)
    {
      this.mSelectedCountry = paramInt;
      onSelectedCountryChange();
      if ((this.mOnRegionCodeSelectedListener != null) && (paramInt != 0)) {
        this.mOnRegionCodeSelectedListener.onRegionCodeSelected(paramInt, this.mFragmentId, paramBoolean);
      }
    }
  }
  
  protected final boolean validate(int[] paramArrayOfInt, boolean paramBoolean)
  {
    boolean bool = true;
    if (this.mHidden) {}
    do
    {
      do
      {
        return bool;
        if (!this.mUiEnabled) {
          return false;
        }
        if (hasPendingRequests()) {
          return false;
        }
        if (this.mContainer == null) {
          return false;
        }
      } while (areFormFieldsHidden());
      if (this.mSelectedCountry == 0) {
        return false;
      }
      bool = super.validate(paramArrayOfInt, paramBoolean);
      if ((this.mRecipientNameText != null) && (this.mRecipientNameListener != null) && (!TextUtils.isEmpty(this.mRecipientNameText.getError()))) {
        this.mRecipientNameListener.onRecipientNameErrorOccurred();
      }
      if ((!bool) && (((AddressFormOuterClass.AddressForm)this.mFormProto).editability == 2)) {
        throw new IllegalArgumentException("Read-only address form has invalid value.");
      }
    } while ((!paramBoolean) || (bool) || (!this.mIsReadOnlyMode));
    this.mIsReadOnlyMode = false;
    setAddressFieldVisibilities();
    return bool;
  }
  
  static final class AdminArea
    implements SelectableItem
  {
    JSONObject mData;
    final boolean mHasLocalities;
    final String mI18nKey;
    final String mLabel;
    final String mValue;
    final String mZipStartsWithRegEx;
    
    AdminArea(String paramString1, boolean paramBoolean, String paramString2, String paramString3, String paramString4)
    {
      this.mI18nKey = paramString1;
      this.mHasLocalities = paramBoolean;
      this.mValue = paramString2;
      this.mLabel = paramString3;
      this.mZipStartsWithRegEx = paramString4;
    }
    
    public final String getValue()
    {
      return this.mValue;
    }
    
    public final String toString()
    {
      return this.mLabel;
    }
  }
  
  final class FetchAddressTask
    extends AsyncTask<AddressSourceResult, Void, Postaladdress.PostalAddress>
  {
    private final ArrayList<AddressSource> mCopyOfAddressSources;
    private final String mCurrentLanguageCode;
    private final View mInput;
    
    public FetchAddressTask(View paramView)
    {
      this.mInput = paramView;
      this.mCurrentLanguageCode = AddressEntryFragment.this.getAddressLanguageCode();
      if (AddressEntryFragment.this.mAddressSources != null) {}
      for (ArrayList localArrayList = new ArrayList(AddressEntryFragment.this.mAddressSources);; localArrayList = new ArrayList())
      {
        this.mCopyOfAddressSources = localArrayList;
        return;
      }
    }
  }
  
  static final class Locality
    implements SelectableItem
  {
    final String mLabel;
    final String mValue;
    
    Locality(String paramString1, String paramString2)
    {
      this.mValue = paramString1;
      this.mLabel = paramString2;
    }
    
    public final String getValue()
    {
      return this.mValue;
    }
    
    public final String toString()
    {
      return this.mLabel;
    }
  }
  
  public static abstract interface OnAddressFieldsShownListener
  {
    public abstract void onAddressFieldsShown(boolean paramBoolean);
  }
  
  public static abstract interface RecipientNameListener
  {
    public abstract void onRecipientNameChanged$552c4e01();
    
    public abstract void onRecipientNameErrorOccurred();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.address.AddressEntryFragment
 * JD-Core Version:    0.7.0.1
 */