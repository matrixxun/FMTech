package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.util.Log;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzc;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemAsset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class zzaf
  extends zzc
  implements DataItem
{
  private final int zzaSn;
  
  public zzaf(DataHolder paramDataHolder, int paramInt1, int paramInt2)
  {
    super(paramDataHolder, paramInt1);
    this.zzaSn = paramInt2;
  }
  
  public final byte[] getData()
  {
    return getByteArray("data");
  }
  
  public final Uri getUri()
  {
    return Uri.parse(getString("path"));
  }
  
  public final String toString()
  {
    boolean bool = Log.isLoggable("DataItem", 3);
    byte[] arrayOfByte = getByteArray("data");
    HashMap localHashMap = new HashMap(this.zzaSn);
    for (int i = 0; i < this.zzaSn; i++)
    {
      zzac localzzac = new zzac(this.zzapd, i + this.zzars);
      if (localzzac.getString("asset_key") != null) {
        localHashMap.put(localzzac.getString("asset_key"), localzzac);
      }
    }
    StringBuilder localStringBuilder1 = new StringBuilder("DataItemInternal{ ");
    localStringBuilder1.append("uri=" + getUri());
    StringBuilder localStringBuilder2 = new StringBuilder(", dataSz=");
    if (arrayOfByte == null) {}
    for (Object localObject = "null";; localObject = Integer.valueOf(arrayOfByte.length))
    {
      localStringBuilder1.append(localObject);
      localStringBuilder1.append(", numAssets=" + localHashMap.size());
      if ((!bool) || (localHashMap.isEmpty())) {
        break label330;
      }
      localStringBuilder1.append(", assets=[");
      Iterator localIterator = localHashMap.entrySet().iterator();
      for (String str = ""; localIterator.hasNext(); str = ", ")
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        localStringBuilder1.append(str + (String)localEntry.getKey() + ": " + ((DataItemAsset)localEntry.getValue()).getId());
      }
    }
    localStringBuilder1.append("]");
    label330:
    localStringBuilder1.append(" }");
    return localStringBuilder1.toString();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.wearable.internal.zzaf
 * JD-Core Version:    0.7.0.1
 */