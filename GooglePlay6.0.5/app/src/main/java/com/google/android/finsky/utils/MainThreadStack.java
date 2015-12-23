package com.google.android.finsky.utils;

import java.util.Stack;

public final class MainThreadStack<T>
  extends Stack<T>
{
  public final boolean isEmpty()
  {
    Utils.ensureOnMainThread();
    return super.isEmpty();
  }
  
  public final T peek()
  {
    Utils.ensureOnMainThread();
    return super.peek();
  }
  
  public final T pop()
  {
    Utils.ensureOnMainThread();
    return super.pop();
  }
  
  public final T push(T paramT)
  {
    Utils.ensureOnMainThread();
    return super.push(paramT);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.utils.MainThreadStack
 * JD-Core Version:    0.7.0.1
 */