package com.google.android.finsky.protos;

import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class Location
  extends MessageNano
{
  public long accuracyInMeters = 0L;
  public boolean hasAccuracyInMeters = false;
  public boolean hasLatitude = false;
  public boolean hasLongitude = false;
  public boolean hasTimestamp = false;
  public double latitude = 0.0D;
  public double longitude = 0.0D;
  public long timestamp = 0L;
  
  public Location()
  {
    this.cachedSize = -1;
  }
  
  protected final int computeSerializedSize()
  {
    int i = super.computeSerializedSize();
    if ((this.hasLatitude) || (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(0.0D))) {
      i += 8 + CodedOutputByteBufferNano.computeTagSize(1);
    }
    if ((this.hasLongitude) || (Double.doubleToLongBits(this.longitude) != Double.doubleToLongBits(0.0D))) {
      i += 8 + CodedOutputByteBufferNano.computeTagSize(2);
    }
    if ((this.hasAccuracyInMeters) || (this.accuracyInMeters != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(3, this.accuracyInMeters);
    }
    if ((this.hasTimestamp) || (this.timestamp != 0L)) {
      i += CodedOutputByteBufferNano.computeInt64Size(4, this.timestamp);
    }
    return i;
  }
  
  public final void writeTo(CodedOutputByteBufferNano paramCodedOutputByteBufferNano)
    throws IOException
  {
    if ((this.hasLatitude) || (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(0.0D))) {
      paramCodedOutputByteBufferNano.writeDouble(1, this.latitude);
    }
    if ((this.hasLongitude) || (Double.doubleToLongBits(this.longitude) != Double.doubleToLongBits(0.0D))) {
      paramCodedOutputByteBufferNano.writeDouble(2, this.longitude);
    }
    if ((this.hasAccuracyInMeters) || (this.accuracyInMeters != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(3, this.accuracyInMeters);
    }
    if ((this.hasTimestamp) || (this.timestamp != 0L)) {
      paramCodedOutputByteBufferNano.writeInt64(4, this.timestamp);
    }
    super.writeTo(paramCodedOutputByteBufferNano);
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.protos.Location
 * JD-Core Version:    0.7.0.1
 */