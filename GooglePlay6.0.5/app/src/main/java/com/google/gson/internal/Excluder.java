package com.google.gson.internal;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.Since;
import com.google.gson.annotations.Until;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Excluder
  implements TypeAdapterFactory, Cloneable
{
  public static final Excluder DEFAULT = new Excluder();
  public List<ExclusionStrategy> deserializationStrategies = Collections.emptyList();
  public int modifiers = 136;
  public boolean requireExpose;
  public List<ExclusionStrategy> serializationStrategies = Collections.emptyList();
  public boolean serializeInnerClasses = true;
  public double version = -1.0D;
  
  public static boolean isAnonymousOrLocal(Class<?> paramClass)
  {
    return (!Enum.class.isAssignableFrom(paramClass)) && ((paramClass.isAnonymousClass()) || (paramClass.isLocalClass()));
  }
  
  public static boolean isInnerClass(Class<?> paramClass)
  {
    if (paramClass.isMemberClass())
    {
      if ((0x8 & paramClass.getModifiers()) != 0) {}
      for (int i = 1; i == 0; i = 0) {
        return true;
      }
    }
    return false;
  }
  
  public Excluder clone()
  {
    try
    {
      Excluder localExcluder = (Excluder)super.clone();
      return localExcluder;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      throw new AssertionError();
    }
  }
  
  public final <T> TypeAdapter<T> create(final Gson paramGson, final TypeToken<T> paramTypeToken)
  {
    Class localClass = paramTypeToken.rawType;
    final boolean bool1 = excludeClass(localClass, true);
    final boolean bool2 = excludeClass(localClass, false);
    if ((!bool1) && (!bool2)) {
      return null;
    }
    new TypeAdapter()
    {
      private TypeAdapter<T> delegate;
      
      private TypeAdapter<T> delegate()
      {
        TypeAdapter localTypeAdapter1 = this.delegate;
        if (localTypeAdapter1 != null) {
          return localTypeAdapter1;
        }
        TypeAdapter localTypeAdapter2 = GsonInternalAccess.INSTANCE.getNextAdapter(paramGson, Excluder.this, paramTypeToken);
        this.delegate = localTypeAdapter2;
        return localTypeAdapter2;
      }
      
      public final T read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (bool2)
        {
          paramAnonymousJsonReader.skipValue();
          return null;
        }
        return delegate().read(paramAnonymousJsonReader);
      }
      
      public final void write(JsonWriter paramAnonymousJsonWriter, T paramAnonymousT)
        throws IOException
      {
        if (bool1)
        {
          paramAnonymousJsonWriter.nullValue();
          return;
        }
        delegate().write(paramAnonymousJsonWriter, paramAnonymousT);
      }
    };
  }
  
  public final boolean excludeClass(Class<?> paramClass, boolean paramBoolean)
  {
    if ((this.version != -1.0D) && (!isValidVersion((Since)paramClass.getAnnotation(Since.class), (Until)paramClass.getAnnotation(Until.class)))) {
      return true;
    }
    if ((!this.serializeInnerClasses) && (isInnerClass(paramClass))) {
      return true;
    }
    if (isAnonymousOrLocal(paramClass)) {
      return true;
    }
    if (paramBoolean) {}
    for (List localList = this.serializationStrategies;; localList = this.deserializationStrategies)
    {
      Iterator localIterator = localList.iterator();
      do
      {
        if (!localIterator.hasNext()) {
          break;
        }
      } while (!((ExclusionStrategy)localIterator.next()).shouldSkipClass$1b2d8b94());
      return true;
    }
    return false;
  }
  
  public final boolean isValidVersion(Since paramSince, Until paramUntil)
  {
    int i;
    if ((paramSince != null) && (paramSince.value() > this.version))
    {
      i = 0;
      if (i == 0) {
        break label63;
      }
      if ((paramUntil == null) || (paramUntil.value() > this.version)) {
        break label57;
      }
    }
    label57:
    for (int j = 0;; j = 1)
    {
      if (j == 0) {
        break label63;
      }
      return true;
      i = 1;
      break;
    }
    label63:
    return false;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.gson.internal.Excluder
 * JD-Core Version:    0.7.0.1
 */