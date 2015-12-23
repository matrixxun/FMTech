package com.google.android.wallet.ui.usernamepassword;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.wallet.analytics.AnalyticsClickListener;
import com.google.android.wallet.analytics.UiNode;
import com.google.android.wallet.analytics.WalletUiElement;
import com.google.android.wallet.common.util.PaypalPasswordEncryptor;
import com.google.android.wallet.ui.common.BaseWalletUiComponentFragment;
import com.google.android.wallet.ui.common.ClickSpan.OnClickListener;
import com.google.android.wallet.ui.common.FormEditText;
import com.google.android.wallet.ui.common.FormFragment;
import com.google.android.wallet.ui.common.FormFragment.FieldData;
import com.google.android.wallet.ui.common.InfoMessageTextView;
import com.google.android.wallet.ui.common.WalletUiUtils;
import com.google.android.wallet.ui.common.WebViewDialogFragment;
import com.google.android.wallet.uicomponents.R.attr;
import com.google.android.wallet.uicomponents.R.id;
import com.google.commerce.payments.orchestration.proto.ui.common.FormFieldReferenceOuterClass.FormFieldReference;
import com.google.commerce.payments.orchestration.proto.ui.common.UiErrorOuterClass.FormFieldMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.UsernamePassword.UsernamePasswordForm;
import com.google.commerce.payments.orchestration.proto.ui.common.components.instrument.types.UsernamePassword.UsernamePasswordFormValue;
import com.google.commerce.payments.orchestration.proto.ui.common.components.legal.LegalMessageOuterClass.LegalMessage;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiField;
import com.google.commerce.payments.orchestration.proto.ui.common.generic.UiFieldOuterClass.UiFieldValue;
import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.crypto.SecretKey;

