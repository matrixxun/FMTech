package com.squareup.okhttp.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

final class OptionalMethod<T>
{
  private final String methodName;
  private final Class[] methodParams;
  private final Class<?> returnType;
  
  public OptionalMethod(Class<?> paramClass, String paramString, Class... paramVarArgs)
  {
    this.returnType = paramClass;
    this.methodName = paramString;
    this.methodParams = paramVarArgs;
  }
  
  private Method getMethod(Class<?> paramClass)
  {
    String str = this.methodName;
    Method localMethod = null;
    if (str != null)
    {
      localMethod = getPublicMethod(paramClass, this.methodName, this.methodParams);
      if ((localMethod != null) && (this.returnType != null) && (!this.returnType.isAssignableFrom(localMethod.getReturnType()))) {
        localMethod = null;
      }
    }
    return localMethod;
  }
  
  private static Method getPublicMethod(Class<?> paramClass, String paramString, Class[] paramArrayOfClass)
  {
    Method localMethod = null;
    try
    {
      localMethod = paramClass.getMethod(paramString, paramArrayOfClass);
      int i = localMethod.getModifiers();
      if ((i & 0x1) == 0) {
        localMethod = null;
      }
      return localMethod;
    }
    catch (NoSuchMethodException localNoSuchMethodException) {}
    return localMethod;
  }
  
  private Object invoke(T paramT, Object... paramVarArgs)
    throws InvocationTargetException
  {
    Method localMethod = getMethod(paramT.getClass());
    if (localMethod == null) {
      throw new AssertionError("Method " + this.methodName + " not supported for object " + paramT);
    }
    try
    {
      Object localObject = localMethod.invoke(paramT, paramVarArgs);
      return localObject;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      AssertionError localAssertionError = new AssertionError("Unexpectedly could not call: " + localMethod);
      localAssertionError.initCause(localIllegalAccessException);
      throw localAssertionError;
    }
  }
  
  private Object invokeOptional(T paramT, Object... paramVarArgs)
    throws InvocationTargetException
  {
    Method localMethod = getMethod(paramT.getClass());
    if (localMethod == null) {
      return null;
    }
    try
    {
      Object localObject = localMethod.invoke(paramT, paramVarArgs);
      return localObject;
    }
    catch (IllegalAccessException localIllegalAccessException) {}
    return null;
  }
  
  public final Object invokeOptionalWithoutCheckedException(T paramT, Object... paramVarArgs)
  {
    try
    {
      Object localObject = invokeOptional(paramT, paramVarArgs);
      return localObject;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      Throwable localThrowable = localInvocationTargetException.getTargetException();
      if ((localThrowable instanceof RuntimeException)) {
        throw ((RuntimeException)localThrowable);
      }
      AssertionError localAssertionError = new AssertionError("Unexpected exception");
      localAssertionError.initCause(localThrowable);
      throw localAssertionError;
    }
  }
  
  public final Object invokeWithoutCheckedException(T paramT, Object... paramVarArgs)
  {
    try
    {
      Object localObject = invoke(paramT, paramVarArgs);
      return localObject;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      Throwable localThrowable = localInvocationTargetException.getTargetException();
      if ((localThrowable instanceof RuntimeException)) {
        throw ((RuntimeException)localThrowable);
      }
      AssertionError localAssertionError = new AssertionError("Unexpected exception");
      localAssertionError.initCause(localThrowable);
      throw localAssertionError;
    }
  }
  
  public final boolean isSupported(T paramT)
  {
    return getMethod(paramT.getClass()) != null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.OptionalMethod
 * JD-Core Version:    0.7.0.1
 */