package com.google.android.libraries.bind.data;

import com.google.android.libraries.bind.async.Queues;
import java.util.List;

public final class LayoutResIdFilter
  extends BaseReadonlyFilter
{
  private final Integer originalResId;
  private final int resIdKey;
  
  public LayoutResIdFilter(int paramInt1, int paramInt2)
  {
    super(Queues.BIND_IMMEDIATE);
    this.originalResId = Integer.valueOf(paramInt1);
    this.resIdKey = paramInt2;
  }
  
  public final List<Data> transform$7ddb133f(List<Data> paramList)
  {
    if ((paramList.size() > 0) && (!this.originalResId.equals(((Data)paramList.get(0)).getAsInteger(this.resIdKey)))) {
      paramList = null;
    }
    return paramList;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.data.LayoutResIdFilter
 * JD-Core Version:    0.7.0.1
 */