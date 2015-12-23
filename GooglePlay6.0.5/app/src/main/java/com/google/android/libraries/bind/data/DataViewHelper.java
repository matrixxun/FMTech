package com.google.android.libraries.bind.data;

import com.google.android.libraries.bind.util.Util;
import java.util.Locale;

public class DataViewHelper
  extends DataObserver
{
  public boolean attached;
  private boolean clearDataOnDetach = true;
  public DataList dataRow;
  private boolean registered;
  public boolean temporarilyDetached;
  private final DataView view;
  
  public DataViewHelper(DataView paramDataView)
  {
    this.view = paramDataView;
  }
  
  private void unregister()
  {
    this.dataRow.unregisterDataObserver(this);
    this.registered = false;
  }
  
  public Data getData()
  {
    if ((this.dataRow != null) && (!this.dataRow.isEmpty())) {}
    for (int i = 1; i != 0; i = 0) {
      return this.dataRow.getData(0);
    }
    return null;
  }
  
  public final void onDataChanged(DataChange paramDataChange)
  {
    int i = 1;
    if ((this.dataRow == null) || (this.dataRow.getCount() <= i)) {}
    for (;;)
    {
      Util.checkPrecondition(i, "Passed DataList with more than one row.");
      this.view.onDataUpdated(getData());
      return;
      int j = 0;
    }
  }
  
  public final void onFinishTemporaryDetach()
  {
    this.temporarilyDetached = false;
    updateRegistrationIfNeeded();
  }
  
  public void setDataRow(DataList paramDataList)
  {
    if (paramDataList == this.dataRow) {
      return;
    }
    if (this.registered) {
      unregister();
    }
    this.dataRow = paramDataList;
    updateRegistrationIfNeeded();
    onDataChanged(DataChange.INVALIDATION);
  }
  
  public String toString()
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[6];
    arrayOfObject[0] = this.view.getClass().getSimpleName();
    DataList localDataList = this.dataRow;
    boolean bool = false;
    if (localDataList != null) {
      bool = true;
    }
    arrayOfObject[1] = Boolean.valueOf(bool);
    arrayOfObject[2] = Boolean.valueOf(this.registered);
    arrayOfObject[3] = Boolean.valueOf(this.attached);
    arrayOfObject[4] = Boolean.valueOf(this.temporarilyDetached);
    arrayOfObject[5] = Boolean.valueOf(this.clearDataOnDetach);
    return String.format(localLocale, "View type: %s, hasData: %b, registered: %b, attached: %b,temporarilyDetached: %b, clearDataOnDetach: %b", arrayOfObject);
  }
  
  public final void updateRegistrationIfNeeded()
  {
    int i;
    if (((this.attached) && (!this.temporarilyDetached)) || (!this.clearDataOnDetach))
    {
      i = 1;
      if (i == 0) {
        break label60;
      }
      if ((!this.registered) && (this.dataRow != null))
      {
        this.dataRow.registerDataObserver(this);
        this.registered = true;
      }
    }
    label60:
    while (!this.registered)
    {
      return;
      i = 0;
      break;
    }
    unregister();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.data.DataViewHelper
 * JD-Core Version:    0.7.0.1
 */