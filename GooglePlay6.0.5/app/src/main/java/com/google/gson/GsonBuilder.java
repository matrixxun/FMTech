package com.google.gson;

import com.google.gson.internal..Gson.Preconditions;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.Primitives;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GsonBuilder
{
  public boolean complexMapKeySerialization;
  public String datePattern;
  public int dateStyle = 2;
  public boolean escapeHtmlChars = true;
  public Excluder excluder = Excluder.DEFAULT;
  public final List<TypeAdapterFactory> factories = new ArrayList();
  public FieldNamingStrategy fieldNamingPolicy = FieldNamingPolicy.IDENTITY;
  public boolean generateNonExecutableJson;
  public final List<TypeAdapterFactory> hierarchyFactories = new ArrayList();
  public final Map<Type, InstanceCreator<?>> instanceCreators = new HashMap();
  public LongSerializationPolicy longSerializationPolicy = LongSerializationPolicy.DEFAULT;
  public boolean prettyPrinting;
  public boolean serializeNulls;
  public boolean serializeSpecialFloatingPointValues;
  public int timeStyle = 2;
  
  public final GsonBuilder registerTypeAdapter(Type paramType, Object paramObject)
  {
    boolean bool1 = true;
    if (((paramObject instanceof JsonSerializer)) || ((paramObject instanceof JsonDeserializer)) || ((paramObject instanceof InstanceCreator)) || ((paramObject instanceof TypeAdapter))) {}
    for (boolean bool2 = bool1;; bool2 = false)
    {
      .Gson.Preconditions.checkArgument(bool2);
      if ((!Primitives.isPrimitive(paramType)) && (!Primitives.isWrapperType(paramType))) {
        break;
      }
      throw new IllegalArgumentException("Cannot register type adapters for " + paramType);
    }
    if ((paramObject instanceof InstanceCreator)) {
      this.instanceCreators.put(paramType, (InstanceCreator)paramObject);
    }
    TypeToken localTypeToken;
    List localList;
    if (((paramObject instanceof JsonSerializer)) || ((paramObject instanceof JsonDeserializer)))
    {
      localTypeToken = TypeToken.get(paramType);
      localList = this.factories;
      if (localTypeToken.type != localTypeToken.rawType) {
        break label192;
      }
    }
    for (;;)
    {
      localList.add(new TreeTypeAdapter.SingleTypeFactory(paramObject, localTypeToken, bool1));
      if ((paramObject instanceof TypeAdapter)) {
        this.factories.add(TypeAdapters.newFactory(TypeToken.get(paramType), (TypeAdapter)paramObject));
      }
      return this;
      label192:
      bool1 = false;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.gson.GsonBuilder
 * JD-Core Version:    0.7.0.1
 */