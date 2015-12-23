package com.google.android.finsky.widget.recommendation;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.config.G;
import com.google.android.finsky.protos.Common.Image;
import com.google.android.finsky.protos.Dismissal;
import com.google.android.finsky.protos.DocV2;
import com.google.android.finsky.utils.PlayCardImageTypeSequence;
import com.google.android.play.utils.config.GservicesValue;
import java.util.List;

public class Recommendation
  implements Parcelable
{
  private static final int[] APP_IMAGE_TYPES = PlayCardImageTypeSequence.PROMO_IMAGE_TYPES;
  public static final Parcelable.Creator<Recommendation> CREATOR = new Parcelable.Creator() {};
  private static final int[] NONAPP_IMAGE_TYPES = { 4, 2, 0 };
  public final Document mDocument;
  final long mExpirationTimeMs;
  final Common.Image mImage;
  
  public Recommendation(Document paramDocument)
  {
    this(paramDocument, System.currentTimeMillis() + ((Long)G.recommendationTtlMs.get()).longValue());
  }
  
  Recommendation(Document paramDocument, long paramLong)
  {
    this.mDocument = paramDocument;
    this.mImage = fetchImage();
    this.mExpirationTimeMs = paramLong;
  }
  
  private Common.Image fetchImage()
  {
    int[] arrayOfInt;
    int i;
    if (this.mDocument.mDocument.backendId == 3)
    {
      arrayOfInt = APP_IMAGE_TYPES;
      i = arrayOfInt.length;
    }
    for (int j = 0;; j++)
    {
      if (j >= i) {
        break label84;
      }
      int k = arrayOfInt[j];
      List localList = this.mDocument.getImages(k);
      if ((localList != null) && (!localList.isEmpty()))
      {
        return (Common.Image)localList.get(0);
        arrayOfInt = NONAPP_IMAGE_TYPES;
        break;
      }
    }
    label84:
    return null;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof Recommendation)) {
      return false;
    }
    return ((Recommendation)paramObject).mDocument.mDocument.docid.equals(this.mDocument.mDocument.docid);
  }
  
  public int hashCode()
  {
    return 31 * this.mDocument.hashCode() + this.mImage.imageType;
  }
  
  public String toString()
  {
    if (this.mDocument.hasNeutralDismissal()) {
      return this.mDocument.getNeutralDismissal().url;
    }
    return null;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeLong(2L);
    paramParcel.writeParcelable(this.mDocument, 0);
    paramParcel.writeLong(this.mExpirationTimeMs);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.recommendation.Recommendation
 * JD-Core Version:    0.7.0.1
 */