package com.google.android.finsky.layout;

import android.accounts.Account;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.view.ViewStub;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Common.Offer;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.TvEpisodeDetails;
import com.google.android.finsky.utils.DocUtils;
import com.google.android.finsky.utils.DocUtils.OfferFilter;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.LibraryUtils;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.layout.ForegroundRelativeLayout;
import com.google.android.play.layout.PlayActionButton;

public class EpisodeSnippet
  extends ForegroundRelativeLayout
  implements PlayStoreUiElementNode
{
  private View mAddedDrawable;
  private TextView mAddedState;
  public BitmapLoader mBitmapLoader;
  private PlayActionButton mBuyButton;
  public OnCollapsedStateChanged mCollapsedStateChangedListener;
  public Document mEpisode;
  private TextView mEpisodeNumber;
  private TextView mEpisodeTitle;
  private View mExpandedContent;
  private TextView mExpandedDescription;
  private HeroGraphicView mExpandedEpisodeScreencap;
  private ViewStub mExpandedViewStub;
  private final Handler mHandler = new Handler(Looper.getMainLooper());
  public boolean mIsNewPurchase;
  public NavigationManager mNavigationManager;
  public PlayStoreUiElementNode mParentNode;
  private final Runnable mScrollerRunnable = new Runnable()
  {
    public final void run()
    {
      int[] arrayOfInt = new int[2];
      EpisodeSnippet.this.getLocationInWindow(arrayOfInt);
      int i = arrayOfInt[1];
      ViewParent localViewParent = EpisodeSnippet.this.getParent();
      for (;;)
      {
        if ((localViewParent instanceof RecyclerView))
        {
          RecyclerView localRecyclerView = (RecyclerView)localViewParent;
          localRecyclerView.getLocationInWindow(arrayOfInt);
          localRecyclerView.smoothScrollBy(0, i - arrayOfInt[1]);
          return;
        }
        localViewParent = localViewParent.getParent();
        if (localViewParent == null) {
          FinskyLog.wtf("Found no suitable parent.", new Object[0]);
        }
      }
    }
  };
  public Document mSeasonDocument;
  public PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(503);
  
  public EpisodeSnippet(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public static boolean hasSeasonOffer(Document paramDocument)
  {
    Account localAccount = FinskyApp.get().getCurrentAccount();
    if (LibraryUtils.getOwnerWithCurrentAccount(paramDocument, FinskyApp.get().mLibraries, localAccount) != null) {}
    while (DocUtils.getNumberOfValidOffers(paramDocument.mDocument.offer) > 0) {
      return true;
    }
    return false;
  }
  
  private static void setBuyButtonStyle(PlayActionButton paramPlayActionButton)
  {
    paramPlayActionButton.setDrawAsLabel(false);
    paramPlayActionButton.setActionStyle(2);
    paramPlayActionButton.setEnabled(true);
  }
  
  public static void updateBuyButtonState(Resources paramResources, PlayActionButton paramPlayActionButton, TextView paramTextView, View paramView, Document paramDocument1, final Document paramDocument2, boolean paramBoolean, final NavigationManager paramNavigationManager, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    paramPlayActionButton.setVisibility(0);
    Account localAccount1 = FinskyApp.get().getCurrentAccount();
    final Account localAccount2 = LibraryUtils.getOwnerWithCurrentAccount(paramDocument2, FinskyApp.get().mLibraries, localAccount1);
    Common.Offer[] arrayOfOffer = paramDocument2.mDocument.offer;
    int i = DocUtils.getNumberOfValidOffers(arrayOfOffer);
    int i5;
    if (localAccount2 != null)
    {
      setBuyButtonStyle(paramPlayActionButton);
      if (paramDocument2.mDocument.docType == 19)
      {
        i5 = 2131362617;
        paramPlayActionButton.setEnabled(false);
        paramPlayActionButton.configure(4, i5, new View.OnClickListener()
        {
          public final void onClick(View paramAnonymousView)
          {
            FinskyApp.get().getEventLogger().logClickEvent(218, null, this.val$parentNode);
            paramNavigationManager.openItem(localAccount2, paramDocument2, false);
          }
        });
        label98:
        if (paramTextView != null) {
          if (!paramBoolean) {
            break label553;
          }
        }
      }
    }
    label275:
    label295:
    label553:
    for (int j = 0;; j = 8)
    {
      paramView.setVisibility(j);
      paramTextView.setVisibility(j);
      return;
      i5 = 2131362550;
      paramPlayActionButton.setEnabled(true);
      break;
      if (i > 0)
      {
        Common.Offer localOffer = DocUtils.getLowestPricedOffer(arrayOfOffer, true);
        setBuyButtonStyle(paramPlayActionButton);
        int k = paramDocument2.mDocument.docType;
        String str = null;
        int n;
        int i1;
        if (k == 19)
        {
          n = 0;
          i1 = 0;
          int i2 = arrayOfOffer.length;
          int i3 = 0;
          if (i3 < i2)
          {
            int i4 = arrayOfOffer[i3].offerType;
            if (DocUtils.OfferFilter.RENTAL.matches(i4)) {
              i1 = 1;
            }
            for (;;)
            {
              i3++;
              break;
              if (DocUtils.OfferFilter.PURCHASE.matches(i4)) {
                n = 1;
              }
            }
          }
          if ((n != 0) && (i1 != 0))
          {
            Object[] arrayOfObject5 = new Object[1];
            arrayOfObject5[0] = localOffer.formattedAmount;
            str = paramResources.getString(2131362615, arrayOfObject5);
          }
        }
        else
        {
          if (str == null)
          {
            if (!localOffer.hasFormattedAmount) {
              break label473;
            }
            str = localOffer.formattedAmount;
          }
          if (i != 1) {
            break label479;
          }
        }
        label473:
        label479:
        for (int m = localOffer.offerType;; m = 0)
        {
          paramPlayActionButton.configure(4, str, paramNavigationManager.getBuyImmediateClickListener(localAccount1, paramDocument2, m, null, null, 200, paramPlayStoreUiElementNode));
          break;
          if (n != 0)
          {
            if (i == 1)
            {
              Object[] arrayOfObject4 = new Object[1];
              arrayOfObject4[0] = localOffer.formattedAmount;
              str = paramResources.getString(2131361909, arrayOfObject4);
              break label275;
            }
            Object[] arrayOfObject3 = new Object[1];
            arrayOfObject3[0] = localOffer.formattedAmount;
            str = paramResources.getString(2131362616, arrayOfObject3);
            break label275;
          }
          str = null;
          if (i1 == 0) {
            break label275;
          }
          if (i == 1)
          {
            Object[] arrayOfObject2 = new Object[1];
            arrayOfObject2[0] = localOffer.formattedAmount;
            str = paramResources.getString(2131362654, arrayOfObject2);
            break label275;
          }
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = localOffer.formattedAmount;
          str = paramResources.getString(2131362656, arrayOfObject1);
          break label275;
          str = null;
          break label295;
        }
      }
      if ((paramDocument1 != null) && (DocUtils.getNumberOfValidOffers(paramDocument1.mDocument.offer) > 0))
      {
        paramPlayActionButton.setDrawAsLabel(true);
        paramPlayActionButton.setActionStyle(2);
        paramPlayActionButton.setEnabled(false);
        paramPlayActionButton.configure(4, paramPlayActionButton.getText().toString(), null);
        paramPlayActionButton.configure(4, 2131362716, null);
        break label98;
      }
      paramPlayActionButton.setVisibility(4);
      break label98;
    }
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    throw new IllegalStateException("unwanted children");
  }
  
  public final void collapseWithoutNotifyingListeners()
  {
    if (this.mExpandedContent == null) {
      return;
    }
    this.mExpandedContent.setVisibility(8);
  }
  
  public Document getEpisode()
  {
    return this.mEpisode;
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return this.mParentNode;
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElementProto;
  }
  
  public final boolean isExpanded()
  {
    return (this.mExpandedContent != null) && (this.mExpandedContent.getVisibility() == 0);
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    if (this.mEpisode.getTvEpisodeDetails() == null)
    {
      setVisibility(8);
      return;
    }
    updateContentView();
    setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        EpisodeSnippet.access$000(EpisodeSnippet.this);
        EpisodeSnippet.access$100(EpisodeSnippet.this);
      }
    });
  }
  
  protected void onDetachedFromWindow()
  {
    this.mHandler.removeCallbacks(this.mScrollerRunnable);
    super.onDetachedFromWindow();
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mExpandedViewStub = ((ViewStub)findViewById(2131755478));
    this.mEpisodeNumber = ((TextView)findViewById(2131755473));
    this.mBuyButton = ((PlayActionButton)findViewById(2131755378));
    this.mEpisodeTitle = ((TextView)findViewById(2131755474));
    this.mAddedState = ((TextView)findViewById(2131755477));
    this.mAddedDrawable = findViewById(2131755476);
  }
  
  public void setExpandedContentVisibility(int paramInt)
  {
    boolean bool = isExpanded();
    if (this.mExpandedContent == null)
    {
      this.mExpandedContent = this.mExpandedViewStub.inflate();
      this.mExpandedDescription = ((TextView)findViewById(2131755494));
      this.mExpandedEpisodeScreencap = ((HeroGraphicView)findViewById(2131755493));
      this.mExpandedEpisodeScreencap.setFocusable(false);
    }
    this.mExpandedContent.setVisibility(paramInt);
    if (paramInt == 8)
    {
      this.mEpisodeTitle.setMaxLines(2);
      this.mEpisodeTitle.setEllipsize(TextUtils.TruncateAt.END);
    }
    for (;;)
    {
      if (paramInt == 0)
      {
        this.mExpandedEpisodeScreencap.bindVideoThumbnail(this.mBitmapLoader, this.mEpisode);
        if (!TextUtils.isEmpty(this.mEpisode.getDescription()))
        {
          String str1 = this.mEpisode.getDescription().toString();
          if (this.mEpisode.getTvEpisodeDetails() != null)
          {
            String str2 = str1 + "\n\n";
            StringBuilder localStringBuilder = new StringBuilder().append(str2);
            Resources localResources = getResources();
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = this.mEpisode.getTvEpisodeDetails().releaseDate;
            str1 = localResources.getString(2131362461, arrayOfObject);
          }
          this.mExpandedDescription.setText(str1);
          Context localContext = getContext();
          if ((!bool) && (UiUtils.isAccessibilityEnabled(localContext))) {
            UiUtils.sendAccessibilityEventWithText(localContext, localContext.getString(2131361812), this.mExpandedDescription, true);
          }
        }
      }
      if (this.mCollapsedStateChangedListener != null) {
        this.mCollapsedStateChangedListener.onCollapsedStateChanged$25edfb42(this);
      }
      this.mHandler.post(this.mScrollerRunnable);
      return;
      this.mEpisodeTitle.setMaxLines(1000);
      this.mEpisodeTitle.setEllipsize(null);
    }
  }
  
  public final void updateContentView()
  {
    TvEpisodeDetails localTvEpisodeDetails = this.mEpisode.getTvEpisodeDetails();
    this.mEpisodeNumber.setText(Integer.toString(localTvEpisodeDetails.episodeIndex));
    TextView localTextView = this.mEpisodeNumber;
    Resources localResources = getResources();
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(localTvEpisodeDetails.episodeIndex);
    localTextView.setContentDescription(localResources.getString(2131361985, arrayOfObject));
    this.mEpisodeTitle.setText(this.mEpisode.mDocument.title);
    this.mEpisodeTitle.setMaxLines(2);
    this.mEpisodeTitle.setEllipsize(TextUtils.TruncateAt.END);
    updateBuyButtonState(getResources(), this.mBuyButton, this.mAddedState, this.mAddedDrawable, this.mSeasonDocument, this.mEpisode, this.mIsNewPurchase, this.mNavigationManager, this);
  }
  
  public static abstract interface OnCollapsedStateChanged
  {
    public abstract void onCollapsedStateChanged$25edfb42(EpisodeSnippet paramEpisodeSnippet);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.EpisodeSnippet
 * JD-Core Version:    0.7.0.1
 */