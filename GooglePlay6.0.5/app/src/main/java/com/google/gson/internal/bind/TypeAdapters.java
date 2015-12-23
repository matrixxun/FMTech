package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

public final class TypeAdapters
{
  public static final TypeAdapter<BitSet> BIT_SET;
  public static final TypeAdapterFactory BIT_SET_FACTORY;
  public static final TypeAdapter<Boolean> BOOLEAN;
  public static final TypeAdapter<Boolean> BOOLEAN_AS_STRING;
  public static final TypeAdapterFactory BOOLEAN_FACTORY;
  public static final TypeAdapter<Number> BYTE;
  public static final TypeAdapterFactory BYTE_FACTORY;
  public static final TypeAdapter<Calendar> CALENDAR;
  public static final TypeAdapterFactory CALENDAR_FACTORY;
  public static final TypeAdapter<Character> CHARACTER;
  public static final TypeAdapterFactory CHARACTER_FACTORY;
  public static final TypeAdapter<Class> CLASS = new TypeAdapter() {};
  public static final TypeAdapterFactory CLASS_FACTORY = newFactory(Class.class, CLASS);
  public static final TypeAdapter<Number> DOUBLE;
  public static final TypeAdapterFactory ENUM_FACTORY = new TypeAdapterFactory()
  {
    public final <T> TypeAdapter<T> create(Gson paramAnonymousGson, TypeToken<T> paramAnonymousTypeToken)
    {
      Class localClass = paramAnonymousTypeToken.rawType;
      if ((!Enum.class.isAssignableFrom(localClass)) || (localClass == Enum.class)) {
        return null;
      }
      if (!localClass.isEnum()) {
        localClass = localClass.getSuperclass();
      }
      return new TypeAdapters.EnumTypeAdapter(localClass);
    }
  };
  public static final TypeAdapter<Number> FLOAT;
  public static final TypeAdapter<InetAddress> INET_ADDRESS;
  public static final TypeAdapterFactory INET_ADDRESS_FACTORY;
  public static final TypeAdapter<Number> INTEGER;
  public static final TypeAdapterFactory INTEGER_FACTORY;
  public static final TypeAdapter<JsonElement> JSON_ELEMENT;
  public static final TypeAdapterFactory JSON_ELEMENT_FACTORY;
  public static final TypeAdapter<Locale> LOCALE;
  public static final TypeAdapterFactory LOCALE_FACTORY;
  public static final TypeAdapter<Number> LONG;
  public static final TypeAdapter<Number> NUMBER;
  public static final TypeAdapterFactory NUMBER_FACTORY;
  public static final TypeAdapter<Number> SHORT;
  public static final TypeAdapterFactory SHORT_FACTORY;
  public static final TypeAdapter<String> STRING;
  public static final TypeAdapter<StringBuffer> STRING_BUFFER;
  public static final TypeAdapterFactory STRING_BUFFER_FACTORY;
  public static final TypeAdapter<StringBuilder> STRING_BUILDER;
  public static final TypeAdapterFactory STRING_BUILDER_FACTORY;
  public static final TypeAdapterFactory STRING_FACTORY;
  public static final TypeAdapterFactory TIMESTAMP_FACTORY;
  public static final TypeAdapter<URI> URI;
  public static final TypeAdapterFactory URI_FACTORY;
  public static final TypeAdapter<URL> URL;
  public static final TypeAdapterFactory URL_FACTORY;
  public static final TypeAdapter<UUID> UUID;
  public static final TypeAdapterFactory UUID_FACTORY;
  
