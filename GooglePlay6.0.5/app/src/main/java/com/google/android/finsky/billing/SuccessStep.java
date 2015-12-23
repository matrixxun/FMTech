package com.google.android.finsky.billing;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.billing.lightpurchase.multistep.MultiStepFragment;
import com.google.android.finsky.billing.lightpurchase.multistep.StepFragment;
import com.google.android.finsky.protos.Acquisition.AutoDismissTemplate;
import com.google.android.finsky.protos.Acquisition.ComplexTemplate;
import com.google.android.finsky.protos.Acquisition.IconMessage;
import com.google.android.finsky.protos.Acquisition.PostAcquisitionPrompt;
import com.google.android.finsky.protos.Acquisition.PostSuccessAction;
import com.google.android.finsky.protos.Acquisition.SetupWizardTemplate;
import com.google.android.finsky.protos.Acquisition.SimpleMessageTemplate;
import com.google.android.finsky.protos.Acquisition.TitledTemplate;
import com.google.android.finsky.protos.Acquisition.TwoIconMessagesTemplate;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.utils.UrlSpanUtils;
import com.google.android.play.utils.UrlSpanUtils.Listener;

public abstract class SuccessStep<T extends MultiStepFragment>
  extends StepFragment<T>
  implements UrlSpanUtils.Listener
{
  protected int mAutoDismissMs = -1;
  public String mContinueButtonLabel;
  private final Runnable mFinishRunnable = new Runnable()
  {
    public final void run()
    {
      SuccessStep.this.performSuccessActionAndFinish();
    }
  };
  private final Handler mHandler = new Handler();
  public View mMainView;
  public String mSecondaryButtonLabel;
  public Acquisition.PostSuccessAction mSecondaryPostSuccessAction;
  public String mTextToAnnounce;
  
  private void bindFifeImage(Common.Image paramImage, int paramInt)
  {
    FifeImageView localFifeImageView = (FifeImageView)this.mMainView.findViewById(paramInt);
    if (paramImage == null) {
      return;
    }
    localFifeImageView.setImage(paramImage.imageUrl, paramImage.supportsFifeUrlOptions, FinskyApp.get().mBitmapLoader);
    localFifeImageView.setVisibility(0);
  }
  
  public static Bundle createArgs(Acquisition.PostAcquisitionPrompt paramPostAcquisitionPrompt, int paramInt)
  {
    if (paramInt == 0) {
      throw new IllegalArgumentException("Cannot create a SuccessStep with layoutId of 0.");
    }
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("SuccessStep.post_acquisition_prompt", ParcelableProto.forProto(paramPostAcquisitionPrompt));
    localBundle.putInt("SuccessStep.layout_id", paramInt);
    return localBundle;
  }
  
  public static int getLayoutResId(Acquisition.PostAcquisitionPrompt paramPostAcquisitionPrompt)
  {
    if ((paramPostAcquisitionPrompt.autoDismissTemplate != null) || (paramPostAcquisitionPrompt.simpleMessageTemplate != null)) {
      return 2130968829;
    }
    if (paramPostAcquisitionPrompt.complexTemplate != null) {
      return 2130968665;
    }
    if (paramPostAcquisitionPrompt.titledTemplate != null) {
      return 2130969136;
    }
    if (paramPostAcquisitionPrompt.setupWizardTemplate != null) {
      return 2130969113;
    }
    if (paramPostAcquisitionPrompt.twoIconMessagesTemplate != null) {
      return 2130969140;
    }
    return 0;
  }
  
  public final void bindHtmlMessage(CharSequence paramCharSequence, int paramInt)
  {
    TextView localTextView = (TextView)this.mMainView.findViewById(paramInt);
    localTextView.setText(paramCharSequence);
    localTextView.setMovementMethod(LinkMovementMethod.getInstance());
    localTextView.setLinkTextColor(localTextView.getTextColors());
  }
  
  public void bindSetupWizardTemplate(Acquisition.SetupWizardTemplate paramSetupWizardTemplate)
  {
    throw new UnsupportedOperationException("Setup Wizard template is not supported outside of the Setup Wizard.");
  }
  
  public void bindTwoIconMessagesTemplate(Acquisition.TwoIconMessagesTemplate paramTwoIconMessagesTemplate)
  {
    if (paramTwoIconMessagesTemplate.iconMessage1 == null) {
      throw new IllegalArgumentException("Two icon messages template must have at least one message.");
    }
    if (TextUtils.isEmpty(paramTwoIconMessagesTemplate.buttonLabel)) {
      throw new IllegalArgumentException("Two icon messages template must have a button label.");
    }
    Spanned localSpanned = Html.fromHtml(paramTwoIconMessagesTemplate.iconMessage1.messageHtml);
    bindHtmlMessage(localSpanned, 2131756167);
    bindFifeImage(paramTwoIconMessagesTemplate.iconMessage1.icon, 2131756166);
    if (paramTwoIconMessagesTemplate.iconMessage2 != null)
    {
      bindHtmlMessage(Html.fromHtml(paramTwoIconMessagesTemplate.iconMessage2.messageHtml), 2131756170);
      bindFifeImage(paramTwoIconMessagesTemplate.iconMessage2.icon, 2131756169);
      this.mMainView.findViewById(2131756168).setVisibility(0);
      UrlSpanUtils.selfishifyUrlSpans(((TextView)this.mMainView.findViewById(2131756170)).getText(), null, this);
    }
    this.mTextToAnnounce = localSpanned.toString();
    this.mContinueButtonLabel = paramTwoIconMessagesTemplate.buttonLabel;
    this.mSecondaryButtonLabel = paramTwoIconMessagesTemplate.secondaryButtonLabel;
    this.mSecondaryPostSuccessAction = paramTwoIconMessagesTemplate.secondaryPostSuccessAction;
  }
  
  public final String getContinueButtonLabel(Resources paramResources)
  {
    return this.mContinueButtonLabel;
  }
  
  public final String getSecondaryButtonLabel$469752d4()
  {
    return this.mSecondaryButtonLabel;
  }
  
  public final void onClick(View paramView, String paramString)
  {
    Context localContext = paramView.getContext();
    Uri localUri = Uri.parse(paramString);
    Intent localIntent = new Intent("android.intent.action.VIEW").addCategory("android.intent.category.DEFAULT").setPackage(localContext.getPackageName()).setData(localUri);
    if (localContext.getPackageManager().resolveActivity(localIntent, 65536) == null) {
      localIntent.setPackage(null);
    }
    startActivity(localIntent);
    ((MultiStepFragment)this.mParentFragment).getActivity().finish();
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    Bundle localBundle = this.mArguments;
    Acquisition.PostAcquisitionPrompt localPostAcquisitionPrompt = (Acquisition.PostAcquisitionPrompt)ParcelableProto.getProtoFromBundle(localBundle, "SuccessStep.post_acquisition_prompt");
    this.mMainView = paramLayoutInflater.inflate(localBundle.getInt("SuccessStep.layout_id"), paramViewGroup, false);
    if (localPostAcquisitionPrompt.autoDismissTemplate != null)
    {
      Acquisition.AutoDismissTemplate localAutoDismissTemplate = localPostAcquisitionPrompt.autoDismissTemplate;
      if (TextUtils.isEmpty(localAutoDismissTemplate.messageHtml)) {
        throw new IllegalArgumentException("Auto-dismiss template must have a message.");
      }
      if (localAutoDismissTemplate.dismissMillisecond <= 0)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(localAutoDismissTemplate.dismissMillisecond);
        throw new IllegalArgumentException(String.format("Invalid dismiss ms for auto-dismiss template: %d", arrayOfObject));
      }
      Spanned localSpanned2 = Html.fromHtml(localAutoDismissTemplate.messageHtml);
      bindHtmlMessage(localSpanned2, 2131755233);
      this.mTextToAnnounce = localSpanned2.toString();
      this.mAutoDismissMs = localAutoDismissTemplate.dismissMillisecond;
    }
    for (;;)
    {
      ((MultiStepFragment)this.mParentFragment).restoreUi();
      return this.mMainView;
      if (localPostAcquisitionPrompt.simpleMessageTemplate != null)
      {
        Acquisition.SimpleMessageTemplate localSimpleMessageTemplate = localPostAcquisitionPrompt.simpleMessageTemplate;
        if (TextUtils.isEmpty(localSimpleMessageTemplate.messageHtml)) {
          throw new IllegalArgumentException("Simple message template must have a message.");
        }
        if (TextUtils.isEmpty(localSimpleMessageTemplate.buttonLabel)) {
          throw new IllegalArgumentException("Simple message template must have a button label.");
        }
        Spanned localSpanned1 = Html.fromHtml(localSimpleMessageTemplate.messageHtml);
        bindHtmlMessage(localSpanned1, 2131755233);
        this.mTextToAnnounce = localSpanned1.toString();
        this.mContinueButtonLabel = localSimpleMessageTemplate.buttonLabel;
      }
      else if (localPostAcquisitionPrompt.titledTemplate != null)
      {
        Acquisition.TitledTemplate localTitledTemplate = localPostAcquisitionPrompt.titledTemplate;
        if (TextUtils.isEmpty(localTitledTemplate.title)) {
          throw new IllegalArgumentException("Titled template must have a title.");
        }
        if (TextUtils.isEmpty(localTitledTemplate.messageHtml)) {
          throw new IllegalArgumentException("Titled template must have a message.");
        }
        if (TextUtils.isEmpty(localTitledTemplate.buttonLabel)) {
          throw new IllegalArgumentException("Titled template must have a button label.");
        }
        ((TextView)this.mMainView.findViewById(2131755173)).setText(localTitledTemplate.title);
        bindHtmlMessage(Html.fromHtml(localTitledTemplate.messageHtml), 2131755233);
        this.mTextToAnnounce = localTitledTemplate.title;
        this.mContinueButtonLabel = localTitledTemplate.buttonLabel;
      }
      else if (localPostAcquisitionPrompt.complexTemplate != null)
      {
        Acquisition.ComplexTemplate localComplexTemplate = localPostAcquisitionPrompt.complexTemplate;
        if (TextUtils.isEmpty(localComplexTemplate.title)) {
          throw new IllegalArgumentException("Complex template must have a title.");
        }
        if (TextUtils.isEmpty(localComplexTemplate.buttonLabel)) {
          throw new IllegalArgumentException("Complex template must have a button label.");
        }
        ((TextView)this.mMainView.findViewById(2131755173)).setText(localComplexTemplate.title);
        if (!TextUtils.isEmpty(localComplexTemplate.titleByline)) {
          ((TextView)this.mMainView.findViewById(2131755338)).setText(localComplexTemplate.titleByline);
        }
        bindHtmlMessage(Html.fromHtml(localComplexTemplate.messageHtml), 2131755233);
        if (localComplexTemplate.showCheckmark) {
          this.mMainView.findViewById(2131755337).setVisibility(0);
        }
        bindFifeImage(localComplexTemplate.thumbnailImage, 2131755335);
        this.mTextToAnnounce = localComplexTemplate.title;
        this.mContinueButtonLabel = localComplexTemplate.buttonLabel;
      }
      else if (localPostAcquisitionPrompt.setupWizardTemplate != null)
      {
        bindSetupWizardTemplate(localPostAcquisitionPrompt.setupWizardTemplate);
      }
      else if (localPostAcquisitionPrompt.twoIconMessagesTemplate != null)
      {
        bindTwoIconMessagesTemplate(localPostAcquisitionPrompt.twoIconMessagesTemplate);
      }
    }
  }
  
  public final void onDestroy()
  {
    this.mHandler.removeCallbacks(this.mFinishRunnable);
    super.onDestroy();
  }
  
  public final void onResume()
  {
    super.onResume();
    if (this.mTextToAnnounce == null) {
      throw new IllegalArgumentException("No text was specified for a11y announcements.");
    }
    UiUtils.sendAccessibilityEventWithText(this.mMainView.getContext(), this.mTextToAnnounce, this.mMainView);
  }
  
  public final void onStart()
  {
    super.onStart();
    if (this.mAutoDismissMs > 0) {
      this.mHandler.postDelayed(this.mFinishRunnable, this.mAutoDismissMs);
    }
  }
  
  public abstract void performSuccessActionAndFinish();
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.SuccessStep
 * JD-Core Version:    0.7.0.1
 */