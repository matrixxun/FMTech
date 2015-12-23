package com.google.android.finsky.detailspage;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.analytics.PlayStore.PlayStoreUiElement;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.layout.EpisodeSnippet;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.Libraries.Listener;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.DocDetails.DocumentDetails;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.SelectedChild;
import com.google.android.finsky.protos.TvSeasonDetails;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.LibraryUtils;
import com.google.android.finsky.utils.Lists;
import com.google.android.play.image.BitmapLoader;
import com.google.protobuf.nano.WireFormatNano;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EpisodeListModule
  extends FinskyModule<Data>
  implements EpisodeListModuleLayout.EpisodeSelectionListener, PlayStoreUiElementNode, Libraries.Listener
{
  private OnDataChangedListener mEpisodesDataListener = new OnDataChangedListener()
  {
    public final void onDataChanged()
    {
      ArrayList localArrayList = Lists.newArrayList(EpisodeListModule.this.mEpisodesRequest.getCount());
      for (int i = 0; i < EpisodeListModule.this.mEpisodesRequest.getCount(); i++) {
        localArrayList.add(EpisodeListModule.this.mEpisodesRequest.getItem(i));
      }
      ((EpisodeListModule.Data)EpisodeListModule.this.mModuleData).selectedSeasonEpisodes = localArrayList;
      EpisodeListModule.this.refreshInitiallyOwnedEpisodes();
      if (EpisodeListModule.this.mPreSelectedEpisodeId != null)
      {
        Iterator localIterator = ((EpisodeListModule.Data)EpisodeListModule.this.mModuleData).selectedSeasonEpisodes.iterator();
        while (localIterator.hasNext())
        {
          Document localDocument = (Document)localIterator.next();
          if (localDocument.mDocument.docid.equals(EpisodeListModule.this.mPreSelectedEpisodeId))
          {
            ((EpisodeListModule.Data)EpisodeListModule.this.mModuleData).selectedEpisode = localDocument;
            EpisodeListModule.access$502$1e6ef1a0(EpisodeListModule.this);
          }
        }
      }
      EpisodeListModule.access$602$32bcef80(EpisodeListModule.this);
      EpisodeListModule.access$702$574d4520(EpisodeListModule.this);
      EpisodeListModule.access$802$69fc1ba2(EpisodeListModule.this);
      EpisodeListModule.this.mFinskyModuleController.refreshModule(EpisodeListModule.this, true);
    }
  };
  private Response.ErrorListener mEpisodesErrorListener = new Response.ErrorListener()
  {
    public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
    {
      EpisodeListModule.access$802$69fc1ba2(EpisodeListModule.this);
      ((EpisodeListModule.Data)EpisodeListModule.this.mModuleData).selectedSeason = EpisodeListModule.this.mOldSeason;
      ((EpisodeListModule.Data)EpisodeListModule.this.mModuleData).selectedSeasonEpisodes = EpisodeListModule.this.mOldEpisodes;
      EpisodeListModule.this.mFinskyModuleController.refreshModule(EpisodeListModule.this, true);
      Toast.makeText(EpisodeListModule.this.mContext, ErrorStrings.get(EpisodeListModule.this.mContext, paramAnonymousVolleyError), 0).show();
    }
  };
  private DfeList mEpisodesRequest;
  private boolean mNeedsRefresh;
  private List<Document> mOldEpisodes;
  private Document mOldSeason;
  private PlayStore.PlayStoreUiElement mPlayStoreUiElement = FinskyEventLog.obtainPlayStoreUiElement(211);
  private String mPreSelectedEpisodeId;
  private String mPreSelectedSeasonId;
  private OnDataChangedListener mSeasonsDataListener = new OnDataChangedListener()
  {
    public final void onDataChanged()
    {
      int i = EpisodeListModule.this.mSeasonsRequest.getCount();
      if (i == 0) {}
      do
      {
        return;
        ArrayList localArrayList = new ArrayList();
        for (int j = 0; j < i; j++)
        {
          Document localDocument = (Document)EpisodeListModule.this.mSeasonsRequest.getItem(j);
          if ((((EpisodeListModule.Data)EpisodeListModule.this.mModuleData).selectedSeason == null) && (localDocument.mDocument.docid.equals(EpisodeListModule.this.mPreSelectedSeasonId)))
          {
            ((EpisodeListModule.Data)EpisodeListModule.this.mModuleData).selectedSeason = localDocument;
            EpisodeListModule.this.mFinskyModuleController.broadcastData("EpisodeListModule.SeasonDocument", ((EpisodeListModule.Data)EpisodeListModule.this.mModuleData).selectedSeason);
          }
          localArrayList.add(localDocument);
        }
        ((EpisodeListModule.Data)EpisodeListModule.this.mModuleData).seasons = localArrayList;
        if (((EpisodeListModule.Data)EpisodeListModule.this.mModuleData).selectedSeason == null)
        {
          ((EpisodeListModule.Data)EpisodeListModule.this.mModuleData).selectedSeason = ((Document)localArrayList.get(0));
          EpisodeListModule.this.mFinskyModuleController.broadcastData("EpisodeListModule.SeasonDocument", ((EpisodeListModule.Data)EpisodeListModule.this.mModuleData).selectedSeason);
        }
      } while (((EpisodeListModule.Data)EpisodeListModule.this.mModuleData).selectedSeasonEpisodes != null);
      EpisodeListModule.this.loadEpisodesForSelectedSeason();
    }
  };
  private Response.ErrorListener mSeasonsErrorListener = new Response.ErrorListener()
  {
    public final void onErrorResponse(VolleyError paramAnonymousVolleyError)
    {
      Toast.makeText(EpisodeListModule.this.mContext, ErrorStrings.get(EpisodeListModule.this.mContext, paramAnonymousVolleyError), 0).show();
    }
  };
  private DfeList mSeasonsRequest;
  
  private void loadEpisodesForSelectedSeason()
  {
    if (this.mEpisodesRequest != null)
    {
      this.mEpisodesRequest.removeDataChangedListener(this.mEpisodesDataListener);
      this.mEpisodesRequest.removeErrorListener(this.mEpisodesErrorListener);
    }
    this.mEpisodesRequest = new DfeList(this.mDfeApi, ((Data)this.mModuleData).selectedSeason.getCoreContentListUrl(), false);
    this.mEpisodesRequest.addDataChangedListener(this.mEpisodesDataListener);
    this.mEpisodesRequest.addErrorListener(this.mEpisodesErrorListener);
    this.mEpisodesRequest.startLoadItems();
    if (this.mOldEpisodes != null)
    {
      this.mNeedsRefresh = true;
      this.mFinskyModuleController.refreshModule(this, false);
    }
  }
  
  private void refreshInitiallyOwnedEpisodes()
  {
    if (((Data)this.mModuleData).selectedSeason == null) {}
    String str;
    HashSet localHashSet;
    for (;;)
    {
      return;
      str = ((Data)this.mModuleData).selectedSeason.mDocument.docid;
      if (!((Data)this.mModuleData).initiallyOwnedEpisodes.containsKey(str)) {}
      for (int i = 1; (i != 0) && (((Data)this.mModuleData).selectedSeasonEpisodes != null); i = 0)
      {
        localHashSet = new HashSet();
        Iterator localIterator = ((Data)this.mModuleData).selectedSeasonEpisodes.iterator();
        while (localIterator.hasNext())
        {
          Document localDocument = (Document)localIterator.next();
          if (LibraryUtils.isOwned(localDocument, this.mLibraries)) {
            localHashSet.add(localDocument.mDocument.docid);
          }
        }
      }
    }
    ((Data)this.mModuleData).initiallyOwnedEpisodes.put(str, localHashSet);
  }
  
  public final void bindModule(boolean paramBoolean, Document paramDocument1, DfeDetails paramDfeDetails1, Document paramDocument2, DfeDetails paramDfeDetails2)
  {
    int i;
    if ((paramDocument1.mDocument.docType == 18) && (!TextUtils.isEmpty(paramDocument1.getCoreContentListUrl())))
    {
      i = 1;
      if (i != 0) {
        break label37;
      }
    }
    label37:
    while (this.mModuleData != null)
    {
      return;
      i = 0;
      break;
    }
    this.mModuleData = new Data();
    SelectedChild localSelectedChild = paramDocument1.getSelectedChild();
    if (localSelectedChild != null)
    {
      this.mPreSelectedSeasonId = localSelectedChild.docid;
      if (localSelectedChild.selectedChild != null) {
        this.mPreSelectedEpisodeId = localSelectedChild.selectedChild.docid;
      }
    }
    ((Data)this.mModuleData).initiallyOwnedEpisodes = new HashMap();
    ((Data)this.mModuleData).seasonListUrl = paramDocument1.getCoreContentListUrl();
    this.mSeasonsRequest = new DfeList(this.mDfeApi, ((Data)this.mModuleData).seasonListUrl, false);
    this.mSeasonsRequest.addDataChangedListener(this.mSeasonsDataListener);
    this.mSeasonsRequest.addErrorListener(this.mSeasonsErrorListener);
    this.mSeasonsRequest.startLoadItems();
    this.mLibraries.addListener(this);
  }
  
  public final void bindView(View paramView)
  {
    EpisodeListModuleLayout localEpisodeListModuleLayout = (EpisodeListModuleLayout)paramView;
    Set localSet;
    BitmapLoader localBitmapLoader1;
    NavigationManager localNavigationManager1;
    List localList1;
    Document localDocument1;
    List localList2;
    Document localDocument2;
    Libraries localLibraries;
    if ((!localEpisodeListModuleLayout.mHasBinded) || (this.mNeedsRefresh))
    {
      localSet = (Set)((Data)this.mModuleData).initiallyOwnedEpisodes.get(((Data)this.mModuleData).selectedSeason.mDocument.docid);
      localBitmapLoader1 = this.mBitmapLoader;
      localNavigationManager1 = this.mNavigationManager;
      localList1 = ((Data)this.mModuleData).seasons;
      localDocument1 = ((Data)this.mModuleData).selectedSeason;
      localList2 = ((Data)this.mModuleData).selectedSeasonEpisodes;
      localDocument2 = ((Data)this.mModuleData).selectedEpisode;
      localLibraries = this.mLibraries;
      if (this.mOldEpisodes == null) {
        break label319;
      }
    }
    label319:
    for (int i = 1;; i = 0)
    {
      localEpisodeListModuleLayout.mHasBinded = true;
      localEpisodeListModuleLayout.mBitmapLoader = localBitmapLoader1;
      localEpisodeListModuleLayout.mPlayStoreUiElementNode = this;
      localEpisodeListModuleLayout.mNavigationManager = localNavigationManager1;
      localEpisodeListModuleLayout.mEpisodeSelectionListener = this;
      if (localEpisodeListModuleLayout.mSeasons != localList1)
      {
        localEpisodeListModuleLayout.mSeasons = localList1;
        localEpisodeListModuleLayout.mSeasonSpinner.setAdapter(new EpisodeListModuleLayout.SeasonListAdapter(localEpisodeListModuleLayout, localEpisodeListModuleLayout.getContext(), localEpisodeListModuleLayout.mSeasons));
      }
      if (localEpisodeListModuleLayout.mCurrentSeason != localDocument1)
      {
        localEpisodeListModuleLayout.mCurrentSeason = localDocument1;
        localEpisodeListModuleLayout.mSeasonSpinner.setSelection(localList1.indexOf(localDocument1));
        EpisodeSnippet.updateBuyButtonState(localEpisodeListModuleLayout.getResources(), localEpisodeListModuleLayout.mBuyButton, null, null, null, localDocument1, false, localEpisodeListModuleLayout.mNavigationManager, localEpisodeListModuleLayout.mPlayStoreUiElementNode);
      }
      if (localList1.size() <= 1)
      {
        localEpisodeListModuleLayout.mSeasonSpinner.setClickable(false);
        localEpisodeListModuleLayout.mSeasonSpinner.setBackgroundResource(0);
      }
      if (i == 0) {
        break;
      }
      if (localEpisodeListModuleLayout.mLoadingOverlay != null)
      {
        localEpisodeListModuleLayout.mLoadingOverlay.setVisibility(0);
        localEpisodeListModuleLayout.mLoadingOverlay.setOnClickListener(new EpisodeListModuleLayout.2(localEpisodeListModuleLayout));
      }
      this.mNeedsRefresh = false;
      return;
    }
    Object localObject1 = null;
    int j = localList2.size();
    int k = localEpisodeListModuleLayout.mEpisodesContainer.getChildCount();
    Object localObject2 = null;
    int m = 0;
    label352:
    int i2;
    Object localObject3;
    Object localObject4;
    label420:
    Object localObject5;
    label431:
    boolean bool3;
    label469:
    Object localObject6;
    if (m < j)
    {
      Document localDocument3 = (Document)localList2.get(m);
      if (m < k)
      {
        EpisodeSnippet localEpisodeSnippet2 = (EpisodeSnippet)localEpisodeListModuleLayout.mEpisodesContainer.getChildAt(m);
        if (localEpisodeSnippet2.getEpisode() == localDocument3) {
          break label857;
        }
        localEpisodeSnippet2.collapseWithoutNotifyingListeners();
        i2 = 0;
        localObject3 = localObject2;
        localObject4 = localEpisodeSnippet2;
        if (localDocument3 != localDocument2) {
          break label850;
        }
        localObject5 = localObject4;
        boolean bool1 = LibraryUtils.isOwned(localDocument3, localLibraries);
        boolean bool2 = localSet.contains(localDocument3.mDocument.docid);
        if ((!bool1) || (bool2)) {
          break label650;
        }
        bool3 = true;
        BitmapLoader localBitmapLoader2 = localEpisodeListModuleLayout.mBitmapLoader;
        NavigationManager localNavigationManager2 = localEpisodeListModuleLayout.mNavigationManager;
        PlayStoreUiElementNode localPlayStoreUiElementNode = localEpisodeListModuleLayout.mPlayStoreUiElementNode;
        ((EpisodeSnippet)localObject4).mSeasonDocument = localDocument1;
        ((EpisodeSnippet)localObject4).mEpisode = localDocument3;
        ((EpisodeSnippet)localObject4).mNavigationManager = localNavigationManager2;
        ((EpisodeSnippet)localObject4).mBitmapLoader = localBitmapLoader2;
        ((EpisodeSnippet)localObject4).mIsNewPurchase = bool3;
        ((EpisodeSnippet)localObject4).mCollapsedStateChangedListener = localEpisodeListModuleLayout;
        ((EpisodeSnippet)localObject4).mParentNode = localPlayStoreUiElementNode;
        FinskyEventLog.setServerLogCookie(((EpisodeSnippet)localObject4).mUiElementProto, localDocument3.mDocument.serverLogsCookie);
        ((EpisodeSnippet)localObject4).mParentNode.childImpression((PlayStoreUiElementNode)localObject4);
        if (i2 == 0) {
          break label656;
        }
        localEpisodeListModuleLayout.mEpisodesContainer.addView((View)localObject4, m);
        label580:
        ((EpisodeSnippet)localObject4).setVisibility(0);
        localObject6 = localObject3;
        localObject1 = localObject5;
      }
    }
    for (;;)
    {
      m++;
      localObject2 = localObject6;
      break label352;
      if (localObject2 == null) {
        localObject2 = LayoutInflater.from(localEpisodeListModuleLayout.getContext());
      }
      EpisodeSnippet localEpisodeSnippet1 = (EpisodeSnippet)((LayoutInflater)localObject2).inflate(2130968729, localEpisodeListModuleLayout.mEpisodesContainer, false);
      i2 = 1;
      localObject3 = localObject2;
      localObject4 = localEpisodeSnippet1;
      break label420;
      label650:
      bool3 = false;
      break label469;
      label656:
      ((EpisodeSnippet)localObject4).updateContentView();
      break label580;
      for (int n = j; n < k; n++) {
        localEpisodeListModuleLayout.mEpisodesContainer.getChildAt(n).setVisibility(8);
      }
      if ((localObject1 != null) && (!localObject1.isExpanded())) {
        localObject1.setExpandedContentVisibility(0);
      }
      localEpisodeListModuleLayout.mEpisodesContainer.refreshDrawableState();
      TvSeasonDetails localTvSeasonDetails;
      label750:
      int i1;
      if (EpisodeSnippet.hasSeasonOffer(localDocument1)) {
        if (localDocument1.hasDetails())
        {
          localTvSeasonDetails = localDocument1.mDocument.details.tvSeasonDetails;
          if ((localDocument1.mDocument.docType != 19) || (localTvSeasonDetails == null) || (!localTvSeasonDetails.hasInProgress) || (!localTvSeasonDetails.inProgress)) {
            break label832;
          }
          i1 = 1;
          label787:
          if (i1 == 0) {
            break label838;
          }
          localEpisodeListModuleLayout.mInProgressSnippet.setVisibility(0);
        }
      }
      for (;;)
      {
        if (localEpisodeListModuleLayout.mLoadingOverlay == null) {
          break label848;
        }
        localEpisodeListModuleLayout.mLoadingOverlay.setVisibility(4);
        localEpisodeListModuleLayout.mLoadingOverlay.setOnClickListener(null);
        break;
        localTvSeasonDetails = null;
        break label750;
        label832:
        i1 = 0;
        break label787;
        label838:
        localEpisodeListModuleLayout.mInProgressSnippet.setVisibility(8);
      }
      label848:
      break;
      label850:
      localObject5 = localObject1;
      break label431;
      label857:
      localObject6 = localObject2;
    }
  }
  
  public final void childImpression(PlayStoreUiElementNode paramPlayStoreUiElementNode)
  {
    FinskyEventLog.childImpression(this, paramPlayStoreUiElementNode);
  }
  
  public final int getLayoutResId()
  {
    return 2130968728;
  }
  
  public PlayStoreUiElementNode getParentNode()
  {
    return this.mParentNode;
  }
  
  public PlayStore.PlayStoreUiElement getPlayStoreUiElement()
  {
    return this.mPlayStoreUiElement;
  }
  
  public final void onAllLibrariesLoaded() {}
  
  public final void onDestroyModule()
  {
    this.mLibraries.removeListener(this);
    if (this.mSeasonsRequest != null)
    {
      this.mSeasonsRequest.removeDataChangedListener(this.mSeasonsDataListener);
      this.mSeasonsRequest.removeErrorListener(this.mSeasonsErrorListener);
    }
    if (this.mEpisodesRequest != null)
    {
      this.mEpisodesRequest.removeDataChangedListener(this.mEpisodesDataListener);
      this.mEpisodesRequest.removeErrorListener(this.mEpisodesErrorListener);
    }
  }
  
  public final void onEpisodeSelected(Document paramDocument)
  {
    ((Data)this.mModuleData).selectedEpisode = paramDocument;
  }
  
  public final void onLibraryContentsChanged$40bdb4b1()
  {
    refreshInitiallyOwnedEpisodes();
  }
  
  public final void onRestoreModuleData(FinskyModule.ModuleData paramModuleData)
  {
    super.onRestoreModuleData(paramModuleData);
    if (this.mModuleData != null)
    {
      this.mLibraries.addListener(this);
      if (((Data)this.mModuleData).seasons != null) {
        break label89;
      }
      this.mSeasonsRequest = new DfeList(this.mDfeApi, ((Data)this.mModuleData).seasonListUrl, false);
      this.mSeasonsRequest.addDataChangedListener(this.mSeasonsDataListener);
      this.mSeasonsRequest.addErrorListener(this.mSeasonsErrorListener);
      this.mSeasonsRequest.startLoadItems();
    }
    label89:
    while (((Data)this.mModuleData).selectedSeasonEpisodes != null) {
      return;
    }
    loadEpisodesForSelectedSeason();
  }
  
  public final void onSeasonSelected(Document paramDocument)
  {
    if (((Data)this.mModuleData).selectedSeason != paramDocument)
    {
      this.mOldSeason = ((Data)this.mModuleData).selectedSeason;
      this.mOldEpisodes = ((Data)this.mModuleData).selectedSeasonEpisodes;
      ((Data)this.mModuleData).selectedSeason = paramDocument;
      ((Data)this.mModuleData).selectedSeasonEpisodes = null;
      ((Data)this.mModuleData).selectedEpisode = null;
      loadEpisodesForSelectedSeason();
      if ((this.mPlayStoreUiElement.serverLogsCookie.length != 0) && (!Arrays.equals(this.mPlayStoreUiElement.serverLogsCookie, ((Data)this.mModuleData).selectedSeason.mDocument.serverLogsCookie)))
      {
        this.mPlayStoreUiElement.child = PlayStore.PlayStoreUiElement.emptyArray();
        this.mPlayStoreUiElement.hasServerLogsCookie = false;
        this.mPlayStoreUiElement.serverLogsCookie = WireFormatNano.EMPTY_BYTES;
      }
      FinskyEventLog.setServerLogCookie(this.mPlayStoreUiElement, ((Data)this.mModuleData).selectedSeason.mDocument.serverLogsCookie);
    }
  }
  
  public final boolean readyForDisplay()
  {
    return (this.mModuleData != null) && ((((Data)this.mModuleData).selectedSeasonEpisodes != null) || (this.mOldEpisodes != null));
  }
  
  protected static final class Data
    extends FinskyModule.ModuleData
  {
    Map<String, Set<String>> initiallyOwnedEpisodes;
    String seasonListUrl;
    List<Document> seasons;
    Document selectedEpisode;
    Document selectedSeason;
    List<Document> selectedSeasonEpisodes;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.EpisodeListModule
 * JD-Core Version:    0.7.0.1
 */