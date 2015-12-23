package com.google.android.finsky.billing.promptforfop;

import android.accounts.Account;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.billing.BillingProfileBaseFragment;
import com.google.android.finsky.billing.giftcard.RedeemCodeResult;
import com.google.android.finsky.billing.lightpurchase.billingprofile.FopActionEntry;
import com.google.android.finsky.billing.lightpurchase.billingprofile.instruments.RedeemCodeActivity;
import com.google.android.finsky.layout.SeparatorLinearLayout;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.protos.BillingProfileProtos.BillingProfile;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.Instrument;
import com.google.android.finsky.setup.SetupWizardNavBar;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.SetupWizardUtils;
import com.google.android.play.image.FifeImageView;
import com.google.android.play.layout.PlayActionButton;
import java.util.List;

public class PromptForFopFragment
  extends BillingProfileBaseFragment
  implements View.OnClickListener
{
  private List<FopActionEntry> mActionEntries;
  private ViewGroup mActionsView;
  private View mContinueButton;
  private boolean mHasLoggedScreen;
  private ViewGroup mMainView;
  private final View.OnClickListener mNoneOnClickListener = new View.OnClickListener()
  {
    public final void onClick(View paramAnonymousView)
    {
      PromptForFopFragment.this.mEventLog.logClickEvent(1008, null, PromptForFopFragment.this);
      PromptForFopFragment.access$100(PromptForFopFragment.this);
    }
  };
  private TextView mNotNowButton;
  private int mSelectedIndex = -1;
  private PlayStore.PlayStoreUiElement mUiElement;
  private int mUiMode;
  
  protected static Bundle buildArgumentsBundle(Account paramAccount, byte[] paramArrayOfByte)
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("BillingProfileFragment.account", paramAccount);
    localBundle.putByteArray("PromptForFopFragment.server_logs_cookie", paramArrayOfByte);
    return localBundle;
  }
  
  private Listener getListener()
  {
    if ((getActivity() instanceof Listener)) {
      return (Listener)getActivity();
    }
    FinskyLog.wtf("No listener registered.", new Object[0]);
    return null;
  }
  
  public static Fragment newInstance(Account paramAccount, byte[] paramArrayOfByte)
  {
    Bundle localBundle = buildArgumentsBundle(paramAccount, paramArrayOfByte);
    PromptForFopFragment localPromptForFopFragment = new PromptForFopFragment();
    localPromptForFopFragment.setArguments(localBundle);
    return localPromptForFopFragment;
  }
  
  private void notifyListenerOnInstrumentCreated()
  {
    Listener localListener = getListener();
    if (localListener != null) {
      localListener.onInstrumentCreated();
    }
  }
  
  private void selectItem(int paramInt)
  {
    int i = 0;
    if (i < this.mActionEntries.size())
    {
      RadioButton localRadioButton = (RadioButton)this.mMainView.findViewById(i);
      if (paramInt == i) {}
      for (boolean bool = true;; bool = false)
      {
        localRadioButton.setChecked(bool);
        i++;
        break;
      }
    }
    this.mSelectedIndex = paramInt;
    this.mContinueButton.setEnabled(true);
  }
  
  protected void configureContinueButtonStyle(View paramView)
  {
    if ((paramView instanceof PlayActionButton))
    {
      ((PlayActionButton)paramView).configure(3, 2131362062, this);
      return;
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramView.getClass().getSimpleName();
    FinskyLog.wtf("Unexpected continue button type: %s", arrayOfObject);
  }
  
  protected int determineUiMode()
  {
    return 1;
  }
  
  protected int getActionEntryLayout()
  {
    return 2130969028;
  }
  
  protected final byte[] getBackgroundEventServerLogsCookie()
  {
    return this.mArguments.getByteArray("PromptForFopFragment.server_logs_cookie");
  }
  
  protected int getBillingProfileRequestEnum()
  {
    return 2;
  }
  
  protected int getCreditCardEventType()
  {
    return 350;
  }
  
  protected int getDcbEventType()
  {
    return 351;
  }
  
  protected int getGenericInstrumentEventType()
  {
    return 357;
  }
  
  protected int getMainLayout()
  {
    return 2130969026;
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return (PlayStoreUiElementNode)getActivity();
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElement;
  }
  
  protected int getPlayStoreUiElementType()
  {
    return 1002;
  }
  
  protected int getPrimerStringId()
  {
    return 2131362731;
  }
  
  protected final Intent getRedeemCodeIntent()
  {
    return RedeemCodeActivity.createIntent(this.mAccount.name, 2);
  }
  
  protected int getRedeemEventType()
  {
    return 352;
  }
  
  protected int getTopupEventType()
  {
    return 353;
  }
  
  protected final void logLoading()
  {
    this.mEventLog.logPathImpression$7d139cbf(213, (PlayStoreUiElementNode)getActivity());
  }
  
  protected final void logScreen()
  {
    if (this.mHasLoggedScreen) {}
    do
    {
      return;
      this.mHasLoggedScreen = true;
      for (int i = 0; i < this.mActionEntries.size(); i++)
      {
        FopActionEntry localFopActionEntry = (FopActionEntry)this.mActionEntries.get(i);
        this.mEventLog.logPathImpression(0L, localFopActionEntry.playStoreUiElementType, localFopActionEntry.serverLogsCookie, this);
      }
    } while (this.mUiMode == 2);
    this.mEventLog.logPathImpression$7d139cbf(1008, this);
  }
  
  public void onClick(View paramView)
  {
    if (this.mSelectedIndex >= 0) {
      ((FopActionEntry)this.mActionEntries.get(this.mSelectedIndex)).action.onClick(paramView);
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mUiMode = determineUiMode();
    this.mUiElement = FinskyEventLog.obtainPlayStoreUiElement(getPlayStoreUiElementType());
    byte[] arrayOfByte = this.mArguments.getByteArray("PromptForFopFragment.server_logs_cookie");
    FinskyEventLog.setServerLogCookie(this.mUiElement, arrayOfByte);
    if (paramBundle != null) {
      this.mHasLoggedScreen = paramBundle.getBoolean("PromptForFopFragment.has_logged_screen");
    }
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mMainView = ((ViewGroup)paramLayoutInflater.inflate(getMainLayout(), paramViewGroup, false));
    View localView1 = this.mMainView.findViewById(2131755807);
    this.mActionsView = ((ViewGroup)localView1.findViewById(2131755288));
    TextView localTextView1 = (TextView)this.mMainView.findViewById(2131755173);
    if (localTextView1 != null) {
      localTextView1.setText(2131362733);
    }
    TextView localTextView2 = (TextView)this.mMainView.findViewById(2131755621);
    if (localTextView2 != null)
    {
      localTextView2.setText(this.mAccount.name);
      localTextView2.setVisibility(0);
    }
    ((TextView)this.mMainView.findViewById(2131755976)).setText(Html.fromHtml(getString(getPrimerStringId())));
    this.mProgressIndicator = this.mMainView.findViewById(2131755289);
    this.mProfileView = this.mMainView.findViewById(2131755284);
    SetupWizardNavBar localSetupWizardNavBar = SetupWizardUtils.getNavBarIfPossible(getActivity());
    if (localSetupWizardNavBar != null)
    {
      this.mContinueButton = localSetupWizardNavBar.mNextButton;
      configureContinueButtonStyle(this.mContinueButton);
      this.mContinueButton.setEnabled(false);
      if (!(this.mContinueButton instanceof PlayActionButton)) {
        this.mContinueButton.setOnClickListener(this);
      }
      switch (this.mUiMode)
      {
      default: 
        View localView2 = this.mMainView.findViewById(2131755629);
        if (localView2 != null) {
          localView2.setVisibility(8);
        }
        ViewCompat.setPaddingRelative(localView1, 0, 0, 0, getResources().getDimensionPixelSize(2131493475));
      }
    }
    for (;;)
    {
      return this.mMainView;
      this.mContinueButton = this.mMainView.findViewById(2131755631);
      break;
      this.mNotNowButton = ((TextView)this.mMainView.findViewById(2131755975));
      this.mNotNowButton.setOnClickListener(this.mNoneOnClickListener);
    }
  }
  
  protected final void onFatalError(String paramString)
  {
    Listener localListener = getListener();
    if (localListener != null) {
      localListener.onFatalError$552c4e01();
    }
  }
  
  protected final void onInstrumentCreated(String paramString)
  {
    notifyListenerOnInstrumentCreated();
  }
  
  protected final void onInstrumentEdited(String paramString)
  {
    FinskyLog.wtf("Unexpected instrument edit.", new Object[0]);
  }
  
  protected final void onPromoCodeRedeemed(RedeemCodeResult paramRedeemCodeResult)
  {
    FinskyLog.wtf("Unexpected promo code redemption.", new Object[0]);
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("PromptForFopFragment.selected_index", this.mSelectedIndex);
    paramBundle.putBoolean("PromptForFopFragment.has_logged_screen", this.mHasLoggedScreen);
  }
  
  protected final void onStoredValueAdded(String paramString)
  {
    notifyListenerOnInstrumentCreated();
  }
  
  public final void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    if (paramBundle != null) {
      this.mSelectedIndex = paramBundle.getInt("PromptForFopFragment.selected_index", this.mSelectedIndex);
    }
    if (this.mSelectedIndex >= 0) {
      selectItem(this.mSelectedIndex);
    }
  }
  
  protected final void renderActions(List<FopActionEntry> paramList)
  {
    this.mActionEntries = paramList;
    this.mActionsView.removeAllViews();
    if (this.mUiMode == 1)
    {
      this.mNotNowButton.setVisibility(0);
      this.mNotNowButton.setText(this.mProfile.remindMeLaterLabel.toUpperCase());
    }
    if (this.mUiMode == 2) {
      paramList.add(new FopActionEntry(this.mProfile.remindMeLaterLabel, null, this.mProfile.remindMeLaterIconImage, null, null, this.mNoneOnClickListener, null, 1008));
    }
    ViewGroup localViewGroup = this.mActionsView;
    int i = paramList.size();
    final int j = 0;
    if (j < i)
    {
      int k;
      label124:
      View localView;
      FifeImageView localFifeImageView;
      Common.Image localImage;
      if (j == i - 1)
      {
        k = 1;
        FopActionEntry localFopActionEntry = (FopActionEntry)paramList.get(j);
        localView = LayoutInflater.from(localViewGroup.getContext()).inflate(getActionEntryLayout(), localViewGroup, false);
        ((TextView)localView.findViewById(2131755173)).setText(localFopActionEntry.displayTitle);
        localFifeImageView = (FifeImageView)localView.findViewById(2131755290);
        localImage = localFopActionEntry.iconImage;
        if (localImage != null) {
          break label292;
        }
        localFifeImageView.setVisibility(8);
      }
      for (;;)
      {
        RadioButton localRadioButton = (RadioButton)localView.findViewById(2131755190);
        localRadioButton.setId(j);
        localRadioButton.setClickable(false);
        localRadioButton.setVisibility(0);
        localView.setOnClickListener(new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            PromptForFopFragment.this.selectItem(j);
          }
        });
        if ((k != 0) && ((localView instanceof SeparatorLinearLayout))) {
          ((SeparatorLinearLayout)localView).hideSeparator();
        }
        localViewGroup.addView(localView);
        j++;
        break;
        k = 0;
        break label124;
        label292:
        localFifeImageView.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, FinskyApp.get().mBitmapLoader);
      }
    }
    if (this.mUiMode == 2) {
      selectItem(0);
    }
  }
  
  protected final void renderInstruments(Instrument[] paramArrayOfInstrument, byte[] paramArrayOfByte) {}
  
  protected final boolean shouldRender(Instrument[] paramArrayOfInstrument)
  {
    if (paramArrayOfInstrument.length > 0)
    {
      FinskyLog.w("Unexpected instruments found.", new Object[0]);
      Listener localListener = getListener();
      if (localListener != null) {
        localListener.onAlreadySetup();
      }
      return false;
    }
    return true;
  }
  
  public static abstract interface Listener
  {
    public abstract void onAlreadySetup();
    
    public abstract void onFatalError$552c4e01();
    
    public abstract void onInstrumentCreated();
    
    public abstract void onNoneSelected();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.promptforfop.PromptForFopFragment
 * JD-Core Version:    0.7.0.1
 */