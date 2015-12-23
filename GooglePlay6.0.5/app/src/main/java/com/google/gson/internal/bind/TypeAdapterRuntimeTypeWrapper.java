package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

final class TypeAdapterRuntimeTypeWrapper<T>
  extends TypeAdapter<T>
{
  private final Gson context;
  private final TypeAdapter<T> delegate;
  private final Type type;
  
  TypeAdapterRuntimeTypeWrapper(Gson paramGson, TypeAdapter<T> paramTypeAdapter, Type paramType)
  {
    this.context = paramGson;
    this.delegate = paramTypeAdapter;
    this.type = paramType;
  }
  
  public final T read(JsonReader paramJsonReader)
    throws IOException
  {
    return this.delegate.read(paramJsonReader);
  }
  
  public final void write(JsonWriter paramJsonWriter, T paramT)
    throws IOException
  {
    Object localObject1 = this.delegate;
    Object localObject2 = this.type;
    if ((paramT != null) && ((localObject2 == Object.class) || ((localObject2 instanceof TypeVariable)) || ((localObject2 instanceof Class)))) {
      localObject2 = paramT.getClass();
    }
    TypeAdapter localTypeAdapter;
    if (localObject2 != this.type)
    {
      localTypeAdapter = this.context.getAdapter(TypeToken.get((Type)localObject2));
      if ((!(localTypeAdapter instanceof ReflectiveTypeAdapterFactory.Adapter)) || ((this.delegate instanceof ReflectiveTypeAdapterFactory.Adapter))) {
        break label97;
      }
    }
    label97:
    for (localObject1 = this.delegate;; localObject1 = localTypeAdapter)
    {
      ((TypeAdapter)localObject1).write(paramJsonWriter, paramT);
      return;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.gson.internal.bind.TypeAdapterRuntimeTypeWrapper
 * JD-Core Version:    0.7.0.1
 */