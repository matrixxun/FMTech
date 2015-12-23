package com.google.android.finsky.activities.myapps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.RecyclerListener;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.activities.AppActionAnalyzer;
import com.google.android.finsky.activities.AuthenticatedActivity;
import com.google.android.finsky.adapters.PaginatedListAdapter;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.PaginatedList;
import com.google.android.finsky.appstate.PackageStateRepository;
import com.google.android.finsky.config.G;
import com.google.android.finsky.layout.AccountSelectorView;
import com.google.android.finsky.layout.IdentifiableLinearLayout;
import com.google.android.finsky.layout.play.FinskyHeaderListLayout;
import com.google.android.finsky.layout.play.PlayCardViewMyApps;
import com.google.android.finsky.layout.play.PlayCardViewMyApps.OnArchiveActionListener;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.AppDetails;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.receivers.Installer;
import com.google.android.finsky.utils.DocUtils;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.LibraryUtils;
import com.google.android.finsky.utils.PlayCardUtils;
import com.google.android.finsky.utils.Utils;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.layout.PlayCardViewBase;
import com.google.android.play.utils.config.GservicesValue;

public final class MyAppsLibraryAdapter
  extends PaginatedListAdapter
  implements AbsListView.RecyclerListener, MyAppsListAdapter
{
  private static boolean sEnableRemoveAppsFromLibrary = ((Boolean)G.enableRemoveAppsFromLibrary.get()).booleanValue();
  private final BitmapLoader mBitmapLoader;
  boolean mHasAccountSwitcher;
  private Installer mInstaller;
  private boolean mIsMultiChoiceMode;
  private final int mLeadingSpacerHeight;
  private Libraries mLibraries;
  DfeList mList;
  private final PlayCardViewMyApps.OnArchiveActionListener mOnArchiveActionListener;
  private final View.OnClickListener mOnClickListener;
  private final View.OnLongClickListener mOnLongClickListener;
  private PackageStateRepository mPackageStateRepository;
  private PlayStoreUiElementNode mParentNode = null;
  private DfeToc mToc;
  
  public MyAppsLibraryAdapter(AuthenticatedActivity paramAuthenticatedActivity, NavigationManager paramNavigationManager, DfeToc paramDfeToc, Libraries paramLibraries, PackageStateRepository paramPackageStateRepository, Installer paramInstaller, BitmapLoader paramBitmapLoader, View.OnClickListener paramOnClickListener, PlayCardViewMyApps.OnArchiveActionListener paramOnArchiveActionListener, View.OnLongClickListener paramOnLongClickListener, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    super(paramAuthenticatedActivity, paramNavigationManager);
    this.mBitmapLoader = paramBitmapLoader;
    this.mOnClickListener = paramOnClickListener;
    this.mOnArchiveActionListener = paramOnArchiveActionListener;
    this.mOnLongClickListener = paramOnLongClickListener;
    this.mParentNode = paramPlayStoreUiElementNode;
    this.mIsMultiChoiceMode = false;
    this.mToc = paramDfeToc;
    this.mLibraries = paramLibraries;
    this.mPackageStateRepository = paramPackageStateRepository;
    this.mInstaller = paramInstaller;
    this.mLeadingSpacerHeight = FinskyHeaderListLayout.getMinimumHeaderHeight$3047fd86(paramAuthenticatedActivity, 0);
  }
  
  public static Document getViewDoc(View paramView)
  {
    return (Document)paramView.getTag();
  }
  
  public final int getCount()
  {
    if (this.mList == null) {
      return 0;
    }
    int i = this.mList.getCount();
    if (isMoreDataAvailable()) {
      i++;
    }
    if (i == 0) {
      return 0;
    }
    if (this.mHasAccountSwitcher) {
      i++;
    }
    return i + 1;
  }
  
  public final Document getDocument(int paramInt)
  {
    Object localObject = getItem(paramInt);
    if ((localObject instanceof Document)) {
      return (Document)localObject;
    }
    return null;
  }
  
  public final Object getItem(int paramInt)
  {
    if (paramInt == 0) {}
    int i;
    do
    {
      return null;
      i = paramInt - 1;
    } while ((this.mHasAccountSwitcher) && (i == 0));
    if (this.mHasAccountSwitcher) {
      i--;
    }
    return this.mList.getItem(i);
  }
  
  public final int getItemViewType(int paramInt)
  {
    int i = 1;
    if (paramInt == 0)
    {
      i = 4;
      return i;
    }
    if (paramInt == -1 + getCount())
    {
      switch (this.mFooterMode)
      {
      case 1: 
      default: 
        throw new IllegalStateException("No footer or item at row " + paramInt);
      case 2: 
        return 2;
      }
      return 0;
    }
    if ((this.mHasAccountSwitcher) && (paramInt == i)) {
      return 3;
    }
    return 0;
  }
  
  protected final String getLastRequestError()
  {
    return ErrorStrings.get(this.mContext, this.mList.getVolleyError());
  }
  
  public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    Document localDocument;
    switch (getItemViewType(paramInt))
    {
    default: 
      throw new IllegalStateException("Unknown type for getView " + paramInt);
    case 4: 
      if (paramView == null) {
        paramView = inflate$42ccc377(2130968994, paramViewGroup);
      }
      paramView.getLayoutParams().height = this.mLeadingSpacerHeight;
      paramView.setId(2131755059);
      return paramView;
    case 3: 
      if (paramView == null) {
        paramView = inflate$42ccc377(2130968844, paramViewGroup);
      }
      AccountSelectorView localAccountSelectorView = (AccountSelectorView)paramView.findViewById(2131755219);
      localAccountSelectorView.configure();
      localAccountSelectorView.setIdentifier("account_switcher");
      return paramView;
    case 0: 
      localDocument = getDocument(paramInt);
      if (paramView != null) {
        break;
      }
    }
    for (View localView2 = this.mLayoutInflater.inflate(2130968941, paramViewGroup, false);; localView2 = paramView)
    {
      PlayCardViewMyApps localPlayCardViewMyApps = (PlayCardViewMyApps)localView2;
      if (localDocument == null) {
        localPlayCardViewMyApps.bindLoading();
      }
      for (;;)
      {
        localPlayCardViewMyApps.setTag(localDocument);
        localPlayCardViewMyApps.setIdentifier(localDocument.mDocument.docid);
        return localView2;
        String str = localDocument.getAppDetails().packageName;
        int i;
        label223:
        int k;
        label268:
        int m;
        label279:
        boolean bool;
        label299:
        label337:
        int n;
        if (this.mPackageStateRepository.get(str) != null)
        {
          i = 1;
          int j = this.mInstaller.getState(str);
          if ((i != 0) || (Utils.isDownloadingOrInstalling(j)) || (!LibraryUtils.isAvailable(localDocument, this.mToc, this.mLibraries))) {
            break label420;
          }
          k = 1;
          if (!this.mIsMultiChoiceMode) {
            break label426;
          }
          m = k;
          BitmapLoader localBitmapLoader = this.mBitmapLoader;
          NavigationManager localNavigationManager = this.mNavigationManager;
          if (m != 0) {
            break label432;
          }
          bool = true;
          PlayCardUtils.bindCard(localPlayCardViewMyApps, localDocument, "my_apps:library", localBitmapLoader, localNavigationManager, bool, null, this.mParentNode, true, -1, false);
          if (m == 0) {
            break label438;
          }
          localPlayCardViewMyApps.setOnClickListener(this.mOnClickListener);
          if (sEnableRemoveAppsFromLibrary)
          {
            if (this.mIsMultiChoiceMode) {
              break label564;
            }
            if (localDocument.mDocument.docType == 1) {
              break label447;
            }
            FinskyLog.wtf("Method invalid for non-ANDROID_APP docs.", new Object[0]);
            n = 0;
            label375:
            if (n == 0) {
              break label564;
            }
            localPlayCardViewMyApps.setArchivable(true, this.mOnArchiveActionListener);
          }
        }
        for (;;)
        {
          if ((this.mIsMultiChoiceMode) || (k == 0)) {
            break label574;
          }
          localPlayCardViewMyApps.setOnLongClickListener(this.mOnLongClickListener);
          break;
          i = 0;
          break label223;
          label420:
          k = 0;
          break label268;
          label426:
          m = 1;
          break label279;
          label432:
          bool = false;
          break label299;
          label438:
          localPlayCardViewMyApps.setOnClickListener(null);
          break label337;
          label447:
          Libraries localLibraries = FinskyApp.get().mLibraries;
          AppActionAnalyzer localAppActionAnalyzer = new AppActionAnalyzer(localDocument.mDocument.backendDocid, FinskyApp.get().mAppStates, localLibraries);
          if (localAppActionAnalyzer.isInstalled)
          {
            n = 0;
            break label375;
          }
          if (localAppActionAnalyzer.isRefundable)
          {
            n = 0;
            break label375;
          }
          if (DocUtils.hasAutoRenewingSubscriptions(localLibraries, localDocument.getAppDetails().packageName))
          {
            n = 0;
            break label375;
          }
          if (FinskyApp.get().mInstaller.getState(localDocument.getAppDetails().packageName) != 0)
          {
            n = 0;
            break label375;
          }
          n = 1;
          break label375;
          label564:
          localPlayCardViewMyApps.setArchivable(false, null);
        }
        label574:
        localPlayCardViewMyApps.setOnLongClickListener(null);
      }
      if (paramView != null) {}
      for (View localView1 = paramView;; localView1 = inflate$42ccc377(2130968826, paramViewGroup))
      {
        IdentifiableLinearLayout localIdentifiableLinearLayout = (IdentifiableLinearLayout)localView1;
        localIdentifiableLinearLayout.setIdentifier("loading_footer");
        return localIdentifiableLinearLayout;
      }
      return getErrorFooterView(paramView, paramViewGroup);
    }
  }
  
  public final int getViewTypeCount()
  {
    return 5;
  }
  
  protected final boolean isMoreDataAvailable()
  {
    return (this.mList != null) && (this.mList.mMoreAvailable);
  }
  
  public final void onMovedToScrapHeap(View paramView)
  {
    if ((paramView instanceof PlayCardViewBase)) {
      PlayCardUtils.recycleLoggingData((PlayCardViewBase)paramView);
    }
  }
  
  protected final void retryLoadingItems()
  {
    if (this.mList != null) {
      this.mList.retryLoadItems();
    }
  }
  
  public final void setMultiChoiceMode(boolean paramBoolean)
  {
    this.mIsMultiChoiceMode = paramBoolean;
    notifyDataSetChanged();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.myapps.MyAppsLibraryAdapter
 * JD-Core Version:    0.7.0.1
 */