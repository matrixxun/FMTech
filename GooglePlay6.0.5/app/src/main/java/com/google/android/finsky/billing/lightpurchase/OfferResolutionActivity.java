package com.google.android.finsky.billing.lightpurchase;

import android.accounts.Account;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.SimpleAlertDialog;
import com.google.android.finsky.activities.SimpleAlertDialog.Builder;
import com.google.android.finsky.activities.SimpleAlertDialog.Listener;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.DfeUtils;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.layout.play.RootUiElementNode;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.protos.Common.Offer;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.VoucherInfo;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.DocUtils;
import com.google.android.finsky.utils.DocUtils.OfferFilter;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.LibraryUtils;
import com.google.android.finsky.utils.ParcelableProto;
import com.google.android.finsky.utils.VoucherUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class OfferResolutionActivity
  extends AppCompatActivity
  implements View.OnClickListener, Response.ErrorListener, SimpleAlertDialog.Listener, OnDataChangedListener, RootUiElementNode
{
  private Account mAccount;
  private DfeDetails mDfeDetails;
  private DfeToc mDfeToc;
  private Document mDoc;
  private FinskyEventLog mEventLog;
  private DocUtils.OfferFilter mOfferFilter;
  private final PlayStore.PlayStoreUiElement mUiElement = FinskyEventLog.obtainPlayStoreUiElement(780);
  
  public static Intent createIntent(DfeToc paramDfeToc, Account paramAccount, String paramString, Document paramDocument, DocUtils.OfferFilter paramOfferFilter)
  {
    Intent localIntent = new Intent(FinskyApp.get(), OfferResolutionActivity.class);
    localIntent.putExtra("OfferResolutionActivity.dfeToc", paramDfeToc);
    localIntent.putExtra("OfferResolutionActivity.account", paramAccount);
    localIntent.putExtra("OfferResolutionActivity.docid", paramString);
    localIntent.putExtra("OfferResolutionActivity.doc", paramDocument);
    if (paramOfferFilter != null) {
      localIntent.putExtra("OfferResolutionActivity.offerFilter", paramOfferFilter.name());
    }
    return localIntent;
  }
  
  public static AvailableOffer extractAvailableOffer(Intent paramIntent)
  {
    return new AvailableOffer((Document)paramIntent.getParcelableExtra("OfferResolutionActivity.document"), (Common.Offer)ParcelableProto.getProtoFromIntent(paramIntent, "OfferResolutionActivity.offer"), (byte)0);
  }
  
  private void showError(String paramString)
  {
    SimpleAlertDialog.Builder localBuilder = new SimpleAlertDialog.Builder();
    localBuilder.setMessage(paramString).setPositiveId(2131362418);
    localBuilder.build().show(getSupportFragmentManager(), "OfferResolutionActivity.errorDialog");
  }
  
  private void updateFromDoc()
  {
    findViewById(2131755613).setVisibility(0);
    findViewById(2131755289).setVisibility(8);
    byte[] arrayOfByte = this.mDoc.mDocument.serverLogsCookie;
    FinskyEventLog.setServerLogCookie(this.mUiElement, arrayOfByte);
    this.mEventLog.logPathImpression$7d139cbf(781, this);
    ViewGroup localViewGroup1 = (ViewGroup)findViewById(2131755678);
    localViewGroup1.removeAllViews();
    ArrayList localArrayList = new ArrayList();
    Document localDocument3;
    int i9;
    if (FinskyApp.get().getExperiments().isEnabled(12602952L))
    {
      int i8 = this.mDoc.mDocument.docType;
      if ((i8 == 16) || (i8 == 17))
      {
        if (i8 != 16) {
          break label366;
        }
        localDocument3 = this.mDoc.getChildAt(0);
        if (localDocument3 != null)
        {
          Libraries localLibraries = FinskyApp.get().mLibraries;
          Account localAccount = this.mAccount;
          if (LibraryUtils.getOwnerWithCurrentAccount(localDocument3, localLibraries, localAccount) == null) {
            break label375;
          }
          i9 = 1;
          label167:
          if (i9 == 0)
          {
            AccountLibrary localAccountLibrary2 = FinskyApp.get().mLibraries.getAccountLibrary(this.mAccount);
            DfeToc localDfeToc = this.mDfeToc;
            Common.Offer localOffer3 = DocUtils.getMagazineIssueOffer(localDocument3, localDfeToc, localAccountLibrary2, 1);
            if ((localOffer3 != null) && (localOffer3.offerType != 2) && (localOffer3.micros > 0L))
            {
              AvailableOffer localAvailableOffer5 = new AvailableOffer(localDocument3, localOffer3, (byte)0);
              localArrayList.add(localAvailableOffer5);
            }
          }
        }
      }
    }
    if (this.mDoc.hasSubscriptions())
    {
      DocUtils.getSubscriptions(this.mDoc, this.mDfeToc, FinskyApp.get().mLibraries.getAccountLibrary(this.mAccount));
      Iterator localIterator2 = this.mDoc.getSubscriptionsList().iterator();
      for (;;)
      {
        if (!localIterator2.hasNext()) {
          break label483;
        }
        Document localDocument2 = (Document)localIterator2.next();
        Common.Offer localOffer2 = localDocument2.getOffer(1);
        if (localOffer2 == null)
        {
          Object[] arrayOfObject3 = new Object[1];
          arrayOfObject3[0] = localDocument2.mDocument.docid;
          FinskyLog.w("Skipping subscription doc, no PURCHASE offer: %s", arrayOfObject3);
          continue;
          label366:
          localDocument3 = this.mDoc;
          break;
          label375:
          i9 = 0;
          break label167;
        }
        AvailableOffer localAvailableOffer4 = new AvailableOffer(localDocument2, localOffer2, (byte)0);
        localArrayList.add(localAvailableOffer4);
      }
    }
    for (Common.Offer localOffer1 : this.mDoc.mDocument.offer) {
      if (localOffer1.offerType != 2)
      {
        AvailableOffer localAvailableOffer3 = new AvailableOffer(this.mDoc, localOffer1, (byte)0);
        localArrayList.add(localAvailableOffer3);
      }
    }
    label483:
    if (this.mOfferFilter != null)
    {
      Iterator localIterator1 = localArrayList.iterator();
      while (localIterator1.hasNext())
      {
        AvailableOffer localAvailableOffer2 = (AvailableOffer)localIterator1.next();
        if (!this.mOfferFilter.matches(localAvailableOffer2.offer.offerType)) {
          localIterator1.remove();
        }
      }
    }
    if (localArrayList.isEmpty()) {
      showError(getString(2131362282));
    }
    int k;
    int m;
    label884:
    do
    {
      return;
      k = this.mDoc.mDocument.backendId;
      AccountLibrary localAccountLibrary1 = FinskyApp.get().mLibraries.getAccountLibrary(this.mAccount);
      m = 0;
      int n = localArrayList.size();
      LayoutInflater localLayoutInflater = getLayoutInflater();
      int i1 = 0;
      if (i1 < n)
      {
        AvailableOffer localAvailableOffer1 = (AvailableOffer)localArrayList.get(i1);
        ViewGroup localViewGroup2 = (ViewGroup)localLayoutInflater.inflate(2130968820, localViewGroup1, false);
        TextView localTextView2 = (TextView)localViewGroup2.findViewById(2131755173);
        TextView localTextView3 = (TextView)localViewGroup2.findViewById(2131755679);
        TextView localTextView4 = (TextView)localViewGroup2.findViewById(2131755680);
        TextView localTextView5 = (TextView)localViewGroup2.findViewById(2131755292);
        localTextView2.setText(localAvailableOffer1.offer.formattedName);
        localTextView3.setText(localAvailableOffer1.offer.formattedAmount);
        localTextView3.setTextColor(CorpusResourceUtils.getSecondaryTextColor(this, k));
        boolean bool = FinskyApp.get().getExperiments().isEnabled(12603136L);
        int i5;
        int i7;
        int i3;
        if ((DocUtils.hasDiscount(localAvailableOffer1.offer)) && (!bool))
        {
          localTextView4.setVisibility(0);
          localTextView4.setText(localAvailableOffer1.offer.formattedFullAmount);
          localTextView4.setPaintFlags(0x10 | localTextView4.getPaintFlags());
          Resources localResources1 = getResources();
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = localAvailableOffer1.offer.formattedFullAmount;
          localTextView4.setContentDescription(localResources1.getString(2131361989, arrayOfObject1));
          Resources localResources2 = getResources();
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = localAvailableOffer1.offer.formattedAmount;
          localTextView3.setContentDescription(localResources2.getString(2131361975, arrayOfObject2));
          if (TextUtils.isEmpty(localAvailableOffer1.offer.formattedDescription)) {
            break label1101;
          }
          localTextView5.setText(localAvailableOffer1.offer.formattedDescription);
          localViewGroup2.setTag(localAvailableOffer1);
          localViewGroup2.setOnClickListener(this);
          localViewGroup1.addView(localViewGroup2);
          if (i1 < n - 1) {
            localViewGroup1.addView(localLayoutInflater.inflate(2130968823, localViewGroup1, false));
          }
          if (m == 0)
          {
            Document localDocument1 = this.mDoc;
            int i2 = localAvailableOffer1.offer.offerType;
            if (!localDocument1.hasVouchers()) {
              break label1123;
            }
            VoucherInfo[] arrayOfVoucherInfo = localDocument1.getVouchers();
            int i4 = arrayOfVoucherInfo.length;
            i5 = 0;
            if (i5 >= i4) {
              break label1123;
            }
            VoucherInfo localVoucherInfo = arrayOfVoucherInfo[i5];
            if (!VoucherUtils.isVoucherOwned(localVoucherInfo.doc.backendDocid, localAccountLibrary1)) {
              break label1117;
            }
            Common.Offer[] arrayOfOffer2 = localVoucherInfo.offer;
            int i6 = arrayOfOffer2.length;
            i7 = 0;
            if (i7 >= i6) {
              break label1117;
            }
            if (arrayOfOffer2[i7].offerType != i2) {
              break label1111;
            }
            i3 = 1;
            if ((i3 == 0) || (LibraryUtils.isOfferOwned(localDocument1, localAccountLibrary1, i2))) {
              break label1129;
            }
          }
        }
        for (m = 1;; m = 0)
        {
          i1++;
          break;
          localTextView4.setVisibility(8);
          break label884;
          localTextView5.setVisibility(8);
          break label911;
          i7++;
          break label1042;
          i5++;
          break label997;
          i3 = 0;
          break label1065;
        }
      }
    } while ((m == 0) || (!this.mDoc.hasApplicableVoucherDescription()));
    label911:
    label997:
    label1129:
    findViewById(2131755675).setVisibility(0);
    label1042:
    label1065:
    label1101:
    label1111:
    label1117:
    label1123:
    ((ImageView)findViewById(2131755676)).setImageResource(CorpusResourceUtils.getRewardDrawable(k));
    ColorStateList localColorStateList = CorpusResourceUtils.getSecondaryTextColor(this, k);
    TextView localTextView1 = (TextView)findViewById(2131755677);
    localTextView1.setText(this.mDoc.getApplicableVoucherDescription());
    localTextView1.setTextColor(localColorStateList);
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    FinskyLog.wtf("Not using tree impressions.", new Object[0]);
  }
  
  public void finish()
  {
    if (this.mEventLog != null) {
      this.mEventLog.logPathImpression$7d139cbf(603, this);
    }
    super.finish();
  }
  
  public final void flushImpression()
  {
    FinskyLog.wtf("Not using tree impressions.", new Object[0]);
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return null;
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElement;
  }
  
  public void onClick(View paramView)
  {
    AvailableOffer localAvailableOffer = (AvailableOffer)paramView.getTag();
    Intent localIntent = new Intent();
    localIntent.putExtra("OfferResolutionActivity.document", localAvailableOffer.doc);
    localIntent.putExtra("OfferResolutionActivity.offer", ParcelableProto.forProto(localAvailableOffer.offer));
    this.mEventLog.logClickEvent(782, localAvailableOffer.doc.mDocument.serverLogsCookie, this);
    setResult(-1, localIntent);
    finish();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    int i = 1;
    super.onCreate(paramBundle);
    setContentView(2130968819);
    Intent localIntent = getIntent();
    this.mDfeToc = ((DfeToc)localIntent.getParcelableExtra("OfferResolutionActivity.dfeToc"));
    this.mAccount = ((Account)localIntent.getParcelableExtra("OfferResolutionActivity.account"));
    ((TextView)findViewById(2131755173)).setText(2131362417);
    this.mEventLog = FinskyApp.get().getEventLogger(this.mAccount);
    String str = localIntent.getStringExtra("OfferResolutionActivity.docid");
    this.mDoc = ((Document)localIntent.getParcelableExtra("OfferResolutionActivity.doc"));
    if (localIntent.hasExtra("OfferResolutionActivity.offerFilter")) {
      this.mOfferFilter = DocUtils.OfferFilter.valueOf(localIntent.getStringExtra("OfferResolutionActivity.offerFilter"));
    }
    if (paramBundle == null) {
      this.mEventLog.logPathImpression(0L, this);
    }
    int j;
    if (this.mDoc != null)
    {
      if (!FinskyApp.get().getExperiments().isEnabled(12603117L)) {
        break label328;
      }
      if ((this.mDoc.mDocument.docType != 20) && (this.mDoc.mDocument.docType != 19)) {
        break label317;
      }
      j = i;
      boolean bool = VoucherUtils.hasVouchers(FinskyApp.get().mLibraries.getAccountLibrary(this.mAccount));
      if ((j == 0) || (!bool)) {
        break label323;
      }
    }
    for (;;)
    {
      if (i == 0) {
        break label333;
      }
      findViewById(2131755613).setVisibility(8);
      findViewById(2131755289).setVisibility(0);
      this.mEventLog.logPathImpression$7d139cbf(213, this);
      Collection localCollection = VoucherUtils.getVoucherIds(FinskyApp.get().mLibraries.getAccountLibrary(this.mAccount));
      this.mDfeDetails = new DfeDetails(FinskyApp.get().getDfeApi(this.mAccount.name), DfeUtils.createDetailsUrlFromId(str), false, localCollection);
      this.mDfeDetails.addDataChangedListener(this);
      this.mDfeDetails.addErrorListener(this);
      return;
      label317:
      j = 0;
      break;
      label323:
      i = 0;
      continue;
      label328:
      i = 0;
    }
    label333:
    updateFromDoc();
  }
  
  public final void onDataChanged()
  {
    this.mDoc = this.mDfeDetails.getDocument();
    if (this.mDoc == null)
    {
      showError(getString(2131362282));
      return;
    }
    if (!LibraryUtils.isAvailable(this.mDoc, FinskyApp.get().mToc, FinskyApp.get().mLibraries.getAccountLibrary(this.mAccount)))
    {
      showError(getString(DocUtils.getAvailabilityRestrictionResourceId(this.mDoc)));
      return;
    }
    updateFromDoc();
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    showError(ErrorStrings.get(this, paramVolleyError));
  }
  
  public final void onNegativeClick(int paramInt, Bundle paramBundle) {}
  
  public final void onPositiveClick(int paramInt, Bundle paramBundle)
  {
    setResult(0);
    finish();
  }
  
  protected void onStart()
  {
    super.onStart();
    if (this.mDfeDetails != null)
    {
      this.mDfeDetails.addDataChangedListener(this);
      this.mDfeDetails.addErrorListener(this);
    }
  }
  
  protected void onStop()
  {
    if (this.mDfeDetails != null)
    {
      this.mDfeDetails.removeDataChangedListener(this);
      this.mDfeDetails.removeErrorListener(this);
    }
    super.onStop();
  }
  
  public final void startNewImpression()
  {
    FinskyLog.wtf("Not using impression id's.", new Object[0]);
  }
  
  public static final class AvailableOffer
  {
    public final Document doc;
    public final Common.Offer offer;
    
    private AvailableOffer(Document paramDocument, Common.Offer paramOffer)
    {
      this.doc = paramDocument;
      this.offer = paramOffer;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.lightpurchase.OfferResolutionActivity
 * JD-Core Version:    0.7.0.1
 */