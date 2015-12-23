package com.google.android.finsky.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.finsky.protos.OBSOLETEUserSettings;
import com.google.android.finsky.protos.PrivacySetting;
import com.google.android.finsky.protos.Toc.CorpusMetadata;
import com.google.android.finsky.protos.Toc.TocResponse;
import com.google.android.finsky.utils.ParcelableProto;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DfeToc
  extends DfeModel
  implements Parcelable
{
  public static final Parcelable.Creator<DfeToc> CREATOR = new Parcelable.Creator() {};
  private final Map<Integer, Toc.CorpusMetadata> mCorpusMap = new LinkedHashMap();
  public final Toc.TocResponse mToc;
  
  public DfeToc(Toc.TocResponse paramTocResponse)
  {
    this.mToc = paramTocResponse;
    for (Toc.CorpusMetadata localCorpusMetadata : this.mToc.corpus) {
      this.mCorpusMap.put(Integer.valueOf(localCorpusMetadata.backend), localCorpusMetadata);
    }
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public final Toc.CorpusMetadata getCorpus(int paramInt)
  {
    return (Toc.CorpusMetadata)this.mCorpusMap.get(Integer.valueOf(paramInt));
  }
  
  public final Toc.CorpusMetadata getCorpus(String paramString)
  {
    for (Toc.CorpusMetadata localCorpusMetadata : this.mToc.corpus) {
      if (localCorpusMetadata.landingUrl.equals(paramString)) {
        return localCorpusMetadata;
      }
    }
    return null;
  }
  
  public final List<Toc.CorpusMetadata> getCorpusList()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.addAll(this.mCorpusMap.values());
    return localArrayList;
  }
  
  public final PrivacySetting getUserPrivacySetting(int paramInt)
  {
    PrivacySetting localPrivacySetting;
    if ((this.mToc.userSettings == null) || (this.mToc.userSettings.privacySetting == null))
    {
      localPrivacySetting = null;
      return localPrivacySetting;
    }
    PrivacySetting[] arrayOfPrivacySetting = this.mToc.userSettings.privacySetting;
    int i = arrayOfPrivacySetting.length;
    for (int j = 0;; j++)
    {
      if (j >= i) {
        break label71;
      }
      localPrivacySetting = arrayOfPrivacySetting[j];
      if (localPrivacySetting.type == paramInt) {
        break;
      }
    }
    label71:
    return null;
  }
  
  public final String getWidgetUrl(int paramInt)
  {
    if (paramInt == 0) {
      return this.mToc.recsWidgetUrl;
    }
    Toc.CorpusMetadata localCorpusMetadata = (Toc.CorpusMetadata)this.mCorpusMap.get(Integer.valueOf(paramInt));
    if (localCorpusMetadata != null) {
      return localCorpusMetadata.recsWidgetUrl;
    }
    return null;
  }
  
  public final boolean hasCurrentUserPreviouslyOptedIn()
  {
    return this.mToc.userSettings.tosCheckboxMarketingEmailsOptedIn;
  }
  
  public final boolean isReady()
  {
    return true;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeParcelable(ParcelableProto.forProto(this.mToc), 0);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.model.DfeToc
 * JD-Core Version:    0.7.0.1
 */