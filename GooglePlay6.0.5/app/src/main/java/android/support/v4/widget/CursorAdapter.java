package android.support.v4.widget;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;

public abstract class CursorAdapter
  extends BaseAdapter
  implements CursorFilter.CursorFilterClient, Filterable
{
  protected boolean mAutoRequery = true;
  protected ChangeObserver mChangeObserver;
  public Context mContext;
  public Cursor mCursor = null;
  protected CursorFilter mCursorFilter;
  protected DataSetObserver mDataSetObserver;
  protected boolean mDataValid = false;
  protected FilterQueryProvider mFilterQueryProvider;
  protected int mRowIDColumn;
  
  public CursorAdapter(Context paramContext, Cursor paramCursor, boolean paramBoolean)
  {
    this.mContext = paramContext;
    this.mRowIDColumn = -1;
    this.mChangeObserver = new ChangeObserver();
    this.mDataSetObserver = new MyDataSetObserver((byte)0);
  }
  
  public abstract void bindView$4693bf34(View paramView, Cursor paramCursor);
  
  public void changeCursor(Cursor paramCursor)
  {
    Cursor localCursor;
    if (paramCursor == this.mCursor) {
      localCursor = null;
    }
    for (;;)
    {
      if (localCursor != null) {
        localCursor.close();
      }
      return;
      localCursor = this.mCursor;
      if (localCursor != null)
      {
        if (this.mChangeObserver != null) {
          localCursor.unregisterContentObserver(this.mChangeObserver);
        }
        if (this.mDataSetObserver != null) {
          localCursor.unregisterDataSetObserver(this.mDataSetObserver);
        }
      }
      this.mCursor = paramCursor;
      if (paramCursor != null)
      {
        if (this.mChangeObserver != null) {
          paramCursor.registerContentObserver(this.mChangeObserver);
        }
        if (this.mDataSetObserver != null) {
          paramCursor.registerDataSetObserver(this.mDataSetObserver);
        }
        this.mRowIDColumn = paramCursor.getColumnIndexOrThrow("_id");
        this.mDataValid = true;
        notifyDataSetChanged();
      }
      else
      {
        this.mRowIDColumn = -1;
        this.mDataValid = false;
        notifyDataSetInvalidated();
      }
    }
  }
  
  public CharSequence convertToString(Cursor paramCursor)
  {
    if (paramCursor == null) {
      return "";
    }
    return paramCursor.toString();
  }
  
  public int getCount()
  {
    if ((this.mDataValid) && (this.mCursor != null)) {
      return this.mCursor.getCount();
    }
    return 0;
  }
  
  public final Cursor getCursor()
  {
    return this.mCursor;
  }
  
  public View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (this.mDataValid)
    {
      this.mCursor.moveToPosition(paramInt);
      if (paramView == null) {}
      for (View localView = newDropDownView(this.mContext, this.mCursor, paramViewGroup);; localView = paramView)
      {
        bindView$4693bf34(localView, this.mCursor);
        return localView;
      }
    }
    return null;
  }
  
  public Filter getFilter()
  {
    if (this.mCursorFilter == null) {
      this.mCursorFilter = new CursorFilter(this);
    }
    return this.mCursorFilter;
  }
  
  public Object getItem(int paramInt)
  {
    if ((this.mDataValid) && (this.mCursor != null))
    {
      this.mCursor.moveToPosition(paramInt);
      return this.mCursor;
    }
    return null;
  }
  
  public long getItemId(int paramInt)
  {
    long l = 0L;
    if ((this.mDataValid) && (this.mCursor != null) && (this.mCursor.moveToPosition(paramInt))) {
      l = this.mCursor.getLong(this.mRowIDColumn);
    }
    return l;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (!this.mDataValid) {
      throw new IllegalStateException("this should only be called when the cursor is valid");
    }
    if (!this.mCursor.moveToPosition(paramInt)) {
      throw new IllegalStateException("couldn't move cursor to position " + paramInt);
    }
    if (paramView == null) {}
    for (View localView = newView(this.mContext, this.mCursor, paramViewGroup);; localView = paramView)
    {
      bindView$4693bf34(localView, this.mCursor);
      return localView;
    }
  }
  
  public boolean hasStableIds()
  {
    return true;
  }
  
  public View newDropDownView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    return newView(paramContext, paramCursor, paramViewGroup);
  }
  
  public abstract View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup);
  
  protected final void onContentChanged()
  {
    if ((this.mAutoRequery) && (this.mCursor != null) && (!this.mCursor.isClosed())) {
      this.mDataValid = this.mCursor.requery();
    }
  }
  
  public Cursor runQueryOnBackgroundThread(CharSequence paramCharSequence)
  {
    if (this.mFilterQueryProvider != null) {
      return this.mFilterQueryProvider.runQuery(paramCharSequence);
    }
    return this.mCursor;
  }
  
  private final class ChangeObserver
    extends ContentObserver
  {
    public ChangeObserver()
    {
      super();
    }
    
    public final boolean deliverSelfNotifications()
    {
      return true;
    }
    
    public final void onChange(boolean paramBoolean)
    {
      CursorAdapter.this.onContentChanged();
    }
  }
  
  private final class MyDataSetObserver
    extends DataSetObserver
  {
    private MyDataSetObserver() {}
    
    public final void onChanged()
    {
      CursorAdapter.this.mDataValid = true;
      CursorAdapter.this.notifyDataSetChanged();
    }
    
    public final void onInvalidated()
    {
      CursorAdapter.this.mDataValid = false;
      CursorAdapter.this.notifyDataSetInvalidated();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.widget.CursorAdapter
 * JD-Core Version:    0.7.0.1
 */