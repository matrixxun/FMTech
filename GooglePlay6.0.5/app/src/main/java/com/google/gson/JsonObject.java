package com.google.gson;

import com.google.gson.internal..Gson.Preconditions;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class JsonObject
  extends JsonElement
{
  private final Map<String, JsonElement> members = new LinkedHashMap();
  
  public final void add(String paramString, JsonElement paramJsonElement)
  {
    if (paramJsonElement == null) {
      paramJsonElement = JsonNull.INSTANCE;
    }
    this.members.put(.Gson.Preconditions.checkNotNull(paramString), paramJsonElement);
  }
  
  public final Set<Map.Entry<String, JsonElement>> entrySet()
  {
    return this.members.entrySet();
  }
  
  public final boolean equals(Object paramObject)
  {
    return (paramObject == this) || (((paramObject instanceof JsonObject)) && (((JsonObject)paramObject).members.equals(this.members)));
  }
  
  public final int hashCode()
  {
    return this.members.hashCode();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.gson.JsonObject
 * JD-Core Version:    0.7.0.1
 */