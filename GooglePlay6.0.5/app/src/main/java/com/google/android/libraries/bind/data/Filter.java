package com.google.android.libraries.bind.data;

import java.util.List;
import java.util.concurrent.Executor;

public abstract interface Filter
{
  public abstract Executor executor();
  
  public abstract List<Data> transform$7ddb133f(List<Data> paramList);
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.data.Filter
 * JD-Core Version:    0.7.0.1
 */