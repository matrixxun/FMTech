package com.google.android.finsky.widget.consumption;

import java.util.HashMap;
import java.util.Map;

public final class NowPlayingCellSorter
{
  Map<String, Integer> mSequenceMapping = new HashMap();
  
  static String getCellDescriptor(int paramInt1, int paramInt2, int paramInt3)
  {
    return paramInt1 + ":" + paramInt2 + ":" + paramInt3;
  }
  
  private final class CellInformation
    implements Comparable<CellInformation>
  {
    int blockIndexInRow;
    long cellAreaInPixels;
    int cellIndexInBlock;
    int rowIndexInWidget;
    
    public CellInformation(int paramInt1, int paramInt2, int paramInt3)
    {
      this.rowIndexInWidget = paramInt1;
      this.blockIndexInRow = paramInt2;
      this.cellIndexInBlock = paramInt3;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.widget.consumption.NowPlayingCellSorter
 * JD-Core Version:    0.7.0.1
 */