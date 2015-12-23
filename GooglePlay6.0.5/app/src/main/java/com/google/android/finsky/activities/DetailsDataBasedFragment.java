package com.google.android.finsky.activities;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.detailspage.DetailsFragment2;
import com.google.android.finsky.fragments.PageFragmentHost;
import com.google.android.finsky.fragments.UrlBasedPageFragment;
import com.google.android.finsky.layout.play.GenericUiElementNode;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.previews.PreviewController;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Nfc.GingerbreadMr1NfcHandler;
import com.google.android.finsky.utils.Nfc.IcsNfcHandler;
import com.google.android.finsky.utils.Nfc.NfcHandler;
import com.google.android.finsky.utils.Nfc.NoopNfcHandler;
import com.google.android.finsky.utils.VoucherUtils;
import java.util.Collection;

public abstract class DetailsDataBasedFragment
  extends UrlBasedPageFragment
{
  public DfeDetails mDetailsData;
  public Document mDocument;
  protected GenericUiElementNode mDocumentUiElementNode = null;
  public final Libraries mLibraries = FinskyApp.get().mLibraries;
  private Nfc.NfcHandler mNfcHandler;
  private long mPageCreationTime;
  private PlayStore.PlayStoreUiElement mRootUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(getPlayStoreUiElementType());
  protected Bundle mSavedInstanceState = new Bundle();
  private boolean mSentImpression = false;
  public boolean mUseWideLayout;
  
  public final PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mRootUiElementProto;
  }
  
  public abstract int getPlayStoreUiElementType();
  
  public final boolean hasDetailsDataLoaded()
  {
    return (this.mDetailsData != null) && (this.mDetailsData.isReady());
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    boolean bool = getActivity().getPackageManager().hasSystemFeature("android.hardware.nfc");
    Object localObject;
    if ((bool) && (Build.VERSION.SDK_INT >= 14))
    {
      localObject = new Nfc.IcsNfcHandler(this, (byte)0);
      this.mNfcHandler = ((Nfc.NfcHandler)localObject);
      if (paramBundle != null) {
        this.mSavedInstanceState = paramBundle;
      }
      switchToBlank();
      rebindActionBar();
      if (this.mDetailsData != null) {
        break label115;
      }
      requestData();
    }
    for (;;)
    {
      onDataChanged();
      return;
      if ((bool) && (Build.VERSION.SDK_INT >= 10))
      {
        localObject = new Nfc.GingerbreadMr1NfcHandler(this, (byte)0);
        break;
      }
      localObject = new Nfc.NoopNfcHandler((byte)0);
      break;
      label115:
      this.mDetailsData.addDataChangedListener(this);
      this.mDetailsData.addErrorListener(this);
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    this.mPageCreationTime = System.currentTimeMillis();
    this.mDocument = ((Document)this.mArguments.getParcelable("finsky.DetailsDataBasedFragment.document"));
    super.onCreate(paramBundle);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = super.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    this.mUseWideLayout = paramViewGroup.getResources().getBoolean(2131427334);
    return localView;
  }
  
  public final void onDataChanged()
  {
    int i;
    if (isAdded())
    {
      if (this.mDocument == null) {
        break label71;
      }
      i = 1;
      if (i != 0) {
        if (hasDetailsDataLoaded())
        {
          if (this.mDetailsData.getDocument() != null) {
            break label76;
          }
          this.mPageFragmentHost.showErrorDialog(null, this.mContext.getString(2131362086), true);
        }
      }
    }
    for (;;)
    {
      this.mNfcHandler.onDataChanged();
      super.onDataChanged();
      return;
      label71:
      i = 0;
      break;
      label76:
      this.mDocument = this.mDetailsData.getDocument();
      int j = -2147483648;
      if (this.mDocument.mDocument.backendId == 2) {
        j = 3;
      }
      getActivity().setVolumeControlStream(j);
    }
  }
  
  public void onDestroyView()
  {
    if (this.mDetailsData != null)
    {
      this.mDetailsData.removeDataChangedListener(this);
      this.mDetailsData.removeErrorListener(this);
      if ((this instanceof DetailsFragment2)) {
        this.mDetailsData = null;
      }
    }
    super.onDestroyView();
  }
  
  public final void onPause()
  {
    super.onPause();
    PreviewController.setupOnBackStackChangedListener(this.mNavigationManager);
    this.mNfcHandler.onPause();
  }
  
  public final void onResume()
  {
    super.onResume();
    this.mNfcHandler.onResume();
  }
  
  public final void onSaveInstanceState(Bundle paramBundle)
  {
    if (this.mSavedInstanceState != null) {
      paramBundle.putAll(this.mSavedInstanceState);
    }
    super.onSaveInstanceState(paramBundle);
  }
  
  protected final void rebindViews()
  {
    FinskyEventLog.setServerLogCookie(this.mRootUiElementProto, this.mDetailsData.getServerLogsCookie());
    if (this.mDocument != null)
    {
      if (this.mDocumentUiElementNode == null) {
        this.mDocumentUiElementNode = new GenericUiElementNode(209, null, null, this);
      }
      this.mDocumentUiElementNode.setServerLogsCookie(this.mDocument.mDocument.serverLogsCookie);
      if ((hasDetailsDataLoaded()) && (!this.mSentImpression))
      {
        childImpression(this.mDocumentUiElementNode);
        this.mSentImpression = true;
      }
    }
    rebindViews$79e5e33f();
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = getClass().getSimpleName();
    arrayOfObject[1] = Long.valueOf(System.currentTimeMillis() - this.mPageCreationTime);
    arrayOfObject[2] = Boolean.valueOf(hasDetailsDataLoaded());
    FinskyLog.d("Page [class=%s] loaded in [%s ms] (hasDetailsDataLoaded? %b)", arrayOfObject);
  }
  
  public abstract void rebindViews$79e5e33f();
  
  public void requestData()
  {
    if (this.mDetailsData != null)
    {
      this.mDetailsData.removeDataChangedListener(this);
      this.mDetailsData.removeErrorListener(this);
    }
    Collection localCollection = VoucherUtils.getVoucherIds(FinskyApp.get().mLibraries.getAccountLibrary(this.mDfeApi.getAccount()));
    this.mDetailsData = new DfeDetails(this.mDfeApi, this.mUrl, false, localCollection);
    this.mDetailsData.addDataChangedListener(this);
    this.mDetailsData.addErrorListener(this);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.DetailsDataBasedFragment
 * JD-Core Version:    0.7.0.1
 */