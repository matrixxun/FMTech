package com.google.android.finsky.uninstall;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObservable;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.android.finsky.utils.FinskyLog;
import com.google.android.play.image.FifeImageView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class UninstallManagerRecyclerViewAdapter
  extends RecyclerView.Adapter<RecyclerView.ViewHolder>
  implements UninstallManagerAppViewHolder.AppViewHolderClickListener, UninstallManagerSpinnerViewHolder.SpinnerViewHolderSelectionListener
{
  ArrayList<Boolean> mChecked = new ArrayList();
  private Context mContext;
  Comparator<UninstallManagerDataModel.UninstallManagerSizedDoc> mCurrentComparator;
  int mCurrentSortType;
  ArrayList<UninstallManagerDataModel.UninstallManagerSizedDoc> mDocs = new ArrayList();
  private LayoutInflater mLayoutInflater;
  RowCheckedListener mRowCheckedListener;
  ArrayList<Boolean> mUninstalling = new ArrayList();
  
  public UninstallManagerRecyclerViewAdapter(Context paramContext)
  {
    this.mContext = paramContext;
    this.mLayoutInflater = LayoutInflater.from(this.mContext);
    this.mCurrentSortType = 0;
    this.mCurrentComparator = createComparator(this.mCurrentSortType);
  }
  
  static Comparator<UninstallManagerDataModel.UninstallManagerSizedDoc> createComparator(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return new DocNameComparator((byte)0);
    }
    return new DocSizeComparator((byte)0);
  }
  
  private boolean isItemChecked(int paramInt)
  {
    return ((Boolean)this.mChecked.get(paramInt)).booleanValue();
  }
  
  public final int getItemCount()
  {
    return 1 + this.mDocs.size();
  }
  
  public final int getItemViewType(int paramInt)
  {
    if (paramInt == 0) {
      return 2130969144;
    }
    return 2130969143;
  }
  
  public final boolean isAnyItemChecked()
  {
    return this.mChecked.contains(Boolean.TRUE);
  }
  
  public final void onAppViewHolderClicked$5359dc9a(int paramInt)
  {
    int i;
    if (paramInt != -1)
    {
      i = paramInt - 1;
      if (isItemChecked(i)) {
        break label31;
      }
    }
    label31:
    for (boolean bool = true;; bool = false)
    {
      setItemChecked(i, bool);
      notifyItemChanged(paramInt);
      return;
    }
  }
  
  public final void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt)
  {
    switch (paramViewHolder.mItemViewType)
    {
    default: 
      FinskyLog.wtf("Uninstall Manager binding ViewHolder of unknown type", new Object[0]);
      return;
    case 2131756184: 
      UninstallManagerSpinnerViewHolder localUninstallManagerSpinnerViewHolder = (UninstallManagerSpinnerViewHolder)paramViewHolder;
      int j = this.mCurrentSortType;
      if ((j >= 0) && (j < 2))
      {
        localUninstallManagerSpinnerViewHolder.mSpinner.setSelection(j);
        return;
      }
      FinskyLog.wtf("Tried to set Uninstall Wizard spinner to an invalid selection", new Object[0]);
      return;
    }
    int i = paramInt - 1;
    UninstallManagerDataModel.UninstallManagerSizedDoc localUninstallManagerSizedDoc = (UninstallManagerDataModel.UninstallManagerSizedDoc)this.mDocs.get(i);
    UninstallManagerAppViewHolder localUninstallManagerAppViewHolder = (UninstallManagerAppViewHolder)paramViewHolder;
    boolean bool1 = isItemChecked(i);
    boolean bool2 = ((Boolean)this.mUninstalling.get(i)).booleanValue();
    localUninstallManagerAppViewHolder.mTitle.setText(localUninstallManagerSizedDoc.title);
    if (localUninstallManagerSizedDoc.bytes == -1L) {
      localUninstallManagerAppViewHolder.mSize.setText(localUninstallManagerAppViewHolder.mContext.getResources().getString(2131363039));
    }
    for (;;)
    {
      localUninstallManagerAppViewHolder.mCheckbox.setChecked(bool1);
      try
      {
        PackageManager localPackageManager = localUninstallManagerAppViewHolder.mContext.getPackageManager();
        localUninstallManagerAppViewHolder.mAppIcon.setImageDrawable(localPackageManager.getApplicationIcon(localUninstallManagerSizedDoc.packageName));
        if (bool2)
        {
          localUninstallManagerAppViewHolder.mCheckbox.setVisibility(8);
          localUninstallManagerAppViewHolder.mUninstalling.setVisibility(0);
          localUninstallManagerAppViewHolder.mCheckbox.setEnabled(false);
          return;
          localUninstallManagerAppViewHolder.mSize.setText(Formatter.formatFileSize(localUninstallManagerAppViewHolder.mContext, localUninstallManagerSizedDoc.bytes));
        }
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        for (;;)
        {
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = localUninstallManagerSizedDoc.packageName;
          FinskyLog.wtf("%s not found in PackageManager", arrayOfObject);
          localUninstallManagerAppViewHolder.mAppIcon.clearImage();
        }
        localUninstallManagerAppViewHolder.mUninstalling.setVisibility(8);
        localUninstallManagerAppViewHolder.mCheckbox.setVisibility(0);
        localUninstallManagerAppViewHolder.mCheckbox.setEnabled(true);
      }
    }
  }
  
  public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
  {
    switch (paramInt)
    {
    default: 
      FinskyLog.wtf("Uninstall Manager unknown ViewHolder type requested", new Object[0]);
      return null;
    case 2130969144: 
      UninstallManagerSpinnerViewHolder localUninstallManagerSpinnerViewHolder = new UninstallManagerSpinnerViewHolder(this.mLayoutInflater.inflate(2130969144, paramViewGroup, false), this.mContext);
      localUninstallManagerSpinnerViewHolder.mListener = this;
      return localUninstallManagerSpinnerViewHolder;
    }
    UninstallManagerAppViewHolder localUninstallManagerAppViewHolder = new UninstallManagerAppViewHolder(this.mLayoutInflater.inflate(2130969143, paramViewGroup, false), this.mContext);
    localUninstallManagerAppViewHolder.mListener = this;
    return localUninstallManagerAppViewHolder;
  }
  
  public final void onSpinnerItemSelected(int paramInt)
  {
    this.mCurrentSortType = paramInt;
    this.mCurrentComparator = createComparator(paramInt);
    setDocs(new ArrayList(this.mDocs));
  }
  
  public final void setDocs(List<UninstallManagerDataModel.UninstallManagerSizedDoc> paramList)
  {
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    HashMap localHashMap3 = new HashMap();
    for (int i = 0; i < this.mDocs.size(); i++)
    {
      String str2 = ((UninstallManagerDataModel.UninstallManagerSizedDoc)this.mDocs.get(i)).packageName;
      localHashMap1.put(str2, this.mDocs.get(i));
      localHashMap2.put(str2, this.mChecked.get(i));
      localHashMap3.put(str2, this.mUninstalling.get(i));
    }
    this.mUninstalling.clear();
    this.mChecked.clear();
    this.mDocs.clear();
    if (paramList != null) {
      this.mDocs.addAll(paramList);
    }
    Collections.sort(this.mDocs, this.mCurrentComparator);
    int j = 0;
    if (j < this.mDocs.size())
    {
      String str1 = ((UninstallManagerDataModel.UninstallManagerSizedDoc)this.mDocs.get(j)).packageName;
      if (localHashMap1.containsKey(str1))
      {
        this.mChecked.add(j, localHashMap2.get(str1));
        this.mUninstalling.add(j, localHashMap3.get(str1));
        localHashMap2.remove(str1);
      }
      for (;;)
      {
        j++;
        break;
        this.mChecked.add(j, Boolean.FALSE);
        this.mUninstalling.add(j, Boolean.FALSE);
      }
    }
    if (this.mRowCheckedListener != null)
    {
      long l = 0L;
      Iterator localIterator = localHashMap2.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        if (localEntry.getValue() == Boolean.TRUE) {
          l += ((UninstallManagerDataModel.UninstallManagerSizedDoc)localHashMap1.get((String)localEntry.getKey())).bytes;
        }
      }
      this.mRowCheckedListener.onRowChecked(false, l);
    }
    this.mObservable.notifyChanged();
  }
  
  final void setItemChecked(int paramInt, boolean paramBoolean)
  {
    this.mChecked.set(paramInt, Boolean.valueOf(paramBoolean));
    if (this.mRowCheckedListener != null)
    {
      if (((UninstallManagerDataModel.UninstallManagerSizedDoc)this.mDocs.get(paramInt)).bytes != -1L) {
        this.mRowCheckedListener.onRowChecked(paramBoolean, ((UninstallManagerDataModel.UninstallManagerSizedDoc)this.mDocs.get(paramInt)).bytes);
      }
    }
    else {
      return;
    }
    this.mRowCheckedListener.onRowChecked(paramBoolean, 0L);
  }
  
  private static final class DocNameComparator
    implements Comparator<UninstallManagerDataModel.UninstallManagerSizedDoc>
  {}
  
  private static final class DocSizeComparator
    implements Comparator<UninstallManagerDataModel.UninstallManagerSizedDoc>
  {}
  
  public static abstract interface RowCheckedListener
  {
    public abstract void onRowChecked(boolean paramBoolean, long paramLong);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.uninstall.UninstallManagerRecyclerViewAdapter
 * JD-Core Version:    0.7.0.1
 */