  static
  {
    BIT_SET = new TypeAdapter()
    {
      private static BitSet read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        BitSet localBitSet = new BitSet();
        paramAnonymousJsonReader.beginArray();
        int i = 0;
        JsonToken localJsonToken = paramAnonymousJsonReader.peek();
        if (localJsonToken != JsonToken.END_ARRAY)
        {
          boolean bool;
          switch (TypeAdapters.30.$SwitchMap$com$google$gson$stream$JsonToken[localJsonToken.ordinal()])
          {
          default: 
            throw new JsonSyntaxException("Invalid bitset value type: " + localJsonToken);
          case 1: 
            if (paramAnonymousJsonReader.nextInt() != 0) {
              bool = true;
            }
            break;
          }
          for (;;)
          {
            if (bool) {
              localBitSet.set(i);
            }
            i++;
            localJsonToken = paramAnonymousJsonReader.peek();
            break;
            bool = false;
            continue;
            bool = paramAnonymousJsonReader.nextBoolean();
            continue;
            String str = paramAnonymousJsonReader.nextString();
            try
            {
              int j = Integer.parseInt(str);
              if (j != 0) {}
              for (bool = true;; bool = false) {
                break;
              }
              paramAnonymousJsonReader.endArray();
            }
            catch (NumberFormatException localNumberFormatException)
            {
              throw new JsonSyntaxException("Error: Expecting: bitset number value (1, 0), Found: " + str);
            }
          }
        }
        return localBitSet;
      }
    };
    BIT_SET_FACTORY = newFactory(BitSet.class, BIT_SET);
    BOOLEAN = new TypeAdapter() {};
    BOOLEAN_AS_STRING = new TypeAdapter() {};
    BOOLEAN_FACTORY = newFactory(Boolean.TYPE, Boolean.class, BOOLEAN);
    BYTE = new TypeAdapter()
    {
      private static Number read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        try
        {
          Byte localByte = Byte.valueOf((byte)paramAnonymousJsonReader.nextInt());
          return localByte;
        }
        catch (NumberFormatException localNumberFormatException)
        {
          throw new JsonSyntaxException(localNumberFormatException);
        }
      }
    };
    BYTE_FACTORY = newFactory(Byte.TYPE, Byte.class, BYTE);
    SHORT = new TypeAdapter()
    {
      private static Number read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        try
        {
          Short localShort = Short.valueOf((short)paramAnonymousJsonReader.nextInt());
          return localShort;
        }
        catch (NumberFormatException localNumberFormatException)
        {
          throw new JsonSyntaxException(localNumberFormatException);
        }
      }
    };
    SHORT_FACTORY = newFactory(Short.TYPE, Short.class, SHORT);
    INTEGER = new TypeAdapter()
    {
      private static Number read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        try
        {
          Integer localInteger = Integer.valueOf(paramAnonymousJsonReader.nextInt());
          return localInteger;
        }
        catch (NumberFormatException localNumberFormatException)
        {
          throw new JsonSyntaxException(localNumberFormatException);
        }
      }
    };
    INTEGER_FACTORY = newFactory(Integer.TYPE, Integer.class, INTEGER);
    LONG = new TypeAdapter()
    {
      private static Number read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL)
        {
          paramAnonymousJsonReader.nextNull();
          return null;
        }
        try
        {
          Long localLong = Long.valueOf(paramAnonymousJsonReader.nextLong());
          return localLong;
        }
        catch (NumberFormatException localNumberFormatException)
        {
          throw new JsonSyntaxException(localNumberFormatException);
        }
      }
    };
    FLOAT = new TypeAdapter() {};
    DOUBLE = new TypeAdapter() {};
    NUMBER = new TypeAdapter() {};
    NUMBER_FACTORY = newFactory(Number.class, NUMBER);
    CHARACTER = new TypeAdapter() {};
    CHARACTER_FACTORY = newFactory(Character.TYPE, Character.class, CHARACTER);
    STRING = new TypeAdapter() {};
    STRING_FACTORY = newFactory(String.class, STRING);
    STRING_BUILDER = new TypeAdapter() {};
    STRING_BUILDER_FACTORY = newFactory(StringBuilder.class, STRING_BUILDER);
    STRING_BUFFER = new TypeAdapter() {};
    STRING_BUFFER_FACTORY = newFactory(StringBuffer.class, STRING_BUFFER);
    URL = new TypeAdapter() {};
    URL_FACTORY = newFactory(URL.class, URL);
    URI = new TypeAdapter()
    {
      private static URI read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        if (paramAnonymousJsonReader.peek() == JsonToken.NULL) {
          paramAnonymousJsonReader.nextNull();
        }
        for (;;)
        {
          return null;
          try
          {
            String str = paramAnonymousJsonReader.nextString();
            if ("null".equals(str)) {
              continue;
            }
            URI localURI = new URI(str);
            return localURI;
          }
          catch (URISyntaxException localURISyntaxException)
          {
            throw new JsonIOException(localURISyntaxException);
          }
        }
      }
    };
    URI_FACTORY = newFactory(URI.class, URI);
    INET_ADDRESS = new TypeAdapter() {};
    INET_ADDRESS_FACTORY = new TypeAdapterFactory()
    {
      public final <T> TypeAdapter<T> create(Gson paramAnonymousGson, TypeToken<T> paramAnonymousTypeToken)
      {
        if (this.val$clazz.isAssignableFrom(paramAnonymousTypeToken.rawType)) {
          return this.val$typeAdapter;
        }
        return null;
      }
      
      public final String toString()
      {
        return "Factory[typeHierarchy=" + this.val$clazz.getName() + ",adapter=" + this.val$typeAdapter + "]";
      }
    };
    UUID = new TypeAdapter() {};
    UUID_FACTORY = newFactory(UUID.class, UUID);
    TIMESTAMP_FACTORY = new TypeAdapterFactory()
    {
      public final <T> TypeAdapter<T> create(Gson paramAnonymousGson, TypeToken<T> paramAnonymousTypeToken)
      {
        if (paramAnonymousTypeToken.rawType != Timestamp.class) {
          return null;
        }
        new TypeAdapter() {};
      }
    };
    CALENDAR = new TypeAdapter() {};
    CALENDAR_FACTORY = new TypeAdapterFactory()
    {
      public final <T> TypeAdapter<T> create(Gson paramAnonymousGson, TypeToken<T> paramAnonymousTypeToken)
      {
        Class localClass = paramAnonymousTypeToken.rawType;
        if ((localClass == this.val$base) || (localClass == this.val$sub)) {
          return this.val$typeAdapter;
        }
        return null;
      }
      
      public final String toString()
      {
        return "Factory[type=" + this.val$base.getName() + "+" + this.val$sub.getName() + ",adapter=" + this.val$typeAdapter + "]";
      }
    };
    LOCALE = new TypeAdapter() {};
    LOCALE_FACTORY = newFactory(Locale.class, LOCALE);
    JSON_ELEMENT = new TypeAdapter()
    {
      private JsonElement read(JsonReader paramAnonymousJsonReader)
        throws IOException
      {
        switch (TypeAdapters.30.$SwitchMap$com$google$gson$stream$JsonToken[paramAnonymousJsonReader.peek().ordinal()])
        {
        default: 
          throw new IllegalArgumentException();
        case 3: 
          return new JsonPrimitive(paramAnonymousJsonReader.nextString());
        case 1: 
          return new JsonPrimitive(new LazilyParsedNumber(paramAnonymousJsonReader.nextString()));
        case 2: 
          return new JsonPrimitive(Boolean.valueOf(paramAnonymousJsonReader.nextBoolean()));
        case 4: 
          paramAnonymousJsonReader.nextNull();
          return JsonNull.INSTANCE;
        case 5: 
          JsonArray localJsonArray = new JsonArray();
          paramAnonymousJsonReader.beginArray();
          while (paramAnonymousJsonReader.hasNext()) {
            localJsonArray.add(read(paramAnonymousJsonReader));
          }
          paramAnonymousJsonReader.endArray();
          return localJsonArray;
        }
        JsonObject localJsonObject = new JsonObject();
        paramAnonymousJsonReader.beginObject();
        while (paramAnonymousJsonReader.hasNext()) {
          localJsonObject.add(paramAnonymousJsonReader.nextName(), read(paramAnonymousJsonReader));
        }
        paramAnonymousJsonReader.endObject();
        return localJsonObject;
      }
      
      private void write(JsonWriter paramAnonymousJsonWriter, JsonElement paramAnonymousJsonElement)
        throws IOException
      {
        if ((paramAnonymousJsonElement == null) || ((paramAnonymousJsonElement instanceof JsonNull)))
        {
          paramAnonymousJsonWriter.nullValue();
          return;
        }
        if ((paramAnonymousJsonElement instanceof JsonPrimitive))
        {
          JsonPrimitive localJsonPrimitive = paramAnonymousJsonElement.getAsJsonPrimitive();
          if ((localJsonPrimitive.value instanceof Number))
          {
            paramAnonymousJsonWriter.value(localJsonPrimitive.getAsNumber());
            return;
          }
          if ((localJsonPrimitive.value instanceof Boolean))
          {
            paramAnonymousJsonWriter.value(localJsonPrimitive.getAsBoolean());
            return;
          }
          paramAnonymousJsonWriter.value(localJsonPrimitive.getAsString());
          return;
        }
        if ((paramAnonymousJsonElement instanceof JsonArray))
        {
          paramAnonymousJsonWriter.beginArray();
          if ((paramAnonymousJsonElement instanceof JsonArray))
          {
            Iterator localIterator2 = ((JsonArray)paramAnonymousJsonElement).iterator();
            while (localIterator2.hasNext()) {
              write(paramAnonymousJsonWriter, (JsonElement)localIterator2.next());
            }
          }
          throw new IllegalStateException("This is not a JSON Array.");
          paramAnonymousJsonWriter.endArray();
          return;
        }
        if ((paramAnonymousJsonElement instanceof JsonObject))
        {
          paramAnonymousJsonWriter.beginObject();
          if ((paramAnonymousJsonElement instanceof JsonObject))
          {
            Iterator localIterator1 = ((JsonObject)paramAnonymousJsonElement).entrySet().iterator();
            while (localIterator1.hasNext())
            {
              Map.Entry localEntry = (Map.Entry)localIterator1.next();
              paramAnonymousJsonWriter.name((String)localEntry.getKey());
              write(paramAnonymousJsonWriter, (JsonElement)localEntry.getValue());
            }
          }
          throw new IllegalStateException("Not a JSON Object: " + paramAnonymousJsonElement);
          paramAnonymousJsonWriter.endObject();
          return;
        }
        throw new IllegalArgumentException("Couldn't write " + paramAnonymousJsonElement.getClass());
      }
    };
    JSON_ELEMENT_FACTORY = newFactory(JsonElement.class, JSON_ELEMENT);
  }
  
  public static <TT> TypeAdapterFactory newFactory(TypeToken<TT> paramTypeToken, final TypeAdapter<TT> paramTypeAdapter)
  {
    new TypeAdapterFactory()
    {
      public final <T> TypeAdapter<T> create(Gson paramAnonymousGson, TypeToken<T> paramAnonymousTypeToken)
      {
        if (paramAnonymousTypeToken.equals(this.val$type)) {
          return paramTypeAdapter;
        }
        return null;
      }
    };
  }
  
  public static <TT> TypeAdapterFactory newFactory(Class<TT> paramClass, final TypeAdapter<TT> paramTypeAdapter)
  {
    new TypeAdapterFactory()
    {
      public final <T> TypeAdapter<T> create(Gson paramAnonymousGson, TypeToken<T> paramAnonymousTypeToken)
      {
        if (paramAnonymousTypeToken.rawType == this.val$type) {
          return paramTypeAdapter;
        }
        return null;
      }
      
      public final String toString()
      {
        return "Factory[type=" + this.val$type.getName() + ",adapter=" + paramTypeAdapter + "]";
      }
    };
  }
  
  public static <TT> TypeAdapterFactory newFactory(Class<TT> paramClass1, final Class<TT> paramClass2, final TypeAdapter<? super TT> paramTypeAdapter)
  {
    new TypeAdapterFactory()
    {
      public final <T> TypeAdapter<T> create(Gson paramAnonymousGson, TypeToken<T> paramAnonymousTypeToken)
      {
        Class localClass = paramAnonymousTypeToken.rawType;
        if ((localClass == this.val$unboxed) || (localClass == paramClass2)) {
          return paramTypeAdapter;
        }
        return null;
      }
      
      public final String toString()
      {
        return "Factory[type=" + paramClass2.getName() + "+" + this.val$unboxed.getName() + ",adapter=" + paramTypeAdapter + "]";
      }
    };
  }
  
  private static final class EnumTypeAdapter<T extends Enum<T>>
    extends TypeAdapter<T>
  {
    private final Map<T, String> constantToName = new HashMap();
    private final Map<String, T> nameToConstant = new HashMap();
    
    public EnumTypeAdapter(Class<T> paramClass)
    {
      try
      {
        for (Enum localEnum : (Enum[])paramClass.getEnumConstants())
        {
          String str = localEnum.name();
          SerializedName localSerializedName = (SerializedName)paramClass.getField(str).getAnnotation(SerializedName.class);
          if (localSerializedName != null) {
            str = localSerializedName.value();
          }
          this.nameToConstant.put(str, localEnum);
          this.constantToName.put(localEnum, str);
        }
        return;
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        throw new AssertionError();
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.gson.internal.bind.TypeAdapters
 * JD-Core Version:    0.7.0.1
 */