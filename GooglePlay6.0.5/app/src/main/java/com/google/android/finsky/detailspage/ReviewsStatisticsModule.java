package com.google.android.finsky.detailspage;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.HistogramView;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;

public class ReviewsStatisticsModule
  extends FinskyModule<Data>
  implements View.OnClickListener
{
  public final void bindModule(boolean paramBoolean, Document paramDocument1, DfeDetails paramDfeDetails1, Document paramDocument2, DfeDetails paramDfeDetails2)
  {
    if ((paramDocument1 == null) || (TextUtils.isEmpty(paramDocument1.mDocument.reviewsUrl))) {}
    while (this.mModuleData != null) {
      return;
    }
    this.mModuleData = new Data();
    ((Data)this.mModuleData).detailsDoc = paramDocument1;
  }
  
  public final void bindView(View paramView)
  {
    ReviewsStatisticsModuleLayout localReviewsStatisticsModuleLayout = (ReviewsStatisticsModuleLayout)paramView;
    if (!localReviewsStatisticsModuleLayout.mHasBinded)
    {
      long l = ((Data)this.mModuleData).detailsDoc.getRatingCount();
      float f = ((Data)this.mModuleData).detailsDoc.getStarRating();
      int[] arrayOfInt = ((Data)this.mModuleData).detailsDoc.getRatingHistogram();
      localReviewsStatisticsModuleLayout.mParentNode = this.mParentNode;
      localReviewsStatisticsModuleLayout.mHasBinded = true;
      localReviewsStatisticsModuleLayout.mReviewStats.bind(l, f, arrayOfInt);
      localReviewsStatisticsModuleLayout.setOnClickListener(this);
    }
  }
  
  public final int getLayoutResId()
  {
    return 2130969083;
  }
  
  public void onClick(View paramView)
  {
    this.mNavigationManager.goToAllReviews(((Data)this.mModuleData).detailsDoc, ((Data)this.mModuleData).detailsDoc.mDocument.reviewsUrl, false);
  }
  
  public final boolean readyForDisplay()
  {
    if (this.mModuleData != null)
    {
      if ((((Data)this.mModuleData).detailsDoc.hasReviewHistogramData()) && (((Data)this.mModuleData).detailsDoc.getRatingCount() != 0L)) {}
      for (int i = 1; i != 0; i = 0) {
        return true;
      }
    }
    return false;
  }
  
  protected static final class Data
    extends FinskyModule.ModuleData
  {
    Document detailsDoc;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.ReviewsStatisticsModule
 * JD-Core Version:    0.7.0.1
 */