package com.google.android.finsky.widget.recommendation;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.ParcelUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class RecommendationList
  implements Parcelable, Collection<Recommendation>
{
  public static final Parcelable.Creator<RecommendationList> CREATOR = new Parcelable.Creator() {};
  final int mBackendId;
  public final List<Recommendation> mRecommendations = new ArrayList();
  public String mTitle;
  
  public RecommendationList(String paramString, int paramInt)
  {
    this.mTitle = paramString;
    this.mBackendId = paramInt;
  }
  
  public final boolean add(Recommendation paramRecommendation)
  {
    return this.mRecommendations.add(paramRecommendation);
  }
  
  public boolean addAll(Collection<? extends Recommendation> paramCollection)
  {
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      Recommendation localRecommendation = (Recommendation)localIterator.next();
      if ((!contains(localRecommendation)) && (!add(localRecommendation))) {
        return false;
      }
    }
    return true;
  }
  
  public void clear()
  {
    this.mRecommendations.clear();
  }
  
  public boolean contains(Object paramObject)
  {
    return this.mRecommendations.contains(paramObject);
  }
  
  public boolean containsAll(Collection<?> paramCollection)
  {
    return this.mRecommendations.containsAll(paramCollection);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public boolean isEmpty()
  {
    return this.mRecommendations.isEmpty();
  }
  
  public Iterator<Recommendation> iterator()
  {
    return this.mRecommendations.iterator();
  }
  
  public boolean remove(Object paramObject)
  {
    return this.mRecommendations.remove(paramObject);
  }
  
  public boolean removeAll(Collection<?> paramCollection)
  {
    return this.mRecommendations.removeAll(paramCollection);
  }
  
  public boolean retainAll(Collection<?> paramCollection)
  {
    return this.mRecommendations.retainAll(paramCollection);
  }
  
  public int size()
  {
    return this.mRecommendations.size();
  }
  
  public Object[] toArray()
  {
    return toArray(new Recommendation[size()]);
  }
  
  public <T> T[] toArray(T[] paramArrayOfT)
  {
    return this.mRecommendations.toArray(paramArrayOfT);
  }
  
  public String toString()
  {
    return "[" + this.mBackendId + ", " + this.mRecommendations + "]";
  }
  
  public final void writeToDisk(Context paramContext)
  {
    File localFile = RecommendationsStore.getCacheFile(paramContext, this.mBackendId);
    try
    {
      ParcelUtils.writeToDisk(localFile, this);
      return;
    }
    catch (IOException localIOException)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(this.mBackendId);
      FinskyLog.e(localIOException, "Unable to cache recs for %d", arrayOfObject);
    }
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeLong(1L);
    paramParcel.writeString(this.mTitle);
    paramParcel.writeInt(this.mBackendId);
    paramParcel.writeTypedList(this.mRecommendations);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.recommendation.RecommendationList
 * JD-Core Version:    0.7.0.1
 */