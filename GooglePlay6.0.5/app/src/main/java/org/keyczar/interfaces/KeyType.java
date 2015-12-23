package org.keyczar.interfaces;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import java.util.HashMap;
import java.util.Map;
import org.keyczar.DefaultKeyType;
import org.keyczar.KeyczarKey;
import org.keyczar.exceptions.KeyczarException;

public abstract interface KeyType
{
  public abstract Builder getBuilder();
  
  public abstract String getName();
  
  public abstract int getOutputSize();
  
  public abstract int getOutputSize(int paramInt);
  
  public abstract boolean isAcceptableSize(int paramInt);
  
  public static abstract interface Builder
  {
    public abstract KeyczarKey read(String paramString)
      throws KeyczarException;
  }
  
  public static final class KeyTypeDeserializer
    implements JsonDeserializer<KeyType>
  {
    private static Map<String, KeyType> typeMap = new HashMap();
    
    static
    {
      for (DefaultKeyType localDefaultKeyType : DefaultKeyType.values())
      {
        String str = localDefaultKeyType.getName();
        if (typeMap.containsKey(str)) {
          throw new IllegalArgumentException("Attempt to map two key types to the same name " + str);
        }
        typeMap.put(str, localDefaultKeyType);
      }
    }
  }
  
  public static final class KeyTypeSerializer
    implements JsonSerializer<KeyType>
  {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.interfaces.KeyType
 * JD-Core Version:    0.7.0.1
 */