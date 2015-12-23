package com.google.android.libraries.bind.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class Snapshot
{
  static final List<Data> INVALID_LIST = Collections.unmodifiableList(Collections.emptyList());
  private static final Map<Object, Integer> INVALID_MAP = Collections.emptyMap();
  final DataException exception;
  public final List<Data> list;
  public final int primaryKey;
  final Map<Object, Integer> primaryKeyIndex;
  
  Snapshot(int paramInt)
  {
    this.primaryKey = paramInt;
    this.list = INVALID_LIST;
    this.primaryKeyIndex = INVALID_MAP;
    this.exception = null;
  }
  
  public Snapshot(int paramInt, DataException paramDataException)
  {
    this.primaryKey = paramInt;
    this.exception = paramDataException;
    this.list = INVALID_LIST;
    this.primaryKeyIndex = INVALID_MAP;
  }
  
  public Snapshot(int paramInt, List<Data> paramList)
  {
    this(paramInt, paramList, indexByKey(paramList, paramInt));
  }
  
  private Snapshot(int paramInt, List<Data> paramList, Map<Object, Integer> paramMap)
  {
    this.primaryKey = paramInt;
    this.list = Collections.unmodifiableList(paramList);
    this.primaryKeyIndex = paramMap;
    this.exception = null;
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext()) {
      ((Data)localIterator.next()).frozen = true;
    }
  }
  
  private static Map<Object, Integer> indexByKey(List<Data> paramList, int paramInt)
  {
    Object localObject1;
    if (paramList == null) {
      localObject1 = INVALID_MAP;
    }
    for (;;)
    {
      return localObject1;
      if (paramList.size() == 0) {
        return Collections.emptyMap();
      }
      localObject1 = new HashMap();
      for (int i = 0; i < paramList.size(); i++)
      {
        Data localData = (Data)paramList.get(i);
        if (localData == null)
        {
          Object[] arrayOfObject3 = new Object[1];
          arrayOfObject3[0] = Integer.valueOf(i);
          throw new IllegalStateException(String.format("Entry %d has no data", arrayOfObject3));
        }
        Object localObject2 = localData.get(paramInt);
        if (localObject2 == null)
        {
          Object[] arrayOfObject2 = new Object[3];
          arrayOfObject2[0] = Integer.valueOf(i);
          arrayOfObject2[1] = Data.keyName(paramInt);
          arrayOfObject2[2] = ((Data)paramList.get(i)).toString();
          throw new IllegalStateException(String.format("Entry %d has an empty primary key %s - %s", arrayOfObject2));
        }
        Object localObject3 = ((Map)localObject1).put(localObject2, Integer.valueOf(i));
        if (localObject3 != null)
        {
          Object[] arrayOfObject1 = new Object[5];
          arrayOfObject1[0] = Data.keyName(paramInt);
          arrayOfObject1[1] = localObject2;
          arrayOfObject1[2] = localObject2.getClass().getSimpleName();
          arrayOfObject1[3] = localObject3;
          arrayOfObject1[4] = Integer.valueOf(i);
          throw new IllegalStateException(String.format("Duplicate entries for primary key %s, value %s (class %s), positions %s and %s", arrayOfObject1));
        }
      }
    }
  }
  
  public final int getCount()
  {
    return this.list.size();
  }
  
  public final boolean hasException()
  {
    return this.exception != null;
  }
  
  public final boolean isInvalidPosition(int paramInt)
  {
    return (paramInt < 0) || (paramInt >= this.list.size());
  }
  
  public final String toString()
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = getClass().getSimpleName();
    arrayOfObject[1] = Data.keyName(this.primaryKey);
    arrayOfObject[2] = Integer.valueOf(getCount());
    if (hasException()) {}
    for (String str = this.exception.getMessage();; str = "no")
    {
      arrayOfObject[3] = str;
      return String.format(localLocale, "%s - primaryKey: %s, size: %d, exception: %s", arrayOfObject);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.data.Snapshot
 * JD-Core Version:    0.7.0.1
 */