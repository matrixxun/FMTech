package com.google.android.finsky.uninstall;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.TextView;
import com.google.android.play.image.FifeImageView;

public final class UninstallManagerAppViewHolder
  extends RecyclerView.ViewHolder
  implements View.OnClickListener
{
  final FifeImageView mAppIcon;
  final CheckBox mCheckbox;
  Context mContext;
  AppViewHolderClickListener mListener;
  final TextView mSize;
  final TextView mTitle;
  final TextView mUninstalling;
  
  public UninstallManagerAppViewHolder(View paramView, Context paramContext)
  {
    super(paramView);
    this.mContext = paramContext;
    this.mAppIcon = ((FifeImageView)paramView.findViewById(2131756179));
    this.mTitle = ((TextView)paramView.findViewById(2131756180));
    this.mSize = ((TextView)paramView.findViewById(2131756181));
    this.mUninstalling = ((TextView)paramView.findViewById(2131756182));
    this.mCheckbox = ((CheckBox)paramView.findViewById(2131756183));
    paramView.setOnClickListener(this);
    this.mCheckbox.setOnClickListener(this);
  }
  
  public final void onClick(View paramView)
  {
    if (this.mListener != null) {
      this.mListener.onAppViewHolderClicked$5359dc9a(getAdapterPosition());
    }
  }
  
  public static abstract interface AppViewHolderClickListener
  {
    public abstract void onAppViewHolderClicked$5359dc9a(int paramInt);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.uninstall.UninstallManagerAppViewHolder
 * JD-Core Version:    0.7.0.1
 */