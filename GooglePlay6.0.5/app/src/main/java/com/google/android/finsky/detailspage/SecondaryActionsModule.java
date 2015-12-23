package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.AccessibleLinearLayout;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.protos.Annotations;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.PlusOneData;
import com.google.android.finsky.protos.PlusOneResponse;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.IntentUtils;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.finsky.utils.WishlistHelper;
import com.google.android.finsky.utils.WishlistHelper.WishlistStatusListener;
import java.text.NumberFormat;

public class SecondaryActionsModule
  extends FinskyModule<Data>
  implements SecondaryActionsModuleLayout.SecondaryActionsClickListener, PlayStoreUiElementNode, WishlistHelper.WishlistStatusListener
{
  private PlayStore.PlayStoreUiElement mPlayStoreUiElement = FinskyEventLog.obtainPlayStoreUiElement(1820);
  
  public final void bindModule(boolean paramBoolean, Document paramDocument1, DfeDetails paramDfeDetails1, Document paramDocument2, DfeDetails paramDfeDetails2)
  {
    if (!paramBoolean) {}
    while (this.mModuleData != null) {
      return;
    }
    this.mModuleData = new Data();
    ((Data)this.mModuleData).doc = paramDocument1;
    Data localData1 = (Data)this.mModuleData;
    boolean bool1;
    boolean bool2;
    if (!WishlistHelper.shouldHideWishlistAction(paramDocument1, this.mDfeApi))
    {
      bool1 = true;
      localData1.showWishlist = bool1;
      if (((Data)this.mModuleData).showWishlist) {
        ((Data)this.mModuleData).isInWishlist = WishlistHelper.isInWishlist(paramDocument1, FinskyApp.get().getCurrentAccount());
      }
      Data localData2 = (Data)this.mModuleData;
      if (TextUtils.isEmpty(paramDocument1.mDocument.shareUrl)) {
        break label265;
      }
      bool2 = true;
      label122:
      localData2.showShare = bool2;
      Data localData3 = (Data)this.mModuleData;
      Annotations localAnnotations = paramDocument2.mDocument.annotations;
      boolean bool3 = false;
      if (localAnnotations != null)
      {
        PlusOneData localPlusOneData2 = paramDocument2.mDocument.annotations.plusOneData;
        bool3 = false;
        if (localPlusOneData2 != null) {
          bool3 = true;
        }
      }
      localData3.showPlusOne = bool3;
      if (((Data)this.mModuleData).showPlusOne) {
        if (paramDocument2.mDocument.annotations == null) {
          break label271;
        }
      }
    }
    label265:
    label271:
    for (PlusOneData localPlusOneData1 = paramDocument2.mDocument.annotations.plusOneData;; localPlusOneData1 = null)
    {
      ((Data)this.mModuleData).isPlusOned = localPlusOneData1.setByUser;
      ((Data)this.mModuleData).plusOneCount = localPlusOneData1.total;
      WishlistHelper.addWishlistStatusListener(this);
      return;
      bool1 = false;
      break;
      bool2 = false;
      break label122;
    }
  }
  
  public final void bindView(View paramView)
  {
    SecondaryActionsModuleLayout localSecondaryActionsModuleLayout = (SecondaryActionsModuleLayout)paramView;
    boolean bool1 = ((Data)this.mModuleData).showWishlist;
    boolean bool2 = ((Data)this.mModuleData).isInWishlist;
    int i = ((Data)this.mModuleData).doc.mDocument.backendId;
    boolean bool3 = ((Data)this.mModuleData).showShare;
    boolean bool4 = ((Data)this.mModuleData).showPlusOne;
    long l1 = ((Data)this.mModuleData).plusOneCount;
    boolean bool5 = ((Data)this.mModuleData).isPlusOned;
    if ((!bool1) && (!bool3) && (!bool4))
    {
      localSecondaryActionsModuleLayout.setVisibility(8);
      return;
    }
    localSecondaryActionsModuleLayout.setVisibility(0);
    localSecondaryActionsModuleLayout.mSecondaryActionsClickListener = this;
    Resources localResources = localSecondaryActionsModuleLayout.getResources();
    if (bool1)
    {
      localSecondaryActionsModuleLayout.mWishlistButton.setVisibility(0);
      if (bool2)
      {
        localSecondaryActionsModuleLayout.mWishlistButtonIcon.setImageResource(CorpusResourceUtils.getWishlistOnDrawable(i));
        localSecondaryActionsModuleLayout.mWishlistButton.setContentDescription(localResources.getString(2131362046));
        if (!bool3) {
          break label364;
        }
        localSecondaryActionsModuleLayout.mShareButton.setVisibility(0);
        localSecondaryActionsModuleLayout.mShareButton.setContentDescription(localResources.getString(2131362753));
        label201:
        if (!bool4) {
          break label442;
        }
        localSecondaryActionsModuleLayout.mPlusOneButton.setVisibility(0);
        if (l1 != 0L) {
          break label376;
        }
      }
    }
    label364:
    label376:
    for (long l2 = 1L;; l2 = l1)
    {
      TextView localTextView = localSecondaryActionsModuleLayout.mPlusOneIcon;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = SecondaryActionsModuleLayout.sCountFormatter.format(l2);
      localTextView.setText(localResources.getString(2131362979, arrayOfObject1));
      if (!bool5) {
        break label383;
      }
      localSecondaryActionsModuleLayout.mPlusOneIcon.setBackgroundResource(2130838082);
      localSecondaryActionsModuleLayout.mPlusOneIcon.setTextColor(localResources.getColor(2131689753));
      AccessibleLinearLayout localAccessibleLinearLayout2 = localSecondaryActionsModuleLayout.mPlusOneButton;
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = Long.valueOf(l1);
      localAccessibleLinearLayout2.setContentDescription(localResources.getString(2131361981, arrayOfObject3));
      return;
      localSecondaryActionsModuleLayout.mWishlistButtonIcon.setImageResource(2130837823);
      localSecondaryActionsModuleLayout.mWishlistButton.setContentDescription(localResources.getString(2131362045));
      break;
      localSecondaryActionsModuleLayout.mWishlistButton.setVisibility(8);
      break;
      localSecondaryActionsModuleLayout.mShareButton.setVisibility(8);
      break label201;
    }
    label383:
    localSecondaryActionsModuleLayout.mPlusOneIcon.setBackgroundResource(2130838081);
    localSecondaryActionsModuleLayout.mPlusOneIcon.setTextColor(localResources.getColor(2131689624));
    AccessibleLinearLayout localAccessibleLinearLayout1 = localSecondaryActionsModuleLayout.mPlusOneButton;
    Object[] arrayOfObject2 = new Object[1];
    arrayOfObject2[0] = Long.valueOf(l1);
    localAccessibleLinearLayout1.setContentDescription(localResources.getString(2131361980, arrayOfObject2));
    return;
    label442:
    localSecondaryActionsModuleLayout.mPlusOneButton.setVisibility(8);
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    FinskyEventLog.childImpression(this, paramPlayStoreUiElementNode);
  }
  
  public final int getLayoutResId()
  {
    return 2130969095;
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return this.mParentNode;
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mPlayStoreUiElement;
  }
  
  public final void onDestroyModule()
  {
    WishlistHelper.removeWishlistStatusListener(this);
  }
  
  public final void onPlusOneClick(View paramView)
  {
    boolean bool;
    if (((Data)this.mModuleData).isPlusOned)
    {
      Data localData3 = (Data)this.mModuleData;
      localData3.plusOneCount -= 1L;
      Data localData2 = (Data)this.mModuleData;
      if (((Data)this.mModuleData).isPlusOned) {
        break label198;
      }
      bool = true;
      label58:
      localData2.isPlusOned = bool;
      this.mSocialDfeApi.setPlusOne(((Data)this.mModuleData).doc.mDocument.docid, ((Data)this.mModuleData).isPlusOned, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
      {
        public final void onErrorResponse(VolleyError paramAnonymousVolleyError) {}
      });
      if (!((Data)this.mModuleData).isPlusOned) {
        break label204;
      }
    }
    label198:
    label204:
    for (int i = 2131361815;; i = 2131361814)
    {
      UiUtils.sendAccessibilityEventWithText(this.mContext, this.mContext.getString(i), paramView);
      FinskyApp.get().getEventLogger().logClickEvent(208, null, this);
      this.mFinskyModuleController.refreshModule(this, false);
      return;
      Data localData1 = (Data)this.mModuleData;
      localData1.plusOneCount = (1L + localData1.plusOneCount);
      break;
      bool = false;
      break label58;
    }
  }
  
  public final void onRestoreModuleData(FinskyModule.ModuleData paramModuleData)
  {
    super.onRestoreModuleData(paramModuleData);
    if (this.mModuleData != null) {
      WishlistHelper.addWishlistStatusListener(this);
    }
  }
  
  public final void onShareClick$3c7ec8c3()
  {
    IntentUtils.shareDocument(this.mContext, ((Data)this.mModuleData).doc, this);
  }
  
  public final void onWishlistClick(View paramView)
  {
    if (((Data)this.mModuleData).isInWishlist) {}
    for (int i = 205;; i = 204)
    {
      FinskyApp.get().getEventLogger().logClickEvent(i, null, this);
      WishlistHelper.processWishlistClick(paramView, ((Data)this.mModuleData).doc, this.mDfeApi);
      FinskyApp.get().getEventLogger().logClickEvent(i, null, this);
      return;
    }
  }
  
  public final void onWishlistStatusChanged(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    Data localData;
    if ((this.mModuleData != null) && (((Data)this.mModuleData).doc.mDocument.docid.equals(paramString)))
    {
      ((Data)this.mModuleData).isInWishlist = paramBoolean1;
      localData = (Data)this.mModuleData;
      if (WishlistHelper.shouldHideWishlistAction(((Data)this.mModuleData).doc, this.mDfeApi)) {
        break label92;
      }
    }
    label92:
    for (boolean bool = true;; bool = false)
    {
      localData.showWishlist = bool;
      this.mFinskyModuleController.refreshModule(this, false);
      return;
    }
  }
  
  public final boolean readyForDisplay()
  {
    return (this.mModuleData != null) && ((((Data)this.mModuleData).showWishlist) || (((Data)this.mModuleData).showShare) || (((Data)this.mModuleData).showPlusOne));
  }
  
  protected static final class Data
    extends FinskyModule.ModuleData
  {
    public Document doc;
    public boolean isInWishlist;
    public boolean isPlusOned;
    public long plusOneCount;
    public boolean showPlusOne;
    public boolean showShare;
    public boolean showWishlist;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.SecondaryActionsModule
 * JD-Core Version:    0.7.0.1
 */