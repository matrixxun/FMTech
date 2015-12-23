package com.google.android.vending.verifier.api;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.google.protobuf.nano.MessageNano;

public abstract class BaseVerificationRequestWithResult<T, U extends MessageNano>
  extends BaseVerificationRequest<T, U>
{
  private final Response.Listener<T> mListener;
  
  public BaseVerificationRequestWithResult(String paramString, Response.Listener<T> paramListener, Response.ErrorListener paramErrorListener, U paramU)
  {
    super(paramString, paramErrorListener, paramU);
    this.mListener = paramListener;
  }
  
  protected final void deliverResponse(T paramT)
  {
    this.mListener.onResponse(paramT);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.vending.verifier.api.BaseVerificationRequestWithResult
 * JD-Core Version:    0.7.0.1
 */