public class UsernamePasswordFragment
  extends FormFragment<UsernamePassword.UsernamePasswordForm>
  implements TextWatcher, View.OnClickListener, ClickSpan.OnClickListener
{
  public AnalyticsClickListener mAnalyticsClickListener;
  ArrayList<FormFragment.FieldData<FormEditText>> mFieldData = new ArrayList();
  InfoMessageTextView mLegalMessageText;
  TextView mLoginHelpText;
  FormEditText mPasswordText;
  private final WalletUiElement mUiElement = new WalletUiElement(1680);
  FormEditText mUsernameText;
  
  public void afterTextChanged(Editable paramEditable)
  {
    notifyFormEvent(1, Bundle.EMPTY);
  }
  
  public final boolean applyFormFieldMessage(UiErrorOuterClass.FormFieldMessage paramFormFieldMessage)
  {
    if (!paramFormFieldMessage.formFieldReference.formId.equals(((UsernamePassword.UsernamePasswordForm)this.mFormProto).id)) {
      return false;
    }
    switch (paramFormFieldMessage.formFieldReference.fieldId)
    {
    default: 
      throw new IllegalArgumentException("Unknown FormFieldMessage fieldId: " + paramFormFieldMessage.formFieldReference.fieldId);
    case 1: 
      this.mUsernameText.setError(paramFormFieldMessage.message);
    }
    for (;;)
    {
      return true;
      this.mPasswordText.setError(paramFormFieldMessage.message);
    }
  }
  
  public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {}
  
  public final void doEnableUi()
  {
    if (this.mUsernameText != null)
    {
      boolean bool = this.mUiEnabled;
      this.mUsernameText.setEnabled(bool);
      this.mPasswordText.setEnabled(bool);
    }
  }
  
  public final String getButtonBarExpandButtonText()
  {
    return this.mLegalMessageText.getExpandLabel();
  }
  
  public List<UiNode> getChildren()
  {
    if (((UsernamePassword.UsernamePasswordForm)this.mFormProto).legalMessage != null) {
      return Collections.singletonList(this.mLegalMessageText);
    }
    return null;
  }
  
  public final List<FormFragment.FieldData<FormEditText>> getFieldsForValidationInOrder()
  {
    return this.mFieldData;
  }
  
  public WalletUiElement getUiElement()
  {
    return this.mUiElement;
  }
  
  public final UsernamePassword.UsernamePasswordFormValue getUsernamePasswordFormValue$695fcf2()
  {
    UsernamePassword.UsernamePasswordFormValue localUsernamePasswordFormValue = new UsernamePassword.UsernamePasswordFormValue();
    localUsernamePasswordFormValue.username = WalletUiUtils.createUiFieldValue(this.mUsernameText, ((UsernamePassword.UsernamePasswordForm)this.mFormProto).usernameField);
    localUsernamePasswordFormValue.password = new UiFieldOuterClass.UiFieldValue();
    localUsernamePasswordFormValue.password.name = ((UsernamePassword.UsernamePasswordForm)this.mFormProto).passwordField.name;
    switch (((UsernamePassword.UsernamePasswordForm)this.mFormProto).encryptionType)
    {
    default: 
      throw new IllegalArgumentException("Unsupported encryption type: " + ((UsernamePassword.UsernamePasswordForm)this.mFormProto).encryptionType);
    case 2: 
      PaypalPasswordEncryptor localPaypalPasswordEncryptor = new PaypalPasswordEncryptor(getActivity(), ((UsernamePassword.UsernamePasswordForm)this.mFormProto).vendorSpecificSalt);
      UiFieldOuterClass.UiFieldValue localUiFieldValue;
      byte[] arrayOfByte3;
      byte[] arrayOfByte4;
      byte[] arrayOfByte5;
      int i;
      for (;;)
      {
        byte[] arrayOfByte2;
        try
        {
          localUiFieldValue = localUsernamePasswordFormValue.password;
          byte[] arrayOfByte1 = ((UsernamePassword.UsernamePasswordForm)this.mFormProto).credentialsEncryptionKey;
          String str1 = this.mPasswordText.getText().toString();
          String str2 = localPaypalPasswordEncryptor.mHashedDeviceId;
          long l = System.currentTimeMillis();
          X509Certificate localX509Certificate = (X509Certificate)CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(arrayOfByte1));
          arrayOfByte2 = localX509Certificate.getSerialNumber().toByteArray();
          if (arrayOfByte2.length < 8)
          {
            arrayOfByte3 = new byte[8];
            System.arraycopy(arrayOfByte2, 0, arrayOfByte3, 8 - arrayOfByte2.length, arrayOfByte2.length);
            SecretKey localSecretKey = PaypalPasswordEncryptor.createSessionKey();
            arrayOfByte4 = PaypalPasswordEncryptor.encryptExchangeKey(localX509Certificate, localSecretKey);
            arrayOfByte5 = PaypalPasswordEncryptor.encryptInnerMessage(localSecretKey, PaypalPasswordEncryptor.createInnerMessage(l, str2, str1));
            i = arrayOfByte4.length + arrayOfByte5.length;
            if (i <= 65535) {
              break;
            }
            throw new IllegalStateException("Encrypted message is too long: " + i);
          }
        }
        catch (CertificateException localCertificateException)
        {
          throw new IllegalStateException("Unable to encrypt user PayPal credentials", localCertificateException);
        }
        if (arrayOfByte2.length == 8)
        {
          arrayOfByte3 = arrayOfByte2;
        }
        else
        {
          arrayOfByte3 = new byte[8];
          System.arraycopy(arrayOfByte2, -8 + arrayOfByte2.length, arrayOfByte3, 0, 8);
        }
      }
      ByteBuffer localByteBuffer = ByteBuffer.allocate(i + (2 + (2 + arrayOfByte3.length)));
      localByteBuffer.order(ByteOrder.BIG_ENDIAN);
      localByteBuffer.putShort((short)5);
      localByteBuffer.put(arrayOfByte3);
      localByteBuffer.putChar((char)i);
      localByteBuffer.put(arrayOfByte4);
      localByteBuffer.put(arrayOfByte5);
      localUiFieldValue.stringValue = Base64.encodeToString(localByteBuffer.array(), 2);
      localUsernamePasswordFormValue.hashedDeviceId = localPaypalPasswordEncryptor.mHashedDeviceId;
    }
    for (;;)
    {
      localUsernamePasswordFormValue.encryptionType = ((UsernamePassword.UsernamePasswordForm)this.mFormProto).encryptionType;
      if (((UsernamePassword.UsernamePasswordForm)this.mFormProto).legalMessage != null) {
        localUsernamePasswordFormValue.legalDocData = ((UsernamePassword.UsernamePasswordForm)this.mFormProto).legalMessage.opaqueData;
      }
      return localUsernamePasswordFormValue;
      localUsernamePasswordFormValue.password.stringValue = this.mPasswordText.getText().toString();
    }
  }
  
  public final boolean isReadyToSubmit()
  {
    return (this.mUsernameText != null) && (!TextUtils.isEmpty(this.mUsernameText.getText())) && (this.mPasswordText != null) && (!TextUtils.isEmpty(this.mPasswordText.getText()));
  }
  
  public final void onButtonBarExpandButtonClicked()
  {
    this.mLegalMessageText.expand(true);
  }
  
  public void onClick(View paramView)
  {
    if ((paramView == this.mLoginHelpText) && (this.mAnalyticsClickListener != null)) {
      this.mAnalyticsClickListener.onAnalyticsClickEvent(this, 1681);
    }
  }
  
  public final void onClick(View paramView, String paramString)
  {
    if ((paramView != this.mLegalMessageText) || (this.mFragmentManager.findFragmentByTag("tagTosWebViewDialog") != null)) {
      return;
    }
    WebViewDialogFragment.newInstance(paramString, this.mThemeResourceId).show(this.mFragmentManager, "tagTosWebViewDialog");
  }
  
  protected final View onCreateThemedView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    ContextThemeWrapper localContextThemeWrapper = this.mThemedContext;
    int[] arrayOfInt = new int[1];
    arrayOfInt[0] = R.attr.internalUicUsernamePasswordRootLayout;
    TypedArray localTypedArray = localContextThemeWrapper.obtainStyledAttributes(arrayOfInt);
    int i = localTypedArray.getResourceId(0, -1);
    localTypedArray.recycle();
    if (i == -1) {
      throw new IllegalArgumentException("Attribute internalUicUsernamePasswordRootLayout must be defined.");
    }
    ViewGroup localViewGroup = (ViewGroup)paramLayoutInflater.inflate(i, null, false);
    if (!TextUtils.isEmpty(((UsernamePassword.UsernamePasswordForm)this.mFormProto).title))
    {
      TextView localTextView = (TextView)localViewGroup.findViewById(R.id.username_password_title);
      localTextView.setText(((UsernamePassword.UsernamePasswordForm)this.mFormProto).title);
      localTextView.setVisibility(0);
    }
    this.mUsernameText = ((FormEditText)localViewGroup.findViewById(R.id.username));
    WalletUiUtils.applyUiFieldSpecificationToFormEditText(((UsernamePassword.UsernamePasswordForm)this.mFormProto).usernameField, this.mUsernameText);
    this.mUsernameText.addTextChangedListener(this);
    this.mFieldData.add(new FormFragment.FieldData(((UsernamePassword.UsernamePasswordForm)this.mFormProto).usernameField.uiReference, this.mUsernameText, WalletUiUtils.getInitialValue(((UsernamePassword.UsernamePasswordForm)this.mFormProto).usernameField)));
    this.mPasswordText = ((FormEditText)localViewGroup.findViewById(R.id.password));
    WalletUiUtils.applyUiFieldSpecificationToFormEditText(((UsernamePassword.UsernamePasswordForm)this.mFormProto).passwordField, this.mPasswordText);
    this.mPasswordText.addTextChangedListener(this);
    this.mFieldData.add(new FormFragment.FieldData(((UsernamePassword.UsernamePasswordForm)this.mFormProto).passwordField.uiReference, this.mPasswordText, WalletUiUtils.getInitialValue(((UsernamePassword.UsernamePasswordForm)this.mFormProto).passwordField)));
    this.mLoginHelpText = ((TextView)localViewGroup.findViewById(R.id.login_help_text));
    if (TextUtils.isEmpty(((UsernamePassword.UsernamePasswordForm)this.mFormProto).loginHelpHtml)) {
      this.mLoginHelpText.setVisibility(8);
    }
    for (;;)
    {
      this.mLegalMessageText = ((InfoMessageTextView)localViewGroup.findViewById(R.id.legal_message_text));
      this.mLegalMessageText.setParentUiNode(this);
      this.mLegalMessageText.setUrlClickListener(this);
      if (((UsernamePassword.UsernamePasswordForm)this.mFormProto).legalMessage != null) {
        this.mLegalMessageText.setInfoMessage(((UsernamePassword.UsernamePasswordForm)this.mFormProto).legalMessage.messageText);
      }
      doEnableUi();
      notifyFormEvent(1, Bundle.EMPTY);
      return localViewGroup;
      this.mLoginHelpText.setText(Html.fromHtml(((UsernamePassword.UsernamePasswordForm)this.mFormProto).loginHelpHtml));
      this.mLoginHelpText.setMovementMethod(LinkMovementMethod.getInstance());
      this.mLoginHelpText.setOnClickListener(this);
    }
  }
  
  public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {}
  
  public final void onViewStateRestored(Bundle paramBundle)
  {
    super.onViewStateRestored(paramBundle);
    notifyFormEvent(6, Bundle.EMPTY);
  }
  
  public final boolean shouldShowButtonBarExpandButton()
  {
    return (!this.mLegalMessageText.mInlineExpandLabel) && (!this.mLegalMessageText.mExpanded);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.usernamepassword.UsernamePasswordFragment
 * JD-Core Version:    0.7.0.1
 */