package com.google.android.finsky.layout;

import android.accounts.Account;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.CorpusResourceUtils;
import com.google.android.finsky.utils.WishlistHelper;
import com.google.android.finsky.utils.WishlistHelper.WishlistStatusListener;

public class DetailsSummaryWishlistView
  extends ImageView
  implements WishlistHelper.WishlistStatusListener
{
  private Document mDoc;
  private boolean mIsConfigured;
  
  public DetailsSummaryWishlistView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public DetailsSummaryWishlistView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void syncVisuals(boolean paramBoolean, int paramInt)
  {
    if (paramBoolean)
    {
      setImageResource(CorpusResourceUtils.getWishlistOnDrawable(paramInt));
      setContentDescription(getContext().getString(2131362046));
      return;
    }
    int i;
    switch (paramInt)
    {
    case 5: 
    default: 
      throw new IllegalArgumentException("Unsupported backend ID (" + paramInt + ")");
    case 1: 
      i = 2130837815;
    }
    for (;;)
    {
      setImageResource(i);
      setContentDescription(getContext().getString(2131362045));
      return;
      i = 2130837819;
      continue;
      i = 2130837817;
      continue;
      i = 2130837821;
      continue;
      if (CorpusResourceUtils.isEnterpriseTheme()) {
        i = 2130837812;
      } else {
        i = 2130837811;
      }
    }
  }
  
  public final void configure(final Document paramDocument, final NavigationManager paramNavigationManager)
  {
    if (WishlistHelper.shouldHideWishlistAction(paramDocument, FinskyApp.get().getDfeApi(null)))
    {
      setVisibility(8);
      return;
    }
    this.mDoc = paramDocument;
    setVisibility(0);
    final Account localAccount = FinskyApp.get().getCurrentAccount();
    syncVisuals(WishlistHelper.isInWishlist(paramDocument, localAccount), paramDocument.mDocument.backendId);
    setOnClickListener(new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        DfeApi localDfeApi = FinskyApp.get().getDfeApi(null);
        if (WishlistHelper.isInWishlist(paramDocument, localAccount)) {}
        for (int i = 205;; i = 204)
        {
          PageFragment localPageFragment = paramNavigationManager.getActivePage();
          FinskyApp.get().getEventLogger().logClickEvent(i, null, localPageFragment);
          WishlistHelper.processWishlistClick(DetailsSummaryWishlistView.this, paramDocument, localDfeApi);
          return;
        }
      }
    });
    this.mIsConfigured = true;
  }
  
  public void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    WishlistHelper.addWishlistStatusListener(this);
  }
  
  public void onDetachedFromWindow()
  {
    WishlistHelper.removeWishlistStatusListener(this);
    super.onDetachedFromWindow();
  }
  
  public final void onWishlistStatusChanged(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((this.mIsConfigured) && (paramString.equals(this.mDoc.mDocument.docid))) {
      syncVisuals(paramBoolean1, this.mDoc.mDocument.backendId);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.DetailsSummaryWishlistView
 * JD-Core Version:    0.7.0.1
 */