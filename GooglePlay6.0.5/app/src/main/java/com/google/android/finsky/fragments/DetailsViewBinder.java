package com.google.android.finsky.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.google.android.finsky.api.DfeApi;
import com.google.android.finsky.api.model.Document;
import com.google.android.finsky.layout.LayoutSwitcher;
import com.google.android.finsky.navigationmanager.NavigationManager;
import com.google.android.finsky.utils.ErrorStrings;

public abstract class DetailsViewBinder
  implements Response.ErrorListener
{
  public Context mContext;
  public DfeApi mDfeApi;
  public Document mDoc;
  protected int mHeaderLayoutId;
  protected LayoutInflater mInflater;
  protected View mLayout;
  public LayoutSwitcher mLayoutSwitcher;
  public NavigationManager mNavigationManager;
  
  public final void bind$4d2badcf(View paramView, Document paramDocument)
  {
    this.mLayout = paramView;
    this.mDoc = paramDocument;
    this.mLayout = paramView;
    TextView localTextView = (TextView)this.mLayout.findViewById(this.mHeaderLayoutId);
    if (localTextView != null) {
      localTextView.setText(null);
    }
  }
  
  public final void init(Context paramContext, DfeApi paramDfeApi, NavigationManager paramNavigationManager)
  {
    this.mContext = paramContext;
    this.mDfeApi = paramDfeApi;
    this.mNavigationManager = paramNavigationManager;
    this.mInflater = LayoutInflater.from(this.mContext);
    this.mHeaderLayoutId = 2131755487;
  }
  
  public final void onErrorResponse(VolleyError paramVolleyError)
  {
    if (this.mLayoutSwitcher != null) {
      this.mLayoutSwitcher.switchToErrorMode(ErrorStrings.get(this.mContext, paramVolleyError));
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.fragments.DetailsViewBinder
 * JD-Core Version:    0.7.0.1
 */