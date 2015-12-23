package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface SharingSetting
{
  public static final class FamilySharingSetting
    extends MessageNano
  {
    private static volatile FamilySharingSetting[] _emptyArray;
    public int backendId = 0;
    public boolean hasBackendId = false;
    public boolean hasSharingEnabled = false;
    public boolean sharingEnabled = false;
    
    public FamilySharingSetting()
    {
      this.cachedSize = -1;
    }
    
    public static FamilySharingSetting[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new FamilySharingSetting[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.backendId != 0) || (this.hasBackendId)) {
        i += CodedOutputByteBufferNano.computeInt32Size(1, this.backendId);
      }
      if ((this.hasSharingEnabled) || (this.sharingEnabled)) {
        i += 1 + CodedOutputByteBufferNano.computeTagSize(2);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.backendId != 0) || (this.hasBackendId)) {
        paramCodedOutputByteBufferNano.writeInt32(1, this.backendId);
      }
      if ((this.hasSharingEnabled) || (this.sharingEnabled)) {
        paramCodedOutputByteBufferNano.writeBool(2, this.sharingEnabled);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class GetFamilySharingSettingsResponse
    extends MessageNano
  {
    public SharingSetting.FamilySharingSetting[] sharingSetting = SharingSetting.FamilySharingSetting.emptyArray();
    
    public GetFamilySharingSettingsResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.sharingSetting != null) && (this.sharingSetting.length > 0)) {
        for (int j = 0; j < this.sharingSetting.length; j++)
        {
          SharingSetting.FamilySharingSetting localFamilySharingSetting = this.sharingSetting[j];
          if (localFamilySharingSetting != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localFamilySharingSetting);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.sharingSetting != null) && (this.sharingSetting.length > 0)) {
        for (int i = 0; i < this.sharingSetting.length; i++)
        {
          SharingSetting.FamilySharingSetting localFamilySharingSetting = this.sharingSetting[i];
          if (localFamilySharingSetting != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localFamilySharingSetting);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class UpdateFamilySharingSettingsRequest
    extends MessageNano
  {
    public SharingSetting.FamilySharingSetting[] newSharingSetting = SharingSetting.FamilySharingSetting.emptyArray();
    
    public UpdateFamilySharingSettingsRequest()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if ((this.newSharingSetting != null) && (this.newSharingSetting.length > 0)) {
        for (int j = 0; j < this.newSharingSetting.length; j++)
        {
          SharingSetting.FamilySharingSetting localFamilySharingSetting = this.newSharingSetting[j];
          if (localFamilySharingSetting != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(1, localFamilySharingSetting);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if ((this.newSharingSetting != null) && (this.newSharingSetting.length > 0)) {
        for (int i = 0; i < this.newSharingSetting.length; i++)
        {
          SharingSetting.FamilySharingSetting localFamilySharingSetting = this.newSharingSetting[i];
          if (localFamilySharingSetting != null) {
            paramCodedOutputByteBufferNano.writeMessage(1, localFamilySharingSetting);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class UpdateFamilySharingSettingsResponse
    extends MessageNano
  {
    public UpdateFamilySharingSettingsResponse()
    {
      this.cachedSize = -1;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.SharingSetting
 * JD-Core Version:    0.7.0.1
 */