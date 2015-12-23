package com.google.android.wallet.ui.address;

import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filter.FilterResults;
import android.widget.Filterable;
import android.widget.TextView;
import com.google.android.wallet.common.address.AddressField;
import com.google.android.wallet.common.address.AddressFormatter;
import com.google.android.wallet.common.address.AddressSource;
import com.google.android.wallet.common.address.AddressSourceResult;
import com.google.android.wallet.uicomponents.R.id;
import com.google.location.country.Postaladdress.PostalAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public final class AddressSourceResultAdapter
  extends BaseAdapter
  implements Filterable
{
  private final char mAddressField;
  private final List<AddressSource> mAddressSources;
  private AddressSourceResultFilter mFilter;
  private final LayoutInflater mInflater;
  private final String mLanguageCode;
  CharSequence mLastPublishedConstraint;
  ArrayList<AddressSourceResult> mObjects;
  private final int mRegionCode;
  final char[] mRemainingFields;
  private final char[] mRequiredFields;
  private final int mTextViewResourceId;
  
  public AddressSourceResultAdapter(ContextThemeWrapper paramContextThemeWrapper, int paramInt1, int paramInt2, String paramString1, char paramChar, char[] paramArrayOfChar, String paramString2, List<AddressSource> paramList)
  {
    if ((paramArrayOfChar == null) || (paramArrayOfChar.length == 0)) {
      throw new IllegalArgumentException("remainingFields are required");
    }
    if ((paramList == null) || (paramList.isEmpty())) {
      throw new IllegalArgumentException("addressSources are required");
    }
    this.mTextViewResourceId = paramInt1;
    this.mRegionCode = paramInt2;
    this.mLanguageCode = paramString1;
    this.mAddressField = paramChar;
    this.mRemainingFields = checkValidAddressFieldsAndCopy(paramArrayOfChar);
    if (paramString2 != null) {}
    for (char[] arrayOfChar = paramString2.toCharArray();; arrayOfChar = null)
    {
      this.mRequiredFields = arrayOfChar;
      this.mAddressSources = new ArrayList(paramList);
      this.mInflater = LayoutInflater.from(paramContextThemeWrapper);
      this.mObjects = new ArrayList();
      return;
    }
  }
  
  private static char[] checkValidAddressFieldsAndCopy(char[] paramArrayOfChar)
  {
    for (int i = 0; (i < paramArrayOfChar.length) && (!AddressField.exists(paramArrayOfChar[i])); i++) {}
    if (i < paramArrayOfChar.length) {
      return Arrays.copyOfRange(paramArrayOfChar, i, paramArrayOfChar.length);
    }
    throw new IllegalArgumentException("fields must contain at least one valid field");
  }
  
  private View getCustomView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null) {
      paramView = this.mInflater.inflate(this.mTextViewResourceId, paramViewGroup, false);
    }
    AddressSourceResult localAddressSourceResult = getItem(paramInt);
    ((TextView)paramView.findViewById(R.id.description)).setText(localAddressSourceResult.description);
    return paramView;
  }
  
  private AddressSourceResult getItem(int paramInt)
  {
    return (AddressSourceResult)this.mObjects.get(paramInt);
  }
  
  public final int getCount()
  {
    return this.mObjects.size();
  }
  
  public final View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    return getCustomView(paramInt, paramView, paramViewGroup);
  }
  
  public final Filter getFilter()
  {
    if (this.mFilter == null) {
      this.mFilter = new AddressSourceResultFilter();
    }
    return this.mFilter;
  }
  
  public final long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    return getCustomView(paramInt, paramView, paramViewGroup);
  }
  
  final class AddressSourceResultFilter
    extends Filter
  {
    AddressSourceResultFilter() {}
    
    private boolean containsAllRemainingRequiredFields(Postaladdress.PostalAddress paramPostalAddress)
    {
      if (AddressSourceResultAdapter.this.mRequiredFields == null) {}
      for (;;)
      {
        return true;
        label122:
        for (char c : AddressSourceResultAdapter.this.mRequiredFields)
        {
          if (c == 'A') {
            c = '1';
          }
          char[] arrayOfChar2 = AddressSourceResultAdapter.this.mRemainingFields;
          int n;
          if (arrayOfChar2 != null)
          {
            int m = arrayOfChar2.length;
            n = 0;
            if (n < m) {
              if (arrayOfChar2[n] != c) {}
            }
          }
          for (int k = 1;; k = 0)
          {
            if ((k == 0) || (!TextUtils.isEmpty(AddressFormatter.formatAddressValue(paramPostalAddress, c)))) {
              break label122;
            }
            return false;
            n++;
            break;
          }
        }
      }
    }
    
    private boolean isBadAddress(Postaladdress.PostalAddress paramPostalAddress)
    {
      boolean bool;
      if ((AddressSourceResultAdapter.this.mRequiredFields == null) || (paramPostalAddress.addressLine.length == 0)) {
        bool = false;
      }
      for (;;)
      {
        return bool;
        bool = false;
        for (char c : AddressSourceResultAdapter.this.mRequiredFields) {
          switch (c)
          {
          default: 
            if (AddressField.exists(c))
            {
              bool = true;
              if (!TextUtils.isEmpty(AddressFormatter.formatAddressValue(paramPostalAddress, c))) {
                return false;
              }
            }
            break;
          }
        }
      }
    }
    
    private ArrayList<AddressSourceResult> performFilteringForValues(CharSequence paramCharSequence)
    {
      if (paramCharSequence != null)
      {
        Iterator localIterator = AddressSourceResultAdapter.this.mAddressSources.iterator();
        while (localIterator.hasNext())
        {
          AddressSource localAddressSource = (AddressSource)localIterator.next();
          try
          {
            List localList = localAddressSource.getAddresses(paramCharSequence, AddressSourceResultAdapter.this.mAddressField, AddressSourceResultAdapter.this.mRemainingFields, AddressSourceResultAdapter.this.mRegionCode, AddressSourceResultAdapter.this.mLanguageCode);
            ArrayList localArrayList = processAddressSourceResults(localList, localAddressSource.getName());
            if ((localArrayList != null) && (!localArrayList.isEmpty())) {
              return localArrayList;
            }
          }
          catch (Throwable localThrowable)
          {
            Log.e("AddressSourceResultAdap", "Could not fetch addresses from " + localAddressSource.getName(), localThrowable);
          }
        }
      }
      return new ArrayList();
    }
    
    private ArrayList<AddressSourceResult> processAddressSourceResults(List<AddressSourceResult> paramList, String paramString)
    {
      Object localObject;
      if (paramList == null) {
        localObject = null;
      }
      for (;;)
      {
        return localObject;
        localObject = new ArrayList();
        LinkedHashMap localLinkedHashMap = new LinkedHashMap();
        Iterator localIterator1 = paramList.iterator();
        while (localIterator1.hasNext())
        {
          AddressSourceResult localAddressSourceResult2 = (AddressSourceResult)localIterator1.next();
          if (localAddressSourceResult2.address != null)
          {
            if (isBadAddress(localAddressSourceResult2.address)) {
              continue;
            }
            if (!containsAllRemainingRequiredFields(localAddressSourceResult2.address))
            {
              String str = localAddressSourceResult2.matchingTerm;
              if ((TextUtils.isEmpty(str)) || (localLinkedHashMap.containsKey(str))) {
                continue;
              }
              localLinkedHashMap.put(str, Integer.valueOf(((ArrayList)localObject).size()));
              continue;
            }
          }
          if (!TextUtils.isEmpty(localAddressSourceResult2.description))
          {
            ((ArrayList)localObject).add(localAddressSourceResult2);
            if (!TextUtils.isEmpty(localAddressSourceResult2.matchingTerm)) {
              localLinkedHashMap.put(localAddressSourceResult2.matchingTerm, Integer.valueOf(-1));
            }
          }
        }
        if (!localLinkedHashMap.isEmpty())
        {
          int i = 0;
          Iterator localIterator2 = localLinkedHashMap.entrySet().iterator();
          while (localIterator2.hasNext())
          {
            Map.Entry localEntry = (Map.Entry)localIterator2.next();
            int j = ((Integer)localEntry.getValue()).intValue();
            if (j != -1)
            {
              AddressSourceResult localAddressSourceResult1 = new AddressSourceResult((String)localEntry.getKey(), paramString);
              ((ArrayList)localObject).add(j + i, localAddressSourceResult1);
              i++;
            }
          }
        }
      }
    }
    
    public final CharSequence convertResultToString(Object paramObject)
    {
      if ((paramObject instanceof AddressSourceResult)) {
        return ((AddressSourceResult)paramObject).matchingTerm;
      }
      return super.convertResultToString(paramObject);
    }
    
    protected final Filter.FilterResults performFiltering(CharSequence paramCharSequence)
    {
      ArrayList localArrayList = performFilteringForValues(paramCharSequence);
      Filter.FilterResults localFilterResults = new Filter.FilterResults();
      localFilterResults.values = localArrayList;
      localFilterResults.count = localArrayList.size();
      return localFilterResults;
    }
    
    protected final void publishResults(CharSequence paramCharSequence, Filter.FilterResults paramFilterResults)
    {
      ArrayList localArrayList = (ArrayList)paramFilterResults.values;
      AddressSourceResultAdapter.this.mLastPublishedConstraint = paramCharSequence;
      AddressSourceResultAdapter.this.mObjects = localArrayList;
      if ((AddressSourceResultAdapter.this.mObjects != null) && (!AddressSourceResultAdapter.this.mObjects.isEmpty()))
      {
        AddressSourceResultAdapter.this.notifyDataSetChanged();
        return;
      }
      AddressSourceResultAdapter.this.notifyDataSetInvalidated();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.wallet.ui.address.AddressSourceResultAdapter
 * JD-Core Version:    0.7.0.1
 */