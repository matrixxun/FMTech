package com.google.android.wallet.ui.common;

import android.content.ContentValues;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.google.android.wallet.analytics.AnalyticsClickListener;
import com.google.android.wallet.analytics.SimpleUiNode;
import com.google.android.wallet.analytics.UiNode;
import com.google.android.wallet.analytics.WalletUiElement;
import com.google.android.wallet.common.util.ParcelableProto;
import com.google.android.wallet.common.util.PermissionDelegate;
import com.google.android.wallet.common.util.SmsReceiver;
import com.google.android.wallet.common.util.SmsReceiver.OnSmsReceivedListener;
import com.google.android.wallet.common.util.SmsUtils;
import com.google.android.wallet.dependencygraph.DependencyGraphManager;
import com.google.android.wallet.dependencygraph.TriggerListener;
import com.google.android.wallet.uicomponents.R.id;
import com.google.android.wallet.uicomponents.R.layout;
import com.google.commerce.payments.orchestration.proto.ui.common.components.ButtonContainerOuterClass.Button;
import com.google.commerce.payments.orchestration.proto.ui.common.components.OtpFieldOuterClass.OtpField;
import com.google.commerce.payments.orchestration.proto.ui.common.components.OtpFieldOuterClass.OtpFieldValue;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.DependencyGraphOuterClass.TriggerValueReference;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OtpFieldFragment
  extends BaseWalletUiComponentFragment
  implements View.OnClickListener, UiNode, SmsReceiver.OnSmsReceivedListener, AutoAdvancer, FieldValidatable, OnAutoAdvanceListener
{
  public AnalyticsClickListener mAnalyticsClickListener;
  private DependencyGraphManager mDependencyGraphManager;
  private OtpFieldOuterClass.OtpField mFieldProto;
  long mLastMatchingSmsReceivedMs = -1L;
  long mLastSmsScanForOtpsMs = System.currentTimeMillis();
  private final List<OnAutoAdvanceListener> mOnAutoAdvanceListenerList = new ArrayList();
  CountdownButton mOtpButton;
  public FormEditText mOtpField;
  private UiNode mParentUiNode;
  private Pattern mSmsOtpPattern;
  private Pattern mSmsPhoneNumberPattern;
  private SmsReceiver mSmsReceiver;
  private TriggerListener mTriggerListener;
  private final WalletUiElement mUiElement = new WalletUiElement(1635);
  
  public static Bundle createArgsForOtpFieldFragment(OtpFieldOuterClass.OtpField paramOtpField, int paramInt)
  {
    Bundle localBundle = createArgs(paramInt);
    localBundle.putParcelable("fieldProto", ParcelableProto.forProto(paramOtpField));
    return localBundle;
  }
  
  public static OtpFieldFragment newInstance(OtpFieldOuterClass.OtpField paramOtpField, int paramInt)
  {
    OtpFieldFragment localOtpFieldFragment = new OtpFieldFragment();
    localOtpFieldFragment.setArguments(createArgsForOtpFieldFragment(paramOtpField, paramInt));
    return localOtpFieldFragment;
  }
  
  private void sendAnalyticsAutofillEvent(int paramInt)
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("FormEventListener.EXTRA_BACKGROUND_EVENT_TYPE", 773);
    localBundle.putInt("FormEventListener.EXTRA_BACKGROUND_EVENT_RESULT_CODE", paramInt);
    if (this.mParentFragment != null) {}
    for (FormEventListener localFormEventListener = (FormEventListener)this.mParentFragment;; localFormEventListener = (FormEventListener)getActivity())
    {
      localFormEventListener.notifyFormEvent(7, localBundle);
      return;
    }
  }
  
  private boolean updateOtpFieldBasedOnSms(ContentValues paramContentValues)
  {
    String str1 = paramContentValues.getAsString("address");
    String str2 = paramContentValues.getAsString("body");
    long l = paramContentValues.getAsLong("date").longValue();
    if (this.mSmsPhoneNumberPattern != null) {}
    for (int i = 1; (i != 0) && (!TextUtils.isEmpty(str1)) && (!this.mSmsPhoneNumberPattern.matcher(str1).matches()); i = 0)
    {
      sendAnalyticsAutofillEvent(21);
      return false;
    }
    if (this.mLastMatchingSmsReceivedMs > l)
    {
      Locale localLocale = Locale.US;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Long.valueOf(l);
      arrayOfObject[1] = Long.valueOf(this.mLastMatchingSmsReceivedMs);
      Log.d("OtpFieldFragment", String.format(localLocale, "SMS received with time (%d) prior to last matching SMS OTP (%d). Ignoring.", arrayOfObject));
      return false;
    }
    if (!TextUtils.isEmpty(str2))
    {
      Matcher localMatcher = this.mSmsOtpPattern.matcher(str2);
      if (localMatcher.find())
      {
        String str3 = localMatcher.group(1);
        if (!TextUtils.isEmpty(str3))
        {
          this.mLastMatchingSmsReceivedMs = System.currentTimeMillis();
          this.mOtpField.requestFocus();
          this.mOtpField.setValue(str3, true);
          sendAnalyticsAutofillEvent(0);
          return true;
        }
      }
      if (i != 0)
      {
        sendAnalyticsAutofillEvent(20);
        return false;
      }
      sendAnalyticsAutofillEvent(22);
      return false;
    }
    Log.d("OtpFieldFragment", "SMS received without a message body");
    return false;
  }
  
  public final void addOnAutoAdvanceListener(OnAutoAdvanceListener paramOnAutoAdvanceListener)
  {
    this.mOnAutoAdvanceListenerList.add(paramOnAutoAdvanceListener);
  }
  
  public final void enableUi(boolean paramBoolean)
  {
    if (this.mOtpField == null) {
      return;
    }
    this.mOtpField.setEnabled(paramBoolean);
    this.mOtpButton.setEnabled(paramBoolean);
  }
  
  public List<UiNode> getChildren()
  {
    return Collections.singletonList(new SimpleUiNode(1634, this));
  }
  
  public CharSequence getError()
  {
    return this.mOtpField.getError();
  }
  
  public final OtpFieldOuterClass.OtpFieldValue getFieldValue(Bundle paramBundle)
  {
    OtpFieldOuterClass.OtpFieldValue localOtpFieldValue = new OtpFieldOuterClass.OtpFieldValue();
    localOtpFieldValue.otp = WalletUiUtils.createUiFieldValue(this.mOtpField, this.mFieldProto.otp);
    CountdownButton localCountdownButton = this.mOtpButton;
    int j;
    if (paramBundle.containsKey("FormEventListener.EXTRA_TRIGGER_VALUE_REFERENCES"))
    {
      ArrayList localArrayList = ParcelableProto.getProtoArrayListFromBundle(paramBundle, "FormEventListener.EXTRA_TRIGGER_VALUE_REFERENCES");
      int i = localArrayList.size();
      j = 0;
      if (j < i)
      {
        DependencyGraphOuterClass.TriggerValueReference localTriggerValueReference = (DependencyGraphOuterClass.TriggerValueReference)localArrayList.get(j);
        if (localTriggerValueReference.componentId == localCountdownButton.mButtonSpec.uiReference) {
          if (localTriggerValueReference.triggerType != 2) {
            throw new IllegalArgumentException("Unsupported trigger type: " + localTriggerValueReference.triggerType);
          }
        }
      }
    }
    for (boolean bool = true;; bool = false)
    {
      localOtpFieldValue.buttonPressed = bool;
      return localOtpFieldValue;
      j++;
      break;
    }
  }
  
  public UiNode getParentUiNode()
  {
    if (this.mParentUiNode != null) {
      return this.mParentUiNode;
    }
    if (this.mParentFragment != null) {
      return (UiNode)this.mParentFragment;
    }
    return (UiNode)getActivity();
  }
  
  public WalletUiElement getUiElement()
  {
    return this.mUiElement;
  }
  
  public final boolean isValid()
  {
    return this.mOtpField.isValid();
  }
  
  public final void onAutoAdvance(View paramView)
  {
    int i = 0;
    int j = this.mOnAutoAdvanceListenerList.size();
    while (i < j)
    {
      ((OnAutoAdvanceListener)this.mOnAutoAdvanceListenerList.get(i)).onAutoAdvance(paramView);
      i++;
    }
  }
  
  public void onClick(View paramView)
  {
    if (this.mAnalyticsClickListener != null) {
      this.mAnalyticsClickListener.onAnalyticsClickEvent(this, 1634);
    }
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mFieldProto = ((OtpFieldOuterClass.OtpField)ParcelableProto.getProtoFromBundle(this.mArguments, "fieldProto"));
    if ((PermissionDelegate.selfHasPermission(getActivity(), "android.permission.READ_SMS")) && (!TextUtils.isEmpty(this.mFieldProto.otpFromSmsRetrievalRegex)))
    {
      this.mSmsOtpPattern = Pattern.compile(this.mFieldProto.otpFromSmsRetrievalRegex);
      if (this.mSmsOtpPattern.matcher("").groupCount() != 1) {
        throw new IllegalArgumentException("OTP Regex: " + this.mSmsOtpPattern.pattern() + " should only contain a single group for matching");
      }
    }
    if (!TextUtils.isEmpty(this.mFieldProto.otpSenderNumberRegex)) {
      this.mSmsPhoneNumberPattern = Pattern.compile(this.mFieldProto.otpSenderNumberRegex);
    }
    if (paramBundle != null)
    {
      this.mLastMatchingSmsReceivedMs = paramBundle.getLong("lastMatchingSmsReceivedMs");
      this.mLastSmsScanForOtpsMs = paramBundle.getLong("lastSmsScanForOtpsMs");
    }
  }
  
  protected final View onCreateThemedView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.fragment_otp_field, null, false);
    this.mOtpField = ((FormEditText)localView.findViewById(R.id.otp_field));
    WalletUiUtils.applyUiFieldSpecificationToFormEditText(this.mFieldProto.otp, this.mOtpField);
    this.mOtpField.setMaxLines(2147483647);
    this.mOtpField.setHorizontallyScrolling(false);
    this.mOtpField.addOnAutoAdvanceListener(this);
    WalletUiUtils.addComponentToDependencyGraph(this.mOtpField, this.mFieldProto.otp.uiReference, this.mDependencyGraphManager, this.mTriggerListener);
    this.mOtpButton = ((CountdownButton)localView.findViewById(R.id.otp_button));
    this.mOtpButton.setUiSpecification(this.mFieldProto.button);
    WalletUiUtils.addComponentToDependencyGraph(this.mOtpButton, this.mFieldProto.button.uiReference, this.mDependencyGraphManager, this.mTriggerListener);
    this.mOtpButton.setOnClickListener(this);
    return localView;
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putLong("lastMatchingSmsReceivedMs", this.mLastMatchingSmsReceivedMs);
    paramBundle.putLong("lastSmsScanForOtpsMs", this.mLastSmsScanForOtpsMs);
  }
  
  public final void onSmsReceived(SmsMessage[] paramArrayOfSmsMessage)
  {
    SmsMessage localSmsMessage = paramArrayOfSmsMessage[0];
    ContentValues localContentValues = new ContentValues(3);
    localContentValues.put("address", localSmsMessage.getDisplayOriginatingAddress());
    localContentValues.put("date", Long.valueOf(localSmsMessage.getTimestampMillis()));
    localContentValues.put("body", SmsUtils.getSmsMessageBody(paramArrayOfSmsMessage));
    updateOtpFieldBasedOnSms(localContentValues);
  }
  
  public final void onStart()
  {
    super.onStart();
    if (this.mSmsOtpPattern != null)
    {
      if (this.mSmsReceiver == null) {
        this.mSmsReceiver = new SmsReceiver();
      }
      this.mSmsReceiver.mListener = this;
      SmsReceiver localSmsReceiver = this.mSmsReceiver;
      FragmentActivity localFragmentActivity = getActivity();
      IntentFilter localIntentFilter = new IntentFilter();
      localIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
      localIntentFilter.addAction("android.provider.Telephony.SMS_REJECTED");
      localFragmentActivity.registerReceiver(localSmsReceiver, localIntentFilter);
      long l = Math.max(this.mLastMatchingSmsReceivedMs, this.mLastSmsScanForOtpsMs);
      ArrayList localArrayList = SmsUtils.getSmsMessages(getActivity(), l, this.mSmsPhoneNumberPattern);
      this.mLastSmsScanForOtpsMs = System.currentTimeMillis();
      int i = 0;
      int j = localArrayList.size();
      while ((i < j) && (!updateOtpFieldBasedOnSms((ContentValues)localArrayList.get(i)))) {
        i++;
      }
    }
  }
  
  public final void onStop()
  {
    super.onStop();
    if (this.mSmsReceiver != null)
    {
      SmsReceiver localSmsReceiver = this.mSmsReceiver;
      getActivity().unregisterReceiver(localSmsReceiver);
    }
  }
  
  public final void registerDependencyGraphComponents(DependencyGraphManager paramDependencyGraphManager, TriggerListener paramTriggerListener)
  {
    this.mDependencyGraphManager = paramDependencyGraphManager;
    this.mTriggerListener = paramTriggerListener;
  }
  
  public final void removeOnAutoAdvanceListener(OnAutoAdvanceListener paramOnAutoAdvanceListener)
  {
    this.mOnAutoAdvanceListenerList.remove(paramOnAutoAdvanceListener);
  }
  
  public void setError(CharSequence paramCharSequence)
  {
    this.mOtpField.setError(paramCharSequence);
  }
  
  public void setParentUiNode(UiNode paramUiNode)
  {
    this.mParentUiNode = paramUiNode;
  }
  
  public final boolean validate()
  {
    return this.mOtpField.validate();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.common.OtpFieldFragment
 * JD-Core Version:    0.7.0.1
 */