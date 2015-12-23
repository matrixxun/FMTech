package com.google.android.finsky.billing.lightpurchase.purchasesteps;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.DfeUtils;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.billing.lightpurchase.CheckoutPurchaseSidecar;
import com.google.android.finsky.billing.lightpurchase.GiftEmailParams;
import com.google.android.finsky.billing.lightpurchase.GiftEmailParams.Builder;
import com.google.android.finsky.billing.lightpurchase.PurchaseFragment;
import com.google.android.finsky.billing.lightpurchase.multistep.StepFragment;
import com.google.android.finsky.config.G;
import com.google.android.finsky.layout.LabelEditText;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.protos.Common.Offer;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.utils.config.GservicesValue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GiftEmailStep
  extends StepFragment<PurchaseFragment>
  implements Response.ErrorListener, OnDataChangedListener, PlayStoreUiElementNode
{
  private static final int MAX_MESSAGE_LENGTH = ((Integer)G.giftMessageMaxCharacterCount.get()).intValue();
  private String mAccountName;
  private int mBackend;
  private DfeDetails mDfeDetails;
  private String mFullDocid;
  private String mGiftMessage;
  private EditText mGiftMessageField;
  private View mMainView;
  private String mOfferId;
  private String mOfferTitle;
  private TextView mOfferTitleView;
  private String mRecipientEmailAddress;
  private LabelEditText mRecipientEmailAddressField;
  private String mSenderName;
  private LabelEditText mSenderNameField;
  private String mTitle;
  private TextView mTitleView;
  private final PlayStore.PlayStoreUiElement mUiElement = FinskyEventLog.obtainPlayStoreUiElement(1310);
  
  public static GiftEmailStep newInstance(String paramString1, int paramInt, String paramString2, String paramString3, GiftEmailParams paramGiftEmailParams)
  {
    Bundle localBundle = new Bundle();
    GiftEmailStep localGiftEmailStep = new GiftEmailStep();
    localBundle.putString("GiftEmailStep.accountName", paramString1);
    localBundle.putInt("GiftEmailStep.backend", paramInt);
    localBundle.putString("GiftEmailStep.fullDocid", paramString2);
    localBundle.putString("GiftEmailStep.offerId", paramString3);
    if (paramGiftEmailParams != null)
    {
      localBundle.putString("GiftEmailStep.recipientEmailAddress", paramGiftEmailParams.recipientEmailAddress);
      localBundle.putString("GiftEmailStep.senderName", paramGiftEmailParams.senderName);
      localBundle.putString("GiftEmailStep.giftMessage", paramGiftEmailParams.giftMessage);
    }
    localGiftEmailStep.setArguments(localBundle);
    return localGiftEmailStep;
  }
  
  private void setItemText()
  {
    this.mTitleView.setText(this.mTitle);
    if (this.mOfferTitle != null) {
      this.mOfferTitleView.setText(this.mOfferTitle);
    }
  }
  
  public final String getContinueButtonLabel(Resources paramResources)
  {
    return paramResources.getString(2131362062);
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElement;
  }
  
  public final void onContinueButtonClicked()
  {
    this.mRecipientEmailAddress = this.mRecipientEmailAddressField.getTextValue();
    this.mSenderName = this.mSenderNameField.getTextValue();
    this.mGiftMessage = this.mGiftMessageField.getText().toString().trim();
    int i;
    if (TextUtils.isEmpty(this.mRecipientEmailAddress))
    {
      this.mRecipientEmailAddressField.setError(getString(2131362794));
      i = 1;
    }
    for (;;)
    {
      if (TextUtils.isEmpty(this.mSenderName))
      {
        this.mSenderNameField.setError(getString(2131362180));
        i = 1;
      }
      if (i == 0) {
        break;
      }
      return;
      boolean bool = Patterns.EMAIL_ADDRESS.matcher(this.mRecipientEmailAddress).matches();
      i = 0;
      if (!bool)
      {
        this.mRecipientEmailAddressField.setError(getString(2131362108));
        i = 1;
      }
    }
    logClick(1311, null);
    PurchaseFragment localPurchaseFragment = (PurchaseFragment)this.mParentFragment;
    GiftEmailParams.Builder localBuilder = new GiftEmailParams.Builder();
    localBuilder.mGiftMessage = this.mGiftMessage;
    localBuilder.mSenderName = this.mSenderName;
    localBuilder.mRecipientEmailAddress = this.mRecipientEmailAddress;
    GiftEmailParams localGiftEmailParams = new GiftEmailParams(localBuilder, (byte)0);
    localPurchaseFragment.mSidecar.updateGiftEmailParams(localGiftEmailParams);
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = this.mArguments;
    this.mAccountName = localBundle.getString("GiftEmailStep.accountName");
    this.mBackend = localBundle.getInt("GiftEmailStep.backend");
    this.mFullDocid = localBundle.getString("GiftEmailStep.fullDocid");
    this.mOfferId = localBundle.getString("GiftEmailStep.offerId");
    this.mRecipientEmailAddress = localBundle.getString("GiftEmailStep.recipientEmailAddress");
    this.mSenderName = localBundle.getString("GiftEmailStep.senderName");
    this.mGiftMessage = localBundle.getString("GiftEmailStep.giftMessage");
    this.mDfeDetails = new DfeDetails(FinskyApp.get().getDfeApi(this.mAccountName), DfeUtils.createDetailsUrlFromId(this.mFullDocid));
    this.mDfeDetails.addDataChangedListener(this);
    this.mDfeDetails.addErrorListener(this);
    if (paramBundle != null)
    {
      this.mTitle = paramBundle.getString("GiftEmailStep.title");
      this.mOfferTitle = paramBundle.getString("GiftEmailStep.offerTitle");
      this.mRecipientEmailAddress = paramBundle.getString("GiftEmailStep.recipientEmailAddress");
      this.mSenderName = paramBundle.getString("GiftEmailStep.senderName");
      this.mGiftMessage = paramBundle.getString("GiftEmailStep.giftMessage");
      return;
    }
    this.mRecipientEmailAddress = localBundle.getString("GiftEmailStep.recipientEmailAddress");
    this.mSenderName = localBundle.getString("GiftEmailStep.senderName");
    this.mGiftMessage = localBundle.getString("GiftEmailStep.giftMessage");
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mMainView = paramLayoutInflater.inflate(2130968818, paramViewGroup, false);
    this.mMainView.findViewById(2131755667).setBackgroundColor(CorpusResourceUtils.getStatusBarColor(getContext(), this.mBackend));
    final ScrollView localScrollView = (ScrollView)this.mMainView.findViewById(2131755669);
    localScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
    {
      public final void onGlobalLayout()
      {
        if (!GiftEmailStep.this.mResumed) {
          return;
        }
        if (localScrollView.getHeight() < localScrollView.getChildAt(0).getHeight())
        {
          UiUtils.setBackground(localScrollView, GiftEmailStep.this.getResources().getDrawable(2130837593));
          return;
        }
        UiUtils.setBackground(localScrollView, null);
      }
    });
    this.mTitleView = ((TextView)this.mMainView.findViewById(2131755635));
    this.mOfferTitleView = ((TextView)this.mMainView.findViewById(2131755668));
    TextView localTextView1 = (TextView)this.mMainView.findViewById(2131755674);
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = this.mAccountName;
    localTextView1.setText(getString(2131362189, arrayOfObject1));
    this.mRecipientEmailAddressField = ((LabelEditText)this.mMainView.findViewById(2131755670));
    this.mRecipientEmailAddressField.setTextValue(this.mRecipientEmailAddress);
    this.mSenderNameField = ((LabelEditText)this.mMainView.findViewById(2131755671));
    this.mSenderNameField.setTextValue(this.mSenderName);
    this.mGiftMessageField = ((EditText)this.mMainView.findViewById(2131755672));
    this.mGiftMessageField.setText(this.mGiftMessage);
    EditText localEditText = this.mGiftMessageField;
    InputFilter[] arrayOfInputFilter = new InputFilter[1];
    arrayOfInputFilter[0] = new InputFilter.LengthFilter(MAX_MESSAGE_LENGTH);
    localEditText.setFilters(arrayOfInputFilter);
    final TextView localTextView2 = (TextView)this.mMainView.findViewById(2131755673);
    Object[] arrayOfObject2 = new Object[2];
    if (this.mGiftMessage != null) {}
    for (int i = this.mGiftMessage.length();; i = 0)
    {
      arrayOfObject2[0] = Integer.valueOf(i);
      arrayOfObject2[1] = Integer.valueOf(MAX_MESSAGE_LENGTH);
      localTextView2.setText(getString(2131361927, arrayOfObject2));
      this.mGiftMessageField.addTextChangedListener(new TextWatcher()
      {
        public final void afterTextChanged(Editable paramAnonymousEditable)
        {
          if (GiftEmailStep.this.mGiftMessageField.hasFocus()) {
            GiftEmailStep.access$202(GiftEmailStep.this, paramAnonymousEditable.toString().trim());
          }
        }
        
        public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3) {}
        
        public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
        {
          TextView localTextView = localTextView2;
          GiftEmailStep localGiftEmailStep = GiftEmailStep.this;
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = Integer.valueOf(paramAnonymousCharSequence.length());
          arrayOfObject[1] = Integer.valueOf(GiftEmailStep.MAX_MESSAGE_LENGTH);
          localTextView.setText(localGiftEmailStep.getString(2131361927, arrayOfObject));
          if (paramAnonymousCharSequence.length() == GiftEmailStep.MAX_MESSAGE_LENGTH) {
            UiUtils.hideKeyboard(GiftEmailStep.this.getActivity(), GiftEmailStep.this.mGiftMessageField);
          }
        }
      });
      return this.mMainView;
    }
  }
  
  public final void onDataChanged()
  {
    int i = 0;
    Document localDocument = this.mDfeDetails.getDocument();
    if (localDocument == null)
    {
      FinskyLog.w("Got empty document for displaying gift item.", new Object[0]);
      return;
    }
    this.mTitle = localDocument.mDocument.title;
    label74:
    Common.Offer localOffer;
    if (localDocument.mDocument.offer.length > 1)
    {
      String str = this.mOfferId;
      if (TextUtils.isEmpty(str)) {
        break label130;
      }
      Common.Offer[] arrayOfOffer = localDocument.mDocument.offer;
      int j = arrayOfOffer.length;
      if (i >= j) {
        break label130;
      }
      localOffer = arrayOfOffer[i];
      if (!str.equals(localOffer.offerId)) {
        break label124;
      }
    }
    for (;;)
    {
      if (localOffer != null) {
        this.mOfferTitle = localOffer.formattedName;
      }
      if (!this.mResumed) {
        break;
      }
      setItemText();
      return;
      label124:
      i++;
      break label74;
      label130:
      localOffer = null;
    }
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    FinskyLog.w(paramVolleyError.getMessage(), new Object[0]);
  }
  
  public final void onResume()
  {
    super.onResume();
    UiUtils.sendAccessibilityEventWithText(this.mMainView.getContext(), getString(2131362720), this.mMainView);
    View localView = this.mMainView.findFocus();
    if (TextUtils.isEmpty(this.mRecipientEmailAddress)) {
      UiUtils.showKeyboard(getActivity(), this.mRecipientEmailAddressField);
    }
    for (;;)
    {
      setItemText();
      return;
      if (localView != null) {
        UiUtils.showKeyboard(getActivity(), (EditText)localView);
      }
    }
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putString("GiftEmailStep.title", this.mTitle);
    paramBundle.putString("GiftEmailStep.offerTitle", this.mOfferTitle);
    paramBundle.putString("GiftEmailStep.recipientEmailAddress", this.mRecipientEmailAddressField.getTextValue());
    paramBundle.putString("GiftEmailStep.senderName", this.mSenderNameField.getTextValue());
    paramBundle.putString("GiftEmailStep.giftMessage", this.mGiftMessageField.getText().toString().trim());
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.purchasesteps.GiftEmailStep
 * JD-Core Version:    0.7.0.1
 */