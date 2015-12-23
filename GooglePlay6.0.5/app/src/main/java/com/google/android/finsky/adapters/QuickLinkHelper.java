package com.google.android.finsky.adapters;

import android.content.res.Resources;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.layout.play.PlayQuickLinkBase;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Browse.QuickLink;
import com.google.android.play.image.BitmapLoader;

public final class QuickLinkHelper
{
  public static void bindQuickLinksRow(DfeToc paramDfeToc, NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader, ViewGroup paramViewGroup, QuickLinkInfo[] paramArrayOfQuickLinkInfo, int paramInt1, int paramInt2, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    int i;
    int m;
    int n;
    label47:
    PlayQuickLinkBase localPlayQuickLinkBase;
    int i2;
    if (paramInt1 == -1 + (-1 + (paramInt2 + paramArrayOfQuickLinkInfo.length)) / paramInt2)
    {
      i = 1;
      int j = paramArrayOfQuickLinkInfo.length;
      int k = paramInt1 * paramInt2;
      m = paramViewGroup.getResources().getDimensionPixelSize(2131493476);
      n = 0;
      if (n >= paramViewGroup.getChildCount()) {
        break label135;
      }
      localPlayQuickLinkBase = (PlayQuickLinkBase)paramViewGroup.getChildAt(n);
      i2 = k + n;
      if (i2 < j) {
        break label99;
      }
      localPlayQuickLinkBase.setVisibility(4);
    }
    for (;;)
    {
      n++;
      break label47;
      i = 0;
      break;
      label99:
      localPlayQuickLinkBase.setVisibility(0);
      QuickLinkInfo localQuickLinkInfo = paramArrayOfQuickLinkInfo[i2];
      localPlayQuickLinkBase.bind(localQuickLinkInfo.mQuickLink, localQuickLinkInfo.mBackendId, paramNavigationManager, paramDfeToc, paramBitmapLoader, paramPlayStoreUiElementNode);
    }
    label135:
    if (i != 0) {}
    for (int i1 = m;; i1 = 0)
    {
      ViewCompat.setPaddingRelative(paramViewGroup, 0, 0, 0, i1);
      return;
    }
  }
  
  public static View inflateQuickLinksRow(ViewGroup paramViewGroup, LayoutInflater paramLayoutInflater, int paramInt)
  {
    ViewGroup localViewGroup = (ViewGroup)paramLayoutInflater.inflate(2130969032, paramViewGroup, false);
    for (int i = 0; i < paramInt; i++) {
      localViewGroup.addView(paramLayoutInflater.inflate(2130969031, localViewGroup, false));
    }
    return localViewGroup;
  }
  
  public static final class QuickLinkInfo
  {
    final int mBackendId;
    public final Browse.QuickLink mQuickLink;
    
    public QuickLinkInfo(Browse.QuickLink paramQuickLink, int paramInt)
    {
      this.mQuickLink = paramQuickLink;
      this.mBackendId = paramInt;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.adapters.QuickLinkHelper
 * JD-Core Version:    0.7.0.1
 */