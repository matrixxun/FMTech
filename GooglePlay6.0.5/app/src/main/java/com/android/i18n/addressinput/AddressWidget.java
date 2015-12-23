package com.android.i18n.addressinput;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.android.finsky.layout.AddressAutoComplete;
import com.google.android.finsky.layout.AddressFieldsLayout;
import com.google.android.finsky.layout.AddressSuggestionProvider;
import com.google.android.finsky.utils.BackgroundThreadFactory;
import com.google.android.finsky.utils.UiUtils;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public final class AddressWidget
  implements AdapterView.OnItemSelectedListener
{
  private static final Map<String, Integer> ADMIN_ERROR_MESSAGES;
  private static final Map<String, Integer> ADMIN_LABELS;
  private static final FormOptions SHOW_ALL_FIELDS = new FormOptions.Builder().build();
  private String mAdminLabel;
  private CacheData mCacheData;
  private Context mContext;
  public String mCurrentRegion;
  public FormController mFormController;
  public FormOptions mFormOptions;
  public FormatInterpreter mFormatInterpreter;
  final Handler mHandler = new Handler();
  private LayoutInflater mInflater;
  public final EnumMap<AddressField, AddressUIComponent> mInputWidgets = new EnumMap(AddressField.class);
  public Listener mListener;
  public AddressFieldsLayout mRootView;
  public AddressData mSavedAddress;
  private Map<AddressField, String> mSavedErrors;
  public LookupKey.ScriptType mScript;
  private ArrayList<AddressSpinnerInfo> mSpinners = new ArrayList();
  private AddressSuggestionProvider mSuggestionProvider;
  private StandardAddressVerifier mVerifier;
  private String mWidgetLocale;
  private ZipLabel mZipLabel;
  
  static
  {
    HashMap localHashMap1 = new HashMap(15);
    localHashMap1.put("area", Integer.valueOf(2131362205));
    localHashMap1.put("county", Integer.valueOf(2131362206));
    localHashMap1.put("department", Integer.valueOf(2131362207));
    localHashMap1.put("district", Integer.valueOf(2131362208));
    localHashMap1.put("do_si", Integer.valueOf(2131362209));
    localHashMap1.put("emirate", Integer.valueOf(2131362210));
    localHashMap1.put("island", Integer.valueOf(2131362211));
    localHashMap1.put("parish", Integer.valueOf(2131362214));
    localHashMap1.put("prefecture", Integer.valueOf(2131362216));
    localHashMap1.put("province", Integer.valueOf(2131362217));
    localHashMap1.put("state", Integer.valueOf(2131362219));
    ADMIN_LABELS = Collections.unmodifiableMap(localHashMap1);
    HashMap localHashMap2 = new HashMap(15);
    localHashMap2.put("area", Integer.valueOf(2131362261));
    localHashMap2.put("county", Integer.valueOf(2131362263));
    localHashMap2.put("department", Integer.valueOf(2131362264));
    localHashMap2.put("district", Integer.valueOf(2131362265));
    localHashMap2.put("do_si", Integer.valueOf(2131362266));
    localHashMap2.put("emirate", Integer.valueOf(2131362268));
    localHashMap2.put("island", Integer.valueOf(2131362270));
    localHashMap2.put("parish", Integer.valueOf(2131362273));
    localHashMap2.put("prefecture", Integer.valueOf(2131362277));
    localHashMap2.put("province", Integer.valueOf(2131362278));
    localHashMap2.put("state", Integer.valueOf(2131362279));
    ADMIN_ERROR_MESSAGES = Collections.unmodifiableMap(localHashMap2);
  }
  
  public AddressWidget(Context paramContext, AddressFieldsLayout paramAddressFieldsLayout, FormOptions paramFormOptions, ClientCacheManager paramClientCacheManager, String paramString)
  {
    if ((paramString != null) && (isValidRegionCode(paramString))) {}
    for (this.mCurrentRegion = paramString;; this.mCurrentRegion = getDefaultRegionCode(paramContext))
    {
      this.mContext = paramContext;
      this.mRootView = paramAddressFieldsLayout;
      this.mFormOptions = paramFormOptions;
      this.mCacheData = new CacheData(paramClientCacheManager);
      this.mInflater = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
      this.mFormController = new FormController(new ClientData(this.mCacheData), this.mWidgetLocale, this.mCurrentRegion);
      this.mFormatInterpreter = new FormatInterpreter(this.mFormOptions);
      this.mVerifier = new StandardAddressVerifier(new FieldVerifier(new ClientData(this.mCacheData)));
      return;
    }
  }
  
  private AddressSpinnerInfo findSpinnerByView(View paramView)
  {
    Iterator localIterator = this.mSpinners.iterator();
    while (localIterator.hasNext())
    {
      AddressSpinnerInfo localAddressSpinnerInfo = (AddressSpinnerInfo)localIterator.next();
      if (localAddressSpinnerInfo.mView == paramView) {
        return localAddressSpinnerInfo;
      }
    }
    return null;
  }
  
  private static String getDefaultRegionCode(Context paramContext)
  {
    if (paramContext == null) {}
    String str;
    do
    {
      return "US";
      str = ((TelephonyManager)paramContext.getSystemService("phone")).getSimCountryIso().toUpperCase();
    } while ((str == null) || (str.length() != 2));
    return str;
  }
  
  public static List<String> getFullEnvelopeAddress(AddressData paramAddressData, Context paramContext)
  {
    FormatInterpreter localFormatInterpreter = new FormatInterpreter(SHOW_ALL_FIELDS);
    Object localObject = getDefaultRegionCode(paramContext);
    Util.checkNotNull(paramAddressData, "null input address not allowed");
    String str1 = paramAddressData.mPostalCountry;
    if (!isValidRegionCode(str1)) {}
    for (;;)
    {
      String str2 = paramAddressData.mLanguageCode;
      LookupKey.ScriptType localScriptType = LookupKey.ScriptType.LOCAL;
      ArrayList localArrayList;
      StringBuilder localStringBuilder;
      Iterator localIterator;
      if (str2 != null)
      {
        if (Util.isExplicitLatinScript(str2)) {
          localScriptType = LookupKey.ScriptType.LATIN;
        }
      }
      else
      {
        localArrayList = new ArrayList();
        localStringBuilder = new StringBuilder();
        localIterator = localFormatInterpreter.getFormatSubStrings(localScriptType, (String)localObject).iterator();
      }
      for (;;)
      {
        if (!localIterator.hasNext()) {
          break label404;
        }
        String str4 = (String)localIterator.next();
        if (str4.equals("%n"))
        {
          String str6 = FormatInterpreter.removeAllRedundantSpaces(localStringBuilder.toString());
          if (str6.length() <= 0) {
            continue;
          }
          localArrayList.add(str6);
          localStringBuilder.setLength(0);
          continue;
          localScriptType = LookupKey.ScriptType.LOCAL;
          break;
        }
        if (str4.startsWith("%"))
        {
          char c = str4.charAt(1);
          AddressField localAddressField = AddressField.of(c);
          Util.checkNotNull(localAddressField, "null address field for character " + c);
          int i = FormatInterpreter.2.$SwitchMap$com$android$i18n$addressinput$AddressField[localAddressField.ordinal()];
          String str5 = null;
          switch (i)
          {
          }
          for (;;)
          {
            if (str5 == null) {
              break label391;
            }
            localStringBuilder.append(str5);
            break;
            String[] arrayOfString = new String[2];
            arrayOfString[0] = paramAddressData.mAddressLine1;
            arrayOfString[1] = paramAddressData.mAddressLine2;
            str5 = Util.joinAndSkipNulls("\n", arrayOfString);
            continue;
            str5 = paramAddressData.mAdministrativeArea;
            continue;
            str5 = paramAddressData.mLocality;
            continue;
            str5 = paramAddressData.mDependentLocality;
            continue;
            str5 = paramAddressData.mRecipient;
            continue;
            str5 = paramAddressData.mOrganization;
            continue;
            str5 = paramAddressData.mPostalCode;
          }
        }
        else
        {
          label391:
          localStringBuilder.append(str4);
        }
      }
      label404:
      String str3 = FormatInterpreter.removeAllRedundantSpaces(localStringBuilder.toString());
      if (str3.length() > 0) {
        localArrayList.add(str3);
      }
      return localArrayList;
      localObject = str1;
    }
  }
  
  private List<RegionData> getRegionData(AddressField paramAddressField)
  {
    AddressData localAddressData = getAddressData();
    if (this.mFormController.isDefaultLanguage(localAddressData.mLanguageCode))
    {
      AddressData.Builder localBuilder = new AddressData.Builder(localAddressData);
      localBuilder.mLanguage = null;
      localAddressData = localBuilder.build();
    }
    LookupKey localLookupKey = FormController.getDataKeyFor(localAddressData).getKeyForUpperLevelField(paramAddressField);
    if (localLookupKey == null)
    {
      Log.w(toString(), "Can't build key with parent field " + paramAddressField + ". One of the ancestor fields might be empty");
      return new ArrayList(1);
    }
    return this.mFormController.getRegionData(localLookupKey);
  }
  
  private static boolean isValidRegionCode(String paramString)
  {
    return RegionDataConstants.getCountryFormatMap().containsKey(paramString);
  }
  
  private void setViewErrors(Map<AddressField, String> paramMap)
  {
    Iterator localIterator = paramMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      AddressField localAddressField = (AddressField)localIterator.next();
      AddressUIComponent localAddressUIComponent = (AddressUIComponent)this.mInputWidgets.get(localAddressField);
      if (localAddressUIComponent != null)
      {
        View localView = localAddressUIComponent.mView;
        if ((localView != null) && (localAddressUIComponent.mUiType == AddressUIComponent.UIComponent.EDIT))
        {
          String str = (String)paramMap.get(localAddressField);
          if (str != null) {
            UiUtils.setErrorOnTextView((EditText)localView, localAddressUIComponent.mFieldName, str);
          }
        }
      }
    }
  }
  
  public final TextView displayErrorMessageForInvalidEntryIn(AddressField paramAddressField)
  {
    AddressUIComponent localAddressUIComponent = (AddressUIComponent)this.mInputWidgets.get(paramAddressField);
    if ((localAddressUIComponent != null) && (localAddressUIComponent.mUiType == AddressUIComponent.UIComponent.EDIT))
    {
      int i;
      switch (3.$SwitchMap$com$android$i18n$addressinput$AddressField[paramAddressField.ordinal()])
      {
      default: 
        i = 2131362269;
      }
      for (;;)
      {
        EditText localEditText = (EditText)localAddressUIComponent.mView;
        UiUtils.setErrorOnTextView(localEditText, localAddressUIComponent.mFieldName, this.mContext.getString(i));
        return localEditText;
        i = ((Integer)ADMIN_ERROR_MESSAGES.get(this.mAdminLabel)).intValue();
        continue;
        i = 2131362271;
        continue;
        i = 2131362265;
        continue;
        if (this.mZipLabel == ZipLabel.POSTAL) {
          i = 2131362276;
        } else {
          i = 2131362281;
        }
      }
    }
    return null;
  }
  
  public final AddressData getAddressData()
  {
    AddressData.Builder localBuilder = new AddressData.Builder();
    localBuilder.setCountry(this.mCurrentRegion);
    Iterator localIterator1 = this.mFormatInterpreter.getAddressFieldOrder(this.mScript, this.mCurrentRegion).iterator();
    while (localIterator1.hasNext())
    {
      AddressField localAddressField = (AddressField)localIterator1.next();
      AddressUIComponent localAddressUIComponent = (AddressUIComponent)this.mInputWidgets.get(localAddressField);
      if (localAddressUIComponent != null)
      {
        RegionData localRegionData;
        if (localAddressUIComponent.mView == null)
        {
          if (localAddressUIComponent.mCandidatesList.size() == 0) {
            break label244;
          }
          str = ((RegionData)localAddressUIComponent.mCandidatesList.get(0)).getDisplayName();
          if (localAddressUIComponent.mUiType == AddressUIComponent.UIComponent.SPINNER)
          {
            AddressSpinnerInfo localAddressSpinnerInfo = findSpinnerByView(getViewForField(localAddressField));
            if (localAddressSpinnerInfo != null)
            {
              Iterator localIterator2 = localAddressSpinnerInfo.mCurrentRegions.iterator();
              do
              {
                if (!localIterator2.hasNext()) {
                  break;
                }
                localRegionData = (RegionData)localIterator2.next();
              } while (!localRegionData.getDisplayName().endsWith(str));
            }
          }
        }
        for (String str = localRegionData.mKey;; str = "")
        {
          localBuilder.set(localAddressField, str);
          break;
          switch (AddressUIComponent.1.$SwitchMap$com$android$i18n$addressinput$AddressUIComponent$UIComponent[localAddressUIComponent.mUiType.ordinal()])
          {
          default: 
          case 1: 
            Object localObject;
            do
            {
              str = "";
              break;
              localObject = ((Spinner)localAddressUIComponent.mView).getSelectedItem();
            } while (localObject == null);
            str = localObject.toString();
            break;
          case 2: 
            label244:
            str = ((EditText)localAddressUIComponent.mView).getText().toString();
            break;
          }
        }
      }
    }
    localBuilder.mLanguage = this.mWidgetLocale;
    return localBuilder.build();
  }
  
  public final AddressProblems getAddressProblems()
  {
    AddressProblems localAddressProblems = new AddressProblems();
    AddressData localAddressData = getAddressData();
    StandardAddressVerifier localStandardAddressVerifier = this.mVerifier;
    NotifyingListener localNotifyingListener = new NotifyingListener(localStandardAddressVerifier);
    BackgroundThreadFactory.createThread(new StandardAddressVerifier.Verifier(localStandardAddressVerifier, localAddressData, localAddressProblems, localNotifyingListener)).start();
    try
    {
      localNotifyingListener.waitLoadingEnd();
      localAddressProblems.mProblems.keySet().removeAll(this.mFormOptions.mHiddenFields);
      if (this.mFormOptions.isHidden(AddressField.ADMIN_AREA))
      {
        AddressField localAddressField = AddressField.POSTAL_CODE;
        if ((AddressProblemType)localAddressProblems.mProblems.get(localAddressField) != AddressProblemType.MISSING_REQUIRED_FIELD) {
          localAddressProblems.mProblems.remove(AddressField.POSTAL_CODE);
        }
      }
      return localAddressProblems;
    }
    catch (InterruptedException localInterruptedException)
    {
      throw new RuntimeException(localInterruptedException);
    }
  }
  
  public final String getNameForField(AddressField paramAddressField)
  {
    AddressUIComponent localAddressUIComponent = (AddressUIComponent)this.mInputWidgets.get(paramAddressField);
    if (localAddressUIComponent == null) {
      return null;
    }
    return localAddressUIComponent.mFieldName;
  }
  
  public final View getViewForField(AddressField paramAddressField)
  {
    AddressUIComponent localAddressUIComponent = (AddressUIComponent)this.mInputWidgets.get(paramAddressField);
    if (localAddressUIComponent == null) {
      return null;
    }
    return localAddressUIComponent.mView;
  }
  
  public final void initializeFieldsWithAddress(AddressData paramAddressData, boolean paramBoolean)
  {
    Iterator localIterator1 = this.mFormatInterpreter.getAddressFieldOrder(this.mScript, this.mCurrentRegion).iterator();
    while (localIterator1.hasNext())
    {
      AddressField localAddressField = (AddressField)localIterator1.next();
      AddressUIComponent localAddressUIComponent = (AddressUIComponent)this.mInputWidgets.get(localAddressField);
      if (localAddressUIComponent != null)
      {
        String str = paramAddressData.getFieldValue(localAddressField);
        if (str == null) {
          str = "";
        }
        View localView = localAddressUIComponent.mView;
        if (localView != null) {
          if (localAddressUIComponent.mUiType == AddressUIComponent.UIComponent.SPINNER)
          {
            AddressSpinnerInfo localAddressSpinnerInfo = findSpinnerByView(localView);
            if (localAddressSpinnerInfo != null) {
              if (paramBoolean)
              {
                RegionData localRegionData2 = localAddressSpinnerInfo.findRegionByKeyIgnoreCase(str);
                if (localRegionData2 != null)
                {
                  int j = localAddressSpinnerInfo.mAdapter.getPosition(localRegionData2.getDisplayName());
                  if (j >= 0) {
                    localAddressSpinnerInfo.mView.setSelection(j);
                  }
                }
              }
              else
              {
                RegionData localRegionData1 = localAddressSpinnerInfo.findRegionByKeyIgnoreCase(str);
                if (localRegionData1 == null)
                {
                  Iterator localIterator2 = localAddressSpinnerInfo.mCurrentRegions.iterator();
                  do
                  {
                    if (!localIterator2.hasNext()) {
                      break;
                    }
                    localRegionData1 = (RegionData)localIterator2.next();
                  } while (!localRegionData1.getDisplayName().equalsIgnoreCase(str));
                }
                for (;;)
                {
                  if (localRegionData1 == null) {
                    break label267;
                  }
                  int i = localAddressSpinnerInfo.mAdapter.getPosition(localRegionData1.getDisplayName());
                  if (i < 0) {
                    break;
                  }
                  localAddressSpinnerInfo.mView.setSelection(i);
                  break;
                  localRegionData1 = null;
                }
              }
            }
          }
          else
          {
            label267:
            ((EditText)localView).setText(str);
          }
        }
      }
    }
  }
  
  public final void onItemSelected(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    AddressSpinnerInfo localAddressSpinnerInfo = findSpinnerByView(paramAdapterView);
    if (localAddressSpinnerInfo != null)
    {
      final AddressField localAddressField = localAddressSpinnerInfo.mId;
      if ((localAddressField == AddressField.ADMIN_AREA) || (localAddressField == AddressField.LOCALITY)) {
        this.mFormController.requestDataForAddress(getAddressData(), new DataLoadListener()
        {
          public final void dataLoadingEnd()
          {
            AddressWidget.UpdateRunnable localUpdateRunnable = new AddressWidget.UpdateRunnable(AddressWidget.this, localAddressField);
            AddressWidget.this.mHandler.post(localUpdateRunnable);
          }
        });
      }
    }
  }
  
  public final void onNothingSelected(AdapterView<?> paramAdapterView) {}
  
  public final void renderForm()
  {
    this.mWidgetLocale = Util.getWidgetCompatibleLanguageCode(Locale.getDefault(), this.mCurrentRegion);
    this.mFormController.mLanguageCode = this.mWidgetLocale;
    if (Util.isExplicitLatinScript(this.mWidgetLocale)) {}
    for (LookupKey.ScriptType localScriptType = LookupKey.ScriptType.LATIN;; localScriptType = LookupKey.ScriptType.LOCAL)
    {
      this.mScript = localScriptType;
      AddressData.Builder localBuilder = new AddressData.Builder().setCountry(this.mCurrentRegion);
      localBuilder.mLanguage = this.mWidgetLocale;
      AddressData localAddressData = localBuilder.build();
      this.mFormController.requestDataForAddress(localAddressData, new DataLoadListener()
      {
        public final void dataLoadingEnd()
        {
          Log.d(toString(), "Data loading completed.");
          AddressWidget.this.mHandler.post(new Runnable()
          {
            public final void run()
            {
              AddressWidget.access$500(AddressWidget.this);
              if (AddressWidget.this.mListener != null) {
                AddressWidget.this.mListener.onInitialized();
              }
            }
          });
        }
      });
      this.mRootView.showProgressBar();
      return;
    }
  }
  
  public final void renderFormWithSavedAddress(AddressData paramAddressData)
  {
    if (paramAddressData != null)
    {
      this.mSavedAddress = paramAddressData;
      this.mRootView.disableOneFieldMode();
    }
    renderForm();
  }
  
  public final void restoreInstanceState(Bundle paramBundle)
  {
    this.mSavedAddress = ((AddressData)paramBundle.getSerializable("address_data"));
    initializeFieldsWithAddress(this.mSavedAddress, true);
    ArrayList localArrayList1 = paramBundle.getIntegerArrayList("address_error_fields");
    ArrayList localArrayList2 = paramBundle.getStringArrayList("address_error_values");
    if ((localArrayList1 != null) && (localArrayList2 != null))
    {
      HashMap localHashMap = new HashMap();
      for (int i = 0; i < localArrayList1.size(); i++)
      {
        int j = ((Integer)localArrayList1.get(i)).intValue();
        String str = (String)localArrayList2.get(i);
        localHashMap.put(AddressField.values()[j], str);
      }
      this.mSavedErrors = localHashMap;
      setViewErrors(localHashMap);
    }
  }
  
  public final void saveInstanceState(Bundle paramBundle)
  {
    paramBundle.putSerializable("address_data", getAddressData());
    HashMap localHashMap = new HashMap();
    Iterator localIterator1 = this.mFormatInterpreter.getAddressFieldOrder(this.mScript, this.mCurrentRegion).iterator();
    while (localIterator1.hasNext())
    {
      AddressField localAddressField2 = (AddressField)localIterator1.next();
      AddressUIComponent localAddressUIComponent = (AddressUIComponent)this.mInputWidgets.get(localAddressField2);
      if (localAddressUIComponent != null)
      {
        View localView = localAddressUIComponent.mView;
        if ((localView != null) && (localAddressUIComponent.mUiType == AddressUIComponent.UIComponent.EDIT))
        {
          CharSequence localCharSequence = ((EditText)localView).getError();
          if (localCharSequence != null) {
            localHashMap.put(localAddressField2, localCharSequence.toString());
          }
        }
      }
    }
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    Iterator localIterator2 = localHashMap.keySet().iterator();
    while (localIterator2.hasNext())
    {
      AddressField localAddressField1 = (AddressField)localIterator2.next();
      localArrayList1.add(Integer.valueOf(localAddressField1.ordinal()));
      localArrayList2.add(localHashMap.get(localAddressField1));
    }
    paramBundle.putIntegerArrayList("address_error_fields", localArrayList1);
    paramBundle.putStringArrayList("address_error_values", localArrayList2);
  }
  
  public final void setSuggestionProvider(AddressSuggestionProvider paramAddressSuggestionProvider)
  {
    this.mSuggestionProvider = paramAddressSuggestionProvider;
    View localView = getViewForField(AddressField.ADDRESS_LINE_1);
    if ((localView != null) && ((localView instanceof AddressAutoComplete))) {
      ((AddressAutoComplete)localView).setSuggestionProvider(this.mSuggestionProvider);
    }
  }
  
  private static final class AddressSpinnerInfo
  {
    ArrayAdapter<String> mAdapter;
    List<RegionData> mCurrentRegions;
    AddressField mId;
    AddressField mParentId;
    Spinner mView;
    
    public AddressSpinnerInfo(Spinner paramSpinner, AddressField paramAddressField1, AddressField paramAddressField2)
    {
      this.mView = paramSpinner;
      this.mId = paramAddressField1;
      this.mParentId = paramAddressField2;
    }
    
    final RegionData findRegionByKeyIgnoreCase(String paramString)
    {
      Iterator localIterator = this.mCurrentRegions.iterator();
      while (localIterator.hasNext())
      {
        RegionData localRegionData = (RegionData)localIterator.next();
        if (localRegionData.mKey.equalsIgnoreCase(paramString)) {
          return localRegionData;
        }
      }
      return null;
    }
    
    public final void setSpinnerList(List<RegionData> paramList, String paramString)
    {
      this.mCurrentRegions = paramList;
      this.mAdapter.clear();
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        RegionData localRegionData = (RegionData)localIterator.next();
        this.mAdapter.add(localRegionData.getDisplayName());
      }
      this.mAdapter.sort(Collator.getInstance(Locale.getDefault()));
      if (paramString.length() == 0)
      {
        this.mView.setSelection(0);
        return;
      }
      int i = this.mAdapter.getPosition(paramString);
      this.mView.setSelection(i);
    }
  }
  
  public static abstract interface Listener
  {
    public abstract void onInitialized();
  }
  
  private final class UpdateRunnable
    implements Runnable
  {
    private AddressField myId;
    
    public UpdateRunnable(AddressField paramAddressField)
    {
      this.myId = paramAddressField;
    }
    
    public final void run()
    {
      AddressWidget.access$000(AddressWidget.this, this.myId);
    }
  }
  
  private static enum ZipLabel
  {
    static
    {
      POSTAL = new ZipLabel("POSTAL", 1);
      ZipLabel[] arrayOfZipLabel = new ZipLabel[2];
      arrayOfZipLabel[0] = ZIP;
      arrayOfZipLabel[1] = POSTAL;
      $VALUES = arrayOfZipLabel;
    }
    
    private ZipLabel() {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.i18n.addressinput.AddressWidget
 * JD-Core Version:    0.7.0.1
 */