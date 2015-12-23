package com.google.android.wallet.ui.simple;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.google.android.wallet.analytics.UiNode;
import com.google.android.wallet.analytics.WalletUiElement;
import com.google.android.wallet.common.address.AddressUtils;
import com.google.android.wallet.common.address.RegionCode;
import com.google.android.wallet.dependencygraph.DependencyGraphManager;
import com.google.android.wallet.dependencygraph.TriggerListener;
import com.google.android.wallet.ui.address.AddressEntryFragment;
import com.google.android.wallet.ui.common.BaseWalletUiComponentFragment;
import com.google.android.wallet.ui.common.ClickSpan.OnClickListener;
import com.google.android.wallet.ui.common.FormEditText;
import com.google.android.wallet.ui.common.FormFragment;
import com.google.android.wallet.ui.common.FormFragment.FieldData;
import com.google.android.wallet.ui.common.FormHeaderView;
import com.google.android.wallet.ui.common.InfoMessageTextView;
import com.google.android.wallet.ui.common.OnAutoAdvanceListener;
import com.google.android.wallet.ui.common.OtpFieldFragment;
import com.google.android.wallet.ui.common.RegionCodeView;
import com.google.android.wallet.ui.common.TooltipDialogFragment;
import com.google.android.wallet.ui.common.TooltipUiFieldView.OnTooltipIconClickListener;
import com.google.android.wallet.ui.common.UiFieldBuilder;
import com.google.android.wallet.ui.common.WalletUiUtils;
import com.google.android.wallet.ui.common.WebViewDialogFragment;
import com.google.android.wallet.uicomponents.R.attr;
import com.google.android.wallet.uicomponents.R.id;
import com.google.android.wallet.uicomponents.R.layout;
import com.google.commerce.payments.orchestration.proto.ui.common.FormFieldReferenceOuterClass.FormFieldReference;
import com.google.commerce.payments.orchestration.proto.ui.common.UiErrorOuterClass.FormFieldMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.components.AddressFormOuterClass.AddressForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.AddressFormOuterClass.CountrySelector;
import com.google.commerce.payments.orchestration.proto.ui.common.components.FormHeaderOuterClass.FormHeader;
import com.google.commerce.payments.orchestration.proto.ui.common.components.OtpFieldOuterClass.OtpField;
import com.google.commerce.payments.orchestration.proto.ui.common.components.SimpleFormOuterClass.Field;
import com.google.commerce.payments.orchestration.proto.ui.common.components.SimpleFormOuterClass.FieldValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.SimpleFormOuterClass.SimpleForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.SimpleFormOuterClass.SimpleForm.FormField;
import com.google.commerce.payments.orchestration.proto.ui.common.components.SimpleFormOuterClass.SimpleFormValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.SimpleFormOuterClass.SimpleFormValue.FormFieldValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.SimpleFormOuterClass.SubForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.SimpleFormOuterClass.SubFormValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.legal.LegalMessageOuterClass.LegalMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.InfoMessageOuterClass.InfoMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.TooltipOuterClass.Tooltip;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiField;
import java.util.ArrayList;
import java.util.List;

