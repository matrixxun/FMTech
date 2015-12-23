package com.google.android.finsky.layout.play;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.AppStates.AppState;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.PlayCardUtils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.layout.PlayActionButton;

public class PlayBundleContainerView
  extends PlayClusterView
{
  private String mDocId;
  private PlayActionButton mInstallAll;
  private LinearLayout mInstallList;
  private TextView mTitle;
  
  public PlayBundleContainerView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public PlayBundleContainerView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public final void configureExtraContent(BitmapLoader paramBitmapLoader, NavigationManager paramNavigationManager, Document paramDocument, View.OnClickListener paramOnClickListener, PlayStoreUiElementNode paramPlayStoreUiElementNode, int paramInt, String paramString)
  {
    if (this.mDocId == paramDocument.mDocument.docid) {
      for (int i1 = 0; i1 < this.mInstallList.getChildCount(); i1++) {
        ((PlayBundleContainerViewRow)this.mInstallList.getChildAt(i1)).setInstallerStatus(paramInt, paramString);
      }
    }
    configureLogging(paramDocument.mDocument.serverLogsCookie, paramPlayStoreUiElementNode);
    this.mDocId = paramDocument.mDocument.docid;
    this.mTitle.setText(paramDocument.mDocument.title);
    this.mInstallAll.configure(3, 2131362225, paramOnClickListener);
    LayoutInflater localLayoutInflater = LayoutInflater.from(getContext());
    int i = 0;
    Document[] arrayOfDocument = paramDocument.getChildren();
    int j = arrayOfDocument.length;
    int k = 0;
    if (k < j)
    {
      Document localDocument = arrayOfDocument[k];
      PlayBundleContainerViewRow localPlayBundleContainerViewRow = (PlayBundleContainerViewRow)localLayoutInflater.inflate(2130968913, this.mInstallList, false);
      PlayCardUtils.bindCard(localPlayBundleContainerViewRow, localDocument, localDocument.mDocument.docid, paramBitmapLoader, paramNavigationManager, getPlayStoreUiElementNode());
      localPlayBundleContainerViewRow.setInstallerStatus(paramInt, paramString);
      this.mInstallList.addView(localPlayBundleContainerViewRow);
      if ((i == 0) && (localDocument.mDocument.backendId == 3))
      {
        AppStates.AppState localAppState = FinskyApp.get().mAppStates.getApp(localDocument.mDocument.docid);
        if ((localAppState == null) || (localAppState.packageManagerState == null) || (localAppState.packageManagerState.installedVersion == -1)) {
          break label278;
        }
      }
      label278:
      for (int n = 1;; n = 0)
      {
        if (n == 0) {
          i = 1;
        }
        k++;
        break;
      }
    }
    PlayActionButton localPlayActionButton = this.mInstallAll;
    if (i != 0) {}
    for (int m = 0;; m = 4)
    {
      localPlayActionButton.setVisibility(m);
      return;
    }
  }
  
  protected int getPlayStoreUiElementType()
  {
    return 421;
  }
  
  protected void onFinishInflate()
  {
    this.mInstallList = ((LinearLayout)findViewById(2131755840));
    this.mTitle = ((TextView)findViewById(2131755173));
    this.mInstallAll = ((PlayActionButton)findViewById(2131755729));
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayBundleContainerView
 * JD-Core Version:    0.7.0.1
 */