package com.google.android.finsky.widget.consumption;

import android.content.Context;
import android.util.SparseArray;
import com.google.android.finsky.widget.WidgetUtils;

public final class Block
{
  private int mChildHeightRes;
  private SparseArray<int[]> mChildResArray;
  private int mChildWidthRes;
  private int mHeightRes;
  Block mLastInRowReplacement;
  final int mLayoutId;
  int mMaxRowStartCount;
  int mNumImages;
  boolean mSupportsMetadata;
  private int mWidthRes;
  
  public Block(int paramInt)
  {
    this.mLayoutId = paramInt;
    this.mNumImages = 1;
    this.mMaxRowStartCount = -1;
  }
  
  public final int getHeight(Context paramContext)
  {
    return WidgetUtils.getDips(paramContext, this.mHeightRes);
  }
  
  public final int getImageHeightRes(int paramInt)
  {
    if (this.mChildResArray != null) {
      return ((int[])this.mChildResArray.get(paramInt))[1];
    }
    return this.mChildHeightRes;
  }
  
  public final int getImageWidthRes(int paramInt)
  {
    if (this.mChildResArray != null) {
      return ((int[])this.mChildResArray.get(paramInt))[0];
    }
    return this.mChildWidthRes;
  }
  
  public final int getWidth(Context paramContext)
  {
    return WidgetUtils.getDips(paramContext, this.mWidthRes);
  }
  
  public final int hashCode()
  {
    return 31 * this.mLayoutId + this.mMaxRowStartCount;
  }
  
  public final Block hosting(int paramInt1, int paramInt2, int paramInt3)
  {
    this.mNumImages = paramInt1;
    this.mChildWidthRes = paramInt2;
    this.mChildHeightRes = paramInt3;
    return this;
  }
  
  public final Block sized(int paramInt1, int paramInt2)
  {
    this.mWidthRes = paramInt1;
    this.mHeightRes = paramInt2;
    this.mChildWidthRes = this.mWidthRes;
    this.mChildHeightRes = this.mHeightRes;
    return this;
  }
  
  public final Block withChild(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.mChildResArray == null) {
      this.mChildResArray = new SparseArray(this.mNumImages);
    }
    this.mChildResArray.put(paramInt1, new int[] { paramInt2, paramInt3 });
    return this;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.consumption.Block
 * JD-Core Version:    0.7.0.1
 */