package android.support.v4.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class MapCollections<K, V>
{
  MapCollections<K, V>.android.support.v4.util.MapCollections.android.support.v4.util.MapCollections.android.support.v4.util.MapCollections.EntrySet mEntrySet;
  MapCollections<K, V>.android.support.v4.util.MapCollections.android.support.v4.util.MapCollections.android.support.v4.util.MapCollections.KeySet mKeySet;
  MapCollections<K, V>.android.support.v4.util.MapCollections.android.support.v4.util.MapCollections.android.support.v4.util.MapCollections.ValuesCollection mValues;
  
  public static <T> boolean equalsSetHelper(Set<T> paramSet, Object paramObject)
  {
    if (paramSet == paramObject) {}
    for (;;)
    {
      return true;
      if ((paramObject instanceof Set))
      {
        Set localSet = (Set)paramObject;
        try
        {
          if (paramSet.size() == localSet.size())
          {
            boolean bool = paramSet.containsAll(localSet);
            if (bool) {}
          }
          else
          {
            return false;
          }
        }
        catch (NullPointerException localNullPointerException)
        {
          return false;
        }
        catch (ClassCastException localClassCastException)
        {
          return false;
        }
      }
    }
    return false;
  }
  
  public static <K, V> boolean retainAllHelper(Map<K, V> paramMap, Collection<?> paramCollection)
  {
    int i = paramMap.size();
    Iterator localIterator = paramMap.keySet().iterator();
    while (localIterator.hasNext()) {
      if (!paramCollection.contains(localIterator.next())) {
        localIterator.remove();
      }
    }
    return i != paramMap.size();
  }
  
  protected abstract void colClear();
  
  protected abstract Object colGetEntry(int paramInt1, int paramInt2);
  
  protected abstract Map<K, V> colGetMap();
  
  protected abstract int colGetSize();
  
  protected abstract int colIndexOfKey(Object paramObject);
  
  protected abstract int colIndexOfValue(Object paramObject);
  
  protected abstract void colPut(K paramK, V paramV);
  
  protected abstract void colRemoveAt(int paramInt);
  
  protected abstract V colSetValue(int paramInt, V paramV);
  
  public final Object[] toArrayHelper(int paramInt)
  {
    int i = colGetSize();
    Object[] arrayOfObject = new Object[i];
    for (int j = 0; j < i; j++) {
      arrayOfObject[j] = colGetEntry(j, paramInt);
    }
    return arrayOfObject;
  }
  
  public final <T> T[] toArrayHelper(T[] paramArrayOfT, int paramInt)
  {
    int i = colGetSize();
    if (paramArrayOfT.length < i) {
      paramArrayOfT = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), i);
    }
    for (int j = 0; j < i; j++) {
      paramArrayOfT[j] = colGetEntry(j, paramInt);
    }
    if (paramArrayOfT.length > i) {
      paramArrayOfT[i] = null;
    }
    return paramArrayOfT;
  }
  
  final class ArrayIterator<T>
    implements Iterator<T>
  {
    boolean mCanRemove = false;
    int mIndex;
    final int mOffset;
    int mSize;
    
    ArrayIterator(int paramInt)
    {
      this.mOffset = paramInt;
      this.mSize = MapCollections.this.colGetSize();
    }
    
    public final boolean hasNext()
    {
      return this.mIndex < this.mSize;
    }
    
    public final T next()
    {
      Object localObject = MapCollections.this.colGetEntry(this.mIndex, this.mOffset);
      this.mIndex = (1 + this.mIndex);
      this.mCanRemove = true;
      return localObject;
    }
    
    public final void remove()
    {
      if (!this.mCanRemove) {
        throw new IllegalStateException();
      }
      this.mIndex = (-1 + this.mIndex);
      this.mSize = (-1 + this.mSize);
      this.mCanRemove = false;
      MapCollections.this.colRemoveAt(this.mIndex);
    }
  }
  
  final class EntrySet
    implements Set<Map.Entry<K, V>>
  {
    EntrySet() {}
    
    public final boolean addAll(Collection<? extends Map.Entry<K, V>> paramCollection)
    {
      int i = MapCollections.this.colGetSize();
      Iterator localIterator = paramCollection.iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        MapCollections.this.colPut(localEntry.getKey(), localEntry.getValue());
      }
      return i != MapCollections.this.colGetSize();
    }
    
    public final void clear()
    {
      MapCollections.this.colClear();
    }
    
    public final boolean contains(Object paramObject)
    {
      if (!(paramObject instanceof Map.Entry)) {}
      Map.Entry localEntry;
      int i;
      do
      {
        return false;
        localEntry = (Map.Entry)paramObject;
        i = MapCollections.this.colIndexOfKey(localEntry.getKey());
      } while (i < 0);
      return ContainerHelpers.equal(MapCollections.this.colGetEntry(i, 1), localEntry.getValue());
    }
    
    public final boolean containsAll(Collection<?> paramCollection)
    {
      Iterator localIterator = paramCollection.iterator();
      while (localIterator.hasNext()) {
        if (!contains(localIterator.next())) {
          return false;
        }
      }
      return true;
    }
    
    public final boolean equals(Object paramObject)
    {
      return MapCollections.equalsSetHelper(this, paramObject);
    }
    
    public final int hashCode()
    {
      int i = 0;
      int j = -1 + MapCollections.this.colGetSize();
      if (j >= 0)
      {
        Object localObject1 = MapCollections.this.colGetEntry(j, 0);
        Object localObject2 = MapCollections.this.colGetEntry(j, 1);
        int k;
        if (localObject1 == null)
        {
          k = 0;
          label44:
          if (localObject2 != null) {
            break label75;
          }
        }
        label75:
        for (int m = 0;; m = localObject2.hashCode())
        {
          i += (m ^ k);
          j--;
          break;
          k = localObject1.hashCode();
          break label44;
        }
      }
      return i;
    }
    
    public final boolean isEmpty()
    {
      return MapCollections.this.colGetSize() == 0;
    }
    
    public final Iterator<Map.Entry<K, V>> iterator()
    {
      return new MapCollections.MapIterator(MapCollections.this);
    }
    
    public final boolean remove(Object paramObject)
    {
      throw new UnsupportedOperationException();
    }
    
    public final boolean removeAll(Collection<?> paramCollection)
    {
      throw new UnsupportedOperationException();
    }
    
    public final boolean retainAll(Collection<?> paramCollection)
    {
      throw new UnsupportedOperationException();
    }
    
    public final int size()
    {
      return MapCollections.this.colGetSize();
    }
    
    public final Object[] toArray()
    {
      throw new UnsupportedOperationException();
    }
    
    public final <T> T[] toArray(T[] paramArrayOfT)
    {
      throw new UnsupportedOperationException();
    }
  }
  
  final class KeySet
    implements Set<K>
  {
    KeySet() {}
    
    public final boolean add(K paramK)
    {
      throw new UnsupportedOperationException();
    }
    
    public final boolean addAll(Collection<? extends K> paramCollection)
    {
      throw new UnsupportedOperationException();
    }
    
    public final void clear()
    {
      MapCollections.this.colClear();
    }
    
    public final boolean contains(Object paramObject)
    {
      return MapCollections.this.colIndexOfKey(paramObject) >= 0;
    }
    
    public final boolean containsAll(Collection<?> paramCollection)
    {
      Map localMap = MapCollections.this.colGetMap();
      Iterator localIterator = paramCollection.iterator();
      while (localIterator.hasNext()) {
        if (!localMap.containsKey(localIterator.next())) {
          return false;
        }
      }
      return true;
    }
    
    public final boolean equals(Object paramObject)
    {
      return MapCollections.equalsSetHelper(this, paramObject);
    }
    
    public final int hashCode()
    {
      int i = 0;
      int j = -1 + MapCollections.this.colGetSize();
      if (j >= 0)
      {
        Object localObject = MapCollections.this.colGetEntry(j, 0);
        if (localObject == null) {}
        for (int k = 0;; k = localObject.hashCode())
        {
          i += k;
          j--;
          break;
        }
      }
      return i;
    }
    
    public final boolean isEmpty()
    {
      return MapCollections.this.colGetSize() == 0;
    }
    
    public final Iterator<K> iterator()
    {
      return new MapCollections.ArrayIterator(MapCollections.this, 0);
    }
    
    public final boolean remove(Object paramObject)
    {
      int i = MapCollections.this.colIndexOfKey(paramObject);
      if (i >= 0)
      {
        MapCollections.this.colRemoveAt(i);
        return true;
      }
      return false;
    }
    
    public final boolean removeAll(Collection<?> paramCollection)
    {
      Map localMap = MapCollections.this.colGetMap();
      int i = localMap.size();
      Iterator localIterator = paramCollection.iterator();
      while (localIterator.hasNext()) {
        localMap.remove(localIterator.next());
      }
      return i != localMap.size();
    }
    
    public final boolean retainAll(Collection<?> paramCollection)
    {
      return MapCollections.retainAllHelper(MapCollections.this.colGetMap(), paramCollection);
    }
    
    public final int size()
    {
      return MapCollections.this.colGetSize();
    }
    
    public final Object[] toArray()
    {
      return MapCollections.this.toArrayHelper(0);
    }
    
    public final <T> T[] toArray(T[] paramArrayOfT)
    {
      return MapCollections.this.toArrayHelper(paramArrayOfT, 0);
    }
  }
  
  final class MapIterator
    implements Iterator<Map.Entry<K, V>>, Map.Entry<K, V>
  {
    int mEnd = -1 + MapCollections.this.colGetSize();
    boolean mEntryValid = false;
    int mIndex = -1;
    
    MapIterator() {}
    
    public final boolean equals(Object paramObject)
    {
      if (!this.mEntryValid) {
        throw new IllegalStateException("This container does not support retaining Map.Entry objects");
      }
      if (!(paramObject instanceof Map.Entry)) {}
      Map.Entry localEntry;
      do
      {
        return false;
        localEntry = (Map.Entry)paramObject;
      } while ((!ContainerHelpers.equal(localEntry.getKey(), MapCollections.this.colGetEntry(this.mIndex, 0))) || (!ContainerHelpers.equal(localEntry.getValue(), MapCollections.this.colGetEntry(this.mIndex, 1))));
      return true;
    }
    
    public final K getKey()
    {
      if (!this.mEntryValid) {
        throw new IllegalStateException("This container does not support retaining Map.Entry objects");
      }
      return MapCollections.this.colGetEntry(this.mIndex, 0);
    }
    
    public final V getValue()
    {
      if (!this.mEntryValid) {
        throw new IllegalStateException("This container does not support retaining Map.Entry objects");
      }
      return MapCollections.this.colGetEntry(this.mIndex, 1);
    }
    
    public final boolean hasNext()
    {
      return this.mIndex < this.mEnd;
    }
    
    public final int hashCode()
    {
      if (!this.mEntryValid) {
        throw new IllegalStateException("This container does not support retaining Map.Entry objects");
      }
      Object localObject1 = MapCollections.this.colGetEntry(this.mIndex, 0);
      Object localObject2 = MapCollections.this.colGetEntry(this.mIndex, 1);
      int i;
      int j;
      if (localObject1 == null)
      {
        i = 0;
        j = 0;
        if (localObject2 != null) {
          break label69;
        }
      }
      for (;;)
      {
        return j ^ i;
        i = localObject1.hashCode();
        break;
        label69:
        j = localObject2.hashCode();
      }
    }
    
    public final void remove()
    {
      if (!this.mEntryValid) {
        throw new IllegalStateException();
      }
      MapCollections.this.colRemoveAt(this.mIndex);
      this.mIndex = (-1 + this.mIndex);
      this.mEnd = (-1 + this.mEnd);
      this.mEntryValid = false;
    }
    
    public final V setValue(V paramV)
    {
      if (!this.mEntryValid) {
        throw new IllegalStateException("This container does not support retaining Map.Entry objects");
      }
      return MapCollections.this.colSetValue(this.mIndex, paramV);
    }
    
    public final String toString()
    {
      return getKey() + "=" + getValue();
    }
  }
  
  final class ValuesCollection
    implements Collection<V>
  {
    ValuesCollection() {}
    
    public final boolean add(V paramV)
    {
      throw new UnsupportedOperationException();
    }
    
    public final boolean addAll(Collection<? extends V> paramCollection)
    {
      throw new UnsupportedOperationException();
    }
    
    public final void clear()
    {
      MapCollections.this.colClear();
    }
    
    public final boolean contains(Object paramObject)
    {
      return MapCollections.this.colIndexOfValue(paramObject) >= 0;
    }
    
    public final boolean containsAll(Collection<?> paramCollection)
    {
      Iterator localIterator = paramCollection.iterator();
      while (localIterator.hasNext()) {
        if (!contains(localIterator.next())) {
          return false;
        }
      }
      return true;
    }
    
    public final boolean isEmpty()
    {
      return MapCollections.this.colGetSize() == 0;
    }
    
    public final Iterator<V> iterator()
    {
      return new MapCollections.ArrayIterator(MapCollections.this, 1);
    }
    
    public final boolean remove(Object paramObject)
    {
      int i = MapCollections.this.colIndexOfValue(paramObject);
      if (i >= 0)
      {
        MapCollections.this.colRemoveAt(i);
        return true;
      }
      return false;
    }
    
    public final boolean removeAll(Collection<?> paramCollection)
    {
      int i = MapCollections.this.colGetSize();
      boolean bool = false;
      for (int j = 0; j < i; j++) {
        if (paramCollection.contains(MapCollections.this.colGetEntry(j, 1)))
        {
          MapCollections.this.colRemoveAt(j);
          j--;
          i--;
          bool = true;
        }
      }
      return bool;
    }
    
    public final boolean retainAll(Collection<?> paramCollection)
    {
      int i = MapCollections.this.colGetSize();
      boolean bool = false;
      for (int j = 0; j < i; j++) {
        if (!paramCollection.contains(MapCollections.this.colGetEntry(j, 1)))
        {
          MapCollections.this.colRemoveAt(j);
          j--;
          i--;
          bool = true;
        }
      }
      return bool;
    }
    
    public final int size()
    {
      return MapCollections.this.colGetSize();
    }
    
    public final Object[] toArray()
    {
      return MapCollections.this.toArrayHelper(1);
    }
    
    public final <T> T[] toArray(T[] paramArrayOfT)
    {
      return MapCollections.this.toArrayHelper(paramArrayOfT, 1);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.util.MapCollections
 * JD-Core Version:    0.7.0.1
 */