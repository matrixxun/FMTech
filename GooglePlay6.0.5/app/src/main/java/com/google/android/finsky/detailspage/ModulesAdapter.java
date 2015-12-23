package com.google.android.finsky.detailspage;

import android.os.Handler;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import com.google.android.finsky.utils.Lists;
import java.util.ArrayList;
import java.util.List;

public final class ModulesAdapter
  extends RecyclerView.Adapter<ModuleViewHolder>
{
  Handler mHandler = new Handler();
  List<Module> mModules;
  
  public ModulesAdapter(List<Module> paramList)
  {
    if (paramList != null)
    {
      this.mModules = Lists.newArrayList(paramList);
      return;
    }
    this.mModules = new ArrayList();
  }
  
  public final int getItemCount()
  {
    return this.mModules.size();
  }
  
  public final int getItemViewType(int paramInt)
  {
    return ((Module)this.mModules.get(paramInt)).getLayoutResId();
  }
  
  public static abstract class Module
  {
    ModulesAdapter.ModuleViewHolder mModuleViewHolder;
    
    public abstract void bindView(View paramView);
    
    public abstract int getLayoutResId();
    
    public void onRecycleView(View paramView) {}
  }
  
  protected static final class ModuleViewHolder
    extends RecyclerView.ViewHolder
  {
    ModulesAdapter.Module module;
    
    public ModuleViewHolder(View paramView)
    {
      super();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.detailspage.ModulesAdapter
 * JD-Core Version:    0.7.0.1
 */