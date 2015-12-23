package com.google.android.finsky.detailspage;

import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.protos.SectionMetadata;
import com.google.android.finsky.protos.SeriesAntenna;
import com.google.android.finsky.protos.Template;

public class AntennaAlbumsCardClusterModule
  extends SimpleCardClusterModule
{
  protected final SectionMetadata getSectionMetadata(Document paramDocument)
  {
    SeriesAntenna localSeriesAntenna = paramDocument.getTemplate().seriesAntenna;
    if (localSeriesAntenna == null) {
      return null;
    }
    return localSeriesAntenna.sectionAlbums;
  }
  
  protected final boolean supportsTwoRows()
  {
    return true;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.AntennaAlbumsCardClusterModule
 * JD-Core Version:    0.7.0.1
 */