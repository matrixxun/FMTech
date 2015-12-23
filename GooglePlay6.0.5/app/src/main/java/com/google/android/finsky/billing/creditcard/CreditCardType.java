package com.google.android.finsky.billing.creditcard;

public enum CreditCardType
{
  public final int cvcLength;
  public final int protobufType;
  
  static
  {
    DISCOVER = new CreditCardType("DISCOVER", 1, 4, 3);
    AMEX = new CreditCardType("AMEX", 2, 3, 4);
    MC = new CreditCardType("MC", 3, 2, 3);
    VISA = new CreditCardType("VISA", 4, 1, 3);
    CreditCardType[] arrayOfCreditCardType = new CreditCardType[5];
    arrayOfCreditCardType[0] = JCB;
    arrayOfCreditCardType[1] = DISCOVER;
    arrayOfCreditCardType[2] = AMEX;
    arrayOfCreditCardType[3] = MC;
    arrayOfCreditCardType[4] = VISA;
    $VALUES = arrayOfCreditCardType;
  }
  
  private CreditCardType(int paramInt1, int paramInt2)
  {
    this.protobufType = paramInt1;
    this.cvcLength = paramInt2;
  }
  
  public static CreditCardType getTypeForProtobufType(int paramInt)
  {
    for (CreditCardType localCreditCardType : ) {
      if (localCreditCardType.protobufType == paramInt) {
        return localCreditCardType;
      }
    }
    return null;
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.billing.creditcard.CreditCardType
 * JD-Core Version:    0.7.0.1
 */