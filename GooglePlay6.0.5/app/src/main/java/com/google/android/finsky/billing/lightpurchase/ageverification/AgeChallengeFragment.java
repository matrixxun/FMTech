package com.google.android.finsky.billing.lightpurchase.ageverification;

import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.android.finsky.billing.BillingUtils;
import com.google.android.finsky.fragments.LoggingFragment;
import com.google.android.finsky.protos.ChallengeProto.AgeChallenge;
import com.google.android.finsky.protos.ChallengeProto.FormButton;
import com.google.android.finsky.protos.ChallengeProto.FormCheckbox;
import com.google.android.finsky.protos.ChallengeProto.FormListItem;
import com.google.android.finsky.protos.ChallengeProto.FormListSelector;
import com.google.android.finsky.protos.ChallengeProto.FormRadioButton;
import com.google.android.finsky.protos.ChallengeProto.FormRadioSelector;
import com.google.android.finsky.protos.ChallengeProto.FormTextField;
import com.google.android.finsky.protos.ChallengeProto.InputValidationError;
import com.google.android.finsky.utils.DateUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.layout.PlayActionButton;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class AgeChallengeFragment
  extends LoggingFragment
  implements DatePickerDialog.OnDateSetListener, View.OnClickListener
{
  private int mBackend;
  private EditText mBirthday;
  private Date mBirthdayDate;
  private final RadioGroup.OnCheckedChangeListener mCarrierChangeWatcher = new RadioGroup.OnCheckedChangeListener()
  {
    public final void onCheckedChanged(RadioGroup paramAnonymousRadioGroup, int paramAnonymousInt)
    {
      if ((AgeChallengeFragment.this.mCarrierExtensionButton != null) && (paramAnonymousInt != -1) && (((RadioButton)AgeChallengeFragment.this.mCarrierSelection.findViewById(paramAnonymousInt)).isChecked()))
      {
        AgeChallengeFragment.this.mCarrierExtensionButton.setChecked(false);
        AgeChallengeFragment.this.mCarrierExtensionSpinner.setEnabled(false);
      }
    }
  };
  private RadioButton mCarrierExtensionButton;
  private final CompoundButton.OnCheckedChangeListener mCarrierExtensionChangeWatcher = new CompoundButton.OnCheckedChangeListener()
  {
    public final void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
    {
      if (paramAnonymousBoolean)
      {
        AgeChallengeFragment.this.mCarrierSelection.clearCheck();
        AgeChallengeFragment.this.mCarrierExtensionSpinner.setEnabled(true);
      }
    }
  };
  private Spinner mCarrierExtensionSpinner;
  private RadioGroup mCarrierSelection;
  private ChallengeProto.AgeChallenge mChallenge;
  private final CompoundButton.OnCheckedChangeListener mCheckBoxWatcher = new CompoundButton.OnCheckedChangeListener()
  {
    public final void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
    {
      if (paramAnonymousBoolean) {
        AgeChallengeFragment.this.mCitizenshipError.setError(null);
      }
    }
  };
  private CheckBox mCitizenship;
  private TextView mCitizenshipError;
  private EditText mFullName;
  private RadioGroup mGenderSelection;
  private ViewGroup mMainView;
  private EditText mPhoneNumber;
  private PlayActionButton mSubmitButton;
  
  public static AgeChallengeFragment newInstance(String paramString, int paramInt, ChallengeProto.AgeChallenge paramAgeChallenge)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("authAccount", paramString);
    localBundle.putInt("AgeChallengeFragment.backend", paramInt);
    localBundle.putParcelable("AgeChallengeFragment.challenge", ParcelableProto.forProto(paramAgeChallenge));
    AgeChallengeFragment localAgeChallengeFragment = new AgeChallengeFragment();
    localAgeChallengeFragment.setArguments(localBundle);
    return localAgeChallengeFragment;
  }
  
  protected final int getPlayStoreUiElementType()
  {
    return 1401;
  }
  
  public final void onClick(View paramView)
  {
    if (paramView == this.mBirthday) {
      if (this.mFragmentManager.findFragmentByTag("AgeChallengeFragment.date_picker") == null) {}
    }
    final ArrayList localArrayList;
    do
    {
      do
      {
        return;
        Calendar localCalendar = Calendar.getInstance();
        if (this.mBirthdayDate != null) {
          localCalendar.setTime(this.mBirthdayDate);
        }
        DatePickerDialogFragment localDatePickerDialogFragment = DatePickerDialogFragment.newInstance(localCalendar);
        localDatePickerDialogFragment.setTargetFragment(this, 0);
        localDatePickerDialogFragment.show(this.mFragmentManager, "AgeChallengeFragment.date_picker");
        return;
      } while (paramView != this.mSubmitButton);
      localArrayList = new ArrayList();
      if ((this.mFullName.getVisibility() == 0) && (Utils.isEmptyOrSpaces(this.mFullName.getText()))) {
        localArrayList.add(BillingUtils.createInputValidationError(1, getString(2131362272)));
      }
      if ((this.mBirthday.getVisibility() == 0) && (this.mBirthdayDate == null)) {
        localArrayList.add(BillingUtils.createInputValidationError(2, getString(2131362269)));
      }
      if ((this.mPhoneNumber.getVisibility() == 0) && (Utils.isEmptyOrSpaces(this.mPhoneNumber.getText()))) {
        localArrayList.add(BillingUtils.createInputValidationError(4, getString(2131362274)));
      }
      if ((this.mCitizenship.getVisibility() == 0) && (!this.mCitizenship.isChecked()) && (this.mChallenge.citizenship.required)) {
        localArrayList.add(BillingUtils.createInputValidationError(6, getString(2131362269)));
      }
      this.mFullName.setError(null);
      this.mBirthday.setError(null);
      this.mPhoneNumber.setError(null);
      this.mCitizenshipError.setError(null);
      if (!localArrayList.isEmpty()) {
        new Runnable()
        {
          public final void run()
          {
            Iterator localIterator = localArrayList.iterator();
            while (localIterator.hasNext())
            {
              ChallengeProto.InputValidationError localInputValidationError = (ChallengeProto.InputValidationError)localIterator.next();
              AgeChallengeFragment.access$400(AgeChallengeFragment.this, localInputValidationError);
            }
          }
        }.run();
      }
    } while (!localArrayList.isEmpty());
    logClickEvent(1402);
    UiUtils.hideKeyboard(getActivity(), this.mMainView);
    HashMap localHashMap = new HashMap();
    if (this.mFullName.getVisibility() == 0) {
      localHashMap.put(this.mChallenge.fullName.postParam, this.mFullName.getText().toString());
    }
    if (this.mBirthday.getVisibility() == 0) {
      localHashMap.put(this.mChallenge.birthdate.postParam, DateUtils.formatDate(this.mBirthdayDate, "yyyyMMdd"));
    }
    if (this.mGenderSelection.getVisibility() == 0)
    {
      int m = this.mGenderSelection.indexOfChild(this.mGenderSelection.findViewById(this.mGenderSelection.getCheckedRadioButtonId()));
      localHashMap.put(this.mChallenge.genderSelection.postParam, this.mChallenge.genderSelection.radioButton[m].value);
    }
    if (this.mPhoneNumber.getVisibility() == 0) {
      localHashMap.put(this.mChallenge.phoneNumber.postParam, this.mPhoneNumber.getText().toString());
    }
    String str;
    Listener localListener;
    if (this.mCarrierSelection.getVisibility() == 0)
    {
      int i = this.mCarrierSelection.getCheckedRadioButtonId();
      if (i != -1)
      {
        int k = this.mCarrierSelection.indexOfChild(this.mCarrierSelection.findViewById(i));
        str = this.mChallenge.carrierSelection.radioButton[k].value;
        localHashMap.put(this.mChallenge.carrierSelection.postParam, str);
      }
    }
    else
    {
      if ((this.mCitizenship.getVisibility() == 0) && (this.mCitizenship.isChecked())) {
        localHashMap.put(this.mChallenge.citizenship.postParam, this.mChallenge.citizenship.id);
      }
      if (!(this.mTarget instanceof Listener)) {
        break label711;
      }
      localListener = (Listener)this.mTarget;
    }
    for (;;)
    {
      localListener.onSubmit(this.mChallenge.submitButton.actionUrl, localHashMap);
      return;
      int j = this.mCarrierExtensionSpinner.getSelectedItemPosition();
      str = this.mChallenge.carrierSelectionExtension.item[j].value;
      break;
      label711:
      if ((this.mParentFragment instanceof Listener))
      {
        localListener = (Listener)this.mParentFragment;
      }
      else
      {
        if (!(getActivity() instanceof Listener)) {
          break label755;
        }
        localListener = (Listener)getActivity();
      }
    }
    label755:
    throw new IllegalStateException("No listener registered.");
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mBackend = this.mArguments.getInt("AgeChallengeFragment.backend");
    this.mChallenge = ((ChallengeProto.AgeChallenge)ParcelableProto.getProtoFromBundle(this.mArguments, "AgeChallengeFragment.challenge"));
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mMainView = ((ViewGroup)paramLayoutInflater.inflate(2130968611, paramViewGroup, false));
    TextView localTextView1 = (TextView)this.mMainView.findViewById(2131755173);
    TextView localTextView2;
    if (!TextUtils.isEmpty(this.mChallenge.title))
    {
      localTextView1.setText(this.mChallenge.title);
      ((TextView)this.mMainView.findViewById(2131755219)).setText(Utils.getDisplayAccountName(this.mArguments.getString("authAccount"), getContext()));
      localTextView2 = (TextView)this.mMainView.findViewById(2131755211);
      if (TextUtils.isEmpty(this.mChallenge.descriptionHtml)) {
        break label511;
      }
      localTextView2.setText(Html.fromHtml(this.mChallenge.descriptionHtml));
      localTextView2.setMovementMethod(LinkMovementMethod.getInstance());
      localTextView2.setLinkTextColor(localTextView2.getTextColors());
      label149:
      this.mFullName = ((EditText)this.mMainView.findViewById(2131755221));
      if (this.mChallenge.fullName == null) {
        break label521;
      }
      if (!TextUtils.isEmpty(this.mChallenge.fullName.defaultValue)) {
        this.mFullName.setText(this.mChallenge.fullName.defaultValue);
      }
      if (!TextUtils.isEmpty(this.mChallenge.fullName.hint)) {
        this.mFullName.setHint(this.mChallenge.fullName.hint);
      }
      label242:
      this.mBirthday = ((EditText)this.mMainView.findViewById(2131755222));
      if (this.mChallenge.birthdate == null) {
        break label572;
      }
      if (paramBundle == null) {
        break label533;
      }
      this.mBirthdayDate = ((Date)paramBundle.getSerializable("AgeChallengeFragment.birthday_date"));
      label287:
      if (this.mBirthdayDate != null) {
        this.mBirthday.setText(DateUtils.formatDate(this.mBirthdayDate));
      }
      if (!TextUtils.isEmpty(this.mChallenge.birthdate.hint)) {
        this.mBirthday.setHint(this.mChallenge.birthdate.hint);
      }
      this.mBirthday.setKeyListener(null);
      this.mBirthday.setOnClickListener(this);
    }
    int i;
    for (;;)
    {
      this.mGenderSelection = ((RadioGroup)this.mMainView.findViewById(2131755223));
      if (this.mChallenge.genderSelection == null) {
        break label838;
      }
      LayoutInflater localLayoutInflater2 = LayoutInflater.from(getActivity());
      ChallengeProto.FormRadioButton[] arrayOfFormRadioButton2 = this.mChallenge.genderSelection.radioButton;
      i = 1;
      int i1 = 0;
      while (i1 < arrayOfFormRadioButton2.length)
      {
        ChallengeProto.FormRadioButton localFormRadioButton2 = arrayOfFormRadioButton2[i1];
        RadioButton localRadioButton2 = (RadioButton)localLayoutInflater2.inflate(2130968614, this.mMainView, false);
        localRadioButton2.setText(localFormRadioButton2.label);
        int i2 = i + 1;
        localRadioButton2.setId(i);
        localRadioButton2.setChecked(localFormRadioButton2.selected);
        this.mGenderSelection.addView(localRadioButton2, i1);
        i1++;
        i = i2;
      }
      FinskyLog.wtf("Title is not returned.", new Object[0]);
      break;
      label511:
      localTextView2.setVisibility(8);
      break label149;
      label521:
      this.mFullName.setVisibility(8);
      break label242;
      label533:
      if (TextUtils.isEmpty(this.mChallenge.birthdate.defaultValue)) {
        break label287;
      }
      this.mBirthdayDate = DateUtils.parseDate(this.mChallenge.birthdate.defaultValue, "yyyyMMdd");
      break label287;
      label572:
      this.mBirthday.setVisibility(8);
    }
    if (this.mGenderSelection.getCheckedRadioButtonId() == -1) {
      this.mGenderSelection.check(1);
    }
    this.mPhoneNumber = ((EditText)this.mMainView.findViewById(2131755224));
    if (this.mChallenge.phoneNumber != null)
    {
      if (!TextUtils.isEmpty(this.mChallenge.phoneNumber.defaultValue)) {
        this.mPhoneNumber.setText(this.mChallenge.phoneNumber.defaultValue);
      }
      if (!TextUtils.isEmpty(this.mChallenge.phoneNumber.hint)) {
        this.mPhoneNumber.setHint(this.mChallenge.phoneNumber.hint);
      }
    }
    for (;;)
    {
      this.mCarrierSelection = ((RadioGroup)this.mMainView.findViewById(2131755225));
      if (this.mChallenge.carrierSelection == null) {
        break label1407;
      }
      LayoutInflater localLayoutInflater1 = LayoutInflater.from(getActivity());
      ChallengeProto.FormRadioButton[] arrayOfFormRadioButton1 = this.mChallenge.carrierSelection.radioButton;
      int j = 0;
      int n;
      for (int k = i; j < arrayOfFormRadioButton1.length; k = n)
      {
        ChallengeProto.FormRadioButton localFormRadioButton1 = arrayOfFormRadioButton1[j];
        RadioButton localRadioButton1 = (RadioButton)localLayoutInflater1.inflate(2130968614, this.mMainView, false);
        localRadioButton1.setText(localFormRadioButton1.label);
        n = k + 1;
        localRadioButton1.setId(k);
        localRadioButton1.setChecked(localFormRadioButton1.selected);
        this.mCarrierSelection.addView(localRadioButton1, j);
        j++;
      }
      label838:
      this.mGenderSelection.setVisibility(8);
      i = 1;
      break;
      this.mPhoneNumber.setVisibility(8);
    }
    if (this.mCarrierSelection.getCheckedRadioButtonId() == -1) {
      this.mCarrierSelection.check(i);
    }
    if ((this.mChallenge.carrierSelectionExtension != null) && (!TextUtils.isEmpty(this.mChallenge.carrierSelectionExtension.label)) && (this.mChallenge.carrierSelectionExtension.item != null) && (this.mChallenge.carrierSelectionExtension.item.length > 0) && (this.mChallenge.carrierSelectionExtension.item[0] != null) && (!TextUtils.isEmpty(this.mChallenge.carrierSelectionExtension.item[0].label)))
    {
      View localView = this.mMainView.findViewById(2131755226);
      localView.setVisibility(0);
      this.mCarrierSelection.setOnCheckedChangeListener(this.mCarrierChangeWatcher);
      this.mCarrierExtensionButton = ((RadioButton)localView.findViewById(2131755227));
      this.mCarrierExtensionButton.setText(this.mChallenge.carrierSelectionExtension.label);
      this.mCarrierExtensionButton.setOnCheckedChangeListener(this.mCarrierExtensionChangeWatcher);
      this.mCarrierExtensionSpinner = ((Spinner)localView.findViewById(2131755228));
      this.mCarrierExtensionSpinner.setEnabled(false);
      ArrayAdapter localArrayAdapter = new ArrayAdapter(getContext(), 17367048);
      localArrayAdapter.setDropDownViewResource(17367049);
      for (int m = 0; m < this.mChallenge.carrierSelectionExtension.item.length; m++) {
        localArrayAdapter.add(this.mChallenge.carrierSelectionExtension.item[m].label);
      }
      this.mCarrierExtensionSpinner.setAdapter(localArrayAdapter);
    }
    if (!TextUtils.isEmpty(this.mChallenge.carrierSelectionHelpHtml))
    {
      TextView localTextView4 = (TextView)this.mMainView.findViewById(2131755229);
      localTextView4.setVisibility(0);
      localTextView4.setText(Html.fromHtml(this.mChallenge.carrierSelectionHelpHtml));
      localTextView4.setMovementMethod(LinkMovementMethod.getInstance());
    }
    this.mCitizenship = ((CheckBox)this.mMainView.findViewById(2131755230));
    label1294:
    TextView localTextView3;
    if (this.mChallenge.citizenship != null)
    {
      this.mCitizenship.setText(this.mChallenge.citizenship.description);
      this.mCitizenship.setChecked(this.mChallenge.citizenship.checked);
      this.mCitizenship.setOnCheckedChangeListener(this.mCheckBoxWatcher);
      this.mCitizenshipError = ((TextView)this.mMainView.findViewById(2131755231));
      localTextView3 = (TextView)this.mMainView.findViewById(2131755232);
      if (TextUtils.isEmpty(this.mChallenge.submitFooterHtml)) {
        break label1440;
      }
      localTextView3.setText(Html.fromHtml(this.mChallenge.submitFooterHtml));
      label1337:
      this.mSubmitButton = ((PlayActionButton)this.mMainView.findViewById(2131755631));
      if ((this.mChallenge.submitButton == null) || (TextUtils.isEmpty(this.mChallenge.submitButton.label))) {
        break label1450;
      }
      this.mSubmitButton.configure(this.mBackend, this.mChallenge.submitButton.label, this);
    }
    for (;;)
    {
      return this.mMainView;
      label1407:
      this.mCarrierSelection.setVisibility(8);
      break;
      this.mCitizenship.setVisibility(8);
      this.mCitizenshipError.setVisibility(8);
      break label1294;
      label1440:
      localTextView3.setVisibility(8);
      break label1337;
      label1450:
      FinskyLog.wtf("Submit button is not returned", new Object[0]);
    }
  }
  
  public final void onDateSet(DatePicker paramDatePicker, int paramInt1, int paramInt2, int paramInt3)
  {
    this.mBirthdayDate = new GregorianCalendar(paramInt1, paramInt2, paramInt3).getTime();
    this.mBirthday.setText(DateUtils.formatDate(this.mBirthdayDate));
    this.mBirthday.setError(null);
  }
  
  public final void onResume()
  {
    super.onResume();
    UiUtils.sendAccessibilityEventWithText(this.mMainView.getContext(), this.mChallenge.title, this.mMainView);
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putSerializable("AgeChallengeFragment.birthday_date", this.mBirthdayDate);
  }
  
  public static abstract interface Listener
  {
    public abstract void onSubmit(String paramString, Map<String, String> paramMap);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.ageverification.AgeChallengeFragment
 * JD-Core Version:    0.7.0.1
 */