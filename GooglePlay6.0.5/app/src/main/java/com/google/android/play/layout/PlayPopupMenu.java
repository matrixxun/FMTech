package com.google.android.play.layout;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.ListPopupWindow;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import com.google.android.play.R.id;
import com.google.android.play.R.layout;
import java.util.ArrayList;
import java.util.List;

public final class PlayPopupMenu
  implements PopupWindow.OnDismissListener
{
  protected final View mAnchor;
  protected final Context mContext;
  public PopupWindow.OnDismissListener mOnPopupDismissListener;
  protected final List<PopupAction> mPopupActions;
  protected ListPopupWindow mPopupWindow;
  
  public PlayPopupMenu(Context paramContext, View paramView)
  {
    this.mContext = paramContext;
    this.mAnchor = paramView;
    this.mPopupActions = new ArrayList();
  }
  
  public final void addMenuItem(int paramInt, CharSequence paramCharSequence, boolean paramBoolean, OnPopupActionSelectedListener paramOnPopupActionSelectedListener)
  {
    this.mPopupActions.add(new PopupAction(paramInt, paramCharSequence, paramBoolean, paramOnPopupActionSelectedListener));
  }
  
  public final void dismiss()
  {
    if (this.mPopupWindow != null) {
      this.mPopupWindow.dismiss();
    }
  }
  
  public final void onDismiss()
  {
    this.mPopupWindow = null;
    if (this.mOnPopupDismissListener != null) {
      this.mOnPopupDismissListener.onDismiss();
    }
  }
  
  public final void show()
  {
    this.mPopupWindow = new ListPopupWindow(this.mContext);
    final PopupListAdapter localPopupListAdapter = new PopupListAdapter(this.mContext, this.mPopupActions);
    this.mPopupWindow.setAdapter(localPopupListAdapter);
    this.mPopupWindow.setOnDismissListener(this);
    this.mPopupWindow.mItemClickListener = new AdapterView.OnItemClickListener()
    {
      public final void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        if (paramAnonymousInt < 0) {}
        PlayPopupMenu.PopupAction localPopupAction;
        do
        {
          return;
          PlayPopupMenu.this.dismiss();
          localPopupAction = (PlayPopupMenu.PopupAction)localPopupListAdapter.mPopupActions.get(paramAnonymousInt);
        } while (localPopupAction.mPopupActionListener == null);
        localPopupAction.mPopupActionListener.onActionSelected(localPopupAction.mId);
      }
    };
    this.mPopupWindow.mDropDownAnchorView = this.mAnchor;
    int i = this.mContext.getResources().getDisplayMetrics().widthPixels;
    ListPopupWindow localListPopupWindow = this.mPopupWindow;
    FrameLayout localFrameLayout = new FrameLayout(this.mContext);
    int j = View.MeasureSpec.makeMeasureSpec(0, 0);
    int k = View.MeasureSpec.makeMeasureSpec(0, 0);
    int m = localPopupListAdapter.getCount();
    int n = 0;
    int i1 = 0;
    View localView1 = null;
    int i2 = 0;
    int i3;
    if (n < m)
    {
      i3 = localPopupListAdapter.getItemViewType(n);
      if (i3 == i1) {
        break label237;
      }
    }
    for (View localView2 = null;; localView2 = localView1)
    {
      localView1 = localPopupListAdapter.getView(n, localView2, localFrameLayout);
      localView1.measure(j, k);
      i2 = Math.max(i2, localView1.getMeasuredWidth());
      n++;
      i1 = i3;
      break;
      localListPopupWindow.setContentWidth(Math.min(i, i2));
      this.mPopupWindow.setModal$1385ff();
      this.mPopupWindow.show();
      return;
      label237:
      i3 = i1;
    }
  }
  
  public static abstract interface OnPopupActionSelectedListener
  {
    public abstract void onActionSelected(int paramInt);
  }
  
  protected static final class PopupAction
  {
    public final int mId;
    public final boolean mIsEnabled;
    public final PlayPopupMenu.OnPopupActionSelectedListener mPopupActionListener;
    public final CharSequence mTitle;
    
    public PopupAction(int paramInt, CharSequence paramCharSequence, boolean paramBoolean, PlayPopupMenu.OnPopupActionSelectedListener paramOnPopupActionSelectedListener)
    {
      this.mId = paramInt;
      this.mTitle = paramCharSequence;
      this.mIsEnabled = paramBoolean;
      this.mPopupActionListener = paramOnPopupActionSelectedListener;
    }
  }
  
  public static final class PopupListAdapter
    extends BaseAdapter
  {
    protected final Context mContext;
    List<PlayPopupMenu.PopupAction> mPopupActions;
    
    public PopupListAdapter(Context paramContext, List<PlayPopupMenu.PopupAction> paramList)
    {
      this.mContext = paramContext;
      this.mPopupActions = paramList;
    }
    
    public final int getCount()
    {
      return this.mPopupActions.size();
    }
    
    public final Object getItem(int paramInt)
    {
      return this.mPopupActions.get(paramInt);
    }
    
    public final long getItemId(int paramInt)
    {
      return paramInt;
    }
    
    public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null)
      {
        paramView = LayoutInflater.from(this.mContext).inflate(R.layout.abc_popup_menu_item_layout, paramViewGroup, false);
        ViewHolder localViewHolder2 = new ViewHolder((byte)0);
        localViewHolder2.title = ((TextView)paramView.findViewById(R.id.title));
        localViewHolder2.shortcut = ((TextView)paramView.findViewById(R.id.shortcut));
        paramView.setTag(localViewHolder2);
      }
      ViewHolder localViewHolder1 = (ViewHolder)paramView.getTag();
      PlayPopupMenu.PopupAction localPopupAction = (PlayPopupMenu.PopupAction)this.mPopupActions.get(paramInt);
      localViewHolder1.title.setText(localPopupAction.mTitle);
      localViewHolder1.title.setEnabled(localPopupAction.mIsEnabled);
      localViewHolder1.shortcut.setVisibility(8);
      return paramView;
    }
    
    public final boolean isEnabled(int paramInt)
    {
      return ((PlayPopupMenu.PopupAction)this.mPopupActions.get(paramInt)).mIsEnabled;
    }
    
    private static final class ViewHolder
    {
      TextView shortcut;
      TextView title;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.play.layout.PlayPopupMenu
 * JD-Core Version:    0.7.0.1
 */