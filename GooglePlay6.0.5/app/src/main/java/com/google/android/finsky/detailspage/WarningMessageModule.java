package com.google.android.finsky.detailspage;

import android.accounts.Account;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.finsky.activities.AppActionAnalyzer;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.library.AccountLibrary;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.Libraries.Listener;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.Common.Offer;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.DocUtils;
import com.google.android.finsky.utils.LibraryUtils;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.finsky.utils.VoucherUtils;
import java.util.List;

public class WarningMessageModule
  extends FinskyModule<WarningMessageModuleData>
  implements Libraries.Listener
{
  private boolean mNeedsRefresh;
  
  private void configureWarningMessage()
  {
    Document localDocument = ((WarningMessageModuleData)this.mModuleData).detailsDoc;
    Account localAccount1 = this.mDfeApi.getAccount();
    AccountLibrary localAccountLibrary = this.mLibraries.getAccountLibrary(localAccount1);
    boolean bool1 = LibraryUtils.isAvailable(localDocument, this.mDfeToc, localAccountLibrary);
    int i;
    boolean bool2;
    int i3;
    label93:
    int j;
    label144:
    int k;
    label179:
    int m;
    label199:
    Libraries localLibraries;
    DfeToc localDfeToc;
    Spanned localSpanned;
    label278:
    boolean bool3;
    if ((localDocument.hasApplicableVoucherDescription()) && (VoucherUtils.hasApplicableVouchers(localDocument, localAccountLibrary)))
    {
      i = 1;
      bool2 = localDocument.hasWarningMessage();
      if (localDocument.mDocument.docType != 1) {
        break label367;
      }
      Common.Offer[] arrayOfOffer = localDocument.mDocument.offer;
      int i2 = arrayOfOffer.length;
      i3 = 0;
      if (i3 >= i2) {
        break label367;
      }
      Common.Offer localOffer = arrayOfOffer[i3];
      if ((localOffer.licenseTerms == null) || ((localOffer.micros <= 0L) && (!localOffer.temporarilyFree))) {
        break label361;
      }
      if (LibraryUtils.isOwned(localDocument, localAccountLibrary)) {
        break label355;
      }
      j = 1;
      if ((!LibraryUtils.isOwned(localDocument, localAccountLibrary)) || (localDocument.mDocument.docType != 1) || (!AppActionAnalyzer.isUninstallBlockedByAdmin(localDocument.getAppDetails().packageName))) {
        break label373;
      }
      k = 1;
      if ((localDocument.getAppDetails() == null) || (!localDocument.getAppDetails().externallyHosted)) {
        break label379;
      }
      m = 1;
      localLibraries = this.mLibraries;
      localDfeToc = this.mDfeToc;
      if ((localDocument.mDocument.docType == 1) || (LibraryUtils.isOwned(localDocument, localAccountLibrary))) {
        break label496;
      }
      Account localAccount2 = LibraryUtils.getFirstOwner(localDocument, localLibraries);
      if (localAccount2 == null) {
        break label385;
      }
      Context localContext2 = this.mContext;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = localAccount2.name;
      localSpanned = Html.fromHtml(localContext2.getString(2131362466, arrayOfObject2));
      WarningMessageModuleData localWarningMessageModuleData = (WarningMessageModuleData)this.mModuleData;
      if ((bool1) && (!bool2) && (i == 0) && (j == 0) && (m == 0) && (TextUtils.isEmpty(localSpanned)) && (k == 0)) {
        break label502;
      }
      bool3 = true;
      label328:
      localWarningMessageModuleData.shouldShowWarningMessage = bool3;
      if (((WarningMessageModuleData)this.mModuleData).shouldShowWarningMessage) {
        break label508;
      }
    }
    label355:
    label361:
    label367:
    label373:
    label379:
    label508:
    Resources localResources;
    label385:
    label496:
    label502:
    int n;
    do
    {
      return;
      i = 0;
      break;
      j = 0;
      break label144;
      i3++;
      break label93;
      j = 0;
      break label144;
      k = 0;
      break label179;
      m = 0;
      break label199;
      if (localDocument.hasSubscriptions())
      {
        List localList = DocUtils.getSubscriptions(localDocument, localDfeToc, localLibraries);
        if (LibraryUtils.getOwnerWithCurrentAccount(localList, localLibraries, localAccount1) == null) {
          for (int i1 = 0;; i1++)
          {
            if (i1 >= localList.size()) {
              break label496;
            }
            Account localAccount3 = LibraryUtils.getFirstOwner((Document)localList.get(i1), localLibraries);
            if (localAccount3 != null)
            {
              Context localContext1 = this.mContext;
              Object[] arrayOfObject1 = new Object[1];
              arrayOfObject1[0] = localAccount3.name;
              localSpanned = Html.fromHtml(localContext1.getString(2131362466, arrayOfObject1));
              break;
            }
          }
        }
      }
      localSpanned = null;
      break label278;
      bool3 = false;
      break label328;
      localResources = this.mContext.getResources();
      n = ((WarningMessageModuleData)this.mModuleData).detailsDoc.mDocument.backendId;
      ((WarningMessageModuleData)this.mModuleData).isMessageLink = false;
      if (!bool1)
      {
        ((WarningMessageModuleData)this.mModuleData).messageText = localResources.getString(DocUtils.getAvailabilityRestrictionResourceId(localDocument));
        ((WarningMessageModuleData)this.mModuleData).messageIconResId = CorpusResourceUtils.getWarningDrawable(n);
        return;
      }
      if (j != 0)
      {
        ((WarningMessageModuleData)this.mModuleData).messageText = localResources.getString(2131362118);
        ((WarningMessageModuleData)this.mModuleData).messageIconResId = 2130837761;
        return;
      }
      if (m != 0)
      {
        ((WarningMessageModuleData)this.mModuleData).messageText = localResources.getString(2131362119);
        ((WarningMessageModuleData)this.mModuleData).messageIconResId = CorpusResourceUtils.getWarningDrawable(n);
        return;
      }
      if (bool2)
      {
        ((WarningMessageModuleData)this.mModuleData).messageText = localDocument.getWarningMessage().toString();
        ((WarningMessageModuleData)this.mModuleData).isMessageLink = true;
        ((WarningMessageModuleData)this.mModuleData).messageIconResId = CorpusResourceUtils.getWarningDrawable(n);
        return;
      }
      if (i != 0)
      {
        ((WarningMessageModuleData)this.mModuleData).messageText = localDocument.getApplicableVoucherDescription();
        ((WarningMessageModuleData)this.mModuleData).messageIconResId = CorpusResourceUtils.getRewardDrawable(n);
        return;
      }
      if (!TextUtils.isEmpty(localSpanned))
      {
        ((WarningMessageModuleData)this.mModuleData).messageText = localSpanned.toString();
        ((WarningMessageModuleData)this.mModuleData).messageIconResId = CorpusResourceUtils.getWarningDrawable(n);
        return;
      }
    } while (k == 0);
    ((WarningMessageModuleData)this.mModuleData).messageText = localResources.getString(2131362810);
    ((WarningMessageModuleData)this.mModuleData).messageIconResId = CorpusResourceUtils.getWarningDrawable(n);
  }
  
  public final void bindModule(boolean paramBoolean, Document paramDocument1, DfeDetails paramDfeDetails1, Document paramDocument2, DfeDetails paramDfeDetails2)
  {
    if (this.mModuleData == null)
    {
      this.mModuleData = new WarningMessageModuleData();
      this.mLibraries.addListener(this);
    }
    ((WarningMessageModuleData)this.mModuleData).detailsDoc = paramDocument1;
    configureWarningMessage();
  }
  
  public final void bindView(View paramView)
  {
    WarningMessageModuleLayout localWarningMessageModuleLayout = (WarningMessageModuleLayout)paramView;
    int j;
    if ((!localWarningMessageModuleLayout.mBinded) || (this.mNeedsRefresh))
    {
      String str = ((WarningMessageModuleData)this.mModuleData).messageText;
      boolean bool = ((WarningMessageModuleData)this.mModuleData).isMessageLink;
      int i = ((WarningMessageModuleData)this.mModuleData).messageIconResId;
      j = ((WarningMessageModuleData)this.mModuleData).detailsDoc.mDocument.backendId;
      localWarningMessageModuleLayout.mBinded = true;
      localWarningMessageModuleLayout.mWarningMessageText.setText(str);
      localWarningMessageModuleLayout.mWarningMessageIcon.setImageResource(i);
      if (!bool) {
        break label231;
      }
      localWarningMessageModuleLayout.mWarningMessageText.setMovementMethod(LinkMovementMethod.getInstance());
    }
    for (;;)
    {
      Context localContext = localWarningMessageModuleLayout.getContext();
      ColorStateList localColorStateList = CorpusResourceUtils.getSecondaryTextColor(localContext, j);
      localWarningMessageModuleLayout.mWarningMessageText.setTextColor(localColorStateList);
      int k = UiUtils.interpolateColor$4868c7be(CorpusResourceUtils.getPrimaryColor(localContext, j));
      int m = localWarningMessageModuleLayout.getPaddingTop();
      int n = localWarningMessageModuleLayout.getPaddingBottom();
      int i1 = ViewCompat.getPaddingEnd(localWarningMessageModuleLayout);
      int i2 = ViewCompat.getPaddingStart(localWarningMessageModuleLayout);
      Drawable[] arrayOfDrawable = new Drawable[2];
      arrayOfDrawable[0] = new ColorDrawable(k);
      arrayOfDrawable[1] = ContextCompat.getDrawable(localContext, 2130837958);
      localWarningMessageModuleLayout.setBackgroundDrawable(new LayerDrawable(arrayOfDrawable));
      ViewCompat.setPaddingRelative(localWarningMessageModuleLayout, i2, m, i1, n);
      this.mNeedsRefresh = false;
      return;
      label231:
      localWarningMessageModuleLayout.mWarningMessageText.setMovementMethod(null);
    }
  }
  
  public final int getLayoutResId()
  {
    return 2130969183;
  }
  
  public final void onAllLibrariesLoaded() {}
  
  public final void onDestroyModule()
  {
    this.mLibraries.removeListener(this);
  }
  
  public final void onLibraryContentsChanged$40bdb4b1()
  {
    configureWarningMessage();
    if (((WarningMessageModuleData)this.mModuleData).shouldShowWarningMessage)
    {
      this.mFinskyModuleController.refreshModule(this, true);
      this.mNeedsRefresh = true;
      return;
    }
    this.mFinskyModuleController.removeModule(this);
  }
  
  public final void onRestoreModuleData(FinskyModule.ModuleData paramModuleData)
  {
    super.onRestoreModuleData(paramModuleData);
    if (this.mModuleData != null) {
      this.mLibraries.addListener(this);
    }
  }
  
  public final boolean readyForDisplay()
  {
    return (this.mModuleData != null) && (((WarningMessageModuleData)this.mModuleData).shouldShowWarningMessage);
  }
  
  protected static final class WarningMessageModuleData
    extends FinskyModule.ModuleData
  {
    Document detailsDoc;
    boolean isMessageLink;
    int messageIconResId;
    String messageText;
    boolean shouldShowWarningMessage;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.WarningMessageModule
 * JD-Core Version:    0.7.0.1
 */