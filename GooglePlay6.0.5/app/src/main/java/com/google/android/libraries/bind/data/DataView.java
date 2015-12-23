package com.google.android.libraries.bind.data;

public abstract interface DataView
{
  public abstract DataList getDataRow();
  
  public abstract void onDataUpdated(Data paramData);
  
  public abstract void setDataRow(DataList paramDataList);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.data.DataView
 * JD-Core Version:    0.7.0.1
 */