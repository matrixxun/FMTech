package com.google.gson;

import com.google.gson.internal..Gson.Preconditions;
import com.google.gson.internal.GsonInternalAccess;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;

public final class TreeTypeAdapter<T>
  extends TypeAdapter<T>
{
  private TypeAdapter<T> delegate;
  private final JsonDeserializer<T> deserializer;
  private final Gson gson;
  private final JsonSerializer<T> serializer;
  private final TypeAdapterFactory skipPast;
  private final TypeToken<T> typeToken;
  
  private TreeTypeAdapter(JsonSerializer<T> paramJsonSerializer, JsonDeserializer<T> paramJsonDeserializer, Gson paramGson, TypeToken<T> paramTypeToken, TypeAdapterFactory paramTypeAdapterFactory)
  {
    this.serializer = paramJsonSerializer;
    this.deserializer = paramJsonDeserializer;
    this.gson = paramGson;
    this.typeToken = paramTypeToken;
    this.skipPast = paramTypeAdapterFactory;
  }
  
  private TypeAdapter<T> delegate()
  {
    TypeAdapter localTypeAdapter1 = this.delegate;
    if (localTypeAdapter1 != null) {
      return localTypeAdapter1;
    }
    TypeAdapter localTypeAdapter2 = GsonInternalAccess.INSTANCE.getNextAdapter(this.gson, this.skipPast, this.typeToken);
    this.delegate = localTypeAdapter2;
    return localTypeAdapter2;
  }
  
  public static TypeAdapterFactory newFactory(TypeToken<?> paramTypeToken, Object paramObject)
  {
    return new SingleTypeFactory(paramObject, paramTypeToken, false);
  }
  
  public final T read(JsonReader paramJsonReader)
    throws IOException
  {
    if (this.deserializer == null) {
      return delegate().read(paramJsonReader);
    }
    JsonElement localJsonElement = Streams.parse(paramJsonReader);
    if ((localJsonElement instanceof JsonNull)) {
      return null;
    }
    try
    {
      JsonDeserializer localJsonDeserializer = this.deserializer;
      Type localType = this.typeToken.type;
      Object localObject = localJsonDeserializer.deserialize$140ae884(localJsonElement, localType);
      return localObject;
    }
    catch (JsonParseException localJsonParseException)
    {
      throw localJsonParseException;
    }
    catch (Exception localException)
    {
      throw new JsonParseException(localException);
    }
  }
  
  public final void write(JsonWriter paramJsonWriter, T paramT)
    throws IOException
  {
    if (this.serializer == null)
    {
      delegate().write(paramJsonWriter, paramT);
      return;
    }
    if (paramT == null)
    {
      paramJsonWriter.nullValue();
      return;
    }
    JsonSerializer localJsonSerializer = this.serializer;
    Streams.write(localJsonSerializer.serialize$117eb95b(paramT), paramJsonWriter);
  }
  
  private static final class SingleTypeFactory
    implements TypeAdapterFactory
  {
    private final JsonDeserializer<?> deserializer;
    private final TypeToken<?> exactType;
    private final Class<?> hierarchyType;
    private final boolean matchRawType;
    private final JsonSerializer<?> serializer;
    
    private SingleTypeFactory(Object paramObject, TypeToken<?> paramTypeToken, boolean paramBoolean, Class<?> paramClass)
    {
      JsonSerializer localJsonSerializer;
      JsonDeserializer localJsonDeserializer;
      if ((paramObject instanceof JsonSerializer))
      {
        localJsonSerializer = (JsonSerializer)paramObject;
        this.serializer = localJsonSerializer;
        if (!(paramObject instanceof JsonDeserializer)) {
          break label86;
        }
        localJsonDeserializer = (JsonDeserializer)paramObject;
        label36:
        this.deserializer = localJsonDeserializer;
        if ((this.serializer == null) && (this.deserializer == null)) {
          break label92;
        }
      }
      label86:
      label92:
      for (boolean bool = true;; bool = false)
      {
        .Gson.Preconditions.checkArgument(bool);
        this.exactType = paramTypeToken;
        this.matchRawType = paramBoolean;
        this.hierarchyType = null;
        return;
        localJsonSerializer = null;
        break;
        localJsonDeserializer = null;
        break label36;
      }
    }
    
    public final <T> TypeAdapter<T> create(Gson paramGson, TypeToken<T> paramTypeToken)
    {
      boolean bool;
      if (this.exactType != null) {
        if ((this.exactType.equals(paramTypeToken)) || ((this.matchRawType) && (this.exactType.type == paramTypeToken.rawType))) {
          bool = true;
        }
      }
      while (bool)
      {
        return new TreeTypeAdapter(this.serializer, this.deserializer, paramGson, paramTypeToken, this, (byte)0);
        bool = false;
        continue;
        bool = this.hierarchyType.isAssignableFrom(paramTypeToken.rawType);
      }
      return null;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.gson.TreeTypeAdapter
 * JD-Core Version:    0.7.0.1
 */