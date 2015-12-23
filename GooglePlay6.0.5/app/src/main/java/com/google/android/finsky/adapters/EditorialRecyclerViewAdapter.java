package com.google.android.finsky.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.ContainerList;
import com.google.android.finsky.api.model.DfeToc;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.MultiDfeList;
import com.google.android.finsky.layout.BucketRow;
import com.google.android.finsky.layout.EditorialDescriptionSection;
import com.google.android.finsky.layout.EditorialHeroSpacerView;
import com.google.android.finsky.layout.HeroGraphicView;
import com.google.android.finsky.layout.InsetsFrameLayout;
import com.google.android.finsky.layout.play.PlayCardClusterViewHeader;
import com.google.android.finsky.layout.play.PlayRecyclerView.ViewHolder;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.EditorialSeriesContainer;
import com.google.android.finsky.protos.VideoSnippet;
import com.google.android.finsky.utils.ClientMutationCache;
import com.google.android.finsky.utils.UiUtils;
import com.google.android.play.image.BitmapLoader;
import java.util.ArrayList;
import java.util.List;

public final class EditorialRecyclerViewAdapter<T extends ContainerList<?>>
  extends CardRecyclerViewAdapter<T>
{
  private final Document mContainerDocument;
  private final int mExtraPaddingHorizontal;
  private final int mNumItemsPerFooterRow;
  
  public EditorialRecyclerViewAdapter(Context paramContext, DfeApi paramDfeApi, NavigationManager paramNavigationManager, BitmapLoader paramBitmapLoader, DfeToc paramDfeToc, ClientMutationCache paramClientMutationCache, Document paramDocument, MultiDfeList<T> paramMultiDfeList, boolean paramBoolean, PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    super(paramContext, paramDfeApi, paramNavigationManager, paramBitmapLoader, paramDfeToc, paramClientMutationCache, paramMultiDfeList, null, null, paramBoolean, false, 2, paramPlayStoreUiElementNode, null);
    Resources localResources = paramContext.getResources();
    this.mNumItemsPerFooterRow = localResources.getInteger(2131623936);
    this.mExtraPaddingHorizontal = localResources.getDimensionPixelSize(2131492924);
    this.mContainerDocument = paramDocument;
  }
  
  private int getEditorialFooterItemCount()
  {
    return (int)Math.ceil(this.mContainerDocument.getEditorialSeriesContainer().videoSnippet.length / this.mNumItemsPerFooterRow);
  }
  
  protected final void bindLeadingSpacer(View paramView)
  {
    EditorialHeroSpacerView localEditorialHeroSpacerView = (EditorialHeroSpacerView)paramView;
    if (HeroGraphicView.getHeroGraphic(this.mContainerDocument) != null) {}
    for (boolean bool = true;; bool = false)
    {
      float f = HeroGraphicView.getHeroAspectRatio(this.mContainerDocument.mDocument.docType);
      localEditorialHeroSpacerView.mShowsHeroImage = bool;
      localEditorialHeroSpacerView.mHeroImageAspectRatio = f;
      paramView.setId(2131755059);
      return;
    }
  }
  
  protected final void computeLooseItemRowsValues(Resources paramResources)
  {
    if (this.mMultiDfeList.mContainerList.getBackendId() == 3) {}
    for (int i = 2130968723;; i = 2130968725)
    {
      this.mLooseItemCellId = i;
      this.mLooseItemColCount = paramResources.getInteger(2131623936);
      return;
    }
  }
  
  public final void configureBackgroundView(HeroGraphicView paramHeroGraphicView, int paramInt)
  {
    paramHeroGraphicView.bindDetailsEditorial(this.mContainerDocument, this.mBitmapLoader, this.mParentNode);
  }
  
  protected final void configureContainerOfLooseItemsWithoutReasons(BucketRow paramBucketRow)
  {
    paramBucketRow.setSameChildHeight(true);
  }
  
  protected final View createLeadingSpacerView(ViewGroup paramViewGroup)
  {
    return inflate(2130968724, paramViewGroup, false);
  }
  
  public final int getBackgroundViewSpacerHeight()
  {
    Context localContext = this.mContext;
    int i = this.mContext.getResources().getDisplayMetrics().widthPixels;
    if (HeroGraphicView.getHeroGraphic(this.mContainerDocument) != null) {}
    for (boolean bool = true;; bool = false)
    {
      int j = HeroGraphicView.getSpacerHeight(localContext, i, bool, HeroGraphicView.getHeroAspectRatio(this.mContainerDocument.mDocument.docType));
      if (InsetsFrameLayout.SUPPORTS_IMMERSIVE_MODE) {
        j -= UiUtils.getStatusBarHeight(this.mContext);
      }
      return j;
    }
  }
  
  protected final int getDataRowsCount()
  {
    int i = super.getDataRowsCount();
    int j = getEditorialFooterItemCount();
    if (j > 0) {
      i += j + 1;
    }
    return i;
  }
  
  public final int getItemViewType(int paramInt)
  {
    if (paramInt == 0) {
      return 21;
    }
    int i = getEditorialFooterItemCount();
    if (i > 0) {}
    int k;
    for (int j = 1;; j = 0)
    {
      k = i + 1;
      if (paramInt != 1) {
        break;
      }
      return 40;
    }
    if ((j != 0) && (!isMoreDataAvailable()))
    {
      int m = getItemCount();
      if (paramInt == m - k) {
        return 41;
      }
      if (paramInt >= m - i) {
        return 42;
      }
    }
    return super.getItemViewType(paramInt - 1);
  }
  
  public final int getPrependedRowsCount()
  {
    return 1 + super.getPrependedRowsCount();
  }
  
  public final boolean hasBackgroundView()
  {
    return true;
  }
  
  protected final boolean hasExtraLeadingSpacer()
  {
    return false;
  }
  
  public final void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    int i = paramViewHolder.mItemViewType;
    View localView = paramViewHolder.itemView;
    switch (i)
    {
    default: 
      super.onBindViewHolder(paramViewHolder, paramInt);
      return;
    case 40: 
      EditorialDescriptionSection localEditorialDescriptionSection = (EditorialDescriptionSection)localView;
      int i2 = localEditorialDescriptionSection.getResources().getColor(2131689753);
      if (this.mContainerDocument.hasImages(1)) {
        i2 = UiUtils.getFillColor(this.mContainerDocument.getEditorialSeriesContainer(), i2);
      }
      localEditorialDescriptionSection.bindEditorialDescription$5a1ee44d$316bfc12(this.mNavigationManager, this.mContainerDocument, i2);
      return;
    case 41: 
      PlayCardClusterViewHeader localPlayCardClusterViewHeader = (PlayCardClusterViewHeader)localView;
      localPlayCardClusterViewHeader.setContent(this.mMultiDfeList.mContainerList.getBackendId(), this.mContext.getString(2131362652), null, null, null);
      localPlayCardClusterViewHeader.setExtraHorizontalPadding(this.mExtraPaddingHorizontal);
      return;
    }
    BucketRow localBucketRow = (BucketRow)localView;
    int j = (-3 + (paramInt - super.getDataRowsCount() - super.getPrependedRowsCount())) * this.mNumItemsPerFooterRow;
    localBucketRow.setSameChildHeight(true);
    VideoSnippet[] arrayOfVideoSnippet = this.mContainerDocument.getEditorialSeriesContainer().videoSnippet;
    ArrayList localArrayList = new ArrayList();
    int k = 0;
    if (k < this.mNumItemsPerFooterRow)
    {
      EditorialVideoHolder localEditorialVideoHolder = (EditorialVideoHolder)localBucketRow.getChildAt(k).getTag();
      int m = j + k;
      if (m > -1 + arrayOfVideoSnippet.length) {
        localEditorialVideoHolder.mWrapper.setVisibility(4);
      }
      for (;;)
      {
        k++;
        break;
        VideoSnippet localVideoSnippet = arrayOfVideoSnippet[m];
        localEditorialVideoHolder.mWrapper.setVisibility(0);
        localEditorialVideoHolder.mTitle.setText(localVideoSnippet.title);
        localEditorialVideoHolder.mDescription.setText(localVideoSnippet.description);
        Common.Image[] arrayOfImage = localVideoSnippet.image;
        int n = arrayOfImage.length;
        String str = null;
        int i1 = 0;
        if (i1 < n)
        {
          Common.Image localImage = arrayOfImage[i1];
          if (localImage.imageType == 3) {
            str = localImage.imageUrl;
          }
          for (;;)
          {
            i1++;
            break;
            if (localImage.imageType == 1) {
              localArrayList.add(localImage);
            }
          }
        }
        if ((!TextUtils.isEmpty(str)) && (localArrayList.size() > 0))
        {
          localEditorialVideoHolder.mVideoImage.bindEditorialVideoFooter(this.mBitmapLoader, localArrayList);
          localEditorialVideoHolder.mVideoImage.showPlayIcon(str, localVideoSnippet.title, false, this.mContainerDocument.mDocument.mature, this.mContainerDocument.mDocument.backendId, this.mParentNode);
        }
        localArrayList.clear();
      }
    }
    ViewCompat.setPaddingRelative(localView, this.mExtraPaddingHorizontal, localView.getPaddingTop(), this.mExtraPaddingHorizontal, localView.getPaddingBottom());
  }
  
  public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    View localView;
    switch (paramInt)
    {
    default: 
      return super.onCreateViewHolder(paramViewGroup, paramInt);
    case 40: 
      localView = inflate(2130968726, paramViewGroup, false);
    }
    for (;;)
    {
      return new PlayRecyclerView.ViewHolder(localView);
      localView = inflate(2130968926, paramViewGroup, false);
      continue;
      localView = inflate(2130968646, paramViewGroup, false);
      for (int i = 0; i < this.mNumItemsPerFooterRow; i++)
      {
        ViewGroup localViewGroup = (ViewGroup)inflate(2130968727, (ViewGroup)localView, false);
        EditorialVideoHolder localEditorialVideoHolder = new EditorialVideoHolder((byte)0);
        localEditorialVideoHolder.mWrapper = localViewGroup;
        localEditorialVideoHolder.mVideoImage = ((HeroGraphicView)localViewGroup.findViewById(2131755462));
        localEditorialVideoHolder.mTitle = ((TextView)localViewGroup.findViewById(2131755463));
        localEditorialVideoHolder.mDescription = ((TextView)localViewGroup.findViewById(2131755464));
        localViewGroup.setTag(localEditorialVideoHolder);
        ((ViewGroup)localView).addView(localViewGroup);
      }
    }
  }
  
  private final class EditorialVideoHolder
  {
    TextView mDescription;
    TextView mTitle;
    HeroGraphicView mVideoImage;
    ViewGroup mWrapper;
    
    private EditorialVideoHolder() {}
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.adapters.EditorialRecyclerViewAdapter
 * JD-Core Version:    0.7.0.1
 */