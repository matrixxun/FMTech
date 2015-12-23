package com.google.android.finsky.detailspage;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.protos.SectionMetadata;
import com.google.android.finsky.protos.SeriesAntenna;
import com.google.android.finsky.protos.Template;

public class AntennaSongListModule
  extends SongListModule
{
  protected final SongListModule.Data getData(Document paramDocument)
  {
    SectionMetadata localSectionMetadata = paramDocument.getTemplate().seriesAntenna.sectionTracks;
    SongListModule.Data localData = new SongListModule.Data();
    localData.album = paramDocument;
    localData.useActualTrackNumbers = false;
    localData.highlightedSongDocId = null;
    localData.title = this.mContext.getResources().getString(2131361841);
    localData.subtitle = localSectionMetadata.header;
    localData.songsListUrl = localSectionMetadata.listUrl;
    return localData;
  }
  
  protected final boolean hasSongList(Document paramDocument)
  {
    SeriesAntenna localSeriesAntenna = paramDocument.getTemplate().seriesAntenna;
    if (localSeriesAntenna == null) {}
    while ((localSeriesAntenna.sectionTracks == null) || (TextUtils.isEmpty(localSeriesAntenna.sectionTracks.listUrl))) {
      return false;
    }
    return true;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.AntennaSongListModule
 * JD-Core Version:    0.7.0.1
 */