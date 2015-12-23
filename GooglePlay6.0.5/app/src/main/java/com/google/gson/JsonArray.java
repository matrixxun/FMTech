package com.google.gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class JsonArray
  extends JsonElement
  implements Iterable<JsonElement>
{
  private final List<JsonElement> elements = new ArrayList();
  
  public final void add(JsonElement paramJsonElement)
  {
    if (paramJsonElement == null) {
      paramJsonElement = JsonNull.INSTANCE;
    }
    this.elements.add(paramJsonElement);
  }
  
  public final boolean equals(Object paramObject)
  {
    return (paramObject == this) || (((paramObject instanceof JsonArray)) && (((JsonArray)paramObject).elements.equals(this.elements)));
  }
  
  public final boolean getAsBoolean()
  {
    if (this.elements.size() == 1) {
      return ((JsonElement)this.elements.get(0)).getAsBoolean();
    }
    throw new IllegalStateException();
  }
  
  public final double getAsDouble()
  {
    if (this.elements.size() == 1) {
      return ((JsonElement)this.elements.get(0)).getAsDouble();
    }
    throw new IllegalStateException();
  }
  
  public final int getAsInt()
  {
    if (this.elements.size() == 1) {
      return ((JsonElement)this.elements.get(0)).getAsInt();
    }
    throw new IllegalStateException();
  }
  
  public final long getAsLong()
  {
    if (this.elements.size() == 1) {
      return ((JsonElement)this.elements.get(0)).getAsLong();
    }
    throw new IllegalStateException();
  }
  
  public final Number getAsNumber()
  {
    if (this.elements.size() == 1) {
      return ((JsonElement)this.elements.get(0)).getAsNumber();
    }
    throw new IllegalStateException();
  }
  
  public final String getAsString()
  {
    if (this.elements.size() == 1) {
      return ((JsonElement)this.elements.get(0)).getAsString();
    }
    throw new IllegalStateException();
  }
  
  public final int hashCode()
  {
    return this.elements.hashCode();
  }
  
  public final Iterator<JsonElement> iterator()
  {
    return this.elements.iterator();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.gson.JsonArray
 * JD-Core Version:    0.7.0.1
 */