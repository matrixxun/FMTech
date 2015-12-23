package com.google.android.libraries.bind.data;

public final class DataChange
{
  public static final DataChange AFFECTS_PRIMARY_KEY = new DataChange(false, true);
  public static final DataChange DOESNT_AFFECT_PRIMARY_KEY = new DataChange(false, false);
  public static final DataChange INVALIDATION = new DataChange(true, true);
  public final boolean affectsPrimaryKey;
  public final boolean hasRefreshException;
  public final boolean isInvalidation;
  public final DataException refreshException;
  
  public DataChange(DataException paramDataException)
  {
    this.isInvalidation = true;
    this.affectsPrimaryKey = true;
    this.hasRefreshException = true;
    this.refreshException = paramDataException;
  }
  
  private DataChange(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.isInvalidation = paramBoolean1;
    this.affectsPrimaryKey = paramBoolean2;
    this.hasRefreshException = false;
    this.refreshException = null;
  }
  
  public final String toString()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = Boolean.valueOf(this.isInvalidation);
    arrayOfObject[1] = Boolean.valueOf(this.affectsPrimaryKey);
    arrayOfObject[2] = this.refreshException;
    return String.format("isInvalidation: %b, affectsPrimaryKey: %b, exception: %s", arrayOfObject);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.data.DataChange
 * JD-Core Version:    0.7.0.1
 */