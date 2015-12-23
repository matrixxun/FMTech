package com.google.android.finsky.detailspage;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.android.finsky.analytics.FinskyEventLog;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.DfeDetails;
import com.google.android.finsky.api.model.DfeList;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.api.model.OnDataChangedListener;
import com.google.android.finsky.layout.PlaylistControlButtons;
import com.google.android.finsky.layout.SongSnippet;
import com.google.android.finsky.layout.play.PlayStoreUiElementNode;
import com.google.android.finsky.library.Libraries;
import com.google.android.finsky.library.Libraries.Listener;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.protos.Annotations;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.protos.MusicDetails;
import com.google.android.finsky.protos.SectionMetadata;
import com.google.android.finsky.protos.SelectedChild;
import com.google.android.finsky.protos.SongDetails;
import com.google.android.finsky.utils.ErrorStrings;
import com.google.android.finsky.utils.LibraryUtils;
import com.google.android.finsky.utils.Lists;
import com.google.android.play.image.BitmapLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SongListModule
  extends FinskyModule<Data>
  implements Response.ErrorListener, OnDataChangedListener, Libraries.Listener
{
  private boolean mNeedsRefresh;
  private DfeList mSongsRequest;
  
  private static boolean hasSongListUrl(Document paramDocument)
  {
    return !TextUtils.isEmpty(paramDocument.getCoreContentListUrl());
  }
  
  private void loadSongs()
  {
    this.mSongsRequest = new DfeList(this.mDfeApi, ((Data)this.mModuleData).songsListUrl, false);
    this.mSongsRequest.addDataChangedListener(this);
    this.mSongsRequest.addErrorListener(this);
    this.mSongsRequest.startLoadItems();
  }
  
  private void updateOwnedSongs()
  {
    int i;
    int j;
    label38:
    Document localDocument;
    if (((Data)this.mModuleData).initiallyOwnedSongs == null)
    {
      i = 1;
      if (i != 0) {
        ((Data)this.mModuleData).initiallyOwnedSongs = new HashSet();
      }
      j = 0;
      if (j >= ((Data)this.mModuleData).songs.size()) {
        break label177;
      }
      localDocument = (Document)((Data)this.mModuleData).songs.get(j);
      if (LibraryUtils.isOwned(localDocument, this.mLibraries))
      {
        if (i == 0) {
          break label126;
        }
        ((Data)this.mModuleData).initiallyOwnedSongs.add(localDocument.mDocument.docid);
      }
    }
    for (;;)
    {
      j++;
      break label38;
      i = 0;
      break;
      label126:
      if (!((Data)this.mModuleData).initiallyOwnedSongs.contains(localDocument.mDocument.docid)) {
        ((Data)this.mModuleData).newPurchases.add(localDocument.mDocument.docid);
      }
    }
    label177:
    this.mNeedsRefresh = true;
    this.mFinskyModuleController.refreshModule(this, true);
  }
  
  public final void bindModule(boolean paramBoolean, Document paramDocument1, DfeDetails paramDfeDetails1, Document paramDocument2, DfeDetails paramDfeDetails2)
  {
    if (!hasSongList(paramDocument1)) {}
    while (this.mModuleData != null) {
      return;
    }
    this.mModuleData = getData(paramDocument1);
    ((Data)this.mModuleData).newPurchases = new HashSet();
    loadSongs();
  }
  
  public final void bindView(View paramView)
  {
    SongListModuleLayout localSongListModuleLayout = (SongListModuleLayout)paramView;
    String str1;
    NavigationManager localNavigationManager;
    BitmapLoader localBitmapLoader;
    Document localDocument1;
    List localList;
    boolean bool1;
    Set localSet;
    PlayStoreUiElementNode localPlayStoreUiElementNode;
    String str4;
    View localView;
    int i;
    int j;
    Object localObject1;
    if ((!localSongListModuleLayout.mHasBinded) || (this.mNeedsRefresh))
    {
      str1 = this.mDfeApi.getAccountName();
      localNavigationManager = this.mNavigationManager;
      localBitmapLoader = this.mBitmapLoader;
      localDocument1 = ((Data)this.mModuleData).album;
      localList = ((Data)this.mModuleData).songs;
      String str2 = ((Data)this.mModuleData).title;
      String str3 = ((Data)this.mModuleData).subtitle;
      bool1 = ((Data)this.mModuleData).useActualTrackNumbers;
      localSet = ((Data)this.mModuleData).newPurchases;
      localPlayStoreUiElementNode = this.mParentNode;
      str4 = ((Data)this.mModuleData).highlightedSongDocId;
      localView = localSongListModuleLayout.findViewById(2131756133);
      localSongListModuleLayout.mHeader.setText(str2);
      if (!TextUtils.isEmpty(str3))
      {
        localSongListModuleLayout.mSubHeader.setText(str3);
        localSongListModuleLayout.mSubHeader.setVisibility(0);
      }
      int i6;
      ArrayList localArrayList;
      for (;;)
      {
        i = localList.size();
        if (localDocument1.mDocument.docType != 3) {
          break label927;
        }
        i6 = Math.min(localList.size(), 5);
        localArrayList = Lists.newArrayList(i6);
        for (int i7 = 0; i7 < i6; i7++) {
          localArrayList.add(localList.get(i7));
        }
        localSongListModuleLayout.mSubHeader.setVisibility(8);
      }
      j = i6;
      localObject1 = localArrayList;
    }
    for (;;)
    {
      int k = 0;
      int m = 0;
      if (m < j)
      {
        SongDetails localSongDetails = ((Document)((List)localObject1).get(m)).getSongDetails();
        if ((localSongDetails == null) || (!localSongDetails.hasPreviewUrl) || (TextUtils.isEmpty(localSongDetails.previewUrl))) {
          break label920;
        }
      }
      label399:
      label791:
      label920:
      for (int i5 = k + 1;; i5 = k)
      {
        m++;
        k = i5;
        break;
        LayoutInflater localLayoutInflater;
        int n;
        int i1;
        boolean bool4;
        int i3;
        Object localObject2;
        label454:
        int i4;
        label469:
        boolean bool6;
        if (k < 2)
        {
          localSongListModuleLayout.mSongListControl.setVisibility(8);
          localView.setOnClickListener(null);
          localView.setClickable(false);
          boolean bool2 = SongListModuleLayout.shouldShowArtistNames(localDocument1, (List)localObject1);
          localLayoutInflater = null;
          boolean bool3 = TextUtils.isEmpty(str4);
          n = localSongListModuleLayout.mSongsContainer.getChildCount();
          i1 = 0;
          bool4 = bool3;
          if (i1 >= j) {
            break label866;
          }
          Document localDocument2 = (Document)((List)localObject1).get(i1);
          if (i1 >= n) {
            break label791;
          }
          SongSnippet localSongSnippet2 = (SongSnippet)localSongListModuleLayout.mSongsContainer.getChildAt(i1);
          localSongSnippet2.setVisibility(0);
          i3 = 0;
          localObject2 = localSongSnippet2;
          if (!bool1) {
            break label829;
          }
          i4 = localDocument2.getSongDetails().trackNumber;
          boolean bool5 = localSet.contains(localDocument2.mDocument.docid);
          ((SongSnippet)localObject2).mBitmapLoader = localBitmapLoader;
          ((SongSnippet)localObject2).mShouldShowArtistName = bool2;
          ((SongSnippet)localObject2).mAlbumDocument = localDocument1;
          ((SongSnippet)localObject2).mSongDocument = localDocument2;
          ((SongSnippet)localObject2).mNavigationManager = localNavigationManager;
          ((SongSnippet)localObject2).mTrackNumber = i4;
          ((SongSnippet)localObject2).mIsNewPurchase = bool5;
          ((SongSnippet)localObject2).mParentNode = localPlayStoreUiElementNode;
          ((SongSnippet)localObject2).mSongDetails = ((SongSnippet)localObject2).mSongDocument.getSongDetails();
          ((SongSnippet)localObject2).mMusicDetails = ((SongSnippet)localObject2).mSongDetails.details;
          if ((((SongSnippet)localObject2).mMusicDetails == null) || (((SongSnippet)localObject2).mMusicDetails.durationSec <= 0) || (TextUtils.isEmpty(((SongSnippet)localObject2).mSongDetails.previewUrl))) {
            break label838;
          }
          bool6 = true;
          label604:
          ((SongSnippet)localObject2).mIsPlayable = bool6;
          FinskyEventLog.setServerLogCookie(((SongSnippet)localObject2).mUiElementProto, ((SongSnippet)localObject2).mSongDocument.mDocument.serverLogsCookie);
          ((SongSnippet)localObject2).mParentNode.childImpression((PlayStoreUiElementNode)localObject2);
          if ((localSongListModuleLayout.mHasBinded) || (!localDocument2.mDocument.docid.equals(str4))) {
            break label844;
          }
          ((SongSnippet)localObject2).setState(2);
          label672:
          if ((!bool4) || (!((SongSnippet)localObject2).mIsPlayable)) {
            break label913;
          }
          if (!((SongSnippet)localObject2).mInitialized)
          {
            ((SongSnippet)localObject2).setState(1);
            ((SongSnippet)localObject2).mInitialized = true;
          }
        }
        for (boolean bool7 = false;; bool7 = bool4)
        {
          if (i3 != 0) {
            localSongListModuleLayout.mSongsContainer.addView((View)localObject2);
          }
          for (;;)
          {
            i1++;
            bool4 = bool7;
            break label399;
            localSongListModuleLayout.mSongListControl.setVisibility(0);
            PlaylistControlButtons localPlaylistControlButtons = localSongListModuleLayout.mSongListControl;
            localPlaylistControlButtons.mDocs = ((Collection)localObject1);
            localPlaylistControlButtons.configure(2, 2131362556, null);
            localView.setOnClickListener(new SongListModuleLayout.1(localSongListModuleLayout, SongListModuleLayout.isSongListMature((List)localObject1), str1));
            localView.setClickable(true);
            break;
            if (localLayoutInflater == null) {
              localLayoutInflater = LayoutInflater.from(localSongListModuleLayout.getContext());
            }
            SongSnippet localSongSnippet1 = (SongSnippet)localLayoutInflater.inflate(2130968830, null);
            i3 = 1;
            localObject2 = localSongSnippet1;
            break label454;
            label829:
            i4 = i1 + 1;
            break label469;
            label838:
            bool6 = false;
            break label604;
            label844:
            if (i3 == 0) {
              break label672;
            }
            ((SongSnippet)localObject2).setState(0);
            break label672;
            ((SongSnippet)localObject2).updateContentView();
          }
          label866:
          for (int i2 = ((List)localObject1).size(); i2 < n; i2++) {
            localSongListModuleLayout.mSongsContainer.getChildAt(i2).setVisibility(8);
          }
          localSongListModuleLayout.mHasBinded = true;
          this.mNeedsRefresh = false;
          return;
        }
      }
      label913:
      label927:
      localObject1 = localList;
      j = i;
    }
  }
  
  protected Data getData(Document paramDocument)
  {
    Data localData = new Data();
    localData.album = paramDocument;
    if (paramDocument.mDocument.docType == 2) {}
    for (boolean bool = true;; bool = false)
    {
      localData.useActualTrackNumbers = bool;
      SelectedChild localSelectedChild = paramDocument.getSelectedChild();
      if (localSelectedChild != null) {
        localData.highlightedSongDocId = localSelectedChild.docid;
      }
      if (!hasSongListUrl(paramDocument)) {
        break;
      }
      localData.title = paramDocument.getCoreContentHeader();
      localData.songsListUrl = paramDocument.getCoreContentListUrl();
      return localData;
    }
    Annotations localAnnotations = paramDocument.mDocument.annotations;
    if ((localAnnotations != null) && (localAnnotations.sectionRelatedDocType != null)) {}
    for (String str = localAnnotations.sectionRelatedDocType.header;; str = "")
    {
      localData.title = str;
      localData.songsListUrl = paramDocument.getRelatedDocTypeListUrl();
      return localData;
    }
  }
  
  public final int getLayoutResId()
  {
    return 2130969121;
  }
  
  protected boolean hasSongList(Document paramDocument)
  {
    int i = paramDocument.mDocument.docType;
    int j;
    if ((i == 2) || (i == 3))
    {
      j = 1;
      if (j == 0) {
        break label62;
      }
      if (!hasSongListUrl(paramDocument)) {
        if (TextUtils.isEmpty(paramDocument.getRelatedDocTypeListUrl())) {
          break label56;
        }
      }
    }
    label56:
    for (int k = 1;; k = 0)
    {
      if (k == 0) {
        break label62;
      }
      return true;
      j = 0;
      break;
    }
    label62:
    return false;
  }
  
  public final void onAllLibrariesLoaded() {}
  
  public final void onDataChanged()
  {
    int i = this.mSongsRequest.getCount();
    ArrayList localArrayList = Lists.newArrayList(i);
    for (int j = 0; j < i; j++)
    {
      Document localDocument = (Document)this.mSongsRequest.getItem(j);
      MusicDetails localMusicDetails = localDocument.getSongDetails().details;
      if ((localMusicDetails != null) && (localMusicDetails.durationSec > 0)) {
        localArrayList.add(localDocument);
      }
    }
    ((Data)this.mModuleData).songs = localArrayList;
    if (readyForDisplay()) {
      updateOwnedSongs();
    }
  }
  
  public final void onDestroyModule()
  {
    super.onDestroyModule();
    if (this.mSongsRequest != null)
    {
      this.mSongsRequest.removeDataChangedListener(this);
      this.mSongsRequest.removeErrorListener(this);
    }
    this.mLibraries.removeListener(this);
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    Toast.makeText(this.mContext, ErrorStrings.get(this.mContext, paramVolleyError), 0).show();
  }
  
  public final void onLibraryContentsChanged$40bdb4b1()
  {
    if (readyForDisplay()) {
      updateOwnedSongs();
    }
  }
  
  public final void onRecycleView(View paramView)
  {
    ((SongListModuleLayout)paramView).mHasBinded = false;
  }
  
  public final void onRestoreModuleData(FinskyModule.ModuleData paramModuleData)
  {
    super.onRestoreModuleData(paramModuleData);
    if ((this.mModuleData != null) && (((Data)this.mModuleData).songs == null)) {
      loadSongs();
    }
    this.mLibraries.addListener(this);
  }
  
  public final boolean readyForDisplay()
  {
    return (this.mModuleData != null) && (((Data)this.mModuleData).songs != null) && (!((Data)this.mModuleData).songs.isEmpty());
  }
  
  protected static final class Data
    extends FinskyModule.ModuleData
  {
    Document album;
    String highlightedSongDocId;
    Set<String> initiallyOwnedSongs;
    Set<String> newPurchases;
    List<Document> songs;
    String songsListUrl;
    String subtitle;
    String title;
    boolean useActualTrackNumbers;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.SongListModule
 * JD-Core Version:    0.7.0.1
 */