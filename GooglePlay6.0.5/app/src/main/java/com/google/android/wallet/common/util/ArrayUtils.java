package com.google.android.wallet.common.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public final class ArrayUtils
{
  public static <T> T[] appendToArray(T[] paramArrayOfT, T paramT)
  {
    if ((paramArrayOfT == null) && (paramT == null)) {
      throw new IllegalArgumentException("Cannot generate array of generic type w/o class info");
    }
    if (paramArrayOfT == null) {}
    for (Object[] arrayOfObject = (Object[])Array.newInstance(paramT.getClass(), 1);; arrayOfObject = Arrays.copyOf(paramArrayOfT, 1 + paramArrayOfT.length))
    {
      arrayOfObject[(-1 + arrayOfObject.length)] = paramT;
      return arrayOfObject;
    }
  }
  
  public static boolean contains(int[] paramArrayOfInt, int paramInt)
  {
    if (paramArrayOfInt == null) {}
    for (;;)
    {
      return false;
      int i = paramArrayOfInt.length;
      for (int j = 0; j < i; j++) {
        if (paramArrayOfInt[j] == paramInt) {
          return true;
        }
      }
    }
  }
  
  public static char[] toCharArray(List<Character> paramList)
  {
    int i = paramList.size();
    char[] arrayOfChar = new char[i];
    for (int j = 0; j < i; j++) {
      arrayOfChar[j] = ((Character)paramList.get(j)).charValue();
    }
    return arrayOfChar;
  }
  
  public static Integer[] toWrapperArray(int[] paramArrayOfInt)
  {
    Integer[] arrayOfInteger;
    if (paramArrayOfInt == null) {
      arrayOfInteger = null;
    }
    for (;;)
    {
      return arrayOfInteger;
      int i = paramArrayOfInt.length;
      arrayOfInteger = new Integer[i];
      for (int j = 0; j < i; j++) {
        arrayOfInteger[j] = Integer.valueOf(paramArrayOfInt[j]);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.common.util.ArrayUtils
 * JD-Core Version:    0.7.0.1
 */