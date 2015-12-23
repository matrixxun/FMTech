package com.google.android.finsky.billing;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.SimpleAlertDialog;
import com.google.android.finsky.activities.SimpleAlertDialog.Builder;
import com.google.android.finsky.activities.SimpleAlertDialog.Listener;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.billing.giftcard.RedeemCodeResult;
import com.google.android.finsky.billing.lightpurchase.billingprofile.BillingProfileSidecar;
import com.google.android.finsky.billing.lightpurchase.billingprofile.FopActionEntry;
import com.google.android.finsky.billing.lightpurchase.billingprofile.instruments.RedeemCodeActivity;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.SeparatorLinearLayout;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.protos.BillingProfileProtos.BillingProfile;
import com.google.android.finsky.protos.Common.Docid;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DisabledInfo;
import com.google.android.finsky.protos.Instrument;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.play.image.FifeImageView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class BillingProfileFragment
  extends BillingProfileBaseFragment
  implements SimpleAlertDialog.Listener
{
  private TextView mActionsHeader;
  private View mActionsHeaderSeparator;
  private ViewGroup mActionsView;
  private ViewGroup mExistingInstrumentsView;
  private final ArrayList<Runnable> mImpressionsToLog = new ArrayList();
  private final PlayStore.PlayStoreUiElement mUiElement = FinskyEventLog.obtainPlayStoreUiElement(800);
  
  private void addEntry(ViewGroup paramViewGroup, final FopActionEntry paramFopActionEntry, boolean paramBoolean, String paramString, final byte[] paramArrayOfByte)
  {
    LayoutInflater localLayoutInflater = LayoutInflater.from(paramViewGroup.getContext());
    View localView;
    FifeImageView localFifeImageView;
    Common.Image localImage;
    if (paramString == null)
    {
      localView = localLayoutInflater.inflate(2130968635, paramViewGroup, false);
      if (paramBoolean) {
        localView.findViewById(2131755294).setVisibility(0);
      }
      localView.setOnClickListener(paramFopActionEntry.action);
      ((TextView)localView.findViewById(2131755173)).setText(paramFopActionEntry.displayTitle);
      TextView localTextView1 = (TextView)localView.findViewById(2131755291);
      if (!TextUtils.isEmpty(paramFopActionEntry.displaySubtitle))
      {
        localTextView1.setText(paramFopActionEntry.displaySubtitle);
        localTextView1.setVisibility(0);
      }
      localFifeImageView = (FifeImageView)localView.findViewById(2131755290);
      localImage = paramFopActionEntry.iconImage;
      if (localImage != null) {
        break label272;
      }
      localFifeImageView.setVisibility(8);
    }
    for (;;)
    {
      paramViewGroup.addView(localView);
      this.mImpressionsToLog.add(new Runnable()
      {
        public final void run()
        {
          BillingProfileFragment.this.mEventLog.logPathImpression(0L, paramFopActionEntry.playStoreUiElementType, paramFopActionEntry.serverLogsCookie, BillingProfileFragment.this);
        }
      });
      if ((FinskyApp.get().getExperiments().isEnabled(12604305L)) && (!TextUtils.isEmpty(paramFopActionEntry.editButtonLabel)) && (paramFopActionEntry.paymentsIntegratorEditToken != null) && (paramFopActionEntry.paymentsIntegratorEditToken.length > 0))
      {
        TextView localTextView2 = (TextView)localView.findViewById(2131755293);
        localTextView2.setText(paramFopActionEntry.editButtonLabel.toUpperCase());
        localView.setOnClickListener(new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            BillingProfileFragment.this.mEventLog.logClickEvent(803, paramFopActionEntry.serverLogsCookie, BillingProfileFragment.this);
            BillingProfileFragment.this.mBillingProfileSidecar.editInstrument(paramFopActionEntry.paymentsIntegratorEditToken, paramArrayOfByte);
          }
        });
        localTextView2.setVisibility(0);
      }
      return;
      localView = localLayoutInflater.inflate(2130968634, paramViewGroup, false);
      ((TextView)localView.findViewById(2131755292)).setText(paramString);
      break;
      label272:
      localFifeImageView.setImage(localImage.imageUrl, localImage.supportsFifeUrlOptions, FinskyApp.get().mBitmapLoader);
    }
  }
  
  private Listener getListener()
  {
    if ((getActivity() instanceof Listener)) {
      return (Listener)getActivity();
    }
    FinskyLog.wtf("No listener registered.", new Object[0]);
    return null;
  }
  
  public static BillingProfileFragment newInstance(Account paramAccount, String paramString, Common.Docid paramDocid, int paramInt)
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("BillingProfileFragment.account", paramAccount);
    localBundle.putString("BillingProfileFragment.purchaseContextToken", paramString);
    localBundle.putParcelable("BillingProfileFragment.docid", ParcelableProto.forProto(paramDocid));
    localBundle.putInt("BillingProfileFragment.offer_type", paramInt);
    BillingProfileFragment localBillingProfileFragment = new BillingProfileFragment();
    localBillingProfileFragment.setArguments(localBundle);
    return localBillingProfileFragment;
  }
  
  private void notifyListenerOnCancel()
  {
    Listener localListener = getListener();
    if (localListener != null) {
      localListener.onCancel();
    }
  }
  
  protected final void clearInstrumentsAndActionViews()
  {
    this.mExistingInstrumentsView.removeAllViews();
    this.mActionsView.removeAllViews();
    this.mImpressionsToLog.clear();
  }
  
  protected final byte[] getBackgroundEventServerLogsCookie()
  {
    return null;
  }
  
  protected final int getBillingProfileRequestEnum()
  {
    return 1;
  }
  
  protected final int getCreditCardEventType()
  {
    return 320;
  }
  
  protected final int getDcbEventType()
  {
    return 321;
  }
  
  protected final int getEditEventType()
  {
    return 325;
  }
  
  protected final int getGenericInstrumentEventType()
  {
    return 324;
  }
  
  public final PlayStoreUiElementNode getParentNode()
  {
    return null;
  }
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElement;
  }
  
  protected final Intent getRedeemCodeIntent()
  {
    Bundle localBundle = this.mArguments;
    Common.Docid localDocid = (Common.Docid)ParcelableProto.getProtoFromBundle(localBundle, "BillingProfileFragment.docid");
    return RedeemCodeActivity.createBuyFlowIntent(this.mAccount.name, 3, localDocid, localBundle.getInt("BillingProfileFragment.offer_type"));
  }
  
  protected final int getRedeemEventType()
  {
    return 322;
  }
  
  protected final int getTopupEventType()
  {
    return 323;
  }
  
  protected final void logLoading()
  {
    this.mEventLog.logPathImpression$7d139cbf(213, this);
  }
  
  protected final void logScreen()
  {
    this.mEventLog.logPathImpression$7d139cbf(801, this);
    Iterator localIterator = this.mImpressionsToLog.iterator();
    while (localIterator.hasNext()) {
      ((Runnable)localIterator.next()).run();
    }
  }
  
  protected final void notifyListenerOnInstrumentSelected(String paramString)
  {
    Listener localListener = getListener();
    if (localListener != null) {
      localListener.onInstrumentSelected(paramString);
    }
  }
  
  public final void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle == null) {
      this.mEventLog.logPathImpression(0L, this);
    }
  }
  
  public final View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    ViewGroup localViewGroup = (ViewGroup)paramLayoutInflater.inflate(2130968632, paramViewGroup, false);
    this.mExistingInstrumentsView = ((ViewGroup)localViewGroup.findViewById(2131755285));
    this.mActionsView = ((ViewGroup)localViewGroup.findViewById(2131755288));
    this.mProgressIndicator = localViewGroup.findViewById(2131755289);
    this.mProfileView = localViewGroup.findViewById(2131755284);
    this.mActionsHeader = ((TextView)localViewGroup.findViewById(2131755286));
    this.mActionsHeader.setText(getString(2131361903).toUpperCase());
    this.mActionsHeaderSeparator = localViewGroup.findViewById(2131755287);
    return localViewGroup;
  }
  
  protected final void onFatalError(String paramString)
  {
    renderProfile();
    SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
    localBuilder.setMessageHtml(paramString).setPositiveId(2131362418).setCallback(this, 1, null);
    localBuilder.build().show(this.mFragmentManager, "BillingProfileFragment.errorDialog");
  }
  
  protected final void onInstrumentCreated(String paramString)
  {
    notifyListenerOnInstrumentSelected(paramString);
  }
  
  protected final void onInstrumentEdited(String paramString)
  {
    notifyListenerOnInstrumentSelected(paramString);
  }
  
  public final void onNegativeClick(int paramInt, Bundle paramBundle)
  {
    if (paramInt == 1) {
      notifyListenerOnCancel();
    }
  }
  
  public final void onPositiveClick(int paramInt, Bundle paramBundle)
  {
    if (paramInt == 1) {
      notifyListenerOnCancel();
    }
  }
  
  protected final void onPromoCodeRedeemed(RedeemCodeResult paramRedeemCodeResult)
  {
    Listener localListener = getListener();
    if (localListener != null) {
      localListener.onPromoCodeRedeemed(paramRedeemCodeResult);
    }
  }
  
  protected final void onStoredValueAdded(String paramString)
  {
    if (paramString != null)
    {
      notifyListenerOnInstrumentSelected(paramString);
      return;
    }
    this.mProfileView.post(new Runnable()
    {
      public final void run()
      {
        BillingProfileFragment.this.requestBillingProfile();
      }
    });
  }
  
  protected final void renderActions(List<FopActionEntry> paramList)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      FopActionEntry localFopActionEntry = (FopActionEntry)localIterator.next();
      addEntry(this.mActionsView, localFopActionEntry, false, null, null);
    }
    if (this.mActionsView.getChildCount() > 0) {
      ((SeparatorLinearLayout)this.mActionsView.getChildAt(-1 + this.mActionsView.getChildCount())).hideSeparator();
    }
  }
  
  protected final void renderInstruments(Instrument[] paramArrayOfInstrument, byte[] paramArrayOfByte)
  {
    String str1;
    int j;
    label38:
    final Instrument localInstrument;
    if (paramArrayOfInstrument.length == 0)
    {
      this.mActionsHeader.setVisibility(8);
      this.mActionsHeaderSeparator.setVisibility(8);
      str1 = this.mProfile.selectedExternalInstrumentId;
      int i = paramArrayOfInstrument.length;
      j = 0;
      if (j >= i) {
        break label187;
      }
      localInstrument = paramArrayOfInstrument[j];
      if (localInstrument.disabledInfo.length <= 0) {
        break label181;
      }
    }
    label181:
    for (String str2 = localInstrument.disabledInfo[0].disabledMessageHtml;; str2 = null)
    {
      final String str3 = localInstrument.externalInstrumentId;
      boolean bool = str3.equals(str1);
      FopActionEntry localFopActionEntry = new FopActionEntry(localInstrument.displayTitle, localInstrument.displaySubtitle, localInstrument.iconImage, localInstrument.editButtonLabel, localInstrument.paymentsIntegratorEditToken, new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          BillingProfileFragment.this.mEventLog.logClickEvent(802, localInstrument.serverLogsCookie, BillingProfileFragment.this);
          BillingProfileFragment.this.notifyListenerOnInstrumentSelected(str3);
        }
      }, localInstrument.serverLogsCookie, 802);
      addEntry(this.mExistingInstrumentsView, localFopActionEntry, bool, str2, paramArrayOfByte);
      j++;
      break label38;
      this.mActionsHeader.setVisibility(0);
      this.mActionsHeaderSeparator.setVisibility(0);
      break;
    }
    label187:
    if (this.mExistingInstrumentsView.getChildCount() > 0) {
      ((SeparatorLinearLayout)this.mExistingInstrumentsView.getChildAt(-1 + this.mExistingInstrumentsView.getChildCount())).hideSeparator();
    }
  }
  
  public static abstract interface Listener
  {
    public abstract void onCancel();
    
    public abstract void onInstrumentSelected(String paramString);
    
    public abstract void onPromoCodeRedeemed(RedeemCodeResult paramRedeemCodeResult);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.BillingProfileFragment
 * JD-Core Version:    0.7.0.1
 */