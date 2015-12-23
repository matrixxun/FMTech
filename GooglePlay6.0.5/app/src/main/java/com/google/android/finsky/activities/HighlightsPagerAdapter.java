package com.google.android.finsky.activities;

import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObservable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView.RecycledViewPool;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.adapters.HighlightsContentBinder;
import com.google.android.finsky.adapters.HighlightsContentBinder.HighlightsItemLoadedListener;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.ContainerList;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.config.G;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.ClusterContentBinder;
import com.google.android.finsky.layout.ClusterContentConfigurator;
import com.google.android.finsky.layout.play.PlayHighlightsBannerItemView;
import com.google.android.finsky.layout.play.PlayHighlightsBannerView;
import com.google.android.finsky.layout.play.PlayHighlightsBannerView.HighlightsBannerListener;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.IntMath;
import com.google.android.finsky.utils.ObjectMap;
import com.google.android.libraries.bind.bidi.BidiAwarePagerAdapter;
import com.google.android.libraries.bind.bidi.BidiPagingHelper;
import com.google.android.play.image.BitmapLoader;
import com.google.android.play.utils.PlayUtils;
import com.google.android.play.utils.config.GservicesValue;
import java.util.ArrayList;
import java.util.List;

public final class HighlightsPagerAdapter
  extends PagerAdapter
  implements CrossfadeTransformer.IndexablePager, BidiAwarePagerAdapter
{
  private int mCurrentLogicalPage;
  final ArrayList<HighlightsTab> mHighlightsTabList = new ArrayList();
  private boolean mIsRtl;
  
  public HighlightsPagerAdapter(DfeApi paramDfeApi, DfeList[] paramArrayOfDfeList, Context paramContext, LayoutInflater paramLayoutInflater, BitmapLoader paramBitmapLoader, NavigationManager paramNavigationManager, TabbedAdapter paramTabbedAdapter, ObjectMap paramObjectMap)
  {
    HighlightsClusterConfigurator localHighlightsClusterConfigurator = new HighlightsClusterConfigurator(paramContext.getResources());
    RecyclerView.RecycledViewPool localRecycledViewPool = new RecyclerView.RecycledViewPool();
    List localList = null;
    if (paramObjectMap != null)
    {
      boolean bool3 = paramObjectMap.containsKey("HighlightsPagerAdapter.TabsState");
      localList = null;
      if (bool3) {
        localList = paramObjectMap.getList("HighlightsPagerAdapter.TabsState");
      }
    }
    int i = 0;
    if (i < paramTabbedAdapter.getCount())
    {
      ObjectMap localObjectMap;
      label116:
      HighlightsTab localHighlightsTab;
      if ((localList != null) && (i < localList.size()))
      {
        localObjectMap = (ObjectMap)localList.get(i);
        int j = BidiPagingHelper.getLogicalPosition(paramTabbedAdapter, i);
        localHighlightsTab = new HighlightsTab(i, paramDfeApi, paramContext, paramLayoutInflater, paramBitmapLoader, paramNavigationManager, ((TabbedAdapter.TabData)paramTabbedAdapter.mTabDataList.get(j)).elementNode, localHighlightsClusterConfigurator, localRecycledViewPool, paramArrayOfDfeList[i], localObjectMap);
        if (i != this.mCurrentLogicalPage) {
          break label213;
        }
      }
      label213:
      for (boolean bool2 = true;; bool2 = false)
      {
        localHighlightsTab.setActive(bool2);
        this.mHighlightsTabList.add(localHighlightsTab);
        i++;
        break;
        localObjectMap = null;
        break label116;
      }
    }
    if (!PlayUtils.useLtr(paramContext)) {}
    for (boolean bool1 = true;; bool1 = false)
    {
      this.mIsRtl = bool1;
      return;
    }
  }
  
  public final void destroyItem(ViewGroup paramViewGroup, int paramInt, Object paramObject)
  {
    int i = BidiPagingHelper.getLogicalPosition(this, paramInt);
    HighlightsTab localHighlightsTab = (HighlightsTab)this.mHighlightsTabList.get(i);
    localHighlightsTab.mHighlightsBanner.onSaveInstanceState(localHighlightsTab.mScrollState);
    paramViewGroup.removeView(localHighlightsTab.mHighlightsBanner);
    localHighlightsTab.mHighlightsBanner = null;
    localHighlightsTab.mHighlightsContentBinder.mHighlightsItemImageLoadedListener = null;
  }
  
  public final int getCount()
  {
    return this.mHighlightsTabList.size();
  }
  
  public final int getHighlightBannerCountForPage(int paramInt)
  {
    if ((paramInt < 0) || (paramInt > this.mHighlightsTabList.size())) {
      return 0;
    }
    HighlightsTab localHighlightsTab = (HighlightsTab)this.mHighlightsTabList.get(paramInt);
    if (localHighlightsTab.mClusterDoc == null) {
      return 0;
    }
    return localHighlightsTab.mClusterDoc.getChildCount();
  }
  
  public final View getViewByIndex$7529eef0()
  {
    int i = BidiPagingHelper.getLogicalPosition(this, 0);
    return ((HighlightsTab)this.mHighlightsTabList.get(i)).mHighlightsBanner;
  }
  
  public final Object instantiateItem(ViewGroup paramViewGroup, int paramInt)
  {
    int i = BidiPagingHelper.getLogicalPosition(this, paramInt);
    HighlightsTab localHighlightsTab = (HighlightsTab)this.mHighlightsTabList.get(i);
    localHighlightsTab.mHighlightsBanner = ((PlayHighlightsBannerView)localHighlightsTab.mLayoutInflater.inflate(2130968988, paramViewGroup, false));
    paramViewGroup.addView(localHighlightsTab.mHighlightsBanner);
    localHighlightsTab.mHighlightsBanner.setHighlightBannerListener(localHighlightsTab);
    if (localHighlightsTab.mDfeList.isReady()) {
      localHighlightsTab.onDataChanged();
    }
    return localHighlightsTab;
  }
  
  public final boolean isRtl()
  {
    return this.mIsRtl;
  }
  
  public final boolean isViewFromObject(View paramView, Object paramObject)
  {
    return ((HighlightsTab)paramObject).mHighlightsBanner == paramView;
  }
  
  public final void onDestroyView(ObjectMap paramObjectMap)
  {
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < this.mHighlightsTabList.size(); i++)
    {
      HighlightsTab localHighlightsTab = (HighlightsTab)this.mHighlightsTabList.get(i);
      ObjectMap localObjectMap = new ObjectMap();
      if (localHighlightsTab.mHighlightsBanner != null) {
        localHighlightsTab.mHighlightsBanner.onSaveInstanceState(localHighlightsTab.mScrollState);
      }
      localObjectMap.put("HighlightsTab.ScrollState", localHighlightsTab.mScrollState);
      localHighlightsTab.mDfeList.removeDataChangedListener(localHighlightsTab);
      localArrayList.add(localObjectMap);
    }
    paramObjectMap.put("HighlightsPagerAdapter.TabsState", localArrayList);
  }
  
  public final void setCurrentPage(int paramInt)
  {
    this.mCurrentLogicalPage = paramInt;
    int i = 0;
    if (i < this.mHighlightsTabList.size())
    {
      HighlightsTab localHighlightsTab2 = (HighlightsTab)this.mHighlightsTabList.get(i);
      if (i == paramInt) {}
      for (boolean bool = true;; bool = false)
      {
        localHighlightsTab2.setActive(bool);
        i++;
        break;
      }
    }
    HighlightsTab localHighlightsTab1 = (HighlightsTab)this.mHighlightsTabList.get(paramInt);
    localHighlightsTab1.requestData();
    localHighlightsTab1.onCurrentBannerChanged(localHighlightsTab1.mCurrentBanner);
  }
  
  public final void setHighlightsPageListener(HighlightsPageListener paramHighlightsPageListener)
  {
    for (int i = 0; i < this.mHighlightsTabList.size(); i++) {
      ((HighlightsTab)this.mHighlightsTabList.get(i)).mPageListener = paramHighlightsPageListener;
    }
  }
  
  public final void setRtl(boolean paramBoolean)
  {
    if (this.mIsRtl != paramBoolean)
    {
      this.mIsRtl = paramBoolean;
      this.mObservable.notifyChanged();
    }
  }
  
  private final class HighlightsClusterConfigurator
    implements ClusterContentConfigurator
  {
    private final int mColorSwatchSolidHeight;
    
    public HighlightsClusterConfigurator(Resources paramResources)
    {
      this.mColorSwatchSolidHeight = paramResources.getDimensionPixelSize(2131493027);
    }
    
    public final int getChildHeight(float paramFloat1, float paramFloat2, int paramInt)
    {
      return 0;
    }
    
    public final float getChildPeekingAmount()
    {
      return 0.0F;
    }
    
    public final int getChildVerticalOffset(float paramFloat1, float paramFloat2, int paramInt)
    {
      return 0;
    }
    
    public final float getChildWidthMultiplier()
    {
      return 1.0F;
    }
    
    public final int getChildWidthPolicy()
    {
      return 1;
    }
    
    public final int getClusterHeight(int paramInt, float paramFloat)
    {
      return 0;
    }
    
    public final int getFixedChildWidth$255f288(int paramInt)
    {
      return IntMath.heightToWidth$4868d301(paramInt - this.mColorSwatchSolidHeight);
    }
    
    public final float getPrimaryChildAspectRatio(ClusterContentBinder paramClusterContentBinder)
    {
      return 1.0F;
    }
  }
  
  public static abstract interface HighlightsPageListener
  {
    public abstract void onCurrentHighlightChanged(int paramInt);
    
    public abstract void onHighlightScrolled(int paramInt);
    
    public abstract void onHighlightsContentLoaded(int paramInt1, int paramInt2);
    
    public abstract void onHighlightsContentLoading(int paramInt);
    
    public abstract void onHighlightsLoaded(int paramInt1, Document paramDocument, int paramInt2);
    
    public abstract void onScrolledToPosition(int paramInt1, int paramInt2);
  }
  
  private static final class HighlightsTab
    implements HighlightsContentBinder.HighlightsItemLoadedListener, OnDataChangedListener, PlayHighlightsBannerView.HighlightsBannerListener
  {
    private static final boolean SUPPORTS_HIGHLIGHT_ITEM_ELEVATION;
    private final BitmapLoader mBitmapLoader;
    Document mClusterDoc;
    private final Context mContext;
    int mCurrentBanner;
    private final DfeApi mDfeApi;
    DfeList mDfeList;
    private final boolean mDoNotPrefetchExtraTabs;
    private final Handler mHandler;
    private final RecyclerView.RecycledViewPool mHeapRecycledPool;
    PlayHighlightsBannerView mHighlightsBanner;
    private final ClusterContentConfigurator mHighlightsConfigurator;
    HighlightsContentBinder mHighlightsContentBinder;
    private boolean mIsActive;
    final LayoutInflater mLayoutInflater;
    private final NavigationManager mNavigationManager;
    HighlightsPagerAdapter.HighlightsPageListener mPageListener;
    private final int mPageLogicalIndex;
    private PlayStoreUiElementNode mParentNode;
    final Bundle mScrollState;
    
    static
    {
      if (Build.VERSION.SDK_INT >= 21) {}
      for (boolean bool = true;; bool = false)
      {
        SUPPORTS_HIGHLIGHT_ITEM_ELEVATION = bool;
        return;
      }
    }
    
    public HighlightsTab(int paramInt, DfeApi paramDfeApi, Context paramContext, LayoutInflater paramLayoutInflater, BitmapLoader paramBitmapLoader, NavigationManager paramNavigationManager, PlayStoreUiElementNode paramPlayStoreUiElementNode, ClusterContentConfigurator paramClusterContentConfigurator, RecyclerView.RecycledViewPool paramRecycledViewPool, DfeList paramDfeList, ObjectMap paramObjectMap)
    {
      this.mPageLogicalIndex = paramInt;
      this.mDfeApi = paramDfeApi;
      this.mContext = paramContext;
      this.mLayoutInflater = paramLayoutInflater;
      this.mBitmapLoader = paramBitmapLoader;
      this.mNavigationManager = paramNavigationManager;
      this.mHighlightsConfigurator = paramClusterContentConfigurator;
      this.mHeapRecycledPool = paramRecycledViewPool;
      this.mParentNode = paramPlayStoreUiElementNode;
      int j;
      label124:
      int k;
      if ((paramObjectMap != null) && (paramObjectMap.containsKey("HighlightsTab.ScrollState")))
      {
        this.mScrollState = ((Bundle)paramObjectMap.get("HighlightsTab.ScrollState"));
        int i = paramNavigationManager.getCurrentPageType();
        if ((!FinskyApp.get().getExperiments().isEnabled(12605163L)) && (!((Boolean)G.preventTabPreloadingOnHomePage.get()).booleanValue())) {
          break label198;
        }
        j = 1;
        if (i != 1) {
          break label204;
        }
        k = 1;
        label133:
        if ((j == 0) || (k == 0)) {
          break label210;
        }
      }
      label198:
      label204:
      label210:
      for (boolean bool = true;; bool = false)
      {
        this.mDoNotPrefetchExtraTabs = bool;
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mDfeList = paramDfeList;
        if (!this.mDoNotPrefetchExtraTabs) {
          requestData();
        }
        return;
        this.mScrollState = new Bundle();
        break;
        j = 0;
        break label124;
        k = 0;
        break label133;
      }
    }
    
    private boolean shouldNotifyListener()
    {
      return (this.mPageListener != null) && (this.mHighlightsContentBinder != null) && (this.mClusterDoc != null) && (this.mIsActive);
    }
    
    public final void onCurrentBannerChanged(int paramInt)
    {
      if (this.mCurrentBanner == paramInt) {}
      do
      {
        return;
        if (SUPPORTS_HIGHLIGHT_ITEM_ELEVATION)
        {
          Resources localResources = this.mContext.getResources();
          if ((localResources.getBoolean(2131427336)) && (this.mHighlightsBanner != null))
          {
            PlayHighlightsBannerItemView localPlayHighlightsBannerItemView1 = this.mHighlightsBanner.getViewForItemPosition(paramInt);
            if (localPlayHighlightsBannerItemView1 != null) {
              localPlayHighlightsBannerItemView1.setElevation(localResources.getDimensionPixelSize(2131492877));
            }
            PlayHighlightsBannerItemView localPlayHighlightsBannerItemView2 = this.mHighlightsBanner.getViewForItemPosition(this.mCurrentBanner);
            if (localPlayHighlightsBannerItemView2 != null) {
              localPlayHighlightsBannerItemView2.setElevation(0.0F);
            }
          }
        }
        this.mCurrentBanner = paramInt;
      } while (!shouldNotifyListener());
      int i = translatePosition(paramInt);
      this.mPageListener.onCurrentHighlightChanged(i);
      if (this.mHighlightsBanner.isItemLoaded(this.mCurrentBanner))
      {
        this.mPageListener.onHighlightsContentLoaded(this.mPageLogicalIndex, i);
        return;
      }
      this.mHandler.postDelayed(new Runnable()
      {
        public final void run()
        {
          if ((HighlightsPagerAdapter.HighlightsTab.this.mPageListener != null) && (!HighlightsPagerAdapter.HighlightsTab.this.mHighlightsBanner.isItemLoaded(HighlightsPagerAdapter.HighlightsTab.this.mCurrentBanner))) {
            HighlightsPagerAdapter.HighlightsTab.this.mPageListener.onHighlightsContentLoading(HighlightsPagerAdapter.HighlightsTab.this.mPageLogicalIndex);
          }
        }
      }, 500L);
    }
    
    public final void onDataChanged()
    {
      if ((this.mDfeList.isReady()) && (this.mHighlightsBanner != null))
      {
        this.mClusterDoc = this.mDfeList.mContainerDocument;
        this.mHighlightsContentBinder = new HighlightsContentBinder(this.mContext, this.mBitmapLoader, this.mNavigationManager, this.mClusterDoc, this.mDfeList, this.mHighlightsBanner);
        this.mHighlightsContentBinder.mHighlightsItemImageLoadedListener = this;
        this.mHighlightsBanner.createContent(this.mHighlightsContentBinder, this.mHighlightsConfigurator, 1, this.mHeapRecycledPool, this.mScrollState, this.mParentNode, this.mClusterDoc.mDocument.serverLogsCookie);
        setActive(this.mIsActive);
        if (this.mPageListener != null)
        {
          this.mPageListener.onHighlightsLoaded(this.mPageLogicalIndex, this.mClusterDoc, translatePosition(this.mCurrentBanner));
          this.mHandler.postDelayed(new Runnable()
          {
            public final void run()
            {
              if ((HighlightsPagerAdapter.HighlightsTab.this.mPageListener != null) && (!HighlightsPagerAdapter.HighlightsTab.this.mHighlightsBanner.isItemLoaded(HighlightsPagerAdapter.HighlightsTab.this.mCurrentBanner))) {
                HighlightsPagerAdapter.HighlightsTab.this.mPageListener.onHighlightsContentLoading(HighlightsPagerAdapter.HighlightsTab.this.mPageLogicalIndex);
              }
            }
          }, 500L);
        }
      }
    }
    
    public final void onHighlightsScrolled(int paramInt)
    {
      if (shouldNotifyListener()) {
        this.mPageListener.onHighlightScrolled(paramInt);
      }
    }
    
    public final void onItemImageLoaded$60682d84(final int paramInt)
    {
      this.mHandler.post(new Runnable()
      {
        public final void run()
        {
          if ((HighlightsPagerAdapter.HighlightsTab.this.mPageListener != null) && (HighlightsPagerAdapter.HighlightsTab.this.mHighlightsBanner.isItemLoaded(paramInt))) {
            HighlightsPagerAdapter.HighlightsTab.this.mPageListener.onHighlightsContentLoaded(HighlightsPagerAdapter.HighlightsTab.this.mPageLogicalIndex, HighlightsPagerAdapter.HighlightsTab.this.translatePosition(paramInt));
          }
        }
      });
    }
    
    public final void onScrolledToPosition(int paramInt1, int paramInt2)
    {
      if (shouldNotifyListener()) {
        this.mPageListener.onScrolledToPosition(translatePosition(paramInt1), paramInt2);
      }
    }
    
    public final void requestData()
    {
      if (!this.mDfeList.isReady())
      {
        this.mDfeList.addDataChangedListener(this);
        if (!this.mDfeList.isInTransit()) {
          this.mDfeList.startLoadItems();
        }
      }
    }
    
    public final void setActive(boolean paramBoolean)
    {
      this.mIsActive = paramBoolean;
      if ((this.mHighlightsBanner != null) && (paramBoolean)) {
        FinskyEventLog.requestImpressions(this.mHighlightsBanner);
      }
    }
    
    public final int translatePosition(int paramInt)
    {
      if (this.mHighlightsContentBinder == null) {
        return 0;
      }
      return this.mHighlightsContentBinder.adjustPosition(paramInt);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.activities.HighlightsPagerAdapter
 * JD-Core Version:    0.7.0.1
 */