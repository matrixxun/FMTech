package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public abstract interface Preloads
{
  public static final class Preload
    extends MessageNano
  {
    private static volatile Preload[] _emptyArray;
    public String deliveryToken = "";
    public Common.Docid docid = null;
    public boolean hasDeliveryToken = false;
    public boolean hasInstallLocation = false;
    public boolean hasSize = false;
    public boolean hasTitle = false;
    public boolean hasVersionCode = false;
    public Common.Image icon = null;
    public int installLocation = 0;
    public long size = 0L;
    public String title = "";
    public int versionCode = 0;
    
    public Preload()
    {
      this.cachedSize = -1;
    }
    
    public static Preload[] emptyArray()
    {
      if (_emptyArray == null) {}
      synchronized (InternalNano.LAZY_INIT_LOCK)
      {
        if (_emptyArray == null) {
          _emptyArray = new Preload[0];
        }
        return _emptyArray;
      }
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.docid != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.docid);
      }
      if ((this.hasVersionCode) || (this.versionCode != 0)) {
        i += CodedOutputByteBufferNano.computeInt32Size(2, this.versionCode);
      }
      if ((this.hasTitle) || (!this.title.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(3, this.title);
      }
      if (this.icon != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(4, this.icon);
      }
      if ((this.hasDeliveryToken) || (!this.deliveryToken.equals(""))) {
        i += CodedOutputByteBufferNano.computeStringSize(5, this.deliveryToken);
      }
      if ((this.installLocation != 0) || (this.hasInstallLocation)) {
        i += CodedOutputByteBufferNano.computeInt32Size(6, this.installLocation);
      }
      if ((this.hasSize) || (this.size != 0L)) {
        i += CodedOutputByteBufferNano.computeInt64Size(7, this.size);
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.docid != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.docid);
      }
      if ((this.hasVersionCode) || (this.versionCode != 0)) {
        paramCodedOutputByteBufferNano.writeInt32(2, this.versionCode);
      }
      if ((this.hasTitle) || (!this.title.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(3, this.title);
      }
      if (this.icon != null) {
        paramCodedOutputByteBufferNano.writeMessage(4, this.icon);
      }
      if ((this.hasDeliveryToken) || (!this.deliveryToken.equals(""))) {
        paramCodedOutputByteBufferNano.writeString(5, this.deliveryToken);
      }
      if ((this.installLocation != 0) || (this.hasInstallLocation)) {
        paramCodedOutputByteBufferNano.writeInt32(6, this.installLocation);
      }
      if ((this.hasSize) || (this.size != 0L)) {
        paramCodedOutputByteBufferNano.writeInt64(7, this.size);
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
  
  public static final class PreloadsResponse
    extends MessageNano
  {
    public Preloads.Preload[] appPreload = Preloads.Preload.emptyArray();
    public Preloads.Preload configPreload = null;
    
    public PreloadsResponse()
    {
      this.cachedSize = -1;
    }
    
    protected final int computeSerializedSize()
    {
      int i = super.computeSerializedSize();
      if (this.configPreload != null) {
        i += CodedOutputByteBufferNano.computeMessageSize(1, this.configPreload);
      }
      if ((this.appPreload != null) && (this.appPreload.length > 0)) {
        for (int j = 0; j < this.appPreload.length; j++)
        {
          Preloads.Preload localPreload = this.appPreload[j];
          if (localPreload != null) {
            i += CodedOutputByteBufferNano.computeMessageSize(2, localPreload);
          }
        }
      }
      return i;
    }
    
    public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
      throws IOException
    {
      if (this.configPreload != null) {
        paramCodedOutputByteBufferNano.writeMessage(1, this.configPreload);
      }
      if ((this.appPreload != null) && (this.appPreload.length > 0)) {
        for (int i = 0; i < this.appPreload.length; i++)
        {
          Preloads.Preload localPreload = this.appPreload[i];
          if (localPreload != null) {
            paramCodedOutputByteBufferNano.writeMessage(2, localPreload);
          }
        }
      }
      super.writeTo(paramCodedOutputByteBufferNano);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Preloads
 * JD-Core Version:    0.7.0.1
 */