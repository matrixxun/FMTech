package com.google.android.libraries.bind.data;

import java.util.List;
import java.util.concurrent.Executor;

public abstract class BaseFilter
  implements Filter
{
  private final Executor executor;
  
  public BaseFilter(Executor paramExecutor)
  {
    this.executor = paramExecutor;
  }
  
  public final Executor executor()
  {
    return this.executor;
  }
  
  public List<Data> transform$7ddb133f(List<Data> paramList)
  {
    return paramList;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.libraries.bind.data.BaseFilter
 * JD-Core Version:    0.7.0.1
 */