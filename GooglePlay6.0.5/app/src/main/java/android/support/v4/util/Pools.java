package android.support.v4.util;

public final class Pools
{
  public static abstract interface Pool<T>
  {
    public abstract T acquire();
    
    public abstract boolean release(T paramT);
  }
  
  public static final class SimplePool<T>
    implements Pools.Pool<T>
  {
    private final Object[] mPool;
    private int mPoolSize;
    
    public SimplePool(int paramInt)
    {
      if (paramInt <= 0) {
        throw new IllegalArgumentException("The max pool size must be > 0");
      }
      this.mPool = new Object[paramInt];
    }
    
    public final T acquire()
    {
      if (this.mPoolSize > 0)
      {
        int i = -1 + this.mPoolSize;
        Object localObject = this.mPool[i];
        this.mPool[i] = null;
        this.mPoolSize = (-1 + this.mPoolSize);
        return localObject;
      }
      return null;
    }
    
    public final boolean release(T paramT)
    {
      int i = 0;
      if (i < this.mPoolSize) {
        if (this.mPool[i] != paramT) {}
      }
      for (int j = 1;; j = 0)
      {
        if (j == 0) {
          break label47;
        }
        throw new IllegalStateException("Already in the pool!");
        i++;
        break;
      }
      label47:
      int k = this.mPoolSize;
      int m = this.mPool.length;
      boolean bool = false;
      if (k < m)
      {
        this.mPool[this.mPoolSize] = paramT;
        this.mPoolSize = (1 + this.mPoolSize);
        bool = true;
      }
      return bool;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.util.Pools
 * JD-Core Version:    0.7.0.1
 */