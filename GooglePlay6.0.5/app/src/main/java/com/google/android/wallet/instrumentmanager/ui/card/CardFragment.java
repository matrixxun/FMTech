package com.google.android.wallet.instrumentmanager.ui.card;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.nfc.NfcAdapter;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewPropertyAnimator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
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
import com.google.android.wallet.common.analytics.util.AnalyticsUtil;
import com.google.android.wallet.instrumentmanager.R.attr;
import com.google.android.wallet.instrumentmanager.R.dimen;
import com.google.android.wallet.instrumentmanager.R.id;
import com.google.android.wallet.instrumentmanager.R.layout;
import com.google.android.wallet.instrumentmanager.R.string;
import com.google.android.wallet.instrumentmanager.common.util.OcrUtils;
import com.google.android.wallet.instrumentmanager.ui.common.DropDownItem;
import com.google.android.wallet.instrumentmanager.ui.common.ImOtpFieldFragment;
import com.google.android.wallet.instrumentmanager.ui.creditcard.CreditCardImagesView;
import com.google.android.wallet.instrumentmanager.ui.creditcard.NfcInfoDialogFragment;
import com.google.android.wallet.nfc.CreditCardNfcResult;
import com.google.android.wallet.nfc.NfcIntentForwarder;
import com.google.android.wallet.nfc.NfcReadResultListener;
import com.google.android.wallet.nfc.NfcService;
import com.google.android.wallet.nfc.NfcServiceImpl;
import com.google.android.wallet.nfc.NullNfcService;
import com.google.android.wallet.ui.address.AddressEntryFragment;
import com.google.android.wallet.ui.address.AddressEntryFragment.RecipientNameListener;
import com.google.android.wallet.ui.common.AutoAdvancer;
import com.google.android.wallet.ui.common.BaseWalletUiComponentFragment;
import com.google.android.wallet.ui.common.ClickSpan.OnClickListener;
import com.google.android.wallet.ui.common.ExpDateEditText;
import com.google.android.wallet.ui.common.FormFragment;
import com.google.android.wallet.ui.common.FormFragment.FieldData;
import com.google.android.wallet.ui.common.FormHeaderView;
import com.google.android.wallet.ui.common.OnAutoAdvanceListener;
import com.google.android.wallet.ui.common.OtpFieldFragment;
import com.google.android.wallet.ui.common.TooltipDialogFragment;
import com.google.android.wallet.ui.common.TooltipUiFieldView.OnTooltipIconClickListener;
import com.google.android.wallet.ui.common.UiFieldBuilder;
import com.google.android.wallet.ui.common.WalletDialogFragment;
import com.google.android.wallet.ui.common.WalletDialogFragment.Builder;
import com.google.android.wallet.ui.common.WalletUiUtils;
import com.google.android.wallet.ui.common.WebViewDialogFragment;
import com.google.commerce.payments.orchestration.proto.ui.common.FormFieldReferenceOuterClass.FormFieldReference;
import com.google.commerce.payments.orchestration.proto.ui.common.UiErrorOuterClass.FormFieldMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.components.AddressFormOuterClass.AddressForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.FormHeaderOuterClass.FormHeader;
import com.google.commerce.payments.orchestration.proto.ui.common.components.OtpFieldOuterClass.OtpField;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CardFormOuterClass.CardField;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CardFormOuterClass.CardForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CardFormOuterClass.CardSubform;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.CardFormOuterClass.PanCategory;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.ImageWithCaptionOuterClass.ImageWithCaption;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.TooltipOuterClass.Tooltip;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public final class CardFragment
  extends FormFragment<CardFormOuterClass.CardForm>
  implements View.OnClickListener, AdapterView.OnItemClickListener, CardNumberEditText.OnPanCategoryChangedListener, NfcIntentForwarder, NfcReadResultListener, AddressEntryFragment.RecipientNameListener, ClickSpan.OnClickListener, OnAutoAdvanceListener, TooltipUiFieldView.OnTooltipIconClickListener
{
  public final ArrayList<AddressEntryFragment> mAddressFragments = new ArrayList();
  private final ArrayList<UiNode> mBaseChildUiNodes = new ArrayList();
  private int mCameraInputPreference = 0;
  public final ArrayList<ArrayList<CardFormFieldData>> mCardFormFieldDataList = new ArrayList();
  private TextWatcher mCardHolderNameTextWatcher;
  CreditCardImagesView mCardImagesView;
  LinearLayout mCardLogoGrid;
  Button mCardLogoGridToggle;
  public CardNumberEditText mCardNumberText;
  private TextWatcher mCardNumberTextWatcher;
  final ArrayList<AutoAdvancer> mCollapseTriggers = new ArrayList();
  private final ArrayList<View> mCollapsedViews = new ArrayList();
  TextView mConcealedCardNumberText;
  private CreditCardEntryAction mCreditCardEntryAction;
  AutoAdvancer mCurrentCollapseTrigger;
  private TextWatcher mExpDateTextWatcher;
  View mExpandIcon;
  boolean mIsNfcIconSetToInProgress;
  private Intent mLaunchOcrIntent;
  final ArrayList<View> mLegalMessages = new ArrayList();
  private int mNextViewId = 1;
  private boolean mNfcAdapterEnabledWhenLastPaused = true;
  ImageView mNfcIcon;
  private Drawable mNfcIconDrawable;
  private int mNfcInputPreference = 0;
  NfcService mNfcService;
  View mOcrIcon;
  private boolean mOneCardMode;
  public final ArrayList<OtpFieldFragment> mOtpFields = new ArrayList();
  private final ArrayList<ArrayList<Integer>> mRevealBelowTriggerViewIndices = new ArrayList();
  private int[] mRevealBelowTriggeredCount;
  RelativeLayout mRootView;
  int[] mSubformCollapsibleChildViewsEndIndex;
  private final ArrayList<UiNode> mSubformUiNodes = new ArrayList();
  final ArrayList<ViewGroup> mSubforms = new ArrayList();
  private final WalletUiElement mUiElement = new WalletUiElement(1654);
  public final ArrayList<View> mUiFields = new ArrayList();
  View mUnresolvedView;
  int mViewState = 1;
  
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
  
  private void expandWithoutAnimationIfCollapsed()
  {
    if (this.mViewState == 2) {
      transitionToState(3, false);
    }
  }
  
  private AutoAdvancer getAutoAdvancerFromField(int paramInt1, int paramInt2)
  {
    CardFormFieldData localCardFormFieldData = (CardFormFieldData)((ArrayList)this.mCardFormFieldDataList.get(paramInt1)).get(paramInt2);
    if (localCardFormFieldData.mFieldType == 1)
    {
      View localView = WalletUiUtils.getBaseUiFieldView((View)this.mUiFields.get(localCardFormFieldData.mIndexInFieldType));
      if ((localView instanceof AutoAdvancer)) {
        return (AutoAdvancer)localView;
      }
    }
    else if (localCardFormFieldData.mFieldType == 2)
    {
      return (AutoAdvancer)this.mOtpFields.get(localCardFormFieldData.mIndexInFieldType);
    }
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = Integer.valueOf(paramInt1);
    arrayOfObject[1] = Integer.valueOf(paramInt2);
    arrayOfObject[2] = Integer.valueOf(localCardFormFieldData.mFieldType);
    throw new IllegalStateException(String.format(localLocale, "Field (subform %s, field %s, type %s) is not an AutoAdvancer", arrayOfObject));
  }
  
  private View getTargetViewFromUiFieldsInPanCategory(int paramInt)
  {
    if (this.mCardNumberText.getPanCategory() == null) {}
    for (;;)
    {
      return null;
      for (int k : this.mCardNumberText.getPanCategory().cardSubformIndex)
      {
        ArrayList localArrayList = (ArrayList)this.mCardFormFieldDataList.get(k);
        int m = 0;
        int n = localArrayList.size();
        while (m < n)
        {
          CardFormFieldData localCardFormFieldData = (CardFormFieldData)localArrayList.get(m);
          if ((localCardFormFieldData.mFieldType == 1) && (localCardFormFieldData.mSemanticHint == paramInt)) {
            return (View)this.mUiFields.get(localCardFormFieldData.mIndexInFieldType);
          }
          m++;
        }
      }
    }
  }
  
  private ArrayList<View> getTargetViewsFromUiFields(int paramInt)
  {
    ArrayList localArrayList1 = new ArrayList();
    int i = 0;
    int j = this.mCardFormFieldDataList.size();
    while (i < j)
    {
      ArrayList localArrayList2 = (ArrayList)this.mCardFormFieldDataList.get(i);
      int k = 0;
      int m = localArrayList2.size();
      while (k < m)
      {
        CardFormFieldData localCardFormFieldData = (CardFormFieldData)localArrayList2.get(k);
        if ((localCardFormFieldData.mFieldType == 1) && (localCardFormFieldData.mSemanticHint == paramInt)) {
          localArrayList1.add(this.mUiFields.get(localCardFormFieldData.mIndexInFieldType));
        }
        k++;
      }
      i++;
    }
    return localArrayList1;
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
  
  public static CardFragment newInstance(CardFormOuterClass.CardForm paramCardForm, int paramInt)
  {
    CardFragment localCardFragment = new CardFragment();
    localCardFragment.setArguments(createArgsForFormFragment$179723a0(paramInt, paramCardForm));
    return localCardFragment;
  }
  
  private void populateCardInfo(String paramString1, int paramInt1, int paramInt2, String paramString2, long paramLong, int paramInt3)
  {
    boolean bool1;
    boolean bool2;
    label27:
    boolean bool3;
    label38:
    ArrayList localArrayList1;
    int[] arrayOfInt;
    int n;
    if (!TextUtils.isEmpty(paramString1))
    {
      bool1 = true;
      if ((paramInt1 <= 0) || (paramInt1 >= 13) || (paramInt2 < 0)) {
        break label394;
      }
      bool2 = true;
      if (TextUtils.isEmpty(paramString2)) {
        break label400;
      }
      bool3 = true;
      if (!bool1) {
        break label475;
      }
      if (this.mConcealedCardNumberText.getVisibility() == 0) {
        transitionToState(3, false);
      }
      this.mCreditCardEntryAction.panEntryType = paramInt3;
      this.mCardNumberText.removeTextChangedListener(this.mCardNumberTextWatcher);
      this.mCardNumberText.requestFocus();
      this.mCardNumberText.setText(paramString1);
      this.mCardNumberText.addTextChangedListener(this.mCardNumberTextWatcher);
      if (!this.mCardNumberText.isValid()) {
        break label475;
      }
      if (bool2)
      {
        View localView2 = getTargetViewFromUiFieldsInPanCategory(3);
        if ((localView2 instanceof ExpDateEditText))
        {
          ExpDateEditText localExpDateEditText = (ExpDateEditText)localView2;
          this.mCreditCardEntryAction.expDateEntryType = paramInt3;
          localExpDateEditText.removeTextChangedListener(this.mExpDateTextWatcher);
          localExpDateEditText.requestFocus();
          localExpDateEditText.setExpDate(Integer.toString(paramInt1), Integer.toString(paramInt2 % 100));
          localExpDateEditText.addTextChangedListener(this.mExpDateTextWatcher);
        }
      }
      if (!bool3) {
        break label475;
      }
      View localView1 = getTargetViewFromUiFieldsInPanCategory(1);
      if ((localView1 instanceof TextView))
      {
        TextView localTextView = (TextView)localView1;
        this.mCreditCardEntryAction.nameEntryType = paramInt3;
        localTextView.removeTextChangedListener(this.mCardHolderNameTextWatcher);
        localTextView.requestFocus();
        localTextView.setText(paramString2);
        localTextView.addTextChangedListener(this.mCardHolderNameTextWatcher);
      }
      localArrayList1 = new ArrayList();
      if (this.mCardNumberText.getPanCategory() == null) {
        break label412;
      }
      arrayOfInt = this.mCardNumberText.getPanCategory().cardSubformIndex;
      n = arrayOfInt.length;
    }
    for (int i1 = 0;; i1++)
    {
      if (i1 >= n) {
        break label412;
      }
      int i2 = arrayOfInt[i1];
      ArrayList localArrayList2 = (ArrayList)this.mCardFormFieldDataList.get(i2);
      int i3 = localArrayList2.size();
      int i4 = 0;
      for (;;)
      {
        if (i4 < i3)
        {
          CardFormFieldData localCardFormFieldData = (CardFormFieldData)localArrayList2.get(i4);
          if (localCardFormFieldData.mFieldType == 4) {
            localArrayList1.add(this.mAddressFragments.get(localCardFormFieldData.mIndexInFieldType));
          }
          i4++;
          continue;
          bool1 = false;
          break;
          label394:
          bool2 = false;
          break label27;
          label400:
          bool3 = false;
          break label38;
        }
      }
    }
    label412:
    int k = localArrayList1.size();
    for (int m = 0; m < k; m++)
    {
      this.mCreditCardEntryAction.nameEntryType = paramInt3;
      AddressEntryFragment localAddressEntryFragment = (AddressEntryFragment)localArrayList1.get(m);
      localAddressEntryFragment.mRecipientNameListener = null;
      localAddressEntryFragment.setRecipientName(paramString2);
      localAddressEntryFragment.mRecipientNameListener = this;
    }
    label475:
    if (paramInt3 == 2)
    {
      CreditCardEntryAction localCreditCardEntryAction2 = this.mCreditCardEntryAction;
      if (this.mCreditCardEntryAction.numOcrAttempts < 0) {}
      for (int j = 1;; j = 1 + this.mCreditCardEntryAction.numOcrAttempts)
      {
        localCreditCardEntryAction2.numOcrAttempts = j;
        this.mCreditCardEntryAction.panRecognizedByOcr = bool1;
        this.mCreditCardEntryAction.expDateRecognizedByOcr = bool2;
        this.mCreditCardEntryAction.nameRecognizedByOcr = bool3;
        sendCreditCardEntryActionBackgroundEvent();
        return;
      }
    }
    if (paramInt3 == 3)
    {
      CreditCardEntryAction localCreditCardEntryAction1 = this.mCreditCardEntryAction;
      if (this.mCreditCardEntryAction.numNfcAttempts < 0) {}
      for (int i = 1;; i = 1 + this.mCreditCardEntryAction.numNfcAttempts)
      {
        localCreditCardEntryAction1.numNfcAttempts = i;
        this.mCreditCardEntryAction.panRecognizedByNfc = bool1;
        this.mCreditCardEntryAction.expDateRecognizedByNfc = bool2;
        this.mCreditCardEntryAction.nameRecognizedByNfc = bool3;
        if (paramLong < 0L) {
          break;
        }
        this.mCreditCardEntryAction.nfcElapsedTimeMillis = paramLong;
        break;
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
  
  private void showNfcInfoDialog()
  {
    NfcInfoDialogFragment localNfcInfoDialogFragment = NfcInfoDialogFragment.newInstance(this.mThemeResourceId, this.mNfcService.isAdapterEnabled());
    localNfcInfoDialogFragment.setTargetFragment(this, 0);
    localNfcInfoDialogFragment.show(this.mFragmentManager, "tagNfcInfoDialog");
  }
  
  private void switchToOneCardMode()
  {
    this.mCardImagesView.switchToOneCardMode();
    RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)this.mCardNumberText.getLayoutParams();
    localLayoutParams.addRule(WalletUiUtils.sanitizeRelativeLayoutVerb(3), R.id.header);
    localLayoutParams.leftMargin = ((RelativeLayout.LayoutParams)this.mConcealedCardNumberText.getLayoutParams()).leftMargin;
    this.mCardNumberText.setLayoutParams(localLayoutParams);
    this.mOneCardMode = true;
  }
  
  @TargetApi(14)
  private void transitionFromCollapsedToExpandedState(boolean paramBoolean)
  {
    CardFormOuterClass.PanCategory localPanCategory = this.mCardNumberText.getPanCategory();
    if ((localPanCategory == null) || (localPanCategory.cardSubformIndex.length == 0)) {
      throw new IllegalStateException("State transitions should not be triggered when no subforms are visible.");
    }
    if (!this.mOneCardMode) {
      switchToOneCardMode();
    }
    int j;
    int k;
    label143:
    View localView;
    if (paramBoolean)
    {
      WalletUiUtils.animateViewDisappearingToInvisible$17e143a3(this.mConcealedCardNumberText, 0);
      WalletUiUtils.animateViewDisappearingToInvisible$17e143a3(this.mExpandIcon, 0);
      WalletUiUtils.animateViewAppearing(this.mCardNumberText, 0, 0);
      if (this.mOcrIcon != null) {
        WalletUiUtils.animateViewAppearing(this.mOcrIcon, 0, 0);
      }
      if (this.mNfcIcon != null) {
        WalletUiUtils.animateViewAppearing(this.mNfcIcon, 0, 0);
      }
      ViewGroup localViewGroup1 = (ViewGroup)this.mSubforms.get(localPanCategory.cardSubformIndex[0]);
      int i = WalletUiUtils.getViewHeightWithMargins(this.mConcealedCardNumberText);
      j = 0;
      k = -1 + this.mCollapsedViews.size();
      if (k < 0) {
        break label304;
      }
      localView = (View)this.mCollapsedViews.get(k);
      LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams((ViewGroup.MarginLayoutParams)localView.getLayoutParams());
      this.mRootView.removeView(localView);
      localViewGroup1.addView(localView, 0, localLayoutParams);
      if (localView.getVisibility() != 8)
      {
        if (!paramBoolean) {
          break label295;
        }
        j += WalletUiUtils.getViewHeightWithMargins(localView);
        WalletUiUtils.animateViewAppearing(localView, j - i, 0);
      }
    }
    for (;;)
    {
      k--;
      break label143;
      this.mConcealedCardNumberText.setVisibility(4);
      this.mExpandIcon.setVisibility(4);
      this.mCardNumberText.setVisibility(0);
      if (this.mOcrIcon != null) {
        this.mOcrIcon.setVisibility(0);
      }
      if (this.mNfcIcon == null) {
        break;
      }
      this.mNfcIcon.setVisibility(0);
      break;
      label295:
      localView.setVisibility(0);
    }
    label304:
    if (paramBoolean) {
      for (int i1 : localPanCategory.cardSubformIndex)
      {
        ViewGroup localViewGroup2 = (ViewGroup)this.mSubforms.get(i1);
        localViewGroup2.setTranslationY(-j);
        localViewGroup2.animate().translationY(0.0F).start();
      }
    }
    this.mCollapsedViews.clear();
  }
  
  @TargetApi(14)
  private void transitionFromInitialToCollapsedState(boolean paramBoolean)
  {
    CardFormOuterClass.PanCategory localPanCategory = this.mCardNumberText.getPanCategory();
    if ((localPanCategory == null) || (localPanCategory.cardSubformIndex.length == 0)) {
      throw new IllegalStateException("State transitions should not be triggered when no subforms are visible.");
    }
    int i = WalletUiUtils.getViewHeightWithMargins(this.mCardNumberText);
    boolean bool1 = this.mOneCardMode;
    int j = 0;
    if (!bool1)
    {
      ((RelativeLayout.LayoutParams)this.mCardNumberText.getLayoutParams()).addRule(WalletUiUtils.sanitizeRelativeLayoutVerb(3), R.id.header);
      j = 0;
      if (paramBoolean)
      {
        int i3 = WalletUiUtils.getViewHeightWithMargins(this.mConcealedCardNumberText);
        j = i3 + 0;
        i += i3;
      }
    }
    label205:
    int k;
    int[] arrayOfInt;
    int m;
    if (paramBoolean)
    {
      WalletUiUtils.animateViewDisappearingToInvisible$17e143a3(this.mCardNumberText, j);
      if ((this.mOcrIcon != null) && (this.mOcrIcon.getVisibility() == 0)) {
        WalletUiUtils.animateViewDisappearingToGone(this.mOcrIcon, j, 0);
      }
      if ((this.mNfcIcon != null) && (this.mNfcIcon.getVisibility() == 0)) {
        WalletUiUtils.animateViewDisappearingToGone(this.mNfcIcon, j, 0);
      }
      this.mConcealedCardNumberText.setText(this.mCardNumberText.getConcealedCardNumber());
      if (!paramBoolean) {
        break label457;
      }
      WalletUiUtils.animateViewAppearing(this.mConcealedCardNumberText, j, 0);
      WalletUiUtils.animateViewAppearing(this.mExpandIcon, j, 0);
      k = 0;
      arrayOfInt = localPanCategory.cardSubformIndex;
      m = arrayOfInt.length;
    }
    for (int n = 0;; n++)
    {
      if (n >= m) {
        return;
      }
      int i1 = arrayOfInt[n];
      ViewGroup localViewGroup = (ViewGroup)this.mSubforms.get(i1);
      if (i1 == localPanCategory.cardSubformIndex[0])
      {
        int i2 = 0;
        label264:
        if (i2 <= this.mSubformCollapsibleChildViewsEndIndex[i1])
        {
          View localView = localViewGroup.getChildAt(0);
          RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams((ViewGroup.MarginLayoutParams)localView.getLayoutParams());
          localLayoutParams.addRule(WalletUiUtils.sanitizeRelativeLayoutVerb(3), R.id.header);
          boolean bool2 = localView.hasFocus();
          if (bool2) {
            WalletUiUtils.clearFocus(getActivity().findViewById(16908290));
          }
          localViewGroup.removeView(localView);
          this.mRootView.addView(localView, localLayoutParams);
          if (bool2) {
            localView.requestFocus();
          }
          if (localView.getVisibility() == 0)
          {
            if (!paramBoolean) {
              break label476;
            }
            WalletUiUtils.animateViewDisappearingToInvisible$17e143a3(localView, i + k);
          }
          for (;;)
          {
            k += WalletUiUtils.getViewHeightWithMargins(localView);
            this.mCollapsedViews.add(localView);
            i2++;
            break label264;
            this.mCardNumberText.setVisibility(4);
            if (this.mOcrIcon != null) {
              this.mOcrIcon.setVisibility(8);
            }
            if (this.mNfcIcon == null) {
              break;
            }
            this.mNfcIcon.setVisibility(8);
            break;
            label457:
            this.mConcealedCardNumberText.setVisibility(0);
            this.mExpandIcon.setVisibility(0);
            break label205;
            label476:
            localView.setVisibility(4);
          }
        }
      }
      if (paramBoolean)
      {
        localViewGroup.setTranslationY(j + k);
        localViewGroup.animate().translationY(0.0F).start();
      }
    }
  }
  
  private void transitionToState(int paramInt, boolean paramBoolean)
  {
    if (paramInt == this.mViewState) {
      return;
    }
    if (paramInt < this.mViewState) {
      throw new IllegalArgumentException("Cannot transition to an earlier state");
    }
    if (Build.VERSION.SDK_INT < 14) {
      throw new IllegalStateException("State transitions are not implemented for API level: " + Build.VERSION.SDK_INT);
    }
    if ((this.mViewState == 1) && (paramInt == 2)) {
      transitionFromInitialToCollapsedState(paramBoolean);
    }
    for (;;)
    {
      this.mViewState = paramInt;
      return;
      if ((this.mViewState == 2) && (paramInt == 3))
      {
        transitionFromCollapsedToExpandedState(paramBoolean);
      }
      else
      {
        if ((this.mViewState != 1) || (paramInt != 3)) {
          break;
        }
        if (paramBoolean) {
          throw new IllegalArgumentException("Cannot animate transition from STATE_INITIAL to STATE_EXPANDED");
        }
        if (!this.mOneCardMode) {
          switchToOneCardMode();
        }
      }
    }
    throw new IllegalArgumentException("Unimplemented state transition: " + this.mViewState + " to " + paramInt);
  }
  
  public final boolean applyFormFieldMessage(UiErrorOuterClass.FormFieldMessage paramFormFieldMessage)
  {
    if (paramFormFieldMessage.formFieldReference.formId.equals(((CardFormOuterClass.CardForm)this.mFormProto).formHeader.id))
    {
      if (paramFormFieldMessage.formFieldReference.fieldId == 1)
      {
        this.mCardNumberText.setError(paramFormFieldMessage.message);
        if (!this.mCreditCardEntryAction.panValidationErrorOccurred)
        {
          this.mCreditCardEntryAction.panValidationErrorOccurred = true;
          sendCreditCardEntryActionBackgroundEvent();
        }
        expandWithoutAnimationIfCollapsed();
        return true;
      }
      throw new IllegalArgumentException("Unknown FormFieldMessage fieldId: " + paramFormFieldMessage.formFieldReference.fieldId);
    }
    CardFormOuterClass.PanCategory localPanCategory = this.mCardNumberText.getPanCategory();
    if (localPanCategory == null) {
      return false;
    }
    for (int k : localPanCategory.cardSubformIndex)
    {
      ArrayList localArrayList = (ArrayList)this.mCardFormFieldDataList.get(k);
      if (paramFormFieldMessage.formFieldReference.formId.equals(((CardFormOuterClass.CardForm)this.mFormProto).cardSubform[k].formHeader.id))
      {
        CardFormFieldData localCardFormFieldData2 = (CardFormFieldData)localArrayList.get(paramFormFieldMessage.formFieldReference.repeatedFieldIndex);
        if (localCardFormFieldData2.mFieldType == 1)
        {
          WalletUiUtils.setUiFieldError((View)this.mUiFields.get(localCardFormFieldData2.mIndexInFieldType), paramFormFieldMessage.message);
          if ((localCardFormFieldData2.mSemanticHint == 3) && (!this.mCreditCardEntryAction.expDateValidationErrorOccurred))
          {
            this.mCreditCardEntryAction.expDateValidationErrorOccurred = true;
            sendCreditCardEntryActionBackgroundEvent();
          }
          if ((localCardFormFieldData2.mSemanticHint == 1) && (!this.mCreditCardEntryAction.nameValidationErrorOccurred))
          {
            this.mCreditCardEntryAction.nameValidationErrorOccurred = true;
            sendCreditCardEntryActionBackgroundEvent();
          }
        }
        for (;;)
        {
          expandWithoutAnimationIfCollapsed();
          return true;
          if (localCardFormFieldData2.mFieldType != 2) {
            break;
          }
          ((OtpFieldFragment)this.mOtpFields.get(localCardFormFieldData2.mIndexInFieldType)).setError(paramFormFieldMessage.message);
        }
        throw new IllegalArgumentException("Could not apply FormFieldMessage to fieldId: " + paramFormFieldMessage.formFieldReference.fieldId);
      }
      int m = 0;
      int n = localArrayList.size();
      while (m < n)
      {
        CardFormFieldData localCardFormFieldData1 = (CardFormFieldData)localArrayList.get(m);
        if ((localCardFormFieldData1.mFieldType == 4) && (((AddressEntryFragment)this.mAddressFragments.get(localCardFormFieldData1.mIndexInFieldType)).applyFormFieldMessage(paramFormFieldMessage)))
        {
          expandWithoutAnimationIfCollapsed();
          return true;
        }
        m++;
      }
    }
    return false;
  }
  
  protected final void doEnableUi()
  {
    if (this.mCardNumberText == null) {}
    boolean bool;
    do
    {
      return;
      bool = this.mUiEnabled;
      this.mCardNumberText.setEnabled(bool);
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
      int i1 = this.mOtpFields.size();
      while (n < i1)
      {
        ((OtpFieldFragment)this.mOtpFields.get(n)).enableUi(bool);
        n++;
      }
      int i2 = 0;
      int i3 = this.mLegalMessages.size();
      while (i2 < i3)
      {
        ((View)this.mLegalMessages.get(i2)).setEnabled(bool);
        i2++;
      }
      if (this.mOcrIcon != null) {
        this.mOcrIcon.setEnabled(bool);
      }
    } while (this.mNfcIcon == null);
    this.mNfcIcon.setEnabled(bool);
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
      if ((this.mNfcIcon != null) && (this.mViewState != 2))
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
  
  public final List<UiNode> getChildren()
  {
    Object localObject;
    if (this.mCardNumberText == null) {
      localObject = null;
    }
    for (;;)
    {
      return localObject;
      localObject = new ArrayList(this.mBaseChildUiNodes);
      if (isOcrEnabled()) {
        ((List)localObject).add(new SimpleUiNode(1652, this));
      }
      if (isNfcEnabled()) {
        ((List)localObject).add(new SimpleUiNode(1655, this));
      }
      if ((NfcInfoDialogFragment)this.mFragmentManager.findFragmentByTag("tagNfcInfoDialog") != null) {
        ((List)localObject).add(new SimpleUiNode(1656, this));
      }
      CardFormOuterClass.PanCategory localPanCategory = this.mCardNumberText.getPanCategory();
      if (localPanCategory != null) {
        for (int k : localPanCategory.cardSubformIndex) {
          ((List)localObject).add(this.mSubformUiNodes.get(k));
        }
      }
    }
  }
  
  public final List<FormFragment.FieldData> getFieldsForValidationInOrder()
  {
    ArrayList localArrayList1 = new ArrayList();
    FormFragment.FieldData localFieldData1 = new FormFragment.FieldData(((CardFormOuterClass.CardForm)this.mFormProto).primaryAccountNumberUiReference, this.mCardNumberText, null);
    localArrayList1.add(localFieldData1);
    CardFormOuterClass.PanCategory localPanCategory = this.mCardNumberText.getPanCategory();
    if (localPanCategory == null) {}
    for (;;)
    {
      return localArrayList1;
      for (int k : localPanCategory.cardSubformIndex)
      {
        ArrayList localArrayList2 = (ArrayList)this.mCardFormFieldDataList.get(k);
        int m = 0;
        int n = localArrayList2.size();
        if (m < n)
        {
          CardFormFieldData localCardFormFieldData = (CardFormFieldData)localArrayList2.get(m);
          CardFormOuterClass.CardField localCardField = ((CardFormOuterClass.CardForm)this.mFormProto).cardSubform[k].cardField[m];
          switch (localCardFormFieldData.mFieldType)
          {
          case 3: 
          default: 
            throw new IllegalStateException("Unknown field type " + localCardFormFieldData.mFieldType + " in CardSubform.");
          case 1: 
            View localView = (View)this.mUiFields.get(localCardFormFieldData.mIndexInFieldType);
            FormFragment.FieldData localFieldData4 = new FormFragment.FieldData(localCardFormFieldData.mUiReference, localView, WalletUiUtils.getInitialValue(localCardField.uiField));
            localArrayList1.add(localFieldData4);
          }
          for (;;)
          {
            m++;
            break;
            AddressEntryFragment localAddressEntryFragment = (AddressEntryFragment)this.mAddressFragments.get(localCardFormFieldData.mIndexInFieldType);
            FormFragment.FieldData localFieldData3 = new FormFragment.FieldData(localCardFormFieldData.mUiReference, localAddressEntryFragment);
            localArrayList1.add(localFieldData3);
            continue;
            OtpFieldFragment localOtpFieldFragment = (OtpFieldFragment)this.mOtpFields.get(localCardFormFieldData.mIndexInFieldType);
            FormFragment.FieldData localFieldData2 = new FormFragment.FieldData(localCardFormFieldData.mUiReference, localOtpFieldFragment, null);
            localArrayList1.add(localFieldData2);
          }
        }
      }
    }
  }
  
  public final WalletUiElement getUiElement()
  {
    return this.mUiElement;
  }
  
  public final boolean isReadyToSubmit()
  {
    if (this.mCardNumberText == null) {
      return false;
    }
    CardFormOuterClass.PanCategory localPanCategory = this.mCardNumberText.getPanCategory();
    if (localPanCategory == null) {
      return false;
    }
    for (int k : localPanCategory.cardSubformIndex)
    {
      ArrayList localArrayList = (ArrayList)this.mCardFormFieldDataList.get(k);
      int m = 0;
      int n = localArrayList.size();
      while (m < n)
      {
        CardFormFieldData localCardFormFieldData = (CardFormFieldData)localArrayList.get(m);
        if (localCardFormFieldData.mFieldType == 4) {
          this.mAddressFragments.get(localCardFormFieldData.mIndexInFieldType);
        }
        m++;
      }
      if (!((ArrayList)this.mRevealBelowTriggerViewIndices.get(k)).isEmpty()) {
        return false;
      }
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
      populateCardInfo(localCreditCardOcrResult.zzbrS, localCreditCardOcrResult.zzbrT, localCreditCardOcrResult.zzbrU, "", -1L, 2);
      return;
    }
    populateCardInfo("", 0, -1, "", -1L, 2);
  }
  
  public final void onAutoAdvance(View paramView)
  {
    if (this.mViewState == 1) {
      transitionToState(2, true);
    }
  }
  
  public final void onClick(View paramView)
  {
    if (paramView == this.mExpandIcon) {
      transitionToState(3, true);
    }
    do
    {
      return;
      if (paramView == this.mCardLogoGridToggle)
      {
        if (this.mCardLogoGrid.getVisibility() == 0)
        {
          WalletUiUtils.animateViewDisappearingToGone(this.mCardLogoGrid, 0, 0);
          this.mCardLogoGridToggle.setText(getString(R.string.wallet_im_view_card_logos));
          return;
        }
        WalletUiUtils.animateViewAppearing(this.mCardLogoGrid, 0, 0);
        this.mCardLogoGridToggle.setText(getString(R.string.wallet_im_hide_card_logos));
        return;
      }
      if (paramView == this.mOcrIcon)
      {
        launchOcr();
        return;
      }
    } while (paramView != this.mNfcIcon);
    launchNfc();
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
    int[] arrayOfInt1 = ((CardFormOuterClass.CardForm)this.mFormProto).cameraInputUiPreference;
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
        int[] arrayOfInt2 = ((CardFormOuterClass.CardForm)this.mFormProto).nfcInputPreference;
        int k = arrayOfInt2.length;
        m = 0;
        label165:
        if (m < k)
        {
          int n = arrayOfInt2[m];
          if ((n != 1) && (n != 2)) {
            break label327;
          }
          this.mNfcInputPreference = n;
        }
      }
      if (!isNfcEnabled()) {
        break label333;
      }
    }
    label327:
    label333:
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
      break label165;
    }
  }
  
  @TargetApi(11)
  protected final View onCreateThemedView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    ContextThemeWrapper localContextThemeWrapper = this.mThemedContext;
    int[] arrayOfInt1 = new int[2];
    arrayOfInt1[0] = R.attr.internalUicLegalMessageLayout;
    arrayOfInt1[1] = R.attr.internalUicFormNonEditableTextStartMargin;
    TypedArray localTypedArray = localContextThemeWrapper.obtainStyledAttributes(arrayOfInt1);
    int i = localTypedArray.getResourceId(0, R.layout.view_legal_message_text);
    int j = localTypedArray.getDimensionPixelSize(1, 0);
    localTypedArray.recycle();
    int k = (int)this.mThemedContext.getResources().getDimension(R.dimen.wallet_im_legal_message_top_margin);
    this.mRootView = ((RelativeLayout)paramLayoutInflater.inflate(R.layout.fragment_card, null));
    this.mNextViewId = ((FormHeaderView)this.mRootView.findViewById(R.id.header)).setFormHeader(((CardFormOuterClass.CardForm)this.mFormProto).formHeader, paramLayoutInflater, this, this, this.mBaseChildUiNodes, this.mNextViewId);
    boolean bool;
    ImageWithCaptionOuterClass.ImageWithCaption[] arrayOfImageWithCaption1;
    ImageWithCaptionOuterClass.ImageWithCaption[] arrayOfImageWithCaption2;
    label260:
    label569:
    label607:
    int n;
    label703:
    int i1;
    CardFormOuterClass.CardSubform localCardSubform;
    LinearLayout localLinearLayout;
    UiNode local2;
    int i6;
    ArrayList localArrayList1;
    int i7;
    label877:
    CardFormOuterClass.CardField localCardField;
    int i16;
    CardFormFieldData localCardFormFieldData;
    View localView2;
    if ((((CardFormOuterClass.CardForm)this.mFormProto).logoUiPreference == 2) || (((CardFormOuterClass.CardForm)this.mFormProto).logoUiPreference == 3))
    {
      bool = true;
      this.mCardImagesView = ((CreditCardImagesView)this.mRootView.findViewById(R.id.card_images));
      if (((CardFormOuterClass.CardForm)this.mFormProto).resolvedOnlyLogoStartingIndex < 0) {
        break label1117;
      }
      arrayOfImageWithCaption1 = (ImageWithCaptionOuterClass.ImageWithCaption[])Arrays.copyOfRange(((CardFormOuterClass.CardForm)this.mFormProto).logo, 0, ((CardFormOuterClass.CardForm)this.mFormProto).resolvedOnlyLogoStartingIndex);
      arrayOfImageWithCaption2 = (ImageWithCaptionOuterClass.ImageWithCaption[])Arrays.copyOfRange(((CardFormOuterClass.CardForm)this.mFormProto).logo, ((CardFormOuterClass.CardForm)this.mFormProto).resolvedOnlyLogoStartingIndex, ((CardFormOuterClass.CardForm)this.mFormProto).logo.length);
      CreditCardImagesView localCreditCardImagesView1 = this.mCardImagesView;
      int m = this.mNextViewId;
      this.mNextViewId = localCreditCardImagesView1.setCardIcons(arrayOfImageWithCaption1, arrayOfImageWithCaption2, bool, m);
      this.mCardNumberText = ((CardNumberEditText)this.mRootView.findViewById(R.id.card_number));
      this.mCardNumberText.enableAutoAdvance(this.mCardNumberText, this.mCardNumberText, false);
      this.mCardNumberText.setAllowedPanCategories(((CardFormOuterClass.CardForm)this.mFormProto).allowedPanCategory);
      this.mCardNumberText.setExcludedPanCategories(((CardFormOuterClass.CardForm)this.mFormProto).excludedPanCategory);
      this.mCardNumberText.setNoMatchPanMessage(((CardFormOuterClass.CardForm)this.mFormProto).noMatchPanMessage);
      this.mCardNumberText.setInvalidPanMessage(((CardFormOuterClass.CardForm)this.mFormProto).invalidPanMessage);
      this.mCardNumberText.setOnPanCategoryChangedListener(this);
      WalletUiUtils.addComponentToDependencyGraph(this.mCardNumberText, ((CardFormOuterClass.CardForm)this.mFormProto).primaryAccountNumberUiReference, this.mDependencyGraphManager, this.mTriggerListener);
      this.mConcealedCardNumberText = ((TextView)this.mRootView.findViewById(R.id.card_number_concealed));
      this.mConcealedCardNumberText.setPadding(this.mCardNumberText.getPaddingLeft(), this.mCardNumberText.getPaddingTop(), this.mCardNumberText.getPaddingRight(), this.mCardNumberText.getPaddingBottom());
      if ((Build.VERSION.SDK_INT >= 18) && (Build.VERSION.SDK_INT < 21)) {
        this.mConcealedCardNumberText.addOnLayoutChangeListener(new View.OnLayoutChangeListener()
        {
          public final void onLayoutChange(View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, int paramAnonymousInt5, int paramAnonymousInt6, int paramAnonymousInt7, int paramAnonymousInt8)
          {
            CardFragment.this.mCardImagesView.getLayoutParams().height = (paramAnonymousInt4 - paramAnonymousInt2);
            CardFragment.this.mConcealedCardNumberText.removeOnLayoutChangeListener(this);
          }
        });
      }
      this.mExpandIcon = this.mRootView.findViewById(R.id.expand_icon);
      this.mExpandIcon.setOnClickListener(this);
      if (this.mNfcInputPreference != 1) {
        break label1137;
      }
      this.mNfcIcon = ((ImageView)this.mRootView.findViewById(R.id.nfc_icon));
      this.mNfcIcon.setOnClickListener(this);
      this.mNfcIcon.setVisibility(0);
      if (this.mCameraInputPreference != 1) {
        break label1207;
      }
      this.mOcrIcon = this.mRootView.findViewById(R.id.ocr_icon);
      this.mOcrIcon.setOnClickListener(this);
      this.mOcrIcon.setVisibility(0);
      this.mUnresolvedView = this.mRootView.findViewById(R.id.unresolved_view);
      this.mCardLogoGridToggle = ((Button)this.mRootView.findViewById(R.id.card_logo_grid_toggle));
      this.mCardLogoGrid = ((LinearLayout)this.mRootView.findViewById(R.id.card_logo_grid));
      final WalletUiElement localWalletUiElement = new WalletUiElement(1716);
      n = ((CardFormOuterClass.CardForm)this.mFormProto).cardSubform.length;
      this.mSubformCollapsibleChildViewsEndIndex = new int[n];
      if (paramBundle == null) {
        break label1278;
      }
      this.mRevealBelowTriggeredCount = paramBundle.getIntArray("revealBelowTriggeredCount");
      i1 = 0;
      if (i1 >= n) {
        break label2092;
      }
      localCardSubform = ((CardFormOuterClass.CardForm)this.mFormProto).cardSubform[i1];
      localLinearLayout = (LinearLayout)paramLayoutInflater.inflate(R.layout.view_subform, this.mRootView, false);
      int i5 = this.mNextViewId;
      this.mNextViewId = (i5 + 1);
      localLinearLayout.setId(i5);
      local2 = new UiNode()
      {
        private final List<UiNode> mChildren = new ArrayList();
        
        public final List<UiNode> getChildren()
        {
          return this.mChildren;
        }
        
        public final UiNode getParentUiNode()
        {
          return CardFragment.this;
        }
        
        public final WalletUiElement getUiElement()
        {
          return localWalletUiElement;
        }
        
        public final void setParentUiNode(UiNode paramAnonymousUiNode)
        {
          throw new UnsupportedOperationException("Custom parents not supported for CardForm subforms.");
        }
      };
      this.mSubforms.add(localLinearLayout);
      this.mSubformUiNodes.add(local2);
      this.mCardFormFieldDataList.add(new ArrayList());
      this.mNextViewId = ((FormHeaderView)localLinearLayout.findViewById(R.id.subform_header)).setFormHeader(localCardSubform.formHeader, paramLayoutInflater, this, local2, local2.getChildren(), this.mNextViewId);
      i6 = localCardSubform.cardField.length;
      localArrayList1 = new ArrayList(localCardSubform.cardField.length);
      i7 = 0;
      if (i7 >= i6) {
        break label1668;
      }
      localCardField = ((CardFormOuterClass.CardForm)this.mFormProto).cardSubform[i1].cardField[i7];
      i16 = this.mNextViewId;
      this.mNextViewId = (i16 + 1);
      localCardFormFieldData = new CardFormFieldData((byte)0);
      if (localCardField.uiField == null) {
        break label1289;
      }
      UiFieldBuilder localUiFieldBuilder = new UiFieldBuilder(localCardField.uiField, this.mThemedInflater);
      localUiFieldBuilder.mActivity = getActivity();
      localUiFieldBuilder.mViewId = i16;
      localUiFieldBuilder.mOnTooltipIconClickListener = this;
      localView2 = localUiFieldBuilder.build();
      localCardFormFieldData.mFieldType = 1;
      localCardFormFieldData.mIndexInFieldType = this.mUiFields.size();
      localCardFormFieldData.mUiReference = localCardField.uiField.uiReference;
      localCardFormFieldData.mSemanticHint = localCardField.uiField.semanticHint;
      this.mUiFields.add(localView2);
      WalletUiUtils.addComponentToDependencyGraph(localView2, localCardField.uiField.uiReference, this.mDependencyGraphManager, this.mTriggerListener);
    }
    label1063:
    label1117:
    label1137:
    label1278:
    FrameLayout localFrameLayout1;
    for (Object localObject1 = localView2;; localObject1 = localFrameLayout1)
    {
      ((ArrayList)this.mCardFormFieldDataList.get(i1)).add(localCardFormFieldData);
      ((View)localObject1).setTag(R.id.field_type, Integer.valueOf(localCardFormFieldData.mFieldType));
      localArrayList1.add(localObject1);
      i7++;
      break label877;
      bool = false;
      break;
      arrayOfImageWithCaption1 = ((CardFormOuterClass.CardForm)this.mFormProto).logo;
      arrayOfImageWithCaption2 = ImageWithCaptionOuterClass.ImageWithCaption.emptyArray();
      break label260;
      if (this.mNfcInputPreference != 2) {
        break label569;
      }
      CardNumberEditText localCardNumberEditText1 = this.mCardNumberText;
      if (localCardNumberEditText1.mNfcDropDownItem == null)
      {
        localCardNumberEditText1.mNfcDropDownItem = new DropDownItem(R.attr.imNfcDrawable, localCardNumberEditText1.getResources().getString(R.string.wallet_im_nfc_popup_title), 2);
        localCardNumberEditText1.addDropDownButton(localCardNumberEditText1.mNfcDropDownItem);
      }
      this.mCardNumberText.setOnItemClickListener(this);
      break label569;
      label1207:
      if (this.mCameraInputPreference != 6) {
        break label607;
      }
      CardNumberEditText localCardNumberEditText2 = this.mCardNumberText;
      if (localCardNumberEditText2.mOcrDropDownItem == null)
      {
        localCardNumberEditText2.mOcrDropDownItem = new DropDownItem(R.attr.imCameraDrawable, localCardNumberEditText2.getResources().getString(R.string.wallet_im_ocr_drop_down_button), 1);
        localCardNumberEditText2.addDropDownButton(localCardNumberEditText2.mOcrDropDownItem);
      }
      this.mCardNumberText.setOnItemClickListener(this);
      break label607;
      this.mRevealBelowTriggeredCount = new int[n];
      break label703;
      label1289:
      if (localCardField.addressForm == null) {
        break label1468;
      }
      localFrameLayout1 = new FrameLayout(this.mThemedContext);
      localFrameLayout1.setId(i16);
      AddressEntryFragment localAddressEntryFragment1 = (AddressEntryFragment)getChildFragmentManager().findFragmentById(i16);
      if (localAddressEntryFragment1 == null)
      {
        localAddressEntryFragment1 = AddressEntryFragment.newInstance(localCardField.addressForm, this.mThemeResourceId);
        getChildFragmentManager().beginTransaction().add(i16, localAddressEntryFragment1).commit();
      }
      AddressEntryFragment localAddressEntryFragment2 = localAddressEntryFragment1;
      localAddressEntryFragment2.mRecipientNameListener = this;
      localCardFormFieldData.mFieldType = 4;
      localCardFormFieldData.mIndexInFieldType = this.mAddressFragments.size();
      localCardFormFieldData.mUiReference = localCardField.addressForm.uiReference;
      this.mAddressFragments.add(localAddressEntryFragment2);
      localAddressEntryFragment2.setParentUiNode((UiNode)this.mSubformUiNodes.get(i1));
      ((UiNode)this.mSubformUiNodes.get(i1)).getChildren().add(localAddressEntryFragment2);
    }
    label1468:
    FrameLayout localFrameLayout2;
    OtpFieldFragment localOtpFieldFragment;
    ImOtpFieldFragment localImOtpFieldFragment;
    if (localCardField.otpField != null)
    {
      localFrameLayout2 = new FrameLayout(this.mThemedContext);
      localFrameLayout2.setId(i16);
      localOtpFieldFragment = (OtpFieldFragment)getChildFragmentManager().findFragmentById(i16);
      if (localOtpFieldFragment != null) {
        break label2314;
      }
      localImOtpFieldFragment = ImOtpFieldFragment.newInstance(localCardField.otpField, this.mThemeResourceId);
      getChildFragmentManager().beginTransaction().add(i16, localImOtpFieldFragment).commit();
    }
    label1668:
    label2314:
    for (Object localObject2 = localImOtpFieldFragment;; localObject2 = localOtpFieldFragment)
    {
      ((OtpFieldFragment)localObject2).registerDependencyGraphComponents(this.mDependencyGraphManager, this.mTriggerListener);
      localCardFormFieldData.mFieldType = 2;
      localCardFormFieldData.mIndexInFieldType = this.mOtpFields.size();
      localCardFormFieldData.mUiReference = localCardField.otpField.otp.uiReference;
      this.mOtpFields.add(localObject2);
      ((OtpFieldFragment)localObject2).setParentUiNode((UiNode)this.mSubformUiNodes.get(i1));
      ((UiNode)this.mSubformUiNodes.get(i1)).getChildren().add(localObject2);
      localObject1 = localFrameLayout2;
      break label1063;
      throw new IllegalArgumentException("Empty or unknown field in CardSubform.");
      int[] arrayOfInt2 = WalletUiUtils.addGroupedViewsToViewGroup(this.mThemedContext, localArrayList1, localCardSubform.fieldGrouping, localLinearLayout);
      if (localCardSubform.legalMessage != null)
      {
        View localView1 = WalletUiUtils.createViewForLegalMessage(this.mThemedInflater, i, localCardSubform.legalMessage, this, this);
        int i15 = this.mNextViewId;
        this.mNextViewId = (i15 + 1);
        localView1.setId(i15);
        localLinearLayout.addView(localView1);
        this.mLegalMessages.add(localView1);
        local2.getChildren().add((UiNode)localView1);
        ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)localView1.getLayoutParams();
        MarginLayoutParamsCompat.setMarginStart(localMarginLayoutParams, j);
        localMarginLayoutParams.topMargin = k;
        localMarginLayoutParams.bottomMargin = k;
      }
      ArrayList localArrayList2;
      int i9;
      if (localCardSubform.collapsibleFieldsEndIndex >= 0)
      {
        this.mSubformCollapsibleChildViewsEndIndex[i1] = arrayOfInt2[localCardSubform.collapsibleFieldsEndIndex];
        if (this.mSubformCollapsibleChildViewsEndIndex[i1] == -1) {
          throw new IllegalStateException("Invalid collapsibleFieldsEndIndex");
        }
        ArrayList localArrayList3 = this.mCollapseTriggers;
        int i14 = localCardSubform.collapsibleFieldsEndIndex;
        localArrayList3.add(getAutoAdvancerFromField(i1, i14));
        localArrayList2 = new ArrayList();
        int i8 = this.mRevealBelowTriggeredCount[i1];
        i9 = 0;
        label1892:
        if (i9 >= i6) {
          break label2060;
        }
        if (localCardSubform.cardField[i9].hideFieldsBelow)
        {
          if (i8 <= 0) {
            break label1947;
          }
          i8--;
        }
      }
      for (;;)
      {
        i9++;
        break label1892;
        this.mSubformCollapsibleChildViewsEndIndex[i1] = -1;
        this.mCollapseTriggers.add(null);
        break;
        label1947:
        final int i10 = i1;
        final int i11 = arrayOfInt2[i9];
        if (i11 == -1) {
          throw new IllegalStateException("Invalid hideFieldsBelow field");
        }
        getAutoAdvancerFromField(i1, i9).addOnAutoAdvanceListener(new OnAutoAdvanceListener()
        {
          public final void onAutoAdvance(View paramAnonymousView)
          {
            CardFragment.access$000(CardFragment.this, i10, i11);
          }
        });
        if (localArrayList2.isEmpty())
        {
          int i12 = i11 + 1;
          int i13 = localLinearLayout.getChildCount();
          while (i12 < i13)
          {
            localLinearLayout.getChildAt(i12).setVisibility(8);
            i12++;
          }
        }
        localArrayList2.add(Integer.valueOf(i11));
      }
      label2060:
      this.mRevealBelowTriggerViewIndices.add(localArrayList2);
      localLinearLayout.setVisibility(8);
      this.mRootView.addView(localLinearLayout);
      i1++;
      break;
      label2092:
      if (bool) {
        switchToOneCardMode();
      }
      if (((CardFormOuterClass.CardForm)this.mFormProto).logoUiPreference == 3)
      {
        this.mCardLogoGridToggle.setVisibility(0);
        this.mCardLogoGridToggle.setOnClickListener(this);
        int i2 = arrayOfImageWithCaption1.length;
        int i4;
        for (int i3 = 0; i3 < i2; i3 = i4)
        {
          i4 = Math.min(i3 + 5, i2);
          CreditCardImagesView localCreditCardImagesView2 = (CreditCardImagesView)this.mThemedInflater.inflate(R.layout.view_card_logo_grid_row, this.mCardLogoGrid, false);
          this.mNextViewId = localCreditCardImagesView2.setCardIcons((ImageWithCaptionOuterClass.ImageWithCaption[])Arrays.copyOfRange(arrayOfImageWithCaption1, i3, i4, [Lcom.google.commerce.payments.orchestration.proto.ui.common.generic.ImageWithCaptionOuterClass.ImageWithCaption.class), ImageWithCaptionOuterClass.ImageWithCaption.emptyArray(), false, this.mNextViewId);
          this.mCardLogoGrid.addView(localCreditCardImagesView2);
        }
        if ((paramBundle != null) && (paramBundle.getBoolean("cardLogoGridVisible")))
        {
          this.mCardLogoGrid.setVisibility(0);
          this.mCardLogoGridToggle.setText(getString(R.string.wallet_im_hide_card_logos));
        }
      }
      doEnableUi();
      notifyFormEvent(1, Bundle.EMPTY);
      this.mCardNumberTextWatcher = new TextWatcher()
      {
        public final void afterTextChanged(Editable paramAnonymousEditable) {}
        
        public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
        
        public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
        {
          if (CardFragment.this.mCreditCardEntryAction.panEntryType != 1)
          {
            CardFragment.this.mCreditCardEntryAction.panEntryType = 1;
            CardFragment.this.sendCreditCardEntryActionBackgroundEvent();
          }
        }
      };
      this.mExpDateTextWatcher = new TextWatcher()
      {
        public final void afterTextChanged(Editable paramAnonymousEditable) {}
        
        public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
        
        public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
        {
          if (CardFragment.this.mCreditCardEntryAction.expDateEntryType != 1)
          {
            CardFragment.this.mCreditCardEntryAction.expDateEntryType = 1;
            CardFragment.this.sendCreditCardEntryActionBackgroundEvent();
          }
        }
      };
      this.mCardHolderNameTextWatcher = new TextWatcher()
      {
        public final void afterTextChanged(Editable paramAnonymousEditable) {}
        
        public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
        
        public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
        {
          if (CardFragment.this.mCreditCardEntryAction.nameEntryType != 1)
          {
            CardFragment.this.mCreditCardEntryAction.nameEntryType = 1;
            CardFragment.this.sendCreditCardEntryActionBackgroundEvent();
          }
        }
      };
      return this.mRootView;
    }
  }
  
  public final void onDestroyView()
  {
    this.mSubformUiNodes.clear();
    this.mCardFormFieldDataList.clear();
    this.mNextViewId = 1;
    this.mSubforms.clear();
    this.mUiFields.clear();
    this.mAddressFragments.clear();
    this.mOtpFields.clear();
    this.mLegalMessages.clear();
    super.onDestroyView();
  }
  
  public final void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    int i = ((DropDownItem)this.mCardNumberText.getAdapter().getItem(paramInt)).eventType;
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
    while ((paramInt == 1) && (paramCreditCardNfcResult != null))
    {
      populateCardInfo(paramCreditCardNfcResult.cardNumber, paramCreditCardNfcResult.expMonth, paramCreditCardNfcResult.expYear, paramCreditCardNfcResult.cardHolderName, paramLong, 3);
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
    populateCardInfo("", 0, -1, "", paramLong, 3);
  }
  
  public final void onPanCategoryChanged(CardFormOuterClass.PanCategory paramPanCategory)
  {
    int i;
    int j;
    label20:
    int k;
    int m;
    label47:
    int n;
    label59:
    View localView;
    if (this.mUnresolvedView.getVisibility() == 8)
    {
      i = 1;
      if (paramPanCategory == null) {
        break label111;
      }
      j = 1;
      k = i ^ j;
      if ((k == 0) || (((CardFormOuterClass.CardForm)this.mFormProto).logoUiPreference != 3)) {
        break label116;
      }
      m = 1;
      n = 0;
      int i1 = this.mSubforms.size();
      if (n >= i1) {
        break label132;
      }
      localView = (View)this.mSubforms.get(n);
      if (localView.getVisibility() == 0)
      {
        if (k == 0) {
          break label122;
        }
        WalletUiUtils.animateViewDisappearingToGone(localView, 0, 0);
      }
    }
    for (;;)
    {
      n++;
      break label59;
      i = 0;
      break;
      label111:
      j = 0;
      break label20;
      label116:
      m = 0;
      break label47;
      label122:
      localView.setVisibility(8);
    }
    label132:
    if (this.mCurrentCollapseTrigger != null)
    {
      this.mCurrentCollapseTrigger.removeOnAutoAdvanceListener(this);
      this.mCurrentCollapseTrigger = null;
    }
    if (paramPanCategory == null)
    {
      this.mCardImagesView.setCardIcon(null);
      if (this.mUnresolvedView.getVisibility() != 0)
      {
        if (m == 0) {
          break label204;
        }
        WalletUiUtils.animateViewAppearing(this.mUnresolvedView, 0, 0);
      }
      for (;;)
      {
        notifyFormEvent(1, Bundle.EMPTY);
        AnalyticsUtil.createAndSendImpressionEvent(this, -1);
        return;
        label204:
        this.mUnresolvedView.setVisibility(0);
      }
    }
    this.mCardImagesView.setCardIcon(((CardFormOuterClass.CardForm)this.mFormProto).logo[paramPanCategory.logoIndex]);
    label251:
    int i3;
    int i5;
    label277:
    int i6;
    ViewGroup localViewGroup;
    if (m != 0)
    {
      WalletUiUtils.animateViewDisappearingToGone(this.mUnresolvedView, 0, 0);
      int i2 = this.mCardNumberText.getId();
      i3 = 0;
      int[] arrayOfInt = paramPanCategory.cardSubformIndex;
      int i4 = arrayOfInt.length;
      i5 = 0;
      if (i5 < i4)
      {
        i6 = arrayOfInt[i5];
        if ((i6 == paramPanCategory.cardSubformIndex[0]) && (this.mCollapseTriggers.get(i6) != null) && (Build.VERSION.SDK_INT >= 14))
        {
          this.mCurrentCollapseTrigger = ((AutoAdvancer)this.mCollapseTriggers.get(i6));
          this.mCurrentCollapseTrigger.addOnAutoAdvanceListener(this);
        }
        localViewGroup = (ViewGroup)this.mSubforms.get(i6);
        ((RelativeLayout.LayoutParams)localViewGroup.getLayoutParams()).addRule(WalletUiUtils.sanitizeRelativeLayoutVerb(3), i2);
        i2 = localViewGroup.getId();
        if (i3 == 0)
        {
          if (k == 0) {
            break label444;
          }
          WalletUiUtils.animateViewAppearing(localViewGroup, 0, 0);
        }
      }
    }
    for (;;)
    {
      if (!((List)this.mRevealBelowTriggerViewIndices.get(i6)).isEmpty()) {
        i3 = 1;
      }
      i5++;
      break label277;
      break;
      this.mUnresolvedView.setVisibility(8);
      break label251;
      label444:
      localViewGroup.setVisibility(0);
    }
  }
  
  public final void onPause()
  {
    super.onPause();
    this.mNfcService.pause();
    this.mNfcAdapterEnabledWhenLastPaused = this.mNfcService.isAdapterEnabled();
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
  
  public final void onResume()
  {
    super.onResume();
    this.mNfcService.resume();
    boolean bool = this.mNfcService.isAdapterEnabled();
    if ((!this.mNfcService.isReadInProgress()) && (!this.mNfcAdapterEnabledWhenLastPaused) && (bool))
    {
      closeDialogFragmentIfOpen("tagNfcInfoDialog");
      showNfcInfoDialog();
    }
    this.mCardNumberText.showDropDownIfNecessary();
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
    if (this.mCardLogoGrid.getVisibility() == 0) {}
    for (boolean bool = true;; bool = false)
    {
      paramBundle.putBoolean("cardLogoGridVisible", bool);
      paramBundle.putIntArray("revealBelowTriggeredCount", this.mRevealBelowTriggeredCount);
      paramBundle.putParcelable("creditCardEntryAction", this.mCreditCardEntryAction);
      return;
    }
  }
  
  public final void onViewStateRestored(Bundle paramBundle)
  {
    super.onViewStateRestored(paramBundle);
    if (paramBundle != null) {
      transitionToState(paramBundle.getInt("viewState", 1), false);
    }
    this.mCardNumberText.addTextChangedListener(this.mCardNumberTextWatcher);
    ArrayList localArrayList1 = getTargetViewsFromUiFields(3);
    int i = 0;
    int j = localArrayList1.size();
    while (i < j)
    {
      View localView2 = (View)localArrayList1.get(i);
      if ((localView2 instanceof TextView)) {
        ((TextView)localView2).addTextChangedListener(this.mExpDateTextWatcher);
      }
      i++;
    }
    ArrayList localArrayList2 = getTargetViewsFromUiFields(1);
    int k = 0;
    int m = localArrayList2.size();
    while (k < m)
    {
      View localView1 = (View)localArrayList2.get(k);
      if ((localView1 instanceof TextView)) {
        ((TextView)localView1).addTextChangedListener(this.mCardHolderNameTextWatcher);
      }
      k++;
    }
  }
  
  public final boolean validate(int[] paramArrayOfInt)
  {
    boolean bool1 = super.validate(paramArrayOfInt);
    boolean bool2 = this.mCreditCardEntryAction.panValidationErrorOccurred;
    int i = 0;
    if (!bool2)
    {
      boolean bool3 = TextUtils.isEmpty(this.mCardNumberText.getError());
      i = 0;
      if (!bool3)
      {
        this.mCreditCardEntryAction.panValidationErrorOccurred = true;
        i = 1;
      }
    }
    View localView1 = getTargetViewFromUiFieldsInPanCategory(3);
    if ((!this.mCreditCardEntryAction.expDateValidationErrorOccurred) && (localView1 != null) && (!TextUtils.isEmpty(WalletUiUtils.getUiFieldError(localView1))))
    {
      this.mCreditCardEntryAction.expDateValidationErrorOccurred = true;
      i = 1;
    }
    View localView2 = getTargetViewFromUiFieldsInPanCategory(1);
    if ((!this.mCreditCardEntryAction.nameValidationErrorOccurred) && (localView2 != null) && (!TextUtils.isEmpty(WalletUiUtils.getUiFieldError(localView2))))
    {
      this.mCreditCardEntryAction.nameValidationErrorOccurred = true;
      i = 1;
    }
    if (i != 0) {
      sendCreditCardEntryActionBackgroundEvent();
    }
    if (!bool1) {
      expandWithoutAnimationIfCollapsed();
    }
    return bool1;
  }
  
  private static final class CardFormFieldData
  {
    public int mFieldType;
    public int mIndexInFieldType;
    int mSemanticHint;
    int mUiReference;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.instrumentmanager.ui.card.CardFragment
 * JD-Core Version:    0.7.0.1
 */