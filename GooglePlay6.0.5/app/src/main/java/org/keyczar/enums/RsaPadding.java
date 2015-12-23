package org.keyczar.enums;

public enum RsaPadding
{
  public final String cryptAlgorithm;
  
  static
  {
    RsaPadding[] arrayOfRsaPadding = new RsaPadding[2];
    arrayOfRsaPadding[0] = OAEP;
    arrayOfRsaPadding[1] = PKCS;
    $VALUES = arrayOfRsaPadding;
  }
  
  private RsaPadding(String paramString)
  {
    this.cryptAlgorithm = paramString;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     org.keyczar.enums.RsaPadding
 * JD-Core Version:    0.7.0.1
 */