package com.google.android.finsky.layout;

import android.accounts.Account;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.fragments.PageFragment;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.WishlistHelper;
import com.google.android.finsky.utils.WishlistHelper.WishlistStatusListener;
import com.google.android.play.layout.PlayActionButton;

public class WishlistPlayActionButton
  extends PlayActionButton
  implements WishlistHelper.WishlistStatusListener
{
  public Document mDoc;
  public boolean mIsConfigured;
  public View.OnClickListener mOnWishlistClickListener;
  
  public WishlistPlayActionButton(Context paramContext)
  {
    super(paramContext);
  }
  
  public WishlistPlayActionButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
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
  
  public final void syncVisuals(boolean paramBoolean, int paramInt)
  {
    if (paramBoolean)
    {
      configure(paramInt, getContext().getString(2131362286), this.mOnWishlistClickListener);
      return;
    }
    configure(paramInt, getContext().getString(2131362285), this.mOnWishlistClickListener);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.WishlistPlayActionButton
 * JD-Core Version:    0.7.0.1
 */