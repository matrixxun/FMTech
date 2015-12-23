package com.google.android.play.search;

import java.lang.reflect.Array;

public final class LevenshteinDistance
{
  final int[][] mDistanceTable;
  final int[][] mEditTypeTable;
  final Token[] mSource;
  final Token[] mTarget;
  
  public LevenshteinDistance(Token[] paramArrayOfToken1, Token[] paramArrayOfToken2)
  {
    int i = paramArrayOfToken1.length;
    int j = paramArrayOfToken2.length;
    int[] arrayOfInt1 = { i + 1, j + 1 };
    int[][] arrayOfInt2 = (int[][])Array.newInstance(Integer.TYPE, arrayOfInt1);
    int[] arrayOfInt3 = { i + 1, j + 1 };
    int[][] arrayOfInt4 = (int[][])Array.newInstance(Integer.TYPE, arrayOfInt3);
    arrayOfInt2[0][0] = 3;
    arrayOfInt4[0][0] = 0;
    for (int k = 1; k <= i; k++)
    {
      arrayOfInt2[k][0] = 0;
      arrayOfInt4[k][0] = k;
    }
    for (int m = 1; m <= j; m++)
    {
      arrayOfInt2[0][m] = 1;
      arrayOfInt4[0][m] = m;
    }
    this.mEditTypeTable = arrayOfInt2;
    this.mDistanceTable = arrayOfInt4;
    this.mSource = paramArrayOfToken1;
    this.mTarget = paramArrayOfToken2;
  }
  
  public static final class EditOperation
  {
    final int mPosition;
    final int mType;
    
    public EditOperation(int paramInt1, int paramInt2)
    {
      this.mType = paramInt1;
      this.mPosition = paramInt2;
    }
  }
  
  public static final class Token
    implements CharSequence
  {
    final char[] mContainer;
    public final int mEnd;
    public final int mStart;
    
    public Token(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      this.mContainer = paramArrayOfChar;
      this.mStart = paramInt1;
      this.mEnd = paramInt2;
    }
    
    private String subSequence$13d12155(int paramInt)
    {
      return new String(this.mContainer, paramInt + this.mStart, length());
    }
    
    public final char charAt(int paramInt)
    {
      return this.mContainer[(paramInt + this.mStart)];
    }
    
    public final int length()
    {
      return this.mEnd - this.mStart;
    }
    
    public final String toString()
    {
      length();
      return subSequence$13d12155(0);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.search.LevenshteinDistance
 * JD-Core Version:    0.7.0.1
 */