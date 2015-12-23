package com.google.gson;

import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.StringWriter;

public abstract class JsonElement
{
  public boolean getAsBoolean()
  {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }
  
  public double getAsDouble()
  {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }
  
  public int getAsInt()
  {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }
  
  public final JsonPrimitive getAsJsonPrimitive()
  {
    if ((this instanceof JsonPrimitive)) {
      return (JsonPrimitive)this;
    }
    throw new IllegalStateException("This is not a JSON Primitive.");
  }
  
  public long getAsLong()
  {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }
  
  public Number getAsNumber()
  {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }
  
  public String getAsString()
  {
    throw new UnsupportedOperationException(getClass().getSimpleName());
  }
  
  public String toString()
  {
    try
    {
      StringWriter localStringWriter = new StringWriter();
      JsonWriter localJsonWriter = new JsonWriter(localStringWriter);
      localJsonWriter.lenient = true;
      Streams.write(this, localJsonWriter);
      String str = localStringWriter.toString();
      return str;
    }
    catch (IOException localIOException)
    {
      throw new AssertionError(localIOException);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.gson.JsonElement
 * JD-Core Version:    0.7.0.1
 */