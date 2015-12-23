package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal..Gson.Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public final class MapTypeAdapterFactory
  implements TypeAdapterFactory
{
  final boolean complexMapKeySerialization;
  private final ConstructorConstructor constructorConstructor;
  
  public MapTypeAdapterFactory(ConstructorConstructor paramConstructorConstructor, boolean paramBoolean)
  {
    this.constructorConstructor = paramConstructorConstructor;
    this.complexMapKeySerialization = paramBoolean;
  }
  
  static <T> JsonElement toJsonTree(TypeAdapter<T> paramTypeAdapter, T paramT)
  {
    JsonTreeWriter localJsonTreeWriter;
    try
    {
      localJsonTreeWriter = new JsonTreeWriter();
      localJsonTreeWriter.lenient = true;
      paramTypeAdapter.write(localJsonTreeWriter, paramT);
      if (!localJsonTreeWriter.stack.isEmpty()) {
        throw new IllegalStateException("Expected one JSON element but was " + localJsonTreeWriter.stack);
      }
    }
    catch (IOException localIOException)
    {
      throw new JsonIOException(localIOException);
    }
    JsonElement localJsonElement = localJsonTreeWriter.product;
    return localJsonElement;
  }
  
  public final <T> TypeAdapter<T> create(Gson paramGson, TypeToken<T> paramTypeToken)
  {
    Type localType1 = paramTypeToken.type;
    if (!Map.class.isAssignableFrom(paramTypeToken.rawType)) {
      return null;
    }
    Type[] arrayOfType = .Gson.Types.getMapKeyAndValueTypes(localType1, .Gson.Types.getRawType(localType1));
    Type localType2 = arrayOfType[0];
    if ((localType2 == Boolean.TYPE) || (localType2 == Boolean.class)) {}
    for (TypeAdapter localTypeAdapter1 = TypeAdapters.BOOLEAN_AS_STRING;; localTypeAdapter1 = paramGson.getAdapter(TypeToken.get(localType2)))
    {
      TypeAdapter localTypeAdapter2 = paramGson.getAdapter(TypeToken.get(arrayOfType[1]));
      ObjectConstructor localObjectConstructor = this.constructorConstructor.getConstructor(paramTypeToken);
      return new Adapter(paramGson, arrayOfType[0], localTypeAdapter1, arrayOfType[1], localTypeAdapter2, localObjectConstructor);
    }
  }
  
  private final class Adapter<K, V>
    extends TypeAdapter<Map<K, V>>
  {
    private final ObjectConstructor<? extends Map<K, V>> constructor;
    private final TypeAdapter<K> keyTypeAdapter;
    private final TypeAdapter<V> valueTypeAdapter;
    
    public Adapter(Type paramType1, TypeAdapter<K> paramTypeAdapter, Type paramType2, TypeAdapter<V> paramTypeAdapter1, ObjectConstructor<? extends Map<K, V>> paramObjectConstructor)
    {
      this.keyTypeAdapter = new TypeAdapterRuntimeTypeWrapper(paramType1, paramType2, paramTypeAdapter);
      this.valueTypeAdapter = new TypeAdapterRuntimeTypeWrapper(paramType1, paramObjectConstructor, paramTypeAdapter1);
      Object localObject;
      this.constructor = localObject;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.gson.internal.bind.MapTypeAdapterFactory
 * JD-Core Version:    0.7.0.1
 */