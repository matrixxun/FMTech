package com.google.android.finsky.detailspage;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.google.android.finsky.FinskyApp;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.experiments.FinskyExperiments;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.ReviewSnippetsResponse;
import java.util.List;

public class ReviewSnippetsModule
  extends FinskyModule<Data>
  implements ReviewSnippetsModuleLayout.ReviewSnippetClickListener, PlayStoreUiElementNode
{
  private PlayStore.PlayStoreUiElement mUiElementProto = FinskyEventLog.obtainPlayStoreUiElement(1215);
  
  public final void bindModule(boolean paramBoolean, Document paramDocument1, DfeDetails paramDfeDetails1, Document paramDocument2, DfeDetails paramDfeDetails2)
  {
    if ((paramDocument1 == null) || (TextUtils.isEmpty(paramDocument1.mDocument.snippetsUrl))) {}
    while ((!paramBoolean) && (FinskyApp.get().getExperiments().isEnabled(12603844L))) {
      return;
    }
    if (this.mModuleData == null)
    {
      this.mModuleData = new Data();
      this.mDfeApi.getSnippets(paramDocument1.mDocument.snippetsUrl, new Response.Listener()new Response.ErrorListener {}, new Response.ErrorListener()
      {
        public final void onErrorResponse(VolleyError paramAnonymousVolleyError) {}
      });
    }
    ((Data)this.mModuleData).detailsDoc = paramDocument1;
  }
  
  public final void bindView(View paramView)
  {
    ReviewSnippetsModuleLayout localReviewSnippetsModuleLayout = (ReviewSnippetsModuleLayout)paramView;
    if (!localReviewSnippetsModuleLayout.hasBinded())
    {
      localReviewSnippetsModuleLayout.bind$ba0d3f4(((Data)this.mModuleData).snippetItems, ((Data)this.mModuleData).detailsDoc, this.mNavigationManager, this.mParentNode);
      localReviewSnippetsModuleLayout.setReviewSnippetClickListener(this);
      localReviewSnippetsModuleLayout.setAllReviewsClickListener(new View.OnClickListener()
      {
        public final void onClick(View paramAnonymousView)
        {
          ReviewSnippetsModule.this.mNavigationManager.goToAllReviews(((ReviewSnippetsModule.Data)ReviewSnippetsModule.this.mModuleData).detailsDoc, ((ReviewSnippetsModule.Data)ReviewSnippetsModule.this.mModuleData).detailsDoc.mDocument.reviewsUrl, false);
        }
      });
    }
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    FinskyEventLog.childImpression(this, paramPlayStoreUiElementNode);
  }
  
  public final int getLayoutResId()
  {
    return 2130969076;
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return this.mParentNode;
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mUiElementProto;
  }
  
  public final void onReviewSnippetClick(ReviewSnippetsModuleLayout.SnippetItem paramSnippetItem)
  {
    if (!TextUtils.isEmpty(paramSnippetItem.tipUrl))
    {
      FinskyApp.get().getEventLogger().logClickEvent(1216, null, this);
      this.mNavigationManager.goToAllReviews(((Data)this.mModuleData).detailsDoc, paramSnippetItem.tipUrl, false);
    }
  }
  
  public final boolean readyForDisplay()
  {
    return (this.mModuleData != null) && (((Data)this.mModuleData).snippetItems != null) && (!((Data)this.mModuleData).snippetItems.isEmpty());
  }
  
  protected static final class Data
    extends FinskyModule.ModuleData
  {
    Document detailsDoc;
    List<ReviewSnippetsModuleLayout.SnippetItem> snippetItems;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.ReviewSnippetsModule
 * JD-Core Version:    0.7.0.1
 */