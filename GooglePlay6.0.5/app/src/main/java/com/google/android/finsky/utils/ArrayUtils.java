package com.google.android.finsky.utils;

import java.lang.reflect.Array;
import java.util.AbstractList;

public final class ArrayUtils
{
  public static <T> T[] remove(T[] paramArrayOfT, int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= paramArrayOfT.length)) {
      throw new ArrayIndexOutOfBoundsException(paramInt);
    }
    int i = -1 + paramArrayOfT.length;
    Object[] arrayOfObject = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), i);
    if (paramInt != 0) {
      System.arraycopy(paramArrayOfT, 0, arrayOfObject, 0, paramInt);
    }
    int j = i - paramInt;
    if (j > 0) {
      System.arraycopy(paramArrayOfT, paramInt + 1, arrayOfObject, paramInt, j);
    }
    return arrayOfObject;
  }
  
  private static final class ArrayAsList<T>
    extends AbstractList<T>
  {
    private final T[] mArray;
    
    public ArrayAsList(T[] paramArrayOfT)
    {
      this.mArray = paramArrayOfT;
    }
    
    public final T get(int paramInt)
    {
      return this.mArray[paramInt];
    }
    
    public final int size()
    {
      return this.mArray.length;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.ArrayUtils
 * JD-Core Version:    0.7.0.1
 */