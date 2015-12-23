package com.google.android.libraries.bind.data;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.AbsListView.LayoutParams;
import com.google.android.libraries.bind.R.layout;
import com.google.android.libraries.bind.card.ViewGenerator;
import com.google.android.libraries.bind.util.Util;
import com.google.android.libraries.bind.view.ViewHeap;
import java.util.Iterator;
import java.util.List;

public final class BindingDataAdapter
  extends DataAdapter
{
  private static SparseIntArray viewResIdMap;
  private static final int[] viewResIds;
  private final int a11yCardCountKey;
  private final int equalityFieldsKey;
  private DataList originalDataList;
  private final int viewGeneratorKey;
  private final int viewResourceIdKey;
  private final List<Integer> viewTypes;
  
  static
  {
    int[] arrayOfInt = new int[2];
    arrayOfInt[0] = R.layout.bind__card_edit_placeholder;
    arrayOfInt[1] = R.layout.bind__card_list_padding;
    viewResIds = arrayOfInt;
  }
  
  public final int findRowWithCardId(Object paramObject)
  {
    for (int i = 0; i < getCount(); i++)
    {
      Data localData = getItem(i);
      if (localData != null) {
        if (localData.containsKey(this.viewResourceIdKey))
        {
          if (paramObject.equals(this.dataList.getItemId(i))) {
            return i;
          }
        }
        else
        {
          Iterator localIterator = ((ViewGenerator)localData.get(this.viewGeneratorKey)).getCardIds().iterator();
          while (localIterator.hasNext()) {
            if (paramObject.equals(localIterator.next())) {
              return i;
            }
          }
        }
      }
    }
    return -1;
  }
  
  public final int getA11yRowCount(int paramInt)
  {
    Data localData = getItem(paramInt);
    if (localData == null) {}
    Integer localInteger;
    do
    {
      return 1;
      localInteger = (Integer)localData.get(this.a11yCardCountKey);
    } while (localInteger == null);
    return localInteger.intValue();
  }
  
  public final int getItemViewType(int paramInt)
  {
    if (paramInt < this.viewTypes.size()) {
      return ((Integer)this.viewTypes.get(paramInt)).intValue();
    }
    return -1;
  }
  
  public final Object getRowFirstCardId(int paramInt)
  {
    Data localData = getItem(paramInt);
    if (localData == null) {
      return null;
    }
    if (localData.containsKey(this.viewResourceIdKey)) {
      return this.dataList.getItemId(paramInt);
    }
    List localList = ((ViewGenerator)localData.get(this.viewGeneratorKey)).getCardIds();
    if (localList.size() > 0) {
      return localList.get(0);
    }
    return null;
  }
  
  public final View getView$7f88da85(int paramInt, View paramView, Data paramData)
  {
    Integer localInteger = (Integer)paramData.get(this.viewResourceIdKey);
    if (localInteger == null)
    {
      ViewGenerator localViewGenerator = (ViewGenerator)paramData.get(this.viewGeneratorKey);
      if (localViewGenerator != null) {}
      for (boolean bool = true;; bool = false)
      {
        Util.checkPrecondition(bool, "Missing both view resource ID and view generator");
        return localViewGenerator.makeView$4b5572a0();
      }
    }
    View localView = this.viewHeap.get(localInteger.intValue(), paramView, new AbsListView.LayoutParams(-1, -2));
    int[] arrayOfInt = (int[])paramData.get(this.equalityFieldsKey);
    if ((localView instanceof DataView))
    {
      DataView localDataView = (DataView)localView;
      FilteredDataRow localFilteredDataRow = new FilteredDataRow(this.originalDataList, this.originalDataList.getItemId(paramInt), new LayoutResIdFilter(localInteger.intValue(), this.viewResourceIdKey), arrayOfInt);
      localFilteredDataRow.startAutoRefresh();
      localDataView.setDataRow(localFilteredDataRow);
    }
    return localView;
  }
  
  public final int getViewTypeCount()
  {
    return viewResIdMap.size();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.data.BindingDataAdapter
 * JD-Core Version:    0.7.0.1
 */