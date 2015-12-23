package com.google.gson.internal.bind;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;
import com.google.gson.annotations.Until;
import com.google.gson.internal..Gson.Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.Primitives;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ReflectiveTypeAdapterFactory
  implements TypeAdapterFactory
{
  private final ConstructorConstructor constructorConstructor;
  private final Excluder excluder;
  private final FieldNamingStrategy fieldNamingPolicy;
  
  public ReflectiveTypeAdapterFactory(ConstructorConstructor paramConstructorConstructor, FieldNamingStrategy paramFieldNamingStrategy, Excluder paramExcluder)
  {
    this.constructorConstructor = paramConstructorConstructor;
    this.fieldNamingPolicy = paramFieldNamingStrategy;
    this.excluder = paramExcluder;
  }
  
  private boolean excludeField(Field paramField, boolean paramBoolean)
  {
    if (!this.excluder.excludeClass(paramField.getType(), paramBoolean))
    {
      Excluder localExcluder = this.excluder;
      int i;
      if ((localExcluder.modifiers & paramField.getModifiers()) != 0) {
        i = 1;
      }
      while (i == 0)
      {
        return true;
        if ((localExcluder.version != -1.0D) && (!localExcluder.isValidVersion((Since)paramField.getAnnotation(Since.class), (Until)paramField.getAnnotation(Until.class)))) {
          i = 1;
        } else {
          label140:
          label270:
          if (paramField.isSynthetic())
          {
            i = 1;
          }
          else
          {
            if (localExcluder.requireExpose)
            {
              Expose localExpose = (Expose)paramField.getAnnotation(Expose.class);
              if (localExpose != null)
              {
                if (!paramBoolean) {
                  break label140;
                }
                if (localExpose.serialize()) {
                  break label150;
                }
              }
              while (!localExpose.deserialize())
              {
                i = 1;
                break;
              }
            }
            label150:
            if ((!localExcluder.serializeInnerClasses) && (Excluder.isInnerClass(paramField.getType())))
            {
              i = 1;
            }
            else if (Excluder.isAnonymousOrLocal(paramField.getType()))
            {
              i = 1;
            }
            else
            {
              if (paramBoolean) {}
              for (List localList = localExcluder.serializationStrategies;; localList = localExcluder.deserializationStrategies)
              {
                if (localList.isEmpty()) {
                  break label270;
                }
                new FieldAttributes(paramField);
                Iterator localIterator = localList.iterator();
                do
                {
                  if (!localIterator.hasNext()) {
                    break;
                  }
                } while (!((ExclusionStrategy)localIterator.next()).shouldSkipField$6e8224bb());
                i = 1;
                break;
              }
              i = 0;
            }
          }
        }
      }
    }
    return false;
  }
  
  private Map<String, BoundField> getBoundFields(final Gson paramGson, TypeToken<?> paramTypeToken, Class<?> paramClass)
  {
    LinkedHashMap localLinkedHashMap = new LinkedHashMap();
    if (paramClass.isInterface()) {}
    for (;;)
    {
      return localLinkedHashMap;
      Type localType1 = paramTypeToken.type;
      while (paramClass != Object.class)
      {
        for (final Field localField : paramClass.getDeclaredFields())
        {
          boolean bool1 = excludeField(localField, true);
          boolean bool2 = excludeField(localField, false);
          if ((bool1) || (bool2))
          {
            localField.setAccessible(true);
            Type localType4 = paramTypeToken.type;
            Type localType5 = localField.getGenericType();
            Type localType6 = .Gson.Types.resolve(localType4, paramClass, localType5);
            SerializedName localSerializedName = (SerializedName)localField.getAnnotation(SerializedName.class);
            if (localSerializedName == null) {}
            for (String str = this.fieldNamingPolicy.translateName(localField);; str = localSerializedName.value())
            {
              final TypeToken localTypeToken = TypeToken.get(localType6);
              BoundField local1 = new BoundField(str, bool1, bool2)
              {
                final TypeAdapter<?> typeAdapter = paramGson.getAdapter(localTypeToken);
                
                final void read(JsonReader paramAnonymousJsonReader, Object paramAnonymousObject)
                  throws IOException, IllegalAccessException
                {
                  Object localObject = this.typeAdapter.read(paramAnonymousJsonReader);
                  if ((localObject != null) || (!this.val$isPrimitive)) {
                    localField.set(paramAnonymousObject, localObject);
                  }
                }
                
                final void write(JsonWriter paramAnonymousJsonWriter, Object paramAnonymousObject)
                  throws IOException, IllegalAccessException
                {
                  Object localObject = localField.get(paramAnonymousObject);
                  new TypeAdapterRuntimeTypeWrapper(paramGson, this.typeAdapter, localTypeToken.type).write(paramAnonymousJsonWriter, localObject);
                }
              };
              BoundField localBoundField = (BoundField)localLinkedHashMap.put(local1.name, local1);
              if (localBoundField == null) {
                break;
              }
              throw new IllegalArgumentException(localType1 + " declares multiple JSON fields named " + localBoundField.name);
            }
          }
        }
        Type localType2 = paramTypeToken.type;
        Type localType3 = paramClass.getGenericSuperclass();
        paramTypeToken = TypeToken.get(.Gson.Types.resolve(localType2, paramClass, localType3));
        paramClass = paramTypeToken.rawType;
      }
    }
  }
  
  public final <T> TypeAdapter<T> create(Gson paramGson, TypeToken<T> paramTypeToken)
  {
    Class localClass = paramTypeToken.rawType;
    if (!Object.class.isAssignableFrom(localClass)) {
      return null;
    }
    return new Adapter(this.constructorConstructor.getConstructor(paramTypeToken), getBoundFields(paramGson, paramTypeToken, localClass), (byte)0);
  }
  
  public final class Adapter<T>
    extends TypeAdapter<T>
  {
    private final Map<String, ReflectiveTypeAdapterFactory.BoundField> boundFields;
    private final ObjectConstructor<T> constructor;
    
    private Adapter(Map<String, ReflectiveTypeAdapterFactory.BoundField> paramMap)
    {
      this.constructor = paramMap;
      Object localObject;
      this.boundFields = localObject;
    }
    
    public final T read(JsonReader paramJsonReader)
      throws IOException
    {
      if (paramJsonReader.peek() == JsonToken.NULL)
      {
        paramJsonReader.nextNull();
        return null;
      }
      Object localObject = this.constructor.construct();
      try
      {
        paramJsonReader.beginObject();
        for (;;)
        {
          if (!paramJsonReader.hasNext()) {
            break label111;
          }
          String str = paramJsonReader.nextName();
          localBoundField = (ReflectiveTypeAdapterFactory.BoundField)this.boundFields.get(str);
          if ((localBoundField != null) && (localBoundField.deserialized)) {
            break;
          }
          paramJsonReader.skipValue();
        }
      }
      catch (IllegalStateException localIllegalStateException)
      {
        for (;;)
        {
          ReflectiveTypeAdapterFactory.BoundField localBoundField;
          throw new JsonSyntaxException(localIllegalStateException);
          localBoundField.read(paramJsonReader, localObject);
        }
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        throw new AssertionError(localIllegalAccessException);
      }
      label111:
      paramJsonReader.endObject();
      return localObject;
    }
    
    public final void write(JsonWriter paramJsonWriter, T paramT)
      throws IOException
    {
      if (paramT == null)
      {
        paramJsonWriter.nullValue();
        return;
      }
      paramJsonWriter.beginObject();
      try
      {
        Iterator localIterator = this.boundFields.values().iterator();
        while (localIterator.hasNext())
        {
          ReflectiveTypeAdapterFactory.BoundField localBoundField = (ReflectiveTypeAdapterFactory.BoundField)localIterator.next();
          if (localBoundField.serialized)
          {
            paramJsonWriter.name(localBoundField.name);
            localBoundField.write(paramJsonWriter, paramT);
          }
        }
        paramJsonWriter.endObject();
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        throw new AssertionError();
      }
    }
  }
  
  static abstract class BoundField
  {
    final boolean deserialized;
    final String name;
    final boolean serialized;
    
    protected BoundField(String paramString, boolean paramBoolean1, boolean paramBoolean2)
    {
      this.name = paramString;
      this.serialized = paramBoolean1;
      this.deserialized = paramBoolean2;
    }
    
    abstract void read(JsonReader paramJsonReader, Object paramObject)
      throws IOException, IllegalAccessException;
    
    abstract void write(JsonWriter paramJsonWriter, Object paramObject)
      throws IOException, IllegalAccessException;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.gson.internal.bind.ReflectiveTypeAdapterFactory
 * JD-Core Version:    0.7.0.1
 */