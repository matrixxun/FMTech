package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class FamilyPurchaseSettingOption
  extends MessageNano
{
  private static volatile FamilyPurchaseSettingOption[] _emptyArray;
  public boolean hasOptionDescription = false;
  public boolean hasOptionId = false;
  public String optionDescription = "";
  public int optionId = 0;
  public FamilyPurchaseSettingWarning warningIfSelected = null;
  
  public FamilyPurchaseSettingOption()
  {
    this.cachedSize = -1;
  }
  
  public static FamilyPurchaseSettingOption[] emptyArray()
  {
    if (_emptyArray == null) {}
    synchronized (InternalNano.LAZY_INIT_LOCK)
    {
      if (_emptyArray == null) {
        _emptyArray = new FamilyPurchaseSettingOption[0];
      }
      return _emptyArray;
    }
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasOptionId) || (this.optionId != 0)) {
      i += CodedOutputByteBufferNano.computeInt32Size(1, this.optionId);
    }
    if ((this.hasOptionDescription) || (!this.optionDescription.equals(""))) {
      i += CodedOutputByteBufferNano.computeStringSize(2, this.optionDescription);
    }
    if (this.warningIfSelected != null) {
      i += CodedOutputByteBufferNano.computeMessageSize(3, this.warningIfSelected);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasOptionId) || (this.optionId != 0)) {
      paramCodedOutputByteBufferNano.writeInt32(1, this.optionId);
    }
    if ((this.hasOptionDescription) || (!this.optionDescription.equals(""))) {
      paramCodedOutputByteBufferNano.writeString(2, this.optionDescription);
    }
    if (this.warningIfSelected != null) {
      paramCodedOutputByteBufferNano.writeMessage(3, this.warningIfSelected);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.FamilyPurchaseSettingOption
 * JD-Core Version:    0.7.0.1
 */