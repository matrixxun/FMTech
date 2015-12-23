package com.google.android.finsky.api.model;

import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import java.util.HashSet;

public abstract class DfeModel
  implements Response.ErrorListener
{
  private HashSet<Response.ErrorListener> mErrorListeners = new HashSet();
  private HashSet<OnDataChangedListener> mListeners = new HashSet();
  private VolleyError mVolleyError;
  
  public final void addDataChangedListener(OnDataChangedListener paramOnDataChangedListener)
  {
    if (!this.mListeners.contains(paramOnDataChangedListener)) {
      this.mListeners.add(paramOnDataChangedListener);
    }
  }
  
  public final void addErrorListener(Response.ErrorListener paramErrorListener)
  {
    if (!this.mErrorListeners.contains(paramErrorListener)) {
      this.mErrorListeners.add(paramErrorListener);
    }
  }
  
  protected void clearErrors()
  {
    this.mVolleyError = null;
  }
  
  public VolleyError getVolleyError()
  {
    return this.mVolleyError;
  }
  
  public boolean inErrorState()
  {
    return this.mVolleyError != null;
  }
  
  public abstract boolean isReady();
  
  protected final void notifyDataSetChanged()
  {
    OnDataChangedListener[] arrayOfOnDataChangedListener = (OnDataChangedListener[])this.mListeners.toArray(new OnDataChangedListener[this.mListeners.size()]);
    for (int i = 0; i < arrayOfOnDataChangedListener.length; i++) {
      arrayOfOnDataChangedListener[i].onDataChanged();
    }
  }
  
  protected final void notifyErrorOccured(VolleyError paramVolleyError)
  {
    Response.ErrorListener[] arrayOfErrorListener = (Response.ErrorListener[])this.mErrorListeners.toArray(new Response.ErrorListener[this.mErrorListeners.size()]);
    for (int i = 0; i < arrayOfErrorListener.length; i++) {
      arrayOfErrorListener[i].onErrorResponse(paramVolleyError);
    }
  }
  
  public void onErrorResponse(VolleyError paramVolleyError)
  {
    this.mVolleyError = paramVolleyError;
    notifyErrorOccured(paramVolleyError);
  }
  
  public final void removeDataChangedListener(OnDataChangedListener paramOnDataChangedListener)
  {
    this.mListeners.remove(paramOnDataChangedListener);
  }
  
  public final void removeErrorListener(Response.ErrorListener paramErrorListener)
  {
    this.mErrorListeners.remove(paramErrorListener);
  }
  
  public final void unregisterAll()
  {
    this.mListeners.clear();
    this.mErrorListeners.clear();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.api.model.DfeModel
 * JD-Core Version:    0.7.0.1
 */