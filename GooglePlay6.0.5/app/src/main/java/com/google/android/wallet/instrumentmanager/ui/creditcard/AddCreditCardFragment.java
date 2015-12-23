package com.google.android.wallet.instrumentmanager.ui.creditcard;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.nfc.NfcAdapter;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.google.android.gms.ocr.CreditCardOcrIntentBuilder;
import com.google.android.gms.ocr.CreditCardOcrResult;
import com.google.android.wallet.analytics.CreditCardEntryAction;
import com.google.android.wallet.analytics.SimpleUiNode;
import com.google.android.wallet.analytics.UiNode;
import com.google.android.wallet.analytics.WalletUiElement;
import com.google.android.wallet.common.address.RegionCode;
import com.google.android.wallet.common.analytics.util.AnalyticsUtil;
import com.google.android.wallet.common.util.PaymentUtils;
import com.google.android.wallet.instrumentmanager.R.attr;
import com.google.android.wallet.instrumentmanager.R.dimen;
import com.google.android.wallet.instrumentmanager.R.id;
import com.google.android.wallet.instrumentmanager.R.layout;
import com.google.android.wallet.instrumentmanager.R.string;
import com.google.android.wallet.instrumentmanager.common.util.OcrUtils;
import com.google.android.wallet.instrumentmanager.ui.common.DropDownItem;
import com.google.android.wallet.instrumentmanager.ui.common.ImInfoMessageTextView;
import com.google.android.wallet.nfc.CreditCardNfcResult;
import com.google.android.wallet.nfc.NfcIntentForwarder;
import com.google.android.wallet.nfc.NfcReadResultListener;
import com.google.android.wallet.nfc.NfcService;
import com.google.android.wallet.nfc.NfcServiceImpl;
import com.google.android.wallet.nfc.NullNfcService;
import com.google.android.wallet.ui.address.AddressEntryFragment;
import com.google.android.wallet.ui.address.AddressEntryFragment.OnAddressFieldsShownListener;
import com.google.android.wallet.ui.address.AddressEntryFragment.RecipientNameListener;
import com.google.android.wallet.ui.address.DynamicAddressFieldsLayout.OnHeightOffsetChangedListener;
import com.google.android.wallet.ui.common.BaseWalletUiComponentFragment;
import com.google.android.wallet.ui.common.ClickSpan.OnClickListener;
import com.google.android.wallet.ui.common.ExpDateEditText;
import com.google.android.wallet.ui.common.FormEditText;
import com.google.android.wallet.ui.common.FormFragment;
import com.google.android.wallet.ui.common.FormFragment.FieldData;
import com.google.android.wallet.ui.common.InfoMessageTextView;
import com.google.android.wallet.ui.common.OnAutoAdvanceListener;
import com.google.android.wallet.ui.common.RegionCodeSelectorSpinner.OnRegionCodeSelectedListener;
import com.google.android.wallet.ui.common.WalletDialogFragment;
import com.google.android.wallet.ui.common.WalletDialogFragment.Builder;
import com.google.android.wallet.ui.common.WalletUiUtils;
import com.google.android.wallet.ui.common.WebViewDialogFragment;
import com.google.commerce.payments.orchestration.proto.common.DateOuterClass.Date;
import com.google.commerce.payments.orchestration.proto.ui.common.FormFieldReferenceOuterClass.FormFieldReference;
import com.google.commerce.payments.orchestration.proto.ui.common.UiErrorOuterClass.FormFieldMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CreditCard.CardType;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CreditCard.CreditCardForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.legal.LegalMessageOuterClass.LegalMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.ImageWithCaptionOuterClass.ImageWithCaption;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.InfoMessageOuterClass.InfoMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiField;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiField.DateField;
import java.util.ArrayList;
import java.util.List;

