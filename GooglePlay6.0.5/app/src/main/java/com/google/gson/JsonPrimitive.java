package com.google.gson;

import com.google.gson.internal..Gson.Preconditions;
import com.google.gson.internal.LazilyParsedNumber;
import java.math.BigInteger;

public final class JsonPrimitive
  extends JsonElement
{
  private static final Class<?>[] PRIMITIVE_TYPES;
  public Object value;
  
  static
  {
    Class[] arrayOfClass = new Class[16];
    arrayOfClass[0] = Integer.TYPE;
    arrayOfClass[1] = Long.TYPE;
    arrayOfClass[2] = Short.TYPE;
    arrayOfClass[3] = Float.TYPE;
    arrayOfClass[4] = Double.TYPE;
    arrayOfClass[5] = Byte.TYPE;
    arrayOfClass[6] = Boolean.TYPE;
    arrayOfClass[7] = Character.TYPE;
    arrayOfClass[8] = Integer.class;
    arrayOfClass[9] = Long.class;
    arrayOfClass[10] = Short.class;
    arrayOfClass[11] = Float.class;
    arrayOfClass[12] = Double.class;
    arrayOfClass[13] = Byte.class;
    arrayOfClass[14] = Boolean.class;
    arrayOfClass[15] = Character.class;
    PRIMITIVE_TYPES = arrayOfClass;
  }
  
  public JsonPrimitive(Boolean paramBoolean)
  {
    setValue(paramBoolean);
  }
  
  public JsonPrimitive(Number paramNumber)
  {
    setValue(paramNumber);
  }
  
  public JsonPrimitive(String paramString)
  {
    setValue(paramString);
  }
  
  private static boolean isIntegral(JsonPrimitive paramJsonPrimitive)
  {
    boolean bool1 = paramJsonPrimitive.value instanceof Number;
    boolean bool2 = false;
    if (bool1)
    {
      Number localNumber = (Number)paramJsonPrimitive.value;
      if ((!(localNumber instanceof BigInteger)) && (!(localNumber instanceof Long)) && (!(localNumber instanceof Integer)) && (!(localNumber instanceof Short)))
      {
        boolean bool3 = localNumber instanceof Byte;
        bool2 = false;
        if (!bool3) {}
      }
      else
      {
        bool2 = true;
      }
    }
    return bool2;
  }
  
  private void setValue(Object paramObject)
  {
    if ((paramObject instanceof Character))
    {
      this.value = String.valueOf(((Character)paramObject).charValue());
      return;
    }
    int k;
    if (!(paramObject instanceof Number))
    {
      if (!(paramObject instanceof String)) {
        break label58;
      }
      k = 1;
    }
    for (;;)
    {
      boolean bool = false;
      if (k != 0) {
        bool = true;
      }
      .Gson.Preconditions.checkArgument(bool);
      this.value = paramObject;
      return;
      label58:
      Class localClass = paramObject.getClass();
      Class[] arrayOfClass = PRIMITIVE_TYPES;
      int i = arrayOfClass.length;
      for (int j = 0;; j++)
      {
        if (j >= i) {
          break label107;
        }
        if (arrayOfClass[j].isAssignableFrom(localClass))
        {
          k = 1;
          break;
        }
      }
      label107:
      k = 0;
    }
  }
  
  public final boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    JsonPrimitive localJsonPrimitive;
    double d1;
    double d2;
    do
    {
      do
      {
        do
        {
          return true;
          if ((paramObject == null) || (getClass() != paramObject.getClass())) {
            return false;
          }
          localJsonPrimitive = (JsonPrimitive)paramObject;
          if (this.value != null) {
            break;
          }
        } while (localJsonPrimitive.value == null);
        return false;
        if ((!isIntegral(this)) || (!isIntegral(localJsonPrimitive))) {
          break;
        }
      } while (getAsNumber().longValue() == localJsonPrimitive.getAsNumber().longValue());
      return false;
      if ((!(this.value instanceof Number)) || (!(localJsonPrimitive.value instanceof Number))) {
        break;
      }
      d1 = getAsNumber().doubleValue();
      d2 = localJsonPrimitive.getAsNumber().doubleValue();
    } while ((d1 == d2) || ((Double.isNaN(d1)) && (Double.isNaN(d2))));
    return false;
    return this.value.equals(localJsonPrimitive.value);
  }
  
  public final boolean getAsBoolean()
  {
    if ((this.value instanceof Boolean)) {
      return ((Boolean)this.value).booleanValue();
    }
    return Boolean.parseBoolean(getAsString());
  }
  
  public final double getAsDouble()
  {
    if ((this.value instanceof Number)) {
      return getAsNumber().doubleValue();
    }
    return Double.parseDouble(getAsString());
  }
  
  public final int getAsInt()
  {
    if ((this.value instanceof Number)) {
      return getAsNumber().intValue();
    }
    return Integer.parseInt(getAsString());
  }
  
  public final long getAsLong()
  {
    if ((this.value instanceof Number)) {
      return getAsNumber().longValue();
    }
    return Long.parseLong(getAsString());
  }
  
  public final Number getAsNumber()
  {
    if ((this.value instanceof String)) {
      return new LazilyParsedNumber((String)this.value);
    }
    return (Number)this.value;
  }
  
  public final String getAsString()
  {
    if ((this.value instanceof Number)) {
      return getAsNumber().toString();
    }
    if ((this.value instanceof Boolean)) {
      return ((Boolean)this.value).toString();
    }
    return (String)this.value;
  }
  
  public final int hashCode()
  {
    if (this.value == null) {
      return 31;
    }
    if (isIntegral(this))
    {
      long l2 = getAsNumber().longValue();
      return (int)(l2 ^ l2 >>> 32);
    }
    if ((this.value instanceof Number))
    {
      long l1 = Double.doubleToLongBits(getAsNumber().doubleValue());
      return (int)(l1 ^ l1 >>> 32);
    }
    return this.value.hashCode();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.gson.JsonPrimitive
 * JD-Core Version:    0.7.0.1
 */