package android.support.v4.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ArrayMap<K, V>
  extends SimpleArrayMap<K, V>
  implements Map<K, V>
{
  MapCollections<K, V> mCollections;
  
  public ArrayMap() {}
  
  public ArrayMap(int paramInt)
  {
    super(paramInt);
  }
  
  private MapCollections<K, V> getCollection()
  {
    if (this.mCollections == null) {
      this.mCollections = new MapCollections()
      {
        protected final void colClear()
        {
          ArrayMap.this.clear();
        }
        
        protected final Object colGetEntry(int paramAnonymousInt1, int paramAnonymousInt2)
        {
          return ArrayMap.this.mArray[(paramAnonymousInt2 + (paramAnonymousInt1 << 1))];
        }
        
        protected final Map<K, V> colGetMap()
        {
          return ArrayMap.this;
        }
        
        protected final int colGetSize()
        {
          return ArrayMap.this.mSize;
        }
        
        protected final int colIndexOfKey(Object paramAnonymousObject)
        {
          return ArrayMap.this.indexOfKey(paramAnonymousObject);
        }
        
        protected final int colIndexOfValue(Object paramAnonymousObject)
        {
          return ArrayMap.this.indexOfValue(paramAnonymousObject);
        }
        
        protected final void colPut(K paramAnonymousK, V paramAnonymousV)
        {
          ArrayMap.this.put(paramAnonymousK, paramAnonymousV);
        }
        
        protected final void colRemoveAt(int paramAnonymousInt)
        {
          ArrayMap.this.removeAt(paramAnonymousInt);
        }
        
        protected final V colSetValue(int paramAnonymousInt, V paramAnonymousV)
        {
          return ArrayMap.this.setValueAt(paramAnonymousInt, paramAnonymousV);
        }
      };
    }
    return this.mCollections;
  }
  
  public Set<Map.Entry<K, V>> entrySet()
  {
    MapCollections localMapCollections = getCollection();
    if (localMapCollections.mEntrySet == null) {
      localMapCollections.mEntrySet = new MapCollections.EntrySet(localMapCollections);
    }
    return localMapCollections.mEntrySet;
  }
  
  public Set<K> keySet()
  {
    MapCollections localMapCollections = getCollection();
    if (localMapCollections.mKeySet == null) {
      localMapCollections.mKeySet = new MapCollections.KeySet(localMapCollections);
    }
    return localMapCollections.mKeySet;
  }
  
  public void putAll(Map<? extends K, ? extends V> paramMap)
  {
    ensureCapacity(this.mSize + paramMap.size());
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      put(localEntry.getKey(), localEntry.getValue());
    }
  }
  
  public Collection<V> values()
  {
    MapCollections localMapCollections = getCollection();
    if (localMapCollections.mValues == null) {
      localMapCollections.mValues = new MapCollections.ValuesCollection(localMapCollections);
    }
    return localMapCollections.mValues;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.util.ArrayMap
 * JD-Core Version:    0.7.0.1
 */