public final class AddCreditCardFragment
  extends FormFragment<CreditCard.CreditCardForm>
  implements View.OnClickListener, View.OnFocusChangeListener, AdapterView.OnItemClickListener, UiNode, CreditCardNumberEditText.OnCreditCardTypeChangedListener, NfcIntentForwarder, NfcReadResultListener, AddressEntryFragment.OnAddressFieldsShownListener, AddressEntryFragment.RecipientNameListener, DynamicAddressFieldsLayout.OnHeightOffsetChangedListener, ClickSpan.OnClickListener, RegionCodeSelectorSpinner.OnRegionCodeSelectedListener
{
  public AddressEntryFragment mAddressEntryFragment;
  View mAddressEntryFragmentHolder;
  private boolean mAddressFieldsShown = true;
  View[] mAnimatedChildren;
  private int mCameraInputPreference = 0;
  private CreditCardEntryAction mCreditCardEntryAction;
  CreditCardImagesView mCreditCardImagesView;
  TextView mCreditCardNumberConcealedText;
  public CreditCardNumberEditText mCreditCardNumberText;
  private TextWatcher mCreditCardNumberTextWatcher;
  ImageView mCvcHintImage;
  public FormEditText mCvcText;
  View mExpCvcLayout;
  public ExpDateEditText mExpDateText;
  private TextWatcher mExpDateTextWatcher;
  View mExpandCreditCardIcon;
  final ArrayList<FormFragment.FieldData> mFieldData = new ArrayList();
  boolean mIsNfcIconSetToInProgress;
  private Intent mLaunchOcrIntent;
  public LegalMessageOuterClass.LegalMessage mLegalMessage;
  ImInfoMessageTextView mLegalMessageText;
  private boolean mNfcEnabledWhenLastPaused = true;
  ImageView mNfcIcon;
  private Drawable mNfcIconDrawable;
  private int mNfcInputPreference = 0;
  NfcService mNfcService;
  View mOcrIcon;
  public OnAddCreditCardFragmentStateChangedListener mOnStateChangedListener;
  RelativeLayout mRoot;
  int mSelectedRegionCode = 0;
  private final WalletUiElement mUiElement = new WalletUiElement(1650);
  int mViewState = 0;
  
  private void closeDialogFragmentIfOpen(String paramString)
  {
    Fragment localFragment = this.mFragmentManager.findFragmentByTag(paramString);
    if (localFragment != null) {
      this.mFragmentManager.beginTransaction().remove(localFragment).commit();
    }
  }
  
  private void displayNfcErrorDialog(String paramString)
  {
    if (this.mFragmentManager.findFragmentByTag("tagNfcErrorDialog") != null) {
      return;
    }
    WalletDialogFragment.Builder localBuilder = new WalletDialogFragment.Builder();
    localBuilder.mTitle = getString(R.string.wallet_im_nfc_error_title);
    localBuilder.mMessage = paramString;
    localBuilder.mThemeResourceId = this.mThemeResourceId;
    localBuilder.mPositiveButtonText = getString(17039370);
    localBuilder.build().show(this.mFragmentManager, "tagNfcErrorDialog");
  }
  
  private static int getStateAfterEnteringCardNumberCompleted()
  {
    if (Build.VERSION.SDK_INT < 14) {
      return 3;
    }
    return 2;
  }
  
  private boolean isNfcEnabled()
  {
    return this.mNfcInputPreference != 0;
  }
  
  private boolean isOcrEnabled()
  {
    return this.mCameraInputPreference != 0;
  }
  
  private void launchNfc()
  {
    if (this.mFragmentManager.findFragmentByTag("tagNfcInfoDialog") != null) {
      return;
    }
    showNfcInfoDialog();
    AnalyticsUtil.createAndSendClickEvent(this, -1, 1655);
    AnalyticsUtil.createAndSendImpressionEvent(this, -1);
  }
  
  private void launchOcr()
  {
    startActivityForResultFromTopLevelFragment(this.mLaunchOcrIntent, 500);
    enableUi(false);
    AnalyticsUtil.createAndSendClickEvent(this, -1, 1652);
    AnalyticsUtil.createAndSendImpressionEvent(new SimpleUiNode(1653, null), -1);
  }
  
  public static AddCreditCardFragment newInstance(CreditCard.CreditCardForm paramCreditCardForm, int paramInt)
  {
    AddCreditCardFragment localAddCreditCardFragment = new AddCreditCardFragment();
    localAddCreditCardFragment.setArguments(createArgsForFormFragment$179723a0(paramInt, paramCreditCardForm));
    return localAddCreditCardFragment;
  }
  
  private void populateCardInfoAndSendCcEntryAction(String paramString1, int paramInt1, int paramInt2, String paramString2, long paramLong, int paramInt3)
  {
    int i = 1;
    int j = -1;
    int k;
    int m;
    label35:
    int n;
    label47:
    CreditCardEntryAction localCreditCardEntryAction2;
    if (!TextUtils.isEmpty(paramString1))
    {
      k = i;
      if ((paramInt1 <= 0) || (paramInt1 >= 13) || (paramInt2 < 0)) {
        break label312;
      }
      m = i;
      if (TextUtils.isEmpty(paramString2)) {
        break label318;
      }
      n = i;
      if (k != 0)
      {
        if (this.mCreditCardNumberConcealedText.getVisibility() == 0) {
          transitionToState(5, false);
        }
        this.mCreditCardEntryAction.panEntryType = paramInt3;
        if (this.mViewState == i) {
          j = getStateAfterEnteringCardNumberCompleted();
        }
        if (m != 0) {
          j = 3;
        }
        transitionToState(j, false);
        this.mCreditCardNumberText.requestFocus();
        this.mCreditCardNumberText.removeTextChangedListener(this.mCreditCardNumberTextWatcher);
        this.mCreditCardNumberText.setText(paramString1);
        this.mCreditCardNumberText.addTextChangedListener(this.mCreditCardNumberTextWatcher);
        if (m != 0)
        {
          this.mCreditCardEntryAction.expDateEntryType = paramInt3;
          this.mExpDateText.removeTextChangedListener(this.mExpDateTextWatcher);
          this.mExpDateText.requestFocus();
          this.mExpDateText.setExpDate(Integer.toString(paramInt1), Integer.toString(paramInt2 % 100));
          this.mExpDateText.addTextChangedListener(this.mExpDateTextWatcher);
        }
        if (n != 0)
        {
          this.mCreditCardEntryAction.nameEntryType = paramInt3;
          this.mAddressEntryFragment.mRecipientNameListener = null;
          this.mAddressEntryFragment.setRecipientName(paramString2);
          this.mAddressEntryFragment.mRecipientNameListener = this;
        }
      }
      if (paramInt3 != 2) {
        break label338;
      }
      localCreditCardEntryAction2 = this.mCreditCardEntryAction;
      if (this.mCreditCardEntryAction.numOcrAttempts >= 0) {
        break label324;
      }
    }
    for (;;)
    {
      localCreditCardEntryAction2.numOcrAttempts = i;
      this.mCreditCardEntryAction.panRecognizedByOcr = k;
      this.mCreditCardEntryAction.expDateRecognizedByOcr = m;
      this.mCreditCardEntryAction.nameRecognizedByOcr = n;
      sendCreditCardEntryActionBackgroundEvent();
      return;
      k = 0;
      break;
      label312:
      m = 0;
      break label35;
      label318:
      n = 0;
      break label47;
      label324:
      i = 1 + this.mCreditCardEntryAction.numOcrAttempts;
    }
    label338:
    if (paramInt3 == 3)
    {
      CreditCardEntryAction localCreditCardEntryAction1 = this.mCreditCardEntryAction;
      if (this.mCreditCardEntryAction.numNfcAttempts < 0) {}
      for (;;)
      {
        localCreditCardEntryAction1.numNfcAttempts = i;
        this.mCreditCardEntryAction.panRecognizedByNfc = k;
        this.mCreditCardEntryAction.expDateRecognizedByNfc = m;
        this.mCreditCardEntryAction.nameRecognizedByNfc = n;
        if (paramLong < 0L) {
          break;
        }
        this.mCreditCardEntryAction.nfcElapsedTimeMillis = paramLong;
        break;
        i = 1 + this.mCreditCardEntryAction.numNfcAttempts;
      }
    }
    throw new IllegalArgumentException("Unknown entry type: " + paramInt3);
  }
  
  private void sendCreditCardEntryActionBackgroundEvent()
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("FormEventListener.EXTRA_BACKGROUND_EVENT_TYPE", 770);
    localBundle.putParcelable("FormEventListener.EXTRA_BACKGROUND_EVENT_DATA", this.mCreditCardEntryAction);
    notifyFormEvent(7, localBundle);
  }
  
  @TargetApi(14)
  private void setAnimationDelay(long paramLong)
  {
    if (Build.VERSION.SDK_INT >= 14)
    {
      int i = 0;
      int j = this.mAnimatedChildren.length;
      while (i < j)
      {
        if (this.mAnimatedChildren[i] != null) {
          this.mAnimatedChildren[i].animate().setStartDelay(paramLong);
        }
        i++;
      }
    }
  }
  
  private void setCvcTextNextFocus()
  {
    if ((this.mViewState >= 4) && (this.mAddressFieldsShown)) {}
    for (int i = R.id.address_field_recipient; Build.VERSION.SDK_INT >= 14; i = -1)
    {
      this.mCvcText.setNextFocusForwardId(i);
      return;
    }
    this.mCvcText.setNextFocusDownId(i);
  }
  
  private void showNfcInfoDialog()
  {
    NfcInfoDialogFragment localNfcInfoDialogFragment = NfcInfoDialogFragment.newInstance(this.mThemeResourceId, this.mNfcService.isAdapterEnabled());
    localNfcInfoDialogFragment.setTargetFragment(this, 0);
    localNfcInfoDialogFragment.show(this.mFragmentManager, "tagNfcInfoDialog");
  }
  
  @TargetApi(14)
  private void switchToExpandedState(boolean paramBoolean)
  {
    if (Build.VERSION.SDK_INT < 14) {
      throw new IllegalStateException("Can not switch ui to expanded state for api level: " + Build.VERSION.SDK_INT);
    }
    RelativeLayout.LayoutParams localLayoutParams1 = (RelativeLayout.LayoutParams)this.mCreditCardNumberText.getLayoutParams();
    localLayoutParams1.leftMargin = getResources().getDimensionPixelSize(R.dimen.wallet_im_credit_card_number_collapsed_start_margin);
    localLayoutParams1.addRule(WalletUiUtils.sanitizeRelativeLayoutVerb(3), R.id.add_credit_card_title);
    this.mCreditCardNumberText.setLayoutParams(localLayoutParams1);
    if (this.mOcrIcon != null)
    {
      RelativeLayout.LayoutParams localLayoutParams4 = (RelativeLayout.LayoutParams)this.mOcrIcon.getLayoutParams();
      localLayoutParams4.addRule(WalletUiUtils.sanitizeRelativeLayoutVerb(3), R.id.add_credit_card_title);
      this.mOcrIcon.setLayoutParams(localLayoutParams4);
    }
    if (this.mNfcIcon != null)
    {
      RelativeLayout.LayoutParams localLayoutParams3 = (RelativeLayout.LayoutParams)this.mNfcIcon.getLayoutParams();
      localLayoutParams3.addRule(WalletUiUtils.sanitizeRelativeLayoutVerb(3), R.id.add_credit_card_title);
      this.mNfcIcon.setLayoutParams(localLayoutParams3);
    }
    int i;
    if (paramBoolean)
    {
      setAnimationDelay(150L);
      WalletUiUtils.animateViewDisappearingToGone(this.mCreditCardNumberConcealedText, 0, 0);
      WalletUiUtils.animateViewDisappearingToGone(this.mExpandCreditCardIcon, 0, 0);
      WalletUiUtils.animateViewAppearing(this.mCreditCardNumberText, 0, 0);
      if (this.mOcrIcon != null) {
        WalletUiUtils.animateViewAppearing(this.mOcrIcon, 0, 0);
      }
      if (this.mNfcIcon != null) {
        WalletUiUtils.animateViewAppearing(this.mNfcIcon, 0, 0);
      }
      RelativeLayout.LayoutParams localLayoutParams2 = (RelativeLayout.LayoutParams)this.mExpCvcLayout.getLayoutParams();
      WalletUiUtils.removeRule(localLayoutParams2, WalletUiUtils.sanitizeRelativeLayoutVerb(10), ViewCompat.getLayoutDirection(this.mExpCvcLayout));
      localLayoutParams2.addRule(WalletUiUtils.sanitizeRelativeLayoutVerb(3), this.mCreditCardNumberText.getId());
      i = -WalletUiUtils.getViewHeightWithMargins(this.mCreditCardNumberConcealedText);
      if (!paramBoolean) {
        break label502;
      }
      WalletUiUtils.animateViewAppearing(this.mExpCvcLayout, i, 0);
    }
    for (;;)
    {
      ((RelativeLayout.LayoutParams)this.mAddressEntryFragmentHolder.getLayoutParams()).addRule(WalletUiUtils.sanitizeRelativeLayoutVerb(3), this.mExpCvcLayout.getId());
      if (paramBoolean)
      {
        this.mAddressEntryFragmentHolder.setTranslationY(i);
        this.mAddressEntryFragmentHolder.animate().translationY(0.0F).start();
        this.mLegalMessageText.setTranslationY(i);
        this.mLegalMessageText.animate().translationY(0.0F).start();
        if (this.mOnStateChangedListener != null) {
          this.mOnStateChangedListener.animateViewsBelow$4868d6cf(i);
        }
        setAnimationDelay(0L);
      }
      return;
      this.mCreditCardNumberConcealedText.setVisibility(8);
      this.mExpandCreditCardIcon.setVisibility(8);
      this.mCreditCardNumberText.setVisibility(0);
      this.mCreditCardNumberText.setTranslationY(0.0F);
      this.mCreditCardNumberText.setAlpha(1.0F);
      if (this.mOcrIcon != null)
      {
        this.mOcrIcon.setVisibility(0);
        this.mOcrIcon.setTranslationY(0.0F);
        this.mOcrIcon.setAlpha(1.0F);
      }
      if (this.mNfcIcon == null) {
        break;
      }
      this.mNfcIcon.setVisibility(0);
      this.mNfcIcon.setTranslationY(0.0F);
      this.mNfcIcon.setAlpha(1.0F);
      break;
      label502:
      this.mExpCvcLayout.setVisibility(0);
      this.mExpCvcLayout.setTranslationY(0.0F);
      this.mExpCvcLayout.setAlpha(1.0F);
    }
  }
  
  @TargetApi(14)
  private void switchToShowingAddress(boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      this.mAddressEntryFragmentHolder.setVisibility(0);
      this.mLegalMessageText.setVisibility(0);
      if (this.mOnStateChangedListener != null) {
        this.mOnStateChangedListener.showViewsBelow$71dafd8f(true, false, 0, 0L);
      }
      if (Build.VERSION.SDK_INT >= 14)
      {
        this.mExpandCreditCardIcon.setVisibility(0);
        this.mCreditCardNumberText.setVisibility(8);
        if (this.mOcrIcon != null) {
          this.mOcrIcon.setVisibility(8);
        }
        if (this.mNfcIcon != null) {
          this.mNfcIcon.setVisibility(8);
        }
        this.mCreditCardNumberConcealedText.setVisibility(0);
        this.mCreditCardImagesView.switchToOneCardMode();
        this.mExpCvcLayout.setVisibility(8);
      }
    }
    do
    {
      return;
      if (Build.VERSION.SDK_INT >= 14) {
        break;
      }
      WalletUiUtils.animateViewAppearing(this.mAddressEntryFragmentHolder, 0, 0);
      WalletUiUtils.animateViewAppearing(this.mLegalMessageText, 0, 0);
    } while (this.mOnStateChangedListener == null);
    this.mOnStateChangedListener.showViewsBelow$71dafd8f(true, true, 0, 0L);
    return;
    setAnimationDelay(150L);
    int i = WalletUiUtils.getViewHeightWithMargins(this.mCreditCardImagesView);
    int j = WalletUiUtils.getViewHeightWithMargins(this.mCreditCardNumberText);
    int k = WalletUiUtils.getViewHeightWithMargins(this.mExpCvcLayout);
    this.mCreditCardImagesView.switchToOneCardMode();
    WalletUiUtils.animateViewDisappearingToGone(this.mCreditCardNumberText, 0, -i);
    if ((this.mOcrIcon != null) && (this.mOcrIcon.getVisibility() == 0)) {
      WalletUiUtils.animateViewDisappearingToGone(this.mOcrIcon, 0, -i);
    }
    if ((this.mNfcIcon != null) && (this.mNfcIcon.getVisibility() == 0)) {
      WalletUiUtils.animateViewDisappearingToGone(this.mNfcIcon, 0, -i);
    }
    WalletUiUtils.animateViewAppearing(this.mCreditCardNumberConcealedText, i, 0);
    WalletUiUtils.animateViewAppearing(this.mExpandCreditCardIcon, i, 0);
    RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)this.mExpCvcLayout.getLayoutParams();
    WalletUiUtils.removeRule(localLayoutParams, WalletUiUtils.sanitizeRelativeLayoutVerb(3), ViewCompat.getLayoutDirection(this.mExpCvcLayout));
    localLayoutParams.addRule(WalletUiUtils.sanitizeRelativeLayoutVerb(10));
    WalletUiUtils.animateViewDisappearingToGone(this.mExpCvcLayout, i + j, 0);
    ((RelativeLayout.LayoutParams)this.mAddressEntryFragmentHolder.getLayoutParams()).addRule(WalletUiUtils.sanitizeRelativeLayoutVerb(3), this.mCreditCardImagesView.getId());
    int m = j + k;
    View localView = this.mAddressEntryFragmentHolder.findViewById(R.id.address_field_recipient);
    boolean bool = localView instanceof FormEditText;
    Runnable local4 = null;
    if (bool)
    {
      final FormEditText localFormEditText = (FormEditText)localView;
      final int n = localFormEditText.getThreshold();
      localFormEditText.setThreshold(2147483647);
      local4 = new Runnable()
      {
        public final void run()
        {
          localFormEditText.setThreshold(n);
          localFormEditText.triggerAutocompleteSuggestionsIfPossible();
        }
      };
    }
    WalletUiUtils.animateViewAppearing$249729a5(this.mAddressEntryFragmentHolder, m, local4);
    WalletUiUtils.animateViewAppearing(this.mLegalMessageText, m, 0);
    if (this.mOnStateChangedListener != null) {
      this.mOnStateChangedListener.showViewsBelow$71dafd8f(true, true, m, 150L);
    }
    setAnimationDelay(0L);
  }
  
  @TargetApi(14)
  private void transitionToState(int paramInt, boolean paramBoolean)
  {
    if (paramInt <= this.mViewState) {
      return;
    }
    if (this.mViewState == 0) {
      switch (paramInt)
      {
      }
    }
    for (;;)
    {
      this.mViewState = paramInt;
      setCvcTextNextFocus();
      notifyFormEvent(1, Bundle.EMPTY);
      return;
      if (this.mOcrIcon != null) {
        this.mOcrIcon.setVisibility(0);
      }
      if (this.mNfcIcon != null) {
        this.mNfcIcon.setVisibility(0);
      }
      this.mExpCvcLayout.setVisibility(4);
      this.mCreditCardNumberText.addOnAutoAdvanceListener(new OnAutoAdvanceListener()
      {
        public final void onAutoAdvance(View paramAnonymousView)
        {
          if (AddCreditCardFragment.this.mViewState == 1) {
            AddCreditCardFragment.access$000$41cd1b5b(AddCreditCardFragment.this, AddCreditCardFragment.access$300());
          }
        }
      });
      if (this.mOnStateChangedListener != null)
      {
        this.mOnStateChangedListener.showViewsBelow$71dafd8f(false, false, 0, 0L);
        continue;
        if (this.mOcrIcon != null) {
          this.mOcrIcon.setVisibility(0);
        }
        if (this.mNfcIcon != null) {
          this.mNfcIcon.setVisibility(0);
        }
        this.mCvcText.setOnFocusChangeListener(this);
        this.mCvcHintImage.setVisibility(8);
        if (this.mOnStateChangedListener != null)
        {
          this.mOnStateChangedListener.showViewsBelow$71dafd8f(false, false, 0, 0L);
          continue;
          if (this.mOcrIcon != null) {
            this.mOcrIcon.setVisibility(0);
          }
          if (this.mNfcIcon != null) {
            this.mNfcIcon.setVisibility(0);
          }
          if (this.mOnStateChangedListener != null)
          {
            this.mOnStateChangedListener.showViewsBelow$71dafd8f(false, false, 0, 0L);
            continue;
            if ((Build.VERSION.SDK_INT < 14) && (this.mOcrIcon != null)) {
              this.mOcrIcon.setVisibility(0);
            }
            if ((Build.VERSION.SDK_INT < 14) && (this.mNfcIcon != null)) {
              this.mNfcIcon.setVisibility(0);
            }
            switchToShowingAddress(false);
            continue;
            this.mAddressEntryFragmentHolder.setVisibility(0);
            this.mLegalMessageText.setVisibility(0);
            switchToExpandedState(false);
            if (this.mOnStateChangedListener != null)
            {
              this.mOnStateChangedListener.showViewsBelow$71dafd8f(true, false, 0, 0L);
              continue;
              if ((this.mViewState == 1) && ((paramInt == 3) || (paramInt == 2)))
              {
                if (paramInt == 2)
                {
                  this.mCvcHintImage.setVisibility(8);
                  this.mCvcText.setOnFocusChangeListener(this);
                }
                if (paramBoolean) {
                  WalletUiUtils.animateViewAppearing(this.mExpCvcLayout, -this.mCreditCardNumberText.getHeight(), 0);
                } else {
                  this.mExpCvcLayout.setVisibility(0);
                }
              }
              else if (this.mViewState == 4)
              {
                if (paramInt == 5) {
                  switchToExpandedState(paramBoolean);
                }
              }
              else if (paramInt == 4)
              {
                this.mCreditCardNumberConcealedText.setText(this.mCreditCardNumberText.getConcealedCardNumber());
                switchToShowingAddress(true);
              }
              else if ((this.mViewState == 2) && (paramInt == 3) && (this.mCvcHintImage.getVisibility() != 0))
              {
                WalletUiUtils.animateViewAppearing(this.mCvcHintImage, 0, 0);
              }
            }
          }
        }
      }
    }
  }
  
  public final boolean applyFormFieldMessage(UiErrorOuterClass.FormFieldMessage paramFormFieldMessage)
  {
    if (paramFormFieldMessage.formFieldReference.formId.equals(((CreditCard.CreditCardForm)this.mFormProto).id))
    {
      switch (paramFormFieldMessage.formFieldReference.fieldId)
      {
      default: 
        throw new IllegalArgumentException("Unknown FormFieldMessage fieldId: " + paramFormFieldMessage.formFieldReference.fieldId);
      case 1: 
        this.mCreditCardNumberText.setError(paramFormFieldMessage.message);
        if (!this.mCreditCardEntryAction.panValidationErrorOccurred)
        {
          this.mCreditCardEntryAction.panValidationErrorOccurred = true;
          sendCreditCardEntryActionBackgroundEvent();
        }
        break;
      }
      for (;;)
      {
        if (Build.VERSION.SDK_INT >= 14) {
          transitionToState(5, false);
        }
        return true;
        this.mCvcText.setError(paramFormFieldMessage.message);
        continue;
        this.mExpDateText.setError(paramFormFieldMessage.message);
        if (!this.mCreditCardEntryAction.expDateValidationErrorOccurred)
        {
          this.mCreditCardEntryAction.expDateValidationErrorOccurred = true;
          sendCreditCardEntryActionBackgroundEvent();
          continue;
          this.mExpDateText.setError(paramFormFieldMessage.message);
          if (!this.mCreditCardEntryAction.expDateValidationErrorOccurred)
          {
            this.mCreditCardEntryAction.expDateValidationErrorOccurred = true;
            sendCreditCardEntryActionBackgroundEvent();
          }
        }
      }
    }
    return this.mAddressEntryFragment.applyFormFieldMessage(paramFormFieldMessage);
  }
  
  public final void doEnableUi()
  {
    if (this.mCreditCardNumberText != null)
    {
      boolean bool = this.mUiEnabled;
      this.mCreditCardNumberText.setEnabled(bool);
      this.mExpDateText.setEnabled(bool);
      this.mCvcText.setEnabled(bool);
      this.mExpandCreditCardIcon.setEnabled(bool);
      this.mAddressEntryFragment.enableUi(bool);
      this.mCvcHintImage.setEnabled(bool);
      this.mLegalMessageText.setEnabled(bool);
      if (this.mOcrIcon != null) {
        this.mOcrIcon.setEnabled(bool);
      }
      if (this.mNfcIcon != null) {
        this.mNfcIcon.setEnabled(bool);
      }
    }
  }
  
  public final void forwardNfcIntent(Intent paramIntent)
  {
    if ((!isNfcEnabled()) || (this.mNfcService.isReadInProgress())) {
      return;
    }
    NfcInfoDialogFragment localNfcInfoDialogFragment = (NfcInfoDialogFragment)this.mFragmentManager.findFragmentByTag("tagNfcInfoDialog");
    enableUi(false);
    if (localNfcInfoDialogFragment != null) {
      localNfcInfoDialogFragment.showSpinner();
    }
    for (;;)
    {
      this.mNfcService.readNfcIntentInBackground(paramIntent);
      return;
      closeDialogFragmentIfOpen("tagNfcErrorDialog");
      if ((this.mNfcIcon != null) && (this.mViewState != 4))
      {
        this.mIsNfcIconSetToInProgress = true;
        ContextThemeWrapper localContextThemeWrapper = this.mThemedContext;
        int[] arrayOfInt = new int[2];
        arrayOfInt[0] = R.attr.imNfcInProgressDrawable;
        arrayOfInt[1] = R.attr.imNfcDrawable;
        TypedArray localTypedArray = localContextThemeWrapper.obtainStyledAttributes(arrayOfInt);
        Drawable localDrawable = localTypedArray.getDrawable(0);
        this.mNfcIconDrawable = localTypedArray.getDrawable(1);
        localTypedArray.recycle();
        this.mNfcIcon.setImageDrawable(localDrawable);
      }
    }
  }
  
  public final String getButtonBarExpandButtonText()
  {
    return this.mLegalMessageText.getExpandLabel();
  }
  
  public final List<UiNode> getChildren()
  {
    ArrayList localArrayList = new ArrayList();
    if (isOcrEnabled()) {
      localArrayList.add(new SimpleUiNode(1652, this));
    }
    if (isNfcEnabled()) {
      localArrayList.add(new SimpleUiNode(1655, this));
    }
    if ((NfcInfoDialogFragment)this.mFragmentManager.findFragmentByTag("tagNfcInfoDialog") != null) {
      localArrayList.add(new SimpleUiNode(1656, this));
    }
    if (this.mLegalMessage != null) {
      localArrayList.add(this.mLegalMessageText);
    }
    return localArrayList;
  }
  
  public final List<FormFragment.FieldData> getFieldsForValidationInOrder()
  {
    return this.mFieldData;
  }
  
  public final WalletUiElement getUiElement()
  {
    return this.mUiElement;
  }
  
  public final boolean handleErrorMessageDismissed(String paramString, int paramInt)
  {
    return this.mAddressEntryFragment.handleErrorMessageDismissed(paramString, paramInt);
  }
  
  public final boolean isReadyToSubmit()
  {
    if (this.mAddressEntryFragment != null) {}
    switch (this.mViewState)
    {
    default: 
      return false;
    }
    return true;
  }
  
  public final void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt1 != 500)
    {
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
      return;
    }
    enableUi(true);
    CreditCardOcrResult localCreditCardOcrResult = CreditCardOcrResult.fromIntent(paramIntent);
    if (paramIntent != null) {
      this.mCreditCardEntryAction.expDateOcrEnabled = paramIntent.getBooleanExtra("com.google.android.gms.ocr.EXP_DATE_RECOGNITION_ENABLED", false);
    }
    this.mCreditCardEntryAction.ocrExitReason = OcrUtils.ocrResultCodeToExitReason(paramInt2);
    if (localCreditCardOcrResult != null)
    {
      populateCardInfoAndSendCcEntryAction(localCreditCardOcrResult.zzbrS, localCreditCardOcrResult.zzbrT, localCreditCardOcrResult.zzbrU, "", -1L, 2);
      return;
    }
    populateCardInfoAndSendCcEntryAction("", 0, -1, "", -1L, 2);
  }
  
  public final void onAddressFieldsShown(boolean paramBoolean)
  {
    this.mAddressFieldsShown = paramBoolean;
    setCvcTextNextFocus();
  }
  
  public final void onButtonBarExpandButtonClicked()
  {
    this.mLegalMessageText.expand(true);
  }
  
  public final void onClick(View paramView)
  {
    if (paramView.getId() == R.id.expand_icon) {
      transitionToState(5, true);
    }
    do
    {
      return;
      if (paramView.getId() == R.id.ocr_icon)
      {
        launchOcr();
        return;
      }
      if (paramView.getId() == R.id.nfc_icon)
      {
        launchNfc();
        return;
      }
    } while ((paramView.getId() != R.id.cvc_hint) || (this.mFragmentManager.findFragmentByTag("tagCvcInfoDialog") != null));
    CvcInfoDialogFragment.newInstance(this.mThemeResourceId).show(this.mFragmentManager, "tagCvcInfoDialog");
  }
  
  public final void onClick(View paramView, String paramString)
  {
    if ((paramView != this.mLegalMessageText) || (this.mFragmentManager.findFragmentByTag("tagTosWebViewDialog") != null)) {
      return;
    }
    WebViewDialogFragment.newInstance(paramString, this.mThemeResourceId).show(this.mFragmentManager, "tagTosWebViewDialog");
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      this.mSelectedRegionCode = paramBundle.getInt("selectedRegionCode", 0);
      if (this.mSelectedRegionCode != 0) {
        this.mLegalMessage = PaymentUtils.findLegalMessageByCountry(((CreditCard.CreditCardForm)this.mFormProto).legalMessages, RegionCode.toCountryCode(this.mSelectedRegionCode));
      }
    }
    int[] arrayOfInt1 = ((CreditCard.CreditCardForm)this.mFormProto).cameraInputPreference;
    int i = arrayOfInt1.length;
    int j = 0;
    int m;
    if (j < i)
    {
      int i1 = arrayOfInt1[j];
      if ((i1 == 1) || (i1 == 6))
      {
        ContextThemeWrapper localContextThemeWrapper = this.mThemedContext;
        int[] arrayOfInt3 = new int[1];
        arrayOfInt3[0] = R.attr.ccOcrTheme;
        TypedArray localTypedArray = localContextThemeWrapper.obtainStyledAttributes(arrayOfInt3);
        int i2 = localTypedArray.getInt(0, 0);
        localTypedArray.recycle();
        this.mLaunchOcrIntent = new CreditCardOcrIntentBuilder(getActivity()).setTheme(i2).build();
        if (this.mLaunchOcrIntent == null) {
          i1 = 0;
        }
        this.mCameraInputPreference = i1;
      }
    }
    else
    {
      if ((Build.VERSION.SDK_INT >= 10) && (NfcAdapter.getDefaultAdapter(getActivity()) != null))
      {
        int[] arrayOfInt2 = ((CreditCard.CreditCardForm)this.mFormProto).nfcInputPreference;
        int k = arrayOfInt2.length;
        m = 0;
        label212:
        if (m < k)
        {
          int n = arrayOfInt2[m];
          if ((n != 1) && (n != 2)) {
            break label374;
          }
          this.mNfcInputPreference = n;
        }
      }
      if (!isNfcEnabled()) {
        break label380;
      }
    }
    label374:
    label380:
    for (Object localObject = new NfcServiceImpl(getActivity(), this);; localObject = new NullNfcService())
    {
      this.mNfcService = ((NfcService)localObject);
      if (paramBundle != null) {
        this.mCreditCardEntryAction = ((CreditCardEntryAction)paramBundle.getParcelable("creditCardEntryAction"));
      }
      if (this.mCreditCardEntryAction == null)
      {
        this.mCreditCardEntryAction = new CreditCardEntryAction();
        this.mCreditCardEntryAction.panOcrEnabled = isOcrEnabled();
        this.mCreditCardEntryAction.nfcFeatureEnabled = isNfcEnabled();
        this.mCreditCardEntryAction.nfcAdapterEnabled = this.mNfcService.isAdapterEnabled();
        this.mCreditCardEntryAction.cameraInputPreference = this.mCameraInputPreference;
        this.mCreditCardEntryAction.nfcInputPreference = this.mNfcInputPreference;
      }
      return;
      j++;
      break;
      m++;
      break label212;
    }
  }
  
  protected final View onCreateThemedView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.fragment_add_credit_card, null, false);
    this.mRoot = ((RelativeLayout)localView.findViewById(R.id.credit_card_root));
    if (!TextUtils.isEmpty(((CreditCard.CreditCardForm)this.mFormProto).title))
    {
      TextView localTextView = (TextView)this.mRoot.findViewById(R.id.add_credit_card_title);
      localTextView.setText(((CreditCard.CreditCardForm)this.mFormProto).title);
      localTextView.setVisibility(0);
    }
    int i = ((CreditCard.CreditCardForm)this.mFormProto).allowedCardType.length;
    ImageWithCaptionOuterClass.ImageWithCaption[] arrayOfImageWithCaption = new ImageWithCaptionOuterClass.ImageWithCaption[i];
    for (int j = 0; j < i; j++) {
      arrayOfImageWithCaption[j] = ((CreditCard.CreditCardForm)this.mFormProto).allowedCardType[j].icon;
    }
    this.mCreditCardImagesView = ((CreditCardImagesView)localView.findViewById(R.id.credit_card_images));
    this.mCreditCardImagesView.setCardIcons(arrayOfImageWithCaption, ImageWithCaptionOuterClass.ImageWithCaption.emptyArray(), false, 1);
    this.mCreditCardNumberConcealedText = ((TextView)localView.findViewById(R.id.card_number_concealed));
    this.mCreditCardNumberText = ((CreditCardNumberEditText)localView.findViewById(R.id.card_number));
    this.mExpCvcLayout = localView.findViewById(R.id.exp_date_and_cvc);
    this.mExpDateText = ((ExpDateEditText)localView.findViewById(R.id.exp_date));
    this.mCvcText = ((FormEditText)localView.findViewById(R.id.cvc));
    FormEditText localFormEditText = this.mCvcText;
    InputFilter[] arrayOfInputFilter = new InputFilter[1];
    arrayOfInputFilter[0] = new InputFilter.LengthFilter(4);
    localFormEditText.setFilters(arrayOfInputFilter);
    this.mFieldData.add(new FormFragment.FieldData(0, this.mCreditCardNumberText, null));
    this.mFieldData.add(new FormFragment.FieldData(0, this.mExpDateText, null));
    this.mCvcHintImage = ((ImageView)localView.findViewById(R.id.cvc_hint));
    this.mCvcHintImage.setOnClickListener(this);
    if (this.mNfcInputPreference == 1)
    {
      this.mNfcIcon = ((ImageView)localView.findViewById(R.id.nfc_icon));
      this.mNfcIcon.setOnClickListener(this);
      this.mNfcIcon.setVisibility(0);
      if (this.mCameraInputPreference != 1) {
        break label1116;
      }
      this.mOcrIcon = localView.findViewById(R.id.ocr_icon);
      this.mOcrIcon.setOnClickListener(this);
      this.mOcrIcon.setVisibility(0);
    }
    for (;;)
    {
      this.mExpandCreditCardIcon = localView.findViewById(R.id.expand_icon);
      this.mExpandCreditCardIcon.setOnClickListener(this);
      this.mLegalMessageText = ((ImInfoMessageTextView)localView.findViewById(R.id.add_card_legal_message_text));
      this.mLegalMessageText.setParentUiNode(this);
      this.mLegalMessageText.setUrlClickListener(this);
      CvcValidator localCvcValidator = new CvcValidator(this.mCvcText, this.mCreditCardNumberText);
      this.mCvcText.addValidator(localCvcValidator);
      this.mFieldData.add(new FormFragment.FieldData(0, this.mCvcText, null));
      UiFieldOuterClass.UiField localUiField = new UiFieldOuterClass.UiField();
      localUiField.isOptional = false;
      localUiField.label = getString(R.string.wallet_uic_exp_date);
      localUiField.dateField = new UiFieldOuterClass.UiField.DateField();
      localUiField.dateField.type = 2;
      localUiField.dateField.minDate = new DateOuterClass.Date();
      localUiField.dateField.minDate.month = ((CreditCard.CreditCardForm)this.mFormProto).minExpirationMonth;
      localUiField.dateField.minDate.year = ((CreditCard.CreditCardForm)this.mFormProto).minExpirationYear;
      localUiField.dateField.maxDate = new DateOuterClass.Date();
      localUiField.dateField.maxDate.month = ((CreditCard.CreditCardForm)this.mFormProto).maxExpirationMonth;
      localUiField.dateField.maxDate.year = ((CreditCard.CreditCardForm)this.mFormProto).maxExpirationYear;
      WalletUiUtils.applyUiFieldSpecificationToFormEditText(localUiField, this.mExpDateText);
      this.mCvcText.enableAutoAdvance(localCvcValidator, this.mCvcText, true);
      this.mCreditCardNumberText.enableAutoAdvance(this.mCreditCardNumberText, this.mCreditCardNumberText, false);
      this.mCvcText.addOnAutoAdvanceListener(new OnAutoAdvanceListener()
      {
        public final void onAutoAdvance(View paramAnonymousView)
        {
          AddCreditCardFragment.access$000$41cd1b5b(AddCreditCardFragment.this, 4);
        }
      });
      this.mCreditCardNumberText.setOnCreditCardTypeChangedListener(this);
      this.mCreditCardNumberText.setAllowedCardTypes(((CreditCard.CreditCardForm)this.mFormProto).allowedCardType);
      this.mCreditCardNumberText.setInvalidBins(((CreditCard.CreditCardForm)this.mFormProto).invalidBin);
      this.mAddressEntryFragmentHolder = localView.findViewById(R.id.address_fragment_holder);
      this.mAddressEntryFragment = ((AddressEntryFragment)getChildFragmentManager().findFragmentById(R.id.address_fragment_holder));
      if (this.mAddressEntryFragment == null)
      {
        this.mAddressEntryFragment = AddressEntryFragment.newInstance(((CreditCard.CreditCardForm)this.mFormProto).billingAddress, this.mThemeResourceId);
        getChildFragmentManager().beginTransaction().add(R.id.address_fragment_holder, this.mAddressEntryFragment).commit();
      }
      this.mFieldData.add(new FormFragment.FieldData(0, this.mAddressEntryFragment));
      this.mAddressEntryFragment.mOnRegionCodeSelectedListener = this;
      this.mAddressEntryFragment.mOnHeightOffsetChangedListener = this;
      this.mAddressEntryFragment.mOnAddressFieldsShownListener = this;
      this.mAddressEntryFragment.mRecipientNameListener = this;
      doEnableUi();
      View[] arrayOfView = new View[10];
      arrayOfView[0] = this.mRoot;
      arrayOfView[1] = this.mCreditCardImagesView;
      arrayOfView[2] = this.mCreditCardNumberText;
      arrayOfView[3] = this.mCreditCardNumberConcealedText;
      arrayOfView[4] = this.mExpandCreditCardIcon;
      arrayOfView[5] = this.mOcrIcon;
      arrayOfView[6] = this.mNfcIcon;
      arrayOfView[7] = this.mExpCvcLayout;
      arrayOfView[8] = this.mAddressEntryFragmentHolder;
      arrayOfView[9] = this.mLegalMessageText;
      this.mAnimatedChildren = arrayOfView;
      int k = 1;
      if (paramBundle != null) {
        k = paramBundle.getInt("viewState", 3);
      }
      transitionToState(k, false);
      this.mCreditCardNumberTextWatcher = new TextWatcher()
      {
        public final void afterTextChanged(Editable paramAnonymousEditable) {}
        
        public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
        
        public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
        {
          if (AddCreditCardFragment.this.mCreditCardEntryAction.panEntryType != 1)
          {
            AddCreditCardFragment.this.mCreditCardEntryAction.panEntryType = 1;
            AddCreditCardFragment.this.sendCreditCardEntryActionBackgroundEvent();
          }
        }
      };
      this.mExpDateTextWatcher = new TextWatcher()
      {
        public final void afterTextChanged(Editable paramAnonymousEditable) {}
        
        public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
        
        public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
        {
          if (AddCreditCardFragment.this.mCreditCardEntryAction.expDateEntryType != 1)
          {
            AddCreditCardFragment.this.mCreditCardEntryAction.expDateEntryType = 1;
            AddCreditCardFragment.this.sendCreditCardEntryActionBackgroundEvent();
          }
        }
      };
      return localView;
      if (this.mNfcInputPreference != 2) {
        break;
      }
      CreditCardNumberEditText localCreditCardNumberEditText1 = this.mCreditCardNumberText;
      if (localCreditCardNumberEditText1.mNfcDropDownItem == null)
      {
        localCreditCardNumberEditText1.mNfcDropDownItem = new DropDownItem(R.attr.imNfcDrawable, localCreditCardNumberEditText1.getResources().getString(R.string.wallet_im_nfc_popup_title), 2);
        localCreditCardNumberEditText1.addDropDownButton(localCreditCardNumberEditText1.mNfcDropDownItem);
      }
      this.mCreditCardNumberText.setOnItemClickListener(this);
      break;
      label1116:
      if (this.mCameraInputPreference == 6)
      {
        CreditCardNumberEditText localCreditCardNumberEditText2 = this.mCreditCardNumberText;
        if (localCreditCardNumberEditText2.mOcrDropDownItem == null)
        {
          localCreditCardNumberEditText2.mOcrDropDownItem = new DropDownItem(R.attr.imCameraDrawable, localCreditCardNumberEditText2.getResources().getString(R.string.wallet_im_ocr_drop_down_button), 1);
          localCreditCardNumberEditText2.addDropDownButton(localCreditCardNumberEditText2.mOcrDropDownItem);
        }
        this.mCreditCardNumberText.setOnItemClickListener(this);
      }
    }
  }
  
  public final void onCreditCardTypeChanged(CreditCard.CardType paramCardType)
  {
    CreditCardImagesView localCreditCardImagesView = this.mCreditCardImagesView;
    ImageWithCaptionOuterClass.ImageWithCaption localImageWithCaption;
    if (paramCardType != null)
    {
      localImageWithCaption = paramCardType.icon;
      localCreditCardImagesView.setCardIcon(localImageWithCaption);
      if (paramCardType == null) {
        break label88;
      }
    }
    label88:
    for (int i = paramCardType.cvcLength;; i = 4)
    {
      FormEditText localFormEditText = this.mCvcText;
      InputFilter[] arrayOfInputFilter = new InputFilter[1];
      arrayOfInputFilter[0] = new InputFilter.LengthFilter(i);
      localFormEditText.setFilters(arrayOfInputFilter);
      if (!TextUtils.isEmpty(this.mCvcText.getText())) {
        this.mCvcText.validate();
      }
      return;
      localImageWithCaption = null;
      break;
    }
  }
  
  @TargetApi(14)
  public final void onFocusChange(View paramView, boolean paramBoolean)
  {
    if (paramBoolean) {
      transitionToState(3, true);
    }
  }
  
  @TargetApi(11)
  public final void onHeightOffsetChanged(float paramFloat)
  {
    this.mLegalMessageText.setTranslationY(paramFloat + this.mAddressEntryFragmentHolder.getTranslationY());
  }
  
  public final void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    int i = ((DropDownItem)this.mCreditCardNumberText.getAdapter().getItem(paramInt)).eventType;
    if (i == 1)
    {
      launchOcr();
      return;
    }
    if (i == 2)
    {
      launchNfc();
      return;
    }
    throw new IllegalArgumentException("Unknown DropDownItem event type: " + i);
  }
  
  public final void onNfcReadResult(int paramInt, CreditCardNfcResult paramCreditCardNfcResult, long paramLong)
  {
    closeDialogFragmentIfOpen("tagNfcInfoDialog");
    enableUi(true);
    if (this.mIsNfcIconSetToInProgress)
    {
      this.mIsNfcIconSetToInProgress = false;
      this.mNfcIcon.setImageDrawable(this.mNfcIconDrawable);
    }
    switch (paramInt)
    {
    default: 
      throw new IllegalStateException("Unknown NFC result code: " + paramInt);
    case 1: 
      this.mCreditCardEntryAction.nfcExitReason = 1;
      this.mCreditCardEntryAction.nfcErrorReason = 0;
    }
    while (paramCreditCardNfcResult != null)
    {
      populateCardInfoAndSendCcEntryAction(paramCreditCardNfcResult.cardNumber, paramCreditCardNfcResult.expMonth, paramCreditCardNfcResult.expYear, paramCreditCardNfcResult.cardHolderName, paramLong, 3);
      return;
      this.mCreditCardEntryAction.nfcExitReason = 3;
      this.mCreditCardEntryAction.nfcErrorReason = 0;
      continue;
      displayNfcErrorDialog(getString(R.string.wallet_im_nfc_unsupported_error_message));
      this.mCreditCardEntryAction.nfcExitReason = 4;
      this.mCreditCardEntryAction.nfcErrorReason = 1;
      continue;
      displayNfcErrorDialog(getString(R.string.wallet_im_nfc_timeout_error_message));
      this.mCreditCardEntryAction.nfcExitReason = 2;
      this.mCreditCardEntryAction.nfcErrorReason = 3;
      continue;
      displayNfcErrorDialog(getString(R.string.wallet_im_nfc_transceive_error_message));
      this.mCreditCardEntryAction.nfcExitReason = 4;
      this.mCreditCardEntryAction.nfcErrorReason = 2;
    }
    populateCardInfoAndSendCcEntryAction("", 0, -1, "", paramLong, 3);
  }
  
  public final void onPause()
  {
    super.onPause();
    this.mNfcService.pause();
    this.mNfcEnabledWhenLastPaused = this.mNfcService.isAdapterEnabled();
  }
  
  public final void onRecipientNameChanged$552c4e01()
  {
    if (this.mCreditCardEntryAction.nameEntryType != 1)
    {
      this.mCreditCardEntryAction.nameEntryType = 1;
      sendCreditCardEntryActionBackgroundEvent();
    }
  }
  
  public final void onRecipientNameErrorOccurred()
  {
    if (!this.mCreditCardEntryAction.nameValidationErrorOccurred)
    {
      this.mCreditCardEntryAction.nameValidationErrorOccurred = true;
      sendCreditCardEntryActionBackgroundEvent();
    }
  }
  
  public final void onRegionCodeSelected(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    ImInfoMessageTextView localImInfoMessageTextView;
    if (this.mSelectedRegionCode != paramInt1)
    {
      this.mSelectedRegionCode = paramInt1;
      this.mLegalMessage = PaymentUtils.findLegalMessageByCountry(((CreditCard.CreditCardForm)this.mFormProto).legalMessages, RegionCode.toCountryCode(paramInt1));
      localImInfoMessageTextView = this.mLegalMessageText;
      if (this.mLegalMessage != null) {
        break label67;
      }
    }
    label67:
    for (InfoMessageOuterClass.InfoMessage localInfoMessage = null;; localInfoMessage = this.mLegalMessage.messageText)
    {
      localImInfoMessageTextView.setInfoMessage(localInfoMessage);
      notifyFormEvent(6, Bundle.EMPTY);
      return;
    }
  }
  
  public final void onResume()
  {
    super.onResume();
    this.mNfcService.resume();
    boolean bool = this.mNfcService.isAdapterEnabled();
    if ((!this.mNfcService.isReadInProgress()) && (!this.mNfcEnabledWhenLastPaused) && (bool))
    {
      closeDialogFragmentIfOpen("tagNfcInfoDialog");
      showNfcInfoDialog();
    }
    this.mCreditCardNumberText.showDropDownIfNecessary();
    if (this.mCreditCardEntryAction.nfcAdapterEnabled != bool)
    {
      this.mCreditCardEntryAction.nfcAdapterEnabled = bool;
      sendCreditCardEntryActionBackgroundEvent();
    }
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("viewState", this.mViewState);
    paramBundle.putInt("selectedRegionCode", this.mSelectedRegionCode);
    paramBundle.putParcelable("creditCardEntryAction", this.mCreditCardEntryAction);
  }
  
  public final void onViewStateRestored(Bundle paramBundle)
  {
    super.onViewStateRestored(paramBundle);
    notifyFormEvent(6, Bundle.EMPTY);
    this.mCreditCardNumberText.addTextChangedListener(this.mCreditCardNumberTextWatcher);
    this.mExpDateText.addTextChangedListener(this.mExpDateTextWatcher);
  }
  
  public final boolean shouldShowButtonBarExpandButton()
  {
    return (!this.mLegalMessageText.mInlineExpandLabel) && (!this.mLegalMessageText.mExpanded);
  }
  
  public final boolean validate(int[] paramArrayOfInt)
  {
    boolean bool1 = super.validate(paramArrayOfInt);
    boolean bool2 = this.mCreditCardEntryAction.panValidationErrorOccurred;
    int i = 0;
    if (!bool2)
    {
      boolean bool3 = TextUtils.isEmpty(this.mCreditCardNumberText.getError());
      i = 0;
      if (!bool3)
      {
        this.mCreditCardEntryAction.panValidationErrorOccurred = true;
        i = 1;
      }
    }
    if ((!this.mCreditCardEntryAction.expDateValidationErrorOccurred) && (!TextUtils.isEmpty(this.mExpDateText.getError())))
    {
      this.mCreditCardEntryAction.expDateValidationErrorOccurred = true;
      i = 1;
    }
    if (i != 0) {
      sendCreditCardEntryActionBackgroundEvent();
    }
    if ((Build.VERSION.SDK_INT >= 14) && (!bool1)) {
      transitionToState(5, false);
    }
    return bool1;
  }
  
  public static abstract interface OnAddCreditCardFragmentStateChangedListener
  {
    public abstract void animateViewsBelow$4868d6cf(int paramInt);
    
    public abstract void showViewsBelow$71dafd8f(boolean paramBoolean1, boolean paramBoolean2, int paramInt, long paramLong);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.creditcard.AddCreditCardFragment
 * JD-Core Version:    0.7.0.1
 */