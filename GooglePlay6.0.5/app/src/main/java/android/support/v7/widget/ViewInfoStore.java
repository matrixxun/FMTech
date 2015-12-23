package android.support.v7.widget;

import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.Pools.Pool;
import android.support.v4.util.Pools.SimplePool;

final class ViewInfoStore
{
  final ArrayMap<RecyclerView.ViewHolder, InfoRecord> mLayoutHolderMap = new ArrayMap();
  final LongSparseArray<RecyclerView.ViewHolder> mOldChangedHolders = new LongSparseArray();
  
  final void addToDisappearedInLayout(RecyclerView.ViewHolder paramViewHolder)
  {
    InfoRecord localInfoRecord = (InfoRecord)this.mLayoutHolderMap.get(paramViewHolder);
    if (localInfoRecord == null)
    {
      localInfoRecord = InfoRecord.obtain();
      this.mLayoutHolderMap.put(paramViewHolder, localInfoRecord);
    }
    localInfoRecord.flags = (0x1 | localInfoRecord.flags);
  }
  
  final void addToOldChangeHolders(long paramLong, RecyclerView.ViewHolder paramViewHolder)
  {
    this.mOldChangedHolders.put(paramLong, paramViewHolder);
  }
  
  final void addToPreLayout(RecyclerView.ViewHolder paramViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo)
  {
    InfoRecord localInfoRecord = (InfoRecord)this.mLayoutHolderMap.get(paramViewHolder);
    if (localInfoRecord == null)
    {
      localInfoRecord = InfoRecord.obtain();
      this.mLayoutHolderMap.put(paramViewHolder, localInfoRecord);
    }
    localInfoRecord.preInfo = paramItemHolderInfo;
    localInfoRecord.flags = (0x4 | localInfoRecord.flags);
  }
  
  final void clear()
  {
    this.mLayoutHolderMap.clear();
    this.mOldChangedHolders.clear();
  }
  
  final void removeFromDisappearedInLayout(RecyclerView.ViewHolder paramViewHolder)
  {
    InfoRecord localInfoRecord = (InfoRecord)this.mLayoutHolderMap.get(paramViewHolder);
    if (localInfoRecord == null) {
      return;
    }
    localInfoRecord.flags = (0xFFFFFFFE & localInfoRecord.flags);
  }
  
  final void removeViewHolder(RecyclerView.ViewHolder paramViewHolder)
  {
    for (int i = -1 + this.mOldChangedHolders.size();; i--)
    {
      if (i >= 0)
      {
        if (paramViewHolder != this.mOldChangedHolders.valueAt(i)) {
          continue;
        }
        LongSparseArray localLongSparseArray = this.mOldChangedHolders;
        if (localLongSparseArray.mValues[i] != LongSparseArray.DELETED)
        {
          localLongSparseArray.mValues[i] = LongSparseArray.DELETED;
          localLongSparseArray.mGarbage = true;
        }
      }
      InfoRecord localInfoRecord = (InfoRecord)this.mLayoutHolderMap.remove(paramViewHolder);
      if (localInfoRecord != null) {
        InfoRecord.recycle(localInfoRecord);
      }
      return;
    }
  }
  
  static final class InfoRecord
  {
    static Pools.Pool<InfoRecord> sPool = new Pools.SimplePool(20);
    int flags;
    RecyclerView.ItemAnimator.ItemHolderInfo postInfo;
    RecyclerView.ItemAnimator.ItemHolderInfo preInfo;
    
    static void drainCache()
    {
      while (sPool.acquire() != null) {}
    }
    
    static InfoRecord obtain()
    {
      InfoRecord localInfoRecord = (InfoRecord)sPool.acquire();
      if (localInfoRecord == null) {
        localInfoRecord = new InfoRecord();
      }
      return localInfoRecord;
    }
    
    static void recycle(InfoRecord paramInfoRecord)
    {
      paramInfoRecord.flags = 0;
      paramInfoRecord.preInfo = null;
      paramInfoRecord.postInfo = null;
      sPool.release(paramInfoRecord);
    }
  }
  
  static abstract interface ProcessCallback
  {
    public abstract void processAppeared(RecyclerView.ViewHolder paramViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo1, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo2);
    
    public abstract void processDisappeared(RecyclerView.ViewHolder paramViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo1, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo2);
    
    public abstract void processPersistent(RecyclerView.ViewHolder paramViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo1, RecyclerView.ItemAnimator.ItemHolderInfo paramItemHolderInfo2);
    
    public abstract void unused(RecyclerView.ViewHolder paramViewHolder);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v7.widget.ViewInfoStore
 * JD-Core Version:    0.7.0.1
 */