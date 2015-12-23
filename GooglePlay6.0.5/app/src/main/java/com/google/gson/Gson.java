package com.google.gson;

import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.GsonInternalAccess;
import com.google.gson.internal.Primitives;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.google.gson.internal.bind.BigDecimalTypeAdapter;
import com.google.gson.internal.bind.BigIntegerTypeAdapter;
import com.google.gson.internal.bind.CollectionTypeAdapterFactory;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.internal.bind.MapTypeAdapterFactory;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.internal.bind.SqlDateTypeAdapter;
import com.google.gson.internal.bind.TimeTypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class Gson
{
  private final ThreadLocal<Map<TypeToken<?>, FutureTypeAdapter<?>>> calls = new ThreadLocal() {};
  private final ConstructorConstructor constructorConstructor;
  final JsonDeserializationContext deserializationContext = new JsonDeserializationContext() {};
  private final List<TypeAdapterFactory> factories;
  private final boolean generateNonExecutableJson;
  private final boolean htmlSafe;
  private final boolean prettyPrinting;
  final JsonSerializationContext serializationContext = new JsonSerializationContext() {};
  private final boolean serializeNulls;
  private final Map<TypeToken<?>, TypeAdapter<?>> typeTokenCache = Collections.synchronizedMap(new HashMap());
  
  static
  {
    GsonInternalAccess.INSTANCE = new GsonInternalAccess()
    {
      public final <T> TypeAdapter<T> getNextAdapter(Gson paramAnonymousGson, TypeAdapterFactory paramAnonymousTypeAdapterFactory, TypeToken<T> paramAnonymousTypeToken)
      {
        int i = 0;
        Iterator localIterator = paramAnonymousGson.factories.iterator();
        while (localIterator.hasNext())
        {
          TypeAdapterFactory localTypeAdapterFactory = (TypeAdapterFactory)localIterator.next();
          if (i == 0)
          {
            if (localTypeAdapterFactory == paramAnonymousTypeAdapterFactory) {
              i = 1;
            }
          }
          else
          {
            TypeAdapter localTypeAdapter = localTypeAdapterFactory.create(paramAnonymousGson, paramAnonymousTypeToken);
            if (localTypeAdapter != null) {
              return localTypeAdapter;
            }
          }
        }
        throw new IllegalArgumentException("GSON cannot serialize " + paramAnonymousTypeToken);
      }
    };
  }
  
  public Gson()
  {
    this(Excluder.DEFAULT, FieldNamingPolicy.IDENTITY, Collections.emptyMap(), false, false, false, true, false, false, LongSerializationPolicy.DEFAULT, Collections.emptyList());
  }
  
  public Gson(Excluder paramExcluder, FieldNamingStrategy paramFieldNamingStrategy, Map<Type, InstanceCreator<?>> paramMap, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6, LongSerializationPolicy paramLongSerializationPolicy, List<TypeAdapterFactory> paramList)
  {
    this.constructorConstructor = new ConstructorConstructor(paramMap);
    this.serializeNulls = paramBoolean1;
    this.generateNonExecutableJson = paramBoolean3;
    this.htmlSafe = paramBoolean4;
    this.prettyPrinting = paramBoolean5;
    ReflectiveTypeAdapterFactory localReflectiveTypeAdapterFactory = new ReflectiveTypeAdapterFactory(this.constructorConstructor, paramFieldNamingStrategy, paramExcluder);
    ConstructorConstructor localConstructorConstructor = new ConstructorConstructor();
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(TypeAdapters.STRING_FACTORY);
    localArrayList.add(TypeAdapters.INTEGER_FACTORY);
    localArrayList.add(TypeAdapters.BOOLEAN_FACTORY);
    localArrayList.add(TypeAdapters.BYTE_FACTORY);
    localArrayList.add(TypeAdapters.SHORT_FACTORY);
    Class localClass1 = Long.TYPE;
    Object localObject1;
    Object localObject2;
    label228:
    Class localClass3;
    if (paramLongSerializationPolicy == LongSerializationPolicy.DEFAULT)
    {
      localObject1 = TypeAdapters.LONG;
      localArrayList.add(TypeAdapters.newFactory(localClass1, Long.class, (TypeAdapter)localObject1));
      Class localClass2 = Double.TYPE;
      if (!paramBoolean6) {
        break label625;
      }
      localObject2 = TypeAdapters.DOUBLE;
      localArrayList.add(TypeAdapters.newFactory(localClass2, Double.class, (TypeAdapter)localObject2));
      localClass3 = Float.TYPE;
      if (!paramBoolean6) {
        break label638;
      }
    }
    label625:
    label638:
    for (Object localObject3 = TypeAdapters.FLOAT;; localObject3 = new TypeAdapter() {})
    {
      localArrayList.add(TypeAdapters.newFactory(localClass3, Float.class, (TypeAdapter)localObject3));
      localArrayList.add(paramExcluder);
      localArrayList.add(TypeAdapters.NUMBER_FACTORY);
      localArrayList.add(TypeAdapters.CHARACTER_FACTORY);
      localArrayList.add(TypeAdapters.STRING_BUILDER_FACTORY);
      localArrayList.add(TypeAdapters.STRING_BUFFER_FACTORY);
      localArrayList.add(TypeAdapters.newFactory(BigDecimal.class, new BigDecimalTypeAdapter()));
      localArrayList.add(TypeAdapters.newFactory(BigInteger.class, new BigIntegerTypeAdapter()));
      localArrayList.add(TypeAdapters.JSON_ELEMENT_FACTORY);
      localArrayList.add(ObjectTypeAdapter.FACTORY);
      localArrayList.addAll(paramList);
      localArrayList.add(new CollectionTypeAdapterFactory(localConstructorConstructor));
      localArrayList.add(TypeAdapters.URL_FACTORY);
      localArrayList.add(TypeAdapters.URI_FACTORY);
      localArrayList.add(TypeAdapters.UUID_FACTORY);
      localArrayList.add(TypeAdapters.LOCALE_FACTORY);
      localArrayList.add(TypeAdapters.INET_ADDRESS_FACTORY);
      localArrayList.add(TypeAdapters.BIT_SET_FACTORY);
      localArrayList.add(DateTypeAdapter.FACTORY);
      localArrayList.add(TypeAdapters.CALENDAR_FACTORY);
      localArrayList.add(TimeTypeAdapter.FACTORY);
      localArrayList.add(SqlDateTypeAdapter.FACTORY);
      localArrayList.add(TypeAdapters.TIMESTAMP_FACTORY);
      localArrayList.add(new MapTypeAdapterFactory(localConstructorConstructor, paramBoolean2));
      localArrayList.add(ArrayTypeAdapter.FACTORY);
      localArrayList.add(TypeAdapters.ENUM_FACTORY);
      localArrayList.add(TypeAdapters.CLASS_FACTORY);
      localArrayList.add(localReflectiveTypeAdapterFactory);
      this.factories = Collections.unmodifiableList(localArrayList);
      return;
      localObject1 = new TypeAdapter() {};
      break;
      localObject2 = new TypeAdapter() {};
      break label228;
    }
  }
  
  /* Error */
  private <T> T fromJson(JsonReader paramJsonReader, Type paramType)
    throws JsonIOException, JsonSyntaxException
  {
    // Byte code:
    //   0: iconst_1
    //   1: istore_3
    //   2: aload_1
    //   3: getfield 321	com/google/gson/stream/JsonReader:lenient	Z
    //   6: istore 4
    //   8: aload_1
    //   9: iconst_1
    //   10: putfield 321	com/google/gson/stream/JsonReader:lenient	Z
    //   13: aload_1
    //   14: invokevirtual 325	com/google/gson/stream/JsonReader:peek	()Lcom/google/gson/stream/JsonToken;
    //   17: pop
    //   18: iconst_0
    //   19: istore_3
    //   20: aload_0
    //   21: aload_2
    //   22: invokestatic 331	com/google/gson/reflect/TypeToken:get	(Ljava/lang/reflect/Type;)Lcom/google/gson/reflect/TypeToken;
    //   25: invokevirtual 335	com/google/gson/Gson:getAdapter	(Lcom/google/gson/reflect/TypeToken;)Lcom/google/gson/TypeAdapter;
    //   28: aload_1
    //   29: invokevirtual 341	com/google/gson/TypeAdapter:read	(Lcom/google/gson/stream/JsonReader;)Ljava/lang/Object;
    //   32: astore 10
    //   34: aload_1
    //   35: iload 4
    //   37: putfield 321	com/google/gson/stream/JsonReader:lenient	Z
    //   40: aload 10
    //   42: areturn
    //   43: astore 8
    //   45: iload_3
    //   46: ifeq +11 -> 57
    //   49: aload_1
    //   50: iload 4
    //   52: putfield 321	com/google/gson/stream/JsonReader:lenient	Z
    //   55: aconst_null
    //   56: areturn
    //   57: new 310	com/google/gson/JsonSyntaxException
    //   60: dup
    //   61: aload 8
    //   63: invokespecial 344	com/google/gson/JsonSyntaxException:<init>	(Ljava/lang/Throwable;)V
    //   66: athrow
    //   67: astore 6
    //   69: aload_1
    //   70: iload 4
    //   72: putfield 321	com/google/gson/stream/JsonReader:lenient	Z
    //   75: aload 6
    //   77: athrow
    //   78: astore 7
    //   80: new 310	com/google/gson/JsonSyntaxException
    //   83: dup
    //   84: aload 7
    //   86: invokespecial 344	com/google/gson/JsonSyntaxException:<init>	(Ljava/lang/Throwable;)V
    //   89: athrow
    //   90: astore 5
    //   92: new 310	com/google/gson/JsonSyntaxException
    //   95: dup
    //   96: aload 5
    //   98: invokespecial 344	com/google/gson/JsonSyntaxException:<init>	(Ljava/lang/Throwable;)V
    //   101: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	102	0	this	Gson
    //   0	102	1	paramJsonReader	JsonReader
    //   0	102	2	paramType	Type
    //   1	45	3	i	int
    //   6	65	4	bool	boolean
    //   90	7	5	localIOException	IOException
    //   67	9	6	localObject1	Object
    //   78	7	7	localIllegalStateException	IllegalStateException
    //   43	19	8	localEOFException	java.io.EOFException
    //   32	9	10	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   13	18	43	java/io/EOFException
    //   20	34	43	java/io/EOFException
    //   13	18	67	finally
    //   20	34	67	finally
    //   57	67	67	finally
    //   80	90	67	finally
    //   92	102	67	finally
    //   13	18	78	java/lang/IllegalStateException
    //   20	34	78	java/lang/IllegalStateException
    //   13	18	90	java/io/IOException
    //   20	34	90	java/io/IOException
  }
  
  private JsonWriter newJsonWriter(Writer paramWriter)
    throws IOException
  {
    if (this.generateNonExecutableJson) {
      paramWriter.write(")]}'\n");
    }
    JsonWriter localJsonWriter = new JsonWriter(paramWriter);
    if (this.prettyPrinting)
    {
      if ("  ".length() != 0) {
        break label61;
      }
      localJsonWriter.indent = null;
    }
    for (localJsonWriter.separator = ":";; localJsonWriter.separator = ": ")
    {
      localJsonWriter.serializeNulls = this.serializeNulls;
      return localJsonWriter;
      label61:
      localJsonWriter.indent = "  ";
    }
  }
  
  public final <T> T fromJson(String paramString, Class<T> paramClass)
    throws JsonSyntaxException
  {
    Object localObject;
    if (paramString == null) {
      localObject = null;
    }
    for (;;)
    {
      return Primitives.wrap(paramClass).cast(localObject);
      JsonReader localJsonReader = new JsonReader(new StringReader(paramString));
      localObject = fromJson(localJsonReader, paramClass);
      if (localObject == null) {
        continue;
      }
      try
      {
        if (localJsonReader.peek() == JsonToken.END_DOCUMENT) {
          continue;
        }
        throw new JsonIOException("JSON document was not fully consumed.");
      }
      catch (MalformedJsonException localMalformedJsonException)
      {
        throw new JsonSyntaxException(localMalformedJsonException);
      }
      catch (IOException localIOException)
      {
        throw new JsonIOException(localIOException);
      }
    }
  }
  
  public final <T> TypeAdapter<T> getAdapter(TypeToken<T> paramTypeToken)
  {
    TypeAdapter localTypeAdapter1 = (TypeAdapter)this.typeTokenCache.get(paramTypeToken);
    if (localTypeAdapter1 != null) {
      return localTypeAdapter1;
    }
    Map localMap = (Map)this.calls.get();
    FutureTypeAdapter localFutureTypeAdapter1 = (FutureTypeAdapter)localMap.get(paramTypeToken);
    if (localFutureTypeAdapter1 != null) {
      return localFutureTypeAdapter1;
    }
    FutureTypeAdapter localFutureTypeAdapter2 = new FutureTypeAdapter();
    localMap.put(paramTypeToken, localFutureTypeAdapter2);
    TypeAdapter localTypeAdapter2;
    try
    {
      Iterator localIterator = this.factories.iterator();
      do
      {
        if (!localIterator.hasNext()) {
          break;
        }
        localTypeAdapter2 = ((TypeAdapterFactory)localIterator.next()).create(this, paramTypeToken);
      } while (localTypeAdapter2 == null);
      if (localFutureTypeAdapter2.delegate != null) {
        throw new AssertionError();
      }
    }
    finally
    {
      localMap.remove(paramTypeToken);
    }
    localFutureTypeAdapter2.delegate = localTypeAdapter2;
    this.typeTokenCache.put(paramTypeToken, localTypeAdapter2);
    localMap.remove(paramTypeToken);
    return localTypeAdapter2;
    throw new IllegalArgumentException("GSON cannot handle " + paramTypeToken);
  }
  
  public final <T> TypeAdapter<T> getAdapter(Class<T> paramClass)
  {
    return getAdapter(TypeToken.get(paramClass));
  }
  
  /* Error */
  public final String toJson(Object paramObject)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +157 -> 158
    //   4: getstatic 472	com/google/gson/JsonNull:INSTANCE	Lcom/google/gson/JsonNull;
    //   7: astore 12
    //   9: new 474	java/io/StringWriter
    //   12: dup
    //   13: invokespecial 475	java/io/StringWriter:<init>	()V
    //   16: astore 13
    //   18: aload_0
    //   19: aload 13
    //   21: invokestatic 481	com/google/gson/internal/Streams:writerForAppendable	(Ljava/lang/Appendable;)Ljava/io/Writer;
    //   24: invokespecial 483	com/google/gson/Gson:newJsonWriter	(Ljava/io/Writer;)Lcom/google/gson/stream/JsonWriter;
    //   27: astore 15
    //   29: aload 15
    //   31: getfield 484	com/google/gson/stream/JsonWriter:lenient	Z
    //   34: istore 16
    //   36: aload 15
    //   38: iconst_1
    //   39: putfield 484	com/google/gson/stream/JsonWriter:lenient	Z
    //   42: aload 15
    //   44: getfield 485	com/google/gson/stream/JsonWriter:htmlSafe	Z
    //   47: istore 17
    //   49: aload 15
    //   51: aload_0
    //   52: getfield 107	com/google/gson/Gson:htmlSafe	Z
    //   55: putfield 485	com/google/gson/stream/JsonWriter:htmlSafe	Z
    //   58: aload 15
    //   60: getfield 376	com/google/gson/stream/JsonWriter:serializeNulls	Z
    //   63: istore 18
    //   65: aload 15
    //   67: aload_0
    //   68: getfield 103	com/google/gson/Gson:serializeNulls	Z
    //   71: putfield 376	com/google/gson/stream/JsonWriter:serializeNulls	Z
    //   74: aload 12
    //   76: aload 15
    //   78: invokestatic 488	com/google/gson/internal/Streams:write	(Lcom/google/gson/JsonElement;Lcom/google/gson/stream/JsonWriter;)V
    //   81: aload 15
    //   83: iload 16
    //   85: putfield 484	com/google/gson/stream/JsonWriter:lenient	Z
    //   88: aload 15
    //   90: iload 17
    //   92: putfield 485	com/google/gson/stream/JsonWriter:htmlSafe	Z
    //   95: aload 15
    //   97: iload 18
    //   99: putfield 376	com/google/gson/stream/JsonWriter:serializeNulls	Z
    //   102: aload 13
    //   104: invokevirtual 489	java/io/StringWriter:toString	()Ljava/lang/String;
    //   107: areturn
    //   108: astore 20
    //   110: new 308	com/google/gson/JsonIOException
    //   113: dup
    //   114: aload 20
    //   116: invokespecial 411	com/google/gson/JsonIOException:<init>	(Ljava/lang/Throwable;)V
    //   119: athrow
    //   120: astore 19
    //   122: aload 15
    //   124: iload 16
    //   126: putfield 484	com/google/gson/stream/JsonWriter:lenient	Z
    //   129: aload 15
    //   131: iload 17
    //   133: putfield 485	com/google/gson/stream/JsonWriter:htmlSafe	Z
    //   136: aload 15
    //   138: iload 18
    //   140: putfield 376	com/google/gson/stream/JsonWriter:serializeNulls	Z
    //   143: aload 19
    //   145: athrow
    //   146: astore 14
    //   148: new 491	java/lang/RuntimeException
    //   151: dup
    //   152: aload 14
    //   154: invokespecial 492	java/lang/RuntimeException:<init>	(Ljava/lang/Throwable;)V
    //   157: athrow
    //   158: aload_1
    //   159: invokevirtual 496	java/lang/Object:getClass	()Ljava/lang/Class;
    //   162: astore_2
    //   163: new 474	java/io/StringWriter
    //   166: dup
    //   167: invokespecial 475	java/io/StringWriter:<init>	()V
    //   170: astore_3
    //   171: aload_0
    //   172: aload_3
    //   173: invokestatic 481	com/google/gson/internal/Streams:writerForAppendable	(Ljava/lang/Appendable;)Ljava/io/Writer;
    //   176: invokespecial 483	com/google/gson/Gson:newJsonWriter	(Ljava/io/Writer;)Lcom/google/gson/stream/JsonWriter;
    //   179: astore 5
    //   181: aload_0
    //   182: aload_2
    //   183: invokestatic 331	com/google/gson/reflect/TypeToken:get	(Ljava/lang/reflect/Type;)Lcom/google/gson/reflect/TypeToken;
    //   186: invokevirtual 335	com/google/gson/Gson:getAdapter	(Lcom/google/gson/reflect/TypeToken;)Lcom/google/gson/TypeAdapter;
    //   189: astore 6
    //   191: aload 5
    //   193: getfield 484	com/google/gson/stream/JsonWriter:lenient	Z
    //   196: istore 7
    //   198: aload 5
    //   200: iconst_1
    //   201: putfield 484	com/google/gson/stream/JsonWriter:lenient	Z
    //   204: aload 5
    //   206: getfield 485	com/google/gson/stream/JsonWriter:htmlSafe	Z
    //   209: istore 8
    //   211: aload 5
    //   213: aload_0
    //   214: getfield 107	com/google/gson/Gson:htmlSafe	Z
    //   217: putfield 485	com/google/gson/stream/JsonWriter:htmlSafe	Z
    //   220: aload 5
    //   222: getfield 376	com/google/gson/stream/JsonWriter:serializeNulls	Z
    //   225: istore 9
    //   227: aload 5
    //   229: aload_0
    //   230: getfield 103	com/google/gson/Gson:serializeNulls	Z
    //   233: putfield 376	com/google/gson/stream/JsonWriter:serializeNulls	Z
    //   236: aload 6
    //   238: aload 5
    //   240: aload_1
    //   241: invokevirtual 499	com/google/gson/TypeAdapter:write	(Lcom/google/gson/stream/JsonWriter;Ljava/lang/Object;)V
    //   244: aload 5
    //   246: iload 7
    //   248: putfield 484	com/google/gson/stream/JsonWriter:lenient	Z
    //   251: aload 5
    //   253: iload 8
    //   255: putfield 485	com/google/gson/stream/JsonWriter:htmlSafe	Z
    //   258: aload 5
    //   260: iload 9
    //   262: putfield 376	com/google/gson/stream/JsonWriter:serializeNulls	Z
    //   265: aload_3
    //   266: invokevirtual 489	java/io/StringWriter:toString	()Ljava/lang/String;
    //   269: areturn
    //   270: astore 11
    //   272: new 308	com/google/gson/JsonIOException
    //   275: dup
    //   276: aload 11
    //   278: invokespecial 411	com/google/gson/JsonIOException:<init>	(Ljava/lang/Throwable;)V
    //   281: athrow
    //   282: astore 10
    //   284: aload 5
    //   286: iload 7
    //   288: putfield 484	com/google/gson/stream/JsonWriter:lenient	Z
    //   291: aload 5
    //   293: iload 8
    //   295: putfield 485	com/google/gson/stream/JsonWriter:htmlSafe	Z
    //   298: aload 5
    //   300: iload 9
    //   302: putfield 376	com/google/gson/stream/JsonWriter:serializeNulls	Z
    //   305: aload 10
    //   307: athrow
    //   308: astore 4
    //   310: new 308	com/google/gson/JsonIOException
    //   313: dup
    //   314: aload 4
    //   316: invokespecial 411	com/google/gson/JsonIOException:<init>	(Ljava/lang/Throwable;)V
    //   319: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	320	0	this	Gson
    //   0	320	1	paramObject	Object
    //   162	21	2	localClass	Class
    //   170	96	3	localStringWriter1	java.io.StringWriter
    //   308	7	4	localIOException1	IOException
    //   179	120	5	localJsonWriter1	JsonWriter
    //   189	48	6	localTypeAdapter	TypeAdapter
    //   196	91	7	bool1	boolean
    //   209	85	8	bool2	boolean
    //   225	76	9	bool3	boolean
    //   282	24	10	localObject1	Object
    //   270	7	11	localIOException2	IOException
    //   7	68	12	localJsonNull	JsonNull
    //   16	87	13	localStringWriter2	java.io.StringWriter
    //   146	7	14	localIOException3	IOException
    //   27	110	15	localJsonWriter2	JsonWriter
    //   34	91	16	bool4	boolean
    //   47	85	17	bool5	boolean
    //   63	76	18	bool6	boolean
    //   120	24	19	localObject2	Object
    //   108	7	20	localIOException4	IOException
    // Exception table:
    //   from	to	target	type
    //   74	81	108	java/io/IOException
    //   74	81	120	finally
    //   110	120	120	finally
    //   18	74	146	java/io/IOException
    //   81	102	146	java/io/IOException
    //   122	146	146	java/io/IOException
    //   236	244	270	java/io/IOException
    //   236	244	282	finally
    //   272	282	282	finally
    //   171	236	308	java/io/IOException
    //   244	265	308	java/io/IOException
    //   284	308	308	java/io/IOException
  }
  
  public final String toString()
  {
    return "{serializeNulls:" + this.serializeNulls + "factories:" + this.factories + ",instanceCreators:" + this.constructorConstructor + "}";
  }
  
  static final class FutureTypeAdapter<T>
    extends TypeAdapter<T>
  {
    TypeAdapter<T> delegate;
    
    public final T read(JsonReader paramJsonReader)
      throws IOException
    {
      if (this.delegate == null) {
        throw new IllegalStateException();
      }
      return this.delegate.read(paramJsonReader);
    }
    
    public final void write(JsonWriter paramJsonWriter, T paramT)
      throws IOException
    {
      if (this.delegate == null) {
        throw new IllegalStateException();
      }
      this.delegate.write(paramJsonWriter, paramT);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.gson.Gson
 * JD-Core Version:    0.7.0.1
 */