package com.google.android.finsky.layout.play;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.MultiInstallActivity;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.appstate.AppStates;
import com.google.android.finsky.appstate.AppStates.AppState;
import com.google.android.finsky.appstate.PackageStateRepository.PackageState;
import com.google.android.finsky.protos.DocV2;
import com.google.android.play.layout.PlayActionButton;
import com.google.android.play.layout.PlayCardViewBase;
import java.util.Arrays;
import java.util.List;

public class PlayBundleContainerViewRow
  extends PlayCardViewBase
{
  private View mBottomRow;
  private Document mDocument;
  private PlayActionButton mInstallButton;
  private View mProgressBar;
  private boolean previousShouldShowProgressBar;
  
  public PlayBundleContainerViewRow(Context paramContext)
  {
    this(paramContext, null, 0);
  }
  
  public PlayBundleContainerViewRow(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public PlayBundleContainerViewRow(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void updateInstallButton()
  {
    Document[] arrayOfDocument = new Document[1];
    arrayOfDocument[0] = this.mDocument;
    View.OnClickListener local1 = new View.OnClickListener()
    {
      public final void onClick(View paramAnonymousView)
      {
        Context localContext = PlayBundleContainerViewRow.this.getContext();
        FinskyApp.get().getEventLogger().logClickEvent(221, null, (PlayStoreUiElementNode)PlayBundleContainerViewRow.this.getLoggingData());
        localContext.startActivity(MultiInstallActivity.getInstallIntent(localContext, this.val$docList, FinskyApp.get().getCurrentAccountName()));
      }
    };
    if (this.mDocument.mDocument.backendId == 3)
    {
      AppStates.AppState localAppState = FinskyApp.get().mAppStates.getApp(this.mDocument.mDocument.docid);
      if ((localAppState != null) && (localAppState.packageManagerState != null) && (localAppState.packageManagerState.installedVersion != -1)) {}
      for (int i = 1;; i = 0)
      {
        this.mInstallButton.setVisibility(0);
        if (i == 0) {
          break;
        }
        this.mInstallButton.setClickable(false);
        this.mInstallButton.configure(3, 2131362351, null);
        this.mInstallButton.setDrawAsLabel(true);
        return;
      }
      this.mInstallButton.configure(3, 2131362224, local1);
      this.mInstallButton.setDrawAsLabel(false);
      return;
    }
    this.mInstallButton.setVisibility(8);
  }
  
  public int getCardType()
  {
    return 19;
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mInstallButton = ((PlayActionButton)findViewById(2131755842));
    this.mProgressBar = findViewById(2131755330);
    this.mBottomRow = findViewById(2131755841);
  }
  
  public void setExtraInfo(Document paramDocument)
  {
    this.mDocument = paramDocument;
    updateInstallButton();
  }
  
  public final void setInstallerStatus(int paramInt, String paramString)
  {
    if (!this.mDocument.mDocument.docid.equals(paramString)) {
      return;
    }
    if (paramInt != -1) {
      switch (paramInt)
      {
      }
    }
    for (boolean bool = false; bool != this.previousShouldShowProgressBar; bool = true)
    {
      this.previousShouldShowProgressBar = bool;
      if (!bool) {
        break label101;
      }
      this.mBottomRow.setVisibility(4);
      this.mProgressBar.setVisibility(0);
      return;
    }
    label101:
    this.mProgressBar.setVisibility(8);
    this.mBottomRow.setVisibility(0);
    updateInstallButton();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayBundleContainerViewRow
 * JD-Core Version:    0.7.0.1
 */