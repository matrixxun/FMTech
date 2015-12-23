package org.keyczar;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.keyczar.enums.RsaPadding;
import org.keyczar.exceptions.KeyczarException;
import org.keyczar.exceptions.UnsupportedTypeException;
import org.keyczar.interfaces.KeyType;
import org.keyczar.interfaces.KeyType.Builder;

public enum DefaultKeyType
  implements KeyType
{
  private static Map<String, KeyType> typeMapping;
  public final List<Integer> acceptableSizes;
  private final Map<Integer, Integer> outputSizeMap = new HashMap();
  
  static
  {
    Integer[] arrayOfInteger1 = new Integer[3];
    arrayOfInteger1[0] = Integer.valueOf(128);
    arrayOfInteger1[1] = Integer.valueOf(192);
    arrayOfInteger1[2] = Integer.valueOf(256);
    AES = new DefaultKeyType("AES", 0, Arrays.asList(arrayOfInteger1), 0);
    Integer[] arrayOfInteger2 = new Integer[1];
    arrayOfInteger2[0] = Integer.valueOf(256);
    HMAC_SHA1 = new DefaultKeyType("HMAC_SHA1", 1, Arrays.asList(arrayOfInteger2), 20);
    Integer[] arrayOfInteger3 = new Integer[1];
    arrayOfInteger3[0] = Integer.valueOf(1024);
    DSA_PRIV = new DefaultKeyType("DSA_PRIV", 2, Arrays.asList(arrayOfInteger3), 48);
    Integer[] arrayOfInteger4 = new Integer[1];
    arrayOfInteger4[0] = Integer.valueOf(1024);
    DSA_PUB = new DefaultKeyType("DSA_PUB", 3, Arrays.asList(arrayOfInteger4), 48);
    Integer[] arrayOfInteger5 = new Integer[3];
    arrayOfInteger5[0] = Integer.valueOf(4096);
    arrayOfInteger5[1] = Integer.valueOf(2048);
    arrayOfInteger5[2] = Integer.valueOf(1024);
    List localList1 = Arrays.asList(arrayOfInteger5);
    Integer[] arrayOfInteger6 = new Integer[3];
    arrayOfInteger6[0] = Integer.valueOf(512);
    arrayOfInteger6[1] = Integer.valueOf(256);
    arrayOfInteger6[2] = Integer.valueOf(128);
    RSA_PRIV = new DefaultKeyType("RSA_PRIV", 4, localList1, Arrays.asList(arrayOfInteger6));
    Integer[] arrayOfInteger7 = new Integer[3];
    arrayOfInteger7[0] = Integer.valueOf(4096);
    arrayOfInteger7[1] = Integer.valueOf(2048);
    arrayOfInteger7[2] = Integer.valueOf(1024);
    List localList2 = Arrays.asList(arrayOfInteger7);
    Integer[] arrayOfInteger8 = new Integer[3];
    arrayOfInteger8[0] = Integer.valueOf(512);
    arrayOfInteger8[1] = Integer.valueOf(256);
    arrayOfInteger8[2] = Integer.valueOf(128);
    RSA_PUB = new DefaultKeyType("RSA_PUB", 5, localList2, Arrays.asList(arrayOfInteger8));
    Integer[] arrayOfInteger9 = new Integer[4];
    arrayOfInteger9[0] = Integer.valueOf(256);
    arrayOfInteger9[1] = Integer.valueOf(384);
    arrayOfInteger9[2] = Integer.valueOf(521);
    arrayOfInteger9[3] = Integer.valueOf(192);
    EC_PRIV = new DefaultKeyType("EC_PRIV", 6, Arrays.asList(arrayOfInteger9), 70);
    Integer[] arrayOfInteger10 = new Integer[4];
    arrayOfInteger10[0] = Integer.valueOf(256);
    arrayOfInteger10[1] = Integer.valueOf(384);
    arrayOfInteger10[2] = Integer.valueOf(521);
    arrayOfInteger10[3] = Integer.valueOf(192);
    EC_PUB = new DefaultKeyType("EC_PUB", 7, Arrays.asList(arrayOfInteger10), 70);
    Integer[] arrayOfInteger11 = new Integer[1];
    arrayOfInteger11[0] = Integer.valueOf(1);
    TEST = new DefaultKeyType("TEST", 8, Arrays.asList(arrayOfInteger11), 0);
    DefaultKeyType[] arrayOfDefaultKeyType = new DefaultKeyType[9];
    arrayOfDefaultKeyType[0] = AES;
    arrayOfDefaultKeyType[1] = HMAC_SHA1;
    arrayOfDefaultKeyType[2] = DSA_PRIV;
    arrayOfDefaultKeyType[3] = DSA_PUB;
    arrayOfDefaultKeyType[4] = RSA_PRIV;
    arrayOfDefaultKeyType[5] = RSA_PUB;
    arrayOfDefaultKeyType[6] = EC_PRIV;
    arrayOfDefaultKeyType[7] = EC_PUB;
    arrayOfDefaultKeyType[8] = TEST;
    $VALUES = arrayOfDefaultKeyType;
  }
  
  private DefaultKeyType(List<Integer> paramList, int paramInt)
  {
    this.acceptableSizes = paramList;
    Iterator localIterator = this.acceptableSizes.iterator();
    while (localIterator.hasNext())
    {
      int i = ((Integer)localIterator.next()).intValue();
      this.outputSizeMap.put(Integer.valueOf(i), Integer.valueOf(paramInt));
    }
    addToMapping(name(), this);
  }
  
  private DefaultKeyType(List<Integer> paramList1, List<Integer> paramList2)
  {
    this.acceptableSizes = paramList1;
    for (int i = 0; i < paramList1.size(); i++) {
      this.outputSizeMap.put(this.acceptableSizes.get(i), paramList2.get(i));
    }
    addToMapping(name(), this);
  }
  
  private static void addToMapping(String paramString, KeyType paramKeyType)
  {
    if (typeMapping == null) {
      typeMapping = new HashMap();
    }
    typeMapping.put(paramString, paramKeyType);
  }
  
  public final int defaultSize()
  {
    return ((Integer)this.acceptableSizes.get(0)).intValue();
  }
  
  public final KeyType.Builder getBuilder()
  {
    return new DefaultKeyBuilder((byte)0);
  }
  
  public final String getName()
  {
    return name();
  }
  
  public final int getOutputSize()
  {
    return getOutputSize(defaultSize());
  }
  
  public final int getOutputSize(int paramInt)
  {
    return ((Integer)this.outputSizeMap.get(Integer.valueOf(paramInt))).intValue();
  }
  
  public final boolean isAcceptableSize(int paramInt)
  {
    return this.acceptableSizes.contains(Integer.valueOf(paramInt));
  }
  
  private final class DefaultKeyBuilder
    implements KeyType.Builder
  {
    private final RsaPadding padding = null;
    
    private DefaultKeyBuilder() {}
    
    public final KeyczarKey read(String paramString)
      throws KeyczarException
    {
      switch (DefaultKeyType.1.$SwitchMap$org$keyczar$DefaultKeyType[DefaultKeyType.this.ordinal()])
      {
      default: 
        throw new UnsupportedTypeException(DefaultKeyType.this);
      case 1: 
        return AesKey.read(paramString);
      case 2: 
        return HmacKey.read(paramString);
      case 3: 
        return DsaPrivateKey.read(paramString);
      case 4: 
        return DsaPublicKey.read(paramString);
      case 5: 
        return RsaPrivateKey.read(paramString);
      }
      return RsaPublicKey.read(paramString);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.DefaultKeyType
 * JD-Core Version:    0.7.0.1
 */