package com.google.android.finsky.widget.consumption;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.finsky.services.ConsumptionAppDoc;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.finsky.utils.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ConsumptionAppDocList
  extends ArrayList<ConsumptionAppDoc>
  implements Parcelable
{
  public static final Parcelable.Creator<ConsumptionAppDocList> CREATOR = new Parcelable.Creator() {};
  public static final Comparator<ConsumptionAppDocList> NEWEST_FIRST = new Comparator() {};
  public final int mBackend;
  
  public ConsumptionAppDocList(int paramInt)
  {
    this.mBackend = paramInt;
  }
  
  public ConsumptionAppDocList(int paramInt, List<ConsumptionAppDoc> paramList)
  {
    this(paramInt);
    addAll(paramList);
  }
  
  private boolean add(ConsumptionAppDoc paramConsumptionAppDoc)
  {
    if (paramConsumptionAppDoc == null)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(this.mBackend);
      FinskyLog.wtf("Not adding a null document for backend=[%d]", arrayOfObject);
      return false;
    }
    return super.add(paramConsumptionAppDoc);
  }
  
  public static ConsumptionAppDocList createFromBundles(int paramInt, List<Bundle> paramList)
  {
    ArrayList localArrayList = Lists.newArrayList(paramList.size());
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext()) {
      localArrayList.add(new ConsumptionAppDoc((Bundle)localIterator.next()));
    }
    return new ConsumptionAppDocList(paramInt, localArrayList);
  }
  
  public boolean addAll(Collection<? extends ConsumptionAppDoc> paramCollection)
  {
    boolean bool = true;
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext()) {
      bool &= add((ConsumptionAppDoc)localIterator.next());
    }
    return bool;
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.mBackend);
    paramParcel.writeTypedList(this);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.consumption.ConsumptionAppDocList
 * JD-Core Version:    0.7.0.1
 */