public class SimpleFragment
  extends FormFragment<SimpleFormOuterClass.SimpleForm>
  implements ClickSpan.OnClickListener, TooltipUiFieldView.OnTooltipIconClickListener
{
  final ArrayList<AddressEntryFragment> mAddressFragments = new ArrayList();
  private final ArrayList<UiNode> mChildUiNodes = new ArrayList();
  final ArrayList<RegionCodeView> mCountrySelectors = new ArrayList();
  private int mInfoMessageLayout;
  final ArrayList<InfoMessageTextView> mInfoMessages = new ArrayList();
  final ArrayList<View> mLegalMessages = new ArrayList();
  private int mNextViewId = 1;
  final ArrayList<OtpFieldFragment> mOtpFields = new ArrayList();
  final ArrayList<int[]> mRegionCodeLists = new ArrayList();
  private final ArrayList<Integer> mRevealBelowTriggerChildViewIndices = new ArrayList();
  private final ArrayList<Integer> mRevealBelowTriggerGrandChildViewIndices = new ArrayList();
  private int mRevealBelowTriggeredCount;
  LinearLayout mRootView;
  final ArrayList<SimpleFormFieldData> mSimpleFormFieldDataList = new ArrayList();
  private final ArrayList<ArrayList<UiNode>> mSubFormChildUiNodes = new ArrayList();
  private final ArrayList<UiNode> mSubFormUiNodes = new ArrayList();
  private final WalletUiElement mUiElement = new WalletUiElement(1715);
  final ArrayList<View> mUiFields = new ArrayList();
  
  private void addLeafUiNodeToUiNodeTree(UiNode paramUiNode, int paramInt)
  {
    if (paramInt < 0)
    {
      this.mChildUiNodes.add(paramUiNode);
      return;
    }
    paramUiNode.setParentUiNode((UiNode)this.mSubFormUiNodes.get(paramInt));
    ((ArrayList)this.mSubFormChildUiNodes.get(paramInt)).add(paramUiNode);
  }
  
  private void createField(ViewGroup paramViewGroup, SimpleFormOuterClass.Field paramField, String paramString, int paramInt1, int paramInt2)
  {
    int i = this.mNextViewId;
    this.mNextViewId = (i + 1);
    Object localObject1;
    int j;
    int k;
    int m;
    Object localObject2;
    Object localObject3;
    int i1;
    label180:
    OnAutoAdvanceListener local2;
    if (paramField.uiField != null)
    {
      UiFieldBuilder localUiFieldBuilder = new UiFieldBuilder(paramField.uiField, this.mThemedInflater);
      localUiFieldBuilder.mActivity = getActivity();
      localUiFieldBuilder.mViewId = i;
      localUiFieldBuilder.mOnTooltipIconClickListener = this;
      localObject1 = localUiFieldBuilder.build();
      j = 1;
      k = this.mUiFields.size();
      m = paramField.uiField.uiReference;
      localObject2 = localObject1;
      localObject3 = WalletUiUtils.getInitialValue(paramField.uiField);
      this.mUiFields.add(localObject1);
      WalletUiUtils.addComponentToDependencyGraph(localObject1, paramField.uiField.uiReference, this.mDependencyGraphManager, this.mTriggerListener);
      if (paramField.hideFieldsBelow)
      {
        final int n = this.mRevealBelowTriggerChildViewIndices.size();
        this.mRevealBelowTriggerChildViewIndices.add(Integer.valueOf(this.mRootView.getChildCount()));
        ArrayList localArrayList = this.mRevealBelowTriggerGrandChildViewIndices;
        if (paramInt1 != -1) {
          break label881;
        }
        i1 = -1;
        localArrayList.add(Integer.valueOf(i1));
        if (n >= this.mRevealBelowTriggeredCount)
        {
          local2 = new OnAutoAdvanceListener()
          {
            public final void onAutoAdvance(View paramAnonymousView)
            {
              SimpleFragment.access$100(SimpleFragment.this, n);
            }
          };
          View localView = WalletUiUtils.getBaseUiFieldView((View)localObject1);
          if (!(localView instanceof FormEditText)) {
            break label890;
          }
          ((FormEditText)localView).addOnAutoAdvanceListener(local2);
        }
      }
    }
    for (;;)
    {
      ((View)localObject1).setTag(R.id.field_type, Integer.valueOf(j));
      paramViewGroup.addView((View)localObject1);
      SimpleFormFieldData localSimpleFormFieldData = new SimpleFormFieldData(m, localObject2, localObject3);
      localSimpleFormFieldData.mFieldType = j;
      localSimpleFormFieldData.mIdxInFieldType = k;
      localSimpleFormFieldData.mFormHeaderId = paramString;
      localSimpleFormFieldData.mIdxInParent = paramInt2;
      this.mSimpleFormFieldDataList.add(localSimpleFormFieldData);
      return;
      if (paramField.addressForm != null)
      {
        localObject1 = new FrameLayout(this.mThemedContext);
        ((View)localObject1).setId(i);
        AddressEntryFragment localAddressEntryFragment = (AddressEntryFragment)getChildFragmentManager().findFragmentById(i);
        if (localAddressEntryFragment == null)
        {
          localAddressEntryFragment = AddressEntryFragment.newInstance(paramField.addressForm, this.mThemeResourceId);
          getChildFragmentManager().beginTransaction().add(i, localAddressEntryFragment).commit();
        }
        j = 4;
        k = this.mAddressFragments.size();
        m = paramField.addressForm.uiReference;
        localObject2 = localAddressEntryFragment;
        this.mAddressFragments.add(localAddressEntryFragment);
        addLeafUiNodeToUiNodeTree(localAddressEntryFragment, paramInt1);
        localObject3 = null;
        break;
      }
      if (paramField.infoMessage != null)
      {
        InfoMessageTextView localInfoMessageTextView = (InfoMessageTextView)this.mThemedInflater.inflate(this.mInfoMessageLayout, paramViewGroup, false);
        localInfoMessageTextView.setId(i);
        localInfoMessageTextView.setInfoMessage(paramField.infoMessage);
        localInfoMessageTextView.setUrlClickListener(this);
        k = this.mInfoMessages.size();
        m = paramField.infoMessage.uiReference;
        localObject2 = localInfoMessageTextView;
        this.mInfoMessages.add(localInfoMessageTextView);
        addLeafUiNodeToUiNodeTree(localInfoMessageTextView, paramInt1);
        localObject1 = localInfoMessageTextView;
        localObject3 = null;
        j = 0;
        break;
      }
      if (paramField.otpField != null)
      {
        localObject1 = new FrameLayout(this.mThemedContext);
        ((View)localObject1).setId(i);
        OtpFieldFragment localOtpFieldFragment = (OtpFieldFragment)getChildFragmentManager().findFragmentById(i);
        if (localOtpFieldFragment == null)
        {
          localOtpFieldFragment = createOtpFieldFragment(paramField.otpField);
          getChildFragmentManager().beginTransaction().add(i, localOtpFieldFragment).commit();
        }
        localOtpFieldFragment.registerDependencyGraphComponents(this.mDependencyGraphManager, this.mTriggerListener);
        j = 2;
        k = this.mOtpFields.size();
        m = paramField.otpField.otp.uiReference;
        localObject2 = localOtpFieldFragment;
        this.mOtpFields.add(localOtpFieldFragment);
        addLeafUiNodeToUiNodeTree(localOtpFieldFragment, paramInt1);
        localObject3 = null;
        break;
      }
      if (paramField.countrySelector != null)
      {
        RegionCodeView localRegionCodeView = (RegionCodeView)this.mThemedInflater.inflate(R.layout.view_region_code, paramViewGroup, false);
        localRegionCodeView.setId(i);
        k = this.mCountrySelectors.size();
        int[] arrayOfInt;
        if (this.mRegionCodeLists.size() > k) {
          arrayOfInt = (int[])this.mRegionCodeLists.get(k);
        }
        for (;;)
        {
          localRegionCodeView.setFormHeader(paramField.countrySelector.formHeader);
          localRegionCodeView.setRegionCodes(arrayOfInt);
          localRegionCodeView.setSelectedRegionCode$2563266(RegionCode.toRegionCode(paramField.countrySelector.initialCountryCode));
          j = 3;
          m = paramField.countrySelector.formHeader.uiReference;
          localObject2 = localRegionCodeView;
          this.mCountrySelectors.add(localRegionCodeView);
          localObject1 = localRegionCodeView;
          DependencyGraphManager localDependencyGraphManager = this.mDependencyGraphManager;
          TriggerListener localTriggerListener = this.mTriggerListener;
          WalletUiUtils.addComponentToDependencyGraph(localRegionCodeView, m, localDependencyGraphManager, localTriggerListener);
          localObject3 = paramField.countrySelector.initialCountryCode;
          break;
          arrayOfInt = AddressUtils.scrubAndSortRegionCodes(AddressUtils.stringArrayToRegionCodeArray(paramField.countrySelector.allowedCountryCode));
          this.mRegionCodeLists.add(arrayOfInt);
        }
      }
      throw new IllegalArgumentException("Empty or unknown field in SimpleForm.");
      label881:
      i1 = paramViewGroup.getChildCount();
      break label180;
      label890:
      if (j != 2) {
        break label916;
      }
      ((OtpFieldFragment)this.mOtpFields.get(k)).addOnAutoAdvanceListener(local2);
    }
    label916:
    throw new IllegalStateException("Invalid field type for hideFieldsBelow");
  }
  
  private SimpleFormOuterClass.FieldValue getFieldValue(SimpleFormOuterClass.Field paramField, int paramInt, Bundle paramBundle)
  {
    SimpleFormOuterClass.FieldValue localFieldValue = new SimpleFormOuterClass.FieldValue();
    SimpleFormFieldData localSimpleFormFieldData = (SimpleFormFieldData)this.mSimpleFormFieldDataList.get(paramInt);
    switch (localSimpleFormFieldData.mFieldType)
    {
    default: 
      throw new IllegalStateException("Unknown field type " + localSimpleFormFieldData.mFieldType + " in SimpleForm.");
    case 1: 
      localFieldValue.uiFieldValue = WalletUiUtils.createUiFieldValue((View)this.mUiFields.get(localSimpleFormFieldData.mIdxInFieldType), paramField.uiField);
    case 0: 
      return localFieldValue;
    case 4: 
      localFieldValue.addressFormValue = ((AddressEntryFragment)this.mAddressFragments.get(localSimpleFormFieldData.mIdxInFieldType)).getAddressFormValue$64352f99();
      return localFieldValue;
    case 2: 
      localFieldValue.otpFieldValue = ((OtpFieldFragment)this.mOtpFields.get(localSimpleFormFieldData.mIdxInFieldType)).getFieldValue(paramBundle);
      return localFieldValue;
    }
    localFieldValue.countrySelectorValue = ((RegionCodeView)this.mCountrySelectors.get(localSimpleFormFieldData.mIdxInFieldType)).getFieldValue();
    return localFieldValue;
  }
  
  private void hideBelow()
  {
    if (this.mRevealBelowTriggeredCount == this.mRevealBelowTriggerChildViewIndices.size()) {}
    for (;;)
    {
      return;
      int i = ((Integer)this.mRevealBelowTriggerChildViewIndices.get(this.mRevealBelowTriggeredCount)).intValue();
      int j = ((Integer)this.mRevealBelowTriggerGrandChildViewIndices.get(this.mRevealBelowTriggeredCount)).intValue();
      int k = i + 1;
      int m = this.mRootView.getChildCount();
      while (k < m)
      {
        this.mRootView.getChildAt(k).setVisibility(8);
        k++;
      }
      if (j >= 0)
      {
        ViewGroup localViewGroup = (ViewGroup)this.mRootView.getChildAt(i);
        int n = j + 1;
        int i1 = localViewGroup.getChildCount();
        while (n < i1)
        {
          localViewGroup.getChildAt(n).setVisibility(8);
          n++;
        }
      }
    }
  }
  
  public final boolean applyFormFieldMessage(UiErrorOuterClass.FormFieldMessage paramFormFieldMessage)
  {
    int i = 0;
    int j = this.mSimpleFormFieldDataList.size();
    while (i < j)
    {
      SimpleFormFieldData localSimpleFormFieldData = (SimpleFormFieldData)this.mSimpleFormFieldDataList.get(i);
      if ((paramFormFieldMessage.formFieldReference.formId.equals(localSimpleFormFieldData.mFormHeaderId)) && (paramFormFieldMessage.formFieldReference.repeatedFieldIndex == localSimpleFormFieldData.mIdxInParent))
      {
        if (localSimpleFormFieldData.mFieldType == 1) {
          WalletUiUtils.setUiFieldError((View)this.mUiFields.get(localSimpleFormFieldData.mIdxInFieldType), paramFormFieldMessage.message);
        }
        for (;;)
        {
          return true;
          if (localSimpleFormFieldData.mFieldType != 2) {
            break;
          }
          ((OtpFieldFragment)this.mOtpFields.get(localSimpleFormFieldData.mIdxInFieldType)).setError(paramFormFieldMessage.message);
        }
        throw new IllegalArgumentException("Could not apply FormFieldMessage to fieldId: " + paramFormFieldMessage.formFieldReference.fieldId);
      }
      i++;
    }
    int k = 0;
    int m = this.mAddressFragments.size();
    while (k < m)
    {
      if (((AddressEntryFragment)this.mAddressFragments.get(k)).applyFormFieldMessage(paramFormFieldMessage)) {
        return true;
      }
      k++;
    }
    return false;
  }
  
  public OtpFieldFragment createOtpFieldFragment(OtpFieldOuterClass.OtpField paramOtpField)
  {
    return OtpFieldFragment.newInstance(paramOtpField, this.mThemeResourceId);
  }
  
  protected final void doEnableUi()
  {
    if (this.mUiFields.size() + this.mAddressFragments.size() + this.mInfoMessages.size() + this.mOtpFields.size() + this.mLegalMessages.size() == 0) {}
    for (;;)
    {
      return;
      boolean bool = this.mUiEnabled;
      int i = 0;
      int j = this.mUiFields.size();
      while (i < j)
      {
        ((View)this.mUiFields.get(i)).setEnabled(bool);
        i++;
      }
      int k = 0;
      int m = this.mAddressFragments.size();
      while (k < m)
      {
        ((AddressEntryFragment)this.mAddressFragments.get(k)).enableUi(bool);
        k++;
      }
      int n = 0;
      int i1 = this.mInfoMessages.size();
      while (n < i1)
      {
        ((InfoMessageTextView)this.mInfoMessages.get(n)).setEnabled(bool);
        n++;
      }
      int i2 = 0;
      int i3 = this.mOtpFields.size();
      while (i2 < i3)
      {
        ((OtpFieldFragment)this.mOtpFields.get(i2)).enableUi(bool);
        i2++;
      }
      int i4 = 0;
      int i5 = this.mCountrySelectors.size();
      while (i4 < i5)
      {
        ((RegionCodeView)this.mCountrySelectors.get(i4)).setEnabled(bool);
        i4++;
      }
      int i6 = 0;
      int i7 = this.mLegalMessages.size();
      while (i6 < i7)
      {
        ((View)this.mLegalMessages.get(i6)).setEnabled(bool);
        i6++;
      }
    }
  }
  
  public List<UiNode> getChildren()
  {
    return this.mChildUiNodes;
  }
  
  public final List<SimpleFormFieldData> getFieldsForValidationInOrder()
  {
    return this.mSimpleFormFieldDataList;
  }
  
  public final SimpleFormOuterClass.SimpleFormValue getSimpleFormValue(Bundle paramBundle)
  {
    SimpleFormOuterClass.SimpleFormValue localSimpleFormValue = new SimpleFormOuterClass.SimpleFormValue();
    localSimpleFormValue.id = ((SimpleFormOuterClass.SimpleForm)this.mFormProto).formHeader.id;
    localSimpleFormValue.dataToken = ((SimpleFormOuterClass.SimpleForm)this.mFormProto).formHeader.dataToken;
    localSimpleFormValue.formFieldValue = new SimpleFormOuterClass.SimpleFormValue.FormFieldValue[((SimpleFormOuterClass.SimpleForm)this.mFormProto).formField.length];
    int i = 0;
    int j = ((SimpleFormOuterClass.SimpleForm)this.mFormProto).formField.length;
    int k = 0;
    SimpleFormOuterClass.SimpleForm.FormField localFormField;
    int i1;
    if (i < j)
    {
      localFormField = ((SimpleFormOuterClass.SimpleForm)this.mFormProto).formField[i];
      localSimpleFormValue.formFieldValue[i] = new SimpleFormOuterClass.SimpleFormValue.FormFieldValue();
      if (localFormField.subForm != null)
      {
        SimpleFormOuterClass.SubFormValue localSubFormValue = new SimpleFormOuterClass.SubFormValue();
        localSubFormValue.id = localFormField.subForm.formHeader.id;
        localSubFormValue.dataToken = localFormField.subForm.formHeader.dataToken;
        localSubFormValue.fieldValue = new SimpleFormOuterClass.FieldValue[localFormField.subForm.field.length];
        int m = 0;
        int n = localFormField.subForm.field.length;
        while (m < n)
        {
          SimpleFormOuterClass.FieldValue[] arrayOfFieldValue = localSubFormValue.fieldValue;
          SimpleFormOuterClass.Field localField1 = localFormField.subForm.field[m];
          int i2 = k + 1;
          arrayOfFieldValue[m] = getFieldValue(localField1, k, paramBundle);
          m++;
          k = i2;
        }
        if (localFormField.subForm.legalMessage != null) {
          localSubFormValue.legalDocData = localFormField.subForm.legalMessage.opaqueData;
        }
        localSimpleFormValue.formFieldValue[i].subFormValue = localSubFormValue;
        i1 = k;
      }
    }
    for (;;)
    {
      i++;
      k = i1;
      break;
      if (localFormField.field != null)
      {
        SimpleFormOuterClass.SimpleFormValue.FormFieldValue localFormFieldValue = localSimpleFormValue.formFieldValue[i];
        SimpleFormOuterClass.Field localField2 = localFormField.field;
        i1 = k + 1;
        localFormFieldValue.fieldValue = getFieldValue(localField2, k, paramBundle);
        continue;
        if (((SimpleFormOuterClass.SimpleForm)this.mFormProto).legalMessage != null) {
          localSimpleFormValue.legalDocData = ((SimpleFormOuterClass.SimpleForm)this.mFormProto).legalMessage.opaqueData;
        }
        return localSimpleFormValue;
      }
      else
      {
        i1 = k;
      }
    }
  }
  
  public WalletUiElement getUiElement()
  {
    return this.mUiElement;
  }
  
  public final boolean isReadyToSubmit()
  {
    int i = 0;
    int j = this.mAddressFragments.size();
    while (i < j)
    {
      this.mAddressFragments.get(i);
      i++;
    }
    return this.mRevealBelowTriggeredCount >= this.mRevealBelowTriggerChildViewIndices.size();
  }
  
  public final void onClick(View paramView, String paramString)
  {
    if (this.mFragmentManager.findFragmentByTag("tagWebViewDialog") != null) {
      return;
    }
    WebViewDialogFragment.newInstance(paramString, this.mThemeResourceId).show(this.mFragmentManager, "tagWebViewDialog");
  }
  
  public final void onClick(TooltipOuterClass.Tooltip paramTooltip)
  {
    if (this.mFragmentManager.findFragmentByTag("tagTooltipDialog") != null) {
      return;
    }
    TooltipDialogFragment localTooltipDialogFragment = TooltipDialogFragment.newInstance(paramTooltip, this.mThemeResourceId);
    localTooltipDialogFragment.setTargetFragment(this, -1);
    localTooltipDialogFragment.show(this.mFragmentManager, "tagTooltipDialog");
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      int j;
      for (int i = 0;; i = j)
      {
        StringBuilder localStringBuilder = new StringBuilder("regionCodes_");
        j = i + 1;
        int[] arrayOfInt = paramBundle.getIntArray(i);
        if (arrayOfInt == null) {
          break;
        }
        this.mRegionCodeLists.add(arrayOfInt);
      }
    }
  }
  
  protected final View onCreateThemedView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    ContextThemeWrapper localContextThemeWrapper = this.mThemedContext;
    int[] arrayOfInt = new int[4];
    arrayOfInt[0] = R.attr.internalUicInfoMessageLayout;
    arrayOfInt[1] = R.attr.internalUicLegalMessageLayout;
    arrayOfInt[2] = R.attr.internalUicFormNonEditableTextStartMargin;
    arrayOfInt[3] = R.attr.uicLegalMessageTopMargin;
    TypedArray localTypedArray = localContextThemeWrapper.obtainStyledAttributes(arrayOfInt);
    this.mInfoMessageLayout = localTypedArray.getResourceId(0, R.layout.view_info_message_text);
    int i = localTypedArray.getResourceId(1, R.layout.view_legal_message_text);
    int j = localTypedArray.getDimensionPixelSize(2, 0);
    int k = localTypedArray.getDimensionPixelSize(3, 0);
    localTypedArray.recycle();
    this.mRootView = ((LinearLayout)paramLayoutInflater.inflate(R.layout.fragment_simple, null));
    this.mNextViewId = ((FormHeaderView)this.mRootView.findViewById(R.id.header)).setFormHeader(((SimpleFormOuterClass.SimpleForm)this.mFormProto).formHeader, paramLayoutInflater, this, this, this.mChildUiNodes, this.mNextViewId);
    final WalletUiElement localWalletUiElement = new WalletUiElement(1716);
    int m = 0;
    int n = ((SimpleFormOuterClass.SimpleForm)this.mFormProto).formField.length;
    if (m < n)
    {
      SimpleFormOuterClass.SimpleForm.FormField localFormField = ((SimpleFormOuterClass.SimpleForm)this.mFormProto).formField[m];
      if (localFormField.subForm != null)
      {
        final int i2 = this.mSubFormChildUiNodes.size();
        this.mSubFormChildUiNodes.add(new ArrayList());
        UiNode local1 = new UiNode()
        {
          public final List<UiNode> getChildren()
          {
            return (List)SimpleFragment.this.mSubFormChildUiNodes.get(i2);
          }
          
          public final UiNode getParentUiNode()
          {
            return SimpleFragment.this;
          }
          
          public final WalletUiElement getUiElement()
          {
            return localWalletUiElement;
          }
          
          public final void setParentUiNode(UiNode paramAnonymousUiNode)
          {
            throw new UnsupportedOperationException("Custom parents not supported for SimpleForm subforms.");
          }
        };
        this.mChildUiNodes.add(local1);
        this.mSubFormUiNodes.add(local1);
        LinearLayout localLinearLayout = (LinearLayout)paramLayoutInflater.inflate(R.layout.view_subform, this.mRootView, false);
        this.mNextViewId = ((FormHeaderView)localLinearLayout.findViewById(R.id.subform_header)).setFormHeader(localFormField.subForm.formHeader, paramLayoutInflater, this, local1, (List)this.mSubFormChildUiNodes.get(i2), this.mNextViewId);
        int i3 = 0;
        int i4 = localFormField.subForm.field.length;
        while (i3 < i4)
        {
          createField(localLinearLayout, localFormField.subForm.field[i3], localFormField.subForm.formHeader.id, i2, i3);
          i3++;
        }
        if (localFormField.subForm.legalMessage != null)
        {
          View localView2 = WalletUiUtils.createViewForLegalMessage(this.mThemedInflater, i, localFormField.subForm.legalMessage, this, this);
          int i5 = this.mNextViewId;
          this.mNextViewId = (i5 + 1);
          localView2.setId(i5);
          localLinearLayout.addView(localView2);
          this.mLegalMessages.add(localView2);
          ((ArrayList)this.mSubFormChildUiNodes.get(i2)).add((UiNode)localView2);
          ViewGroup.MarginLayoutParams localMarginLayoutParams2 = (ViewGroup.MarginLayoutParams)localView2.getLayoutParams();
          MarginLayoutParamsCompat.setMarginStart(localMarginLayoutParams2, j);
          localMarginLayoutParams2.topMargin = k;
          localMarginLayoutParams2.bottomMargin = k;
        }
        this.mRootView.addView(localLinearLayout);
      }
      for (;;)
      {
        m++;
        break;
        if (localFormField.field == null) {
          break label566;
        }
        createField(this.mRootView, localFormField.field, ((SimpleFormOuterClass.SimpleForm)this.mFormProto).formHeader.id, -1, m);
      }
      label566:
      throw new IllegalArgumentException("Empty or unknown form field in SimpleForm.");
    }
    if (((SimpleFormOuterClass.SimpleForm)this.mFormProto).legalMessage != null)
    {
      View localView1 = WalletUiUtils.createViewForLegalMessage(this.mThemedInflater, i, ((SimpleFormOuterClass.SimpleForm)this.mFormProto).legalMessage, this, this);
      int i1 = this.mNextViewId;
      this.mNextViewId = (i1 + 1);
      localView1.setId(i1);
      this.mRootView.addView(localView1);
      this.mLegalMessages.add(localView1);
      this.mChildUiNodes.add((UiNode)localView1);
      ViewGroup.MarginLayoutParams localMarginLayoutParams1 = (ViewGroup.MarginLayoutParams)localView1.getLayoutParams();
      MarginLayoutParamsCompat.setMarginStart(localMarginLayoutParams1, j);
      localMarginLayoutParams1.topMargin = k;
    }
    if (paramBundle != null) {
      this.mRevealBelowTriggeredCount = paramBundle.getInt("revealBelowTriggeredCount");
    }
    hideBelow();
    doEnableUi();
    notifyFormEvent(1, Bundle.EMPTY);
    return this.mRootView;
  }
  
  public final void onDestroyView()
  {
    this.mChildUiNodes.clear();
    this.mSubFormUiNodes.clear();
    this.mSubFormChildUiNodes.clear();
    this.mSimpleFormFieldDataList.clear();
    this.mUiFields.clear();
    this.mAddressFragments.clear();
    this.mInfoMessages.clear();
    this.mOtpFields.clear();
    this.mCountrySelectors.clear();
    this.mRegionCodeLists.clear();
    this.mLegalMessages.clear();
    super.onDestroyView();
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("revealBelowTriggeredCount", this.mRevealBelowTriggeredCount);
    int i = 0;
    int j = this.mRegionCodeLists.size();
    while (i < j)
    {
      paramBundle.putIntArray("regionCodes_" + i, (int[])this.mRegionCodeLists.get(i));
      i++;
    }
  }
  
  static final class SimpleFormFieldData<T>
    extends FormFragment.FieldData<T>
  {
    int mFieldType;
    String mFormHeaderId;
    int mIdxInFieldType;
    int mIdxInParent;
    
    public SimpleFormFieldData(int paramInt, T paramT, Object paramObject)
    {
      super(paramT, paramObject);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.simple.SimpleFragment
 * JD-Core Version:    0.7.0.1
 */