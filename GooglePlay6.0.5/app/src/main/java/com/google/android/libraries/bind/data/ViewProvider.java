package com.google.android.libraries.bind.data;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.libraries.bind.widget.LoadingView;

public abstract interface ViewProvider
{
  public static final ViewProvider DEFAULT_EMPTY_VIEW_PROVIDER = new ViewProvider()
  {
    public final View getView$70191498(ViewGroup paramAnonymousViewGroup)
    {
      return new View(paramAnonymousViewGroup.getContext());
    }
  };
  public static final ViewProvider DEFAULT_ERROR_VIEW_PROVIDER = new ViewProvider()
  {
    public final View getView$70191498(ViewGroup paramAnonymousViewGroup)
    {
      TextView localTextView = new TextView(paramAnonymousViewGroup.getContext());
      localTextView.setText("Error! (replace me)");
      return localTextView;
    }
  };
  public static final ViewProvider DEFAULT_LOADING_VIEW_PROVIDER = new ViewProvider()
  {
    public final View getView$70191498(ViewGroup paramAnonymousViewGroup)
    {
      return new LoadingView(paramAnonymousViewGroup.getContext());
    }
  };
  
  public abstract View getView$70191498(ViewGroup paramViewGroup);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.data.ViewProvider
 * JD-Core Version:    0.7.0.1
 */