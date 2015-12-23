package com.google.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

public abstract class TypeAdapter<T>
{
  public abstract T read(JsonReader paramJsonReader)
    throws IOException;
  
  public abstract void write(JsonWriter paramJsonWriter, T paramT)
    throws IOException;
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.gson.TypeAdapter
 * JD-Core Version:    0.7.0.1
 */